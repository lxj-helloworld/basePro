package com.example.xiaojin20135.basemodule.retrofit.helper;

import android.util.Log;

import com.example.xiaojin20135.basemodule.activity.BaseApplication;
import com.example.xiaojin20135.basemodule.retrofit.api.IServiceApi;
import com.example.xiaojin20135.basemodule.retrofit.util.NetUtil;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lixiaojin on 2018-07-12.
 * 功能描述：
 */

public enum  RetrofitManager {
    RETROFIT_MANAGER;
    private static final String TAG = "RetrofitManager";
    //地址
    public String BASE_URL = "";
    //短缓存有效期为1分钟
    public final int CACHE_STALE_SHORT = 120;
    //长缓存有效期为7天
    public final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public final String CACHE_CONTROL_NETWORK = "max-age=0";
    private IServiceApi iServiceApi;
    private int DEFAULT_TIMEOUT = 120;
    OkHttpClient.Builder httpClientBuilder = null;

    private boolean selfDefineHttps = false;

    RetrofitManager(){
    }

    public RetrofitManager setConnectTimeout(int timeout){
        this.DEFAULT_TIMEOUT=timeout;
        return this;
    }

    public void init(String url){
        BASE_URL  = url;
        initOkHttpClient(null);
        if(selfDefineHttps){
            try {
                setCertificates(httpClientBuilder, BaseApplication.getInstance ().getAssets().open("server.cer"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder.build ())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iServiceApi = retrofit.create(IServiceApi.class);
    }
    public void init(String url,Interceptor interceptor){
        BASE_URL  = url;
        initOkHttpClient(interceptor);
        if(selfDefineHttps){
            try {
                setCertificates(httpClientBuilder, BaseApplication.getInstance ().getAssets().open("server.cer"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder.build ())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iServiceApi = retrofit.create(IServiceApi.class);
    }

    public static RetrofitManager builder() {
        return RETROFIT_MANAGER;
    }

    public IServiceApi getService() {
        return iServiceApi;
    }

    private void initOkHttpClient(Interceptor interceptor) {
        ClearableCookieJar cookieJar = new PersistentCookieJar (new SetCookieCache (),new SharedPrefsCookiePersistor (BaseApplication.getInstance()));
        //创建okhttp，写入缓存机制，还有addInteceptor
        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS); //连接超时时间
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS); //写超时时间
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS); //读超时时间
        File cacheFile = new File(BaseApplication.getInstance().getCacheDir(),"topscommFire");
        Cache cache = new Cache(cacheFile,1024*1024*100);
        httpClientBuilder.cache(cache);
        httpClientBuilder.cookieJar(cookieJar);
        if(interceptor!=null){
            httpClientBuilder.addInterceptor(interceptor);
        }
        httpClientBuilder.addInterceptor(LoggingInterceptor);
        httpClientBuilder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        httpClientBuilder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        httpClientBuilder.hostnameVerifier (new HostnameVerifier () {
            @Override
            public boolean verify (String hostname, SSLSession session) {
                return true;
            }
        });
    }

    //okhttp拦截器
    private static final Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.d (TAG,String.format("Sending request %s on %s%n%s", request.url(),  chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.d (TAG,String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!NetUtil.isNetworkConnected()){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d (TAG,"no network");
            }
            Response originalResponse = chain.proceed(request);
            if(NetUtil.isNetworkConnected()){
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };


    public void setCertificates(OkHttpClient.Builder clientBuilder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory .generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {

                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance( TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom ());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelfDefineHttps (boolean selfDefineHttps) {
        this.selfDefineHttps = selfDefineHttps;
    }
}
