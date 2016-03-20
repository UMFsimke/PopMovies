/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces;

import popmovies.udacity.com.model.beans.Gallery;

/**
 * Interface defining available actions on movies gallery view
 */
public interface IGalleryView {

    /**
     * Invoked when existing gallery gets updated
     * @param gallery New gallery
     */
    void onGalleryUpdated(Gallery gallery);

    /**
     * Returns gallery type saved in settings shared preferences
     * @return Chosen gallery type to show
     */
    String getSettingsGalleryType();

    /**
     * Notifies screen that data will refresh
     */
    void onRefresh();

    /**
     * Invoked when API is not responding and message
     * should be shown
     */
    void showServerErrorMessage();

    /**
     * Invoked when progress bar should be hidden
     */
    void hideProgressBar();

    /**
     * Invoked when there is no internet connection
     * and user should be notified
     */
    void showNoInternetConnection();
}
