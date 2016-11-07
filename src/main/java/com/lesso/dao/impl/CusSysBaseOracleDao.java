package com.lesso.dao.impl;

import com.lesso.base.MsgBo;
import com.lesso.dao.IBaseOracleDao;
import com.lesso.util.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/24.
 */
@Repository(value = "cusSysBaseOracleDao")
public class CusSysBaseOracleDao implements IBaseOracleDao {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    @Qualifier(value="dataSource_cusSys")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    /***
     * 查询分页列表
     * */
    public <T> List<T> queryPageList(String query, Class<T> c, Integer start, Integer length , Integer draw){
        if(start==null || length==null){
            return new ArrayList<T>();
        }
        int end=start+length-1;
        String pageSql="SELECT * FROM ( SELECT A.*, ROWNUM RN " +
                "FROM ("+query+") A WHERE ROWNUM <= "+end+" ) WHERE RN >= "+start;
        System.out.println(pageSql);
        List<Map<String,Object>> resultMap=this.jdbcTemplate.queryForList(pageSql);
        if(resultMap!=null && resultMap.size()>0){
            List<T> resultList= ReflectUtil.reflectList(resultMap,c);
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


    public JdbcTemplate getJdbcTemplate() {
        //return jdbcTemplateOfChildren;
        return jdbcTemplate;
    }
    public NamedParameterJdbcTemplate getnamedParameterJdbcTemplate() {
        //return  namedParameterJdbcTemplatOfChildren;
        return namedParameterJdbcTemplate;
    }
}
