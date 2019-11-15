package cn.lianhy.demo.utils;

import cn.lianhy.demo.constant.DateConstant;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 作者：lianhy
 * 说明：
 * 1.org.apache.commons.lang3.time.DateUtils已提供多种日期的处理方法；
 * 2.根据个人项目经验，对经常涉及的日期的处理进行整理
 * 3.更多的日期处理持续更新中...
 */
public class DateExtendUtils {

    /**
     * 获取当天的日期 指定格式
     * @param format
     * @return
     */
    public String getString(String format) {
        return getString(new Date(),format);
    }
    /**
     * 获取当天时间 默认"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public String getString() {
        return getString(new Date(),DateConstant.PATTERN_YMDHMS);
    }

    /**
     * 日期格式化 指定日期 默认"yyyy-MM-dd HH:mm:ss"
     * @param dt
     * @return
     */
    public String getString(Date dt) {
        return getString(dt,DateConstant.PATTERN_YMDHMS);
    }

    /**
     * 日期格式化
     * @param dt
     * @param format
     * @return
     */
    public String getString(Date dt, String format) {
        try {
            if(StringUtils.isBlank(format)) {
                format= DateConstant.PATTERN_YMDHMS;
            }
            return new SimpleDateFormat(format).format(dt);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期字符串转为Date对象
     * @param time
     * @param pattern
     * @return
     */
    public Date getDate(String time, String pattern){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat(pattern);
            Date date=sdf.parse(time);
            return date;
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    // 获得某天最大时间
    public String getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return getString(Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant()));
    }

    // 获得某天最小时间
    public String getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return getString(Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant()));
    }

    // 获得某天最大时间
    public String getEndOfDay(String time) {
        Date date=getDate(time,DateConstant.PATTERN_YMDHMS);
        return getEndOfDay(date);
    }

    // 获得某天最小时间
    public String getStartOfDay(String time) {
        Date date=getDate(time,DateConstant.PATTERN_YMDHMS);
        return getStartOfDay(date);
    }

    // 获得当天最大时间
    public String getEndOfDay() {
        return getEndOfDay(new Date());
    }

    // 获得当天最小时间
    public String getStartOfDay() {
        return getStartOfDay(new Date());
    }

    /**
     * 说明
     * 1.d1>d2 return 1
     * 2.d1<d2 return -1
     * 3.d1=d2 return 0
     * @param d1
     * @param d2
     * @return
     */
    public int compareDate(Date d1,Date d2){
        if (d1.getTime() > d2.getTime()) {
            return 1;
        } else if (d1.getTime() < d2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 传入日期字符串 日期格式
     * @param t1
     * @param t2
     * @param pattern
     * @return
     */
    public int compareDate(String t1,String t2,String pattern){
        Date d1=getDate(t1,pattern);
        Date d2=getDate(t2,pattern);
        return compareDate(d1,d2);
    }

    /**
     * 获取时间戳 指定日期 格式 秒/毫秒
     * @param t1
     * @param pattern
     * @return
     */
    public long getTime(String t1,String pattern,int field){
        Calendar.getInstance();
        Date dt=getDate(t1,pattern);
        return getTime(dt,field);
    }

    /**
     * 获取时间戳 指定日期 秒/毫秒
     * @param dt
     * @param field
     * @return
     */
    public long getTime(Date dt,int field){
        if(DateConstant.INTERVAL_SECOND == field){
            return dt.getTime()/1000L;
        }else{
            return dt.getTime();
        }
    }

    /**
     * 获取时间戳 指定日期 格式 默认秒
     * @param t1
     * @param pattern
     * @return
     */
    public long getTime(String t1,String pattern){
        Calendar.getInstance();
        Date dt=getDate(t1,pattern);
        return getTime(dt,DateConstant.INTERVAL_SECOND);
    }

    private static class SingletonHolder {
        private final static DateExtendUtils INSTANT=new DateExtendUtils();
    }

    public static DateExtendUtils getInstance() {
        return SingletonHolder.INSTANT;
    }
}
