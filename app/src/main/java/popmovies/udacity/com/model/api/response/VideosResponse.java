/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import popmovies.udacity.com.model.beans.Video;

/**
 * Abstraction of an TheMovieDb API response when querying for list of movie's videos.
 */
public class VideosResponse {

    /**
     * Movie ID
     */
    @SerializedName("id")
    String mMovieId;

    /**
     * List of vides
     */
    @SerializedName("results")
    List<Video> mResults;

    public VideosResponse() {
    }

    public String getMovieId() {
        return mMovieId;
    }

    public List<Video> getVideos() {
        return mResults;
    }
}
