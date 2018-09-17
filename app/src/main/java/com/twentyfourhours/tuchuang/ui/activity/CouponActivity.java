package com.twentyfourhours.tuchuang.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.CouponNoUseAdapter;
import com.twentyfourhours.tuchuang.adapter.CouponUseAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.widget.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/15.
 */
public class CouponActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.shure_btn)
    Button mShureBtn;
    @BindView(R.id.input_code)
    EditText mInputCode;
    @BindView(R.id.exchange)
    Button mExchange;
    @BindView(R.id.list_view_use)
    MyListView mListViewUse;
    @BindView(R.id.list_view_no_use)
    MyListView mListViewNoUse;
    private CouponUseAdapter mAdapterUse;
    private CouponNoUseAdapter mAdapterNoUse;
    private ArrayList<StoreBeen> mStoreBeens;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mAdapterUse = new CouponUseAdapter(this);
        mStoreBeens = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            StoreBeen been = new StoreBeen();
            been.setCheck(true);
            been.setMyCoupon(false);
//            been.setItemCount(1);
            mStoreBeens.add(been);
        }
        mAdapterUse.setData(mStoreBeens);
        mListViewUse.setAdapter(mAdapterUse);

        mAdapterNoUse = new CouponNoUseAdapter(this);
        mStoreBeens = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            StoreBeen been = new StoreBeen();
//            been.setCheck(true);
//            been.setItemCount(1);
            mStoreBeens.add(been);
        }
        mAdapterNoUse.setData(mStoreBeens);
        mListViewNoUse.setAdapter(mAdapterNoUse);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @OnClick({R.id.back, R.id.shure_btn,R.id.exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.shure_btn:
                finish();
                break;
            case R.id.exchange:
                exchange();
                break;
        }
    }

    /**
     * 兑换
     */
    private void exchange() {

    }

}
