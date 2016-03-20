/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import popmovies.udacity.com.presenter.interfaces.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.IGalleryView;
import popmovies.udacity.com.presenter.interfaces.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.IMovieDetailsView;

/**
 * Handles the creation of presenter classes instances
 */
public class PresenterFactory {

    //TODO: Add Dagger2
    /**
     * Creates new instance of {@link IGalleryPresenter)
     * @param view View that requires a presenter
     * @return Presenter for the given gallery view
     */
    public static IGalleryPresenter getGalleryPresenter(IGalleryView view) {
        return new GalleryPresenter(view);
    }

    /**
     * Creates new instance of {@link IMovieDetailsPresenter}
     * @param view View that requires a presenter
     * @return Presenter for the given movie details view
     */
    public static IMovieDetailsPresenter getMovieDetailsPresenter(IMovieDetailsView view) {
        return new MovieDetailsPresenter(view);
    }
}
