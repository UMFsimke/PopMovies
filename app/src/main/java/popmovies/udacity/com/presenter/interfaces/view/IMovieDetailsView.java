/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces.view;

import popmovies.udacity.com.presenter.interfaces.view.IView;

/**
 * Interface defining available actions on movies details view
 */
public interface IMovieDetailsView extends IView {

    /**
     * Renders movie title
     * @param movieTitle Movie title
     */
    void setMovieTitle(String movieTitle);

    /**
     * Renders movie details
     * @param releaseDate Release date
     * @param rating Rating
     */
    void setMovieDetails(String releaseDate, double rating);

    /**
     * Renders movie overview
     * @param movieOverview Movie overview
     */
    void setMovieOverview(String movieOverview);

    /**
     * Renders thumbnail
     * @param thumbnailUrl Thumbnail URL to load image from
     */
    void loadThumbnailUrl(String thumbnailUrl);

    /**
     * Invoked when movie is not valid. Hides all views and shows placeholders
     */
    void showPlaceholder();

    /**
     * Hides placeholder and shows movie details
     */
    void hidePlaceholder();
}
