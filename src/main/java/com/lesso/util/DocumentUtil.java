package com.lesso.util;

import com.alibaba.fastjson.JSON;
import com.lesso.base.MsgBo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2016/5/11.
 */
public class DocumentUtil {
    public static Object getValue(String name, Document document){
        try{
            String[] member= name.split("\\.");
            if(member.length!=1){
                return getValue(name.replaceFirst(member[0]+".",""),(Document)document.get(member[0]));
            }else{
                return  document.get(name);
            }
        }catch (Exception e){
            return  null;
        }
    }
    public static void main(String[] args){



    }
}
