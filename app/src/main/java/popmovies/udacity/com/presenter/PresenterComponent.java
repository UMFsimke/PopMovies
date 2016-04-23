/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import dagger.Component;
import popmovies.udacity.com.presenter.interfaces.presenter.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;

/**
 * Presenters dagger component
 */
@Component(modules = PresenterModule.class)
public interface PresenterComponent {
    IGalleryPresenter getGalleryPresenter();
    IMovieDetailsPresenter getMovieDetailsPresenter();
}
