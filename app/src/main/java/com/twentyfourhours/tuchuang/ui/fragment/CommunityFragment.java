package com.twentyfourhours.tuchuang.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.StoreFragmentPageAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.ui.activity.SearchActivity;
import com.twentyfourhours.tuchuang.ui.activity.WriteHeartActivity;
import com.twentyfourhours.tuchuang.ui.activity.WriteHeartTextActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/7.
 */
public class CommunityFragment extends BaseFragment {
    @BindView(R.id.search)
    RelativeLayout mSearch;
    @BindView(R.id.write_heart)
    TextView mWriteHeart;
    @BindView(R.id.qa)
    TextView mQa;
    @BindView(R.id.find_friend)
    TextView mFindFriend;
    @BindView(R.id.my_home)
    TextView mMyHome;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp)
    ViewPager mVp;
    private StoreFragmentPageAdapter mFragmentPageAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] types = {"推荐", "关注", "猜你喜欢", "手机壳", "T恤", "水杯","玩具","配件"};
    private FragmentManager mChildFragmentManager;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_community, null);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initFragment();
        mChildFragmentManager = getChildFragmentManager();
        if (mFragmentPageAdapter == null) {
            mFragmentPageAdapter = new StoreFragmentPageAdapter(mChildFragmentManager, mFragmentList);
        }
        mFragmentPageAdapter.setTitles(types);
        mVp.setAdapter(mFragmentPageAdapter);
        mTabs.setupWithViewPager(mVp);
    }

    @OnClick({R.id.search, R.id.write_heart, R.id.qa, R.id.find_friend, R.id.my_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                goTo(SearchActivity.class, false);
                break;
            case R.id.write_heart:
                editHeart();
                goTo(WriteHeartActivity.class,false);
                break;
            case R.id.qa:
                break;
            case R.id.find_friend:
                break;
            case R.id.my_home:
                break;
        }
    }

    private void editHeart() {
        goTo(WriteHeartTextActivity.class,false);
    }

    private void initFragment() {
        mFragmentList.clear();
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
    }
}
