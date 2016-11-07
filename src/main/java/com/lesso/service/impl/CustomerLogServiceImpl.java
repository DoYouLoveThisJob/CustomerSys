package com.lesso.service.impl;

import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.dao.IJobTaskDao;
import com.lesso.dao.IMongoDBDao;
import com.lesso.dao.IUserLogDao;
import com.lesso.entity.BehaviorLog;
import com.lesso.entity.OperateLog;
import com.lesso.service.ICustomerLogService;
import com.lesso.util.DateUtil;
import com.lesso.util.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 0003 on 2016/5/13.
 */
@Service(value="customerLogService")
public class CustomerLogServiceImpl  implements ICustomerLogService {
    @Autowired
    @Qualifier(value="mongoDBDao")
    private IMongoDBDao mongoDBDao;
    @Autowired
    @Qualifier(value="userLogDao")
    private IUserLogDao userLogDao;
    @Autowired
    @Qualifier(value="jobTaskDao")
    private IJobTaskDao jobTaskDao;
    public MsgBo getMongDBLogs() throws Exception{
        MsgBo msgBo=this.mongoDBDao.getBehaviorLogs();
        return msgBo;
    }
    public MsgBo saveLogs(List<BehaviorLog> logs) throws Exception {
        MsgBo msgBo=null;
        if(logs==null || logs.size()<=0){
            return new MsgBo(true,"",0);
        }
        msgBo=this.userLogDao.saveUserLog( logs);
        return msgBo;
    }

    public MsgBo saveLog(BehaviorLog logs) throws Exception {
        MsgBo msgBo=null;
        if(logs==null){
            return new MsgBo(true,"",0);
        }
        msgBo=this.userLogDao.saveUserLog( logs);
        return msgBo;
    }

    public MsgBo saveLogs() throws Exception {
        MsgBo msgBo=null;

        String resultMsg="";//消息结果
        //获取上一次操作时间
        Date lastOperDate=getLastOperateTime();

        Map<String,Object> resultMap=null;

        if(lastOperDate==null){
            msgBo=new MsgBo();
            msgBo.setIsSucc(false);
            msgBo.setMsg("因为此次执行获取不到上一次操作时间,所以操作失败。");
            return msgBo;
        }

        msgBo=this.mongoDBDao.getBehaviorLogList(lastOperDate);

        resultMsg=msgBo.getMsg();

        if(msgBo.getIsSucc()){
            resultMap=(Map) msgBo.getObj();
        }else{
            return  msgBo;
        }

        List<BehaviorLog> logs=(List<BehaviorLog>)resultMap.get("resultList");
        Date nowDate =(Date) resultMap.get("nowDate");

        if(logs==null || logs.size()<=0){
            msgBo= new MsgBo(true,resultMsg,0);
        }else{
            msgBo=this.userLogDao.saveUserLog( logs);
            resultMsg=resultMsg+msgBo.getMsg();
            msgBo.setMsg(resultMsg);
        }
        this.jobTaskDao.updateOperateLog(nowDate);
        return msgBo;
    }

    public MsgBo saveLogsOfLastMonth() throws Exception {
        //获取前一个月的时间截止
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dayStart =new Date();
        //String nowDateStr="2016-08-01 00:00:00";
        //Date dayStart = df.parse(DateUtil.getSpecifiedDayBefore(nowDateStr));
        Calendar day =  Calendar.getInstance();
        day.setTime(dayStart);
        Calendar startCalendar=DateUtil.getStartMounth(day);
        Date startDate =startCalendar.getTime();

        Date lastMonthDay = df.parse(DateUtil.getSpecifiedDayBefore(startDate));
        day.setTime(lastMonthDay);
        startCalendar=DateUtil.getStartMounth(day);
        Calendar endCalendar=DateUtil.getEndMounth(day);
        Date startDateLastMonth =startCalendar.getTime();
        Date endDateLastMonth =endCalendar.getTime();
        MsgBo msgBo=null;
        String resultMsg="";//消息结果
        Map<String,Object> resultMap=null;
        msgBo=this.mongoDBDao.getBehaviorLogList(startDateLastMonth,endDateLastMonth);
        resultMsg=msgBo.getMsg();

        if(msgBo.getIsSucc()){
            resultMap=(Map) msgBo.getObj();
        }else{
            return  msgBo;
        }

        List<BehaviorLog> logs=(List<BehaviorLog>)resultMap.get("resultList");
        if(logs==null || logs.size()<=0){
            msgBo= new MsgBo(true,resultMsg,0);
        }else{
            msgBo=this.userLogDao.updateUserLog( logs);
            List<BehaviorLog> addlogs=(List<BehaviorLog>)msgBo.getObj();
            if(addlogs!=null && addlogs.size()>0){
                this.userLogDao.saveUserLog( addlogs);
            }
            resultMsg=resultMsg+msgBo.getMsg();
            msgBo.setMsg(resultMsg);
        }
        return msgBo;
    }

    public void rollBackMongoDB(String uuidStr) throws Exception {
        this.mongoDBDao.rollbackData(uuidStr);
    }
    public void printUserLog()throws Exception{
        List<Map<String,Object>> result= this.userLogDao.findEmptyColUserLog();
        this.mongoDBDao.printDocumentByIds(result);
    }
    public List<BehaviorLog> getBehaviorList()throws Exception{
        return this.userLogDao.getBehaviorList();
    }
    public Date getLastOperateTime(){
        List<OperateLog>  result=this.jobTaskDao.queryList("select * from OPERATE_LOG t where t.operate_name = 'CustomSYS'", OperateLog.class);
        if(result == null || result.size()<=0){
             return null;
        }else{
            return result.get(0).getOperateTime();
        }
    }

    public DataTablesBo queryBehaviorLogsList(String seachText, String startTime,String endTime,Integer start, Integer length , Integer draw) {
        DataTablesBo dataTablesBo=new DataTablesBo();
        if(start==null || length==null){
            dataTablesBo.setData(new ArrayList<BehaviorLog>());
            dataTablesBo.setDraw(draw);
            dataTablesBo.setRecordsFiltered(0);
            dataTablesBo.setRecordsTotal(0);
            return dataTablesBo;
        }
        String countSqlhead =" select count(1) ";
        String querySqlHead =" select * ";
        String queryBody=" from bi_user_behavior_log t where ";
        String orderBy=" order by t.createtime desc ";
        List parms=new ArrayList();
        Map map=new LinkedHashMap();
        if(seachText == null ||seachText.trim().equals("")){
            //queryBody+=" 1=1 ";
            //queryBody+=" 1=? ";
            queryBody+=" 1=1 ";
            parms.add(1);
        }else{
            //queryBody+=" t.usercode like '%"+seachText+"%' ";
            //queryBody+=" t.usercode like '%'||?||'%' ";
            queryBody+=" t.usercode like :searchText ";
            parms.add(seachText);
            map.put("searchText","%"+seachText+"%");
        }

        if(startTime != null && !startTime.trim().equals("")){
            //queryBody+=" and t.createtime >= to_date('"+startTime+" 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
            //queryBody+=" and t.createtime >= to_date(?||' 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
            queryBody+=" and t.createtime >= to_date(:startTime,'yyyy-mm-dd hh24:mi:ss') ";
            //parms.add(startTime);
            map.put("startTime",startTime+" 00:00:00");
        }

        if(endTime != null && !endTime.trim().equals("")){
            // queryBody+=" and t.createtime <= to_date('"+endTime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
//           queryBody+=" and t.createtime <= to_date(?||' 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
//            parms.add(endTime);

            queryBody+=" and t.createtime <= to_date(:endTime,'yyyy-mm-dd hh24:mi:ss') ";
            //parms.add(startTime);
            map.put("endTime",endTime+" 23:59:59");
        }
        //Integer resultCount=this.oracleDBDao.queryTotal(countSqlhead+queryBody);
        //Integer resultCount=this.oracleDBDao.queryTotal(countSqlhead+queryBody,parms);
        Integer resultCount=this.userLogDao.queryTotal(countSqlhead+queryBody,map);
        if(resultCount==null || resultCount<=0){
            dataTablesBo.setData(new ArrayList<BehaviorLog>());
            dataTablesBo.setDraw(draw);
            dataTablesBo.setRecordsFiltered(0);
            dataTablesBo.setRecordsTotal(0);
        }else{
            //List<BehaviorLog> resultMap=this.oracleDBDao.queryPageList(querySqlHead+queryBody+orderBy,BehaviorLog.class,start, length ,draw);
            //List<BehaviorLog> resultMap=this.oracleDBDao.queryPageList(querySqlHead+queryBody+orderBy,BehaviorLog.class,parms,start, length ,draw);
            List<BehaviorLog> resultMap=this.userLogDao.queryPageList(querySqlHead+queryBody+orderBy,BehaviorLog.class,map,start, length ,draw);
            for(int i=0;i<resultMap.size();i++){
                resultMap.get(i).setAppTypeDesc(SysConfig.getSysName(resultMap.get(i).getAppType()));
                resultMap.get(i).setBehaviorDesc(SysConfig.getSysName(resultMap.get(i).getBehavior()));
//                resultMap.get(i).setBrowserDesc(SysConfig.getSysName(resultMap.get(i).getBrowser()));
//                resultMap.get(i).setOptSystemDesc(SysConfig.getSysName(resultMap.get(i).getOptSystem()));
                resultMap.get(i).setBrowserDesc(resultMap.get(i).getBrowser());
                resultMap.get(i).setOptSystemDesc(resultMap.get(i).getOptSystem());
            }
            if(resultMap!=null && resultMap.size()>0){
                dataTablesBo.setData(resultMap);
                dataTablesBo.setDraw(draw);
                dataTablesBo.setRecordsFiltered(resultCount);
                dataTablesBo.setRecordsTotal(resultCount);
            }else{
                dataTablesBo.setData(new ArrayList<BehaviorLog>());
                dataTablesBo.setDraw(draw);
                dataTablesBo.setRecordsFiltered(0);
                dataTablesBo.setRecordsTotal(0);
            }
        }
        return dataTablesBo;
    }

    public MsgBo getLogs(String logId) throws Exception {
        String query="select * from bi_user_behavior_log t where t.id=:id";
        Map map=new HashMap();
        map.put("id",logId);
        List<BehaviorLog> result=this.userLogDao.queryList(query,BehaviorLog.class,map);
        if(result==null || result.size()<=0){
            return new MsgBo(false,"获取不到该条记录",null);
        }else{
            result.get(0).setAppTypeDesc(SysConfig.getSysName(result.get(0).getAppType()));
            result.get(0).setBehaviorDesc(SysConfig.getSysName(result.get(0).getBehavior()));
//          result.get(0).setBrowserDesc(SysConfig.getSysName(result.get(0).getBrowser()));
//          result.get(0).setOptSystemDesc(SysConfig.getSysName(result.get(0).getOptSystem()));
            result.get(0).setBrowserDesc(result.get(0).getBrowser());
            result.get(0).setOptSystemDesc(result.get(0).getOptSystem());
            return new MsgBo(true,"",result.get(0));
        }
    }

}
