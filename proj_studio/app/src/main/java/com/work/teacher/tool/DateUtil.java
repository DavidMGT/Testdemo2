package com.work.teacher.tool;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuli on 2015/11/27.
 */
public class DateUtil {


    /**
     * 浣跨敤鐢ㄦ埛鏍煎紡鎻愬彇瀛楃涓叉棩鏈?
     *
     * @param strDate 鏃ユ湡瀛楃涓?
     * @param pattern 鏃ユ湡鏍煎紡
     * @return
     */

    public static Date parse(String strDate, String pattern) {

        if (TextUtil.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 浣跨敤鐢ㄦ埛鏍煎紡鏍煎紡鍖栨棩鏈?
     *
     * @param date    鏃ユ湡
     * @param pattern 鏃ユ湡鏍煎紡
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyMM"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDate(long data) {
        return new SimpleDateFormat("yy:MM").format(new Date(data));
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyMM"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDateFromString(String sData) {
        if (TextUtil.isEmpty(sData)) {
            LogUtils.debug("Sdata=null");
            return "未知";
        }
        long data = Long.parseLong(sData);
        return new SimpleDateFormat("MM-dd HH:mm").format(new Date(data));
    }


}
