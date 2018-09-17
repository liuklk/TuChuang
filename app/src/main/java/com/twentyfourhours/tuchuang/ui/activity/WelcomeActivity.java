package com.twentyfourhours.tuchuang.ui.activity;

import android.os.Handler;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.common.util.PreferenceUtils;
import com.twentyfourhours.tuchuang.widget.BannerWl;

/**
 * Created by Administrator on 2018/1/18.
 */

public class WelcomeActivity extends BaseActivity {

    private BannerWl mBanner;
    private Handler mHandler = new Handler();
    private boolean mAutoUpdate;

    @Override
    protected void initListener() {
        if (mBanner!=null) {
            mBanner.setOnGotoListener(new BannerWl.OnGotoListener() {
                @Override
                public void action() {
                    PreferenceUtils.setAutoUpdate(WelcomeActivity.this, true);
                    goTo(MainActivity.class, true);
                }
            });
        }
    }

    @Override
    protected void initData() {
        if (mAutoUpdate) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            goTo(MainActivity.class, true);
                        }
                    });
                }
            }, 1500);
        }else {
            mBanner = (BannerWl) findViewById(R.id.banner);
        }

    }

    @Override
    protected int getLayoutId() {
        mAutoUpdate = PreferenceUtils.getAutoUpdate(WelcomeActivity.this);
        boolean success = mAutoUpdate;
        if (success) {
            return R.layout.activity_welcome;
        } else {
            return R.layout.activity_welcome_w;
        }
    }

}
