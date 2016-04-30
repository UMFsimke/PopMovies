/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces.view;

import android.content.Context;

import popmovies.udacity.com.model.beans.Movie;

/**
 * Interface defining available actions on movies details view
 */
public interface IMovieDetailsView extends IView {

    /**
     * Renders a movie on the view
     * @param movie Movie to render
     */
    void renderMovie(Movie movie);
}
