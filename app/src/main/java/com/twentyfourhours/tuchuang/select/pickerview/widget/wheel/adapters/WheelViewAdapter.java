package com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/3/17.
 */

public interface WheelViewAdapter {
    int getItemsCount();

    View getItem(int var1, View var2, ViewGroup var3);

    View getEmptyItem(View var1, ViewGroup var2);

    void registerDataSetObserver(DataSetObserver var1);

    void unregisterDataSetObserver(DataSetObserver var1);
}
