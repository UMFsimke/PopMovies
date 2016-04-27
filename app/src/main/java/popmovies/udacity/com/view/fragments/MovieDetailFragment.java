/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import popmovies.udacity.com.PopMovies;
import popmovies.udacity.com.R;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IMovieDetailsView;

/**
 * Shows details of a movie
 */
public class MovieDetailFragment extends BaseFragment<IMovieDetailsPresenter>
        implements IMovieDetailsView {

    /**
     * Bundle key for the movie
     */
    public static final String EXTRA_MOVIE_KEY = "EXTRA_MOVIE_KEY";

    /**
     * Tag of fragment
     */
    public static final String TAG = MovieDetailFragment.class.getSimpleName();

    /**
     * Movie title text view
     */
    @Bind(R.id.movie_detail_title) TextView mMovieTitle;

    /**
     * Movie info text view
     */
    @Bind(R.id.movie_detail_info) TextView mMovieInfo;

    /**
     * Movie overview text view
     */
    @Bind(R.id.movie_detail_overview) TextView mMovieOverview;

    /**
     * Movie thumbnail image view
     */
    @Bind(R.id.movie_detail_thumbnail) ImageView mThumbnail;

    /**
     * User friendly message when movie is not chosen
     * or doesnt exists on API with given movie ID
     */
    @Bind(R.id.movie_details_empty) TextView mPlaceholderView;

    /**
     * Wrapper for all movie details views
     */
    @Bind(R.id.movie_details_wrapper) View mMovieDetailsWrapper;

    /**
     * Creates new instance of a fragment
     * @param movie Movie that should be loaded
     * @return Movie Detail fragment instance
     */
    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(EXTRA_MOVIE_KEY, movie);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void inject() {
        PopMovies.getInstance().graph().inject(this);
    }

    @NonNull
    @Override
    protected Integer getLayout() {
        return R.layout.fragment_movies_detail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter.loadData(getArguments());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMovieTitle(String movieTitle) {
        mMovieTitle.setText(movieTitle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMovieDetails(String releaseDate, double rating) {
        String releaseDateString = TextUtils.isEmpty(releaseDate) ?
                getString(R.string.date_not_defined) :
                releaseDate;

        String ratingString = rating == 0 ?
                getString(R.string.rating_not_defined) :
                String.valueOf(rating);
        String movieInfo = String.format(
                getString(R.string.format_movie_details),
                releaseDateString,
                ratingString);

        mMovieInfo.setText(movieInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMovieOverview(String movieOverview) {
        mMovieOverview.setText(movieOverview);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadThumbnailUrl(String thumbnailUrl) {
        Picasso.with(getContext().getApplicationContext())
                .load(thumbnailUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_action_refresh)
                .error(R.drawable.ic_stop)
                .into(mThumbnail);
    }

    @Override
    protected void setContentVisibility(int visibility) {
        hidePlaceholder();
        mMovieDetailsWrapper.setVisibility(visibility);
    }

    @Override
    public void showPlaceholder() {
        mPlaceholderView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlaceholder() {
        mPlaceholderView.setVisibility(View.GONE);
    }
}
