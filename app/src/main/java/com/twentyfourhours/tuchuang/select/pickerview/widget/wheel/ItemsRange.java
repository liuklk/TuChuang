package com.twentyfourhours.tuchuang.select.pickerview.widget.wheel;

/**
 * Created by Administrator on 2018/3/17.
 */

public class ItemsRange {
    private int first;
    private int count;

    public ItemsRange() {
        this(0, 0);
    }

    public ItemsRange(int first, int count) {
        this.first = first;
        this.count = count;
    }

    public int getFirst() {
        return this.first;
    }

    public int getLast() {
        return this.getFirst() + this.getCount() - 1;
    }

    public int getCount() {
        return this.count;
    }

    public boolean contains(int index) {
        return index >= this.getFirst() && index <= this.getLast();
    }
}
