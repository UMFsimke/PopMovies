/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces;

import popmovies.udacity.com.model.beans.Movie;

/**
 * Interface defining available actions on movies detail presenter
 */
public interface IMovieDetailsPresenter extends IPresenter {

    /**
     * Invoked when movie should be loaded from API
     */
    void setMovieIdToLoad(int movieId);

    /**
     * Invoked when user leaves the view and data should be cleared
     */
    void onDestroy();
}