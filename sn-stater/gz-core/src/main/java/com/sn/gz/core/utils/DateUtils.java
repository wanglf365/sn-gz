/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DateUtils
 * Author:   luffy
 * Date:   2018/5/18 17:53
 * Since: 1.0.0
 */
package com.sn.gz.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @author luffy
 * @since 1.0.0
 * 2018/5/18
 */
@SuppressWarnings("unused")
public class DateUtils {
    public final static String NORMAL_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    public final static String MILLI_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String NUM_MILLI_DATE_FORMAT_STR = "yyyyMMddHHmmssSSS";

    public final static String SMS_DATE_FORMAT_STR = "yyyyMMddHHmmss";

    public final static String INTEGRAL_DATE_FORMAT_STR = "yyyy-MM-dd";

    public final static String YY_MM_DD = "yyMMdd";

    public final static String ZH_DATE_FORMAT_STR = "yyyy年MM月dd日";

    public final static String YEAR_MONTH_STR = "yyyy-MM";

    public final static String ZH_MONTH_STR = "MM月";

    public final static String YYYY = "yyyy";

    public final static String HH_MM = "HH:mm";

    public static List<String> dateOnly2StrList(List<Date> list) {
        if (ListUtils.isNull(list)) {
            return null;
        }
        List<String> dataList = new ArrayList<>();
        for (Date date : list) {
            dataList.add(dateOnly2Str(date));
        }
        return dataList;
    }

    /**
     * 图片文字识别日期转字符串
     *
     * @param date 日期
     * @return java.lang.String
     * @author Enma.ai
     * 2018/5/31
     * @since 1.0.0
     */
    public static String bizDate2Str(Date date) {
        String strDate;
        try {
            strDate = dateOnly2Str(date);
        } catch (Exception e) {
            return null;
        }
        return strDate;
    }

    /**
     * 时间转换-HH:mm
     *
     * @param date 待格式化日期
     * @return java.util.Date
     * @author xumiao
     * 2019/1/8
     */
    public static Date dateToHHmm(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HH_MM);
        String format = simpleDateFormat.format(date);
        try {
            return simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取今天日期
     *
     * @param date 日期
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 17:56
     * @since 1.0.0
     */
    public static Date getDateZeroTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        getDateZeroTime(calendar);
        return calendar.getTime();
    }

    /**
     * 获取今天最后时间
     *
     * @param date 日期
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 17:57
     * @since 1.0.0
     */
    public static Date getCriticalTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取今天开始时间
     *
     * @param calendar 日历
     * @return java.util.Calendar
     * @author luffy
     * 2018/5/18 17:57
     * @since 1.0.0
     */
    public static Calendar getDateZeroTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param ts Timestamp
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 17:58
     * @since 1.0.0
     */
    public static String getTimeStampStr(Timestamp ts) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (null != ts && !"".equals(ts)) {
                tsStr = sdf.format(ts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 转化
     *
     * @param date      Date
     * @param formatter SimpleDateFormat
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 17:59
     * @since 1.0.0
     */
    public static String date2Str(Date date, SimpleDateFormat formatter) {
        return formatter.format(date);
    }

    /**
     * 转化
     *
     * @param date    日期
     * @param pattern 模式字符串
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 17:59
     * @since 1.0.0
     */
    public static String date2Str(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 转化
     *
     * @param date 日期
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 17:59
     * @since 1.0.0
     */
    public static String date2Str(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(date);
    }

    /**
     * 转化
     *
     * @param time the milliseconds since January 1, 1970, 00:00:00 GMT
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:00
     * @since 1.0.0
     */
    public static Date getDateFromLong(long time) {
        return new Date(time);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date 日期
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:00
     * @since 1.0.0
     */
    public static String dateOnly2Str(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR).format(date);
    }

    /**
     * 获取开始时间
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:00
     * @since 1.0.0
     */
    public static String getDateStart() {
        Date date = new Date();

        String str = dateOnly2Str(date);

        return str + " " + "00:00:00";
    }

    /**
     * 获取结束时间
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:01
     * @since 1.0.0
     */
    public static String getDateEnd() {
        Date date = new Date();

        String str = dateOnly2Str(date);

        return str + " " + "23:59:59";
    }

    /**
     * 转化
     *
     * @param dateStr   日期字符串
     * @param formatter SimpleDateFormat
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:01
     * @since 1.0.0
     */
    public static Date str2Date(String dateStr, SimpleDateFormat formatter) throws ParseException {
        return formatter.parse(dateStr);
    }

    /**
     * 字符串转日期
     *
     * @param dateStr      日期字符串
     * @param formatterStr 格式字符串
     * @return java.util.Date
     * @author xiaoxueliang
     * 2018/5/20 09:22
     * @since 1.0.0
     */
    public static Date str2Date(String dateStr, String formatterStr) throws ParseException {
        SimpleDateFormat fm = new SimpleDateFormat(formatterStr);
        return fm.parse(dateStr);
    }

    /**
     * 转化
     *
     * @param dateStr 日期字符串
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:01
     * @since 1.0.0
     */
    public static Date str2Date(String dateStr) throws ParseException {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        return getDateByStrAndFormat(dateStr, NORMAL_DATE_FORMAT_STR);
    }

    /**
     * 根据格式返回日期
     *
     * @param str    字符串
     * @param format 格式
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:25
     * @since 1.0.0
     */
    public static Date getDateByStrAndFormat(String str, String format) throws ParseException {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(format)) {
            return null;
        }
        Date date;
        SimpleDateFormat sf = new SimpleDateFormat(format);
        date = sf.parse(str);
        return date;
    }

    /**
     * 转化
     *
     * @param dateStr 日期字符串
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:02
     * @since 1.0.0
     */
    public static Date str2DateYMD(String dateStr) throws ParseException {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        return getDateByStrAndFormat(dateStr, INTEGRAL_DATE_FORMAT_STR);
    }

    /**
     * 转化
     *
     * @param dateStr 日期字符串
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:03
     * @since 1.0.0
     */
    public static Date smsDateStr2Date(String dateStr) throws ParseException {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        return getDateByStrAndFormat(dateStr, INTEGRAL_DATE_FORMAT_STR);
    }

    /**
     * 当前时间
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:04
     * @since 1.0.0
     */
    public static String getCurrentTimeStr() {
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(new Date());
    }

    /**
     * 当前时间
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:05
     * @since 1.0.0
     */
    public static String getCurrentZhTimeStr() {
        return new SimpleDateFormat(ZH_DATE_FORMAT_STR).format(new Date());
    }

    /**
     * 当前时间
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:05
     * @since 1.0.0
     */
    public static String getYYMMDD() {
        return new SimpleDateFormat(YY_MM_DD).format(new Date());
    }

    /**
     * 得到当前时间，精确到毫秒 时间格式：yyyy-MM-dd HH:mm:ss.SSS
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:05
     * @since 1.0.0
     */
    public static String getCurrentMilliTimeStr() {
        return new SimpleDateFormat(MILLI_DATE_FORMAT_STR).format(new Date());
    }

    /**
     * 得到当前时间，精确到毫秒 时间格式：yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date 日期
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:05
     * @since 1.0.0
     */
    public static String getDateMilliTimeStr(Date date) {
        return new SimpleDateFormat(MILLI_DATE_FORMAT_STR).format(date);
    }

    /**
     * 得到当前时间，精确到毫秒 时间格式：yyyyMMddHHmmssSSS
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:06
     * @since 1.0.0
     */
    public static String getCurrentNumMilliTimeStr() {
        return new SimpleDateFormat(NUM_MILLI_DATE_FORMAT_STR).format(new Date());
    }

    /**
     * 转化
     *
     * @param date 日期
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:06
     * @since 1.0.0
     */
    public static String convertSqlDate2UtilDate(java.sql.Date date) {
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(new Date());
    }

    /**
     * @param strTimeZone 时区字符串
     * @param date        日期
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:07
     * @since 1.0.0
     */
    public static String convertGmtDateStr(String strTimeZone, Date date) {
        TimeZone timeZone;
        if (StringUtils.isEmpty(strTimeZone)) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = TimeZone.getTimeZone(strTimeZone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    /**
     * 获取给定分钟后的时间
     *
     * @param date   日期
     * @param minute 分钟数
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:07
     * @since 1.0.0
     */
    public static String getTimeAfter(Date date, int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cd.add(Calendar.MINUTE, minute);
        Date res = cd.getTime();

        return date2Str(res);
    }

    /**
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:08
     * @since 1.0.0
     */
    public static String getSMSTimeStr() {
        return new SimpleDateFormat(SMS_DATE_FORMAT_STR).format(new Date());
    }

    /**
     * @param dateStr 日期字符串
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:08
     * @since 1.0.0
     */
    public static String getOracleDateStr(String dateStr) {
        StringBuilder sb = new StringBuilder();
        sb.append("to_date('").append(dateStr).append("','yyyy-mm-dd hh24:mi:ss')");

        return sb.toString();
    }

    /**
     * 时间比较
     *
     * @param source 源日期
     * @param target 目标日期
     * @return int
     * @author luffy
     * 2018/5/18 18:08
     * @since 1.0.0
     */
    public static int compareTime(Date source, Date target) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(source);
        c2.setTime(target);

        return c1.compareTo(c2);
    }

    /**
     * 日期后指定天数的日期
     *
     * @param date 日期
     * @param day  天数
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:08
     * @since 1.0.0
     */
    public static String getTimeAfterDate(Date date, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cd.add(Calendar.DATE, day);
        Date res = cd.getTime();

        return date2Str(res);
    }

    /**
     * 获取给定分钟后的时间
     *
     * @param date   日期
     * @param minute 分钟数
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:08
     * @since 1.0.0
     */
    public static Date getTimeAfterMinute(Date date, int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cd.add(Calendar.MINUTE, minute);
        return cd.getTime();
    }

    /**
     * 获取给定秒后的时间
     *
     * @param date   日期
     * @param second 秒数
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:09
     * @since 1.0.0
     */
    public static Date getTimeAfterSecond(Date date, int second) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cd.add(Calendar.SECOND, second);
        return cd.getTime();
    }

    /**
     * 获取给定天数前的时间
     *
     * @param date 日期
     * @param day  天数
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:09
     * @since 1.0.0
     */
    public static String getTimeBeforeDate(Date date, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cd.add(Calendar.DATE, -day);
        Date res = cd.getTime();

        return date2Str(res);
    }

    /**
     * 返回当前的 yyyy-MM-dd
     *
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:09
     * @since 1.0.0
     */
    public static String getCurDate() {
        return dateOnly2Str(new Date());
    }

    /**
     * 返回多少天以前的 yyyy-MM-dd
     *
     * @param days 天数
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:09
     * @since 1.0.0
     */
    public static String getBeforeDay(int days) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        cd.add(Calendar.DAY_OF_MONTH, -days);
        return dateOnly2Str(cd.getTime());
    }

    /**
     * 返回多少月以前的 yyyy-MM-dd
     *
     * @param month 月数
     * @return java.lang.String
     * @author luffy
     * 2018/5/18 18:09
     * @since 1.0.0
     */
    public static String getBeforeMonth(int month) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        cd.add(Calendar.MONTH, -month);
        return dateOnly2Str(cd.getTime());
    }

    /**
     * 获取当前时间的年
     *
     * @return int
     * @author luffy
     * 2018/5/18 18:10
     * @since 1.0.0
     */
    public static int getCurYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月
     *
     * @return int
     * @author luffy
     * 2018/5/18 18:10
     * @since 1.0.0
     */
    public static int getCurMonth() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日
     *
     * @return int
     * @author luffy
     * 2018/5/18 18:10
     * @since 1.0.0
     */
    public static int getCurDay() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }

    /**
     * 通过时间戳返回日期
     *
     * @param timestamp 时间戳
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:10
     * @since 1.0.0
     */
    public static Date fromTimestamp(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * 获取今天0点日期
     *
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:11
     * @since 1.0.0
     */
    public static Date getTodayZeroDate() {
        return getDateZeroTime(new Date());
    }

    /**
     * 获取今天0点的时间戳
     *
     * @return long
     * @author luffy
     * 2018/5/18 18:11
     * @since 1.0.0
     */
    public static long getTodayZeroTimestamp() {
        return getTodayZeroDate().getTime();
    }

    /**
     * 获取0点的时间戳
     *
     * @param date 日期
     * @return long
     * @author luffy
     * 2018/5/18 18:11
     * @since 1.0.0
     */
    public static long getDateZeroTimestamp(Date date) {
        return getDateZeroTime(date).getTime();
    }

    /**
     * 获取0点的时间戳
     *
     * @param timestamp 时间戳
     * @return long
     * @author luffy
     * 2018/5/18 18:11
     * @since 1.0.0
     */
    public static long getDateZeroTimestamp(long timestamp) {
        return getDateZeroTime(new Date(timestamp)).getTime();
    }

    /**
     * 判断是否同天
     *
     * @param date  日期
     * @param date2 日期2
     * @return boolean
     * @author luffy
     * 2018/5/18 18:12
     * @since 1.0.0
     */
    public static boolean isSameDate(Date date, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
        );
    }

    /**
     * 判断是否同天
     *
     * @param date  日期
     * @param date2 日期2
     * @return boolean
     * @author luffy
     * 2018/5/18 18:12
     * @since 1.0.0
     */
    public static boolean isSameDate(long date, long date2) {
        return isSameDate(new Date(date), new Date(date2));
    }

    /**
     * 是否今天
     *
     * @param date 日期
     * @return boolean
     * @author luffy
     * 2018/5/18 18:12
     * @since 1.0.0
     */
    public static boolean isToday(Date date) {
        return isSameDate(date, new Date());
    }

    /**
     * 日期转换成yyyy-MM-dd格式¬
     *
     * @param dateTime 日期
     * @return java.util.Date
     * @author luffy
     * 2018/5/18 18:12
     * @since 1.0.0
     */
    public static Date DateTime2DateYMD(Date dateTime) {
        String dateStr = dateOnly2Str(dateTime);

        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }

        Date date = null;

        SimpleDateFormat sf = new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR);
        try {
            date = sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取指定日期下个月第一天
     *
     * @param date 指定日期
     * @return java.util.Date
     * @author Enma.ai
     * 2018/7/5
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取今天还剩下多少秒
     *
     * @return int
     * @author lufeiwang
     * 2018/12/18
     */
    public static int getRemainSecondsInToday() {
        Calendar curDate = Calendar.getInstance();
        Calendar tomorrowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int) (tomorrowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }

    /**
     * 获取指定日期的当前月份
     *
     * @param date 日期
     * @return int
     * @author muzi
     * 2019/1/3
     */
    public static String getYearMonthToStr(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat(YEAR_MONTH_STR).format(date);
    }

    /**
     * 获取给定月后的时间
     *
     * @param date  日期
     * @param month 月数
     * @return java.util.Date
     * @author muzi
     * 2019/1/4
     */
    public static String getYearMonthToStrAfterMonth(Date date, int month) {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();
        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cd.add(Calendar.MONTH, month);
        return sdf.format(cd.getTime());
    }

    /**
     * 获取两个指定时间的月份差
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return int
     * @author muzi
     * 2019/1/4
     */
    public static int getMonthDateDifference(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        try {
            bef.setTime(sdf.parse(sdf.format(startDate)));
            aft.setTime(sdf.parse(sdf.format(endDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH) + 1;
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 获取给定月后的时间
     *
     * @param date  日期
     * @param month 月数
     * @return java.util.Date
     * @author muzi
     * 2019/1/4
     */
    public static Date getYearMonthAfterMonth(Date date, int month) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.MONTH, month);
        return cd.getTime();
    }

    /**
     * 获取指定日期的当前月份
     *
     * @param date 日期
     * @return int
     * @author muzi
     * 2019/1/3
     */
    public static String getZHMonthStr(Date date) {
        return new SimpleDateFormat(ZH_MONTH_STR).format(date);
    }

    /**
     * 获取指定日期的年份
     *
     * @param date 日期
     * @return int
     * @author muzi
     * 2019/1/3
     */
    public static String getYearToStr(Date date) {
        return new SimpleDateFormat(YYYY).format(date);
    }

    /**
     * 获取指定日期的年份
     *
     * @param date 日期
     * @return Date
     * @author muzi
     * 2019/1/7
     */
    public static Date getLastMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取指定日期的前一天
     *
     * @param date 日期
     * @return Date
     * @author muzi
     * 2019/1/7
     */
    public static Date getSameDateYesterday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取上个月的第一天
     *
     * @return Date
     * @author muzi
     * 2019/1/7
     */
    public static Date getLastMonthFirstDay() {
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, -1);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR,0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND,0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTime();
    }

    /**
     * 获取当前月的第一天
     *
     * @return Date
     * @author muzi
     * 2019/1/25
     */
    public static Date getThisMonthFirstDay() {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR,0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND,0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getThisMonthLastDay());
    }
    /**
     * 获取这个月的最后一天
     *
     * @return Date
     * @author muzi
     * 2019/1/7
     */
    public static Date getThisMonthLastDay() {
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, 0);
        c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));
        c1.set(Calendar.HOUR_OF_DAY,23);
        c1.set(Calendar.MINUTE, 59);
        c1.set(Calendar.SECOND,59);
        c1.set(Calendar.MILLISECOND, 59);
        return c1.getTime();
    }

    /**
     * 和上个月比是不是同一个月
     *
     * @return Date
     * @author muzi
     * 2019/1/8
     */
    public static boolean isSameMonthAsLastMonth(String date) {
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar1.setTime(str2Date(date, INTEGRAL_DATE_FORMAT_STR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(getLastMonthFirstDay());

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
    }

    /**
     * 获取这个月比是不是同一个月
     *
     * @return Date
     * @author muzi
     * 2019/1/8
     */
    public static boolean isSameMonthAsThisMonth(String date) {
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar1.setTime(str2Date(date, INTEGRAL_DATE_FORMAT_STR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(getThisMonthLastDay());

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
    }

    /**
     * 获取两个指定时间的天数差
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return int
     * @author muzi
     * 2019/1/9
     */
    public static int getDayDateDifference(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int day = 0;
        try {
            day = (int) ((sdf.parse(sdf.format(endDate)).getTime() - sdf.parse(sdf.format(startDate)).getTime()) / (24 * 60 * 60 * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 指定日期后指定天数的日期
     *
     * @param date 日期
     * @param day  天数
     * @return date
     * @author muzi
     * 2019/1/9
     */
    public static Date getDayDateAfterDay(Date date, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();
        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cd.add(Calendar.DATE, day);
        return cd.getTime();
    }

    /**
     * 时间转换-HH:mm
     *
     * @param date 待格式化日期
     * @return java.util.Date
     * @author muzi
     * 2019/1/8
     */
    public static String getDateHHmmToStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HH_MM);
        return simpleDateFormat.format(date);
    }
}
