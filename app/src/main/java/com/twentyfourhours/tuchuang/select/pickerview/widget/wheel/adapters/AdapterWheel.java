package com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.adapters;

import android.content.Context;

import com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.WheelAdapter;


/**
 * Created by Administrator on 2018/3/17.
 */

public class AdapterWheel extends AbstractWheelTextAdapter {
    private WheelAdapter adapter;

    public AdapterWheel(Context context, WheelAdapter adapter) {
        super(context);
        this.adapter = adapter;
    }

    public WheelAdapter getAdapter() {
        return this.adapter;
    }

    public int getItemsCount() {
        return this.adapter.getItemsCount();
    }

    protected CharSequence getItemText(int index) {
        return this.adapter.getItem(index);
    }
}
