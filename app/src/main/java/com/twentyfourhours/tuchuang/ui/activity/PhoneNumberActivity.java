package com.twentyfourhours.tuchuang.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/18.
 */
public class PhoneNumberActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.phone_number)
    TextView mPhoneNumber;
    @BindView(R.id.change_phone_number)
    Button mChangePhoneNumber;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_number;
    }

    @OnClick({R.id.back, R.id.change_phone_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.change_phone_number:
                goTo(BindPhoneNumberActivity.class,false);
                break;
        }
    }
}
