package com.drpeng.worklog.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfY4_M_D = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat sdfY4MD = new SimpleDateFormat(
            "yyyyMMdd");

    private final static SimpleDateFormat sdfY4_M_D_TIME = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat sdfY4MDTIME = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    private static final SimpleDateFormat[] formaterArray = {sdfY4_M_D, sdfY4MD, sdfY4_M_D_TIME, sdfY4MDTIME};

    /**
     * @return
     * @title: getTimeMillis
     * @description: 获取时间戳
     * @author zhaoyo
     * @date 2015年10月5日 下午7:38:39
     */
    public static long getTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getSpecifiedDatetime(Date date) {
        return sdfY4_M_D.format(date);
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getY4_M_DDate() {
        return sdfY4_M_D.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getY4MDDate() {
        return sdfY4MD.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfY4_M_D_TIME.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTimeSpecified(Date date) {
        return sdfY4_M_D_TIME.format(date);
    }

    /**
     * @return
     * @title: getTimes
     * @description: 获取 yyyyMMddHHmmss 格式日期
     * @author zhaoyo
     * @date 2015年10月8日 上午11:30:40
     */
    public static String getY4MDTime() {
        return sdfY4MDTIME.format(new Date());
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description: (日期比较，如果s>=e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (formatDate(s) == null || formatDate(e) == null) {
            return false;
        }
        return formatDate(s).getTime() >= formatDate(e).getTime();
    }

    public static Date parseDate(String strDate) {
        for (DateFormat formatItem : formaterArray) {
            try {
                return formatItem.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static String formatDate(Date date, String format) {
        DateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);

    }

    public static Date formatDate(String date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static Date formatDate(String date, String format) {
        DateFormat fmt = new SimpleDateFormat(format);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidTime(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
                    startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        day = getDiffDays(beginDateStr, endDateStr, "yyyy-MM-dd");
        return day;
    }

    public static long getDiffDays(String beginDateStr, String endDateStr, String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDiffDays(endDate, beginDate);
    }

    public static long getDiffDays(Date endDate, Date beginDate) {
        return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    public static String getAfterDayDate(Date now, String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = new GregorianCalendar(); // java.util包
        canlendar.setTime(now);
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 得到当前年份.月份或者几号
     */
    public static int getCurrentYearMonthOrDay(String yearOrMonth) {
        Calendar cal = Calendar.getInstance();
        if (yearOrMonth.equals("year")) {
            return cal.get(Calendar.YEAR);
        }
        if (yearOrMonth.equals("month")) {
            return cal.get(Calendar.MONTH) + 1;
        }
        if (yearOrMonth.equals("day")) {
            return cal.get(Calendar.DATE);
        }
        return 0;
    }

    /**
     * 为查询日期添加最小时间
     * 比如2016-06-06或者2016-06-06 12:12:12 变成 2016-06-06 00:00:00
     */
    public static Date addStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 为查询日期添加最大时间
     * 比如2016-06-06或者2016-06-06 12:12:12 变成 2016-06-06 23:59:59
     */
    public static Date addEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MINUTE, 59);
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理区间的日期 2016/07/07 - 2016/07/07  解析成
     * startDate 2016-07-07
     * endDate 2016-07-07
     */
    public static Map<String, String> parseRangeDate(String rangeDate) {
        Map<String, String> dateInfo = new HashMap<String, String>();
        try {
            String[] dateInfoStr = rangeDate.split(" - ");
            if (null != dateInfoStr && dateInfoStr.length == 2) {
                dateInfo.put("startDate", dateInfoStr[0].replaceAll("/", "-"));
                dateInfo.put("endDate", dateInfoStr[1].replaceAll("/", "-"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateInfo;
    }

    /**
     * 改变 日期格式 比如:2016年06月13日 -> 2016-06-13
     *
     * @param date
     * @return
     */
    public static String changeavAilableFormate(String date) {
        try {
            date = date.replaceAll("年", "-");
            date = date.replaceAll("月", "-");
            date = date.replaceAll("日", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 把毫秒格式转换成日期格式
     * 返回的字符串形式是形如：2016-07-28 20:58:33 或者  2016-07-28
     */
    public static String formatDateInMinites(long timeInMillis, String formateStr) {
        String fmt = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timeInMillis);
            Date date = cal.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat(formateStr);
            fmt = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fmt;
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 计算日期和当前时间相差的天数
     *
     * @param date
     * @return
     */
    public static Integer daysBetween(Date date) {
        Integer result = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(sdf.format(date));
            Date now = sdf.parse(sdf.format(new Date()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            result = Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
/*
        String dateStr = "2016-07-05";
		String now = "2016-07-06";
		System.out.print(DateUtil.compareDate(now,dateStr));
		System.out.println(getY4MDDate());
		System.out.println(getAfterDayDate("5"));

		String date = "2016年06月13日";
		date = "2016-06-13";
		date = changeavAilableFormate(date);
		System.out.println("date====="+date);
		String aab= "1471795200000";
		Date date1 = new Date(Long.parseLong(aab));
		System.out.println(date1);
		//{"code":"IN10000","msg":"处理成功","data":{"phoneNum":"01067404131","remainSet":[{"itemCode":"FREE_INTL","itemName":"免费国际语音时长","itemValue":0,"expireDate":1474041600000},{"itemCode":"FREE_DATA","itemName":"免费流量资源","itemValue":0,"expireDate":1477497600000},{"itemCode":"REMIANS","itemName":"余额","itemValue":0,"expireDate":1470326400000}]}}
		String aa = DateUtil.formatDate(date1,"yyyy-MM-dd HH:mm:ss");
		System.out.println("aa====="+aa);

*/
       // System.out.print(DateUtil.daysBetween(DateUtil.parseDate("2017-03-21 06:00:00")));

        System.out.print(DateUtil.getDiffDays(DateUtil.parseDate("2016-10-20 00:00:00"), new Date()));


    }
}
