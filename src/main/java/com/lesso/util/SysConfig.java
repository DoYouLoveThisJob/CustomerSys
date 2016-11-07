package com.lesso.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/5/12.
 */
public class SysConfig {
    static private String SYS_RESOURCE_FILE="sys.properties";
    static private Map<String,String> sfgMap= new HashMap<String,String>();
    static private Map<String,String> sfgMap2= new HashMap<String,String>();
    /**
     * 获取文件中的连接参数
     */
    public static File getConfigFile() {
        String path = MongoDBConfig.class.getResource("/").getPath();
        String fileName = path + SYS_RESOURCE_FILE;
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
    public static void initData(int type) {
        File file = getConfigFile();
        if (file != null) {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream(file));
                for (Enumeration enu = p.propertyNames(); enu.hasMoreElements();) {
                    String key = (String) enu.nextElement();
                    String value = (String) p.getProperty(key);
                    if(type==0){
                        sfgMap.put(key, value);
                    }else{
                        sfgMap2.put(value,key);
                    }
                }
            } catch (IOException e) {
                System.out.println("加载SYS配置文件失败!");
                e.printStackTrace();
            }
        }
    }
    public static String  getSysData(String name){
        if(sfgMap.isEmpty()){
            initData(0);
        }
        return sfgMap.get(name);
    }

    public static String  getSysName(String code){
        if(sfgMap2.isEmpty()){
            initData(1);
        }
        return sfgMap2.get(code);
    }

}
