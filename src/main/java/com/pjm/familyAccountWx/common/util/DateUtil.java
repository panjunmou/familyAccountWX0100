package com.pjm.familyAccountWx.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 格式：年－月－日 时：分钟：秒
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式：年－月－日
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static Logger log = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 把日期转换为字符串
     */
    public static String dateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String dateStr = "";
        try {
            dateStr = formater.format(date);
        } catch (Exception e) {
            dateStr = "";
        }
        return dateStr;
    }

    /**
     * 把字符串转换为日期
     */
    public static Date stringToDate(String dateStr, String format) {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            log.error("The specified string cannot be parsed. " + e.getMessage());
        }
        return date;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenDay(Date date1, Date date2) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(date1);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(date2);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 获取两个日期之间的年数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenYear(Date date1, Date date2) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(date1);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(date2);
        int nowYear = d2.get(Calendar.YEAR);
        int dateYear = d1.get(Calendar.YEAR);
        return nowYear - dateYear;
    }

    /**
     * 获取年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取月
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        return month + 1;
    }

    /**
     * 获取日
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取时(24小时制)
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 获取分
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    /**
     * 获取秒
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int second = calendar.get(Calendar.SECOND);
        return second;
    }

    /**
     * 获取时分秒
     *
     * @return
     */
    public static String getHMS(Date date) {
        String hour = getHour(date) < 10 ? "0" + getHour(date) : getHour(date) + "";
        String minute = getMinute(date) < 10 ? "0" + getMinute(date) : getMinute(date) + "";
        String second = getSecond(date) < 10 ? "0" + getSecond(date) : getSecond(date) + "";
        String hms = hour + ":" + minute + ":" + second;
        return hms;
    }

    /**
     * 获取月份天数
     *
     * @param date
     * @return
     */
    public static int getMonthDay(Date date) throws ParseException {
        //方法1利用getActualMaximum
        Calendar calendar = Calendar.getInstance();
        //calendar设置时间
        calendar.setTime(date);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return actualMaximum;
    }

    /**
     * 获取某月第一天
     *
     * @param date
     * @return
     */
    public static String getMonthFirstStr(Date date) throws ParseException {
        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        String dateStr = year + "-" + (month < 10 ? "0" + month : month) + "-" + "01";
        return dateStr;
    }

    /**
     * 获取某月第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDate(Date date) throws ParseException {
        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        String dateStr = year + "-" + month + "-" + "01";
        Date toDate = DateUtil.stringToDate(dateStr, DateUtil.FORMAT_DATE);
        return toDate;
    }

    /**
     * 获取某月最后一天
     *
     * @param date
     * @return
     */
    public static String getMonthLastStr(Date date) throws ParseException {
        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        int day = DateUtil.getMonthDay(date);
        String dateStr = year + "-" + (month < 10 ? "0" + month : month) + "-" + day;
        return dateStr;
    }

    /**
     * 获取某月最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDate(Date date) throws ParseException {
        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        int day = DateUtil.getMonthDay(date);
        String dateStr = year + "-" + month + "-" + day;
        Date toDate = DateUtil.stringToDate(dateStr, DateUtil.FORMAT_DATE);
        return toDate;
    }
}
