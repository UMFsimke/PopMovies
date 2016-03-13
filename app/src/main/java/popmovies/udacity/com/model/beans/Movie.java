/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import popmovies.udacity.com.model.mappers.MovieMapper;

/**
 * Abstraction of a movie object.
 *
 * Contains details of single movie.
 */
public class Movie implements Parcelable {

    /**
     * Id
     */
    @SerializedName("id")
    protected int mId;

    /**
     * Title
     */
    @SerializedName("original_title")
    protected String mTitle;

    /**
     * Poster path
     */
    @SerializedName("poster_path")
    protected String mPosterPath;

    /**
     * Plot overview
     */
    @SerializedName("overview")
    protected String mPlotOverview;

    /**
     * User's rating
     */
    @SerializedName("vote_average")
    protected double mUserRating;

    /**
     * Relase date
     */
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
        return mPlotOverview;
    }

    public void setPlotOverview(String plotOverview) {
        mPlotOverview = plotOverview;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        MovieMapper.writeToParcel(dest, this);
    }

    /**
     * Creator that generates instancess of class from a Parcel
     */
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return MovieMapper.constructFromParcel(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
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
