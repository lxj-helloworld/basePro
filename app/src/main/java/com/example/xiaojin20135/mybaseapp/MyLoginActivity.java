package com.example.xiaojin20135.mybaseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.xiaojin20135.basemodule.activity.login.BaseLoginActivity;
import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;

/**
 * @author lixiaojin
 * @create 2018-07-13
 * @Describe 
 */
public class MyLoginActivity extends BaseLoginActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        //设置显示图片
        setLloginImage(R.mipmap.ic_launcher);
        //设置底部copytight信息
        setCopyRight(R.string.app_name);
        //设置登陆地址
        //设置请求登录地址
        setLoginUrl("http://172.20.3.53:8919/toa/" +   "toa/toaMobileLogin_login");
        //设置用户名和密码的参数名，适应不同系统，默认参数名为loginName和password
        init("loginname", "password");
        addParaMap("ismobile", "1");
//        addParaMap("needToken", "1");
        addParaMap("uuid", getDeviceId());
        addParaMap("mobileversion",android.os.Build.VERSION.RELEASE);
        addParaMap("mobilemodel",android.os.Build.MANUFACTURER+"_"+android.os.Build.MODEL);
        canStart();
    }

    @Override
    public void canStart () {
        super.canStart ();
    }

    @Override
    public void loadDataSuccess (Object tData) {
        super.loadDataSuccess (tData);
        Intent intent = new Intent (MyLoginActivity.this,MainActivity.class);
        startActivity (intent);
    }


}
