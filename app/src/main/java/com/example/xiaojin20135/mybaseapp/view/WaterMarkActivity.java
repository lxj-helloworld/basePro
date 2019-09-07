package com.example.xiaojin20135.mybaseapp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.others.Watermark;
import com.example.xiaojin20135.mybaseapp.R;

public class WaterMarkActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
        Watermark.getInstance().setLogo(bitmap).setText("1234").setTextColor(0xDEFF9797).show(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_water_mark;
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
}
