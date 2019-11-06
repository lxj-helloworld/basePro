package com.example.xiaojin20135.basemodule.retrofit.util;

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
    public static String getErrorMessage(Throwable throwable){
        String message = "";

        if(throwable instanceof HttpException){
            HttpException httpException = (HttpException)throwable;
            switch (httpException.code()){
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
        }else{
            message = throwable.getMessage();
        }
        return message;
    }


}
