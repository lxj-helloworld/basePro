package com.example.xiaojin20135.basemodule.retrofit.presenter;

import android.content.Context;

import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;
import com.example.xiaojin20135.basemodule.retrofit.model.BaseModelImpl;
import com.example.xiaojin20135.basemodule.retrofit.view.IBaseView;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by lixiaojin on 2018-07-13.
 * 功能描述：
 */

public class PresenterImpl extends BasePresenterImp<IBaseView,ResponseBean> implements IBasePresenter{
    private Context context = null;
    private BaseModelImpl baseModel = null;

    /**
     * 构造方法，具体业务的视图接口对象
     *
     * @param view
     */
    public PresenterImpl (IBaseView view,Context context) {
        super (view);
        this.context = context;
        this.baseModel = new BaseModelImpl (context);
    }



    @Override
    public void loadData (String url,String methodName, Map paraMap) {
        baseModel.loadData (url,methodName,paraMap,this);
    }

    @Override
    public void getData(String url, String methodName, Map paraMap) {
        baseModel.getData(url,methodName,paraMap,this);
    }

    @Override
    public void postData(String url, String methodName, Map paraMap) {
        baseModel.postData(url,methodName,paraMap,this);
    }

    @Override
    public void putData(String url, String methodName, Map paraMap) {
        baseModel.putData(url,methodName,paraMap,this);

    }

    @Override
    public void loadData (String url, String methodName, String errorMethodName, Map paraMap) {
        baseModel.loadData (url,methodName,errorMethodName,paraMap,this);
    }

    @Override
    public void uploadFile (String url, String methodMethod, Map paraMap, MultipartBody.Part[] filePart) {
        baseModel.upload (url,methodMethod,paraMap,filePart,this);
    }


    @Override
    public void loadData (String url, Map paraMap) {
        baseModel.loadData (url,paraMap,this);
    }

    @Override
    public void unSubscribe () {
        baseModel.onUnsubscribe();
    }


}
