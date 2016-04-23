/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces.view;

import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.presenter.interfaces.view.IView;

/**
 * Interface defining available actions on movies gallery view
 */
public interface IGalleryView extends IView {

    /**
     * Invoked when gallery should be rendered
     * @param gallery Gallery to render
     */
    void renderGallery(Gallery gallery);

    /**
     * Returns gallery type saved in settings shared preferences
     * @return Chosen gallery type to show
     */
    String getSettingsGalleryType();

    /**
     * Notifies screen that data will refresh and scroll has to be reset
     */
    void resetScroll();
}
