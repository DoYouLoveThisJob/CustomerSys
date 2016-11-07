package com.lesso.util;

/**
 * Created by 0003 on 2016/4/27.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * MongoDB配置类
 *
 */
public class MongoDBConfig {

    private static MongoClient mongo;
    private static MongoDatabase db;
    //连接信息
    private static final String MONGO_DB_ADDRESS = "....";
    private static final int MONGO_DB_PORT = 1111;
    private static final String MONGO_DB_USERNAME = "...";
    private static final String MONGO_DB_PASSWORD = "...";
    private static final String MONGO_DB_DBNAME = "...";
    //连接信息文件存放位置
    private static final String MONGO_DB_RESOURCE_FILE = "mongodb.properties";
    /**
     * Mongo数据库参数
     */
    private static Map<String, String> cfgMap = new HashMap<String, String>();
    /**
     * 数据库存储 DB对象
     */
    private static Hashtable<String, MongoDatabase> mongoDBs = new Hashtable<String, MongoDatabase>();
    /**
     * 初始化Mongo的数据库
     */
    static {
        init();
    }
    /**
     * 获取文件中的连接参数
     */
    public static File getConfigFile() {
        String path = MongoDBConfig.class.getResource("/").getPath();
        String fileName = path + MONGO_DB_RESOURCE_FILE;
        File file = new File(fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }
    /**
     * 将连接参数  存储在cfgMap中
     */
    @SuppressWarnings("unchecked")
    private static void initCfgMap() {
        File file = getConfigFile();
        if (file != null) {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream(file));
                for (Enumeration enu = p.propertyNames(); enu.hasMoreElements();) {
                    String key = (String) enu.nextElement();
                    String value = (String) p.getProperty(key);
                    cfgMap.put(key, value);
                }
            } catch (IOException e) {
                System.out.println("记载Mongo配置文件失败!");
                e.printStackTrace();
            }
        } else {
            cfgMap.put("mongo.db.address", MONGO_DB_ADDRESS);
            cfgMap.put("mongo.db.port", String.valueOf(MONGO_DB_PORT));
            cfgMap.put("mongo.db.username", MONGO_DB_USERNAME);
            cfgMap.put("mongo.db.password", MONGO_DB_PASSWORD);
            cfgMap.put("mongo.db.dbname", MONGO_DB_DBNAME);
        }
    }

    /**
     * 初始化Mongo数据库
     */
    private static void init() {
        initCfgMap();
        try {
            String address = cfgMap.get("mongo.db.address");
            int port = Integer.parseInt(cfgMap.get("mongo.db.port").toString());
            String dbName = cfgMap.get("mongo.db.dbname");
            String username = cfgMap.get("mongo.db.username");
            String password = cfgMap.get("mongo.db.password");
            //根据地址 address  和 端口port获取mongo对象

            if (dbName != null && !"".equals(dbName)) {
                if (username != null && !"".equals(username)) {
                    //验证身份
                    ServerAddress serverAddress = new ServerAddress(address,port);
                    List<ServerAddress> addrs = new ArrayList<ServerAddress>();
                    addrs.add(serverAddress);
                    //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
                    MongoCredential credential = MongoCredential.createScramSha1Credential(username, dbName, password.toCharArray());
                    List<MongoCredential> credentials = new ArrayList<MongoCredential>();
                    credentials.add(credential);
                    //通过连接认证获取MongoDB连接
                    mongo = new MongoClient(addrs,credentials);
                }else{
                    mongo = new MongoClient(address, port);
                }

                //根据mongo对象获取DB对象
                db = mongo.getDatabase(dbName);
                //将DB对象存储
                mongoDBs.put(dbName, db);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到Mongo的实例
     *
     * @return
     */
    public static MongoClient getMongo() {
        return mongo;
    }
    /**
     * 得到Mongo的实例
     * @return
     */
    public static MongoDatabase getDB() {
        return db;
    }
    public static boolean  retsetClient(String address,int port){
        boolean flag=false;
        try {
            //根据地址 address  和 端口port获取mongo对象
            if (address != null && !"".equals(address)) {
                mongo = new MongoClient(address, port);
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
//    public static boolean retsetDB(String address,int port,String dbName) {
//        boolean flag=false;
//        try {
//            //根据地址 address  和 端口port获取mongo对象
//            if (address != null && !"".equals(address)) {
//                if (dbName != null && !"".equals(dbName))
//                {
//                    mongo = new MongoClient(address, port);
//                    //根据mongo对象获取DB对象
//                    db = mongo.getDatabase(dbName);
//                    //将DB对象存储
//                    mongoDBs.put(dbName, db);
//                    flag=true;
//               }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
    public static MongoCollection getCollection(String collectionName){
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection;
    }
    public static boolean retsetDB(String address,int port,String dbName,String userName,String  pwd) {
        boolean flag =false;
        try {

            //根据地址 address  和 端口port获取mongo对象
            if (dbName != null && !"".equals(dbName)) {
                if (userName != null && !"".equals(userName)) {
                    //验证身份
                    ServerAddress serverAddress = new ServerAddress(address,port);
                    List<ServerAddress> addrs = new ArrayList<ServerAddress>();
                    addrs.add(serverAddress);
                    //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
                    MongoCredential credential = MongoCredential.createScramSha1Credential(userName, dbName, pwd.toCharArray());
                    List<MongoCredential> credentials = new ArrayList<MongoCredential>();
                    credentials.add(credential);
                    //通过连接认证获取MongoDB连接
                    mongo = new MongoClient(addrs,credentials);
                }else{
                    mongo = new MongoClient(address, port);
                }
                //根据mongo对象获取DB对象
                db = mongo.getDatabase(dbName);
                //将DB对象存储
                mongoDBs.put(dbName, db);
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

}

