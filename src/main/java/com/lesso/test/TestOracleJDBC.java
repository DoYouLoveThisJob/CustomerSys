package com.lesso.test;

import com.alibaba.fastjson.JSON;
import com.lesso.dao.impl.UserLogDao;
import com.lesso.entity.BehaviorLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0003 on 2016/5/19.
 */
public class TestOracleJDBC extends  BaseTest {
    @Autowired
    @Qualifier(value="oracleDBDao")
    private UserLogDao oracleDBDao;
    @Test
    public void test(){
        String query="select * from bi_user_behavior_log t where t.id = ? or t.id = ?";
        List parms=new ArrayList();
        parms.add("NWE4PURQLYK3X80OMAV5W4WG40Z49B2P");
        parms.add("0O9H8JTFB7KCFN1YYE0IL21SXE9Z6UGM");
        List<BehaviorLog>  result=oracleDBDao.queryList( query, BehaviorLog.class, parms);
        for(BehaviorLog behaviorLog:result){
            System.out.println(JSON.toJSONString(behaviorLog));
        }
    }
}
