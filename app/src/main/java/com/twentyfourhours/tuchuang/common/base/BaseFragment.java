package com.twentyfourhours.tuchuang.common.base;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twentyfourhours.tuchuang.common.util.LogUtil;
import com.twentyfourhours.tuchuang.common.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/4.
 */

public abstract class BaseFragment extends Fragment {
    private Handler mHandler=new Handler();
    protected Context mContext;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        init();
        return initView();
    }

    protected void init() {

    }

    protected abstract View initView();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    protected abstract void initListener();

    protected abstract void initData();

    protected void toast(final String msg){
        if (Looper.myLooper()==Looper.getMainLooper()){
            ToastUtil.toast(mContext,msg);
        }else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.toast(mContext,msg);
                }
            });
        }
    }

    protected void logD(String msg){
        LogUtil.logD(getClass().getSimpleName(),msg);
    }

    protected void goTo(Class clzz,boolean finish){
        Intent intent=new Intent(getActivity(),clzz);
        startActivity(intent);
        if (finish){
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
