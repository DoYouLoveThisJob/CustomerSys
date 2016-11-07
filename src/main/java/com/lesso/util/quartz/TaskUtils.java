package com.lesso.util.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lesso.base.MsgBo;
import com.lesso.dao.IJobTaskDao;
import com.lesso.entity.ScheduleJob;
import com.lesso.service.IJobTaskService;
import com.lesso.util.SpringUtils;
import com.lesso.util.StringUtils;
import org.apache.log4j.Logger;


public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) throws Exception {

		Object object = null;
		Class clazz = null;
		System.out.println("Thread:"+Thread.currentThread().getId()+"作业名称 = [" + scheduleJob.getJobName() + "]----------作业开始");
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringUtils.getBean(scheduleJob.getSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			clazz = Class.forName(scheduleJob.getBeanClass());
			object = clazz.newInstance();
		}
		if (object == null) {
			throw new Exception("作业名称 = [" + scheduleJob.getJobName() + "]---------------作业未启动成功，请检查是否配置正确类别！！！");
		}
		clazz = object.getClass();
		Method method = null;
		method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
		if (method != null) {
			Object result=method.invoke(object);
			if(result instanceof MsgBo){
				MsgBo msgBo=(MsgBo)result;
				if(msgBo.getIsSucc()){
					scheduleJob.setProcessStatus("11");
					scheduleJob.setProcessResult(msgBo.getMsg());
				}else{
					scheduleJob.setProcessStatus("99");
				}
			}else{
				scheduleJob.setProcessStatus("11");
				scheduleJob.setProcessResult("无返回结果");
			}
		}else{
			throw new Exception("作业名称 = [" + scheduleJob.getJobName() + "]---------------作业未启动成功，请检查是否配置正确方法！！！");
		}

		System.out.println("Thread:"+Thread.currentThread().getId()+"任务名称 = [" + scheduleJob.getJobName() + "]----------作业结束");
	}
}
