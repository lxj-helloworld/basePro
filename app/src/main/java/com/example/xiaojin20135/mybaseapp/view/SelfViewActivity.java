package com.example.xiaojin20135.mybaseapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.util.LogUtils;
import com.example.xiaojin20135.basemodule.view.button.AnimationButton;
import com.example.xiaojin20135.mybaseapp.R;

public class SelfViewActivity extends ToolBarActivity {

    private AnimationButton animation_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_self_view;
    }

    @Override
    protected void initView() {
        animation_btn = findViewById(R.id.animation_btn);
    }

    @Override
    protected void initEvents() {
        animation_btn.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                animation_btn.start();
            }

            @Override
            public void animationFinish() {
                LogUtils.d(TAG,"动画执行完成...");
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
