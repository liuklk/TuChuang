package com.twentyfourhours.tuchuang.factory.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twentyfourhours.tuchuang.common.Const;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/5/10.
 */

public class NetWorkManager {

    private static Api mApi;  //全局网络接口

    private static Gson mGson = new GsonBuilder().create();

    private static final int  DEFAULT_CACHE_SIZE = 35 * 1024 * 1024;//缓存空间大小


    private static final Interceptor mInterceptor =new Interceptor(){

        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    };

    public static void init(Context context){
        //指定缓存路径
        String cachePath = context.getCacheDir().getAbsolutePath() + "/responses";

        File cacheDir = new File(cachePath);

        //配置OkHttpClient
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, DEFAULT_CACHE_SIZE))
                .addInterceptor(mInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

        //使用Retrofit实现api的方法
        mApi = retrofit.create(Api.class);
    }


    //提供一个共有的获取api的方法

    public static Api getApi(){
        return mApi ;
    }
}
