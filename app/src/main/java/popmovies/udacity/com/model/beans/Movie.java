/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Abstraction of a movie object.
 *
 * Contains details of single movie.
 */
public class Movie {

    @SerializedName("id")
    protected int mId;

    @SerializedName("original_title")
    protected String mTitle;

    @SerializedName("poster_path")
    protected String mPosterPath;

    @SerializedName("overview")
    protected String mPlotOverivew;

    @SerializedName("vote_average")
    protected double mUserRating;

    @SerializedName("release_date")
    protected String mReleaseDate;

    public Movie() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getPlotOverview() {
        return mPlotOverivew;
    }

    public void setPlotOverview(String plotOverview) {
        mPlotOverivew = plotOverview;
    }

    public double getUserRating() {
        return mUserRating;
    }

    public void setUserRating(double userRating) {
        mUserRating = userRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }
}
