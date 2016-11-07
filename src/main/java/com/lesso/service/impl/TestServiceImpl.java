package com.lesso.service.impl;

import com.lesso.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/5/28.
 */
@Service(value="testService")
public class TestServiceImpl implements ITestService {
    public void task2(){
        System.out.print("-----------------------task2 in TestServiceImpl is start--------------------------------------");
    }

}
