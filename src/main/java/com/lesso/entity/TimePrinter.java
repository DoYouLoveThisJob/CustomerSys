package com.lesso.entity;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by czx on 2016/8/24.
 */
public class TimePrinter implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      Date now =new Date();
        System.out.println("At the tone,the time is "+now);
        Toolkit.getDefaultToolkit().beep();
    }
}
