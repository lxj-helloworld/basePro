package com.example.xiaojin20135.basemodule.view.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.xiaojin20135.basemodule.view.widget.popup.Popup;
import com.example.xiaojin20135.basemodule.view.widget.popup.QuickAction;

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


    /*
    * @author lixiaojin
    * create on 2020-03-05 16:01
    * description: 显示列表popup
    */
    public static Popup listPopup(Context context, int width, int maxHeight, BaseAdapter adapter, AdapterView.OnItemClickListener onItemClickListener) {
        ListView listView = new WrapContentListView(context, maxHeight);
        listView.setAdapter(adapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setDivider(null);
        return popup(context, width).view(listView);
    }


    /*
    * @author lixiaojin
    * create on 2020-03-06 10:42
    * description: 快捷菜单类popup
    */
    public static QuickAction quickAction(Context context, int actionWidth, int actionHeight) {
        return new QuickAction(context, ViewGroup.LayoutParams.WRAP_CONTENT, actionHeight)
                .actionWidth(actionWidth)
                .actionHeight(actionHeight);
    }
}
