package com.lesso.entity;

import com.lesso.base.MyColumn;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/23.
 */
public class JobDeatil {
    /**
     * 是否中断
     */
    @MyColumn(name="IS_INTERRUPT")
    private String isInterrupt;
    @MyColumn(name="JOB_ID")
    private Long jobId;

    @MyColumn(name="CREATE_TIME")
    private Date createTime;
    @MyColumn(name="UPDATE_TIME")
    private Date updateTime;
    /**
     * 任务名称
     */
    @MyColumn(name="JOB_NAME")
    private String jobName;
    /**
     * 任务分组
     */
    @MyColumn(name="JOB_GROUP")
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    @MyColumn(name="JOB_STATUS")
    private String jobStatus;
    /**
     * 描述
     */
    @MyColumn(name="DESCRIPTION")
    private String description;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    @MyColumn(name="BEAN_CLASS")
    private String beanClass;
    /**
     * 任务是否有状态
     */
    @MyColumn(name="IS_CONCURRENT")
    private String isConcurrent;
    /**
     * spring bean
     */
    @MyColumn(name="SPRING_ID")
    private String springId;
    /**
     * 任务调用的方法名
     */
    @MyColumn(name="METHOD_NAME")
    private String methodName;

    private String jobStatusDesc;
    @MyColumn(name="JOB_CNAME")
    private String jobCname;

    public String getJobCname() {
        return jobCname;
    }

    public void setJobCname(String jobCname) {
        this.jobCname = jobCname;
    }

    public String getJobStatusDesc() {
        return jobStatusDesc;
    }

    public void setJobStatusDesc(String jobStatusDesc) {
        this.jobStatusDesc = jobStatusDesc;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getIsInterrupt() {
        return isInterrupt;
    }

    public void setIsInterrupt(String isInterrupt) {
        this.isInterrupt = isInterrupt;
    }

}
