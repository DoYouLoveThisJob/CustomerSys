package com.lesso.test;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.lesso.entity.UserAgent;
import com.lesso.util.BehaviorDataUtil;
import com.lesso.util.DocumentUtil;
import com.lesso.util.MongoDBConfig;
import com.lesso.util.UserAgentUtil;
import com.mchange.v2.lang.ObjectUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;


/**
 * Created by 0003 on 2016/4/26.
 */
public class TestMongoJDBC {
    public static void main(String[] arg){
        try {


            try{
//              连接到 mongodb 服务
//              MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//              连接到数据库
//              MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
//              System.out.println("Connect to database successfully");
                MongoDatabase mongoDatabase = MongoDBConfig.getDB();
                MongoCollection<Document> collection = mongoDatabase.getCollection("behavior-logs");
               // System.out.println("集合 test 选择成功");
                // 插入文档
                /**
                 * 1. 创建文档 org.bson.Document 参数为key-value的格式
                 * 2. 创建文档集合List<Document>
                 * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
                 * */
//                Document doc = new Document("name", "MongoDB")
//                        .append("type", "database")
//                        .append("count", 1)
//                        .append("info", new Document("x", 203).append("y", 102));
//                collection.insertOne(doc);
//                System.out.println("文档插入成功");
//                for(int i=0;i<1;i++){
//                    Document doc = new Document("userName", "MongoDB"+i)
//                        .append("ip", "database")
//                        .append("count", 1)
//                        .append("info", new Document("x", 203).append("y", 102));
//                    collection.insertOne(doc);
//                }

                collection.updateMany(Filters.eq("status",null),new Document("$set", new Document("staus", "0")));
                BasicDBObject exclude = new BasicDBObject();
                exclude.append("_id", 1);
                exclude.append("meta", 1);
                exclude.append("status", 1);
                //FindIterable<Document> findIterable = collection.find(Filters.eq("staus","0")).projection(exclude);
//                String variableName="meta.body.query";
//                String regexStr="query ProductQueries{product"+".*";
//                FindIterable<Document> findIterable = collection.find(Filters.regex(variableName,regexStr));
                ObjectId bsonObjectId= new ObjectId("5730266546f525a54b87b3da");

                FindIterable<Document> findIterable = collection.find(Filters.eq("_id",bsonObjectId));


                MongoCursor<Document> mongoCursor = findIterable.iterator();
                while(mongoCursor.hasNext()){

                    Document  document=mongoCursor.next();

                    System.out.println("+++++++++++++++++++++++++++++++++++++");
                    System.out.println("query:"+DocumentUtil.getValue("meta.body.query",document));
                    System.out.println("+++++++++++++++++++++++++++++++++++++");
                    System.out.println("input_0:"+ DocumentUtil.getValue("meta.body.variables",document));
                    System.out.println("+++++++++++++++++++++++++++++++++++++");
                    List<String> result=BehaviorDataUtil.getBehaviorType(document);
                    System.out.println(result);

                    System.out.println(DocumentUtil.getValue("meta.headers.user-agent",document));
                    UserAgent userAgent=UserAgentUtil.initUserAgent((String)DocumentUtil.getValue("meta.headers.user-agent",document));
                    System.out.println("OS:"+userAgent.getOpSystem());
                    System.out.println("OSCode:"+userAgent.getOpSystemCode());
                    System.out.println("OSVesion:"+userAgent.getOpVersion());
                    System.out.println("Brower:"+userAgent.getBrower());
                    System.out.println("BrowerVesion:"+userAgent.getBrowerCode());
                    System.out.println("BrowerVesion:"+userAgent.getBrowerVesion());
                }
                //collection.updateMany(Filters.eq("status","0"),new Document("$set", new Document("staus", "1")));
                // TODO Auto-generated method stub
//                MongoClient mongoClient = new MongoClient();
//                DB psdoc = mongoClient.getDB("admin");
//                DBCollection user=psdoc.getCollection("user");
//                User u1=new User();
//                u1.setAge(20);
//                u1.setUsername("ssss");
//                Gson gson=new Gson();
//                //转换成json字符串，再转换成DBObject对象
//                DBObject dbObject = (DBObject) JSON.parse(gson.toJson(u1));
//                //插入数据库
//                user.insert(dbObject);
//
//                DBCursor cursor=user.find();
//                while(cursor.hasNext())
//                {
//                    DBObject obj=cursor.next();
//                    //反转
//                    User u=gson.fromJson(obj.toString(), User.class);
//                    System.out.println(u.getAge());
//                }

            }catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
