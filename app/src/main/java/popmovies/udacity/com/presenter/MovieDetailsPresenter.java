/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import android.os.Bundle;

import java.net.UnknownHostException;

import popmovies.udacity.com.model.api.ApiComponent;
import popmovies.udacity.com.model.api.ApiModule;
import popmovies.udacity.com.model.api.DaggerApiComponent;
import popmovies.udacity.com.model.api.MoviesApi;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.presenter.interfaces.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.IMovieDetailsView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter that controls flow of movie details screen
 */
public class MovieDetailsPresenter implements IMovieDetailsPresenter {

    /**
     * Key for movie bundle values
     */
    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    /**
     * Movie whos details will be rendered
     */
    protected Movie mMovie;

    /**
     * View which will render movie details
     */
    protected IMovieDetailsView mView;

    /**
     * API factory component
     */
    protected ApiComponent mApiComponent;

    /**
     * Retrofit API call that will obtain movies from API
     */
    protected Call<Movie> mApiCall;

    /**
     * Defines if state has just been restored or not
     */
    protected boolean mRestoredState;

    public MovieDetailsPresenter(IMovieDetailsView view) {
        mView = view;
        mRestoredState = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMovieIdToLoad(int movieId) {
        if (mRestoredState) return;

        mMovie = new Movie();
        mMovie.setId(movieId);
    }

    /**
     * Loads a movie if screen is created, or renders the one
     * if save instance state is restored
     */
    @Override
    public void onScreenCreated() {
        if (null == mView) return;

        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();

        if (mRestoredState) {
            mRestoredState = false;
            renderMovie();
        } else {
            loadMovie();
        }
    }

    /**
     * Loads movie from API and renders it
     */
    public void loadMovie() {
        if (mView == null) return;

        if (mMovie == null || mMovie.getId() == -1) {
            renderMovie();
            return;
        }

        MoviesApi api = mApiComponent.moviesApiClient();
        mApiCall = api.getMovieDetails(mMovie.getId(), Constants.API_KEY);

        mApiCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (null == mView) return;

                if (response.isSuccess()) {
                    mMovie = response.body();
                    renderMovie();
                    return;
                }

                mView.hideProgressBar();
                mView.showServerErrorMessage();
                renderMovie();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                if (mView == null) {
                    return;
                }

                mView.hideProgressBar();
                if (t instanceof UnknownHostException) {
                    mView.showNoInternetConnection();
                }

                renderMovie();
            }
        });
    }

    /**
     * Renders a movie to the view
     */
    protected void renderMovie() {
        if (mView == null) return;

        mView.hideProgressBar();

        if (mMovie == null || mMovie.getId() == -1) {
            mView.onMovieInvalidId();
            return;
        }

        mView.onMovieValid();
        mView.setMovieTitle(mMovie.getTitle());
        mView.setMovieOverview(mMovie.getPlotOverview());
        mView.loadThumbnailUrl(mMovie.getMoviePosterFullUrl());

        mView.setMovieDetails(mMovie.getDuration(),
                mMovie.getGenresAsStringArray(),
                mMovie.getReleaseDate(),
                mMovie.getUserRating());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        if (null != mApiCall) {
            mApiCall.cancel();
        }

        mView = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (null == savedInstanceState || null == mView) return;

        mRestoredState = true;
        mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_MOVIE, mMovie);
    }
}
