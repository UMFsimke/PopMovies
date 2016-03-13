/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api;

import popmovies.udacity.com.model.api.response.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Contains list of definitions of API methods.
 */
public interface MoviesApi {

    /**
     * Endpoint for top rated movies
     */
    String TOP_RATED_API_ROUTE = "movie/top_rated";

    /**
     * Endpoint for popular movies
     */
    String POPULAR_API_ROUTE = "movie/popular";

    /**
     * API key query parameter name in URL request
     */
    String API_KEY_QUERY_PARAMETER_NAME = "api_key";

    /**
     * Page query parameter name in URL request
     */
    String PAGE_QUERY_PARAMETER_NAME = "page";

    /**
     * Definition of top rated movies endpoint route which gives as result
     * {@link Call} that can be used for querying API for top rated movies.
     * @param apiKey API key for TheMovieDb. API key is required parameter.
     * @param page Optional parameter, defining page that should be returned in query.
     *             Default fallback value is 1 if not sent
     * @return {@link Call<MoviesResponse>} that can be initiated for querying API
     */
    @GET(TOP_RATED_API_ROUTE)
    Call<MoviesResponse> getTopRatedMovies(
            @Query(API_KEY_QUERY_PARAMETER_NAME) String apiKey,
            @Query(PAGE_QUERY_PARAMETER_NAME) Integer page
    );

    /**
     * Definition of popular movies endpoint route which gives as result
     * {@link Call} that can be used for querying API for popular movies.
     * @param apiKey API key for TheMovieDb. API key is required parameter.
     * @param page Optional parameter, defining page that should be returned in query.
     *             Default fallback value is 1 if not sent
     * @return {@link Call<MoviesResponse>} that can be initiated for querying API
     */
    @GET(POPULAR_API_ROUTE)
    Call<MoviesResponse> getPopularMovies(
            @Query(API_KEY_QUERY_PARAMETER_NAME) String apiKey,
            @Query(PAGE_QUERY_PARAMETER_NAME) Integer page
    );
}
