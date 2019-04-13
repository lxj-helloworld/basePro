package com.example.xiaojin20135.mybaseapp.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.map.LocationActivity;
import com.example.xiaojin20135.basemodule.map.LocationInMapActivity;
import com.example.xiaojin20135.basemodule.map.LocationItem;
import com.example.xiaojin20135.basemodule.map.MapUtil;
import com.example.xiaojin20135.basemodule.map.bean.LocationLogHelp;
import com.example.xiaojin20135.mybaseapp.R;

public class MyLocationActivity extends LocationActivity {

    TextView locationinfo_TV,locationinfo_sel_TV;
    Button open_map_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startLocation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        locationinfo_TV = (TextView)findViewById(R.id.locationinfo_TV);
        locationinfo_sel_TV = (TextView)findViewById(R.id.locationinfo_sel_TV);

        open_map_btn = (Button)findViewById(R.id.open_map_btn);
    }

    @Override
    protected void initEvents() {
        open_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                canGo(LocationInMapActivity.class);
                canGoForResult(LocationInMapActivity.class,MapUtil.resultCode_Baidu);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void locationSuccess (LocationItem locationItem) {
        Log.d (TAG,"locationItem = " + locationItem.toString ());
        dismissProgress();
        if(locationinfo_TV != null){
            StringBuilder stringBuilder = new StringBuilder("");
            stringBuilder.append("地址：" + locationItem.getProvince () + " " + locationItem.getCity () + " " + locationItem.getLocationDescribe());
            stringBuilder.append("经纬度：" + locationItem.getLongitude() + "," + locationItem.getLatitude());
            locationinfo_TV.setText (locationItem.toString ());
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == MapUtil.resultCode_Baidu){
            LocationItem locationItem = (LocationItem) data.getSerializableExtra("locationItem");
            locationinfo_sel_TV.setText ("选择新坐标：" + locationItem.getLatitude() + "," + locationItem.getLongitude());
        }
    }
}