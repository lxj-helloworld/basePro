package com.example.xiaojin20135.mybaseapp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.endlessscroll.ScrollingBackgroundView;
import com.example.xiaojin20135.basemodule.view.others.Watermark;
import com.example.xiaojin20135.mybaseapp.R;

public class WaterMarkActivity extends ToolBarActivity {
    ScrollView scroll_content;
    TextView content_tv;
    LinearLayout content_ll;
    RelativeLayout re_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        re_rl = findViewById(R.id.re_rl);
        Button btn_water = findViewById(R.id.btn_water);
        content_ll = findViewById(R.id.content_ll);
        scroll_content = findViewById(R.id.scroll_content);
        content_tv = findViewById(R.id.content_tv);
        content_tv.setMovementMethod(ScrollingMovementMethod.getInstance());

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        Watermark.getInstance().setLogo(bitmap).setText("1234").setOffsetParas(1.5).setImageScale(1.3f).setAlpha(50).setTextColor(0xDEFF9797).show(this,content_tv);

//        BitmapDrawable drawable = new BitmapDrawable(getResources(),bitmap);

        final ScrollingBackgroundView scrollingBackgroundView = new ScrollingBackgroundView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        scrollingBackgroundView.setDrawable(scrollingBackgroundView.drawTextToBitmap(this,"0903",bitmap));
        scrollingBackgroundView.setAlpha((float) 0.9);
        scrollingBackgroundView.setLayoutParams(params);
        re_rl.addView(scrollingBackgroundView,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroll_content.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    scrollingBackgroundView.scrollTo(scrollX ,scrollY );
                }
            });
        }
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


    @Override
    protected void onResume() {
        super.onResume();

    }
}
