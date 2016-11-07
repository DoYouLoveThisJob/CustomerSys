package com.lesso.util.xml;
import com.lesso.entity.xml.Book;
import com.lesso.entity.xml.Chapter;
import com.lesso.entity.xml.Library;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/5/23.
 */
public class LibraryDigesterUtil {
    public static void digester() throws Exception {
        Digester digester = new Digester();
        //指定它不要用DTD验证XML文档的合法性——这是因为我们没有为XML文档定义DTD
        digester.setValidating(false);
        // 从library标签开始解析,并新建一个Library对象做为根对象
        digester.addObjectCreate("library", Library.class);
        // 根据library标签属性值设置对象的属性,一次可以设置多个属性
        digester.addSetProperties("library");
        // 也可以用下面的方法，指定propertyName
        // digester.addSetProperties("library", "name", "name");
        digester.addSetProperties("library", "name", "tname");
        digester.addSetProperties("library", "address", "taddress");
        // -----第1层元素开始
        digester.addObjectCreate("library/book", Book.class);
        //digester.addSetProperties("library/book");
        // 可以用以下三条语句代替
        digester.addCallMethod("library/book", "setBookInfo", 2);
        digester.addCallParam("library/book", 0, "title");
        digester.addCallParam("library/book", 1, "author");
        /**
         * addCallParam(String rule, int  paraIndex,String attributeName)
         * 该方法与addCallMethod配合使用
         * int paraIndex:表明需要填充的方法形参序号,从 0 开始,方法由addCallMethod指定
         * String attributeName:指定标签属性名称
         */

        // -----第2层元素开始
        digester.addObjectCreate("library/book/chapter", Chapter.class);
        /** addBeanPropertySetter()是将子节点转换为对象的属性，这个方法还可以有第二个参数，当对象的属性名和子节点的名字不一样时用来指定对象的属性名
         该方法的作用及使用方法类似于addSetProperties,只不过它是用String rule规则所指定标签的值(而不是标签的属性)来调用对象的setter*/
        digester.addBeanPropertySetter("library/book/chapter/no");
        // digester.addBeanPropertySetter("library/book/chapter/no", "no");

        /** addCallMethod(String rule,String methodName, int  paraNumber) 方法
         * 同样是设置对象的属性,但是方式更加灵活,不需要对象具有setter
         * 当paraNumber = 0时,可以单独使用(表明为标签的值来调用),不然需要配合addCallParam方法
         */
        // digester.addBeanPropertySetter("library/book/chapter/caption");
        // 下面的方法，可以用来代替上一句，作用是一样的
        digester.addCallMethod("library/book/chapter/caption", "setCaption", 0);

        // addSetNext()是说在再次遇到匹配节点后， 调用当前对象(Chapter类的对象)的父对象(Book类的对象)的方法，方法参数是当前层元素的对象
        digester.addSetNext("library/book/chapter", "addChapter");
        // -----第2层元素结束

        digester.addSetNext("library/book", "addBook");
        // -----第1层元素结束

        try {
            // 解析XML文件,并得到ROOT元素
            File input = new File(LibraryDigesterUtil.class.getResource("/").getPath()+"books.xml");
            Library library = (Library) digester.parse(input);
            System.out.println(" 图书馆: " + library.getTname());
            System.out.println(" 图书馆地址: " + library.getTaddress());
            System.out.println(" 共藏书: " + library.getBookList().size() + " 本 ");
            System.out.println(" ***************************** ");

            for (Book book : library.getBookList()) {
                System.out.println(" 书名: " + book.getTitle() + "        作者: " + book.getAuthor());
                System.out.println(" ------------------------------ ");
                // 显示章节
                System.out.println(" 共 " + book.getChapters().size() + " 章 ");
                for (Chapter chapter : book.getChapters()) {
                    System.out.println(chapter.getNo() + ": " + chapter.getCaption());
                }
                System.out.println(" ------------------------------ ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


    public static void digester2() throws Exception {
        try {
            InputSource  inputSource=new  InputSource(LibraryDigesterUtil.class.getResource("/").getPath()+"books-rule.xml");
            File file2 = new File(LibraryDigesterUtil.class.getResource("/").getPath()+"books.xml");
            Digester digester = DigesterLoader.createDigester(inputSource);
            Library library = (Library) digester.parse(file2);

            System.out.println(" 图书馆: " + library.getName());
            System.out.println(" 共藏书: " + library.getBookList().size() + " 本 ");
            System.out.println(" ***************************** ");

            for (Book book : library.getBookList()) {
                System.out.println(" 书名: " + book.getTitle() + "        作者: " + book.getAuthor());
                System.out.println(" ------------------------------ ");
                // 显示章节
                System.out.println(" 共 " + book.getChapters().size() + " 章 ");
                for (Chapter chapter : book.getChapters()) {
                    System.out.println(chapter.getNo() + ": " + chapter.getCaption());
                }
                System.out.println(" ------------------------------ ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


    public  static void  main(String[] args){
        try {
           LibraryDigesterUtil.digester2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
