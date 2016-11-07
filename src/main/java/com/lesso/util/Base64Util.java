package com.lesso.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by 0003 on 2016/8/4.
 */
public class Base64Util {

    // 将 s 进行 BASE64 编码
    public static String getBASE64(String s) {
        if (s == null) return null;
        return (new BASE64Encoder()).encode( s.getBytes() );
    }

    // 将 BASE64 编码的字符串 s 进行解码
    public static String getFromBASE64(String s) {
        if (s == null) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
    public static void main(String[] arg){
        String str="Q2FydEVudHJ5OjQwMjg4NGY2NTUzOWJiNWIwMTU1M2UxNWJmZjAwZjlk";

        System.out.println(getFromBASE64(str) );
        String obj=(String)null;
        System.out.println(obj );
    }
}

