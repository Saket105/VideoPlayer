package com.example.videoplayer;

import android.graphics.Bitmap;

public class VideoRVModel {
    private String videoName;
    private String videoPath;
    private Bitmap thumbnail;

    public VideoRVModel(String videoName, String videoPath, Bitmap thumbnail) {
        this.videoName = videoName;
        this.videoPath = videoPath;
        this.thumbnail = thumbnail;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}