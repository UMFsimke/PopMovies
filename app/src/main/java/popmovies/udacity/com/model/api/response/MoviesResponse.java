/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api.response;

import popmovies.udacity.com.model.beans.Movie;

/**
 * Abstraction of an TheMovieDb API response when querying for list of movies.
 */
public class MoviesResponse extends BasePaginatedResponse<Movie> {

    public MoviesResponse() {
        super();
    }
}
