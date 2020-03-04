package com.example.xiaojin20135.basemodule.view.widget;

import android.content.Context;
import android.view.ViewGroup;

import com.example.xiaojin20135.basemodule.view.widget.popup.Popup;

/*
* @author lixiaojin
* create on 2020-03-03 10:46
* description: 悬浮层
*  小工厂模式
*/
public class Popus {

    public static Popup popup(Context context){
        return new Popup(context, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static Popup popup(Context context,int width){
        return new Popup(context, width,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static Popup popup(Context context,int width,int height){
        return new Popup(context, width,height);
    }

}
