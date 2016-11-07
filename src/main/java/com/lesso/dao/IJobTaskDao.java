package com.lesso.dao;

import com.lesso.base.MsgBo;
import com.lesso.entity.ScheduleJob;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface IJobTaskDao extends IBaseOracleDao{
    public void save(List<ScheduleJob> scheduleJobs ) throws Exception;
    public void save(ScheduleJob scheduleJob ) throws Exception;
    public void updateById(ScheduleJob scheduleJob ) throws Exception;
    public void updateStatusOfTrigger(ScheduleJob scheduleJob) throws  Exception;
    public void updateStatusOfJob(ScheduleJob scheduleJob) throws  Exception;

    public void saveJobDeatil(ScheduleJob scheduleJob ) throws Exception;
    public void updateJobDeatilById(ScheduleJob scheduleJob ) throws Exception;
    public void saveLogs(ScheduleJob scheduleJob ) throws Exception;
    /***
     * 更新客户日志操作时间
     * */
    public MsgBo updateOperateLog(Date operateLogDate) throws Exception;
}
