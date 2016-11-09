package com.lesso.util;



import org.omg.CORBA.IntHolder;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by 0003 on 2016/7/21.
 */
public class JavaUtil {
    public static void  printlnAll(Integer... a){
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }
    public static void addone(Integer a){
        a++;
    }
    public static void addone(String  a){
        a=a+"one";
    }
    public static void addone(IntHolder a){
        a.value=a.value+1;
    }

    public  static void  main(String[] args){
//        int[] a=new int[100];
//        System.out.println(a[0]);
//        a =new  int[] {1,2,3,4,5,6,7};
//        for(int e: a){
//            System.out.println(e);
//        }
//        String s="ok";
//        String s1="ok";
//        System.out.println(s==s1);
//        String s3=s1;
//        s1="s1";
//        String t1=new String("ok");
//        String t2=new String("ok");
//        String s4="";
//        s4=t1;
//        t1=t1+"s2";
//        StringBuilder sb=new StringBuilder();
//        StringBuilder sb1=sb;
//        sb.append("s");
//        System.out.println(s.hashCode());
//        System.out.println(t1.hashCode());
//        System.out.println(t2.hashCode());
//        System.out.println(s.equals(t1));
//        System.out.println(s.equals(t2));
//        System.out.println(t2.equals(t1));
//        System.out.println(s==t1);
//        System.out.println(s==t2);
//        System.out.println(t2==t1);
//        System.out.println(s==s1);
//        System.out.println(s3);
//        System.out.println(s4);
//        System.out.println(sb1.toString());
//        int[] array={1,23,4,6,78,9};
//        int[][] array2={{1,2,3},{1,2,3}};
//        System.out.println(Arrays.toString(array));
//        System.out.println(Arrays.deepToString(array2));
//        Integer a=new Integer(1000);
//        Integer b=new Integer(1000);
//        Integer c=1000;
//        Integer d=1000;
//       System.out.println(a==b);
//        System.out.println(c==d);
//        c++;
//        System.out.println(c);
//        addone(c);
//        System.out.println(c);
//        String str="str";
//        addone(str);
//        System.out.println(str);
//        IntHolder ah=new IntHolder();
//        ah.value=1;
//        addone(ah);
//        System.out.println(ah.value);
//        printlnAll(1,2,3,4);
        Integer[] a={1,2,3};

       Object[] b=badCopyOf(a,10);
        for(int i=0;i<b.length;i++)
        System.out.println( (Integer) b[i]);



        Integer[] c=(Integer[])goodCopyOf(a,10);
        for(int i=0;i<b.length;i++)
            System.out.println(  c[i]);


    }
    public static Object[] badCopyOf(Object[] a,int newLength){
        Object[] newArray=new Object[newLength];
        System.arraycopy(a,0,newArray,0,Math.min(a.length,newLength));
        return newArray;
    }
    public static Object goodCopyOf(Object a,int newLength){
        Class cl=a.getClass();
        if(!cl.isArray()) return null;
        Class componentType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType,newLength);
        System.arraycopy(a,0,newArray,0,Math.min(length,newLength));
        return  newArray;
    }

}
