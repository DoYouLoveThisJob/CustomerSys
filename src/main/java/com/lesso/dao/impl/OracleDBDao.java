package com.lesso.dao.impl;


import com.lesso.base.DataTablesBo;
import com.lesso.base.MsgBo;
import com.lesso.dao.IOracleDao;
import com.lesso.entity.BehaviorLog;
import com.lesso.entity.OperateLog;
import com.lesso.util.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.faces.component.behavior.Behavior;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 0003 on 2016/4/27.
 */
@Repository(value = "oracleDBDao")
public class OracleDBDao  implements IOracleDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate  namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    /***
     * 批量保存用户日志记录
     * */
    public MsgBo saveUserLog(List<BehaviorLog> behaviorLogs) throws Exception {
        MsgBo msgBo=new MsgBo();
        String sql = "insert into bi_user_behavior_log (OPTSYSTEMVERSION, CREATETIME, APPTYPE, SESSIONID, BEHAVIORCONTENT, DEVICE, IPADDRESS, LONGITUDE, LATITUDE, BROWSER, " +
                "OPTSYSTEM, PAGETITLE, APPVERSION, DELFLAG, USERCODE, BEHAVIOR, OPTPATH, OBJECTID, BROWSERVERSION,LASTUPDATED, ID)" +
                "values (?,?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,dbms_random.string('x',32))";
       final List<BehaviorLog> saveList=behaviorLogs;
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                // TODO Auto-generated method stub
                ps.setString(1, saveList.get(i).getOptSystemVersion());
                ps.setDate(2, new java.sql.Date(saveList.get(i).getCreateTime().getTime()));
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
                ps.setDate(20, new java.sql.Date(saveList.get(i).getCreateTime().getTime()));
            }
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return saveList.size();
            }
        };
        int[] result = jdbcTemplate.batchUpdate(sql, setter);
        msgBo.setMsg("save Success");
        msgBo.setIsSucc(true);
        msgBo.setObj(behaviorLogs);
        return msgBo;
    }





    public <T> MsgBo saveObj(List<T> objs) throws Exception {

        return null;
    }



    public List<Map<String,Object>> findEmptyColUserLog(){
        //return jdbcTemplate.queryForList("select t.objectid from BI_USER_BEHAVIOR_LOG t where t.behavior is null");
        return jdbcTemplate.queryForList("select t.objectid from BI_USER_BEHAVIOR_LOG t where t.ipaddress is null");
    }

    public List<BehaviorLog> getBehaviorList() {
        return ReflectUtil.reflectList(jdbcTemplate.queryForList("select * from BI_USER_BEHAVIOR_LOG t where rownum<11"),BehaviorLog.class) ;
    }

    /***
     * 查询分页列表
     * */
    public <T> List<T> queryPageList(String query,Class<T> c,Integer start,Integer length ,Integer draw){
        if(start==null || length==null){
            return new ArrayList<T>();
        }
        int end=start+length-1;
        String pageSql="SELECT * FROM ( SELECT A.*, ROWNUM RN " +
                "FROM ("+query+") A WHERE ROWNUM <= "+end+" ) WHERE RN >= "+start;
        System.out.println(pageSql);
        List<Map<String,Object>> resultMap=this.jdbcTemplate.queryForList(pageSql);
        if(resultMap!=null && resultMap.size()>0){
            List<T> resultList=ReflectUtil.reflectList(resultMap,c);
            return resultList;
        }else{
            return  new ArrayList<T>();
        }
    }
    /***
     * 查询分页列表
     * */
    public <T> List<T> queryPageList(String query,Class<T> c,List parms,Integer start,Integer length ,Integer draw){
        if(start==null || length==null){
            return new ArrayList<T>();
        }
        int end=start+length-1;
        String pageSql="SELECT * FROM ( SELECT A.*, ROWNUM RN " +
                "FROM ("+query+") A WHERE ROWNUM <= ?) WHERE RN >= ?";
        parms.add(end);
        parms.add(start);
        return queryList(pageSql,c,parms);
    }

    /***
     * 查询分页列表
     * */
    public <T> List<T> queryPageList(String query,Class<T> c,Map parms,Integer start,Integer length ,Integer draw){
        if(start==null || length==null){
            return new ArrayList<T>();
        }
        int end=start+length-1;
        String pageSql="SELECT * FROM ( SELECT A.*, ROWNUM RN " +
                "FROM ("+query+") A WHERE ROWNUM <= :end) WHERE RN >= :start";
        parms.put("end",end);
        parms.put("start",start);
        return queryList(pageSql,c,parms);
    }


    /***
     * 查询结果总数
     * */
    public Integer queryTotal(String queryCount){
        System.out.println(queryCount);
        Integer resultCount=this.jdbcTemplate.queryForObject(queryCount,Integer.class);
        return resultCount;
    }
    /***
     * 带参数查询,防止sql注入
     * */
    public Integer queryTotal(String query,List parms){
        final List<Object> parmsf=parms;
//        Integer result=this.jdbcTemplate.query(query,
//                new PreparedStatementSetter() {
//                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                        for (int i = 0; i < parmsf.size(); i++) {
//                            preparedStatement.setObject(i + 1, parmsf.get(i));
//                        }
//                    }
//                },
//                new ResultSetExtractor<Integer>() {
//                public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
//                    Integer  resultCount=0;
//                    while(resultSet.next()){
//                        resultCount=resultSet.getInt(1);
//                    }
//                    return resultCount;
//                }
//                }
//        );
        Integer result=this.jdbcTemplate.queryForObject(query,parmsf.toArray(),Integer.class);
        return result;
    }

    /***
     * 带参数查询,防止sql注入
     * */
    public Integer queryTotal(String query,Map parms){
        return this.namedParameterJdbcTemplate.queryForObject(query,parms,Integer.class);
    }

    /***
     * 无参数查询,可能会出现sql注入
     * */
    public <T> List<T> queryList(String query,Class<T> c){
        List<Map<String,Object>> resultMap=this.jdbcTemplate.queryForList(query);
        if(resultMap!=null && resultMap.size()>0){
            List<T> resultList=ReflectUtil.reflectList(resultMap,c);
            return resultList;
        }else{
            return  new ArrayList<T>();
        }
    }


    /***
     * 带参数查询,防止sql注入
     * */
    public <T> List<T> queryList(String query,Class<T> c,List parms){
       final List<Object> parmsf=parms;
       final Class<T> cf=c;
       List<T> result=this.jdbcTemplate.query(query, new PreparedStatementSetter() {
           public void setValues(PreparedStatement preparedStatement) throws SQLException {
               for (int i = 0; i < parmsf.size(); i++) {
                   preparedStatement.setObject(i + 1, parmsf.get(i));
               }
           }
       }, new ResultSetExtractor<List<T>>() {
           public List<T> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
               List<T>  resultList=ReflectUtil.reflectList(resultSet,cf);
               return resultList;
           }
       });
        return result;
    }

    public <T> List<T> queryList(String query,Class<T> c,Map parms){
       List<Map<String,Object>> resultMap=this.namedParameterJdbcTemplate.queryForList(query,parms);
        if(resultMap!=null && resultMap.size()>0){
            List<T> resultList=ReflectUtil.reflectList(resultMap,c);
            return resultList;
        }else{
            return  new ArrayList<T>();
        }
    }







}
