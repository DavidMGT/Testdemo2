package com.work.student.tool;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;

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
    public static String getSimpleDate(String sData) {
        if (sData == null) {
            return "未知";
        }
        long data = Long.parseLong(sData);
        return new SimpleDateFormat("MM-dd HH:mm").format(new Date(data));
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyMM"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDateYYMMDD(String sData) {
        LogUtils.debug("sData==" + sData);
        long data;
        if (sData == null) {
            return "未知";
        }
        try {
            data = Long.parseLong(sData);
        } catch (Exception e) {
            return sData;
        }
        return new SimpleDateFormat("yy:MM-dd").format(new Date(data));
    }


    public static String calculateTime(int recordingtime) {
        int minute = recordingtime / 60;
        //少于60分钟
        //少于60分钟
        if (minute == 0) {
            if (recordingtime <= 9) {
                return "00:0" + recordingtime;
            }
            return "00:" + recordingtime;
        }
        int second = recordingtime % 60;
        if (minute <= 9) {
            if (second <= 9) {
                return "O" + minute + ":" + "0" + second;
            } else {
                return "O" + minute + ":" + second;
            }

        } else {
            if (second <= 9) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }

    public static String calculateTime1(long duration) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
        LogUtils.debug(" formatter.format(duration)=" + formatter.format(duration));
        return formatter.format(duration);
    }

    /*
* 毫秒转化
*/
    public static String formatTime(long ms) {
        LogUtils.debug("formatTime--" + ms);
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strMinute + " 分钟 " + strSecond + " 秒";
    }
}
