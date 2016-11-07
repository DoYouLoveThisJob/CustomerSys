package com.lesso.dao;

import com.lesso.base.MsgBo;
import com.lesso.entity.BehaviorLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 */
public interface IUserLogDao extends IBaseOracleDao {
    public MsgBo saveUserLog(List<BehaviorLog> behaviorLogs) throws Exception;
    public MsgBo saveUserLog(BehaviorLog behaviorLog) throws Exception;
    public List<Map<String,Object>> findEmptyColUserLog();
    public List<BehaviorLog> getBehaviorList();
    //public MsgBo updateOperateLog(Date operateLogDate) throws Exception;
    public MsgBo updateUserLog(List<BehaviorLog> behaviorLogs) throws Exception;
}
