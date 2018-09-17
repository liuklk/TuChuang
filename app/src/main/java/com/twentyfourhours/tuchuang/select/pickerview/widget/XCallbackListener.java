package com.twentyfourhours.tuchuang.select.pickerview.widget;

/**
 * Created by Administrator on 2018/3/17.
 */

public abstract class XCallbackListener {
    public XCallbackListener() {
    }

    protected abstract void callback(Object... var1);

    public void call(Object... obj) {
        this.callback(obj);
    }
}
