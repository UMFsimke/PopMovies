/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api;

import dagger.Component;
import retrofit2.Call;

/**
 * API dagger component
 */
@Component(modules = ApiModule.class)
public interface ApiComponent {
    MoviesApi moviesApiClient();
}
