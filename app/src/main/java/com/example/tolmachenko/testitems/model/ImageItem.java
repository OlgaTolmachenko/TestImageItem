package com.example.tolmachenko.testitems.model;

import android.net.Uri;

import java.text.SimpleDateFormat;

/**
 * Created by Olga Tolmachenko on 24.03.17.
 */

public class ImageItem {

    private Uri imageThumb;
    private String imageName;
    private long imageTime;

    public ImageItem(Uri imageThumb, String imageName, long imageTime) {
        this.imageThumb = imageThumb;
        this.imageName = imageName;
        this.imageTime = imageTime;
    }

    public Uri getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(Uri imageThumb) {
        this.imageThumb = imageThumb;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageTime() {
        final String DATE_TIME_FORMAT = "dd MMM yyyy HH:mm:ss";
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(imageTime);
    }

    public void setImageTime(long imageTime) {
        this.imageTime = imageTime;
    }
}
