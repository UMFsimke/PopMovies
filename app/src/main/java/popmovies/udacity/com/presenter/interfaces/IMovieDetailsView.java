/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces;

/**
 * Interface defining available actions on movies details view
 */
public interface IMovieDetailsView {

    /**
     * Renders movie title
     * @param movieTitle Movie title
     */
    void setMovieTitle(String movieTitle);

    /**
     * Renders movie details
     * @param duration Duration of movie
     * @param genres Movie's genres
     * @param releaseDate Release date
     * @param rating Rating
     */
    void setMovieDetails(int duration, String[] genres, String releaseDate, double rating);

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
     * Invoked when API didn't returned result for a movie since it
     * doesnt exists
     */
    void onMovieInvalidId();

    /**
     * Invoked when progress bar should be hidden
     */
    void hideProgressBar();

    /**
     * Invoked when API returned result for given movie
     */
    void onMovieValid();

    /**
     * Invoked when API is not responding and message
     * should be shown
     */
    void showServerErrorMessage();

    /**
     * Invoked when there is no internet connection
     * and user should be notified
     */
    void showNoInternetConnection();
}
