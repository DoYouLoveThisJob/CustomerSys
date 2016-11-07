package com.lesso.controller;

import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.entity.BehaviorLog;
import com.lesso.entity.UserAgent;
import com.lesso.service.ICustomerLogService;
import com.lesso.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * Created by 0003 on 2016/5/13.
 */
@Controller
@RequestMapping("/logs")
public class CustomerLogCotroller {
    @Autowired
    @Qualifier(value="customerLogService")
    private ICustomerLogService customerLogService;
    @RequestMapping("/custLogsPage")
    public String custLogsPage() {
        return  "index/index";
    }
    @RequestMapping("/custLogsDeatilPage")
    public String custLogsDeatilPage() {
        return  "index/custLogsDeatil";
    }

    @ResponseBody
    @RequestMapping("/saveLogs")
    public MsgBo saveLogs() {
        MsgBo msgBo=new MsgBo();

        try{
//            msgBoM=this.customerLogService.getMongDBLogs();
//            if(msgBoM.getIsSucc()){
//                msgBoO=this.customerLogService.saveLogs((List<BehaviorLog>) msgBoM.getObj());
//            }
//            msgBoO.setMsg(msgBoM.getMsg()+msgBoO.getMsg());
            msgBo=this.customerLogService.saveLogs();
        }catch (Exception ex){
          ex.printStackTrace();
//            try {
//                msgBoO.setIsSucc(false);
//                msgBoO.setMsg("程序异常,无法正常执行完毕");
//                this.customerLogService.rollBackMongoDB(msgBoM.getMsg());
//            }catch (Exception e){
//                 e.printStackTrace();
//            }
        }
        return msgBo;
   }

    @ResponseBody
    @RequestMapping("/saveLogsOfLastMonth")
    public MsgBo saveLogsOfLastMonth() {
        MsgBo msgBo=new MsgBo();
        try{
            msgBo=this.customerLogService.saveLogsOfLastMonth();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return msgBo;
    }

    @ResponseBody
    @RequestMapping("/txt")
    public String printUserLog() {
        try{
            this.customerLogService.printUserLog();

        }catch (Exception ex){
            ex.printStackTrace();
            return "Exception";
        }
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/printTopTenUserLog")
    public  List<BehaviorLog> printTopTenUserLog() {
        List<BehaviorLog> result=null;
        try{
            result =this.customerLogService.getBehaviorList();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/queryBehaviorLogsList")
    public DataTablesBo queryBehaviorLogsList(String searchText,String startTime,String endTime, Integer start, Integer length , Integer draw) {
        DataTablesBo result=null;
        try{
            result =this.customerLogService.queryBehaviorLogsList( searchText,startTime,endTime,start, length , draw);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/getCustLogs")
    public MsgBo getCustLogs(String logId) {
        MsgBo result=null;
        try{
            result =this.customerLogService.getLogs(logId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/addCusLogs")
    public MsgBo addCusLogs(HttpServletRequest request) {
        MsgBo result=null;
        try{
            String content=request.getParameter("content");
            String behavior=request.getParameter("behavior");
            String appType=request.getParameter("appType");
            String referer = request.getParameter("referer");
            //String device = request.getParameter("device");
            String userCode = request.getParameter("userCode");
            String ua = request.getHeader("User-Agent");
            String ip= CusAccessObjectUtil.getIpAddress(request);
            if(!StringUtils.isNotBlank(behavior)){
                 behavior="1001";
            }
            if(!StringUtils.isNotBlank(appType)){
                appType="701";
            }
            String appVesion= SysConfig.getSysData("appVesion");
            BehaviorLog behaviorLog=new BehaviorLog();
            behaviorLog.setBehavior(behavior);
            behaviorLog.setBehaviorContent(content);
            behaviorLog.setAppType(appType);
            behaviorLog.setPageTitle(null);
            behaviorLog.setSessionId(null);
            behaviorLog.setIpAddress(ip);
            behaviorLog.setLongitude(null);
            behaviorLog.setLatitude(null);
            behaviorLog.setUserCode(userCode);
            behaviorLog.setObjectid( null);
            behaviorLog.setOptPath(referer);
            behaviorLog.setDelflag(0);
            Date nowDate=new Date();
            behaviorLog.setCreateTime(nowDate);
            behaviorLog.setLastUpDated(nowDate);
            behaviorLog.setAppVersion(appVesion);
            UserAgent userAgent=UserAgentUtil.initUserAgent(ua);
            behaviorLog.setOptSystem( userAgent.getOpSystem());
            behaviorLog.setOptSystemVersion(userAgent.getOpVersion());
            behaviorLog.setBrowser(userAgent.getBrower());
            behaviorLog.setBrowserVersion(userAgent.getBrowerVesion());
            behaviorLog.setDevice(userAgent.getDeviceName());
            result =this.customerLogService.saveLog(behaviorLog);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }


}
