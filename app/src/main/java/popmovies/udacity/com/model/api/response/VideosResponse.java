/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api.response;

import com.google.gson.annotations.SerializedName;

import popmovies.udacity.com.model.beans.Video;

/**
 * Abstraction of an TheMovieDb API response when querying for list of movie's videos.
 */
public class VideosResponse extends BaseResponse<Video> {

    /**
     * Movie ID
     */
    @SerializedName("id")
    String mMovieId;

    public VideosResponse() {
        super();
    }

    public String getMovieId() {
        return mMovieId;
    }
}
