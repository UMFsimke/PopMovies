/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces;

import android.os.Bundle;

/**
 * Interface defining available actions on every presenter
 */
public interface IPresenter {

    /**
     * Method that notifies the presenter that view has been rendered on screen
     */
    void onScreenCreated();

    /**
     * Invoked when instance state of a view gets restored
     * @param savedInstanceState Bundle data used to restore instance state
     */
    void onRestoreInstanceState(Bundle savedInstanceState);

    /**
     * Invoked when instance state should be saved
     * @param outState Bundle in which to place data
     */
    void onSaveInstanceState(Bundle outState);
}
