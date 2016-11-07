package com.lesso.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 0003 on 2016/7/18.
 */
public class DateUtil {

    /**
     * 获取当前日期与周一相差的天数
     * @return
     */
    public static int getMondayPlus(){
        Calendar day=Calendar.getInstance();
        int dayOfWeek=day.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek==1){ //一周中第一天（周日）
            return -6;
        }else{
            return 2-dayOfWeek;
        }
    }
    /**
     * 获得当天的起始时间
     * @return
     */
    public static Calendar getStartDate(Calendar today){
        Calendar td=Calendar.getInstance();
        td.set(Calendar.YEAR,today.get(Calendar.YEAR));
        td.set(Calendar.MONTH,today.get(Calendar.MONTH));
        td.set(Calendar.DAY_OF_MONTH,today.get(Calendar.DAY_OF_MONTH));
        td.set(Calendar.HOUR_OF_DAY,0);
        td.set(Calendar.MINUTE, 0);
        td.set(Calendar.SECOND, 0);
        td.set(Calendar.MILLISECOND, 0);
        return td;
    }
    /**
     * 获取当天截止时间
     * @return
     */
    public static Calendar getEndDate(Calendar endToday){
        Calendar td=Calendar.getInstance();
        td.set(Calendar.YEAR,endToday.get(Calendar.YEAR));
        td.set(Calendar.MONTH,endToday.get(Calendar.MONTH));
        td.set(Calendar.DAY_OF_MONTH,endToday.get(Calendar.DAY_OF_MONTH));
        td.set(Calendar.HOUR_OF_DAY, 23);
        td.set(Calendar.MINUTE, 59);
        td.set(Calendar.SECOND, 59);
        td.set(Calendar.MILLISECOND, 59);
        return td;
    }
    /**
     * 获得当月起始时间
     * @return
     */
    public static Calendar getStartMounth(Calendar today){
        Calendar calendar = getStartDate(today);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar;
    }
    /**
     * 获得当月结束时间
     * @return
     */
    public static Calendar getEndMounth(Calendar endToday){
        Calendar endMounth=getEndDate(endToday);
        endMounth.set(Calendar.DAY_OF_MONTH, endMounth.getActualMaximum(endMounth.DAY_OF_MONTH));
        return endMounth;
    }
    /**
     * 获取当前季度 起始时间
     * @return
     */
    public static Calendar getStartQuarter(Calendar today){
        int currentMonth = today.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                today.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                today.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                today.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                today.set(Calendar.MONTH, 9);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return today;
    }
    /**
     * 获取当季的结束时间
     */
    public static Calendar getEndQuarter(Calendar today){
        int currentMonth = today.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                today.set(Calendar.MONTH, 2);
                today.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                today.set(Calendar.MONTH, 5);
                today.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                today.set(Calendar.MONTH,8);
                today.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                today.set(Calendar.MONTH, 11);
                today.set(Calendar.DATE, 31);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return today;
    }
    /**
     * 获取当年起始时间
     */
    public static Calendar getStartYear(Calendar today){
        try {
            today.set(Calendar.MONTH, 0);
            today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return today;
    }
    /**
     * 获取当年结束时间
     */
    public static Calendar getEndYear(Calendar today){
        try {
            today.set(Calendar.MONTH, 11);
            today.set(Calendar.DAY_OF_MONTH, today.getMaximum(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return today;
    }


    /**
     * 获取  当前年、半年、季度、月、日、小时 开始结束时间
     */
    private static final SimpleDateFormat shortSdf =new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    private static final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public  static Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public  static Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本天的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public  static Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本天的结束时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public  static  Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的开始时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public static  Date getCurrentHourStartTime() {
        Date now = new Date();
        try {
            now = longHourSdf.parse(longHourSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的结束时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public  static Date getCurrentHourEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(longHourSdf.format(now) + ":59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本月的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public static  Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     *
     * @return
     */
    public  static Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public  static Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间，即2012-12-31 23:59:59
     *
     * @return
     */
    public  static Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public static  Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public   static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH,8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 获取前/后半年的开始时间
     * @return
     */
    public  static Date getHalfYearStartTime(){
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 0);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;

    }
    /**
     * 获取前/后半年的结束时间
     * @return
     */
    public static Date getHalfYearEndTime(){
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的前一天
     * @param date
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(Date date) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param date
     * @return
     */
    public static String getSpecifiedDayAfter(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(c.getTime());
        return dayAfter;
    }

    public static void main(String[]  args) throws ParseException {
        Calendar dayc1 =  Calendar.getInstance();
        Date dayStart =new Date();
        dayc1.setTime(dayStart);
        Calendar startCalendar=DateUtil.getStartMounth(dayc1);
        Date startDate =startCalendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastMonthDay = df.parse(DateUtil.getSpecifiedDayBefore(startDate));
        dayc1.setTime(lastMonthDay);
        startCalendar=DateUtil.getStartMounth(dayc1);
        Calendar endCalendar=DateUtil.getEndMounth(dayc1);
        Date startDateLastMonth =startCalendar.getTime();
        Date endDateLastMonth =endCalendar.getTime();
        System.out.println(startDateLastMonth.toString());
        System.out.println(endDateLastMonth.toString());
    }
}
