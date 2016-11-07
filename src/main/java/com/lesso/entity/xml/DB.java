package com.lesso.entity.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/2.
 */
public class DB {
    private String  id;
    private Map<String,String> result=new HashMap<String,String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }
    public void addSql( Sql sql){
        result.put(sql.getId(),sql.getContext());
    }
}
