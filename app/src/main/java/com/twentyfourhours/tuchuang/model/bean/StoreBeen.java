package com.twentyfourhours.tuchuang.model.bean;

import android.graphics.Bitmap;
import android.graphics.Typeface;

/**
 * Created by Administrator on 2018/1/8.
 */

public class StoreBeen {
    private String area;
    private String singer;
    private String song;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    private String store;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String detailArea;
    private String distance;
    private boolean isCheck;
    private int itemCount;
    private boolean isAnim;
    private boolean isMyCoupon;
    private boolean isNotify;

    private boolean isShow;
    private boolean isShowVideo;
    private Bitmap bitmap;

    public boolean isShowVideo() {
        return isShowVideo;
    }

    public void setShowVideo(boolean showVideo) {
        isShowVideo = showVideo;
    }

    private String text;
    private Typeface typeface;
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public boolean isMyCoupon() {
        return isMyCoupon;
    }

    public void setMyCoupon(boolean myCoupon) {
        isMyCoupon = myCoupon;
    }

    public boolean isAnim() {
        return isAnim;
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "StoreBeen{" +
                "area='" + area + '\'' +
                ", singer='" + singer + '\'' +
                ", song='" + song + '\'' +
                ", isShow=" + isShow +
                ", store='" + store + '\'' +
                ", detailArea='" + detailArea + '\'' +
                ", distance='" + distance + '\'' +
                ", isCheck=" + isCheck +
                ", itemCount=" + itemCount +
                ", isAnim=" + isAnim +
                ", isMyCoupon=" + isMyCoupon +
                ", isNotify=" + isNotify +
                ", isShowVideo=" + isShowVideo +
                ", text='" + text + '\'' +
                ", typeface=" + typeface +
                ", bitmap=" + bitmap +
                ", duration='" + duration + '\'' +
                '}';
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDetailArea() {
        return detailArea;
    }

    public void setDetailArea(String detailArea) {
        this.detailArea = detailArea;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
