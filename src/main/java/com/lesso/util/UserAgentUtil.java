package com.lesso.util;

import com.alibaba.fastjson.JSON;
import com.lesso.entity.UserAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/12.
 */
public class UserAgentUtil {

    private static UserAgent userAgentEntity=new UserAgent();
    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            +"|windows (phone|ce)|blackberry"
            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            +"|laystation portable)|nokia|fennec|htc[-_]"
            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

    /**
     * 检测是否是移动设备访问
     * @Title: check
     * @Date : 2014-7-7 下午01:29:07
     * @param userAgent 浏览器标识
     * @return true:移动设备接入，false:pc端接入
     */
    public static boolean check(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static String checkOS(String userAgent){
        String agent=userAgent.toLowerCase();
        if (agent.contains("windows nt 6.4")||agent.contains("windows nt 10.0"))
        {
            return "Win10";
        }
        if (agent.contains("windows nt 6.2")||agent.contains("windows nt 6.3"))
        {
           return "Win8";
        }
        if (agent.contains("windows nt 6.1"))
        {
            return "Win7";
        }
        if (agent.contains("windows nt 5.01")||agent.contains("windows xp"))
        {
            return "WinXP";
        }

        //排除 苹果桌面系统
        if (agent.contains("macintosh"))
        {
            return "Mac";
        }
        if (agent.contains("android"))
        {
            return "Android";
        }
        if (agent.contains("linux"))
        {
            return "Linux";
        }
        if (agent.contains("iphone"))
        {
            return "IOS";
        }
//        if (agent.contains("ipod"))
//        {
//            userAgentEntity.setOpSystem("ipod");
//            userAgentEntity.setOpSystemCode(SysConfig.getSysData("OtherOS"));
//            return "OtherOS";
//        }
//        if (agent.contains("ipad"))
//        {
//            userAgentEntity.setOpSystem("ipad");
//            userAgentEntity.setOpSystemCode(SysConfig.getSysData("OtherOS"));
//            return "OtherOS";
//        }
       if (agent.contains("windows phone"))
       {
                return "WinPhone";
       }
        return "OtherOS";
    }
    //获取系统号
    public static String getOSCode(String userAgent){
        return SysConfig.getSysData(checkOS(userAgent));
    }
    //浏览器
    private final static String IE11="Trident";
    private final static String IE10="MSIE 10.0";
    private final static String IE9="MSIE 9.0";
    private final static String IE8="MSIE 8.0";
    private final static String IE7="MSIE 7.0";
    private final static String IE6="MSIE 6.0";
    private final static String MAXTHON="Maxthon";
    private final static String QQ="QQBrowser";
    private final static String QQ2="TencentTraveler";
    private final static String GREEN="GreenBrowser";
    private final static String SE360="360SE";
    private final static String FIREFOX="Firefox";
    private final static String OPERA="Opera";
    private final static String CHROME="Chrome";
    private final static String SAFARI="Safari";
    private final static String UCWEB2="UCBrowser";
    private final static String UCWEB="UCWEB";
    private final static String UCWEB3="UBrowser";
    private final static String SOGO="MetaSr";
    private final static String BAIDU="baidubrowser";
    private final static String BAIDU2="BIDUBrowser";
    private final static String MICROMESSENGER="MicroMessenger";

   // private final static String BAIDU2="BIDUBrowser";



    public static String checkBrowser(String userAgent){
        if(regex(MAXTHON, userAgent)){
            return "OtherBrower";
        }
        if(regex(UCWEB,userAgent)||regex(UCWEB2,userAgent)||regex(UCWEB3,userAgent)){
            return "UcWeb";
        }
        if(regex(QQ,userAgent)||regex(QQ2,userAgent)){
            return "QQBrowser";
        }
        if(regex(BAIDU, userAgent)||regex(BAIDU2, userAgent)){
            return "baidu";
        }
        if(regex(SOGO, userAgent))
        {
            return "Sogo";
        }
        if(regex(OPERA, userAgent)){
            return "Opera";
        }
        if(regex(CHROME, userAgent)){
            return "Chrome";
        }
        if(regex(FIREFOX, userAgent)){
            return "Firefox";
        }
        if(regex(SAFARI, userAgent)){
            return "Safari";
        }
        if(regex(SE360, userAgent)){
            return "360SE";
        }
        if(regex(GREEN,userAgent)){
            return "OtherBrower";
        }

        if(regex(IE9,userAgent)){
            return "IE";
        }
        if(regex(IE8,userAgent)){
            return "IE";
        }
        if(regex(IE7,userAgent)){
            return "IE";
        }
        if(regex(IE6,userAgent)){
            return "IE";
        }
        return "OtherBrower";
    }
    //获取浏览器编码
    public static String getBrowserCode(String userAgent){
        return SysConfig.getSysData(checkBrowser(userAgent));
    }
    public static boolean regex(String regex,String str){
        Pattern p =Pattern.compile(regex,Pattern.MULTILINE);
        Matcher m=p.matcher(str);
        return m.find();
    }

   public static boolean isIOS ( String userAgent){
      // /iPhone|iPad|iPod|Macintos/i.test(userAgent);
       Pattern p =Pattern.compile("iPhone|iPad|iPod|Macintosh",Pattern.MULTILINE);
       Matcher m=p.matcher(userAgent);
       return m.find();
   }
    public static boolean isAndroid (String userAgent){
        ///Android/i.test(userAgent);
        Pattern p =Pattern.compile("Android",Pattern.MULTILINE);
        Matcher m=p.matcher(userAgent);
        return m.find();

    }

    public static String getDeviceName  (String userAgent){
        String deviceName=null;
        if(StringUtils.isNotBlank(userAgent)) {
            if (isIOS(userAgent)) {
                String regStr = "iPhone|iPad|iPod|Macintosh";
                List<Integer> groupNumber = Arrays.asList(new Integer[]{0});
                List<String> vesions = RegExpUtil.getValueByRegExp(userAgent, regStr, groupNumber);
                if (vesions != null && vesions.size() > 1) {
                    deviceName = vesions.get(0);
                }
            } else if (isAndroid(userAgent)) {
                String regStr = "([\\w]+[\\W])+(?=Build)"; //";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/"
                List<Integer> groupNumber = Arrays.asList(new Integer[]{0});
                List<String> vesions = RegExpUtil.getValueByRegExp(userAgent, regStr, groupNumber);
                if (vesions != null && vesions.size() > 1) {
                    deviceName = vesions.get(0);
                }
            }
        }
        return deviceName;
    };


    public static UserAgent initUserAgent(String userAgent){
        String agent="";
        if(StringUtils.isNotBlank(userAgent)){
            agent=userAgent.toLowerCase();
        }else{
            userAgent="";
        }
        //获取系统信息
        if (agent.contains("windows nt 6.4")||agent.contains("windows nt 10.0"))
        {
            userAgentEntity.setOpSystem("Windows");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("Win10"));
            userAgentEntity.setOpVersion("10");
        }else if (agent.contains("windows nt 6.2")||agent.contains("windows nt 6.3"))
        {
            //window8
            userAgentEntity.setOpSystem("Windows");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("Win8"));
            userAgentEntity.setOpVersion("8");
        }else if (agent.contains("windows nt 6.1"))
        {
            userAgentEntity.setOpSystem("Windows");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("Win7"));
            userAgentEntity.setOpVersion("7");

        }else if (agent.contains("windows nt 5.01")||agent.contains("windows xp"))
        {
            userAgentEntity.setOpSystem("Windows");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("WinXP"));
            userAgentEntity.setOpVersion("XP");

        }else if(agent.contains("macintosh"))
        {
            userAgentEntity.setOpSystem("IOS");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("Mac"));
            String regStr="Mac OS X (\\d+).(\\d+)";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1,2});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>1){
                userAgentEntity.setOpVersion(vesions.get(0)+"."+vesions.get(1));
            }


        }else if (agent.contains("android"))
        {
            userAgentEntity.setOpSystem("Android");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("Android"));
            String regStr="Android (\\d+).(\\d+)";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1,2});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>1) {
                userAgentEntity.setOpVersion(vesions.get(0) + "." + vesions.get(1));
            }
        }else if (agent.contains("linux"))
        {
            userAgentEntity.setOpSystem("Linux");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("Linux"));
            userAgentEntity.setOpVersion("");
        }else if (agent.contains("iphone"))
        {
            userAgentEntity.setOpSystem("IOS");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("IOS"));
            String regStr="iPhone OS (\\d+)_(\\d+)";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1,2});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>1) {
                userAgentEntity.setOpVersion(vesions.get(0) + "." + vesions.get(1));
            }
        }
//        else if (agent.contains("ipod"))
//        {
//            userAgentEntity.setOpSystem("ipod");
//            userAgentEntity.setOpSystemCode(SysConfig.getSysData("OtherOS"));
//            userAgentEntity.setOpVersion("");
//
//        }else if (agent.contains("ipad"))
//        {
//            userAgentEntity.setOpSystem("ipad");
//            userAgentEntity.setOpSystemCode(SysConfig.getSysData("OtherOS"));
//            userAgentEntity.setOpVersion("");
//
//        }
        else if (agent.contains("windows phone"))
        {
            userAgentEntity.setOpSystem("WinPhone");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("WinPhone"));
            String regStr="Windows Phone OS (\\w+).(\\w+)";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1,2});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>1) {
                userAgentEntity.setOpVersion(vesions.get(0) + "." + vesions.get(1));
            }
        }else{
            userAgentEntity.setOpSystem("OtherOS");
            userAgentEntity.setOpSystemCode(SysConfig.getSysData("OtherOS"));
        }

        //获取浏览器信息
        if(regex(BAIDU, userAgent)||regex(BAIDU2, userAgent)){
            userAgentEntity.setBrower("baidu");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("baidu"));
        }else if(regex(SOGO, userAgent))
        {
            userAgentEntity.setBrower("Sogo");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("Sogo"));
        }else if(regex(SE360, userAgent)){
            userAgentEntity.setBrower("360SE");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("360SE"));
        }else if(regex(GREEN,userAgent)){
            userAgentEntity.setBrower("GreenBrowser");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("Others"));
        }else if(regex(QQ,userAgent)||regex(QQ2,userAgent)){
            userAgentEntity.setBrower("QQBrowser");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("QQBrowser"));
            String regStr="MQQBrowser\\/(\\S+(?=\\\"|\\s))";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>0) {
                userAgentEntity.setBrowerVesion(vesions.get(0));
            }

        }else if(regex(UCWEB,userAgent)||regex(UCWEB2,userAgent)||regex(UCWEB3,userAgent)){
            userAgentEntity.setBrower("UcWeb");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("UcWeb"));
        }else if(regex(OPERA, userAgent)){
            userAgentEntity.setBrower("Opera");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("Opera"));

        }else if(regex(FIREFOX, userAgent)){
            userAgentEntity.setBrower("Firefox");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("Firefox"));

        }else if(regex(CHROME, userAgent)){
            userAgentEntity.setBrower("Chrome");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("Chrome"));
            String regStr="Chrome\\/(\\S+(?=\\\"|\\s))";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>0) {
                userAgentEntity.setBrowerVesion(vesions.get(0));
            }

        }else if(regex(SAFARI, userAgent)){
            userAgentEntity.setBrower("Safari");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("Safari"));
            String regStr="Version\\/(\\S+(?=\\\"|\\s))";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>0) {
                userAgentEntity.setBrowerVesion(vesions.get(0));
            }
        }
//        else if(regex(MAXTHON, userAgent)){
//            userAgentEntity.setBrower("Maxthon");
//            userAgentEntity.setBrowerCode(SysConfig.getSysData("OtherBrower"));
//
//        }
        else if(regex(IE11,userAgent)&&userAgent.indexOf("rv:11") > -1){
            userAgentEntity.setBrower("IE");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("IE"));
            userAgentEntity.setBrowerVesion("11");
        }
        else if(regex(IE10,userAgent)){
            userAgentEntity.setBrower("IE");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("IE"));
            userAgentEntity.setBrowerVesion("10");
        }
        else if(regex(IE9,userAgent)){
            userAgentEntity.setBrower("IE");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("IE"));
            userAgentEntity.setBrowerVesion("9");
        }else if(regex(IE8,userAgent)){
            userAgentEntity.setBrower("IE");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("IE"));
            userAgentEntity.setBrowerVesion("8");
        }else if(regex(IE7,userAgent)){
            userAgentEntity.setBrower("IE");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("IE"));
            userAgentEntity.setBrowerVesion("7");
        }else if(regex(IE6,userAgent)){
            userAgentEntity.setBrower("IE");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("IE"));
            userAgentEntity.setBrowerVesion("6");
        }else if(regex(MICROMESSENGER,userAgent)){
            userAgentEntity.setBrower("MicroMsger");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("MicroMessenger"));
            String regStr="MicroMessenger\\/(\\S+(?=\\\"|\\s))";
            List<Integer>  groupNumber = Arrays.asList(new Integer[]{1});
            List<String> vesions=RegExpUtil.getValueByRegExp(userAgent,regStr,groupNumber);
            if(vesions!=null && vesions.size()>0) {
                userAgentEntity.setBrowerVesion(vesions.get(0));
            }
        }else{
            userAgentEntity.setBrower("Others");
            userAgentEntity.setBrowerCode(SysConfig.getSysData("OtherBrower"));
        }
        userAgentEntity.setDeviceName(getDeviceName(userAgent));
        return userAgentEntity;
    }


    public static void main(String[] args) {
        String referer ="http://www.lessmall.com/distributor/teacher";
        String regStr="distributor/(\\w+)";
        List<Integer>  groupNumber = Arrays.asList(new Integer[]{1});
        List<String> vesions=RegExpUtil.getValueByRegExp(referer,regStr,groupNumber);
        System.out.println(JSON.toJSONString(vesions.get(0)));

    }
}
