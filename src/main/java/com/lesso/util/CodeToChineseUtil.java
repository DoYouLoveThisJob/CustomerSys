package com.lesso.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class CodeToChineseUtil {
    public static String  getCronExpressionDesc(String cronExpression){
        String cronExpressionDesc="";
        String regSecond="\\*\\/(\\d+) \\* \\* \\* \\* \\?";
        String regMinute="0 \\*/(\\d+) \\* \\* \\* \\?";
        String regHour="0 0 \\*/(\\d+) \\* \\* \\?";
        String regTime="(\\d+) (\\d+) (\\d+) \\* \\* \\?";
      // System.out.print(regSecond);

        if(RegExpUtil.isMatch(cronExpression,regSecond)){
            List<Integer> groupNumber = Arrays.asList(new Integer[]{1});
            List<String> vesions=RegExpUtil.getValueByRegExp(cronExpression,regSecond,groupNumber);
            cronExpressionDesc="每隔"+vesions.get(0)+"秒执行一次";

        }else if(RegExpUtil.isMatch(cronExpression,regMinute)){
            List<Integer> groupNumber = Arrays.asList(new Integer[]{1});
            List<String> vesions=RegExpUtil.getValueByRegExp(cronExpression,regMinute,groupNumber);
            cronExpressionDesc="每隔"+vesions.get(0)+"分钟执行一次";

        }else if(RegExpUtil.isMatch(cronExpression,regHour)){
            List<Integer> groupNumber = Arrays.asList(new Integer[]{1});
            List<String> vesions=RegExpUtil.getValueByRegExp(cronExpression,regHour,groupNumber);
            cronExpressionDesc="每隔"+vesions.get(0)+"小时执行一次";
        }else if(RegExpUtil.isMatch(cronExpression,regTime)){
            List<Integer> groupNumber = Arrays.asList(new Integer[]{1,2,3});
            List<String> vesions=RegExpUtil.getValueByRegExp(cronExpression,regTime,groupNumber);
            cronExpressionDesc="时间为"+vesions.get(2)+":"+vesions.get(1)+":"+vesions.get(0)+"执行一次";
        }else{
            cronExpressionDesc=cronExpression;
        }
        return cronExpressionDesc;
    }
    public static String  getCronExpressionDesc(Long repeatInterval,String unit){
        String cronExpressionDesc="";
        try{
            if(StringUtils.isNotBlank(unit)){
                if(unit.equals("second")){
                    cronExpressionDesc= "每隔"+(repeatInterval/1000)+"秒执行一次";
                }else if(unit.equals("minute")){
                    cronExpressionDesc= "每隔"+(repeatInterval/60/1000)+"分钟执行一次";
                }else if(unit.equals("hour")){
                    cronExpressionDesc= "每隔"+(repeatInterval/60/60/1000)+"小时执行一次";
                }
            }else{
                cronExpressionDesc= "每隔"+(repeatInterval/1000)+"秒执行一次";
            }
        }catch (Exception e){
            cronExpressionDesc="";
        }
        return cronExpressionDesc;

    }
    public static void main(String[] args){
         String cronExpression="0 0 12 * * ?";
         System.out.println(CodeToChineseUtil.getCronExpressionDesc(cronExpression));
       }
}
