package com.example.xiaojin20135.basemodule.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;

/**
 * Created by lixiaojin on 2018-08-17 16:15.
 * 功能描述：
 *  自定义列表Dialog
 */

public class MyItemDialog extends Dialog implements AdapterView.OnItemClickListener {
    private String title;
    private Context context;
    private String[] items;
    private int requestCode;
    private OnDialogItemClickListener listener;
    private TextView textView;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    public MyItemDialog (Context context,String[] items, int requestCode, OnDialogItemClickListener listener) {
        super(context, R.style.AlertDialogStyle);
        this.context=context;
        this.items=items;
        this.requestCode=requestCode;
        this.listener=listener;
    }

    public MyItemDialog (Context context, String title, String[] items, int requestCode, OnDialogItemClickListener listener) {
        this(context,items,requestCode,listener);
        this.title=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog);
        textView = (TextView) findViewById(R.id.tvItemDialogTitle);
        listView = (ListView) findViewById(R.id.lvItemDialog);
        adapter = new ArrayAdapter<String>(context, R.layout.item_dialog_activity,R.id.text_view, items);
        if(title == null){
            textView.setVisibility(View.GONE);
        }else{
            textView.setText(title);
        }


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        listener.onDialogItemClick(requestCode,position, adapter.getItem(position));
        dismiss();
    }


    public interface OnDialogItemClickListener {
        /**
         * 点击item 事件的回调方法
         * @param requestCode 用于区分某种情况下的showDialog
         * @param position
         * @param item
         */
        void onDialogItemClick(int requestCode, int position,String item);
    }
}
