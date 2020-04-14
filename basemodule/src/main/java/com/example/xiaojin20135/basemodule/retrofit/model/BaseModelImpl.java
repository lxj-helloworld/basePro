package com.example.xiaojin20135.basemodule.retrofit.model;

import android.content.Context;
import android.util.Log;

import com.example.xiaojin20135.basemodule.retrofit.api.IServiceApi;
import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lixiaojin on 2018-07-12.
 * 功能描述：
 */

public class BaseModelImpl extends BaseModel implements IBaseModel<ResponseBean>{
    private final static String TAG = "BaseModelImpl";
    private Context context;
    private IServiceApi iServiceApi;
    private CompositeSubscription compositeSubscription;
    private Gson gson;

    public BaseModelImpl(Context context){
        this.context = context;
        iServiceApi = retrofitManager.getService ();
        compositeSubscription = new CompositeSubscription ();
        gson=new Gson();
    }


    @Override
    public void loadData (String url,final String methodName,Map paraMap, final IBaseRequestCallBack<ResponseBean> iBaseRequestCallBack) {
        Log.d (TAG,"paraMap = " + paraMap.toString ());
        compositeSubscription.add (iServiceApi.load (url,paraMap)
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io())
                .subscribe (new Subscriber<ResponseBean> () {
                    @Override
                    public void onStart () {
                        super.onStart ();
                        //在subscribe所发生的线程被调用，如果你的subscribe不是主线程，则会出错，则需要指定主线程
                        iBaseRequestCallBack.beforeRequest ();
                    }
                    @Override
                    public void onCompleted () {
                        //回调接口，请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete ();
                    }
                    @Override
                    public void onError (Throwable e) {
                        //回调接口，请求异常
                        iBaseRequestCallBack.requestError (e);
                    }
                    @Override
                    public void onNext (ResponseBean responseBean) {
                        //回调接口，请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess (responseBean,methodName);

                    }
                }));
    }

    @Override
    public void getData(String url, final String methodName, Map paraMap, final IBaseRequestCallBack<ResponseBean> iBaseRequestCallBack) {
        compositeSubscription.add (iServiceApi.get (url,paraMap)
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io())
                .subscribe (new Subscriber<ResponseBean> () {
                    @Override
                    public void onStart () {
                        super.onStart ();
                        //在subscribe所发生的线程被调用，如果你的subscribe不是主线程，则会出错，则需要指定主线程
                        iBaseRequestCallBack.beforeRequest ();
                    }
                    @Override
                    public void onCompleted () {
                        //回调接口，请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete ();
                    }
                    @Override
                    public void onError (Throwable e) {
                        //回调接口，请求异常
                        iBaseRequestCallBack.requestError (e);
                    }
                    @Override
                    public void onNext (ResponseBean responseBean) {
                        //回调接口，请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess (responseBean,methodName);

                    }
                }));
    }

    @Override
    public void postData(String url, final String methodName, Map paraMap, final IBaseRequestCallBack<ResponseBean> iBaseRequestCallBack) {
        String obj=gson.toJson(paraMap);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        compositeSubscription.add (iServiceApi.post (url,body)
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io())
                .subscribe (new Subscriber<ResponseBean> () {
                    @Override
                    public void onStart () {
                        super.onStart ();
                        //在subscribe所发生的线程被调用，如果你的subscribe不是主线程，则会出错，则需要指定主线程
                        iBaseRequestCallBack.beforeRequest ();
                    }
                    @Override
                    public void onCompleted () {
                        //回调接口，请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete ();
                    }
                    @Override
                    public void onError (Throwable e) {
                        //回调接口，请求异常
                        iBaseRequestCallBack.requestError (e);
                    }
                    @Override
                    public void onNext (ResponseBean responseBean) {
                        //回调接口，请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess (responseBean,methodName);

                    }
                }));
    }

    @Override
    public void loadData (String url,final String methodName,final String errorMethodName,Map paraMap, final IBaseRequestCallBack<ResponseBean> iBaseRequestCallBack) {
        Log.d (TAG,"paraMap = " + paraMap.toString ());
        compositeSubscription.add (iServiceApi.load (url,paraMap)
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io())
                .subscribe (new Subscriber<ResponseBean> () {
                    @Override
                    public void onStart () {
                        super.onStart ();
                        //在subscribe所发生的线程被调用，如果你的subscribe不是主线程，则会出错，则需要指定主线程
                        iBaseRequestCallBack.beforeRequest ();
                    }
                    @Override
                    public void onCompleted () {
                        //回调接口，请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete ();
                    }
                    @Override
                    public void onError (Throwable e) {
                        //回调接口，请求异常
                        iBaseRequestCallBack.requestError (e);
                    }
                    @Override
                    public void onNext (ResponseBean responseBean) {
                        //回调接口，请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess (responseBean,methodName,errorMethodName);

                    }
                }));
    }
    @Override
    public void loadData (String url, Map paraMap, final IBaseRequestCallBack<ResponseBean> iBaseRequestCallBack) {
        Log.d (TAG,"paraMap = " + paraMap.toString ());
        compositeSubscription.add (iServiceApi.load (url,paraMap)
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io())
                .subscribe (new Subscriber<ResponseBean> () {
                    @Override
                    public void onStart () {
                        super.onStart ();
                        //在subscribe所发生的线程被调用，如果你的subscribe不是主线程，则会出错，则需要指定主线程
                        iBaseRequestCallBack.beforeRequest ();
                    }
                    @Override
                    public void onCompleted () {
                        //回调接口，请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete ();
                    }
                    @Override
                    public void onError (Throwable e) {
                        //回调接口，请求异常
                        iBaseRequestCallBack.requestError (e);
                    }
                    @Override
                    public void onNext (ResponseBean responseBean) {
                        //回调接口，请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess (responseBean);
                    }
                }));
    }

   
    @Override
    public void upload (String url,final String methodName, Map paraMap, MultipartBody.Part[] filePart, final IBaseRequestCallBack<ResponseBean> iBaseRequestCallBack) {
        Log.d (TAG,"paraMap = " + paraMap.toString ());
        Log.d (TAG,"filePart = " + filePart.toString ());
        compositeSubscription.add (iServiceApi.uploadFile (url,paraMap,filePart)
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io())
                .subscribe (new Subscriber<ResponseBean> () {
                    @Override
                    public void onStart () {
                        super.onStart ();
                        //在subscribe所发生的线程被调用，如果你的subscribe不是主线程，则会出错，则需要指定主线程
                        iBaseRequestCallBack.beforeRequest ();
                    }
                    @Override
                    public void onCompleted () {
                        //回调接口，请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete ();
                    }
                    @Override
                    public void onError (Throwable e) {
                        //回调接口，请求异常
                        iBaseRequestCallBack.requestError (e);
                    }
                    @Override
                    public void onNext (ResponseBean responseBean) {
                        //回调接口，请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess (responseBean,methodName);

                    }
                }));
       /* Call<ResponseBean> call= iServiceApi.upload (url,paraMap,filePart);
        call.enqueue (new Callback<ResponseBean> () {
            @Override
            public void onResponse (Call<ResponseBean> call, Response<ResponseBean> response) {
                ResponseBean responseBean=response.body();
                *//*ResponseBean responseBean = new ResponseBean ();
                ActionResult actionResult = new ActionResult ();
                actionResult.setSuccess (response.isSuccessful ());
                actionResult.setMessage (response.message ());
                responseBean.setActionResult (actionResult);*//*
                iBaseRequestCallBack.requestSuccess (responseBean,methodName);
            }

            @Override
            public void onFailure (Call<ResponseBean> call, Throwable t) {
                iBaseRequestCallBack.requestError (t);
            }
        });*/
    }

    @Override
    public void onUnsubscribe () {
        compositeSubscription.clear();
    }
}
