package com.twentyfourhours.tuchuang.model.bean;

/**
 * Created by Administrator on 2018/1/12.
 */

public class XyBeen {
    private int left;
    private int top;
    private int right;
    private int bottom;
    private int dvLeft;
    private int dvTop;
    private int dvRight;
    private int dvBottom;

    @Override
    public String toString() {
        return "XyBeen{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                ", dvLeft=" + dvLeft +
                ", dvTop=" + dvTop +
                ", dvRight=" + dvRight +
                ", dvBottom=" + dvBottom +
                '}';
    }

    public int getDvLeft() {
        return dvLeft;
    }

    public void setDvLeft(int dvLeft) {
        this.dvLeft = dvLeft;
    }

    public int getDvTop() {
        return dvTop;
    }

    public void setDvTop(int dvTop) {
        this.dvTop = dvTop;
    }

    public int getDvRight() {
        return dvRight;
    }

    public void setDvRight(int dvRight) {
        this.dvRight = dvRight;
    }

    public int getDvBottom() {
        return dvBottom;
    }

    public void setDvBottom(int dvBottom) {
        this.dvBottom = dvBottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
