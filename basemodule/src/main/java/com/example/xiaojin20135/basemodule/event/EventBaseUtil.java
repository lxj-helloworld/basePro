package com.example.xiaojin20135.basemodule.event;

import org.greenrobot.eventbus.EventBus;

/**
 * 事件的订阅、取消订阅、和发布
 */
public class EventBaseUtil {

    /**
     * 订阅事件
     * @param subscribe
     */
    public static void register(Object subscribe){
        EventBus.getDefault().register(subscribe);
    }

    /**
     *  取消订阅事件
     * @param subscribe
     */
    public static void unRegister(Object subscribe){
        EventBus.getDefault().unregister(subscribe);
    }

    /**
     *  发布事件
     */
    public static void sendEvent(MyEvent event){
        EventBus.getDefault().post(event);
    }

    /**
     *
     */
    public static void sendStickEvent(MyEvent event){
        EventBus.getDefault().postSticky(event);
    }


}
