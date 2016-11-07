package com.lesso.util.quartz;

import com.alibaba.fastjson.JSON;
import com.lesso.entity.ScheduleJob;
import com.lesso.service.IJobTaskService;
import com.lesso.util.SpringUtils;
import org.apache.log4j.Logger;
import org.quartz.*;

/**
 * 
 * @Description: 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @author chenjianlin
 * @date 2014年4月24日 下午5:05:47
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	public final Logger log = Logger.getLogger(this.getClass());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		System.out.println("Thread:"+Thread.currentThread().getId()+"----------------------DisallowConcurrentExecution-------------------------------------");
		try {
			TaskUtils.invokMethod(scheduleJob);
		} catch (Exception e) {
			scheduleJob.setProcessStatus("99");
			scheduleJob.setProcessResult(e.getMessage());
			throw new JobExecutionException("this program have exception!");
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