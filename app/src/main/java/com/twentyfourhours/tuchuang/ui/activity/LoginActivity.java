package com.twentyfourhours.tuchuang.ui.activity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页
 * Created by Administrator on 2018/1/6.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.phone_number_edit)
    EditText mPhoneNumberEdit;
    //    @BindView(R.id.pre_number)
//    TextView mPreNumber;
    @BindView(R.id.shure_edit)
    EditText mShureEdit;
    @BindView(R.id.shure_btn)
    Button mShureBtn;
    @BindView(R.id.check_box)
    CheckBox mCheckBox;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_qq)
    TextView mLoginQq;
    @BindView(R.id.login_weixin)
    TextView mLoginWeixin;
    @BindView(R.id.back)
    ImageView mBack;
    private int time = 60;
    private Handler mHandler = new Handler();


    @Override
    protected void initListener() {
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLoginBtn.setClickable(isChecked);
                mLoginBtn.setBackgroundResource(mCheckBox.isChecked() ? R.drawable.login_btn_bg_selector : R.drawable.login_btn_bg_press);
            }
        });
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
        mLoginBtn.setClickable(mCheckBox.isChecked());
        mLoginBtn.setBackgroundResource(mCheckBox.isChecked() ? R.drawable.login_btn_bg_selector : R.drawable.login_btn_bg_press);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.shure_btn, R.id.login_btn, R.id.login_qq, R.id.login_weixin, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shure_btn:
                getShureNumber();
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.login_qq:
                //loginQQ();
                break;
            case R.id.login_weixin:

                //getCode();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void getShureNumber() {
        if (time != 60) {
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

    private void login() {
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
