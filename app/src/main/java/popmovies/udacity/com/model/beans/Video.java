/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import popmovies.udacity.com.model.mappers.VideoMapper;

/**
 * Abstraction of a single video object for a movie.
 */
public class Video implements Parcelable {

    @SerializedName("name")
    protected String mName;

    @SerializedName("key")
    protected String mYoutubeKey;

    public Video() {
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setYoutubeKey(String youtubeKey) {
        mYoutubeKey = youtubeKey;
    }

    public String getYoutubeKey() {
        return mYoutubeKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        VideoMapper.writeToParcel(dest, this);
    }

    /**
     * Creator that generates instances of class from a Parcel
     */
    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return VideoMapper.constructFromParcel(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }
}
