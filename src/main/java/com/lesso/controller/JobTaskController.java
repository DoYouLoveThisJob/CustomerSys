package com.lesso.controller;
import java.util.List;
import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.entity.JobDeatil;
import com.lesso.entity.ScheduleJob;
import com.lesso.service.IJobTaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/task")
public class JobTaskController {
	// 日志记录器
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IJobTaskService jobTaskService;

	@RequestMapping("addTask")
	@ResponseBody
	public MsgBo addTask(ScheduleJob scheduleJob) {
		MsgBo msgBo=new MsgBo();
		try {
			msgBo=jobTaskService.addTaskToDB(scheduleJob);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msgBo.setMsg("程序出现异常");
		}
		return msgBo;
	}

	@RequestMapping("addOrUpdateTask")
	@ResponseBody
	public MsgBo addOrUpdateTask(ScheduleJob scheduleJob) {
		MsgBo msgBo=new MsgBo();
		try {
			msgBo=jobTaskService.addOrUpdateTaskToDB(scheduleJob);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msgBo.setMsg("程序出现异常");
		}
		return msgBo;
	}


	@RequestMapping("addOrUpdateJobToDB")
	@ResponseBody
	public MsgBo addOrUpdateJobDeatilToDB(ScheduleJob scheduleJob) {
		MsgBo msgBo=new MsgBo();
		try {
			msgBo=jobTaskService.addOrUpdateJobDeatilToDB(scheduleJob);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msgBo.setMsg("程序出现异常");
		}
		return msgBo;
	}


	@RequestMapping("listJobOnSchedule")
	@ResponseBody
	public List<ScheduleJob> listJobOnSchedule() {
		List<ScheduleJob> jobs=null;
		try {
			jobs=jobTaskService.getAllJob();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jobs;
	}


	@RequestMapping("changeJobStatus")
	@ResponseBody
	public MsgBo changeJobStatus( Long taskId, String cmd) {
		MsgBo MsgBo = new MsgBo();
		MsgBo.setIsSucc(false);
		try {
			jobTaskService.changeStatusOfTrigger(taskId, cmd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			MsgBo.setMsg("任务状态改变失败！");
			return MsgBo;
		}
		MsgBo.setIsSucc(true);
		return MsgBo;
	}


	@RequestMapping("changeJobDeatil")
	@ResponseBody
	public MsgBo changeJobDeatil( Long jobId, String cmd) {
		MsgBo MsgBo = new MsgBo();
		MsgBo.setIsSucc(false);
		try {
			jobTaskService.changeJobDeatil(jobId, cmd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			MsgBo.setMsg("任务状态改变失败！");
			return MsgBo;
		}
		MsgBo.setIsSucc(true);
		return MsgBo;
	}


	@RequestMapping("interruptRunJob")
	@ResponseBody
	public MsgBo interruptRunJob(Long taskId) {
		MsgBo MsgBo = new MsgBo();
		try {
			ScheduleJob jobs=jobTaskService.getTaskById(taskId);
			jobTaskService.interruptRunJob(jobs);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBo.setMsg("cron更新失败！");
			return MsgBo;
		}
		MsgBo.setIsSucc(true);
		return MsgBo;
	}

	@RequestMapping("getAllDBTask")
	@ResponseBody
	public List<ScheduleJob> getAllDBTask() {
		List<ScheduleJob> jobs=null;
		try {
			jobs=jobTaskService.getAllTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobs;
	}



	@RequestMapping("queryTask")
	@ResponseBody
	public DataTablesBo queryTask(String searchText,String startTime,String endTime, Integer start, Integer length , Integer draw) {
		DataTablesBo result=null;
		try{
			result =this.jobTaskService.getAllTaskByPage( searchText,startTime,endTime,start, length , draw);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}


	@RequestMapping("queryJobDeatil")
	@ResponseBody
	public DataTablesBo queryJobDeatil(String searchText,String startTime,String endTime, Integer start, Integer length , Integer draw) {
		DataTablesBo result=null;
		try{
			result =this.jobTaskService.getAllJobDetailByPage( searchText,startTime,endTime,start, length , draw);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}


	@RequestMapping("getTaskById")
	@ResponseBody
	public ScheduleJob getTaskById(Long taskId) {
		ScheduleJob result=null;
		try{
			result =this.jobTaskService.getTaskById(taskId);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}


	@RequestMapping("getJobDeatilById")
	@ResponseBody
	public ScheduleJob getJobDeatilById(Long jobId) {
		ScheduleJob result=null;
		try{
			result =this.jobTaskService.getJobDeatilById(jobId);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	@RequestMapping("checkTrigger")
	@ResponseBody
	public MsgBo checkTrigger(String taskId,String triggerName) {
		MsgBo result=null;
		try{
			result =this.jobTaskService.duplicateCheckingOfTrigger(taskId,triggerName);
		}catch (Exception ex){
			ex.printStackTrace();
			result=new MsgBo(false,"程序异常","程序异常");
		}
		return result;
	}

	@RequestMapping("runJobNow")
	@ResponseBody
	public MsgBo runJobNow(Long taskId) {
		MsgBo result=new MsgBo();
		result.setIsSucc(true);
		result.setMsg("执行成功");
		result.setObj(taskId);
		try{
			this.jobTaskService.runJobNow(taskId);
		}catch (Exception ex){
			ex.printStackTrace();
			result.setIsSucc(false);
			result.setObj(taskId);
			result.setMsg("执行异常");
		}
		return result;
	}

	@RequestMapping("queryLogs")
	@ResponseBody
	public DataTablesBo queryLogs(Long taskId,String seachText, String startTime,String endTime,Integer start, Integer length , Integer draw) {
		DataTablesBo result=null;
		try{
			result =this.jobTaskService.getAlScheduleJobLogsByPage(taskId,seachText,startTime,endTime,start,length,draw);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	@RequestMapping("querySumInfoOfLogs")
	@ResponseBody
	public MsgBo querySumInfoOfLogs(Long taskId,String seachText, String startTime,String endTime) {
		MsgBo result=null;
		try{
			result =this.jobTaskService.getSumInfoOfTask(taskId,seachText,startTime,endTime);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}



	@RequestMapping("/taskManagePage")
	public String taskManagePage() {
		return  "task/taskManage";
	}
	@RequestMapping("/jobDeatilManagePage")
	public String jobDeatilManagePage() {
		return  "task/jobDeatilManage";
	}

	@RequestMapping("/addTaskPage")
	public String addTaskPage() {
		return  "task/addTask";
	}
	@RequestMapping("/addJobDeatilPage")
	public String addJobDeatilPage() {
		return  "task/addJobDeatil";
	}

	@RequestMapping("/addTriggerPage")
	public String addTriggerPage() {
		return  "task/addTrigger";
	}

	@RequestMapping("/taskLogsrPage")
	public String taskLogsrPage() {
		return  "task/taskLogs";
	}


}
