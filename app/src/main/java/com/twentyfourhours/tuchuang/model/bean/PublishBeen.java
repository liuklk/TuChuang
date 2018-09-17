package com.twentyfourhours.tuchuang.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */

public class PublishBeen implements Parcelable{
    //歌曲名
    private String song;
    //主题
    private String title;
    //背景路径
    private String bgPath;

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PublishBeen{" +
                "song='" + song + '\'' +
                ", title='" + title + '\'' +
                ", bgPath='" + bgPath + '\'' +
                ", musicPath='" + musicPath + '\'' +
                ", data=" + data +
                '}';
    }

    public String getBgPath() {
        return bgPath;
    }

    public void setBgPath(String bgPath) {
        this.bgPath = bgPath;
    }

    //音乐播放路径
    private String musicPath;
    //内容集合
    private List<HeartBeen> data=new ArrayList<>();


    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public List<HeartBeen> getData() {
        return data;
    }

    public void setData(List<HeartBeen> data) {
        this.data = data;
    }

    public PublishBeen(){

    }

    //将对象属性反序列化然后读取出来，注意属性的顺序必须按照序列化属性的顺序
    protected PublishBeen(Parcel in) {
        song = in.readString();
        title = in.readString();
        bgPath = in.readString();
        musicPath = in.readString();
        in.readTypedList(data,CREATO);
    }

    private static final Creator<HeartBeen> CREATO = new Creator<HeartBeen>() {
        @Override
        public HeartBeen createFromParcel(Parcel in) {
            return new HeartBeen(in);
        }

        @Override
        public HeartBeen[] newArray(int size) {
            return new HeartBeen[size];
        }
    };

    public static final Creator<PublishBeen> CREATOR = new Creator<PublishBeen>() {
        @Override
        public PublishBeen createFromParcel(Parcel in) {
            return new PublishBeen(in);
        }

        @Override
        public PublishBeen[] newArray(int size) {
            return new PublishBeen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //将对象属性进行序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(song);
        dest.writeString(title);
        dest.writeString(bgPath);
        dest.writeString(musicPath);
        dest.writeTypedList(data);
    }

    public PublishBeen(String song, String title, String bgPath,String musicPath,List<HeartBeen> data) {
        this.song = song;
        this.title = title;
        this.bgPath = bgPath;
        this.musicPath = musicPath;
        this.data = data;
    }

}
