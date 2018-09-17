package com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.adapters;

import android.content.Context;

/**
 * Created by Administrator on 2018/3/17.
 */

public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {
    private T[] items;

    public ArrayWheelAdapter(Context context, T[] items) {
        super(context);
        this.items = items;
    }

    public CharSequence getItemText(int index) {
        if(index >= 0 && index < this.items.length) {
            Object item = this.items[index];
            return (CharSequence)(item instanceof CharSequence?(CharSequence)item:item.toString());
        } else {
            return null;
        }
    }

    public int getItemsCount() {
        return this.items.length;
    }
}
