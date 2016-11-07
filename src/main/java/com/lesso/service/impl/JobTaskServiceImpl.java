package com.lesso.service.impl;

import java.lang.reflect.Method;
import java.util.*;
import javax.annotation.PostConstruct;
import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.dao.IJobTaskDao;
import com.lesso.entity.BehaviorLog;
import com.lesso.entity.JobDeatil;
import com.lesso.entity.ScheduleJob;
import com.lesso.entity.ScheduleJobLog;
import com.lesso.service.IJobTaskService;
import com.lesso.util.CodeToChineseUtil;
import com.lesso.util.SpringUtils;
import com.lesso.util.StringUtils;
import com.lesso.util.SysConfig;
import com.lesso.util.quartz.QuartzInterruptableJob;
import com.lesso.util.quartz.QuartzJobFactory;
import com.lesso.util.quartz.QuartzJobFactoryDisallowConcurrentExecution;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;


/**
 * 
 * @Description: 计划任务管理
 * @author chenjianlin
 * @date 2014年4月25日 下午2:43:54
 */
@Service(value="jobTaskService")
public class JobTaskServiceImpl implements IJobTaskService{
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	@Qualifier(value="jobTaskDao")
	private IJobTaskDao jobTaskDao;


	/**
	 * 从数据库中取全部工作细节
	 * @return
	 */
	public List<ScheduleJob> getAllJobDeatil()throws Exception {
		return jobTaskDao.queryList("select * from TASK_JOBDEATIL",ScheduleJob.class);
	}


	/**
	 * 从数据库中取全部调度任务
	 * @return
	 */
	public List<ScheduleJob> getAllTask() {
		return jobTaskDao.queryList("select * from task_schedule_job",ScheduleJob.class);
	}
	/**
	 * 通过任务编号获取调度任务信息
	 */
	public ScheduleJob getTaskById(Long taskId) {
		Map map=new HashMap();
		map.put("taskId",taskId);
		return jobTaskDao.queryList("select * from task_schedule_job where task_id = :taskId",ScheduleJob.class,map).get(0);
	}

	/**
	 * 从数据库中查询工作细节 按分页模式查询
	 * @return
	 */
	public DataTablesBo getAllJobDetailByPage(String seachText, String startTime,String endTime,Integer start, Integer length , Integer draw) {
		DataTablesBo dataTablesBo=new DataTablesBo();
		if(start==null || length==null){
			dataTablesBo.setData(new ArrayList<ScheduleJob>());
			dataTablesBo.setDraw(draw);
			dataTablesBo.setRecordsFiltered(0);
			dataTablesBo.setRecordsTotal(0);
			return dataTablesBo;
		}
		String countSqlhead =" select count(1) ";
		String querySqlHead =" select * ";
		String queryBody=" from TASK_JOBDEATIL t where ";
		String orderBy=" order by t.job_id desc ";
		List parms=new ArrayList();
		Map map=new LinkedHashMap();
		if(seachText == null ||seachText.trim().equals("")){
			queryBody+=" 1=1 ";
		}else{
			queryBody+=" (t.job_name like :searchText or t.job_cname like :searchText) ";
			map.put("searchText","%"+seachText+"%");
		}

		if(startTime != null && !startTime.trim().equals("")){
			queryBody+=" and t.create_time >= to_date(:startTime,'yyyy-mm-dd hh24:mi:ss') ";
			map.put("startTime",startTime+" 00:00:00");
		}

		if(endTime != null && !endTime.trim().equals("")){
			queryBody+=" and t.create_time <= to_date(:endTime,'yyyy-mm-dd hh24:mi:ss') ";
			map.put("endTime",endTime+" 23:59:59");
		}
		Integer resultCount=this.jobTaskDao.queryTotal(countSqlhead+queryBody,map);
		if(resultCount==null || resultCount<=0){
			dataTablesBo.setData(new ArrayList<BehaviorLog>());
			dataTablesBo.setDraw(draw);
			dataTablesBo.setRecordsFiltered(0);
			dataTablesBo.setRecordsTotal(0);
		}else{
			List<ScheduleJob> resultMap=this.jobTaskDao.queryPageList(querySqlHead+queryBody+orderBy,ScheduleJob.class,map,start, length ,draw);
			//编码需要转换成中文
			if(resultMap!=null && resultMap.size()>0){
				for(int i=0;i<resultMap.size();i++){
					if(ScheduleJob.IS_SIMPLE.equals(resultMap.get(i).getTriggerType())){
						resultMap.get(i).setCronExpressionDesc(CodeToChineseUtil.getCronExpressionDesc(resultMap.get(i).getRepeatInterval(),resultMap.get(i).getUnit()));
					}else{
						resultMap.get(i).setCronExpressionDesc(CodeToChineseUtil.getCronExpressionDesc(resultMap.get(i).getCronExpression()));
					}
					resultMap.get(i).setJobStatusDesc(SysConfig.getSysName("jobStatus"+resultMap.get(i).getJobStatus()));
				}
				dataTablesBo.setData(resultMap);
				dataTablesBo.setDraw(draw);
				dataTablesBo.setRecordsFiltered(resultCount);
				dataTablesBo.setRecordsTotal(resultCount);
			}else{
				dataTablesBo.setData(new ArrayList<BehaviorLog>());
				dataTablesBo.setDraw(draw);
				dataTablesBo.setRecordsFiltered(0);
				dataTablesBo.setRecordsTotal(0);
			}
		}
		return dataTablesBo;
	}



	/**
	 * 从数据库中查询调度任务 按分页模式查询
	 * @return
	 */
	public DataTablesBo getAllTaskByPage(String seachText, String startTime,String endTime,Integer start, Integer length , Integer draw) {
		DataTablesBo dataTablesBo=new DataTablesBo();
		if(start==null || length==null){
			dataTablesBo.setData(new ArrayList<ScheduleJob>());
			dataTablesBo.setDraw(draw);
			dataTablesBo.setRecordsFiltered(0);
			dataTablesBo.setRecordsTotal(0);
			return dataTablesBo;
		}
		String countSqlhead =" select count(1) ";
		String querySqlHead =" select * ";
		String queryBody=" from task_schedule_job t where ";
		String orderBy=" order by t.job_id desc ";
		List parms=new ArrayList();
		Map map=new LinkedHashMap();
		if(seachText == null ||seachText.trim().equals("")){
			queryBody+=" 1=1 ";
		}else{
			queryBody+=" t.description like :searchText ";
			map.put("searchText","%"+seachText+"%");
		}

		if(startTime != null && !startTime.trim().equals("")){
			queryBody+=" and t.create_time >= to_date(:startTime,'yyyy-mm-dd hh24:mi:ss') ";
			map.put("startTime",startTime+" 00:00:00");
		}

		if(endTime != null && !endTime.trim().equals("")){
			queryBody+=" and t.create_time <= to_date(:endTime,'yyyy-mm-dd hh24:mi:ss') ";
			map.put("endTime",endTime+" 23:59:59");
		}
		Integer resultCount=this.jobTaskDao.queryTotal(countSqlhead+queryBody,map);
		if(resultCount==null || resultCount<=0){
			dataTablesBo.setData(new ArrayList<BehaviorLog>());
			dataTablesBo.setDraw(draw);
			dataTablesBo.setRecordsFiltered(0);
			dataTablesBo.setRecordsTotal(0);
		}else{
			List<ScheduleJob> resultMap=this.jobTaskDao.queryPageList(querySqlHead+queryBody+orderBy,ScheduleJob.class,map,start, length ,draw);
			if(resultMap!=null && resultMap.size()>0){
				for(int i=0;i<resultMap.size();i++){
					if(ScheduleJob.IS_SIMPLE.equals(resultMap.get(i).getTriggerType())){
						resultMap.get(i).setCronExpressionDesc(CodeToChineseUtil.getCronExpressionDesc(resultMap.get(i).getRepeatInterval(),resultMap.get(i).getUnit()));
					}else{
						resultMap.get(i).setCronExpressionDesc(CodeToChineseUtil.getCronExpressionDesc(resultMap.get(i).getCronExpression()));
					}
					resultMap.get(i).setJobStatusDesc(SysConfig.getSysName("jobStatus"+resultMap.get(i).getJobStatus()));
				}
				dataTablesBo.setData(resultMap);
				dataTablesBo.setDraw(draw);
				dataTablesBo.setRecordsFiltered(resultCount);
				dataTablesBo.setRecordsTotal(resultCount);
			}else{
				dataTablesBo.setData(new ArrayList<BehaviorLog>());
				dataTablesBo.setDraw(draw);
				dataTablesBo.setRecordsFiltered(0);
				dataTablesBo.setRecordsTotal(0);
			}
		}
		return dataTablesBo;
	}




	/**
	 * 从数据库中获取调度任务日志  按分页模式
	 * @return
	 */

	public DataTablesBo getAlScheduleJobLogsByPage(Long taskId,String seachText, String startTime,String endTime,Integer start, Integer length , Integer draw)throws Exception {
		DataTablesBo dataTablesBo=new DataTablesBo();
		if(start==null || length==null){
			dataTablesBo.setData(new ArrayList<ScheduleJobLog>());
			dataTablesBo.setDraw(draw);
			dataTablesBo.setRecordsFiltered(0);
			dataTablesBo.setRecordsTotal(0);
			return dataTablesBo;
		}
		String countSqlhead =" select count(1) ";
		String querySqlHead =" select t.*,tj.job_name,tj.job_cname ";
		String queryBody=" from task_schedule_job_log t left join task_jobdeatil tj on tj.job_id = t.job_id where ";
		String orderBy=" order by t.create_time desc ";
		List parms=new ArrayList();
		Map map=new LinkedHashMap();
		if(seachText == null ||seachText.trim().equals("")){
			queryBody+=" 1=1 ";
		}else{
			queryBody+=" t.processresult like :searchText ";
			map.put("searchText","%"+seachText+"%");
		}
		if(taskId != null){
			queryBody+=" and t.task_id =:taskId ";
			map.put("taskId",taskId);
		}

		if(startTime != null && !startTime.trim().equals("")){
			queryBody+=" and t.create_time >= to_date(:startTime,'yyyy-mm-dd hh24:mi:ss') ";
			map.put("startTime",startTime+" 00:00:00");
		}

		if(endTime != null && !endTime.trim().equals("")){
			queryBody+=" and t.create_time <= to_date(:endTime,'yyyy-mm-dd hh24:mi:ss') ";
			map.put("endTime",endTime+" 23:59:59");
		}
		Integer resultCount=this.jobTaskDao.queryTotal(countSqlhead+queryBody,map);
		if(resultCount==null || resultCount<=0){
			dataTablesBo.setData(new ArrayList<ScheduleJobLog>());
			dataTablesBo.setDraw(draw);
			dataTablesBo.setRecordsFiltered(0);
			dataTablesBo.setRecordsTotal(0);
		}else{
			List<ScheduleJobLog> resultMap=this.jobTaskDao.queryPageList(querySqlHead+queryBody+orderBy,ScheduleJobLog.class,map,start, length ,draw);
			if(resultMap!=null && resultMap.size()>0){
				for(int i=0;i<resultMap.size();i++){
					if(resultMap.get(i).getStatus().equals("99")){
						resultMap.get(i).setStatusDesc("运行失败");
					}else if(resultMap.get(i).getStatus().equals("11")){
						resultMap.get(i).setStatusDesc("运行成功");
					}
				}
				dataTablesBo.setData(resultMap);
				dataTablesBo.setDraw(draw);
				dataTablesBo.setRecordsFiltered(resultCount);
				dataTablesBo.setRecordsTotal(resultCount);
			}else{
				dataTablesBo.setData(new ArrayList<ScheduleJobLog>());
				dataTablesBo.setDraw(draw);
				dataTablesBo.setRecordsFiltered(0);
				dataTablesBo.setRecordsTotal(0);
			}
		}
		return dataTablesBo;
	}

	/**
	 * 统计调度任务的总执行数,执行成功记录数,执行失败记录数
	 * @return
	 */
	public MsgBo getSumInfoOfTask(Long taskId,String seachText, String startTime,String endTime)throws Exception{
		MsgBo msgBo=new MsgBo();
		String querySqlHead =" select tsj.*,nvl(a.allCount,0) as allCount,nvl(a.successCount,0) as successCount,nvl(a.falseCount,0) as falseCount from task_schedule_job tsj " +
				" left join " +
				" (select t.task_id,count(1) as allCount,sum(case when t.status = '99' then 1 else 0 end) as successCount,sum(case when t.status = '11' then 1 else 0 end) as falseCount " +
				" from task_schedule_job_log t " +
				" where (:startTimeAll = 'all' or t.create_time >= to_date(:startTime,'yyyy-mm-dd hh24:mi:ss'))" +
				" and (:endTimeAll = 'all' or  t.create_time <= to_date(:endTime,'yyyy-mm-dd hh24:mi:ss'))"+
				" group by t.task_id) a " +
				" on tsj.task_id = a.task_id "+
				" where  ";
		String queryBody = "";
		Map map=new LinkedHashMap();


		if(seachText == null ||seachText.trim().equals("")){
			queryBody+=" 1=1 ";
		}else{
			queryBody+="and tsj.processresult like :searchText ";
			map.put("searchText","%"+seachText+"%");
		}

		if(taskId != null){
			queryBody+=" and tsj.task_id =:taskId ";
			map.put("taskId",taskId);
		}
		if(startTime != null && !startTime.trim().equals("")){
			map.put("startTimeAll","notall");
			map.put("startTime",startTime+" 00:00:00");
		}else{
			map.put("startTimeAll","all");
			map.put("startTime","");
		}
		if(endTime != null && !endTime.trim().equals("")){
			map.put("endTimeAll","notall");
			map.put("endTime",endTime+" 23:59:59");
		}else{
			map.put("endTimeAll","all");
			map.put("endTime","");
		}
		List<ScheduleJobLog> result=this.jobTaskDao.queryList(querySqlHead+queryBody,ScheduleJobLog.class,map);
		if(result==null || result.size()==0){
			msgBo.setIsSucc(true);
			ScheduleJobLog scheduleJobLog=new ScheduleJobLog();
			scheduleJobLog.setAllCount(0l);
			scheduleJobLog.setFalseCount(0l);
			scheduleJobLog.setSuccessCount(0l);
			msgBo.setObj(scheduleJobLog);
		}else{
			msgBo.setIsSucc(true);
			msgBo.setObj(result.get(0));
		}
		return msgBo;


	}

    //校验触发器名不能重复
	public MsgBo duplicateCheckingOfTrigger(String jobId, String triggerName) {
		MsgBo msgBo=new MsgBo();
		msgBo.setIsSucc(true);
		String sql="select count(*) from task_schedule_job t where t.trigger_name = :triggerName";
		String sql2="select count(*) from task_schedule_job t where t.trigger_name = :triggerName and t.job_id = :jobId";
		Map parm=new HashMap();
		Integer count=null;
		parm.put("triggerName",triggerName);
		if(StringUtils.isNotBlank(triggerName)){
			parm.put("triggerName",triggerName);
			if(StringUtils.isNotBlank(jobId)){
				parm.put("jobId",Long.valueOf(jobId));
				count=this.jobTaskDao.queryTotal(sql2,parm);
				if(count == null || count<=0){
					msgBo.setIsSucc(false);
					msgBo.setMsg("操作有误");
				}
			}else{
				count=this.jobTaskDao.queryTotal(sql,parm);
				if(count !=null && count>0){
					msgBo.setIsSucc(false);
					msgBo.setMsg("该触发器"+triggerName+"已经存在");
				}
			}
		}else{
			msgBo.setIsSucc(false);
			msgBo.setMsg("该触发器不能为空");
		}
		return msgBo;
	}

	//校验自定义作业名不能重复
	public MsgBo duplicateCheckingOfJob(Long jobId, String jobName) {
		MsgBo msgBo=new MsgBo();
		msgBo.setIsSucc(true);
		String sql="select count(*) from TASK_JOBDEATIL t where t.job_name = :jobName";
		String sql2="select count(*) from TASK_JOBDEATIL t where t.job_name = :jobName and t.job_id = :jobId";
		Map parm=new HashMap();
		Integer count=null;
		parm.put("jobName",jobName);
		if(StringUtils.isNotBlank(jobName)){
			parm.put("jobName",jobName);
			if(jobId!=null){
				parm.put("jobId",Long.valueOf(jobId));
				count=this.jobTaskDao.queryTotal(sql2,parm);
				if(count == null || count<=0){
					msgBo.setIsSucc(false);
					msgBo.setMsg("操作有误");
				}
			}else{
				count=this.jobTaskDao.queryTotal(sql,parm);
				if(count !=null && count>0){
					msgBo.setIsSucc(false);
					msgBo.setMsg("自定义作业的作业名不允许重复，该作业名"+jobName+"已经存在");
				}
			}
		}else{
			msgBo.setIsSucc(false);
			msgBo.setMsg("自定义作业的作业名不能为空");
		}
		return msgBo;
	}


	/**
	 * 添加到数据库中 区别于addJob jobName必填
	 */
	public MsgBo addTaskToDB(ScheduleJob scheduleJob) throws Exception {
		MsgBo msgBo = new MsgBo();
		msgBo.setIsSucc(false);
		JobDetail jobDetail=null;
		try{
			//从spring配置文件中获取jobDeatil
			jobDetail=SpringUtils.getBean(scheduleJob.getJobName());
		}catch (Exception e) {

		}
		    //判断触发器名是否为空？
			if(StringUtils.isNotBlank(scheduleJob.getTriggerName())){
				Trigger trigger=null;
				//从spring配置文件中获取触发器
				try{
					trigger = SpringUtils.getBean(scheduleJob.getTriggerName());
				}catch (Exception e){

				}
				//如果触发器在spring中获取得到，则jobDeatil也一定要在spring中获取到并且与trigger匹配。
				if(trigger!=null){
					if(jobDetail==null){
						if(StringUtils.isNotBlank(scheduleJob.getJobName())){
							msgBo.setMsg("在配置文件中,未找到填写作业名称["+scheduleJob.getJobName()+"]的配置！");
							return msgBo;
						}else{
                            scheduleJob.setJobName(trigger.getJobKey().getName());
							scheduleJob.setJobGroup(trigger.getJobKey().getGroup());
							jobDetail=SpringUtils.getBean(trigger.getJobKey().getName());
						}
					}else {
						if (jobDetail.getKey().getName().equals(trigger.getJobKey().getName())) {
							scheduleJob.setTriggerGroup(trigger.getKey().getGroup());
						} else {
							msgBo.setMsg("在配置文件中,触发器[" + scheduleJob.getTriggerName() + "]中作业名称[" + trigger.getJobKey().getName() + "]与填写作业名称[" + scheduleJob.getJobName() + "]不匹配！");
							return msgBo;
						}
					}
					if (trigger instanceof CronTrigger) {
						if(StringUtils.isNotBlank(scheduleJob.getTriggerType())){
							if ("2".equals(scheduleJob.getTriggerType())) {
								if (!StringUtils.isNotBlank(scheduleJob.getCronExpression())) {
									scheduleJob.setCronExpression(((CronTrigger) trigger).getCronExpression());
								}
							} else {
								msgBo.setMsg("在配置文件中,该触发器的类型[" + scheduleJob.getTriggerName() + "]与当前的执行规则不匹配！");
								return msgBo;
							}
						}else{
							scheduleJob.setTriggerType("2");
							scheduleJob.setCronExpression(((CronTrigger) trigger).getCronExpression());
						}
					} else if (trigger instanceof SimpleTrigger) {
						if(StringUtils.isNotBlank(scheduleJob.getTriggerType())) {
							if ("1".equals(scheduleJob.getTriggerType())) {
								if (scheduleJob.getRepeatInterval() == null  || scheduleJob.getRepeatInterval()==-1) {
									scheduleJob.setRepeatInterval(((SimpleTrigger) trigger).getRepeatInterval());
									scheduleJob.setUnit("second");
								}
							} else {
								msgBo.setMsg("在配置文件中,该触发器[" + scheduleJob.getTriggerName() + "]的类型与当前的执行规则不匹配！");
								return msgBo;
							}
						}else{
							scheduleJob.setTriggerType("1");
							scheduleJob.setRepeatInterval(((SimpleTrigger) trigger).getRepeatInterval());
							scheduleJob.setUnit("second");
						}
					}else{
						msgBo.setMsg("在配置文件中,该触发器[" + scheduleJob.getTriggerName() + "]的类型暂不支持！");
						return msgBo;
					}
				}
			}


			//判断该jobName在配置文件中是否配置
			if(jobDetail!=null){
				//jobDetail在spring获取得到获取相应的信息。
				if(jobDetail.isConcurrentExectionDisallowed()){
					scheduleJob.setIsConcurrent("0");
				}else{
					scheduleJob.setIsConcurrent("1");
				}
				MethodInvokingJobDetailFactoryBean methodBean=(MethodInvokingJobDetailFactoryBean)jobDetail.getJobDataMap().get("methodInvoker");
				scheduleJob.setBeanClass(methodBean.getTargetClass().getName());
				scheduleJob.setMethodName(methodBean.getTargetMethod());
				scheduleJob.setJobGroup(jobDetail.getKey().getGroup());
			}else{
				//自定义作业的作业名需要校验，保证其唯一性
				msgBo=duplicateCheckingOfJob(scheduleJob.getJobId(), scheduleJob.getJobName());
				if(msgBo==null || !msgBo.getIsSucc()){
					return msgBo;
				}
				if(!StringUtils.isNotBlank(scheduleJob.getSpringId())&& !StringUtils.isNotBlank(scheduleJob.getBeanClass())){
					msgBo.setMsg("因为该作业名称["+scheduleJob.getJobName()+"]为自定义作业，所以调用beanClass和beanId不允许同时为空。");
					return msgBo;
				}
				if(!StringUtils.isNotBlank(scheduleJob.getMethodName())){
					msgBo.setMsg("因为该作业名称["+scheduleJob.getJobName()+"]为自定义作业，所以调用方法不允许为空。");
					return msgBo;
				}

			}


		if(ScheduleJob.IS_SIMPLE.equals(scheduleJob.getTriggerType())){
			if(scheduleJob.getRepeatInterval()==null || scheduleJob.getRepeatInterval()==-1){
				msgBo.setMsg("间隔时间填写");
				return msgBo;
			}
		}else{
			//判断表表达式是否有误
			try {
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			} catch (Exception e) {
				msgBo.setMsg("cron表达式有误，不能被解析！");
				return msgBo;
			}
		}
		Object obj = null;
		try {
			//判断该beanClass是否在配置文件中配置完毕
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				obj = SpringUtils.getBean(scheduleJob.getSpringId());
				scheduleJob.setBeanClass(obj.getClass().toString());
			} else {
				Class clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			}
		} catch (Exception e) {

		}
		if (obj == null) {
			msgBo.setMsg("未找到目标类！");
			return msgBo;
		} else {
			Class clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(scheduleJob.getMethodName(), null);

			} catch (Exception e) {
				// do nothing.....
			}
			if (method == null) {
				msgBo.setMsg("未找到目标方法！");
				return msgBo;
			}
		}

		if(scheduleJob.getJobId()==null){
			scheduleJob.setCreateTime(new Date());
			jobTaskDao.save(scheduleJob);
		}else{
			scheduleJob.setUpdateTime(new Date());
			jobTaskDao.updateById(scheduleJob);
			if(ScheduleJob.STATUS_STOP.equals(scheduleJob.getJobStatus())){
					deleteTrigger(scheduleJob);
			}
			if(ScheduleJob.STATUS_DB.equals(scheduleJob.getJobStatus())){
					deleteTrigger(scheduleJob);
			}
			if(ScheduleJob.STATUS_PAUSE.equals(scheduleJob.getJobStatus())){
				    pauseTrigger(scheduleJob);
			}
		}
		if(ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())){
			addOrUpdateTrigger(scheduleJob);
		}
		msgBo.setIsSucc(true);
		msgBo.setMsg("保存成功");
		return msgBo;
	}

    public List<JobDeatil> getAllJobDeatils()throws Exception{
		String sql="select * from TASK_JOBDEATIL t where t.status='1'";
		return this.jobTaskDao.queryList(sql,JobDeatil.class);
	}





	/**
	 * 添加到数据库中 区别于addJob jobName必填
	 */
	public MsgBo addOrUpdateTaskToDB(ScheduleJob scheduleJob) throws Exception {
		MsgBo msgBo = new MsgBo();
		msgBo.setIsSucc(false);
		ScheduleJob jobDeatil=null;
		if(scheduleJob.getTaskId()!=null){
			jobDeatil=getTaskById(scheduleJob.getTaskId());
		}else{
			jobDeatil=getJobDeatilById(scheduleJob.getJobId());
			if(jobDeatil==null){
				msgBo.setIsSucc(false);
				msgBo.setMsg("工作细节为空");
				return msgBo;
			}
		}
		if(ScheduleJob.IS_SIMPLE.equals(scheduleJob.getTriggerType())){
			if(scheduleJob.getRepeatInterval()==null || scheduleJob.getRepeatInterval()==-1){
				msgBo.setMsg("间隔时间填写");
				return msgBo;
			}
		}else{
			//判断表表达式是否有误
			try {
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			} catch (Exception e) {
				msgBo.setMsg("cron表达式有误，不能被解析！");
				return msgBo;
			}
		}

		if(!StringUtils.isNotBlank(jobDeatil.getTriggerName())){
			jobDeatil.setTriggerName("trigger"+UUID.randomUUID().toString());
			jobDeatil.setTriggerGroup("DEFAULT");
		}
		jobDeatil.setTriggerType(scheduleJob.getTriggerType());
		jobDeatil.setDescription(scheduleJob.getDescription());
		jobDeatil.setCronExpression(scheduleJob.getCronExpression());
		jobDeatil.setRepeatInterval(scheduleJob.getRepeatInterval());
		jobDeatil.setUnit(scheduleJob.getUnit());
		jobDeatil.setJobStatus(scheduleJob.getJobStatus());
		if(jobDeatil.getTaskId()==null){
			jobDeatil.setCreateTime(new Date());
			jobTaskDao.save(jobDeatil);
		}else{
			jobDeatil.setUpdateTime(new Date());
			jobTaskDao.updateById(jobDeatil);
			if(ScheduleJob.STATUS_STOP.equals(jobDeatil.getJobStatus())){
				deleteTrigger(jobDeatil);
			}
			if(ScheduleJob.STATUS_DB.equals(jobDeatil.getJobStatus())){
				deleteTrigger(jobDeatil);
			}
			if(ScheduleJob.STATUS_PAUSE.equals(jobDeatil.getJobStatus())){
				pauseTrigger(jobDeatil);
			}
		}
		if(ScheduleJob.STATUS_RUNNING.equals(jobDeatil.getJobStatus())){
			addOrUpdateTrigger(jobDeatil);
		}
		msgBo.setIsSucc(true);
		msgBo.setMsg("保存成功");
		return msgBo;
	}

	/**
	 * 添加到数据库中 区别于addJob jobName必填
	 */
	public MsgBo addOrUpdateJobDeatilToDB(ScheduleJob scheduleJob) throws Exception {
		MsgBo msgBo = new MsgBo();

		JobDetail jobDetail=null;
		try{
			//从spring配置文件中获取jobDeatil
			jobDetail=SpringUtils.getBean(scheduleJob.getJobName());
		}catch (Exception e) {

		}

		msgBo=duplicateCheckingOfJob(scheduleJob.getJobId(), scheduleJob.getJobName());

		if(msgBo==null || !msgBo.getIsSucc()){
			return msgBo;
		}
		msgBo.setIsSucc(false);
		//判断该jobName在配置文件中是否配置
		if(jobDetail!=null){
			//jobDetail在spring获取得到获取相应的信息。
			if(jobDetail.isConcurrentExectionDisallowed()){
				scheduleJob.setIsConcurrent("0");
			}else{
				scheduleJob.setIsConcurrent("1");
			}
			MethodInvokingJobDetailFactoryBean methodBean=(MethodInvokingJobDetailFactoryBean)jobDetail.getJobDataMap().get("methodInvoker");
			scheduleJob.setBeanClass(methodBean.getTargetClass().getName());
			scheduleJob.setMethodName(methodBean.getTargetMethod());
			scheduleJob.setJobGroup(jobDetail.getKey().getGroup());
		}else{
			if(!StringUtils.isNotBlank(scheduleJob.getSpringId())&& !StringUtils.isNotBlank(scheduleJob.getBeanClass())){
				msgBo.setMsg("因为该作业名称["+scheduleJob.getJobName()+"]为自定义作业，所以调用beanClass和beanId不允许同时为空。");
				return msgBo;
			}
			if(!StringUtils.isNotBlank(scheduleJob.getMethodName())){
				msgBo.setMsg("因为该作业名称["+scheduleJob.getJobName()+"]为自定义作业，所以调用方法不允许为空。");
				return msgBo;
			}

		}
		Object obj = null;
		try {
			//判断该beanClass是否在配置文件中配置完毕
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				obj = SpringUtils.getBean(scheduleJob.getSpringId());
				scheduleJob.setBeanClass(obj.getClass().toString());
			} else {
				Class clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			}
		} catch (Exception e) {

		}
		if (obj == null) {
			msgBo.setMsg("未找到目标类！");
			return msgBo;
		} else {
			Class clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(scheduleJob.getMethodName(), null);

			} catch (Exception e) {
				// do nothing.....
			}
			if (method == null) {
				msgBo.setMsg("未找到目标方法！");
				return msgBo;
			}
		}

		if(scheduleJob.getJobId()==null){
			scheduleJob.setCreateTime(new Date());
			jobTaskDao.saveJobDeatil(scheduleJob);
		}else{
			scheduleJob.setUpdateTime(new Date());
			this.addTaskToScheduler(scheduleJob);
			jobTaskDao.updateJobDeatilById(scheduleJob);
		}
		msgBo.setIsSucc(true);
		msgBo.setMsg("保存成功");
		return msgBo;
	}

	public ScheduleJob getJobDeatilById(Long jobId)throws Exception{
		Map map=new HashMap();
		map.put("jobId",jobId);
		return jobTaskDao.queryList("select * from TASK_JOBDEATIL where job_id = :jobId",ScheduleJob.class,map).get(0);
	}


	/**
	 * 更改任务状态
	 * @throws SchedulerException
	 */
	public void changeStatusOfTrigger(Long jobId, String cmd) throws Exception {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteTrigger(job);
			job.setJobStatus(ScheduleJob.STATUS_STOP);
		} else if ("start".equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			addTaskToScheduler(job);
		}else if("pause".equals(cmd)){
			job.setJobStatus(ScheduleJob.STATUS_PAUSE);
			pauseTrigger(job);
		}else if("resume".equals(cmd)){
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			resumeTrigger(job);
		}
		jobTaskDao.updateStatusOfTrigger(job);
	}

	public void changeJobDeatil(Long jobId, String cmd) throws Exception {
		ScheduleJob job = getJobDeatilById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setStatus(ScheduleJob.STATUS_STOP);
		} else if ("start".equals(cmd)) {
			job.setStatus(ScheduleJob.STATUS_RUNNING);
			addTaskToScheduler(job);
		}else if("pause".equals(cmd)){
			job.setStatus(ScheduleJob.STATUS_PAUSE);
			pauseJob(job);
		}else if("resume".equals(cmd)){
			job.setStatus(ScheduleJob.STATUS_RUNNING);
			resumeJob(job);
		}
		jobTaskDao.updateJobDeatilById(job);
	}

	public void saveLogs(ScheduleJob scheduleJob) throws Exception {
		this.jobTaskDao.saveLogs(scheduleJob);
	}


	/**
	 * 更改任务状态
	 * @throws SchedulerException
	 */
	public void changeStatusOfJob(Long jobId, String cmd) throws Exception {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setJobStatus(ScheduleJob.STATUS_STOP);
		} else if ("start".equals(cmd)) {
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			addTaskToScheduler(job);
		}else if("pause".equals(cmd)){
			job.setJobStatus(ScheduleJob.STATUS_PAUSE);
			pauseJob(job);
		}else if("resume".equals(cmd)){
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			resumeJob(job);
		}
		jobTaskDao.updateStatusOfJob(job);
	}

	@PostConstruct
	public void init() throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		// 这里获取任务信息数据
		List<ScheduleJob> jobList = this.getAllTask();
		for (ScheduleJob job : jobList) {
			addTaskToScheduler(job);
		}
	}

	/**
	 *立即执行任务
	 * **/
	public void runJobNow(Long taskId) throws Exception {
		ScheduleJob job = getTaskById(taskId);
		runAJobNow(job);
	}
	/**
	 * 添加任务到運行中
	 * @param job
	 * @throws SchedulerException
	 */
	public void addTaskToScheduler(ScheduleJob job) throws Exception {
		if(ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())){
			addOrUpdateTrigger(job);
		}
	}

	/**
	 * 获取所有计划中的任务列表
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}
	/**
	 * 所有正在运行的job
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 更新trigger时间表达式
	 * @throws SchedulerException
	 */
	public void addOrUpdateTrigger(ScheduleJob scheduleJob) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		Trigger trigger = null;
		//在spring配置文件中获取trigger并且获取JobDeatil
		try{
			trigger=SpringUtils.getBean(scheduleJob.getTriggerName());
		}catch (Exception e){

		}
		if(trigger!=null){
			if(ScheduleJob.IS_SIMPLE.equals(scheduleJob.getTriggerType())){
				if(scheduleJob.getRepeatInterval()!=null){
					trigger = ((SimpleTrigger)trigger).getTriggerBuilder().withIdentity(trigger.getKey()).withSchedule(simpleSchedule().withIntervalInMilliseconds(scheduleJob.getRepeatInterval()).repeatForever()).build();
				}
			}else{
				if(StringUtils.isNotBlank(scheduleJob.getCronExpression())){
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
					trigger = ((CronTrigger)trigger).getTriggerBuilder().withIdentity(trigger.getKey()).withSchedule(scheduleBuilder).build();
				}
			}
			//查询该trigger是否存在与调度中？如果不存在，创建一个
			Trigger trigger2 =  scheduler.getTrigger(trigger.getKey());
			if (trigger2 == null) {
				JobDetail jobDetail= SpringUtils.getBean(trigger.getJobKey().getName());
				Set triggerSet=new HashSet();
				triggerSet.add(trigger);
				scheduler.scheduleJob(jobDetail,triggerSet,true);
			}else{
				scheduler.rescheduleJob(trigger.getKey(), trigger);
			}
		}else{
			addTriggerWithoutSet(scheduleJob);
		}
	}
	//获取新的作业调度
   public JobDetail getNewJobDetail(ScheduleJob job){
	   Class clazz = null;
	   JobDetail jobDetail=null;
	   if(!ScheduleJob.IS_INTERRUPT.equals(job.getIsInterrupt())){
		   clazz=ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
	   }else{
		   clazz=QuartzInterruptableJob.class;
	   }
	   jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
	   jobDetail.getJobDataMap().put("scheduleJob", job);
	   return  jobDetail;
   }
	//在作业调度中是否为相同作业调度
	public boolean isSameJobDeatilInScheduler(ScheduleJob job) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobDetail jobDetail=scheduler.getJobDetail(new JobKey(job.getJobName(),job.getJobGroup()));
		//jobDetail在spring获取得到获取相应的信息。
		MethodInvokingJobDetailFactoryBean methodBean=(MethodInvokingJobDetailFactoryBean)jobDetail.getJobDataMap().get("methodInvoker");
		if(methodBean != null){
			if(jobDetail.isConcurrentExectionDisallowed() && job.getIsConcurrent().equals("1")){
				return  false;
			}
			if(!jobDetail.getKey().getGroup().equals(job.getJobGroup())){
				return  false;
			}
			if(!methodBean.getTargetClass().getName().equals(job.getBeanClass())){
				return  false;
			}
			if(!methodBean.getTargetMethod().equals(job.getMethodName())){
				return  false;
			}
		}else {
			ScheduleJob scheduleJob=(ScheduleJob)jobDetail.getJobDataMap().get("scheduleJob");
			if(!job.getIsConcurrent().equals(scheduleJob.getIsConcurrent())){
				return  false;
			}
			if(!jobDetail.getKey().getGroup().equals(job.getJobGroup())){
				return  false;
			}
			if(!scheduleJob.getBeanClass().equals(job.getBeanClass())){
				return  false;
			}
			if(!scheduleJob.getSpringId().equals(job.getSpringId())){
				return  false;
			}
			if(!scheduleJob.getMethodName().equals(job.getMethodName())){
				return  false;
			}
		}
		return  true;
	}
	/**
	 * 添加任务
	 * @param job
	 * @throws SchedulerException
	 */
	public void addTriggerWithoutSet(ScheduleJob job) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		log.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroup());
		Trigger trigger =  scheduler.getTrigger(triggerKey);
		//不存在，创建一个
		JobDetail jobDetail=null;
		if (null == trigger) {
			try{
				jobDetail=SpringUtils.getBean(job.getJobName());
			}catch (Exception e){

			}
			if(jobDetail == null){
				jobDetail=getNewJobDetail(job);
			}
			if(ScheduleJob.IS_SIMPLE.equals(job.getTriggerType())){
				trigger=TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(simpleSchedule().withIntervalInMilliseconds(job.getRepeatInterval()).repeatForever()).build();
			}else{
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			}
			Set triggerSet=new HashSet();
			triggerSet.add(trigger);
			scheduler.scheduleJob(jobDetail,triggerSet,true);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			// 按新的cronExpression表达式重新构建trigger
			if(ScheduleJob.IS_SIMPLE.equals(job.getTriggerType())){
				if(trigger instanceof  SimpleTrigger){
					trigger = ((SimpleTrigger)trigger).getTriggerBuilder().withIdentity(triggerKey).withSchedule(simpleSchedule().withIntervalInMilliseconds(job.getRepeatInterval()).repeatForever()).build();
					if(isSameJobDeatilInScheduler(job)){
						scheduler.rescheduleJob(triggerKey, trigger);
					}else{
						//deleteTrigger(job);
						deleteJob(job);
						jobDetail=getNewJobDetail(job);
						Set triggerSet=new HashSet();
						triggerSet.add(trigger);
						//scheduler.scheduleJob(jobDetail,triggerSet,true);
						scheduler.scheduleJob(jobDetail,trigger);
					}
				}else{
					jobDetail=scheduler.getJobDetail(new JobKey(job.getJobName(),job.getJobGroup()));
					if(isSameJobDeatilInScheduler(job)){
						//deleteTrigger(job);
						deleteJob(job);
						jobDetail=getNewJobDetail(job);
					}else{
						deleteTrigger(job);
					}
					trigger =TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(simpleSchedule().withIntervalInMilliseconds(job.getRepeatInterval()).repeatForever()).build();
					Set triggerSet=new HashSet();
					triggerSet.add(trigger);
					scheduler.scheduleJob(jobDetail,triggerSet,true);
				}

			}else{
				if(trigger instanceof  CronTrigger){
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
					trigger = ((CronTrigger)trigger).getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
					if(isSameJobDeatilInScheduler(job)){
						scheduler.rescheduleJob(triggerKey, trigger);
					}else{
						//deleteTrigger(job);
						deleteJob(job);
						jobDetail=getNewJobDetail(job);
						Set triggerSet=new HashSet();
						triggerSet.add(trigger);
						scheduler.scheduleJob(jobDetail,triggerSet,true);
					}
				}else{
					jobDetail=scheduler.getJobDetail(new JobKey(job.getJobName(),job.getJobGroup()));
					if(isSameJobDeatilInScheduler(job)){
						//deleteTrigger(job);
						deleteJob(job);
						jobDetail=getNewJobDetail(job);
					}else{
						deleteTrigger(job);
					}
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
					trigger =TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
					Set triggerSet=new HashSet();
					triggerSet.add(trigger);
					scheduler.scheduleJob(jobDetail,triggerSet,true);
				}
			}
		}
	}

	/**
	 * 暂停一个trigger;
	 */
	public void pauseTrigger(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.pauseTrigger(new TriggerKey(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroup()));// 停止触发器
	}
	/**
	 * 恢复一个trigger;
	 */
	public void resumeTrigger(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		scheduler.resumeTrigger(new TriggerKey(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroup()));// 停止触发器
	}
	/**
	 * 立即触发一个trigger;
	 */
	public void runTrigger(ScheduleJob scheduleJob) throws SchedulerException {
		if(scheduleJob!=null) {
			this.runAJobNow(scheduleJob);
		}
	}
	/**
	 * 删除一个trigger;
	 */
	public void deleteTrigger(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = new TriggerKey(scheduleJob.getTriggerName(), scheduleJob.getTriggerGroup());
//		scheduler.pauseTrigger(triggerKey);// 停止触发器
//		scheduler.unscheduleJob(triggerKey);// 移除触发器
		Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
		while(!triggerState.name().equals("NONE")&&!triggerState.name().equals("NORMAL")){
			triggerState = scheduler.getTriggerState(triggerKey);
		}
		scheduler.pauseTrigger(triggerKey);// 停止触发器
		scheduler.unscheduleJob(triggerKey);// 移除触发器
	}


	/**
	 * 暂停一个job
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}
	/**
	 * 恢复一个job
	 *
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}
	/**
	 * 删除一个job
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		List<? extends  Trigger> triggers=scheduler.getTriggersOfJob(jobKey);
		for(Trigger trigger : triggers){
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			while(!triggerState.name().equals("NONE")&&!triggerState.name().equals("NORMAL") ){
				triggerState = scheduler.getTriggerState(trigger.getKey());
			}
			scheduler.pauseTrigger(trigger.getKey());// 停止触发器
			scheduler.unscheduleJob(trigger.getKey());// 移除触发器
		}
		boolean flag=scheduler.deleteJob(jobKey);
	}
	/**
	 * 立即执行job
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	/**
	 * 中断正在运行的任务
	 */
	public void interruptRunJob(ScheduleJob scheduleJob) throws Exception {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.interrupt(jobKey);
	}


}
