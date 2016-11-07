package com.lesso.util;

import com.lesso.entity.BehaviorLog;
import com.sun.deploy.util.StringUtils;
import java.util.*;

/**
 * Created by 0003 on 2016/5/19.
 */
public class SQLUtil {
    public static  String getHolonomicSqlOfOracleDB(String sql, Map<String,Object> parms){
        String querySql=sql;

        if(parms.isEmpty()){
            return querySql;
        }
        for (String key : parms.keySet()) {
            Object obj=parms.get(key);
            String repalceStr="";
            if(obj== null) {
                repalceStr = "";
            }else if(obj instanceof List){
                List<Object> list=(List)obj;
                List<String> strlist=new ArrayList<String>();
                for(Object o:list){
                    strlist.add(getBaseTypeOfOracleDB(o));
                }
                repalceStr= StringUtils.join(strlist,",");
            }else if(obj instanceof Object[]){
                Object[] list=(Object[])obj;
                List<String> strlist=new ArrayList<String>();
                for(Object o:list){
                    strlist.add(getBaseTypeOfOracleDB(o));
                }
                repalceStr= StringUtils.join(strlist,",");
            }else{
                repalceStr=getBaseTypeOfOracleDB(obj);
            }
            querySql = querySql.replace(":"+key,repalceStr);
        }
        return querySql;
    }
    public static String getBaseTypeOfOracleDB(Object obj){
        String  repalceStr="";
        if(obj == null){
            repalceStr="";
        }
        if(obj instanceof Byte){
            repalceStr=String.valueOf(obj);
        }
        if(obj instanceof Short){
            repalceStr=String.valueOf(obj);
        }
        if(obj instanceof Integer){
            repalceStr=String.valueOf(obj);
        }
        if(obj instanceof Long){
            repalceStr=String.valueOf(obj);
        }
        if(obj instanceof Float){
            repalceStr=String.valueOf(obj);
        }
        if(obj instanceof Double){
            repalceStr=String.valueOf(obj);
        }
        if(obj instanceof Character){
            repalceStr="'"+String.valueOf(obj)+"'";
        }
        if(obj instanceof Boolean){
            repalceStr=String.valueOf(obj);
        }
        if(obj instanceof  String){
            repalceStr="'"+String.valueOf(obj)+"'";
        }
        if(obj instanceof Date){
            java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String  str= format.format(obj);
            repalceStr="to_date("+str+",'yyyy-mm-dd hh24:mi:ss')";
        }
        return repalceStr;
    }
    public static void  main(String[] args){
        String off=null;
        if(off instanceof String){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
        int size=399998;
        int wn=size/10000;
        int wyn=size%10000;
        for(int i=0;i<wn;i++){
            if(i==0){
                System.out.println(""+0+"-"+((i+1)*10000-2));
            }else{
                System.out.println(""+(i*10000-2)+"-"+((i+1)*10000-2));
            }

        }
        System.out.println(""+(wn*10000-2)+"-"+(wn*10000+wyn-1));
    }
}
