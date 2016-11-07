package com.lesso.entity;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by czx on 2016/8/24.
 */
public class TimerTest {
    public  static void main(String[] args){
        ActionListener listener=new TimePrinter();
        Timer t=new Timer(10000,listener);
        t.start();
        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);

    }
}
