package com.example.xiaojin20135.mybaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.activity.BaseActivity;
import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.event.EventBaseUtil;
import com.example.xiaojin20135.basemodule.event.MyEvent;

import org.w3c.dom.Text;

public class MyEventBusActivity extends ToolBarActivity {

    private Button send_post_btn;
    private TextView receive_event_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_event_bus;
    }

    @Override
    protected void initView() {
        send_post_btn = findViewById(R.id.send_post_btn);
        receive_event_tv = findViewById(R.id.receive_event_tv);

    }

    @Override
    protected void initEvents() {
        send_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"this is the onclick");
                MyEvent myEvent = new MyEvent(0,"this is the event content");
                EventBaseUtil.sendEvent(myEvent);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void receiveEvent(MyEvent event) {
        super.receiveEvent(event);
        receive_event_tv.setText(event.getData().toString());
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }
}
