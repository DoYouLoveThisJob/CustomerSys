package com.lesso.util.quartz;

import com.lesso.entity.ScheduleJob;
import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

/**
 * Created by Administrator on 2016/5/24.
 */
public class QuartzInterruptableJob implements InterruptableJob {
    private static Logger LOG = Logger.getLogger(QuartzInterruptableJob.class);

    private volatile boolean isJobInterrupted = true;

    public QuartzInterruptableJob() {

    }
    public void interrupt() throws UnableToInterruptJobException {
        System.out.println("Job-- INTERRUPTING --");
        isJobInterrupted = false;
    }
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        if(isJobInterrupted){
            try {
                TaskUtils.invokMethod(scheduleJob);
            } catch (Exception e) {
                throw new JobExecutionException("this program  have exception!");
            }
        }

    }
}
