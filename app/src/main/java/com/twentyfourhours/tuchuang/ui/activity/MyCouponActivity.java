package com.twentyfourhours.tuchuang.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.CouponAdapter;
import com.twentyfourhours.tuchuang.adapter.CouponUseAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/16.
 */
public class MyCouponActivity extends BaseActivity {

    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.list_view)
    ListView mListView;

    private CouponAdapter mAdapter;
    private ArrayList<StoreBeen> mStoreBeens;
    private CouponUseAdapter mAdapterUse;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mAdapterUse = new CouponUseAdapter(this);
        mStoreBeens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StoreBeen been = new StoreBeen();
            been.setCheck(false);
            been.setMyCoupon(true);
//            been.setItemCount(1);
            mStoreBeens.add(been);
        }
        mAdapterUse.setData(mStoreBeens);
        mListView.setAdapter(mAdapterUse);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    @OnClick({R.id.gold_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gold_back:
                finish();
                break;
        }
    }

}
