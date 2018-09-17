package com.twentyfourhours.tuchuang.ui.activity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/18.
 */
public class BindPhoneNumberActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.phone_number_edit)
    EditText mPhoneNumberEdit;
    @BindView(R.id.shure_edit)
    EditText mShureEdit;
    @BindView(R.id.shure_btn)
    Button mShureBtn;
    @BindView(R.id.bind_btn)
    Button mBindBtn;
    private int time=60;
    private Handler mHandler=new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @OnClick({R.id.back, R.id.shure_btn, R.id.bind_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.shure_btn:
                getShureNumber();
                break;
            case R.id.bind_btn:
                bind();
                break;
        }
    }

    @Override
    protected void initListener() {
        mPhoneNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isMobileNO(mPhoneNumberEdit.getText().toString().trim())) {
                    mShureBtn.setClickable(false);
                    mShureBtn.setBackgroundResource(R.drawable.shure_btn_bg_press);
                } else {
                    mShureBtn.setClickable(true);
                    mShureBtn.setBackgroundResource(R.drawable.shure_btn_bg);
                }
                mBindBtn.setClickable(isMobileNO(mPhoneNumberEdit.getText().toString().trim()));
                mBindBtn.setBackgroundResource(isMobileNO(mPhoneNumberEdit.getText().toString().trim()) ? R.drawable.login_btn_bg_selector : R.drawable.login_btn_bg_press);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        if (!isMobileNO(mPhoneNumberEdit.getText().toString().trim())) {
            mShureBtn.setClickable(false);
            mShureBtn.setBackgroundResource(R.drawable.shure_btn_bg_press);
        } else {
            mShureBtn.setClickable(true);
            mShureBtn.setBackgroundResource(R.drawable.shure_btn_bg);
        }
        mBindBtn.setClickable(isMobileNO(mPhoneNumberEdit.getText().toString().trim()));
        mBindBtn.setBackgroundResource(isMobileNO(mPhoneNumberEdit.getText().toString().trim()) ? R.drawable.login_btn_bg_selector : R.drawable.login_btn_bg_press);
    }

    private void getShureNumber() {
        if (time!=60){
            return;
        }
        if (!isMobileNO(mPhoneNumberEdit.getText().toString().trim())) {
            toast("请输入正确的手机号");
            return;
        }
        mShureBtn.setText(time + "s");
        mShureBtn.setBackgroundResource(R.drawable.shure_btn_bg_press);
        postDelayed();
    }

    private void postDelayed() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time--;
                mShureBtn.setClickable(false);
                if (time == 30) {
                    mShureEdit.setText("ABCD");
                }
                if (time == 0) {
                    mShureBtn.setBackgroundResource(R.drawable.shure_btn_bg);
                    mShureBtn.setText("重新发送");
                    mShureBtn.setClickable(true);
                    time = 60;
                    return;
                }
                mShureBtn.setText(time + "s");
                postDelayed();
            }
        }, 1000);
    }

    private void bind() {
        String number = mPhoneNumberEdit.getText().toString().trim();
        String shure = mShureEdit.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            toast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(shure)) {
            toast("请输入验证码");
            return;
        }
        if (!isMobileNO(number)) {
            toast("请输入正确的手机号");
            return;
        }
//        goTo(MainActivity.class, true);
        finish();
    }

    private boolean isMobileNO(String phoneNumberStr) {
        String mobileNO = "((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}";
        if (TextUtils.isEmpty(phoneNumberStr)) {
            return false;
        } else {
            return phoneNumberStr.matches(mobileNO);
        }
    }
}
