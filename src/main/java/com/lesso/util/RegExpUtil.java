package com.lesso.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/12.
 */
public class RegExpUtil {
    public static List<String> getValueByRegExp(String str, String regExpregExp, List<Integer> groupNumbers){
        List<String> matcherArray=new ArrayList<String>();
        if(str==null || str.equals("") ||str.trim().equals("")){
            return matcherArray;
        }
        if(regExpregExp==null || regExpregExp.equals("") ||regExpregExp.trim().equals("")){
            return matcherArray;
        }
        Pattern pattern = Pattern.compile(regExpregExp);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            for(Integer groupNumber:groupNumbers){
                matcherArray.add(matcher.group(groupNumber));
            }
        }
        return matcherArray;
    }

    public static boolean isMatch(String str, String regExpregExp){
        if(str==null || str.equals("") ||str.trim().equals("")){
            return false;
        }
        if(regExpregExp==null || regExpregExp.equals("") ||regExpregExp.trim().equals("")){
            return false;
        }
        Pattern pattern = Pattern.compile(regExpregExp);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }


}
