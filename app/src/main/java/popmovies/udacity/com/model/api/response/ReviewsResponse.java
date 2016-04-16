/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api.response;

import com.google.gson.annotations.SerializedName;

import popmovies.udacity.com.model.beans.Review;

/**
 * Abstraction of an TheMovieDb API response when querying for list of reviews for a movie.
 */
public class ReviewsResponse extends BasePaginatedResponse<Review> {

    /**
     * Movie id
     */
    @SerializedName("id")
    String mMovieId;

    public ReviewsResponse() {
        super();
    }

    public String getMovieId() {
        return mMovieId;
    }
}
