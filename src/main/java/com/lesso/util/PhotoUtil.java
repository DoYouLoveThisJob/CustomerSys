package com.lesso.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by czx on 2016/11/16.
 */
public class PhotoUtil {

    public static boolean compressPic(String srcFilePath, String descFilePath,Float qality) throws IOException {
        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;

        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        qality=(qality==null||qality>1||qality<0)?1:qality;
        imgWriteParams.setCompressionQuality((float) qality);
        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
        ColorModel colorModel = ImageIO.read(new File(srcFilePath)).getColorModel();// ColorModel.getRGBdefault();
        // 指定压缩时使用的色彩模式
//        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
//                colorModel, colorModel.createCompatibleSampleModel(16, 16)));
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(16, 16)));

        try {
            if (isBlank(srcFilePath)) {
                return false;
            } else {
                file = new File(srcFilePath);
                //System.out.println(file.length());
                src = ImageIO.read(file);
                out = new FileOutputStream(descFilePath);

                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null),
                        imgWriteParams);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0 || string.trim().equals("")) {
            return true;
        }
        return false;
    }
    public static void  main(String[] args){
        try {
            PhotoUtil.compressPic("D:\\photo\\photo1.jpg","D:\\photo\\photo2.jpg",0.5f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
