package com.lesso.util;


import com.alibaba.fastjson.JSON;
import com.lesso.base.MyColumn;
import com.lesso.base.MyTable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 0003 on 2016/4/29.
 */
public class ReflectUtil {
    /**
     * 获取注解映射
     * **/
    public static<T> Map<String,String> getFieldHasColumnAnnoMap(Class<T> clazz){
        Field[] fields = clazz.getDeclaredFields();
        //存放field和column对应关系，该关系来自于实体类的 @Column配置
        Map<String/*field name in modelBean*/, String/*column in db*/> fieldHasColumnAnnoMap = new LinkedHashMap<String, String>();
        Annotation[] annotations = null;
        for (Field field : fields)
        {
            annotations = field.getAnnotations();
            if(annotations==null || annotations.length==0){
                fieldHasColumnAnnoMap.put(field.getName(), field.getName());
            }else{
                for (Annotation an : annotations)
                {
                    if (an instanceof MyColumn)
                    {
                        MyColumn column = (MyColumn)an;
                        fieldHasColumnAnnoMap.put(field.getName(), column.name());
                    }
                }
            }
        }
        return fieldHasColumnAnnoMap;
    }
    /**
     * 将jdbcTemplate查询的map结果集 反射生成对应的bean
     * @param clazz 意向反射的实体.clazz
     * @param jdbcMapResult 查询结果集  key is UpperCase
     * @return
     * @see
     */
    public static <T> T reflectObject(Class<T> clazz, Map<String, Object> jdbcMapResult)
    {
        Map<String/*field name in modelBean*/, String/*column in db*/> fieldHasColumnAnnoMap = getFieldHasColumnAnnoMap(clazz);
        //存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
        Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet())
        {
            //将column大写。因为jdbcMapResult key is UpperCase
            String key = en.getValue().toUpperCase();
            //获得map的该field的属性值
            Object value = jdbcMapResult.get(key);
            //确保value有效性，防止JSON reflect时异常
            if (value != null)
            {
                conCurrent.put(en.getKey(), jdbcMapResult.get(key));
            }
        }
        //fastjson reflect to modelbean
        return JSON.parseObject(JSON.toJSONString(conCurrent), clazz);
    }
    /***
     * 通过jdbcMapResult映射结果集
     * **/
    public static  <T>  List<T> reflectList(List<Map<String,Object>> jdbcMapResults,Class<T> clazz)
    {
        //获得
        List<T> result=new ArrayList();
        Map<String/*field name in modelBean*/, String/*column in db*/> fieldHasColumnAnnoMap = getFieldHasColumnAnnoMap(clazz);

        //存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
        for(Map<String, Object> jdbcMapResult:jdbcMapResults) {
            Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
            for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet()) {
                //将column大写。因为jdbcMapResult key is UpperCase
                String key = en.getValue().toUpperCase();
                //获得map的该field的属性值
                Object value = jdbcMapResult.get(key);
                //确保value有效性，防止JSON reflect时异常
                if (value != null) {
                    conCurrent.put(en.getKey(), jdbcMapResult.get(key));
                }
            }
            result.add(JSON.parseObject(JSON.toJSONString(conCurrent), clazz));
        }
        return result;
    }


    public static  <T>  List<T> reflectList(ResultSet rs, Class<T> clazz) throws SQLException {
        //获得
        List<T> result=new ArrayList();
        Map<String/*field name in modelBean*/, String/*column in db*/> fieldHasColumnAnnoMap = getFieldHasColumnAnnoMap(clazz);
        //存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
        while(rs.next()) {
            Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
            for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet()) {
                //将column大写。因为jdbcMapResult key is UpperCase
                String key = en.getValue().toUpperCase();
                //获得map的该field的属性值
                Object value = rs.getObject(key);
                //确保value有效性，防止JSON reflect时异常
                if (value != null) {
                    if(value instanceof oracle.sql.TIMESTAMP){
                        conCurrent.put(en.getKey(), rs.getTimestamp(key));
                    }else{
                        conCurrent.put(en.getKey(), rs.getObject(key));
                    }
                }
            }
            String jsonStr=JSON.toJSONString(conCurrent);
            result.add(JSON.parseObject(jsonStr, clazz));
        }
        return result;
    }

    public static  <T>  List<T> reflectObject(ResultSet rs, Class<T> clazz) throws SQLException {
        //获得
        List<T> result=new ArrayList();
        Map<String/*field name in modelBean*/, String/*column in db*/> fieldHasColumnAnnoMap = getFieldHasColumnAnnoMap(clazz);
        //存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
            Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
            for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet()) {
                //将column大写。因为jdbcMapResult key is UpperCase
                String key = en.getValue().toUpperCase();
                //获得map的该field的属性值
                Object value = rs.getObject(key);
                //确保value有效性，防止JSON reflect时异常
                if (value != null) {
                    if(value instanceof oracle.sql.TIMESTAMP ){
                        conCurrent.put(en.getKey(), rs.getTimestamp(key));
                    }else{
                        conCurrent.put(en.getKey(), rs.getObject(key));
                    }
                }
            }
            String jsonStr=JSON.toJSONString(conCurrent);
            result.add(JSON.parseObject(jsonStr, clazz));
        return result;
    }

    public static<T>  String getTableName(Class<T> clazz)
    {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation an : annotations)
        {
            if (an instanceof MyTable)
            {
                MyTable tableName = (MyTable)an;
                return tableName.name();
            }
        }
        return "";
    }

    /**
     * test example
     * @param args
     * @throws Exception
     * @see
     */
    public static void main(String[] args)
            throws Exception
    {

    }
}
