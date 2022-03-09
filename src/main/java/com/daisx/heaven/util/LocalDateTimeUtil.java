package com.daisx.heaven.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 日期时间工具类
 * @Author: daisx
 * @Date: 2021/2/19 9:06
 */
public class LocalDateTimeUtil {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATETIME_FORMATTER =  DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 获取当前系统时间
     * @return
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前系统日期
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前系统日期时间
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前系统时间字符串
     * @return
     */
    public static String getLocalTimeString() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    /**
     * 获取当前系统日期字符串
     * @return
     */
    public static String getLocalDateString() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 获取当前系统日期时间字符串
     * @return
     */
    public static String getLocalDateTimeString() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * 字符串转LocalTime
     * @param time
     * @return
     */
    public static LocalTime  string2LocalTime(String time) {
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    /**
     * 字符串转LocalDate
     * @param date
     * @return
     */
    public static LocalDate  string2LocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * 字符串转LocalDateTime
     * @param dateTime
     * @return
     */
    public static LocalDateTime string2LocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATETIME_FORMATTER);
    }

    /**
     * Date转LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    /**
     * Date转LocalDate
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * Date转LocalDate
     * @param date
     * @return
     */
    public static LocalTime date2LocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime localTime = instant.atZone(zoneId).toLocalTime();
        return localTime;
    }

    /**
     * LocalDateTime转换为Date
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * Date转换为LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    /**
     * 获取指定日期的毫秒
     * @param time
     * @return
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒
     * @param time
     * @return
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式
     * @param time
     * @param pattern
     * @return
     */
    public static String formatTime(LocalDateTime time,String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式
     * @param pattern
     * @return
     */
    public static String formatNow(String pattern) {
        return  formatTime(LocalDateTime.now(), pattern);
    }

    /**
     * 日期加上一个数,根据field不同加不同值,field为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 日期减去一个数,根据field不同减不同值,field参数为ChronoUnit.*
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime minute(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     * @param startTime
     * @param endTime
     * @param field  单位(年月日时分秒)
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取一天的开始时间
     * @param time
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * 获取一天的结束时间
     * @param time
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

    /**
     * 获取指定日期的i个月初日期
     * @param localDate
     * @return
     */
    public static LocalDate getMonthStart(LocalDate localDate,int i){
        LocalDate date = LocalDate.now().plusMonths(i);
       return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取指定日期的i个月末日期
     * @param localDate
     * @return
     */
    public static LocalDate getMonthEnd(LocalDate localDate,int i){
        LocalDate date = LocalDate.now().plusMonths(i);
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }


    /**
     * 获取某一时间段所有日期为key,值为0
     * @param start
     * @param end
     * @return
     */
    public static Map<String,Integer> getBetweenDate(LocalDate start, LocalDate end) {
        Map<String,Integer> map = new HashMap<>();
        long distance = ChronoUnit.DAYS.between(start, end);
        if (distance < 1) {
            return map;
        }
        Stream.iterate(start, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> map.put(f.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),0));
        return map;
    }


    /**
     * 降序获取某一时间段所有日期
     */
    public static List<LocalDate>  getDescDateList(LocalDate startDate,LocalDate endDate) {
        List<LocalDate> result = new ArrayList<>();
        if(endDate.compareTo(startDate) < 0 ){
            return  result;
        }
        while (true){
            result.add(endDate);
            if(endDate.compareTo(startDate) <= 0){
                break;
            }
            endDate= endDate.plusDays(-1);
        }
        return result;
    }

    /**
     * 升序获取某一时间段所有日期
     */
    public static List<LocalDate> getAscDateList(LocalDate startDate,LocalDate endDate) {
        List<LocalDate> result = new ArrayList<>();
        if (endDate.compareTo(startDate) < 0) {
            return result;
        }
        while (true) {
            result.add(startDate);
            if (startDate.compareTo(endDate) >= 0) {
                break;
            }
            startDate = startDate.plusDays(1);
        }
        return result;
    }

    public static void printCalendar(LocalDate date){
        int month = date.getMonthValue();
        int nowDay = date.getDayOfMonth();

        //将date设置为当前月的第一天,往前d（day-1）天
        date = date.minusDays(nowDay - 1);
        int weekValue = date.getDayOfWeek().getValue();

        System.out.println("MonTueWedThuFriSatSun");
        for(int i=1 ; i<weekValue ; i++) {
            System.out.print("   ");
        }

        while(date.getMonthValue() == month){
            System.out.printf("%2d", date.getDayOfMonth());
            System.out.printf(date.getDayOfMonth() == nowDay ? "*" : " ");
            if(date.getDayOfWeek().getValue() == 7) {
                System.out.println();
            }
            date = date.plusDays(1);
        }
    }

    public static void main(String[] args) {
//        LocalDate localDate1 = LocalDate.now();
//        LocalDate localDate2 = LocalDate.now().plusDays(10);
//        Map<String, Integer> betweenDate = getBetweenDate(localDate1, localDate2);
//        Set<Map.Entry<String, Integer>> entries = betweenDate.entrySet();
//        for (Map.Entry<String, Integer> entry:entries){
//            System.out.println("key="+entry.getKey()+"   value="+entry.getValue());
//        }
//
//        List<LocalDate> ascDateList = getAscDateList(localDate1, localDate2);
//        List<LocalDate> descDateList = getDescDateList(localDate1, localDate2);
//        System.out.println("========升序=======");
//        for (LocalDate asc:ascDateList){
//            System.out.println(asc);
//        }
//        System.out.println("========降序=======");
//        for (LocalDate desc:descDateList){
//            System.out.println(desc);
////        }
//        LocalDate of = LocalDate.of(2021, 10, 31);
//        int dayOfMonth = LocalDate.now().getDayOfMonth();
//        System.out.println(of.getDayOfMonth());


        LocalDate now = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        int newHour = RandomUtil.randomInt(0, nowTime.getHour());
        LocalTime localTime = nowTime.withHour(newHour);

        LocalDateTime dateTime = now.atTime(localTime);
        System.out.println(dateTime);
    }
}
