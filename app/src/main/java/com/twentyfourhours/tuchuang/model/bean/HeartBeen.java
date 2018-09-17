package com.twentyfourhours.tuchuang.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/3/15.
 */

public class HeartBeen implements Parcelable {
    //是否是视频
    private boolean isAudio;

    public boolean isAudio() {
        return isAudio;
    }

    @Override
    public String toString() {
        return "WriteHeartBeen{" +
                "isAudio=" + isAudio +
                ", path='" + path + '\'' +
                ", audioPathPic='" + audioPathPic + '\'' +
                ", content='" + content + '\'' +
                ", isShow=" + isShow +
                ", isShowVideo=" + isShowVideo +
                '}';
    }

    public void setAudio(boolean audio) {
        isAudio = audio;
    }

    //图片路径或者视频路径
    private String path;
    //视频预览路径
    private String audioPathPic;
    //文字内容路径
    private String content;

    public String getAudioPathPic() {
        return audioPathPic;
    }

    public void setAudioPathPic(String audioPathPic) {
        this.audioPathPic = audioPathPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private boolean isShow;
    private boolean isShowVideo;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isShowVideo() {
        return isShowVideo;
    }

    public void setShowVideo(boolean showVideo) {
        isShowVideo = showVideo;
    }

    //将对象属性反序列化然后读取出来，注意属性的顺序必须按照序列化属性的顺序
    protected HeartBeen(Parcel in) {
        path = in.readString();
        audioPathPic = in.readString();
        content = in.readString();
        isAudio =in.readByte()!=0;
        isShowVideo =in.readByte()!=0;
        isShow =in.readByte()!=0;
    }

    public static final Creator<HeartBeen> CREATOR = new Creator<HeartBeen>() {
        @Override
        public HeartBeen createFromParcel(Parcel in) {
            return new HeartBeen(in);
        }

        @Override
        public HeartBeen[] newArray(int size) {
            return new HeartBeen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //将对象属性进行序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(audioPathPic);
        dest.writeString(content);
        dest.writeByte((byte)(isAudio ?1:0));
        dest.writeByte((byte)(isShowVideo ?1:0));
        dest.writeByte((byte)(isShow ?1:0));
    }

    public HeartBeen(String path, String audioPathPic, String content, boolean isAudio, boolean isShowVideo, boolean isShow) {
        this.path = path;
        this.audioPathPic = audioPathPic;
        this.content = content;
        this.isAudio=isAudio;
        this.isShowVideo=isShowVideo;
        this.isShow=isShow;
    }

}
