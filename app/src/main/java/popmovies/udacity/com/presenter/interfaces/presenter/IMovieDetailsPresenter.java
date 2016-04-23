/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter.interfaces.presenter;

import android.os.Bundle;

/**
 * Interface defining available actions on movies detail presenter
 */
public interface IMovieDetailsPresenter extends IPresenter {

    /**
     * Loads data from bundle which was sent
     * @param bundle Bundled data
     */
    void loadData(Bundle bundle);
}