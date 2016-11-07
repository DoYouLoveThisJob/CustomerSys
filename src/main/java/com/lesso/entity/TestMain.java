package com.lesso.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by 0003 on 2016/8/11.
 */
public class TestMain {
    //测试反射例子
    public static void main(String[] args){
       Class clazz=TestClass.class;
       Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            System.out.println("this class of field is:"+field.getClass().toString());
            System.out.println("this name of field is:"+field.getName());
            Modifier.isPublic(field.getModifiers());
            Modifier.isNative(field.getModifiers());
            Modifier.isPrivate(field.getModifiers());
            Modifier.isProtected(field.getModifiers());
            String modifier= Modifier.toString(field.getModifiers())==""?"native":Modifier.toString(field.getModifiers());
            System.out.println("this level of modifiers in this field is:"+modifier);
        }


    }
}
