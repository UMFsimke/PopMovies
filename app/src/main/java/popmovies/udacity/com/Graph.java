/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import popmovies.udacity.com.model.DataModule;
import popmovies.udacity.com.model.api.ApiModule;
import popmovies.udacity.com.model.api.MoviesApi;
import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.presenter.GalleryPresenter;
import popmovies.udacity.com.presenter.MovieDetailsPresenter;
import popmovies.udacity.com.presenter.PresenterModule;
import popmovies.udacity.com.presenter.interfaces.presenter.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;
import popmovies.udacity.com.view.fragments.GalleryFragment;
import popmovies.udacity.com.view.fragments.MovieDetailFragment;

/**
 * Graph component that is used to provide injectable members
 */
@Singleton
@Component(
        modules = {AppModule.class, ApiModule.class,
                DataModule.class, PresenterModule.class }
)
public interface Graph {
    void inject(GalleryPresenter presenter);
    void inject(MovieDetailsPresenter presenter);
    void inject(GalleryFragment fragment);
    void inject(MovieDetailFragment fragment);

    MoviesApi getMoviesApiClient();
    Gallery getGallery();
    Gallery.GalleryType getSavedGalleryType();
    IGalleryPresenter getGalleryPresenter();
    IMovieDetailsPresenter getMovieDetailsPresenter();

    final class Initializer {
        public static Graph init(Application application) {
            return DaggerGraph.builder()
                    .appModule(new AppModule(application))
                    .dataModule(new DataModule())
                    .apiModule(new ApiModule())
                    .presenterModule(new PresenterModule())
                    .build();
        }
    }
}
