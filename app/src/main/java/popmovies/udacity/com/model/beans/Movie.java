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
    protected long mId;

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
     * Reviews of a movie
     */
    protected List<Review> mReviews;

    /**
     * Videos of a movie
     */
    protected List<Video> mVideos;

    /**
     * Defines order index in popular list for a movie
     */
    protected Integer mPopularIndex;

    /**
     * Defines order index in top rated list for a movie
     */
    protected Integer mTopRatedIndex;

    /**
     * Defines order index in favorite list for a movie
     */
    protected Integer mFavoriteIndex;

    public Movie() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
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
     * Returns full formatted URL string for movie poster
     * @return Full URL for a movie poster
     */
    public String getMoviePosterFullUrl() {
        return String.format("%s%s", MOVIE_POSTER_ENDPOINT, mPosterPath);
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }

    public List<Video> getVideos() {
        return mVideos;
    }

    public void setPopularIndex(Integer popularIndex) {
        mPopularIndex = popularIndex;
    }

    /**
     * Returns order index in popular list for a movie.
     * @return Order index for a movie in popular list. If movie is not
     * in popular list -1 will be returned
     */
    public int getPopularIndex() {
        return mPopularIndex == null ? -1 : mPopularIndex;
    }

    public void setTopRatedIndex(Integer topRatedIndex) {
        mTopRatedIndex = topRatedIndex;
    }

    /**
     * Returns order index in top rated list for a movie.
     * @return Order index for a movie in top rated list. If movie is not
     * in top rated list -1 will be returned
     */
    public int getTopRatedIndex() {
        return mTopRatedIndex == null ? -1 : mTopRatedIndex;
    }

    public void setFavoriteIndex(Integer favoriteIndex) {
        mFavoriteIndex = favoriteIndex;
    }

    /**
     * Returns order index in favorite list for a movie.
     * @return Order index for a movie in favorite list. If movie is not
     * in favorite list -1 will be returned
     */
    public int getFavoriteIndex() {
        return mFavoriteIndex == null ? -1 : mFavoriteIndex;
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
