package com.lesso.dao.impl;


import com.lesso.base.MsgBo;
import com.lesso.dao.IUserLogDao;
import com.lesso.entity.BehaviorLog;
import com.lesso.util.ReflectUtil;

import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 0003 on 2016/4/27.
 */
@Repository(value = "userLogDao")
public class UserLogDao extends BaseOracleDao implements IUserLogDao {
    /***
     * 批量保存用户日志记录
     * */
    public MsgBo saveUserLog(List<BehaviorLog> behaviorLogs) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "insert into bi_user_behavior_log (OPTSYSTEMVERSION, CREATETIME, APPTYPE, SESSIONID, BEHAVIORCONTENT, DEVICE, IPADDRESS, LONGITUDE, LATITUDE, BROWSER, " +
                "OPTSYSTEM, PAGETITLE, APPVERSION, DELFLAG, USERCODE, BEHAVIOR, OPTPATH, OBJECTID, BROWSERVERSION,LASTUPDATED, ID)" +
                "values (?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,dbms_random.string('x',32))";
       final List<BehaviorLog> saveList=behaviorLogs;
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, saveList.get(i).getOptSystemVersion());
                ps.setTimestamp(2, new java.sql.Timestamp(saveList.get(i).getCreateTime().getTime()));
                ps.setString(3, saveList.get(i).getAppType());
                ps.setString(4, saveList.get(i).getSessionId());
                ps.setString(5, saveList.get(i).getBehaviorContent());
                ps.setString(6, saveList.get(i).getDevice());
                ps.setString(7, saveList.get(i).getIpAddress());
                ps.setString(8, saveList.get(i).getLongitude());
                ps.setString(9, saveList.get(i).getLatitude());
                ps.setString(10, saveList.get(i).getBrowser());
                ps.setString(11, saveList.get(i).getOptSystem());
                ps.setString(12, saveList.get(i).getPageTitle());
                ps.setString(13, saveList.get(i).getAppVersion());
                ps.setInt(14, saveList.get(i).getDelflag());
                ps.setString(15, saveList.get(i).getUserCode());
                ps.setString(16, saveList.get(i).getBehavior());
                ps.setString(17, saveList.get(i).getOptPath());
                ps.setString(18, saveList.get(i).getObjectid());
                ps.setString(19, saveList.get(i).getBrowserVersion());
                ps.setTimestamp(20, new java.sql.Timestamp(saveList.get(i).getCreateTime().getTime()));
            }
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return saveList.size();
            }
        };
        int[] result = getJdbcTemplate().batchUpdate(sql, setter);
        msgBo.setMsg("Oracle数据库正在处理"+behaviorLogs.size()+"条数据,成功处理完毕"+behaviorLogs.size()+"条待处理数据;");
        msgBo.setIsSucc(true);
        msgBo.setObj(behaviorLogs);
        return msgBo;
    }


    /***
     * 保存用户日志记录
     * */
    public MsgBo saveUserLog(BehaviorLog behaviorLog) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "insert into bi_user_behavior_log (OPTSYSTEMVERSION, CREATETIME, APPTYPE, SESSIONID, BEHAVIORCONTENT, DEVICE, IPADDRESS, LONGITUDE, LATITUDE, BROWSER, " +
                "OPTSYSTEM, PAGETITLE, APPVERSION, DELFLAG, USERCODE, BEHAVIOR, OPTPATH, OBJECTID, BROWSERVERSION,LASTUPDATED, ID)" +
                "values (?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,dbms_random.string('x',32))";
        final BehaviorLog behaviorLogf=behaviorLog;
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, behaviorLogf.getOptSystemVersion());
               //ps.setDate(2, new java.sql.Date(behaviorLogf.getCreateTime().getTime()));
                ps.setTimestamp(2, new java.sql.Timestamp(behaviorLogf.getCreateTime().getTime()));
                ps.setString(3, behaviorLogf.getAppType());
                ps.setString(4, behaviorLogf.getSessionId());
                ps.setString(5, behaviorLogf.getBehaviorContent());
                ps.setString(6, behaviorLogf.getDevice());
                ps.setString(7, behaviorLogf.getIpAddress());
                ps.setString(8, behaviorLogf.getLongitude());
                ps.setString(9, behaviorLogf.getLatitude());
                ps.setString(10, behaviorLogf.getBrowser());
                ps.setString(11, behaviorLogf.getOptSystem());
                ps.setString(12, behaviorLogf.getPageTitle());
                ps.setString(13, behaviorLogf.getAppVersion());
                ps.setInt(14, behaviorLogf.getDelflag());
                ps.setString(15, behaviorLogf.getUserCode());
                ps.setString(16, behaviorLogf.getBehavior());
                ps.setString(17, behaviorLogf.getOptPath());
                ps.setString(18, behaviorLogf.getObjectid());
                ps.setString(19, behaviorLogf.getBrowserVersion());
                ps.setTimestamp(20, new java.sql.Timestamp(behaviorLogf.getCreateTime().getTime()));
            }
        };
        int result = getJdbcTemplate().update(sql, setter);
        msgBo.setMsg("保存成功");
        msgBo.setIsSucc(true);
        msgBo.setObj(behaviorLog);
        return msgBo;
    }



    /***
     * 批量保存用户日志记录
     * */
    public MsgBo updateUserLog(List<BehaviorLog> behaviorLogs) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "update bi_user_behavior_log set OPTSYSTEMVERSION=?, CREATETIME=?, APPTYPE=?, SESSIONID=?, BEHAVIORCONTENT=?, DEVICE=?, IPADDRESS=?, LONGITUDE=?, LATITUDE=?, BROWSER=?, " +
                "OPTSYSTEM=?, PAGETITLE=?, APPVERSION=?, DELFLAG=?, USERCODE=?, BEHAVIOR=?, OPTPATH=?,  BROWSERVERSION=?,LASTUPDATED= sysdate " +
                "where OBJECTID = ?";
        final List<BehaviorLog> saveList=behaviorLogs;
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, saveList.get(i).getOptSystemVersion());
                ps.setTimestamp(2, new java.sql.Timestamp(saveList.get(i).getCreateTime().getTime()));
                ps.setString(3, saveList.get(i).getAppType());
                ps.setString(4, saveList.get(i).getSessionId());
                ps.setString(5, saveList.get(i).getBehaviorContent());
                ps.setString(6, saveList.get(i).getDevice());
                ps.setString(7, saveList.get(i).getIpAddress());
                ps.setString(8, saveList.get(i).getLongitude());
                ps.setString(9, saveList.get(i).getLatitude());
                ps.setString(10, saveList.get(i).getBrowser());
                ps.setString(11, saveList.get(i).getOptSystem());
                ps.setString(12, saveList.get(i).getPageTitle());
                ps.setString(13, saveList.get(i).getAppVersion());
                ps.setInt(14, saveList.get(i).getDelflag());
                ps.setString(15, saveList.get(i).getUserCode());
                ps.setString(16, saveList.get(i).getBehavior());
                ps.setString(17, saveList.get(i).getOptPath());
                ps.setString(18, saveList.get(i).getBrowserVersion());
                //ps.setDate(19, new java.sql.Date(saveList.get(i).getCreateTime().getTime()));
                ps.setString(19, saveList.get(i).getObjectid());
            }
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return saveList.size();
            }
        };
        int[] result = getJdbcTemplate().batchUpdate(sql, setter);
        List<BehaviorLog> addBehaviorLogs=new ArrayList<BehaviorLog>();
        for(int i=0;i<result.length;i++){
            if(result[i]==0){
                addBehaviorLogs.add(saveList.get(i));
            }
        }
        msgBo.setMsg("Oracle数据库正在处理"+behaviorLogs.size()+"条数据,成功处理完毕"+behaviorLogs.size()+"条待处理数据;");
        msgBo.setIsSucc(true);
        msgBo.setObj(addBehaviorLogs);
        return msgBo;
    }



    public <T> MsgBo saveObj(List<T> objs) throws Exception {
        return null;
    }
    public List<Map<String,Object>> findEmptyColUserLog(){
        //return jdbcTemplate.queryForList("select t.objectid from BI_USER_BEHAVIOR_LOG t where t.behavior is null");
        return getJdbcTemplate().queryForList("select t.objectid from BI_USER_BEHAVIOR_LOG t where t.ipaddress is null");
    }
    public List<BehaviorLog> getBehaviorList() {
        return ReflectUtil.reflectList(getJdbcTemplate().queryForList("select * from BI_USER_BEHAVIOR_LOG t where rownum<11"),BehaviorLog.class) ;
    }

//    /***
//     * 更新客户日志操作时间
//     * */
//    public MsgBo updateOperateLog(Date operateLogDate) throws Exception {
//        MsgBo msgBo=new MsgBo();
//        String sql = "update OPERATE_LOG set operate_time= ? where operate_name= 'CustomSYS' ";
//        final Date operateLogDateF=operateLogDate;
//        PreparedStatementSetter setter = new PreparedStatementSetter() {
//            public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                preparedStatement.setTimestamp(1, new java.sql.Timestamp(operateLogDateF.getTime()));
//            }
//        };
//        int result = getJdbcTemplate().update(sql, setter);
//        msgBo.setMsg("save Success");
//        msgBo.setIsSucc(true);
//        msgBo.setObj(operateLogDate);
//        return msgBo;
//    }


}
