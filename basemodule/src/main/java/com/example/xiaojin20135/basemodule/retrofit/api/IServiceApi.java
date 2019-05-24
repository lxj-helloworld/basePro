package com.example.xiaojin20135.basemodule.retrofit.api;

import com.example.xiaojin20135.basemodule.retrofit.bean.ResponseBean;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lixiaojin on 2018-07-12.
 * 功能描述：
 */

public interface IServiceApi {
    /**
     * 基本写法，传入URL地址以及参数
     * @param url
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Observable<ResponseBean> load(@Url String url,@FieldMap Map<String,String> options);

    /**
     * @author lixiaojin
     * @createon 2018-09-01 8:42
     * @Describe 文件上传
     */
    @Multipart
    @POST
    //@Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Call<ResponseBody> upload(@Url String url,@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part[] file);


    @Multipart
    @POST
        //@Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8") //添加
    Observable<ResponseBean> uploadFile(@Url String url,@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part[] file);
}
