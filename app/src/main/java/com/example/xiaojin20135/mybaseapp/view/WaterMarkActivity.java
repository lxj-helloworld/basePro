package com.example.xiaojin20135.mybaseapp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.others.Watermark;
import com.example.xiaojin20135.mybaseapp.R;

public class WaterMarkActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btn_water = findViewById(R.id.btn_water);
        ScrollView scroll_content = findViewById(R.id.scroll_content);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Watermark.getInstance().setLogo(bitmap).setText("1234").setOffsetParas(1.5).setImageScale(1.3f).setAlpha(200).setTextColor(0xDEFF9797).show(this,btn_water);
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
