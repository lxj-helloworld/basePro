package com.example.xiaojin20135.basemodule.retrofit.model;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by lixiaojin on 2018-07-12.
 * 功能描述：
 */

public interface IBaseModel<T> {
    /**
     * 加载数据
     *
     * @param paraMap
     * @param iBaseRequestCallBack
     */
    void loadData(String url, String methodName, Map paraMap, final IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * 加载数据
     *
     * @param paraMap
     * @param iBaseRequestCallBack
     */
    void getData(String url, String methodName, Map paraMap, final IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * 加载数据
     *
     * @param paraMap
     * @param iBaseRequestCallBack
     */
    void postData(String url, String methodName, Map paraMap, final IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * 加载数据
     *
     * @param paraMap
     * @param iBaseRequestCallBack
     */
    void putData(String url, String methodName, Map paraMap, final IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * 加载数据
     *
     * @param paraMap
     * @param iBaseRequestCallBack
     */
    void loadData(String url, String methodName, String errorMethodName, Map paraMap, final IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * 加载数据
     *
     * @param paraMap
     * @param iBaseRequestCallBack
     */
    void loadData(String url, Map paraMap, final IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @author lixiaojin
     * @createon 2018-09-01 8:52
     * @Describe 文件上传
     */
    void upload(String url, String methodName, Map paraMap, MultipartBody.Part[] filePart, final IBaseRequestCallBack<T> iBaseRequestCallBack);


    /**
     * 注销
     */
    void onUnsubscribe();
}
