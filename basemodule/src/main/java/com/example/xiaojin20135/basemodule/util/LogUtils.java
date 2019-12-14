package com.example.xiaojin20135.basemodule.util;

import android.util.Log;

/*
* @author lixiaojin
* create on 2019-12-02 14:35
* description: 日志帮助类
*/
public class LogUtils {
    public static boolean DEBUG = true;

    /*
    * @author lixiaojin
    * create on 2019-12-02 14:39
    * description: 不过滤的输出所有的调试信息
    * 级别最低， 用于打印琐碎的，意义不大的日志信息
    */
    public static void v(String tag,String message){
        if(DEBUG){
            Log.v(tag,makeMessage(message));
        }
    }


    /*
    * @author lixiaojin
    * create on 2019-12-02 14:40
    * description: 能输出Debug、Info 、Warning、Error级别的Log信息
    * 用于打印一些调试信息
    */
    public static void d(String tag,String message){
        if(DEBUG){
            Log.d(tag,makeMessage(message));
        }
    }

    /*
    * @author lixiaojin
    * create on 2019-12-02 14:41
    * description: 能输出Info、Warning和Error级别的Log信息
    * 用于打印分析问题的重要数据
    */
    public static void i(String tag,String message){
        if(DEBUG){
            Log.i(tag,makeMessage(message));
        }
    }

    /*
    * @author lixiaojin
    * create on 2019-12-02 14:42
    * description: 能输出Warning和Error级别的Log信息
    * 用于打印警告
    */
    public static void w(String tag,String message){
        if(DEBUG){
            Log.w(tag,makeMessage(message));
        }
    }

    /*
    * @author lixiaojin
    * create on 2019-12-02 14:44
    * description: 能输出Error级别的信息
    * 用于打印错误信息
    */
    public static void e(String tag,String message){
        if(DEBUG){
            Log.e(tag,makeMessage(message));
        }
    }

    /*
    * @author lixiaojin
    * create on 2019-12-02 14:35
    * description: 日志输出
    */
    private static String makeMessage(String message){
        return message == null ? "null" : message;
    }
}
