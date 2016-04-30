/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.presenter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;

import popmovies.udacity.com.PopMovies;
import popmovies.udacity.com.model.api.response.BaseResponse;
import popmovies.udacity.com.model.api.response.ReviewsResponse;
import popmovies.udacity.com.model.api.response.VideosResponse;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.model.beans.mappers.MovieMapper;
import popmovies.udacity.com.model.database.MovieContract;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IMovieDetailsView;
import popmovies.udacity.com.view.fragments.MovieDetailFragment;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
    public void onFavoritesClicked() {
        if (getView() == null || mMovie == null) return;

        if(mMovie.isFavorite()) {
            removeMovieFromFavorites();
            return;
        }

        addMovieToFavorites();
    }

    protected void removeMovieFromFavorites() {
        Observable deleteDataObservable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> observer) {
                if (getView() == null || mMovie == null) return;

                ContentResolver contentResolver = getView().getContext().getContentResolver();
                contentResolver.delete(MovieContract.MovieEntry.CONTENT_URI,
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{mMovie.getId()});
                contentResolver.delete(MovieContract.ReviewEntry.CONTENT_URI,
                        MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{mMovie.getId()});
                contentResolver.delete(MovieContract.VideoEntry.CONTENT_URI,
                        MovieContract.VideoEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{mMovie.getId()});

                if (observer.isUnsubscribed()) return;
                observer.onNext(true);
                observer.onCompleted();
            }
        });

        deleteDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        if (mMovie == null || getView() == null) return;

                        mMovie.setFavorite(false);
                        renderMovie();
                    }
                });
    }

    protected void addMovieToFavorites() {
        Observable addDataObservable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> observer) {
                if (getView() == null || mMovie == null) return;

                ContentResolver contentResolver = getView().getContext().getContentResolver();
                ContentValues movieContentValues = mMovie.getContentValues();
                contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, movieContentValues);
                ContentValues[] videosContentValues = mMovie.getVideosContentValues();
                if (videosContentValues != null) {
                    contentResolver.bulkInsert(MovieContract.VideoEntry.CONTENT_URI, videosContentValues);
                }

                ContentValues[] reviewsContentValues = mMovie.getReviewsContentValues();
                if (reviewsContentValues != null) {
                    contentResolver.bulkInsert(MovieContract.ReviewEntry.CONTENT_URI, reviewsContentValues);
                }

                if (observer.isUnsubscribed()) return;
                observer.onNext(true);
                observer.onCompleted();
            }
        });

        addDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        if (mMovie == null || getView() == null) return;

                        mMovie.setFavorite(true);
                        renderMovie();
                    }
                });
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
