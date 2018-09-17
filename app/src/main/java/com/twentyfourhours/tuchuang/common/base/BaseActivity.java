package com.twentyfourhours.tuchuang.common.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.twentyfourhours.tuchuang.common.util.LogUtil;
import com.twentyfourhours.tuchuang.common.util.ToastUtil;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Twentyfourhours on 2018/1/4.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {}
        }
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        //Mob平台授权
        initTitleBar();
        initView();
        initData();
        initData(savedInstanceState);
        initListener();
    }

    protected void initTitleBar() {

    }

    protected void initView(){

    }

    protected void initData(Bundle bundle){

    }

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /**
     * 页面跳转
     * @param clzz
     * @param finish
     */
    protected void goTo(Class clzz,boolean finish){
        Intent intent=new Intent(this,clzz);
        startActivity(intent);
        if (finish){
            finish();
        }
    }

    protected void toast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.toast(BaseActivity.this,msg);
            }
        });
    }

    protected void logD(String msg){
        LogUtil.logD(getClass().getSimpleName(),msg);
    }
}
