package com.lesso.util.xml;

import com.alibaba.fastjson.JSON;
import com.lesso.entity.xml.Area;
import com.lesso.entity.xml.ViewCache;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/5/23.
 */
public class AreaDigesterUtil {
    public static ViewCache digester() throws Exception {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("viewcache/areas", ViewCache.class);
        // 指明匹配模式和要创建的类
        digester.addObjectCreate("viewcache/areas/area", Area.class);
        // 设置对象属性,与xml文件对应,不设置则是默认
        digester.addBeanPropertySetter("viewcache/areas/area/id", "id");
        digester.addBeanPropertySetter("viewcache/areas/area/parentId", "parentId");
        digester.addBeanPropertySetter("viewcache/areas/area/name", "name");
        digester.addBeanPropertySetter("viewcache/areas/area/areaType", "areaType");
        digester.addBeanPropertySetter("viewcache/areas/area/ordering", "ordering");
        digester.addBeanPropertySetter("viewcache/areas/area/zip", "zip");
        digester.addBeanPropertySetter("viewcache/areas/area/phoneArea", "phoneArea");
        // 当移动到下一个标签中时的动作
        digester.addSetNext("viewcache/areas/area", "addArea");
        ViewCache vc = null;
        try {
            File input = new File(AreaDigesterUtil.class.getResource("/").getPath()+"viewcache.xml");
            vc = (ViewCache) digester.parse(input);
        } catch (IOException e) {
            throw new Exception(e);
        } catch (SAXException e) {
            throw new Exception(e);
        }
        return vc;
    }
    public  static void  main(String[] args){
        try {
            System.out.println(JSON.toJSONString(AreaDigesterUtil.digester()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
