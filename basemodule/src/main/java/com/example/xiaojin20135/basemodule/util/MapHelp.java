package com.example.xiaojin20135.basemodule.util;

import java.util.Map;

/*
* @author lixiaojin
* create on 2019/8/19 10:45
* description: 获取Map中的值
*/
public class MapHelp {
    /*
    * @author lixiaojin
    * create on 2019/8/19 10:49
    * description: 获取Map中的int值
    */
    public int getIntValue(Map map,String key){
        int value = -99;
        if(map != null && map.get(key) != null){
            try {
                value = (int)map.get(key);
            }catch (ClassCastException e){
                try {
                    Double doubleValue = Double.parseDouble(map.get(key).toString());
                    value = doubleValue.intValue();
                }catch (NumberFormatException e1){

                }
            }
        }
        return value;
    }
}
