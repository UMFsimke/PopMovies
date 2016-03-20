/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces;

/**
 * Interface defining available actions on movies gallery presenter
 */
public interface IGalleryPresenter extends IPresenter {

    /**
     * Invoked when user is close to the end of the list and
     * additional movies should be loaded
     */
    void loadMoreMovies();

    /**
     * Invoked when user leaves the view and data should be cleared
     */
    void onDestroy();

    /**
     * Invoked when screen is resumed
     */
    void onScreenResumed();
}
