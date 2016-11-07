package com.lesso.dao;

import com.lesso.base.MsgBo;
import com.lesso.entity.BehaviorLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/11.
 */
public interface IMongoDBDao {
    public MsgBo getBehaviorLogs() throws Exception;

    public MsgBo getBehaviorLogList(Date lastOperaTime) throws Exception;

    public MsgBo getBehaviorLogList(Date startDate,Date endDate) throws Exception;

    public void rollbackData( String uuidStr) throws Exception;
    public void printDocumentByIds( List<Map<String,Object>>  ids)throws Exception;

}
