package com.lesso.service;

import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.entity.JobDeatil;
import com.lesso.entity.ScheduleJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface IJobTaskService {
    //添加任務
    public void addOrUpdateTrigger(ScheduleJob scheduleJob) throws Exception;
    public void addTriggerWithoutSet(ScheduleJob job) throws Exception;

    //对于作业的操作
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;
    public void interruptRunJob(ScheduleJob scheduleJob)throws Exception;
    //对于触发器的操作
    public void pauseTrigger(ScheduleJob scheduleJob) throws SchedulerException;
    public void resumeTrigger(ScheduleJob scheduleJob) throws SchedulerException;
    public void runTrigger(ScheduleJob scheduleJob) throws SchedulerException;
    public void deleteTrigger(ScheduleJob scheduleJob) throws SchedulerException;

    //立即执行
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException;
    public void runJobNow(Long taskId) throws Exception;
    public List<ScheduleJob> getRunningJob() throws SchedulerException;
    public List<ScheduleJob> getAllJob() throws SchedulerException;
    public ScheduleJob getTaskById(Long jobId);

    //提供给controller调用接口
    //更改觸發器
    public void changeStatusOfTrigger(Long jobId, String cmd) throws Exception;
    //更改調度作業
    public void changeStatusOfJob(Long jobId, String cmd) throws Exception;
    public MsgBo addTaskToDB(ScheduleJob scheduleJob) throws Exception;
    public List<ScheduleJob> getAllTask();
    public DataTablesBo getAllTaskByPage(String seachText, String startTime, String endTime, Integer start, Integer length , Integer draw);
    public MsgBo duplicateCheckingOfTrigger(String jobId,String triggerName);
    public MsgBo duplicateCheckingOfJob(Long jobId,String jobName);

    public MsgBo addOrUpdateJobDeatilToDB(ScheduleJob scheduleJob) throws Exception;
    public MsgBo addOrUpdateTaskToDB(ScheduleJob scheduleJob) throws Exception;
    public ScheduleJob getJobDeatilById(Long jobId)throws Exception;

    public List<ScheduleJob> getAllJobDeatil()throws Exception;
    public DataTablesBo getAllJobDetailByPage(String seachText, String startTime,String endTime,Integer start, Integer length , Integer draw);

    public void changeJobDeatil( Long jobId, String cmd)throws Exception;

    public void saveLogs(ScheduleJob scheduleJob )throws Exception;

    public MsgBo getSumInfoOfTask(Long taskId,String seachText, String startTime,String endTime)throws Exception;
    public DataTablesBo getAlScheduleJobLogsByPage(Long taskId,String seachText, String startTime,String endTime,Integer start, Integer length , Integer draw)throws Exception;


}
