package com.we.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * Description: 系统常用工具方法类。
 */
public class Tools {

    private final static Logger log = Logger.getLogger(Tools.class);

    /**
     * 得到按指定格式的系统当前时间
     *
     * @param dateFormat 日期字符串格式. eg:"yyyy-MM-dd HH:mm:ss"
     * @return 格式化的日期字符串. eg:"yyyy-MM-dd HH:mm:ss"
     */
    public static String getSysDate(String dateFormat) {
        if (dateFormat == null || "".equals(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date.getTime());
    }

    /**
     * 根据用户的请求获取用户的host
     *
     * @param request
     * @return
     */
    public static String getHost(final HttpServletRequest request) {
        return request.getScheme();
    }

    /**
     * 得到当前系统时间毫秒数作为上载文件的文件名
     *
     * @return 上载文件名
     */
    public static String getFileName() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 得到系统日期(当前服务器时间)
     *
     * @return 当前系统日期
     */
    public static Date getSysDate() {
        return new Date();
    }

    /**
     * 得到当前日期前后相差i天的日期
     *
     * @param i 与当前日期相差的天数
     * @return 日期
     */
    public static Date getBeforeOrBehindDateofCurrentDate(final int i) {
        GregorianCalendar g = new GregorianCalendar();
        g.add(Calendar.DATE, i);
        return g.getTime();
    }

    /**
     * 根据COOKIE名获取COOKIE值。
     *
     * @param request
     * @param name
     * @return value, 不存在此名称的COOKIE时返回NULL
     */
    public static String getCookieValueByName(final HttpServletRequest request, final String name) {
        if (null == request || null == name || "".equals(name)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        Cookie cookie = null;
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (name.equals(cookie.getName())) {
                try {
                    return java.net.URLDecoder.decode(cookie.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    log.error("****** getCookieValueByName name:" + name + " value:"
                            + cookie.getValue() + " error:" + e + " **********");
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * 删除客户端的名称为name的Cookie。
     *
     * @param request
     * @param response
     * @param name
     * @return true:删除成功；false:删除失败。
     */
    public static boolean delCookieByName(HttpServletRequest request, HttpServletResponse response,
                                          final String name) {
        boolean del = false;
        if (null == request || null == response || null == name || "".equals(name)) {
            return del;
        } else {
            Cookie[] cookies = request.getCookies();
            Cookie cookie = null;
            if (cookies == null || cookies.length == 0) {
                return del;
            } else {
                // 删除非加密串中的cookie
                for (int i = 0; i < cookies.length; i++) {
                    cookie = cookies[i];
                    if (name.equals(cookie.getName())) {
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        cookie.setDomain(request.getServerName());
                        response.addCookie(cookie);
                        del = true;
                    }
                }
            }
            return del;
        }
    }

    /**
     * 添加Cookie至用户客户端。
     *
     * @param request
     * @param response
     * @param name     Cookie名
     * @param value    值
     * @param age      生存周期
     * @return true：添加成功，false：添加失败。
     */
    public static boolean setCookie(final HttpServletRequest request,
                                    final HttpServletResponse response, final String name, final String value, final int age) {
        boolean set = false;
        if (null == request || null == response || null == name || "".equals(name) || null == value) {
            return set;
        }
        // 非加密cookie处理。
        Cookie ck = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    ck = cookie;
                }
            }
        }
        try {
            if (null == ck) {
                ck = new Cookie(name, java.net.URLEncoder.encode(value, "UTF-8"));
            } else {
                ck.setValue(java.net.URLEncoder.encode(value, "UTF-8"));
            }
            ck.setMaxAge(age);
            ck.setPath("/");
            ck.setDomain(request.getServerName());
            response.addCookie(ck);
            set = true;
        } catch (Exception e) {
            log.error("cookie set name:" + name + ", value:" + value + ", error: " + e);
        }
        return set;
    }

    /**
     * 得到系统文件分隔符
     *
     * @return 系统文件分隔符
     */
    public static String getOSFileSeparator() {
        return "/".equals(System.getProperty("file.separator")) ? "/" : "\\\\";
    }

    /**
     * 获取给定日期对象的年
     *
     * @param date 日期对象
     * @return 年
     */
    public static int getYear(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取给定日期对象的月
     *
     * @param date 日期对象
     * @return 月
     */
    public static int getMonth(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取给定日期对象的天
     *
     * @param date 日期对象
     * @return 天
     */
    public static int getDay(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    /**
     * 获取给定日期对象的时
     *
     * @param date 日期对象
     * @return 时
     */
    public static int getHour(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取给定日期对象的分
     *
     * @param date 日期对象
     * @return 分
     */
    public static int getMinute(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取给定日期对象的秒
     *
     * @param date 日期对象
     * @return 秒
     */
    public static int getSecond(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 获取传入日期的年和月的Integer形式（yyyyMM）。
     *
     * @param date 要转换的日期对象
     * @return 转换后的Integer对象
     */
    public static Integer getYearMonth(final Date date) {
        return Integer.valueOf(format(date, "yyyyMM"));
    }

    /**
     * 将年月的整数形式（yyyyMM）转换为日期对象返回。
     *
     * @param yearMonth 年月的整数形式（yyyyMM）
     * @return 日期类型
     * @throws ParseException
     */
    public static Date parseYearMonth(final Integer yearMonth) throws ParseException {
        return parse(String.valueOf(yearMonth), "yyyyMM");
    }

    /**
     * 将某个日期增加指定年数，并返回结果。如果传入负数，则为减。
     *
     * @param date    要操作的日期对象
     * @param ammount 要增加年的数目
     * @return 结果日期对象
     */
    public static Date addYear(final Date date, final int ammount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, ammount);
        return c.getTime();
    }

    /**
     * 将某个日期增加指定月数，并返回结果。如果传入负数，则为减。
     *
     * @param date    要操作的日期对象
     * @param ammount 要增加月的数目
     * @return 结果日期对象
     */
    public static Date addMonth(final Date date, final int ammount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, ammount);
        return c.getTime();
    }

    /**
     * 将某个日期增加指定小时，并返回结果。如果传入负数，则为减。
     *
     * @param date
     * @param ammount
     * @return
     */
    public static Date addHour(final Date date, final int ammount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, ammount);
        return c.getTime();
    }

    /**
     * 将某个日期增加指定分钟，并返回结果。如果传入负数，则为减。
     *
     * @param date
     * @param ammount
     * @return
     */
    public static Date addMinute(final Date date, final int ammount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, ammount);
        return c.getTime();
    }

    /**
     * 将某个日期增加指定天数，并返回结果。如果传入负数，则为减。
     *
     * @param date    要操作的日期对象
     * @param ammount 要增加天的数目
     * @return 结果日期对象
     */
    public static Date addDay(final Date date, final int ammount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, ammount);
        return c.getTime();
    }

    /**
     * 将给定整数形式的年月增加指定月数，并返回结果。如果传入负数，则为减。
     *
     * @param yearMonth 要操作的年月
     * @param ammount   要增加的月数
     * @return 结果年月
     * @throws ParseException
     */
    public static Integer addMonth(final Integer yearMonth, final int ammount)
            throws ParseException {
        return getYearMonth(addMonth(parseYearMonth(yearMonth), ammount));
    }

    /**
     * 返回给定的beforeDate比afterDate早的年数。如果beforeDate晚于afterDate，则 返回负数。
     *
     * @param beforeDate 要比较的早的日期
     * @param afterDate  要比较的晚的日期
     * @return beforeDate比afterDate早的年数，负数表示晚。
     */
    public static int beforeYears(final Date beforeDate, final Date afterDate) {
        Calendar beforeCalendar = Calendar.getInstance();
        beforeCalendar.setTime(beforeDate);
        beforeCalendar.set(Calendar.MONTH, 1);
        beforeCalendar.set(Calendar.DATE, 1);
        beforeCalendar.set(Calendar.HOUR, 0);
        beforeCalendar.set(Calendar.SECOND, 0);
        beforeCalendar.set(Calendar.MINUTE, 0);
        Calendar afterCalendar = Calendar.getInstance();
        afterCalendar.setTime(afterDate);
        afterCalendar.set(Calendar.MONTH, 1);
        afterCalendar.set(Calendar.DATE, 1);
        afterCalendar.set(Calendar.HOUR, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        afterCalendar.set(Calendar.MINUTE, 0);
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
        }
        int beforeYears = 0;
        while (true) {
            boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar
                    .get(Calendar.YEAR);
            if (yearEqual) {
                break;
            } else {
                if (positive) {
                    beforeYears++;
                    beforeCalendar.add(Calendar.YEAR, 1);
                } else {
                    beforeYears--;
                    beforeCalendar.add(Calendar.YEAR, -1);
                }
            }
        }
        return beforeYears;
    }

    /**
     * 返回给定的beforeDate比afterDate早的月数。如果beforeDate晚于afterDate，则 返回负数。
     *
     * @param beforeDate 要比较的早的日期
     * @param afterDate  要比较的晚的日期
     * @return beforeDate比afterDate早的月数，负数表示晚。
     */
    public static int beforeMonths(final Date beforeDate, final Date afterDate) {
        Calendar beforeCalendar = Calendar.getInstance();
        beforeCalendar.setTime(beforeDate);
        beforeCalendar.set(Calendar.DATE, 1);
        beforeCalendar.set(Calendar.HOUR, 0);
        beforeCalendar.set(Calendar.SECOND, 0);
        beforeCalendar.set(Calendar.MINUTE, 0);
        Calendar afterCalendar = Calendar.getInstance();
        afterCalendar.setTime(afterDate);
        afterCalendar.set(Calendar.DATE, 1);
        afterCalendar.set(Calendar.HOUR, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        afterCalendar.set(Calendar.MINUTE, 0);
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
        }
        int beforeMonths = 0;
        while (true) {
            boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar
                    .get(Calendar.YEAR);
            boolean monthEqual = beforeCalendar.get(Calendar.MONTH) == afterCalendar
                    .get(Calendar.MONTH);
            if (yearEqual && monthEqual) {
                break;
            } else {
                if (positive) {
                    beforeMonths++;
                    beforeCalendar.add(Calendar.MONTH, 1);
                } else {
                    beforeMonths--;
                    beforeCalendar.add(Calendar.MONTH, -1);
                }
            }
        }
        return beforeMonths;
    }

    /**
     * 返回给定的beforeDate比afterDate早的天数。如果beforeDate晚于afterDate，则 返回负数。
     *
     * @param beforeDate 要比较的早的日期
     * @param afterDate  要比较的晚的日期
     * @return beforeDate比afterDate早的天数，负数表示晚。
     */
    public static int beforeDays(final Date beforeDate, final Date afterDate) {
        Calendar beforeCalendar = Calendar.getInstance();
        beforeCalendar.setTime(beforeDate);
        beforeCalendar.set(Calendar.HOUR, 0);
        beforeCalendar.set(Calendar.SECOND, 0);
        beforeCalendar.set(Calendar.MINUTE, 0);
        Calendar afterCalendar = Calendar.getInstance();
        afterCalendar.setTime(afterDate);
        afterCalendar.set(Calendar.HOUR, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        afterCalendar.set(Calendar.MINUTE, 0);
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
        }
        int beforeDays = 0;
        while (true) {
            boolean yearEqual = beforeCalendar.get(Calendar.YEAR) == afterCalendar
                    .get(Calendar.YEAR);
            boolean monthEqual = beforeCalendar.get(Calendar.MONTH) == afterCalendar
                    .get(Calendar.MONTH);
            boolean dayEqual = beforeCalendar.get(Calendar.DATE) == afterCalendar
                    .get(Calendar.DATE);
            if (yearEqual && monthEqual && dayEqual) {
                break;
            } else {
                if (positive) {
                    beforeDays++;
                    beforeCalendar.add(Calendar.DATE, 1);
                } else {
                    beforeDays--;
                    beforeCalendar.add(Calendar.DATE, -1);
                }
            }
        }
        return beforeDays;
    }

    /**
     * 获取beforeDate和afterDate之间相差的完整年数，精确到天。负数表示晚。
     *
     * @param beforeDate 要比较的早的日期
     * @param afterDate  要比较的晚的日期
     * @return beforeDate比afterDate早的完整年数，负数表示晚。
     */
    public static int beforeRoundYears(final Date beforeDate, final Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
            bDate = afterDate;
            aDate = beforeDate;
        }
        int beforeYears = beforeYears(bDate, aDate);

        int bMonth = getMonth(bDate);
        int aMonth = getMonth(aDate);
        if (aMonth < bMonth) {
            beforeYears--;
        } else if (aMonth == bMonth) {
            int bDay = getDay(bDate);
            int aDay = getDay(aDate);
            if (aDay < bDay) {
                beforeYears--;
            }
        }

        if (positive) {
            return beforeYears;
        } else {
            return new BigDecimal(beforeYears).negate().intValue();
        }
    }

    /**
     * 获取beforeDate和afterDate之间相差的完整年数，精确到月。负数表示晚。
     *
     * @param beforeDate 要比较的早的日期
     * @param afterDate  要比较的晚的日期
     * @return beforeDate比afterDate早的完整年数，负数表示晚。
     */
    public static int beforeRoundAges(final Date beforeDate, final Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
            bDate = afterDate;
            aDate = beforeDate;
        }
        int beforeYears = beforeYears(bDate, aDate);

        int bMonth = getMonth(bDate);
        int aMonth = getMonth(aDate);
        if (aMonth < bMonth) {
            beforeYears--;
        }

        if (positive) {
            return beforeYears;
        } else {
            return new BigDecimal(beforeYears).negate().intValue();
        }
    }

    /**
     * 获取beforeDate和afterDate之间相差的完整月数，精确到天。负数表示晚。
     *
     * @param beforeDate 要比较的早的日期
     * @param afterDate  要比较的晚的日期
     * @return beforeDate比afterDate早的完整月数，负数表示晚。
     */
    public static int beforeRoundMonths(final Date beforeDate, final Date afterDate) {
        Date bDate = beforeDate;
        Date aDate = afterDate;
        boolean positive = true;
        if (beforeDate.after(afterDate)) {
            positive = false;
            bDate = afterDate;
            aDate = beforeDate;
        }
        int beforeMonths = beforeMonths(bDate, aDate);

        int bDay = getDay(bDate);
        int aDay = getDay(aDate);
        if (aDay < bDay) {
            beforeMonths--;
        }

        if (positive) {
            return beforeMonths;
        } else {
            return new BigDecimal(beforeMonths).negate().intValue();
        }
    }

    /**
     * 根据传入的年、月、日构造日期对象
     *
     * @param year  年
     * @param month 月
     * @param date  日
     * @return 返回根据传入的年、月、日构造的日期对象
     */
    public static Date getDate(final int year, final int month, final int date) {
        Calendar c = Calendar.getInstance();
        c.set(year + 1900, month, date);
        return c.getTime();
    }

    /**
     * 根据传入的日期格式化pattern将传入的日期格式化成字符串。
     *
     * @param date    要格式化的日期对象
     * @param pattern 日期格式化pattern
     * @return 格式化后的日期字符串
     */
    public static String format(final Date date, final String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 将传入的日期按照默认形势转换成字符串（yyyy-MM-dd）
     *
     * @param date 要格式化的日期对象
     * @return 格式化后的日期字符串
     */
    public static String format(final Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 根据传入的日期格式化patter将传入的字符串转换成日期对象
     *
     * @param dateStr 要转换的字符串
     * @param pattern 日期格式化pattern
     * @return 转换后的日期对象
     * @throws ParseException 如果传入的字符串格式不合法
     */
    public static Date parse(final String dateStr, final String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateStr);
    }

    /**
     * 将传入的字符串按照默认格式转换为日期对象（yyyy-MM-dd）
     *
     * @param dateStr 要转换的字符串
     * @return 转换后的日期对象
     * @throws ParseException 如果传入的字符串格式不符合默认格式（如果传入的字符串格式不合法）
     */
    public static Date parse(final String dateStr) throws ParseException {
        return parse(dateStr, "yyyy-MM-dd");
    }

    /**
     * 要进行合法性验证的年月数值
     *
     * @param yearMonth 验证年月数值
     * @return 年月是否合法
     */
    public static boolean isYearMonth(final Integer yearMonth) {
        String yearMonthStr = yearMonth.toString();
        return isYearMonth(yearMonthStr);
    }

    /**
     * 要进行合法性验证的年月字符串
     *
     * @param yearMonthStr 验证年月字符串
     * @return 年月是否合法
     */
    public static boolean isYearMonth(final String yearMonthStr) {
        if (yearMonthStr.length() != 6) {
            return false;
        } else {
            String yearStr = yearMonthStr.substring(0, 4);
            String monthStr = yearMonthStr.substring(4, 6);
            try {
                int year = Integer.parseInt(yearStr);
                int month = Integer.parseInt(monthStr);
                if (year < 1800 || year > 3000) {
                    return false;
                }
                if (month < 1 || month > 12) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 获取从from到to的年月Integer形式值的列表
     *
     * @param from 从
     * @param to   到
     * @return 年月Integer形式值列表
     * @throws ParseException
     */
    public static List getYearMonths(Integer from, Integer to) throws ParseException {
        List<Integer> yearMonths = new ArrayList<Integer>();
        Date fromDate = parseYearMonth(from);
        Date toDate = parseYearMonth(to);
        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("'from' date should before 'to' date!");
        }
        Date tempDate = fromDate;
        while (tempDate.before(toDate)) {
            yearMonths.add(getYearMonth(tempDate));
            tempDate = addMonth(tempDate, 1);
        }
        if (!from.equals(to)) {
            yearMonths.add(to);
        }

        return yearMonths;
    }

    /**
     * 取得随机字串联
     *
     * @param length
     * @return 指定长度的随机字串
     */
    public static String getRandomString(int length) {
        length = length > 0 ? length : 6;
        char[] randchars = new char[length];
        // 不包括0,O,o,l,1
        String randstring = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghgkmnpqrstuvwxyz";
        Random rd = new Random();
        // return String.valueOf(randstring.charAt(num));
        for (int i = 0; i < length; i++) {
            randchars[i] = randstring.charAt(rd.nextInt(55));
        }
        return new String(randchars);
    }

    /**
     * 判断字串是否为空，包括NULL，“”，0
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if ("".equals(s)) {
            return true;
        }
        if (s == null) {
            return true;
        }
        if ("0".equals(s)) {
            return true;
        }
        return false;
    }

    /**
     * 将十进制数转化为二进制数
     *
     * @param x
     * @return s
     */
    public static String decimalToBinary(String x) {

        if (x == null || x.equals("")) {
            x = "0";
        }
        String[] str = {"0", "0", "0", "0", "0"};
        int a = 0;
        String s = "";
        int i = Integer.parseInt(x);
        for (a = 0; a < str.length; a++) {

            int j = i % 2;
            if (j == 1) {
                str[4 - a] = "1";
                i = (i - 1) / 2;
                // System.out.println("------str[4]--------" + str[4]);
            } else {
                i = i / 2;
            }
        }
        for (a = 0; a < str.length; a++) {
            s += str[a];
        }
        return s;
    }


    /**
     * 将指定字符串中的html标签过滤替换
     *
     * @param str
     * @return
     */
    public static String htmlConverter(final String str) {
        String tempStr = str;
        if (null != tempStr && !"".equals(tempStr)) {
            tempStr = tempStr.replace("&", "&amp;");
            tempStr = tempStr.replace(">", "&gt;");
            tempStr = tempStr.replace("<", "&lt;");
            tempStr = tempStr.replace("\"", "&quot;");
            tempStr = tempStr.replace("‘", "&apos;");
        }
        return tempStr;
    }

    /**
     * 将指定字符串中的html标签反过滤替换
     *
     * @param str
     * @return
     */
    public static String htmlConverterReverse(final String str) {
        String tempStr = str;
        if (null != tempStr && !"".equals(tempStr)) {
            tempStr = tempStr.replace("&gt;", ">");
            tempStr = tempStr.replace("&lt;", "<");
            tempStr = tempStr.replace("&quot;", "\"");
            tempStr = tempStr.replace("&apos;", "'");
            tempStr = tempStr.replace("&nbsp;", " ");
            tempStr = tempStr.replace("&amp;", "&");

        }
        return tempStr;
    }

    /**
     * 将指定字符串中的html标签过滤替换
     *
     * @param str
     * @return
     */
    public static String htmlConverterReverse1(final String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        StringBuffer buffer = new StringBuffer();
        StringTokenizer token = new StringTokenizer(str, "\r\n");
        while (token.hasMoreTokens()) {
            String temp = token.nextToken();
            buffer.append(convert2(temp));
        }
        return buffer.toString();
    }

    private static String convert2(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        StringBuffer buf = new StringBuffer(input.length() + 6);
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '&') {
                buf.append("&amp;");
            } else if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else if (ch == ' ') {
                buf.append("&nbsp;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 根据图片url获得相应的大图、中图、小图路径 1为大图，2为中图，3为小图，其他情况下返回原图地址
     *
     * @param url
     * @param size
     * @return
     */
    public static String getImageUrl_Old(String url, int size) {
        String suffix;
        int length = url.length();
        int dot = 0;
        // System.out.println(url.charAt(length - 4));
        if (url.charAt(length - 5) == '.') {
            dot = length - 5;
        } else {
            dot = length - 4;
        }

        switch (size) {
            case 1:
                suffix = "_l";
                break;
            case 2:
                suffix = "_m";
                break;
            case 3:
                suffix = "_s";
                break;
            default:
                suffix = "";
                break;
        }
        suffix = "";
        return url.substring(0, dot) + suffix + url.substring(dot);
    }

    /**
     * 根据图片url获得相应的大图、中图、小图路径 1为188*188图，2为 136*136图，3为80*80图，4为420*420图
     * 其他情况下返回原图地址
     *
     * @param url
     * @param size
     * @return
     */
    public static String getImageUrl2(String url, int size) {
        String suffix;
        int length = url.length();
        int dot = 0;
        // System.out.println(url.charAt(length - 4));
        if (url.charAt(length - 5) == '.') {
            dot = length - 5;
        } else {
            dot = length - 4;
        }

        switch (size) {
            case 1:
                suffix = "_l";
                break;
            case 2:
                suffix = "_m";
                break;
            case 3:
                suffix = "_s";
                break;
            case 4:
                suffix = "_x";
                break;
            default:
                suffix = "";
                break;
        }
        return url.substring(0, dot) + suffix + url.substring(dot);
    }

    /**
     * 根据图片url获得相应的大图、中图、小图路径 1为188*188图，2为 136*136图，3为80*80图，4为420*420图
     * 其他情况下返回原图地址
     *
     * @param url
     * @param size
     * @return
     */
    public static String getImageUrl(String url, int size) {
        String suffix;
        int length = url.length();
        int dot = 0;

        if (url.indexOf("eachnet") == -1) {
            return url;
        }

        if (url.charAt(length - 5) == '.') {
            dot = length - 5;
        } else {
            dot = length - 4;
        }

        switch (size) {
            case 1:
                suffix = "_l";
                break;
            case 2:
                suffix = "_m";
                break;
            case 3:
                suffix = "_s";
                break;
            case 4:
                suffix = "_x";
                break;
            default:
                suffix = "";
                break;
        }
        return url.substring(0, dot) + suffix + url.substring(dot);
    }

    private static String getValueByName(final String str, final String name) {
        if (null == str || "".equals(str) || null == name || "".equals(name)) {
            return null;
        }
//        System.out.println("str:" + str);
//        System.out.println("name:" + name);
        log.debug("******** getValueByName str:" + str + " name:" + name + " ************");
        String[] s = str.split(";");
        if (null == s || s.length == 0) {
            return null;
        }
        String n = null;
        String v = null;
        String a = null;
        for (int i = 0; i < s.length; i++) {
            a = s[i];
            // System.out.println(a);
            n = a.substring(0, a.indexOf('='));
            // System.out.println("name:" + name);
            v = a.substring(a.indexOf('=') + 1, a.length());
            // System.out.println("value:" + value);
            if (name.equals(n)) {
                return v;
            }
        }
        return null;
    }

    private static String setValueByName(final String string, final String name, final String value) {
        log.debug("******** setValueByName str:" + string + " name:" + name + " value:" + value
                + " ************");
//        System.out.println("******** setValueByName str:" + string + " name:" + name + " value:"
//                + value + " ************");
        if (null != name && !"".equals(name) && null != value && !"".equals(value)) {
            String temp = null;
            temp = string;
            String s = name + "=" + value + ";";
            if (null == temp || "".equals(temp)) {
                temp = s;
            } else {
                if (temp.endsWith(";")) {
                    temp = temp + s;
                } else {
                    temp = ";" + s;
                }
            }
            return temp;
        }
        return null;
    }

    private static String delValueByName(final String str, final String name) {
        log.debug("******** delValueByName str:" + str + " name:" + name + " ************");
        if (null == str || "".equals(str) || null == name || "".equals(name)) {
            return str;
        }
        String[] s = str.split(";");
        if (null == s || s.length == 0) {
            return str;
        }
        String n = null;
        String v = null;
        String a = null;
        String temp = null;
        for (int i = 0; i < s.length; i++) {
            a = s[i];
            // System.out.println(a);
            n = a.substring(0, a.indexOf('='));
            // System.out.println("name:" + name);
            v = a.substring(a.indexOf('=') + 1, a.length());
            // System.out.println("value:" + value);
            if (name.equals(n)) {
                if (str.endsWith(";")) {
                    temp = str.replace(a + ";", "");
                } else {
                    temp = str.replace(a, "");
                }
                return temp;
            }
        }
        return str;
    }

    public final static String getIpAddr(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            if (ip.contains(",")) {
                ip = ip.substring(0, ip.indexOf(','));
            }
        }
        return ip;
    }

}
