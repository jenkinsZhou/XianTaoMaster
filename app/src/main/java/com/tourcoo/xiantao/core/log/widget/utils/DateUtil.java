package com.tourcoo.xiantao.core.log.widget.utils;

import android.text.TextUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

import java.util.Date;

/**
 * @author :zhoujian
 * @description : 时间工具类
 * @company :途酷科技
 * @date 2018年03月13日下午 04:22
 * @Email: 971613168@qq.com
 */

public class DateUtil {
    private final static String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static String PATTERN_NO_SPLIT = "yyyyMMddHHmmss";
    private final static String PATTERN_DATE = "yyyy-MM-dd";
    private final static int TIME_LENGTH = 14;

    /**
     * 获取当前时间戳格式的字符串
     */
    public static String getCurrentTime() {
        long timeStamp = System.currentTimeMillis();
        return String.valueOf(timeStamp);
    }


    /**
     * 将时间戳转换为时间格式的字符串
     */
    public static String getTimeString(String timeMillis) {
        final FastDateFormat df = FastDateFormat.getInstance(PATTERN);
        if (StringUtils.isNotEmpty(timeMillis)) {
            long time = Long.parseLong(timeMillis);
            return df.format(new Date(time));
        } else {
            return "";
        }
    }

    /**
     * 将时间戳转换为时间格式的字符串
     */
    public static String getTimeStringNoSplit(String timeMillis) {
        final FastDateFormat df = FastDateFormat.getInstance(PATTERN_NO_SPLIT);
        if (StringUtils.isNotEmpty(timeMillis)) {
            long time = Long.parseLong(timeMillis);
            return df.format(new Date(time));
        } else {
            return "";
        }
    }


    public static String getDateString(String timeMillis) {
        final FastDateFormat df = FastDateFormat.getInstance(PATTERN_DATE);
        if (StringUtils.isNotEmpty(timeMillis)) {
            long time = Long.parseLong(timeMillis);
            return df.format(new Date(time));
        } else {
            return "";
        }
    }

    public static String getTimeChineseCharacter(String timeValue) {
        if (TextUtils.isEmpty(timeValue)) {
            return "";
        }
        if (timeValue.length() == TIME_LENGTH) {
            StringBuilder sb = new StringBuilder("");
            sb.append(timeValue.substring(0, 4));
            sb.append("年");
            sb.append(timeValue.substring(4, 6));
            sb.append("月");
            sb.append(timeValue.substring(6, 8));
            sb.append("日");
            sb.append(timeValue.substring(8, 10));
            sb.append("时");
            sb.append(timeValue.substring(10, 12));
            sb.append("分");
            sb.append(timeValue.substring(12, 14));
            sb.append("秒");
            return sb.toString();
        }
        return "";
    }


}
