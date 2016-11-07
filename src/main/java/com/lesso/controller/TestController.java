package com.lesso.controller;

import com.lesso.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/24.
 */
@Controller
public class TestController {
    @Autowired
    @Qualifier(value="testService")
    private ITestService testService;
    public void task() throws InterruptedException {
        System.out.println("----------------------------------------Thread"+Thread.currentThread().getId()+" is start----------------------------------------");
        Thread.sleep(30000);
        System.out.println("----------------------------------------Thread"+Thread.currentThread().getId()+" is over----------------------------------------");
    }
    public void task2() throws InterruptedException {
       this.testService.task2();
    }
}
