/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import android.os.Bundle;

import javax.inject.Inject;

import popmovies.udacity.com.model.api.response.BaseResponse;
import popmovies.udacity.com.model.api.response.MoviesResponse;
import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.presenter.interfaces.presenter.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IGalleryView;
import retrofit2.Response;
import rx.Observable;

/**
 * Presenter that controls flow of gallery screen
 */
public class GalleryPresenter extends BasePresenter<IGalleryView> implements IGalleryPresenter {

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
     * Constructs presenter instance
     */
    public GalleryPresenter() {
        super();
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
            /*case FAVORITES:
                loadFavoriteMovies();
                break;*/
        }
    }

    protected void loadPopularMovies() {
        Observable<Response<MoviesResponse>> retrofitObservable
                = mApi.getPopularMovies(Constants.API_KEY, mGallery.getNextPageToLoad());
        makeApiCall(retrofitObservable);
    }

    protected void loadTopRatedMovies() {
        Observable<Response<MoviesResponse>> retrofitObservable
                = mApi.getTopRatedMovies(Constants.API_KEY, mGallery.getNextPageToLoad());
        makeApiCall(retrofitObservable);
    }

    @Override
    protected <T extends BaseResponse> void onApiResponse(T apiResponse) {
        MoviesResponse moviesResponse = (MoviesResponse) apiResponse;
        updateGalleryWithResponse(moviesResponse);
        renderGallery();
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
            reloadData();
        }
    }

    protected boolean isSortByChanged() {
        String settingsGalleryType = getView().getSettingsGalleryType();
        Gallery.GalleryType savedGalleryType = Gallery.GalleryType.fromString(settingsGalleryType);
        return savedGalleryType.ordinal() != mSortBy.ordinal();
    }

    protected void reloadData() {
        getView().resetScroll();
        onScreenCreated();
    }
}