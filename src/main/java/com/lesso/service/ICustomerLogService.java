package com.lesso.service;

import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.entity.BehaviorLog;

import java.util.Date;
import java.util.List;

/**
 * Created by 0003 on 2016/5/13.
 */
public interface ICustomerLogService {
    public MsgBo saveLogs(List<BehaviorLog> logs) throws Exception;
    public MsgBo saveLog(BehaviorLog logs) throws Exception;
    public MsgBo saveLogs() throws Exception;
    public MsgBo saveLogsOfLastMonth() throws Exception;
    public MsgBo getMongDBLogs() throws Exception;
    public void rollBackMongoDB (String uuidStr)throws  Exception;
    public void printUserLog()throws Exception;
    public List<BehaviorLog> getBehaviorList()throws Exception;
    public DataTablesBo queryBehaviorLogsList(String seachText,String startTime,String endTime, Integer start, Integer length, Integer draw);
    public MsgBo getLogs(String logId) throws Exception;
}
