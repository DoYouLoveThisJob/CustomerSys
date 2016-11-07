package com.lesso.dao.impl;

import com.lesso.base.MsgBo;
import com.lesso.dao.IMongoDBDao;
import com.lesso.entity.BehaviorLog;
import com.lesso.entity.UserAgent;
import com.lesso.util.*;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

/**
 * Created by 0003 on 2016/4/26.
 */
@Repository(value = "mongoDBDao")
public class MongoDBDao implements IMongoDBDao {
    /**
     * 将mongodb的客户日志信息转换成需要的客户日志实体
     * **/
    public MsgBo getBehaviorLogs() throws Exception {
        MsgBo msgBo=new MsgBo();
        List<BehaviorLog> result=new ArrayList<BehaviorLog>();
        //获取数据库集合链接
        MongoCollection<Document> collection = MongoDBConfig.getCollection("behavior-logs");
        UUID uuid = UUID.randomUUID();
        String uuidStr=uuid.toString();
        msgBo.setMsg(uuidStr);
        //锁定数据
        collection.updateMany(Filters.eq("status",null),new Document("$set", new Document("status", uuidStr)));
        FindIterable<Document> findIterable = collection.find(Filters.eq("status",uuidStr));
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        Date nowDate =new Date();
        String appVesion= SysConfig.getSysData("appVesion");
        int size=0;
        try{
            while(mongoCursor.hasNext()){
                size++;
                Document  logs=mongoCursor.next();
                if(logs==null){
                    continue;
                }
                Document  meta=(Document)logs.get("meta");
                if(meta==null){
                    continue;
                }
                Document  user=(Document)meta.get("user");
                Document  headers=(Document)meta.get("headers");
                if(headers==null||user==null){
                    continue;
                }
                BehaviorLog behaviorLog=new BehaviorLog();
                behaviorLog.setId(logs.get("_id").toString());
                behaviorLog.setAppType("102");
                List<String> behaviorTypes=  BehaviorDataUtil.getBehaviorType(logs);
                String behaviorTypeCode="";
                String behaviorTypeContent="";
                if(behaviorTypes!=null && behaviorTypes.size()>0){
                    behaviorTypeCode=behaviorTypes.get(0);
                    behaviorTypeContent=behaviorTypes.get(1);
                }
                behaviorLog.setBehavior(behaviorTypeCode);
                behaviorLog.setBehaviorContent(behaviorTypeContent);
                behaviorLog.setPageTitle(null);
                behaviorLog.setSessionId(null);
                behaviorLog.setIpAddress(BehaviorDataUtil.getIpAddress((String)headers.get("x-forwarded-for")));
                behaviorLog.setLongitude(null);
                behaviorLog.setLatitude(null);
                behaviorLog.setUserCode((String)user.get("uid"));
                behaviorLog.setObjectid( logs.get("_id").toString());
                behaviorLog.setOptPath((String)headers.get("referer"));
                behaviorLog.setDelflag(0);
                behaviorLog.setCreateTime((Date)logs.get("timestamp"));
                behaviorLog.setLastUpDated((Date)logs.get("timestamp"));
                behaviorLog.setAppVersion(appVesion);
                UserAgent userAgent=UserAgentUtil.initUserAgent((String) DocumentUtil.getValue("meta.headers.user-agent",logs));
                behaviorLog.setOptSystem( userAgent.getOpSystem());
                behaviorLog.setOptSystemVersion(userAgent.getOpVersion());
                behaviorLog.setBrowser(userAgent.getBrower());
                behaviorLog.setBrowserVersion(userAgent.getBrowerVesion());
                behaviorLog.setDevice(userAgent.getDeviceName());
                result.add(behaviorLog);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            rollbackData(uuidStr);
            msgBo.setIsSucc(false);
            return msgBo;
        }finally {
            mongoCursor.close();
        }
        msgBo.setObj(result);
        msgBo.setMsg("在mongodb获取"+size+"条数据,转换成功为"+result.size()+"条待处理数据;");
        msgBo.setIsSucc(true);
        return msgBo;
    }
   //按lastOperaTime为起始时间段,当前时间为截至时间查询记录:lastOperaTime<=记录时间<当前时间
    public MsgBo getBehaviorLogList(Date lastOperaTime) throws Exception {
        MsgBo msgBo=new MsgBo();
        List<BehaviorLog> result=new ArrayList<BehaviorLog>();
        //获取数据库集合链接
        MongoCollection<Document> collection = MongoDBConfig.getCollection("behavior-logs");
        //锁定数据
        Date nowDate =new Date();
        FindIterable<Document> findIterable = collection.find(Filters.and(Filters.gte("timestamp",lastOperaTime),Filters.lt("timestamp",nowDate)));
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        String appVesion= SysConfig.getSysData("appVesion");
        int size=0;
        try{
            while(mongoCursor.hasNext()){
                size++;
                Document  logs=mongoCursor.next();
                if(logs==null){
                    continue;
                }
                Document  meta=(Document)logs.get("meta");
                if(meta==null){
                    continue;
                }
                Document  user=(Document)meta.get("user");
                Document  headers=(Document)meta.get("headers");
                if(headers==null||user==null){
                    continue;
                }
                BehaviorLog behaviorLog=new BehaviorLog();
                behaviorLog.setId(logs.get("_id").toString());
                behaviorLog.setAppType("102");
                List<String> behaviorTypes=  BehaviorDataUtil.getBehaviorType(logs);
                String behaviorTypeCode="";
                String behaviorTypeContent="";
                if(behaviorTypes!=null && behaviorTypes.size()>0){
                    behaviorTypeCode=behaviorTypes.get(0);
                    behaviorTypeContent=behaviorTypes.get(1).length()>4000?"":behaviorTypes.get(1);
                }
                behaviorLog.setBehavior(behaviorTypeCode);
                behaviorLog.setBehaviorContent(behaviorTypeContent);
                behaviorLog.setPageTitle(null);
                behaviorLog.setSessionId(null);
                behaviorLog.setIpAddress(BehaviorDataUtil.getIpAddress((String)headers.get("x-forwarded-for")));
                behaviorLog.setLongitude(null);
                behaviorLog.setLatitude(null);
                behaviorLog.setUserCode((String)user.get("uid"));
                behaviorLog.setObjectid( logs.get("_id").toString());
                behaviorLog.setOptPath((String)headers.get("referer"));
                behaviorLog.setDelflag(0);
                behaviorLog.setCreateTime((Date)logs.get("timestamp"));
                behaviorLog.setLastUpDated((Date)logs.get("timestamp"));
                behaviorLog.setAppVersion(appVesion);
                UserAgent userAgent=UserAgentUtil.initUserAgent((String) DocumentUtil.getValue("meta.headers.user-agent",logs));
                behaviorLog.setOptSystem( userAgent.getOpSystem());
                behaviorLog.setOptSystemVersion(userAgent.getOpVersion());
                behaviorLog.setBrowser(userAgent.getBrower());
                behaviorLog.setBrowserVersion(userAgent.getBrowerVesion());
                behaviorLog.setDevice(userAgent.getDeviceName());
                result.add(behaviorLog);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            msgBo.setIsSucc(false);
            return msgBo;
        }finally {
            mongoCursor.close();
        }
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("resultList",result);
        resultMap.put("nowDate",nowDate);
        msgBo.setObj(resultMap);
        msgBo.setMsg("在mongodb获取"+size+"条数据,转换成功为"+result.size()+"条待处理数据;");
        msgBo.setIsSucc(true);
        return msgBo;
    }

    //按startTime为起始时间段,按endTime为截至时间查询记录:startTime<=记录时间<=endTime
    public MsgBo getBehaviorLogList(Date startTime,Date endTime) throws Exception {
        MsgBo msgBo=new MsgBo();
        List<BehaviorLog> result=new ArrayList<BehaviorLog>();
        //获取数据库集合链接
        MongoCollection<Document> collection = MongoDBConfig.getCollection("behavior-logs");
        //锁定数据
        FindIterable<Document> findIterable = collection.find(Filters.and(Filters.gte("timestamp",startTime),Filters.lte("timestamp",endTime)));
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        String appVesion= SysConfig.getSysData("appVesion");
        int size=0;
        try{
            while(mongoCursor.hasNext()){
                size++;
                Document  logs=mongoCursor.next();
                if(logs==null){
                    continue;
                }
                Document  meta=(Document)logs.get("meta");
                if(meta==null){
                    continue;
                }
                Document  user=(Document)meta.get("user");
                Document  headers=(Document)meta.get("headers");
                if(headers==null||user==null){
                    continue;
                }
                BehaviorLog behaviorLog=new BehaviorLog();
                behaviorLog.setId(logs.get("_id").toString());
                behaviorLog.setAppType("102");
                List<String> behaviorTypes=  BehaviorDataUtil.getBehaviorType(logs);
                String behaviorTypeCode="";
                String behaviorTypeContent="";
                if(behaviorTypes!=null && behaviorTypes.size()>0){
                    behaviorTypeCode=behaviorTypes.get(0);
                    behaviorTypeContent=behaviorTypes.get(1).length()>4000?"":behaviorTypes.get(1);
                }
                behaviorLog.setBehavior(behaviorTypeCode);
                behaviorLog.setBehaviorContent(behaviorTypeContent);
                behaviorLog.setPageTitle(null);
                behaviorLog.setSessionId(null);
                behaviorLog.setIpAddress(BehaviorDataUtil.getIpAddress((String)headers.get("x-forwarded-for")));
                behaviorLog.setLongitude(null);
                behaviorLog.setLatitude(null);
                behaviorLog.setUserCode((String)user.get("uid"));
                behaviorLog.setObjectid( logs.get("_id").toString());
                behaviorLog.setOptPath((String)headers.get("referer"));
                behaviorLog.setDelflag(0);
                behaviorLog.setCreateTime((Date)logs.get("timestamp"));
                behaviorLog.setLastUpDated((Date)logs.get("timestamp"));
                behaviorLog.setAppVersion(appVesion);
                UserAgent userAgent=UserAgentUtil.initUserAgent((String) DocumentUtil.getValue("meta.headers.user-agent",logs));
                behaviorLog.setOptSystem( userAgent.getOpSystem());
                behaviorLog.setOptSystemVersion(userAgent.getOpVersion());
                behaviorLog.setBrowser(userAgent.getBrower());
                behaviorLog.setBrowserVersion(userAgent.getBrowerVesion());
                behaviorLog.setDevice(userAgent.getDeviceName());
                result.add(behaviorLog);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            msgBo.setIsSucc(false);
            return msgBo;
        }finally {
            mongoCursor.close();
        }

        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("resultList",result);
        msgBo.setObj(resultMap);
        msgBo.setMsg("全局增量在mongodb获取"+size+"条数据,转换成功为"+result.size()+"条待处理数据;");
        msgBo.setIsSucc(true);
        return msgBo;
    }

    public void rollbackData( List<BehaviorLog> rollData)throws Exception{
        List<ObjectId> rollIds=new ArrayList<ObjectId>();
        for(BehaviorLog logs:rollData){
          rollIds.add(new ObjectId(logs.getObjectid()));
        }
        //获取数据库集合链接
        MongoCollection<Document> collection = MongoDBConfig.getCollection("behavior-logs");
        //更新数据
        collection.updateMany(Filters.in("_id",rollIds),new Document("$set", new Document("status", null)));
    }

    public void rollbackData( String uuidStr)throws Exception{
        //获取数据库集合链接
        MongoCollection<Document> collection = MongoDBConfig.getCollection("behavior-logs");
        //更新数据
        collection.updateMany(Filters.in("status",uuidStr),new Document("$set", new Document("status", null)));
    }

    public void printDocumentByIds(List<Map<String,Object>> ids)throws Exception{
        MongoCollection<Document> collection = MongoDBConfig.getCollection("behavior-logs");
        //PrintStream p=new PrintStream(out);
        File file=new File("D:\\queryData\\queryDataOfIp.txt");
        OutputStream out=new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
        for(Map<String,Object> id :ids) {
            ObjectId bsonObjectId = new ObjectId((String) id.get("objectid"));
            FindIterable<Document> findIterable = collection.find(Filters.eq("_id", bsonObjectId));
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                Document document = mongoCursor.next();
                writer.append("+++++++++++++++++++++++++++++++++++++");
                writer.append("\r\n");
                writer.append("ipAdress:" + DocumentUtil.getValue("meta.headers.x-forwarded-for", document));
                writer.append("\r\n");
            }
        }
        writer.close();
        out.close();
    }

}
