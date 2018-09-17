package com.twentyfourhours.tuchuang.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 金料统计adapter
 * Created by Administrator on 2017/10/16 0016.
 */

public class GoldStockFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments=new ArrayList<>();

    public void setTitles(String[] titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }

    private String[] titles={"入库","出货"};

    public GoldStockFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragments=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        if (mFragments!=null){
            return mFragments.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
