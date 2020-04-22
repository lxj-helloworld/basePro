package com.example.xiaojin20135.basemodule.fragment.base;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiaojin20135.basemodule.R;
import com.example.xiaojin20135.basemodule.activity.BaseActivity;
import com.example.xiaojin20135.basemodule.retrofit.bean.ActionResult;
import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;
import com.example.xiaojin20135.basemodule.retrofit.helper.RetrofitManager;
import com.example.xiaojin20135.basemodule.retrofit.presenter.PresenterImpl;
import com.example.xiaojin20135.basemodule.retrofit.util.HttpError;
import com.example.xiaojin20135.basemodule.retrofit.view.IBaseView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.MultipartBody;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    public static String TAG = "";
    private PresenterImpl presenterImpl;
    public  boolean isShowProgressDialog=true;

    private String lastUrl = ""; //最后一次请求url
    private Map lastMap = new HashMap(); //最后一次请求参数
    private String lastMethodName = "";//最后一次请求的方法名
    private String lastErrorMethodName = "";//最后一次请求的错误方法名
    private MultipartBody.Part[] lastFilePart = null;//最后一次请求附件
    private String lastSuffix = "";//最后一次请求后缀

    private int lastReqCode = -1;

    public BaseFragment () {
        TAG = this.getClass ().getSimpleName ();
        Log.d("BaseActivity",TAG);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenterImpl = new PresenterImpl (this,getContext ());
        TextView textView = new TextView (getActivity ());
        textView.setText (R.string.hello_blank_fragment);
        return textView;
    }

    protected void bindView(View view){
        ButterKnife.bind (this,view);
    }

    protected abstract void initView(View view);

    protected abstract void initEvents(View view);



    @Override
    public void showProgress () {
        if(getActivity ()!=null&&isShowProgressDialog){
            ((BaseActivity)getActivity ()).showProgress ();
        }
    }


    @Override
    public void setProgressText (String text) {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).setProgressText (text);
        }
    }

    @Override
    public void setProgressValue (int value) {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).setProgressValue (value);
        }
    }

    @Override
    public void showProgress (boolean hideTitle, String message, boolean cancled) {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showProgress (hideTitle, message, cancled);
        }
    }

    @Override
    public void dismissProgress () {
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).dismissProgress ();
        }
    }

    public void showAlertDialog(Context context, String text, String title){
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showAlertDialog (context, text, title);
        }
    }

    public void showAlertDialog(Context context, String text){
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showAlertDialog (context, text);
        }
    }

    public void showAlertDialog(Context context,int id){
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showAlertDialog (context, id);
        }
    }


    /**
     * @Description: 平台2.0新请求方式
     * @Parames [url, paraMap]
     * @author 龙少
     * @date 2020/4/14
     * @version V1.0
     */
    public void getData(String url, String methodName, Map paraMap) {
        lastUrl = url;
        lastMap = paraMap;
        presenterImpl.getData(url, methodName, paraMap);
    }

    /**
     * @Description: 平台2.0新请求方式
     * @Parames [url, paraMap]
     * @author 龙少
     * @date 2020/4/14
     * @version V1.0
     */
    public void HttpPutData(String url, String methodName, Map paraMap) {
        lastUrl = url;
        lastMap = paraMap;
        presenterImpl.putData(url, methodName, paraMap);
    }

    /**
     * @Description: 平台2.0新请求方式
     * @Parames [url, paraMap]
     * @author 龙少
     * @date 2020/4/14
     * @version V1.0
     */
    public void postData(String url, String methodName, Map paraMap) {
        lastUrl = url;
        lastMap = paraMap;
        presenterImpl.postData(url, methodName, paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:23
     * @Describe 请求数据 ，带完整路径，自定义回调方法
     */
    public void tryToGetData(String url,String methodName,Map paraMap) {
        lastReqCode = 1;
        lastUrl = url;
        lastMethodName = methodName;
        lastMap = paraMap;
        presenterImpl.loadData (url + ".json",methodName,paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:23
     * @Describe 请求数据 ，带完整路径，自定义回调方法
     */
    public void tryToGetData(String url,String methodName,String errorMethodName,Map paraMap) {
        lastReqCode = 2;
        lastUrl = url;
        lastMethodName = methodName;
        lastErrorMethodName = errorMethodName;
        lastMap = paraMap;
        presenterImpl.loadData (url + ".json",methodName,errorMethodName,paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:23
     * @Describe 请求数据 ，带完整路径，固定回调方法
     */
    public void tryToGetData(String url,Map paraMap) {
        lastReqCode = 3;
        lastUrl = url;
        lastMap = paraMap;
        presenterImpl.loadData (url + ".json",paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:39
     * @Describe 请求数据，带请求方法，并自定义回调方法
     */
    public void getDataWithMethod(String url,Map paraMap) {
        lastReqCode = 4;
        lastUrl = url;
        lastMap = paraMap;
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + ".json",url,paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-09-01 9:35
     * @Describe 上传文件
     */
    public void uploadFileWithMethod(String url,Map paraMap, MultipartBody.Part[] filePart){
        lastReqCode = 5;
        lastUrl = url;
        lastMap = paraMap;
        lastFilePart = filePart;
        presenterImpl.uploadFile (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + ".json",url,paraMap,filePart);
    }


    /**
     * 文件上传，带完整地址
     * @param url
     * @param paraMap
     * @param filePart
     */
    public void uploadFileWithTotalUrl(String url,Map paraMap, MultipartBody.Part[] filePart){
        lastReqCode = 6;
        lastUrl = url;
        lastMap = paraMap;
        lastFilePart = filePart;
        presenterImpl.uploadFile (url + ".json",url,paraMap,filePart);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-17 10:39
     * @Describe 请求数据，带请求方法，固定回调方法
     */
    public void getDataWithCommonMethod(String url,Map paraMap) {
        lastReqCode = 7;
        lastUrl = url;
        lastMap = paraMap;
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + ".json",paraMap);
    }


    /**
     * @author lixiaojin
     * @createon 2018-07-19 8:39
     * @Describe 请求数据，带请求方法和和后缀，自定义回调方法
     */
    public void getDataWithMethod(String url,String suffix,Map paraMap){
        lastReqCode = 8;
        lastUrl = url;
        lastSuffix = suffix;
        lastMap = paraMap;
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + suffix,url,paraMap);
    }

    /**
     * @author lixiaojin
     * @createon 2018-07-19 8:39
     * @Describe 请求数据，带请求方法和和后缀，固定回调方法
     */
    public void getDataWithCommonMethod(String url,String suffix,Map paraMap){
        lastReqCode = 9;
        lastUrl = url;
        lastSuffix = suffix;
        lastMap = paraMap;
        presenterImpl.loadData (RetrofitManager.RETROFIT_MANAGER.BASE_URL + url + suffix,paraMap);
    }

    @Override
    public void loadDataSuccess (Object tData) {
        Log.d (TAG,"loadDataSuccess");
    }

    @Override
    public void loadError (Throwable throwable) {
        Log.d (TAG,"loadDataError");
        requestError (HttpError.getErrorMessage(throwable));
        //((BaseActivity)getActivity ()).showToast (getActivity (),throwable.getLocalizedMessage ());
    }

    @Override
    public void loadComplete () {
        Log.d (TAG,"loadDataComplete");
    }

    @Override
    public void loadSuccess (Object callBack) {
        Log.d (TAG,"loadSuccess");
        ResponseBean responseBean = (ResponseBean)callBack;
        ActionResult actionResult = responseBean.getActionResult ();
        if(actionResult.getSuccess ()){
            loadDataSuccess (callBack);
        }else{
            requestError (responseBean);
        }
    }

    @Override
    public void loadSuccess (Object tData, String methodName) {
        int index = methodName.lastIndexOf ("/");
        if(index < 0){
            index = 0;
        }else{
            index++;
        }
        methodName = methodName.substring (index);
        Log.d (TAG,"loadDataSuccess with methodName :" + methodName);
        ResponseBean responseBean = (ResponseBean)tData;
        ActionResult actionResult = responseBean.getActionResult ();
        if (actionResult==null){
            actionResult=new ActionResult();
            actionResult.setSuccess(false);
        }
        if(actionResult.getSuccess ()||responseBean.isSuccess()){
            if(methodName != null && !methodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(methodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"没有找到自定义回调："+e.getLocalizedMessage ());
                }
            }else{
                ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"not found "+methodName+" method");
            }
        }else{
            requestError (responseBean);
        }
    }
    @Override
    public void loadSuccess (Object tData, String methodName,String errorMethodName) {

        ResponseBean responseBean = (ResponseBean)tData;
        ActionResult actionResult = responseBean.getActionResult ();
        if(actionResult.getSuccess ()){
            int index = methodName.lastIndexOf ("/");
            if(index < 0){
                index = 0;
            }else{
                index++;
            }
            methodName = methodName.substring (index);
            Log.d (TAG,"loadDataSuccess with methodName :" + methodName);
            if(methodName != null && !methodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(methodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"没有找到自定义回调："+e.getLocalizedMessage ());
                }
            }else{
                ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"not found "+methodName+" method");
            }
        }else{
            int index = errorMethodName.lastIndexOf ("/");
            if(index < 0){
                index = 0;
            }else{
                index++;
            }
            errorMethodName = errorMethodName.substring (index);
            Log.d (TAG,"loadDatafail with methodName :" + errorMethodName);
            if(errorMethodName != null && !errorMethodName.equals ("")){
                try {
                    Class c = this.getClass();
                    Method m1 = c.getDeclaredMethod(errorMethodName,new Class[]{ResponseBean.class});
                    m1.invoke(this,new Object[]{responseBean});
                    Log.d (TAG,"调用自定义方法");
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),e.getLocalizedMessage ());
                }
            }else{
                ((BaseActivity)getActivity ()).showAlertDialog (getActivity (),"not found "+errorMethodName+" method");
            }
        }
    }
    @Override
    public void requestError (ResponseBean responseBean) {
        requestError (responseBean.getActionResult ().getMessage ());
        if (responseBean.isTimeout()||responseBean.getCode().equals("401")) {
            cancleRequest();
            reStartApp();
        }
    }

    public void cancleRequest(){
        if (presenterImpl!=null){
            presenterImpl.unSubscribe();
        }
    }

    public void reStartApp(){
        Intent intent =getActivity ().getPackageManager()
            .getLaunchIntentForPackage(getActivity().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public void requestError (String message) {
        Log.d (TAG,"requestError : " + message);
        Log.e(TAG,"**********************************\r\n 当前请求信息：lastUrl = " + lastUrl);
        Log.e(TAG,"\r\n*lastMethodName = " + lastMethodName);
        Log.e(TAG,"\r\n*lastMap = " + lastMap.toString());
        Log.e(TAG,"\r\n*lastErrorMethodName = " + lastErrorMethodName);
        Log.e(TAG,"\r\n*lastFilePart = " + lastFilePart);
        Log.e(TAG,"\r\n*lastSuffix = " + lastSuffix);
        Log.e(TAG,"\r\n**********************************");
        if(getActivity ()!=null) {
            ((BaseActivity) getActivity ()).showToast (getActivity (), message);
        }
    }

    /*
     * @author lixiaojin
     * create on 2019-11-06 10:56
     * description: HTTP错误后重试
     */
    public void tryAgain(){
        switch (lastReqCode){
            case 1:
                tryToGetData(lastUrl,lastMethodName,lastMap);
                break;
            case 2:
                tryToGetData(lastUrl,lastMethodName,lastErrorMethodName,lastMap);
                break;
            case 3:
                tryToGetData(lastUrl,lastMap);
                break;
            case 4:
                getDataWithMethod(lastUrl,lastMap);
                break;
            case 5:
                uploadFileWithMethod(lastUrl,lastMap,lastFilePart);
                break;
            case 6:
                uploadFileWithTotalUrl(lastUrl,lastMap,lastFilePart);
                break;
            case 7:
                getDataWithCommonMethod(lastUrl,lastMap);
                break;
            case 8:
                getDataWithMethod(lastUrl,lastSuffix,lastMap);
                break;
            case 9:
                getDataWithCommonMethod(lastUrl,lastSuffix,lastMap);
                break;
        }
    }


    /**
     * 界面跳转，不传参
     * @param tClass
     */
    protected void canGo(Class<?> tClass){
        canGo (tClass,null);
    }

    /**
     * 界面跳转，带参数
     * @param tClass
     * @param bundle
     */
    protected void canGo(Class<?> tClass,Bundle bundle){
        Intent intent = new Intent (getActivity (),tClass);
        if(bundle != null){
            intent.putExtras (bundle);
        }
        startActivity (intent);
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 跳转到目标页面并杀死当前页面
     */
    protected void canGoThenKill(Class<?> tClass){
        canGoThenKill (tClass,null);
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 跳转到目标页面，并杀死当前页面，带参数
     */
    protected void canGoThenKill(Class<?> tClass,Bundle bundle){
        canGo (tClass,bundle);
        getActivity ().finish ();
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 跳转到目标位置，并返回结果
     */
    protected void canGoForResult(Class<?> tClass,int requestCode){
        Intent intent = new Intent (getActivity (),tClass);
        startActivityForResult (intent,requestCode);
    }

    /**
     * @author lixiaojin
     * @create 2018-07-14
     * @Describe 带参数跳转到目标位置并返回结果，
     */
    protected void canGoForResult(Class<?> tClass,int requestCode,Bundle bundle){
        Intent intent = new Intent (getActivity (),tClass);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult (intent,requestCode);
    }

    @Override
    public void onPause () {
        dismissProgress ();
        super.onPause ();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancleRequest();
    }
}
