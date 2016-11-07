package com.lesso.entity;

/**
 * Created by Administrator on 2016/5/12.
 */
public class UserAgent {
    private String opSystem;
    private String opSystemCode;
    private String opVersion;
    private String brower;
    private String browerVesion;
    private String browerCode;
    private String  deviceName;

    public String getOpSystem() {
        return opSystem;
    }

    public void setOpSystem(String opSystem) {
        this.opSystem = opSystem;
    }

    public String getOpSystemCode() {
        return opSystemCode;
    }

    public void setOpSystemCode(String opSystemCode) {
        this.opSystemCode = opSystemCode;
    }

    public String getOpVersion() {
        return opVersion;
    }

    public void setOpVersion(String opVersion) {
        this.opVersion = opVersion;
    }

    public String getBrower() {
        return brower;
    }

    public void setBrower(String brower) {
        this.brower = brower;
    }

    public String getBrowerVesion() {
        return browerVesion;
    }

    public void setBrowerVesion(String browerVesion) {
        this.browerVesion = browerVesion;
    }

    public String getBrowerCode() {
        return browerCode;
    }

    public void setBrowerCode(String browerCode) {
        this.browerCode = browerCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
