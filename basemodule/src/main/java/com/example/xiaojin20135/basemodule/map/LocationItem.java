package com.example.xiaojin20135.basemodule.map;

import java.io.Serializable;

/**
 * Created by lixiaojin on 2018-08-02 11:23.
 * 功能描述：
 */

public class LocationItem implements Serializable {
    private String longitude = ""; //纬度信息
    private String latitude = ""; //经度信息
    private String addr = "";    //获取详细地址信息
    private String country = "";    //获取国家
    private String province = "";    //获取省份
    private String city = "";    //获取城市
    private String district = "";    //获取区县
    private String street = "";    //获取街道信息
    private String locationDescribe = "";//位置描述
    private String coorType = "";//坐标类型
    private float radius;//定位精度
    private int errorCode;//定位错误返回码

    public String getLongitude () {
        return longitude;
    }

    public void setLongitude (String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude () {
        return latitude;
    }

    public void setLatitude (String latitude) {
        this.latitude = latitude;
    }

    public String getAddr () {
        return addr;
    }

    public void setAddr (String addr) {
        this.addr = addr;
    }

    public String getCountry () {
        return country;
    }

    public void setCountry (String country) {
        this.country = country;
    }

    public String getProvince () {
        return province;
    }

    public void setProvince (String province) {
        this.province = province;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getDistrict () {
        return district;
    }

    public void setDistrict (String district) {
        this.district = district;
    }

    public String getStreet () {
        return street;
    }

    public void setStreet (String street) {
        this.street = street;
    }

    public String getLocationDescribe () {
        return locationDescribe;
    }

    public void setLocationDescribe (String locationDescribe) {
        this.locationDescribe = locationDescribe;
    }

    public String getCoorType () {
        return coorType;
    }

    public void setCoorType (String coorType) {
        this.coorType = coorType;
    }

    public float getRadius () {
        return radius;
    }

    public void setRadius (float radius) {
        this.radius = radius;
    }

    public int getErrorCode () {
        return errorCode;
    }

    public void setErrorCode (int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString () {
        StringBuffer stringBuffer = new StringBuffer ();
        stringBuffer.append ("longitude = " + longitude + "\r\n");
        stringBuffer.append ("latitude = " + latitude + "\r\n");
        stringBuffer.append ("addr = " + addr + "\r\n");
        stringBuffer.append ("country = " + country + "\r\n");
        stringBuffer.append ("province = " + province + "\r\n");
        stringBuffer.append ("city = " + city + "\r\n");
        stringBuffer.append ("district = " + district + "\r\n");
        stringBuffer.append ("street = " + street + "\r\n");
        stringBuffer.append ("locationDescribe = " + locationDescribe + "\r\n");
        stringBuffer.append ("coorType = " + coorType + "\r\n");
        stringBuffer.append ("radius = " + radius + "\r\n");
        stringBuffer.append ("errorCode = " + errorCode + "\r\n");
        return stringBuffer.toString ();
    }
}
