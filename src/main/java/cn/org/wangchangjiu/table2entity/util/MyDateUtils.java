package cn.org.wangchangjiu.table2entity.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Classname CommonUtil
 * @Description
 * @Date 2023/5/8 14:15
 * @Created by wangchangjiu
 */
public class MyDateUtils {


    public static final String SIMPLE_FORMAT = "yyyy-MM-dd";



    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date,SIMPLE_FORMAT,TimeZone.getDefault());
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern,TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(date);
    }
}
