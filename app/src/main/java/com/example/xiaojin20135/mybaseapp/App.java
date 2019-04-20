package com.example.xiaojin20135.mybaseapp;

import com.example.xiaojin20135.basemodule.activity.BaseApplication;
import com.example.xiaojin20135.basemodule.retrofit.helper.RetrofitManager;
import com.example.xiaojin20135.mybaseapp.xgPush.XgPush;

/**
 * Created by lixiaojin on 2018-08-11 16:41.
 * 功能描述：
 */

public class App extends BaseApplication{
    @Override
    public void onCreate () {
        super.onCreate ();
        RetrofitManager.RETROFIT_MANAGER.setSelfDefineHttps (true);//启动自定义证书
        RetrofitManager.RETROFIT_MANAGER.init ("https://186.168.6.201/ChpcyServer/");
//        XgPush.XG_PUSH.init (this);
    }
}
