package com.example.xiaojin20135.mybaseapp.normal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.normal.NormalEditText;
import com.example.xiaojin20135.mybaseapp.R;

public class NormalEdittextActivity extends ToolBarActivity {

    private NormalEditText ll_mynormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_edittext;
    }

    @Override
    protected void initView() {
        ll_mynormal = (NormalEditText)findViewById(R.id.ll_mynormal);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }

    public void onClick(View view) {
        Log.d(TAG,"value = " + ll_mynormal.mEt_value.getText());
    }
}
