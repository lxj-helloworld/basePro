package com.example.xiaojin20135.basemodule.map.bean;

public enum LocationLogHelp {
    LOCATION_LOG_HELP;
    private String province = "";//省份
    private String city = "";//城市
    private String street = "";//街道
    private String locationDescribe = "";//位置描述

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
}

