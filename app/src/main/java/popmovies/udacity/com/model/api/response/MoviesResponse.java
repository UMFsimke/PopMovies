/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import popmovies.udacity.com.model.beans.Movie;

/**
 * Abstraction of an TheMovieDb API response when querying for list of movies.
 */
public class MoviesResponse {

    @SerializedName("total_pages")
    int mLastPage;

    @SerializedName("page")
    int mCurrentPage;

    @SerializedName("results")
    List<Movie> mMoviesList;

    public MoviesResponse() {
    }

    public int getLastPage() {
        return mLastPage;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public List<Movie> getMoviesList() {
        return mMoviesList;
    }
}
