package com.example.xiaojin20135.mybaseapp.datepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.listener.DatePickListener;
import com.example.xiaojin20135.basemodule.view.DatePickDialog;
import com.example.xiaojin20135.mybaseapp.R;

public class DatePickerActivity extends ToolBarActivity {

    private String currentDate = "";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

    }

    @Override
    protected int getLayoutId () {
        return R.layout.activity_date_picker;
    }

    @Override
    protected void initView () {

    }

    @Override
    protected void initEvents () {

    }

    @Override
    protected void loadData () {

    }

    public void onClick (View view) {
        switch (view.getId ()){
            case R.id.date_pick_btn:
                pickDate(true);
                break;
            case R.id.only_date_pick_btn:
                pickDate (false);
                break;
        }
    }

    private void pickDate(final boolean showTime){
        new DatePickDialog (this,showTime,"2099-09-12").builder().setTitle("选择日期")
            .setPositiveButton("确认",new DatePickListener () {
                @Override
                public void onClick(View v) {
                    currentDate = this.getDateValue ();
                    Log.d ("DatePickerActivity","dataValue = " + currentDate);
                    if(showTime){
                        Toast.makeText (DatePickerActivity.this, this.getDateValue () + " " + this.getTimeValue () + "",Toast.LENGTH_LONG).show ();
                    }else{
                        Toast.makeText (DatePickerActivity.this, this.getDateValue (),Toast.LENGTH_LONG).show ();
                    }

                }
            }).setNegativeButton("取消", new DatePickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}
