package com.lesso.entity;

import com.lesso.base.MyColumn;
import com.lesso.base.MyTable;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/11.
 */
@MyTable(name="BI_USER_BEHAVIOR_LOG")
public class BehaviorLog {
    @MyColumn(name="ID",type=String.class)
    private String id;
    @MyColumn(name="APPTYPE",type=String.class)
    private String appType;//102表示微信客户端
    @MyColumn(name="APPVERSION",type=String.class)
    private String appVersion;//版本
    @MyColumn(name="BEHAVIOR",type=String.class)
    private String behavior;//行为
    @MyColumn(name="BEHAVIORCONTENT",type=String.class)
    private String behaviorContent;//行为内容
    @MyColumn(name="BROWSER",type=String.class)
    private String browser;//浏览器
    @MyColumn(name="BROWSERVERSION",type=String.class)
    private String browserVersion;//浏览器版本
    @MyColumn(name="PAGETITLE",type=String.class)
    private String pageTitle;//页面标题
    @MyColumn(name="SESSIONID",type=String.class)
    private String sessionId;//会话id
    @MyColumn(name="DEVICE",type=String.class)
    private String device;//设备id
    @MyColumn(name="IPADDRESS",type=String.class)
    private String ipAddress;//ip地址
    @MyColumn(name="LONGITUDE,type=String.class")
    private String longitude;//经度
    @MyColumn(name="LATITUDE",type=String.class)
    private String latitude;//纬度
    @MyColumn(name="USERCODE",type=String.class)
    private String userCode;//用户编号
    @MyColumn(name="OBJECTID",type=String.class)
    private String objectid;//日志记录id
    @MyColumn(name="OPTSYSTEM",type=String.class)
    private String optSystem;//操作系统版本
    @MyColumn(name="OPTSYSTEMVERSION",type=String.class)
    private String optSystemVersion;//操作系统版本
    @MyColumn(name="OPTPATH",type=String.class)
    private String optPath;//操作链接
    @MyColumn(name="DELFLAG",type=Integer.class)
    private Integer  delflag;//删除标识
    @MyColumn(name="LASTUPDATED",type=Date.class)
    private Date lastUpDated;//修改时间
    @MyColumn(name="CREATETIME",type=Date.class)
    private Date createTime;//创建时间
    private String appTypeDesc;
    private String behaviorDesc;
    private String browserDesc;
    private String optSystemDesc;

    public String getAppTypeDesc() {
        return appTypeDesc;
    }

    public void setAppTypeDesc(String appTypeDesc) {
        this.appTypeDesc = appTypeDesc;
    }

    public String getBehaviorDesc() {
        return behaviorDesc;
    }

    public void setBehaviorDesc(String behaviorDesc) {
        this.behaviorDesc = behaviorDesc;
    }

    public String getBrowserDesc() {
        return browserDesc;
    }

    public void setBrowserDesc(String browserDesc) {
        this.browserDesc = browserDesc;
    }

    public String getOptSystemDesc() {
        return optSystemDesc;
    }

    public void setOptSystemDesc(String optSystemDesc) {
        this.optSystemDesc = optSystemDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getBehaviorContent() {
        return behaviorContent;
    }

    public void setBehaviorContent(String behaviorContent) {
        this.behaviorContent = behaviorContent;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getOptSystem() {
        return optSystem;
    }

    public void setOptSystem(String optSystem) {
        this.optSystem = optSystem;
    }

    public String getOptSystemVersion() {
        return optSystemVersion;
    }

    public void setOptSystemVersion(String optSystemVersion) {
        this.optSystemVersion = optSystemVersion;
    }

    public String getOptPath() {
        return optPath;
    }

    public void setOptPath(String optPath) {
        this.optPath = optPath;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getLastUpDated() {
        return lastUpDated;
    }

    public void setLastUpDated(Date lastUpDated) {
        this.lastUpDated = lastUpDated;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
