package com.lesso.util;

/**
 * Created by Administrator on 2016/5/24.
 */
public class StringUtils {
    public static boolean isNotBlank(String str){
        if(str==null){
            return false;
        }
        if(str.equals("")){
            return false;
        }
        if(str.trim().equals("")){
            return false;
        }
        return true;
    }
}
