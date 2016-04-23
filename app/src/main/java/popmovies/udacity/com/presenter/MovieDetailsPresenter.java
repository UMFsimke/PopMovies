/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import android.os.Bundle;

import popmovies.udacity.com.model.api.response.BaseResponse;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IMovieDetailsView;

/**
 * Presenter that controls flow of movie details screen
 */
public class MovieDetailsPresenter extends BasePresenter<IMovieDetailsView>
        implements IMovieDetailsPresenter {

    /**
     * Key for movie bundle values
     */
    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    /**
     * Movie whos details will be rendered
     */
    protected Movie mMovie;

    public MovieDetailsPresenter() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData(Bundle bundle) {
        if (bundle == null) return;

        mMovie = bundle.getParcelable(EXTRA_MOVIE);
    }

    /**
     * Renders movie if screen is restored
     */
    @Override
    protected void onViewRestored() {
        renderMovie();
    }

    /**
     * Renders a movie to the view
     */
    protected void renderMovie() {
        if (!isViewAttached()) return;

        getView().hideProgressBar();
        if (mMovie == null || mMovie.getId() == -1) {
            getView().showPlaceholder();
            return;
        }

        getView().hidePlaceholder();
        getView().setMovieTitle(mMovie.getTitle());
        getView().setMovieOverview(mMovie.getPlotOverview());
        getView().loadThumbnailUrl(mMovie.getMoviePosterFullUrl());
        getView().setMovieDetails(mMovie.getReleaseDate(),
                mMovie.getUserRating());
    }

    /**
     * Loads movie if screen is created
     */
    @Override
    protected void onViewCreated() {
        loadMovie();
    }

    /**
     * Loads movie from API and renders it
     */
    public void loadMovie() {
    }

    @Override
    protected <T extends BaseResponse> void onApiResponse(T apiResponse) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void restoreData(Bundle savedInstanceState) {
        mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_MOVIE, mMovie);
    }

    @Override
    public void onScreenResumed() {
    }
}
