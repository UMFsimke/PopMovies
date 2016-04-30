/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import popmovies.udacity.com.PopMovies;
import popmovies.udacity.com.model.api.response.BaseResponse;
import popmovies.udacity.com.model.api.response.MoviesResponse;
import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.model.beans.mappers.MovieMapper;
import popmovies.udacity.com.model.database.MovieContract;
import popmovies.udacity.com.presenter.interfaces.presenter.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IGalleryView;
import retrofit2.Response;
import rx.Observable;

/**
 * Presenter that controls flow of gallery screen
 */
public class GalleryPresenter extends BasePresenter<IGalleryView> implements IGalleryPresenter,
        LoaderManager.LoaderCallbacks<Cursor>{

    /**
     * Key for gallery bundle values
     */
    private static final String EXTRA_GALLERY = "EXTRA_GALLERY";


    /**
     * Defines how to sort movies on screen
     */
    @Inject protected Gallery.GalleryType mSortBy;

    /**
     * Gallery that is currently rendered
     */
    @Inject protected Gallery mGallery;

    /**
     * Movie's loader ID
     */
    private static final int MOVIES_LOADER = 0;

    /**
     * Constructs presenter instance
     */
    public GalleryPresenter() {
        super();
        PopMovies.getInstance().graph().inject(this);
    }

    /**
     * {@inheritDoc}
     *
     * Loads movies if screen is created
     */
    @Override
    protected void onViewCreated() {
        loadMovies();
        getView().showProgressBar();
    }

    /**
     * Loads additional page of movies from API
     */
    @Override
    public void loadMovies() {
        switch (mSortBy) {
            case POPULAR:
                loadPopularMovies();
                break;
            case TOP_RATED:
                loadTopRatedMovies();
                break;
            case FAVORITES:
                loadFavoriteMovies();
                break;
        }
    }

    protected void loadPopularMovies() {
        Observable<Response<MoviesResponse>> retrofitObservable
                = mApi.getPopularMovies(Constants.API_KEY, mGallery.getNextPageToLoad());
        makeApiCall(retrofitObservable);
    }

    protected void loadTopRatedMovies() {
        Observable<Response<MoviesResponse>> retrofitObservable
        =mApi.getTopRatedMovies(Constants.API_KEY, mGallery.getNextPageToLoad());
        makeApiCall(retrofitObservable);
    }

    protected void loadFavoriteMovies() {
        LoaderManager manager = ((Activity) getView().getContext()).getLoaderManager();
        manager.restartLoader(MOVIES_LOADER, null, this);
    }

    @Override
    protected <T extends BaseResponse> void onApiResponse(T apiResponse) {
        MoviesResponse moviesResponse = (MoviesResponse) apiResponse;
        updateGalleryWithResponse(moviesResponse);
        renderGallery();
        getView().hideProgressBar();
    }

    protected void updateGalleryWithResponse(MoviesResponse response) {
        mGallery.addMovies(response.getResults());
        mGallery.setLastLoadedPage(response.getCurrentPage());
        mGallery.setHasMore(response.getCurrentPage() ==
                response.getLastPage());
    }

    protected void renderGallery() {
        getView().renderGallery(mGallery);
    }

    /**
     * {@inheritDoc}
     *
     * Renders movies if state is restored
     */
    @Override
    protected void onViewRestored() {
        renderGallery();
        getView().hideProgressBar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void restoreData(Bundle savedInstanceState) {
        mGallery = savedInstanceState.getParcelable(EXTRA_GALLERY);
    }

    /**
     * Saves state of the gallery screen
     * @param outState Bundle in which to place data
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_GALLERY, mGallery);
    }

    /**
     * When screen is resumed if gallery type is changed data should refresh
     */
    @Override
    public void onScreenResumed() {
        if (!isViewAttached()) return;

        if (isSortByChanged()) {
            clearGallery();
            changeSortBy();
            reloadData();
        }
    }

    protected boolean isSortByChanged() {
        Gallery.GalleryType savedGalleryType = PopMovies.getInstance()
                .graph()
                .getSavedGalleryType();
        return mSortBy.ordinal() != savedGalleryType.ordinal();
    }

    protected void clearGallery() {
        mGallery = new Gallery();
    }

    protected void changeSortBy() {
        mSortBy = PopMovies.getInstance()
                .graph()
                .getSavedGalleryType();
    }

    protected void reloadData() {
        getView().resetScroll();
        onScreenCreated();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = MovieContract.MovieEntry._ID + " ASC";
        Uri uri = MovieContract.MovieEntry.CONTENT_URI;

        return new CursorLoader(getView().getContext(),
                uri,
                MovieMapper.MOVIE_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (getView() == null) return;

        if (cursor == null) {
            renderGallery();
            getView().hideProgressBar();
            return;
        }

        List<Movie> movies = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Movie movie = new Movie(cursor);
                movies.add(movie);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        mGallery.addMovies(movies);
        mGallery.setLastLoadedPage(1);
        mGallery.setHasMore(false);
        renderGallery();
        getView().hideProgressBar();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}