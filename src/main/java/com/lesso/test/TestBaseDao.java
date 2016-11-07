package com.lesso.test;


import com.lesso.dao.impl.UserLogDao;

import javax.annotation.Resource;

/**
 * Created by 0003 on 2016/4/26.
 */
public class TestBaseDao extends BaseTest{

    @Resource(name="oracleDBDao")
    UserLogDao oracleDBDao;




}
