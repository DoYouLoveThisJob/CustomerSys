package com.lesso.entity;

import com.lesso.base.MyColumn;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ScheduleJob {
    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_STOP = "0";
    public static final String STATUS_PAUSE = "2";
    public static final String STATUS_DB= "3";
    public static final String CONCURRENT_IS = "1";
    public static final String IS_INTERRUPT = "1";
    public static final String IS_SIMPLE = "1";
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
     * 触发器名称
     */
    @MyColumn(name="TRIGGER_NAME")
    private String triggerName;
    /**
     * 触发器组别
     */
    @MyColumn(name="TRIGGER_GROUP")
    private String triggerGroup;
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
     * cron表达式
     */
    @MyColumn(name="CRON_EXPRESSION")
    private String cronExpression;
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

    private String cronExpressionDesc;
    /**
     * 触发器类型
     */
    @MyColumn(name="TRIGGER_TYPE")
    private String triggerType;
    /**
     * 简单调度时间值
     */
    @MyColumn(name="SC_REPEATINTERVAL")
    private Long repeatInterval;
    /**
     * 简单调度时间单位
     */
    @MyColumn(name="SC_UNIT")
    private String unit;
    /**
     * 是否使用配置文件配置
     */
    @MyColumn(name="IS_CONFIGED_T")
    private String isConfigedOfTrigger;

    @MyColumn(name="IS_CONFIGED_J")
    private String isConfigedOfJob;

    @MyColumn(name="JOB_CNAME")
    private String jobCname;


    @MyColumn(name="TASK_ID")
    private Long taskId;

    @MyColumn(name="STATUS")
    private String status;

    private String processStatus;

    private String processResult;

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getJobCname() {
        return jobCname;
    }

    public void setJobCname(String jobCname) {
        this.jobCname = jobCname;
    }

    public String getIsConfigedOfTrigger() {
        return isConfigedOfTrigger;
    }
    public void setIsConfigedOfTrigger(String isConfigedOfTrigger) {
        this.isConfigedOfTrigger = isConfigedOfTrigger;
    }

    public String getIsConfigedOfJob() {
        return isConfigedOfJob;
    }

    public void setIsConfigedOfJob(String isConfigedOfJob) {
        this.isConfigedOfJob = isConfigedOfJob;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getJobStatusDesc() {
        return jobStatusDesc;
    }

    public void setJobStatusDesc(String jobStatusDesc) {
        this.jobStatusDesc = jobStatusDesc;
    }

    public String getCronExpressionDesc() {
        return cronExpressionDesc;
    }

    public void setCronExpressionDesc(String cronExpressionDesc) {
        this.cronExpressionDesc = cronExpressionDesc;
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
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

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getIsInterrupt() {
        return isInterrupt;
    }

    public void setIsInterrupt(String isInterrupt) {
        this.isInterrupt = isInterrupt;
    }

}
