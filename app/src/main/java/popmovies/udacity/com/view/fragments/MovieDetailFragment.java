/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import popmovies.udacity.com.PopMovies;
import popmovies.udacity.com.R;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.presenter.interfaces.presenter.IMovieDetailsPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IMovieDetailsView;
import popmovies.udacity.com.view.adapter.MovieDetailsAdapter;
import popmovies.udacity.com.view.controls.EndlessRecyclerOnScrollListener;

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
     * Recycler view that renders details of a movie
     */
    @Bind(R.id.movie_details_list) protected RecyclerView mMovieDetailsList;

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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void inject() {
        PopMovies.getInstance().graph().inject(this);
    }

    /**
     * {@inheritDoc}
     */
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
    protected void onPrepareLayoutFinished() {
        initRecyclerView();
    }

    /**
     * Initializes {@link LinearLayoutManager} and {@link EndlessRecyclerOnScrollListener}
     * for the {@link #mMovieDetailsList}
     */
    protected void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mMovieDetailsList.setLayoutManager(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderMovie(Movie movie) {
        MovieDetailsAdapter adapter = (MovieDetailsAdapter) mMovieDetailsList.getAdapter();
        if (adapter == null) {
            adapter = new MovieDetailsAdapter(movie);
            mMovieDetailsList.setAdapter(adapter);
            return;
        }

        adapter.replaceMovie(movie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setContentVisibility(int visibility) {
        mMovieDetailsList.setVisibility(visibility);
    }
}
