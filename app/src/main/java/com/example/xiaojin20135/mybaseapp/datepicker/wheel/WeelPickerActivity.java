package com.example.xiaojin20135.mybaseapp.datepicker.wheel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;
import com.example.xiaojin20135.basemodule.wheelview.widget.DateWheelDialog;
import com.example.xiaojin20135.basemodule.wheelview.widget.views.OnDateListener;
import com.example.xiaojin20135.mybaseapp.R;

import butterknife.BindView;

public class WeelPickerActivity extends ToolBarActivity {
    @BindView (R.id.date_result_TV)
    TextView date_result_TV;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

    }

    @Override
    protected int getLayoutId () {
        return R.layout.activity_weel_picker;
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
        if(view.getId () == R.id.standard_date_btn){
            getStandardDate();
        }
    }


    private void getStandardDate(){
        DateWheelDialog dateWheelDialog = new DateWheelDialog(WeelPickerActivity.this,true,true);
//        dateWheelDialog.setDate(2016, 12, 01);
        dateWheelDialog.show();
        dateWheelDialog.setOnDateListener (new OnDateListener () {
            @Override
            public void onClick(String year, String month, String day,String halfDay) {
                date_result_TV.setText (year + "-" + month + "-" + day + " " + halfDay);
            }
        });
    }
}
