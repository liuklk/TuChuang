package com.twentyfourhours.tuchuang.ui.activity;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.GoldStockFragmentPageAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.ui.fragment.AllMyOderFragment;
import com.twentyfourhours.tuchuang.ui.fragment.GoodsReceivedFragment;
import com.twentyfourhours.tuchuang.ui.fragment.PedingAuditFragment;
import com.twentyfourhours.tuchuang.ui.fragment.PendingEvaluationFragment;
import com.twentyfourhours.tuchuang.ui.fragment.WaitingDeliveryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class MyOderActivity extends BaseActivity {
    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp)
    ViewPager mVp;
    private List<Fragment> mFragmentList=new ArrayList<>();
    private GoldStockFragmentPageAdapter mFragmentPageAdapter;

    @Override
    protected void initData() {
        initFragment();
        mFragmentPageAdapter = new GoldStockFragmentPageAdapter(getFragmentManager(), mFragmentList);
        mFragmentPageAdapter.setTitles(new String[]{"全部","待付款","待发货","待收货","待评价"});
        int oderPosition = getIntent().getIntExtra("oderPosition", 0);
        mVp.setAdapter(mFragmentPageAdapter);
        mVp.setCurrentItem(oderPosition);
        mTabs.setupWithViewPager(mVp);
    }

    private void initFragment() {
        mFragmentList.add(new AllMyOderFragment());
        mFragmentList.add(new PedingAuditFragment());
        mFragmentList.add(new WaitingDeliveryFragment());
        mFragmentList.add(new GoodsReceivedFragment());
        mFragmentList.add(new PendingEvaluationFragment());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_oder;
    }


    @OnClick(R.id.gold_back)
    public void onViewClicked() {
        finish();
    }
}
