package com.example.xiaojin20135.basemodule.event;

import org.greenrobot.eventbus.EventBus;

/**
 * 自定义事件
 */
public class MyEvent<T> {
    private int code;
    private T data;

    public MyEvent(int code){
        this.code = code;
    }

    public MyEvent(int code ,T data){
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
