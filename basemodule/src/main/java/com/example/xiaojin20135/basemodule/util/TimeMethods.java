package com.example.xiaojin20135.basemodule.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiaojin20135 on 2017-11-20.
 */

public enum TimeMethods {
    TIME_METHODS;
    private static final String TAG = "TimeMethods";
    /**
     * 获取当前时间字符串，纯数字
     * @return
     */
    public String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取时间字符串，纯数字
     * @return
     */
    public String getTimeStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmssSSS");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取时间字符串，纯数字
     * @return
     */
    public String getTimeMillStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSS");
        return simpleDateFormat.format(new Date());
    }


    /**
     * 获取当前时间字符串，格式化 年月日时分秒
     * @return
     */
    public String getFormatDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间字符串，格式化 年月日时分秒
     * @return
     */
    public String getFormatDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取时分秒格式化字符串
     * @return
     */
    public String getFormatTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        return simpleDateFormat.format(new Date());
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-18 14:12
     * @Describe
     */
    public String getFormatDateOnly(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:15
     * @Describe 返回年
     */
    public int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:15
     * @Describe 返回月
     */
    public int getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-29 9:15
     * @Describe 返回日
     */
    public int getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE);
    }
}
