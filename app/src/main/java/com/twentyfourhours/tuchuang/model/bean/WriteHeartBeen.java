package com.twentyfourhours.tuchuang.model.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/3/15.
 */

public class WriteHeartBeen {
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
                ", bitmap=" + bitmap +
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
    private boolean isShowLast;

    public boolean isShowLast() {
        return isShowLast;
    }

    public void setShowLast(boolean showLast) {
        isShowLast = showLast;
    }

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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;

}
