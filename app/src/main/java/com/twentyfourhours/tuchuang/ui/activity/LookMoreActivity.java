package com.twentyfourhours.tuchuang.ui.activity;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.GoldStockFragmentPageAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.ui.fragment.LookMoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17.
 */
public class LookMoreActivity extends BaseActivity{
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
        mFragmentPageAdapter.setTitles(new String[]{"T恤","卫衣","裤子","帆布袋","手机","家具","餐具"});
//        int oderPosition = getIntent().getIntExtra("oderPosition", 0);
        mVp.setAdapter(mFragmentPageAdapter);
//        mVp.setCurrentItem(oderPosition);
        mTabs.setupWithViewPager(mVp);
    }

    private void initFragment() {
        mFragmentList.add(new LookMoreFragment());
        mFragmentList.add(new  LookMoreFragment());
        mFragmentList.add(new  LookMoreFragment());
        mFragmentList.add(new  LookMoreFragment());
        mFragmentList.add(new  LookMoreFragment());
        mFragmentList.add(new  LookMoreFragment());
        mFragmentList.add(new  LookMoreFragment());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_look_more;
    }


    @OnClick(R.id.gold_back)
    public void onViewClicked() {
        finish();
    }
}

