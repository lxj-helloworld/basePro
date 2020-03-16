package com.example.xiaojin20135.mybaseapp.alert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.widget.dialog.TipDialog;
import com.example.xiaojin20135.mybaseapp.R;

import java.util.ArrayList;

public class SimpleAlertActivity extends ToolBarActivity {
    private TipDialog mTipDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ArrayList
//        Comparable
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_alert;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.loading_btn:
                mTipDialog = showLoading();
                break;
            case R.id.success_btn:
                mTipDialog = showSuccess();
                break;
            case R.id.fail_btn:
                mTipDialog = showFail();
                break;
            case R.id.info_btn:
                mTipDialog = showInfo();
                break;
            case R.id.only_pic_btn:
                mTipDialog = showPicOnly();
                break;
            case R.id.only_word_btn:
                mTipDialog = showWordOnly();
                break;
        }
        mTipDialog.show();

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialog.dismiss();
            }
        },3000);
    }

    private TipDialog showLoading(){
        return new TipDialog.Builder(SimpleAlertActivity.this)
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
    }

    private TipDialog showSuccess(){
        return new TipDialog.Builder(SimpleAlertActivity.this)
                .setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("发送成功")
                .create();
    }

    private TipDialog showFail(){
        return new TipDialog.Builder(SimpleAlertActivity.this)
                .setIconType(TipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord("发送失败")
                .create();
    }

    private TipDialog showInfo(){
        return new TipDialog.Builder(SimpleAlertActivity.this)
                .setIconType(TipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("请勿重复操作")
                .create();
    }

    private TipDialog showPicOnly(){
        return new TipDialog.Builder(SimpleAlertActivity.this)
                .setIconType(TipDialog.Builder.ICON_TYPE_SUCCESS)
                .create();
    }

    private TipDialog showWordOnly(){
        return new TipDialog.Builder(SimpleAlertActivity.this)
                .setTipWord("请勿重复操作")
                .create();
    }







}
