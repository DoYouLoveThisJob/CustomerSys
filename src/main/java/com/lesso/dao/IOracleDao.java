package com.lesso.dao;

import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.entity.BehaviorLog;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/10.
 */
public interface IOracleDao {
    public MsgBo saveUserLog(List<BehaviorLog> behaviorLogs) throws Exception;
    public List<Map<String,Object>> findEmptyColUserLog();
    public List<BehaviorLog> getBehaviorList();

    public <T> List<T> queryPageList(String query,Class<T> c,Integer start,Integer length ,Integer draw);
    public <T> List<T> queryList(String query,Class<T> c);
    public Integer queryTotal(String queryCount);

    public <T> List<T> queryPageList(String query,Class<T> c,List parms,Integer start,Integer length ,Integer draw);
    public <T> List<T> queryList(String query,Class<T> c,List parms);
    public Integer queryTotal(String query,List parms);

    public <T> List<T> queryPageList(String query,Class<T> c,Map parms,Integer start,Integer length ,Integer draw);
    public <T> List<T> queryList(String query,Class<T> c,Map parms);
    public Integer queryTotal(String query,Map parms);
}
