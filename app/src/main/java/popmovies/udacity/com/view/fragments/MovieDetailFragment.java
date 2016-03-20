/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import popmovies.udacity.com.R;
import popmovies.udacity.com.presenter.PresenterFactory;
import popmovies.udacity.com.presenter.interfaces.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.IMovieDetailsView;

/**
 * Shows details of a movie
 */
public class MovieDetailFragment extends Fragment implements IMovieDetailsView {

    /**
     * Bundle key for the movie
     */
    public static final String EXTRA_MOVIE_KEY = "EXTRA_MOVIE_KEY";

    /**
     * Tag of fragment
     */
    public static final String TAG = MovieDetailFragment.class.getSimpleName();

    /**
     * Movie details presenter that is controlling flow of the screen
     */
    protected IMovieDetailsPresenter mPresenter;

    /**
     * Movie title text view
     */
    @Bind(R.id.movie_detail_title)
    TextView mMovieTitle;

    /**
     * Movie info text view
     */
    @Bind(R.id.movie_detail_info)
    TextView mMovieInfo;

    /**
     * Movie overview text view
     */
    @Bind(R.id.movie_detail_overview)
    TextView mMovieOverview;

    /**
     * Movie thumbnail image view
     */
    @Bind(R.id.movie_detail_thumbnail)
    ImageView mThumbnail;

    /**
     * Progress bar
     */
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    /**
     * User friendly message when movie is not chosen
     * or doesnt exists on API with given movie ID
     */
    @Bind(R.id.movie_details_empty)
    TextView mChooseMovie;

    /**
     * Creates new instance of a fragment
     * @param movieId Movie ID that should be loaded
     * @return Movie Detail fragment instance
     */
    public static MovieDetailFragment newInstance(int movieId) {
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_MOVIE_KEY, movieId);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initPresenter();
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * Initializes presenter
     */
    protected void initPresenter() {
        mPresenter = PresenterFactory.getMovieDetailsPresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            int movieId = arguments.getInt(EXTRA_MOVIE_KEY, -1);
            mPresenter.setMovieIdToLoad(movieId);
        }

        View rootView = inflater.inflate(R.layout.fragment_movies_detail, container, false);
        ButterKnife.bind(this, rootView);
        mPresenter.onScreenCreated();
        return rootView;
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
    public void setMovieDetails(int duration, String[] genres, String releaseDate, double rating) {
        String genresString = null;
        if (genres != null && genres.length > 0) {
            String formatPattern = "";
            for (int i = 0; i < genres.length; i++) {
                formatPattern += "%s";
                if (i < genres.length-1) {
                    formatPattern += ", ";
                }
            }

            genresString = String.format(formatPattern, genres);
        }

        String durationString = duration == 0 ?
                getString(R.string.duration_not_defined) :
                String.valueOf(duration);

        genresString = TextUtils.isEmpty(genresString) ?
                getString(R.string.genres_not_defined) :
                genresString;

        String releaseDateString = TextUtils.isEmpty(releaseDate) ?
                getString(R.string.date_not_defined) :
                releaseDate;

        String ratingString = rating == 0 ?
                getString(R.string.rating_not_defined) :
                String.valueOf(rating);
        String movieInfo = String.format(
                getString(R.string.format_movie_details),
                durationString,
                genresString,
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMovieInvalidId() {
        mChooseMovie.setVisibility(View.VISIBLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMovieValid() {
        mChooseMovie.setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showServerErrorMessage() {
        Toast.makeText(getContext(),
                R.string.api_experiencing_problems_message,
                Toast.LENGTH_LONG)
                .show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showNoInternetConnection() {
        Toast.makeText(getContext(),
                R.string.no_internet_connection_message,
                Toast.LENGTH_LONG)
                .show();
    }

    /**
     * Refreshes content of screen
     */
    public void refresh() {
        mPresenter.onScreenCreated();
    }
}
