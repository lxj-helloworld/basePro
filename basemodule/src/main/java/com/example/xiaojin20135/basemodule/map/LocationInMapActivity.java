package com.example.xiaojin20135.basemodule.map;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.activity.BaseApplication;
import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;

import java.io.Serializable;
import java.util.LinkedList;

import butterknife.BindView;

public class LocationInMapActivity extends ToolBarActivity {
    MapView mMapView;
    TextView location_resultTV;
    Button recordBtn;
    Button get_location_Btn;

    private BaiduMap mBaiduMap;
    private LocationService locService;
    private LinkedList<LocationEntity> locationList = new LinkedList<LocationEntity>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果
    private BDLocation bdLocation;
    private String longitude = "";
    private String latitude = "";
    private String addr = "";    //获取详细地址信息
    private String country = "";    //获取国家
    private String province = "";    //获取省份
    private String city = "";    //获取城市
    private String district = "";    //获取区县
    private String street = "";    //获取街道信息
    private String locationDescribe = "";//位置描述
    private String coorType = "";
    private String longitude_in = "";
    private String latitude_in = "";
    private LocationItem locationItem = new LocationItem ();
    Marker marker = null;
    private boolean drawable = true;//是否允许拖拽
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setTitleText ("");

        locService = ((BaseApplication) getApplication()).locationService;
        LocationClientOption mOption = locService.getDefaultLocationClientOption();

        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        mOption.setCoorType(MapUtil.CoorType_GCJ02);
        locService.setLocationOption(mOption);
        locService.registerListener(listener);
        //如果传递进的坐标信息为空，启动定位
        if(longitude_in == null || latitude_in == null){
            locService.start();
        }else{
            if(longitude_in.equals("") || latitude_in.equals("")){
                locService.start();
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"on toolbar lefticon");
                // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
                locService.unregisterListener(listener);
                locService.stop();
                LocationInMapActivity.this.finish();
//                mMapView.onDestroy();
                Log.d(TAG,"toolbar pressed ");
            }
        });

    }

    @Override
    protected int getLayoutId () {
        return R.layout.activity_location_in_map;
    }

    @Override
    protected void initView () {
        mMapView = (MapView)findViewById(R.id.bmapView);
        location_resultTV = (TextView)findViewById(R.id.location_resultTV);
        recordBtn = (Button)findViewById(R.id.recordBtn);
        get_location_Btn = (Button)findViewById(R.id.get_location_Btn);

        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);
        //设置地图类型(普通矢量图，卫星图和空白地图)
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
    }

    @Override
    protected void initEvents () {
        ///记录该位置
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存坐标信息
                Log.d("LocationInMapActivity","定位成功.longitude="+longitude+";latitude="+latitude);
                locService.stop();
                Intent intent = new Intent();
                intent.putExtra ("locationItem",(Serializable)locationItem);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        //定位
        get_location_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_resultTV.setText("定位中....");
                locService.start();
            }
        });
    }

    @Override
    protected void loadData () {
        /**
         * 传入的坐标为WGS-84坐标，需要转换为GCJ02坐标。此处百度地图使用你的是GCJ02坐标，输入和输出都是
         */
        Intent intent = getIntent();
        longitude_in = intent.getStringExtra("longitude");
        latitude_in = intent.getStringExtra("latitude");
        boolean hideSave = intent.getBooleanExtra("hideSave",false);
        if(hideSave){
            recordBtn.setVisibility(View.GONE);
        }
    }

    /***
     * 定位结果回调，在此方法中处理定位结果
     */
    BDLocationListener listener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null && (location.getLocType() == 161 || location.getLocType() == 66)) {
                Message locMsg = locHander.obtainMessage();
                Bundle locData;
                locData = MapHelp.MAP_HELP.Algorithm(location,locationList);
                if (locData != null) {
                    locData.putParcelable("loc", location);
                    locMsg.setData(locData);
                    locHander.sendMessage(locMsg);
                }
            }else{
                Log.d("LocationInMapActivity","location="+location+";location.getLocType()="+location.getLocType());
            }
        }

        public void onConnectHotSpotMessage(String s, int i){

        }
    };


    /***
     * 接收定位结果消息，并显示在地图上
     */
    private Handler locHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                bdLocation = msg.getData().getParcelable("loc");
                int iscal = msg.getData().getInt("iscalculate");
                if (bdLocation != null) {
                    recordLocationItem(bdLocation);
                    addMark(R.drawable.ic_menu_send,bdLocation.getLongitude(),bdLocation.getLatitude(),drawable);
                    //显示定位成功
                    location_resultTV.setText("定位成功");
                    //停止定位
                    locService.stop();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locService.unregisterListener(listener);
        locService.stop();
//        mBaiduMap = null;
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locService.unregisterListener(listener);
        locService.stop();
//        mMapView.onDestroy();
        Log.d(TAG,"onBackPressed");
    }

    /**
     *
     * @param id 标注图标
     * @param longitude_Double 坐标
     * @param latitude_Double 坐标
     * @param dragAble 拖拽
     */
    public void addMark(int id, final double longitude_Double, final double latitude_Double,boolean dragAble){
        LatLng point = new LatLng(latitude_Double, longitude_Double);
        Log.d("LocationInMapActivity","in addMark.longitude_Double="+longitude_Double+";latitude_Double="+latitude_Double);
        // 构建Marker图标
        BitmapDescriptor bitmap = null;
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark); // 非推算结果
        mBaiduMap.setMyLocationEnabled(dragAble);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).zIndex(9).draggable(true);
        //删除之前的mark
        if(marker != null){
            marker.remove();
        }
        // 在地图上添加Marker，并显示
        marker =  (Marker) mBaiduMap.addOverlay(option);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
        if(dragAble){
            //拖拽监听事件
            mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(Marker marker) {
                    //拖拽中
                    Log.d("LocationInMapActivity","in onMarkerDrag.");
                }
                @Override
                public void onMarkerDragEnd(Marker marker) {
                    //拖拽结束
                    Log.d("LocationInMapActivity","in onMarkerDragEnd. latitude="+marker.getPosition().latitude+";longitude="+marker.getPosition().longitude);
                    latitude = marker.getPosition().latitude+"";
                    longitude = marker.getPosition().longitude+"";
                    locationItem = new LocationItem();
                    locationItem.setLatitude(marker.getPosition().latitude+"");
                    locationItem.setLongitude(marker.getPosition().longitude+"");

                }
                @Override
                public void onMarkerDragStart(Marker marker) {
                    ///开始拖拽
                    Log.d("LocationInMapActivity","in onMarkerDragStart.");
                }
            });
        }
    }

    public void mapTypeSelect(View view){
        boolean checked = ((RadioButton)view).isChecked();
        int i = view.getId();
        if (i == R.id.normal_type) {
            if (checked) {
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }

        } else if (i == R.id.satellite_type) {
            if (checked) {
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }

        }
    }

    private LocationItem recordLocationItem(BDLocation bdLocation){
        //记录定位坐标
        longitude = bdLocation.getLongitude() + "";
        latitude = bdLocation.getLatitude() + "";
        addr = bdLocation.getAddrStr();    //获取详细地址信息
        country = bdLocation.getCountry();    //获取国家
        province = bdLocation.getProvince();    //获取省份
        city = bdLocation.getCity();    //获取城市
        district = bdLocation.getDistrict();    //获取区县
        street = bdLocation.getStreet();    //获取街道信息
        locationDescribe = bdLocation.getLocationDescribe ();//位置描述信息
        coorType = bdLocation.getCoorType ();

        locationItem.setAddr (addr);
        locationItem.setCountry (country);
        locationItem.setProvince (province);
        locationItem.setCity (city);
        locationItem.setDistrict (district);
        locationItem.setStreet (street);
        locationItem.setLocationDescribe (locationDescribe);
        locationItem.setCoorType (coorType);
        locationItem.setLongitude (longitude);
        locationItem.setLatitude (latitude);
        Log.d (TAG,"locationItem = " +locationItem.toString ());
        return locationItem;
    }
}
