package com.lesso.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;

import java.util.List;

/**
 * Created by czx on 2016/11/4.
 */
public class OfficeUtil {

    public static void  main(String[] args){
        try {
            OfficeUtil.writesParagraph("hello", "C:\\Users\\0003\\Desktop\\doc\\test.docx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readParagraph(String text,String fileName) throws IOException {
        InputStream inputStream=new FileInputStream(new File(fileName));
        XWPFDocument wordDoc=new XWPFDocument(inputStream);
        List<XWPFParagraph> paragraphs=wordDoc.getParagraphs();
        for(XWPFParagraph paragraph: paragraphs){
            System.out.println(paragraph.getParagraphText());
        }
        inputStream.close();
    }

    public static void writesParagraph(String text,String fileName) throws IOException {
        //新建一个文档
        XWPFDocument doc = new XWPFDocument();
        //创建一个段落
        XWPFParagraph para = doc.createParagraph();
        //一个XWPFRun代表具有相同属性的一个区域。
        XWPFRun run = para.createRun();
        run.setBold(true); //加粗
        run.setText("加粗的内容");
        run = para.createRun();
        run.setColor("FF0000");
        run.setText("红色的字。");
        OutputStream os = new FileOutputStream(fileName);
        //把doc输出到输出流
        doc.write(os);
        os.close();
    }
}
