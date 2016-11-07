package com.lesso.util.quartz;

import com.alibaba.fastjson.JSON;
import com.lesso.entity.ScheduleJob;
import com.lesso.service.IJobTaskService;
import com.lesso.util.SpringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;


/**
 * 
 * @Description: 计划任务执行处 无状态
 * @author chenjianlin
 * @date 2014年4月24日 下午5:05:47
 */
public class QuartzJobFactory implements Job {
	public final Logger log = Logger.getLogger(this.getClass());
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Thread:"+Thread.currentThread().getId()+"----------------------ConcurrentExecution-------------------------------------");
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		try {
				TaskUtils.invokMethod(scheduleJob);
		} catch (Exception e) {
			scheduleJob.setProcessStatus("99");
			scheduleJob.setProcessResult(e.getClass().getName());
			throw new JobExecutionException("this program  have exception!");
		}finally{
			IJobTaskService jobTaskService=SpringUtils.getBean("jobTaskService");
			try {
				jobTaskService.saveLogs(scheduleJob);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}
}