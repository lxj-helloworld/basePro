package com.example.xiaojin20135.basemodule.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.xiaojin20135.basemodule.retrofit.util.AppContextUtil;

/**
 * Created by xiaojin20135 on 2017-11-20.
 */

public class BaseApplication extends Application {
    private static BaseApplication app;
    private static Activity activity;


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
