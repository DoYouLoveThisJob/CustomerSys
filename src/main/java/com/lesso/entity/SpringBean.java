package com.lesso.entity;

/**
 * Created by Administrator on 2016/5/24.
 */
public class SpringBean {
    private String id;
    private String clazz;
    private String beanType;
    private String beanName;
    public String getBeanName() {
        return beanName;
    }
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    public String getBeanType() {
        return beanType;
    }
    public void setBeanType(String beanType) {
        this.beanType = beanType;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getClazz() {
        return clazz;
    }
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
