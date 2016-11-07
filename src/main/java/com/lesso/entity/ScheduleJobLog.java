package com.lesso.entity;

import com.lesso.base.MyColumn;

import java.util.Date;

/**
 * Created by 0003 on 2016/6/7.
 */
public class ScheduleJobLog {
    @MyColumn(name="TASK_ID")
    private Long taskId;

    @MyColumn(name="JOB_NAME")
    private String jobName;

    @MyColumn(name="JOB_CNAME")
    private String jobCName;


    @MyColumn(name="JOB_ID")
    private Long jobId;

    @MyColumn(name="STATUS")
    private String status;

    @MyColumn(name="CREATE_TIME")
    private Date createTime;

    @MyColumn(name="PROCESSRESULT")
    private String  processResult;

    private String  statusDesc;


    private Long  allCount;
    private Long successCount;
    private Long falseCount;

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getFalseCount() {
        return falseCount;
    }

    public void setFalseCount(Long falseCount) {
        this.falseCount = falseCount;
    }

    public Long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobCName() {
        return jobCName;
    }

    public void setJobCName(String jobCName) {
        this.jobCName = jobCName;
    }
}
