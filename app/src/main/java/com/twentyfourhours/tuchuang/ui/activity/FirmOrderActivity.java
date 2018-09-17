package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.FirmOderAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.AddressManageBean;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.widget.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/15.
 */
public class FirmOrderActivity extends BaseActivity {
    private static final int REQUEST_CODE = 500;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.list_view)
    MyListView mListView;
    @BindView(R.id.coupon)
    RelativeLayout mCoupon;
    @BindView(R.id.weixin)
    ImageView mWeixin;
    @BindView(R.id.check_wechat)
    CheckBox mCheckWechat;
    @BindView(R.id.zhifubao)
    ImageView mZhifubao;
    @BindView(R.id.check_alipay)
    CheckBox mCheckAlipay;
    @BindView(R.id.total_tv)
    TextView mTotalTv;
    @BindView(R.id.pay_btn)
    TextView mPayBtn;
    @BindView(R.id.pay_click)
    LinearLayout mPayClick;
    @BindView(R.id.address_click)
    RelativeLayout mAddressClick;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.phone_number)
    TextView mPhoneNumber;
    @BindView(R.id.none_address)
    TextView mNoneAddress;
    @BindView(R.id.address_view)
    RelativeLayout mAddressView;
    private FirmOderAdapter mAdapter;
    private ArrayList<StoreBeen> mStoreBeens;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mCheckWechat.setChecked(false);
        mCheckAlipay.setChecked(true);

        mAdapter = new FirmOderAdapter(this);
        mStoreBeens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StoreBeen been = new StoreBeen();
//            been.setCheck(true);
//            been.setItemCount(1);
            mStoreBeens.add(been);
        }
        mAdapter.setData(mStoreBeens);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_firm_oder;
    }

    @OnClick({R.id.back, R.id.address_click, R.id.coupon, R.id.check_wechat, R.id.check_alipay, R.id.pay_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.address_click:
                Intent intent = new Intent(this, AddressManagerActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.coupon:
                goTo(CouponActivity.class, false);
                break;
            case R.id.check_wechat:
                mCheckWechat.setChecked(true);
                mCheckAlipay.setChecked(false);
                break;
            case R.id.check_alipay:
                mCheckWechat.setChecked(false);
                mCheckAlipay.setChecked(true);
                break;
            case R.id.pay_click:
                goTo(PayActivity.class, false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            AddressManageBean bean = (AddressManageBean) data.getSerializableExtra("SelectAddress");
            if (requestCode == REQUEST_CODE) {
                mNoneAddress.setVisibility(View.GONE);
                mAddressView.setVisibility(View.VISIBLE);
                mAddress.setText(bean.getAddress()+bean.getDetail());
                mPhoneNumber.setText(bean.getPhoneNumber());
                mName.setText(bean.getName());
            }

        }
    }

}
