package com.example.tolmachenko.testitems.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;

/**
 * Created by Olga Tolmachenko on 24.03.17.
 */

public class ImageItem implements Parcelable {

    private Uri imageThumb;
    private String imageName;
    private long imageTime;

    public ImageItem(Uri imageThumb, String imageName, long imageTime) {
        this.imageThumb = imageThumb;
        this.imageName = imageName;
        this.imageTime = imageTime;
    }

    protected ImageItem(Parcel in) {
        imageThumb = in.readParcelable(Uri.class.getClassLoader());
        imageName = in.readString();
        imageTime = in.readLong();
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(imageThumb, i);
        parcel.writeString(imageName);
        parcel.writeLong(imageTime);
    }
}
