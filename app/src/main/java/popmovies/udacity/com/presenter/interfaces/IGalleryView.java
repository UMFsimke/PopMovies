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
}
