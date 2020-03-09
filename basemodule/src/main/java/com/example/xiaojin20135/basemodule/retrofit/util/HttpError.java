package com.example.xiaojin20135.basemodule.retrofit.util;

import android.content.Context;

import com.example.xiaojin20135.basemodule.util.CrashHandler;
import com.example.xiaojin20135.basemodule.util.NetworkUtil;
import com.google.gson.JsonSyntaxException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

public enum HttpError {
    HTTP_ERROR;

    public static final int UNAUTHORIZED = 401; //没有提供认证信息
    public static final int FORBIDDEN = 403; //请求的资源不允许，就是说没有权限
    public static final int NOT_FOUND = 404; //请求的内容不存在
    public static final int REQUEST_TIMEOUT = 408; //客户端请求超时
    public static final int INTERNAL_SERVER_ERROR = 500; //服务器错误
    public static final int BAD_GATEWAY = 502; //网关错误
    public static final int SERVICE_UNAVAILABLE = 503; //服务暂时不可用
    public static final int GATEWAY_TIMEOUT = 504; //网关超时

    /*
     * @author lixiaojin
     * create on 2019-11-05 13:46
     * description:
     */
    public static String getErrorMessage(Throwable throwable, Context context) {
        String message = "";
        CrashHandler.getInstance().handleException(throwable);

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                    message = "网络错误(" + httpException.code() + ")";
                    break;
            }
        } else if ( throwable instanceof SocketTimeoutException || throwable instanceof SocketException) {
            message = "网络连接超时，请检查您的网络状态！";
        }else if( throwable instanceof  IllegalArgumentException || throwable instanceof JsonSyntaxException){
            message="未能请求到数据！";
        }else if (throwable  instanceof UnknownHostException ){
            if (!NetworkUtil.isNetAvailable(context)) {
                message="hello?好像没网络啊！";
                //无网络
            } else {
                //主机挂了，也就是你服务器关了
                message="服务器开小差,请稍后重试！";
            }
        }else {//其他错误
            message="哎呀故障了！";
        }
        return message;
    }


}
