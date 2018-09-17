package com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public abstract class AbstractWheelAdapter implements WheelViewAdapter {
    private List<DataSetObserver> datasetObservers;

    public AbstractWheelAdapter() {
    }

    public View getEmptyItem(View convertView, ViewGroup parent) {
        return null;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        if(this.datasetObservers == null) {
            this.datasetObservers = new LinkedList();
        }

        this.datasetObservers.add(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        if(this.datasetObservers != null) {
            this.datasetObservers.remove(observer);
        }

    }

    protected void notifyDataChangedEvent() {
        if(this.datasetObservers != null) {
            Iterator var1 = this.datasetObservers.iterator();

            while(var1.hasNext()) {
                DataSetObserver observer = (DataSetObserver)var1.next();
                observer.onChanged();
            }
        }

    }

    protected void notifyDataInvalidatedEvent() {
        if(this.datasetObservers != null) {
            Iterator var1 = this.datasetObservers.iterator();

            while(var1.hasNext()) {
                DataSetObserver observer = (DataSetObserver)var1.next();
                observer.onInvalidated();
            }
        }

    }
}
