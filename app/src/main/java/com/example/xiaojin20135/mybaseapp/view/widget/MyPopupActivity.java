package com.example.xiaojin20135.mybaseapp.view.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.util.ui.DisplayHelper;
import com.example.xiaojin20135.basemodule.view.widget.Popus;
import com.example.xiaojin20135.basemodule.view.widget.popup.Popup;
import com.example.xiaojin20135.mybaseapp.R;

public class MyPopupActivity extends ToolBarActivity {
    private Button normal_btn;


    private Popup mPopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_popup;
    }

    @Override
    protected void initView() {
        normal_btn = (Button) findViewById(R.id.normal_btn);
    }

    @Override
    protected void initEvents() {
        normal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText("这是一条测试消息！");
                textView.setTextColor(Color.RED);

                mPopup = Popus.popup(getApplicationContext(), DisplayHelper.dp2px(getApplicationContext(),250))
                        .preferDirection(Popup.DIRECTION_BOTTOM)
                        .view(textView)
                        .show(v);

            }
        });
    }

    @Override
    protected void loadData() {

    }
}
