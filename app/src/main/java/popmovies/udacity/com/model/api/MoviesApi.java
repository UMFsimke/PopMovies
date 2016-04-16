/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api;

import popmovies.udacity.com.model.api.response.MoviesResponse;
import popmovies.udacity.com.model.api.response.ReviewsResponse;
import popmovies.udacity.com.model.api.response.VideosResponse;
import popmovies.udacity.com.model.beans.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
     * Endpoint for movie details
     */
    String MOVIE_DETAIL_API_ROUTE = "movie/{id}";

    /**
     * Endpoint for movie reviews
     */
    String MOVIE_REVIEWS_API_ROUTE = "movie/{id}/reviews";

    /**
     * Endpoint for movie videos
     */
    String MOVIE_VIDEOS_API_ROUTE = "movie/{id}/videos";

    /**
     * API key query parameter name in URL request
     */
    String API_KEY_QUERY_PARAMETER_NAME = "api_key";

    /**
     * Page query parameter name in URL request
     */
    String PAGE_QUERY_PARAMETER_NAME = "page";

    /**
     * Movie id parameter sub path in URL
     */
    String MOVIE_ID_PARAMETER_NAME = "id";

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

    /**
     * Definition of movie's details endpoint route which gives as result
     * {@link Call} that can be used for querying API for movie's details.
     * @param movieId Movie id to load movie's details
     * @param apiKey API key for TheMovieDb. API key is required parameter.
     * @return {@link Call<Movie>} that can be initiated for querying API
     */
    @GET(MOVIE_DETAIL_API_ROUTE)
    Call<Movie> getMovieDetails(
            @Path(MOVIE_ID_PARAMETER_NAME) Integer movieId,
            @Query(API_KEY_QUERY_PARAMETER_NAME) String apiKey
    );

    /**
     * Definition of movie's reviews endpoint route which gives as result
     * {@link Call} that can be used for querying API for movie's reviews.
     * @param movieId Movie id to load movie's reviews
     * @param apiKey API key for TheMovieDb. API key is required parameter.
     * @return {@link Call<ReviewsResponse>} that can be initiated for querying API
     */
    @GET(MOVIE_REVIEWS_API_ROUTE)
    Call<ReviewsResponse> getMovieReviews(
            @Path(MOVIE_ID_PARAMETER_NAME) Integer movieId,
            @Query(API_KEY_QUERY_PARAMETER_NAME) String apiKey
    );

    /**
     * Definition of movie's videos endpoint route which gives as result
     * {@link Call} that can be used for querying API for movie's videos.
     * @param movieId Movie id to load movie's videos
     * @param apiKey API key for TheMovieDb. API key is required parameter.
     * @return {@link Call<VideosResponse>} that can be initiated for querying API
     */
    @GET(MOVIE_VIDEOS_API_ROUTE)
    Call<VideosResponse> getMovieVideos(
            @Path(MOVIE_ID_PARAMETER_NAME) Integer movieId,
            @Query(API_KEY_QUERY_PARAMETER_NAME) String apiKey
    );
}
