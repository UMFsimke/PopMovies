/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import dagger.Module;
import dagger.Provides;
import popmovies.udacity.com.presenter.interfaces.presenter.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;

/**
 * Module that provides presenter objectto the app
 */
@Module
public class PresenterModule {

    /**
     * Creates new instance of {@link IGalleryPresenter)
     * @return Presenter for the given gallery view
     */
    @Provides
    public IGalleryPresenter getGalleryPresenter() {
        return new GalleryPresenter();
    }

    /**
     * Creates new instance of {@link IMovieDetailsPresenter}
     * @return Presenter for the given movie details view
     */
    @Provides
    public IMovieDetailsPresenter getMovieDetailsPresenter() {
        return new MovieDetailsPresenter();
    }
}
