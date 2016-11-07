package com.lesso.entity;

import com.lesso.base.MyColumn;
import com.lesso.base.MyTable;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/11.
 */
@MyTable(name="OPERATE_LOG")
public class OperateLog {
    @MyColumn(name="OPERATE_ID",type=String.class)
    private String id;
    @MyColumn(name="OPERATE_TIME",type=Date.class)
    private Date operateTime;//操作时间
    @MyColumn(name="OPERATE_NAME",type=String.class)
    private String operateName;//操作名
    @MyColumn(name="DEMO",type=String.class)
    private String demo;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
