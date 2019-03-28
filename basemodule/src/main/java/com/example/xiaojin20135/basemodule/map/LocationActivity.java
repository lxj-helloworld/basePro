package com.example.xiaojin20135.basemodule.map;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.xiaojin20135.basemodule.activity.BaseActivity;
import com.example.xiaojin20135.basemodule.activity.ToolBarActivity;

import java.util.LinkedList;

public abstract class LocationActivity extends ToolBarActivity {
    public  LocationClient locationClient = null;
    private MyLocationListener myLocationListener = new MyLocationListener();
    private Handler locHander;
    private BDLocation bdLocation;
    private LinkedList<LocationEntity> locationList = new LinkedList<LocationEntity>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        locationClient.registerLocationListener(myLocationListener);
        init ();
        //注册监听函数
        initHandler ();
    }

    public void init(){
        LocationClientOption mOption = new LocationClientOption();
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType(MapUtil.CoorType_GCJ02);//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(10000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setOpenGps (true);
        locationClient.setLocOption(mOption);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    private void initHandler(){
        locHander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    bdLocation = msg.getData().getParcelable("loc");
                    int iscal = msg.getData().getInt("iscalculate");
                    if (bdLocation != null) {
                        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                        //以下只列举部分获取经纬度相关（常用）的结果信息
                        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
                        LocationItem locationItem = new LocationItem ();
                        locationItem.setLatitude (bdLocation.getLatitude ()+"");
                        locationItem.setLongitude (bdLocation.getLongitude ()+"");
                        locationItem.setAddr (bdLocation.getAddrStr ()); //获取详细地址信息
                        locationItem.setCountry (bdLocation.getCountry()); //获取国家
                        locationItem.setProvince (bdLocation.getProvince()); //获取省份
                        locationItem.setCity (bdLocation.getCity());//获取城市
                        locationItem.setDistrict (bdLocation.getDistrict()); //获取区县
                        locationItem.setStreet (bdLocation.getStreet()); //获取街道信息
                        locationItem.setLocationDescribe (bdLocation.getLocationDescribe ());//位置描述信息
                        locationItem.setCoorType (bdLocation.getCoorType ()); ///坐标类型
                        locationItem.setRadius (bdLocation.getRadius()); //定位精度
                        locationItem.setErrorCode (bdLocation.getLocType ());//定位错误返回码
                        //通知定位结果
                        locationSuccess(locationItem);
                        //停止定位
                        stopLocation();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        };
    }

    /**
     * @author lixiaojin
     * @createon 2018-08-07 14:18
     * @Describe 开始定位
     */
    public void startLocation(){
        showProgress ();
        locationClient.start ();
        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求
    }

    /**
     * @author lixiaojin
     * @createon 2018-08-07 14:18
     * @Describe 停止定位
     */
    public void stopLocation(){
        dismissProgress ();
        locationClient.stop ();
    }

    public abstract void locationSuccess(LocationItem locationItem);

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation (BDLocation bdLocation) {
            if (bdLocation != null && (bdLocation.getLocType() == 161 || bdLocation.getLocType() == 66)) {
                Message locMsg = locHander.obtainMessage();
                Bundle locData;
                locData = MapHelp.MAP_HELP.Algorithm(bdLocation,locationList);
                if (locData != null) {
                    locData.putParcelable("loc", bdLocation);
                    locMsg.setData(locData);
                    locHander.sendMessage(locMsg);
                }
            }else{
//                Log.d("LocationInMapActivity","location="+location+";location.getLocType()="+location.getLocType());
            }

        }
    }
}
