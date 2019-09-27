package com.example.xiaojin20135.mybaseapp.normal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.view.normal.NormalTextView;
import com.example.xiaojin20135.mybaseapp.R;

public class NormalTextViewActivity extends ToolBarActivity {

    private NormalTextView normal_textview1,normal_textview2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        normal_textview1.setmTitleText("第一个title");
        normal_textview1.setRightValue("第一个值");

        normal_textview2.setmTitleText("第二个title");
        normal_textview2.setRightValue("第二个值");

//        ((TextView)normal_textview1.findViewById(R.id.tv_left_title)).setText("第一个title");
//        ((TextView)normal_textview1.findViewById(R.id.tv_value)).setText("第一个值");
//        ((TextView)normal_textview2.findViewById(R.id.tv_left_title)).setText("第二个title");
//        ((TextView)normal_textview2.findViewById(R.id.tv_value)).setText("第二个值");


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal_text_view;
    }

    @Override
    protected void initView() {
        normal_textview1 = findViewById(R.id.normal_textview1);
        normal_textview2 = findViewById(R.id.normal_textview2);


    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }
}
