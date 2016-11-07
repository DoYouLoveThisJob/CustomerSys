package com.lesso.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface IBaseOracleDao {
    public <T> List<T> queryPageList(String query, Class<T> c, Integer start, Integer length , Integer draw);
    public <T> List<T> queryList(String query,Class<T> c);
    public Integer queryTotal(String queryCount);
    public <T> List<T> queryPageList(String query,Class<T> c,List parms,Integer start,Integer length ,Integer draw);
    public <T> List<T> queryList(String query,Class<T> c,List parms);
    public Integer queryTotal(String query,List parms);
    public <T> List<T> queryPageList(String query, Class<T> c, Map parms, Integer start, Integer length , Integer draw);
    public <T> List<T> queryList(String query,Class<T> c,Map parms);
    public Integer queryTotal(String query,Map parms);
    public JdbcTemplate getJdbcTemplate();
    public NamedParameterJdbcTemplate getnamedParameterJdbcTemplate();
}
