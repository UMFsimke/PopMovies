/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import android.os.Bundle;

import java.io.IOException;
import java.net.UnknownHostException;

import popmovies.udacity.com.model.api.ApiComponent;
import popmovies.udacity.com.model.api.ApiModule;
import popmovies.udacity.com.model.api.DaggerApiComponent;
import popmovies.udacity.com.model.api.MoviesApi;
import popmovies.udacity.com.model.api.response.MoviesResponse;
import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.presenter.interfaces.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.IGalleryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter that controls flow of gallery screen
 */
public class GalleryPresenter implements IGalleryPresenter {

    /**
     * Key for gallery bundle values
     */
    private static final String EXTRA_GALLERY = "EXTRA_GALLERY";

    /**
     * View which will render list of movies
     */
    protected IGalleryView mView;

    /**
     * Defines if state has just been restored or not
     */
    protected boolean mRestoredState;

    /**
     * Retrofit API call that will obtain movies from API
     */
    protected Call<MoviesResponse> mApiCall;

    /**
     * Defines how to sort movies on screen
     */
    protected Gallery.GalleryType mSortBy;

    /**
     * API factory component
     */
    protected ApiComponent mApiComponent;

    /**
     * Gallery that is currently rendered
     */
    protected Gallery mGallery;

    /**
     * Constructs presenter instance for the given view
     * @param view View which will be rendering list of movies
     */
    public GalleryPresenter(IGalleryView view) {
        mView = view;
        mRestoredState = false;
    }

    /**
     * Loads movies if screen is created or renders current if
     * state is restored
     */
    @Override
    public void onScreenCreated() {
        if (null == mView) return;

        String settingsGalleryType = mView.getSettingsGalleryType();
        mSortBy = Gallery.GalleryType.fromString(settingsGalleryType);
        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();

        if (mRestoredState) {
            mRestoredState = false;
        } else {
            mGallery = new Gallery();
            loadMoreMovies();
        }

        mView.onGalleryUpdated(mGallery);
    }

    /**
     * Loads additional page of movies from API
     */
    @Override
    public void loadMoreMovies() {
        if (mView == null) return;

        MoviesApi api = mApiComponent.moviesApiClient();

        switch (mSortBy) {
            case POPULAR:
                mApiCall = api.getPopularMovies(Constants.API_KEY , mGallery.getNextPageToLoad());
                break;
            case TOP_RATED:
                mApiCall = api.getTopRatedMovies(Constants.API_KEY , mGallery.getNextPageToLoad());
                break;
            default:
                return;
        }

        mApiCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (null == mView) return;

                if (response.isSuccess()) {
                    mGallery.addMovies(response.body().getResults());
                    mGallery.setLastLoadedPage(response.body().getCurrentPage());
                    mGallery.setHasMore(response.body().getCurrentPage() ==
                            response.body().getLastPage());

                    mView.onGalleryUpdated(mGallery);
                    return;
                }

                mView.hideProgressBar();
                mView.showServerErrorMessage();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                if (mView == null) {
                    return;
                }

                mView.hideProgressBar();
                if (t instanceof UnknownHostException) {
                    mView.showNoInternetConnection();
                }
            }
        });
    }

    /**
     * Restores state of the gallery screen
     * @param savedInstanceState Bundle data used to restore instance state
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (null == savedInstanceState || null == mView) return;

        mRestoredState = true;
        mGallery = savedInstanceState.getParcelable(EXTRA_GALLERY);
    }

    /**
     * Stops API calls if any is ongoing
     */
    @Override
    public void onDestroy() {
        if (null != mApiCall) {
            mApiCall.cancel();
        }

        mView = null;
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
        if (null == mView) return;

        String settingsGalleryType = mView.getSettingsGalleryType();
        Gallery.GalleryType savedGalleryType = Gallery.GalleryType.fromString(settingsGalleryType);
        if (savedGalleryType.ordinal() != mSortBy.ordinal()) {
            mRestoredState = false;
            mView.onRefresh();
            onScreenCreated();
        }
    }
}
