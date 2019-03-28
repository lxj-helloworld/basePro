package com.example.xiaojin20135.basemodule.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.xiaojin20135.basemodule.map.LocationService;
import com.example.xiaojin20135.basemodule.retrofit.util.AppContextUtil;

/**
 * Created by xiaojin20135 on 2017-11-20.
 */

public class BaseApplication extends Application {
    private static BaseApplication app;
    private static Activity activity;

    //百度地图相关
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        Log.d("BaseApplication","onCreate");
        super.onCreate();
        MultiDex.install(this);
        AppContextUtil.init(this);
        app = this;

    }


    public void initLocation(){
        /**
         * 初始化百度地图SDK，建议在Application中创建
         */
        locationService = new LocationService (this);
        mVibrator =(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(this);
        ///将当前地图的坐标类型设置为GCJ02
        SDKInitializer.setCoordType(CoordType.GCJ02);
    }

    public static BaseApplication getInstance(){
        return app;
    }

    public static void setActivity(Activity activityIn){
        activity = activityIn;
    }
    public static Activity getActivity(){
        return activity;
    }
}
