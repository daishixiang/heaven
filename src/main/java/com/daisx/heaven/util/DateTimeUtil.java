package com.daisx.heaven.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    public static final String DIR_PATTERN = "yyyy/MM/dd/";
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMES_PATTERN = "HH:mm:ss";
    public static final String NOCHAR_PATTERN = "yyyyMMddHHmmss";

    private static final String[] randomValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                                  "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                                                  "k", "l", "m", "n", "u", "t", "s", "o", "x", "v",
                                                  "p", "q", "r", "w", "y", "z" };

    /**
     * 日期转换为字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 转换为默认格式(yyyy-MM-dd)的日期字符串
     */
    public static String formatDefaultDate(Date date) {
        return formatDateByFormat(date, DEFAULT_PATTERN);
    }

    /**
     * 转换为目录格式(yyyy/MM/dd/)的日期字符串
     */
    public static String formatDirDate(Date date) {
        return formatDateByFormat(date, DIR_PATTERN);
    }

    /**
     * 转换为完整格式(yyyy-MM-dd HH:mm:ss)的日期字符串
     */
    public static String formatTimesTampDate(Date date) {
        return formatDateByFormat(date, TIMESTAMP_PATTERN);
    }

    /**
     * 转换为时分秒格式(HH:mm:ss)的日期字符串
     *
     */
    public static String formatTimesDate(Date date) {
        return formatDateByFormat(date, TIMES_PATTERN);
    }

    /**
     * 转换为时分秒格式(HH:mm:ss)的日期字符串
     */
    public static String formatNoCharDate(Date date) {
        return formatDateByFormat(date, NOCHAR_PATTERN);
    }

    /**
     * 日期格式字符串转换为日期对象
     */
    public static Date parseDate(String strDate, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date nowDate = format.parse(strDate);
            return nowDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换为默认格式(yyyy-MM-dd)日期对象
     */
    public static Date parseDefaultDate(String date) {
        return parseDate(date, DEFAULT_PATTERN);
    }

    /**
     * 字符串转换为完整格式(yyyy-MM-dd HH:mm:ss)日期对象
     */
    public static Date parseTimesTampDate(String date) {
        return parseDate(date, TIMESTAMP_PATTERN);
    }

    /**
     * 获得当前时间
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * sql Date 转 util Date
     */
    public static Date parseUtilDate(java.sql.Date date) {
        return date;
    }

    /**
     * util Date 转 sql Date
     */
    public static java.sql.Date parseSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获取年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取星期
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        return dayOfWeek;
    }

    /**
     * 获取日期(多少号)
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间(小时)
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间(分)
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间(秒)
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取当前毫秒
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 日期增加
     */
    public static Date addDate(Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减(返回天数)
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 日期相减(返回秒值)
     */
    public static Long diffDateTime(Date date, Date date1) {
        return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
    }


    /**
     * 生成length位随机数
     */
    public static String getRandomNumber(int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++) {
            Double number = Math.random() * (randomValues.length - 1);
            str.append(randomValues[number.intValue()]);
        }
        return str.toString();
    }


    /**
     * 生成流水号
     */
    public static String getSequenceNumber(int t) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(d);
        String hm = String.valueOf(System.nanoTime());
        str = str + hm.substring(hm.length() - 6);
        return str.substring(str.length() - t);
    }

}
