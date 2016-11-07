package com.lesso.dao.impl;

import com.lesso.base.MsgBo;
import com.lesso.dao.IJobTaskDao;
import com.lesso.entity.ScheduleJob;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
@Repository(value = "jobTaskDao")
public class JobTaskDao extends CusSysBaseOracleDao implements IJobTaskDao {
    /***
     * 批量保存调度任务
     * */
    public void save(List<ScheduleJob> scheduleJobs ) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "insert into task_schedule_job (CREATE_TIME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, JOB_STATUS, CRON_EXPRESSION, DESCRIPTION, BEAN_CLASS," +
                " IS_CONCURRENT, SPRING_ID, METHOD_NAME, TRIGGER_NAME,TRIGGER_TYPE,SC_REPEATINTERVAL,SC_UNIT )" +
                "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final List<ScheduleJob> saveList=scheduleJobs;
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setDate(1, new java.sql.Date(saveList.get(i).getCreateTime().getTime()));
                ps.setString(2,  saveList.get(i).getTriggerGroup());
                ps.setString(3,  saveList.get(i).getJobName());
                ps.setString(4,  saveList.get(i).getJobGroup());
                ps.setString(5,  saveList.get(i).getJobStatus());
                ps.setString(6,  saveList.get(i).getCronExpression());
                ps.setString(7,  saveList.get(i).getDescription());
                ps.setString(8,  saveList.get(i).getBeanClass());
                ps.setString(9,  saveList.get(i).getIsConcurrent());
                ps.setString(10, saveList.get(i).getSpringId());
                ps.setString(11, saveList.get(i).getMethodName());
                ps.setString(12, saveList.get(i).getTriggerName());
                ps.setString(13, saveList.get(i).getTriggerType());
                ps.setLong(14, saveList.get(i).getRepeatInterval());
                ps.setString(15, saveList.get(i).getUnit());

            }
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return saveList.size();
            }
        };
        int[] result = getJdbcTemplate().batchUpdate(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJobs);
        //return msgBo;
    }
    public void save(ScheduleJob scheduleJob ) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "insert into task_schedule_job (CREATE_TIME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, JOB_STATUS, CRON_EXPRESSION, DESCRIPTION, BEAN_CLASS," +
                " IS_CONCURRENT, SPRING_ID, METHOD_NAME, TRIGGER_NAME,TRIGGER_TYPE,SC_REPEATINTERVAL,SC_UNIT,JOB_ID,JOB_CNAME )" +
                "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final ScheduleJob scheduleJobF=scheduleJob;
       PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setDate(1, new java.sql.Date(scheduleJobF.getCreateTime().getTime()));
                ps.setString(2, scheduleJobF.getTriggerGroup());
                ps.setString(3, scheduleJobF.getJobName());
                ps.setString(4, scheduleJobF.getJobGroup());
                ps.setString(5, scheduleJobF.getJobStatus());
                ps.setString(6, scheduleJobF.getCronExpression());
                ps.setString(7, scheduleJobF.getDescription());
                ps.setString(8, scheduleJobF.getBeanClass());
                ps.setString(9, scheduleJobF.getIsConcurrent());
                ps.setString(10, scheduleJobF.getSpringId());
                ps.setString(11, scheduleJobF.getMethodName());
                ps.setString(12, scheduleJobF.getTriggerName());
                ps.setString(13, scheduleJobF.getTriggerType());
                ps.setLong(14, scheduleJobF.getRepeatInterval());
                ps.setString(15, scheduleJobF.getUnit());
                ps.setLong(16, scheduleJobF.getJobId());
                ps.setString(17, scheduleJobF.getJobCname());
            }
        };
        int result = getJdbcTemplate().update(sql, setter);

        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJob);
        //return msgBo;
    }


    public void saveLogs(ScheduleJob scheduleJob ) throws Exception {
        String sql = "insert into TASK_SCHEDULE_JOB_LOG (task_id, job_id, STATUS,PROCESSRESULT,create_time)" +
                "values (?,?,?,?,sysdate)";
        final ScheduleJob scheduleJobF=scheduleJob;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setLong(1, scheduleJobF.getTaskId());
                ps.setLong(2, scheduleJobF.getJobId());
                ps.setString(3, scheduleJobF.getProcessStatus());
                ps.setString(4, scheduleJobF.getProcessResult());
            }
        };
        int result = getJdbcTemplate().update(sql, setter);
    }

    public void updateById(ScheduleJob scheduleJob ) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "update task_schedule_job set update_time=nvl(?,update_time), TRIGGER_GROUP=nvl(?,TRIGGER_GROUP), JOB_NAME=nvl(?,JOB_NAME), JOB_GROUP=nvl(?,JOB_GROUP), JOB_STATUS=nvl(?,JOB_STATUS), CRON_EXPRESSION=nvl(?,CRON_EXPRESSION), DESCRIPTION=nvl(?,DESCRIPTION), BEAN_CLASS=nvl(?,BEAN_CLASS)," +
                " IS_CONCURRENT=nvl(?,IS_CONCURRENT), SPRING_ID=nvl(?,SPRING_ID), METHOD_NAME=nvl(?,METHOD_NAME), TRIGGER_NAME=nvl(?,TRIGGER_NAME),TRIGGER_TYPE=nvl(?,TRIGGER_TYPE),SC_REPEATINTERVAL=nvl(?,SC_REPEATINTERVAL),SC_UNIT=nvl(?,SC_UNIT)" +
                "where job_id=?";
        final ScheduleJob scheduleJobF=scheduleJob;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setDate(1, new java.sql.Date(scheduleJobF.getUpdateTime().getTime()));
                ps.setString(2, scheduleJobF.getTriggerGroup());
                ps.setString(3, scheduleJobF.getJobName());
                ps.setString(4, scheduleJobF.getJobGroup());
                ps.setString(5, scheduleJobF.getJobStatus());
                ps.setString(6, scheduleJobF.getCronExpression());
                ps.setString(7, scheduleJobF.getDescription());
                ps.setString(8, scheduleJobF.getBeanClass());
                ps.setString(9, scheduleJobF.getIsConcurrent());
                ps.setString(10, scheduleJobF.getSpringId());
                ps.setString(11, scheduleJobF.getMethodName());
                ps.setString(12, scheduleJobF.getTriggerName());
                ps.setString(13, scheduleJobF.getTriggerType());
                ps.setLong(14, scheduleJobF.getRepeatInterval());
                ps.setString(15, scheduleJobF.getUnit());
                ps.setLong(16,scheduleJobF.getJobId());

            }
        };
        int result = getJdbcTemplate().update(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJob);
    }



    /***
     * 批量保存调度任务
     * */
    public void update(List<ScheduleJob> scheduleJobs ) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "update task_schedule_job set update_time=nvl(?,update_time), TRIGGER_GROUP=nvl(?,TRIGGER_GROUP), JOB_NAME=nvl(?,JOB_NAME), JOB_GROUP=nvl(?,JOB_GROUP), JOB_STATUS=nvl(?,JOB_STATUS), CRON_EXPRESSION=nvl(?,CRON_EXPRESSION), DESCRIPTION=nvl(?,DESCRIPTION), BEAN_CLASS=nvl(?,BEAN_CLASS)," +
                " IS_CONCURRENT=nvl(?,IS_CONCURRENT), SPRING_ID=nvl(?,SPRING_ID), METHOD_NAME=nvl(?,METHOD_NAME), TRIGGER_NAME=nvl(?,TRIGGER_NAME),TRIGGER_TYPE=nvl(?,TRIGGER_TYPE),SC_REPEATINTERVAL=nvl(?,SC_REPEATINTERVAL),SC_UNIT=nvl(?,SC_UNIT)" +
                "where job_id=?";
        final List<ScheduleJob> saveList=scheduleJobs;
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setDate(1, new java.sql.Date(saveList.get(i).getUpdateTime().getTime()));
                ps.setString(2, saveList.get(i).getTriggerGroup());
                ps.setString(3, saveList.get(i).getJobName());
                ps.setString(4, saveList.get(i).getJobGroup());
                ps.setString(5, saveList.get(i).getJobStatus());
                ps.setString(6, saveList.get(i).getCronExpression());
                ps.setString(7, saveList.get(i).getDescription());
                ps.setString(8, saveList.get(i).getBeanClass());
                ps.setString(9, saveList.get(i).getIsConcurrent());
                ps.setString(10, saveList.get(i).getSpringId());
                ps.setString(11, saveList.get(i).getMethodName());
                ps.setString(12, saveList.get(i).getTriggerName());
                ps.setString(13, saveList.get(i).getTriggerType());
                ps.setLong(14, saveList.get(i).getRepeatInterval());
                ps.setString(15, saveList.get(i).getUnit());
                ps.setLong(16,saveList.get(i).getJobId());
            }
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return saveList.size();
            }
        };
        int[] result = getJdbcTemplate().batchUpdate(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJobs);
    }


    public void updateStatusOfTrigger(ScheduleJob scheduleJob) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "update task_schedule_job set JOB_STATUS=nvl(?,JOB_STATUS)" +
                "where task_id=?";
        final ScheduleJob scheduleJobF=scheduleJob;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, scheduleJobF.getJobStatus());
                ps.setLong(2, scheduleJobF.getTaskId());

            }
        };
        int result = getJdbcTemplate().update(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJob);

    }

    public void updateStatusOfJob(ScheduleJob scheduleJob) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "update task_schedule_job set JOB_STATUS=nvl(?,JOB_STATUS)" +
                "where job_name=?";
        final ScheduleJob scheduleJobF=scheduleJob;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, scheduleJobF.getJobStatus());
                ps.setString(2, scheduleJobF.getJobName());

            }
        };
        int result = getJdbcTemplate().update(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJob);
    }



    public void saveJobDeatil(ScheduleJob scheduleJob ) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "insert into TASK_JOBDEATIL (JOB_NAME, JOB_CNAME,JOB_GROUP,STATUS,DESCRIPTION, BEAN_CLASS," +
                " IS_CONCURRENT, SPRING_ID, METHOD_NAME,CREATE_TIME )" +
                "values ( ?,?,?,?,?,?,?,?,?,?)";
        final ScheduleJob scheduleJobF=scheduleJob;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                ps.setString(1, scheduleJobF.getJobName());
                ps.setString(2, scheduleJobF.getJobCname());
                ps.setString(3, scheduleJobF.getJobGroup());
                ps.setString(4, scheduleJobF.getJobStatus());
                ps.setString(5, scheduleJobF.getDescription());
                ps.setString(6, scheduleJobF.getBeanClass());
                ps.setString(7, scheduleJobF.getIsConcurrent());
                ps.setString(8, scheduleJobF.getSpringId());
                ps.setString(9, scheduleJobF.getMethodName());
                ps.setDate(10, new java.sql.Date(scheduleJobF.getCreateTime().getTime()));
            }
        };
        int result = getJdbcTemplate().update(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJob);
    }


    public void updateJobDeatilById(ScheduleJob scheduleJob ) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "update TASK_JOBDEATIL set job_cname=nvl(?,job_cname), job_name=nvl(?,job_name), job_group=nvl(?,job_group), bean_class=nvl(?,bean_class), is_concurrent=nvl(?,is_concurrent), spring_id=nvl(?,spring_id), method_name=nvl(?,method_name), is_interrupt=nvl(?,is_interrupt)," +
                " status=nvl(?,status),update_time =sysdate where job_id=?";
        final ScheduleJob scheduleJobF=scheduleJob;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, scheduleJobF.getJobCname());
                ps.setString(2, scheduleJobF.getJobName());
                ps.setString(3, scheduleJobF.getJobGroup());
                ps.setString(4, scheduleJobF.getBeanClass());
                ps.setString(5, scheduleJobF.getIsConcurrent());
                ps.setString(6, scheduleJobF.getSpringId());
                ps.setString(7, scheduleJobF.getMethodName());
                ps.setString(8, scheduleJobF.getIsInterrupt());
                ps.setString(9, scheduleJobF.getStatus());
                ps.setLong(10,scheduleJobF.getJobId());
            }
        };
        int result = getJdbcTemplate().update(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(scheduleJob);
    }

    /***
     * 更新客户日志操作时间
     * */
    public MsgBo updateOperateLog(Date operateLogDate) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "update OPERATE_LOG set operate_time= ? where operate_name= 'CustomSYS' ";
        final Date operateLogDateF=operateLogDate;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setTimestamp(1, new java.sql.Timestamp(operateLogDateF.getTime()));
            }
        };
        int result = getJdbcTemplate().update(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(operateLogDate);
        return msgBo;
    }





}
