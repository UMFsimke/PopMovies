/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import popmovies.udacity.com.model.beans.Review;

/**
 * Abstraction of an TheMovieDb API response when querying for list of reviews for a movie.
 */
public class ReviewsResponse {

    /**
     * Movie id
     */
    @SerializedName("id")
    String mMovieId;

    /**
     * Current page
     */
    @SerializedName("page")
    int mCurrentPage;

    /**
     * List of reviews
     */
    @SerializedName("results")
    List<Review> mResults;

    /**
     * Total number of pages
     */
    @SerializedName("total_pages")
    int mTotalPages;

    public ReviewsResponse() {
    }

    public String getMovieId() {
        return mMovieId;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public List<Review> getReviews() {
        return mResults;
    }

    public int getTotalPages() {
        return mTotalPages;
    }
}
