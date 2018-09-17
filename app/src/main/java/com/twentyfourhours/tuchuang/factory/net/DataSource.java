package com.twentyfourhours.tuchuang.factory.net;

/**
 * @Des  数据源
 * @Auther Administrator
 * @date 2018/4/3  10:16
 */

public interface DataSource {

    /**
     * 数据加载的回调
     * @param <T>
     */
    interface Callback<T> extends SuccessCallback<T> ,FailCallback{

    }

    /**
     * 数据加载成功的回调
     * @param <T>
     */
    interface SuccessCallback<T>{
        void onDataLoaded(int type, T t);
    }

    /**
     * 数据加载失败的回调
     */
    interface FailCallback{
        void onDataLoadFailed(int type, String msg);
    }
}
