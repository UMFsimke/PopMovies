/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import popmovies.udacity.com.model.mappers.MovieMapper;

/**
 * Abstraction of a movie object.
 *
 * Contains details of single movie.
 */
public class Movie implements Parcelable {

    private static final String MOVIE_POSTER_ENDPOINT = "http://image.tmdb.org/t/p/w185/";

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
     * Release date
     */
    @SerializedName("release_date")
    protected String mReleaseDate;

    /**
     * Duration of a movie
     */
    @SerializedName("runtime")
    protected int mDuration;

    /**
     * Genres of a movie
     */
    @SerializedName("genres")
    protected List<Genre> mGenres;

    /**
     * Reviews of a movie
     */
    protected List<Review> mReviews;

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

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        this.mGenres = genres;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    /**
     * Returns full formatted URL string for movie poster
     * @return Full URL for a movie poster
     */
    public String getMoviePosterFullUrl() {
        return String.format("%s%s", MOVIE_POSTER_ENDPOINT, mPosterPath);
    }

    /**
     * Returns names of genres as array of strings
     * @return Array of genres names
     */
    public String[] getGenresAsStringArray() {
        if (mGenres == null || mGenres.size() == 0) return null;

        String[] genres = new String[mGenres.size()];
        for (int i = 0; i < mGenres.size(); i++) {
            genres[i] = mGenres.get(i).getName();
        }

        return genres;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public List<Review> getReviews() {
        return mReviews;
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
