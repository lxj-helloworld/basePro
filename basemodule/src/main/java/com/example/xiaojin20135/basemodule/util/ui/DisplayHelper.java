package com.example.xiaojin20135.basemodule.util.ui;

import android.content.Context;
import android.util.DisplayMetrics;

/*
* @author lixiaojin
* create on 2020-03-03 10:37
* description: 展示帮助类
*/
public class DisplayHelper {
    private static final String TAG = "DisplayHelper";

    /*
    * @author lixiaojin
    * create on 2020-03-03 10:38
    * description: 单位换算  dp -> px
    */
    public static int dp2px(Context context,int dp){
        return (int)(getDensity(context) * dp + 0.5);
    }

    /*
    * @author lixiaojin
    * create on 2020-03-03 10:40
    * description: 获取屏幕密度
    */
    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }


    /*
    * @author lixiaojin
    * create on 2020-03-04 14:16
    * description:获取 DisplayMetrics
    */
    public static DisplayMetrics getDisplayMetris(Context context){
        return context.getResources().getDisplayMetrics();
    }

}

