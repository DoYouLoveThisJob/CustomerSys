package com.lesso.util.xml;

import com.alibaba.fastjson.JSON;
import com.lesso.entity.xml.Area;
import com.lesso.entity.xml.DB;
import com.lesso.entity.xml.Sql;
import com.lesso.entity.xml.ViewCache;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/5/23.
 */
public class DBSQLDigesterUtil {
    public static DB digester() throws Exception {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("DB", DB.class);
        digester.addSetProperties("DB", "id", "id");
        // 指明匹配模式和要创建的类
        digester.addObjectCreate("DB/sql", Sql.class);
        digester.addSetProperties("DB/sql", "id", "id");
        // 设置对象属性,与xml文件对应,不设置则是默认
        digester.addBeanPropertySetter("DB/sql/value", "context");
        // 当移动到下一个标签中时的动作
        digester.addSetNext("DB/sql", "addSql");
        DB vc = null;
        try {
            File input = new File(DBSQLDigesterUtil.class.getResource("/").getPath()+"sql.oracle.xml");
            vc = (DB) digester.parse(input);
        } catch (IOException e) {
            throw new Exception(e);
        } catch (SAXException e) {
            throw new Exception(e);
        }
        return vc;
    }
    public  static void  main(String[] args){
        try {
            System.out.println(JSON.toJSONString(DBSQLDigesterUtil.digester()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
