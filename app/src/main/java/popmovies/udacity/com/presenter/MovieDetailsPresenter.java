/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;

import popmovies.udacity.com.PopMovies;
import popmovies.udacity.com.model.api.response.BaseResponse;
import popmovies.udacity.com.model.api.response.ReviewsResponse;
import popmovies.udacity.com.model.api.response.VideosResponse;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IMovieDetailsView;
import popmovies.udacity.com.view.fragments.MovieDetailFragment;
import retrofit2.Response;
import rx.Observable;

/**
 * Presenter that controls flow of movie details screen
 */
public class MovieDetailsPresenter extends BasePresenter<IMovieDetailsView>
        implements IMovieDetailsPresenter {

    /**
     * Key for movie bundle values
     */
    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    /**
     * Movie whos details will be rendered
     */
    protected Movie mMovie;

    public MovieDetailsPresenter() {
        super();
        PopMovies.getInstance().graph().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData(Bundle bundle) {
        if (bundle == null) return;

        mMovie = bundle.getParcelable(MovieDetailFragment.EXTRA_MOVIE_KEY);
    }

    /**
     * Loads movie's details if screen is created
     */
    @Override
    protected void onViewCreated() {
        loadReviews();
    }

    /**
     * Loads reviews for a movie from API
     */
    protected void loadReviews() {
        if (getView() == null || mMovie == null) return;

        Observable<Response<ReviewsResponse>> retrofitObservable
                = mApi.getMovieReviews(mMovie.getId(), Constants.API_KEY);
        makeApiCall(retrofitObservable);
    }

    /**
     * Loads videos for a movie from API
     */
    protected void loadVideos() {
        if (getView() == null || mMovie == null) return;

        Observable<Response<VideosResponse>> retrofitObservable
                = mApi.getMovieVideos(mMovie.getId(), Constants.API_KEY);
        makeApiCall(retrofitObservable);
    }

    @Override
    protected <T extends BaseResponse> void onApiResponse(T apiResponse) {
        if (apiResponse instanceof ReviewsResponse) {
            mMovie.setReviews(apiResponse.getResults());
            loadVideos();
            return;
        } else if (apiResponse instanceof VideosResponse) {
            mMovie.setVideos(apiResponse.getResults());
        }

        renderMovie();
    }

    /**
     * Renders a movie to the view
     */
    protected void renderMovie() {
        if (!isViewAttached()) return;

        getView().hideProgressBar();
        getView().renderMovie(mMovie);
    }

    /**
     * Loads movie's videos and reviews if they were not loaded.
     * Renders movie if screen is restored
     */
    @Override
    protected void onViewRestored() {
        if (mMovie != null && mMovie.getReviews() == null) {
            loadReviews();
            return;
        }

        if (mMovie != null && mMovie.getVideos() == null) {
            loadVideos();
        }

        renderMovie();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void restoreData(Bundle savedInstanceState) {
        mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_MOVIE, mMovie);
    }

    @Override
    public void onScreenResumed() {
    }

    @Override
    public void onAddToFavoritesClicked() {
        if (getView() == null || mMovie == null) return;

    }

    @Override
    public String getTrailerUrl() {
        return mMovie.getVideos().get(0).getYoutubeUrl();
    }

    @Override
    public boolean doesMovieHasTrailers() {
        return mMovie != null && mMovie.getVideos() != null && mMovie.getVideos().size() > 0;
    }
}
