/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import popmovies.udacity.com.R;
import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.presenter.PresenterFactory;
import popmovies.udacity.com.presenter.interfaces.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.IGalleryView;
import popmovies.udacity.com.view.adapter.GalleryAdapter;
import popmovies.udacity.com.view.controls.EndlessRecyclerOnScrollListener;

/**
 * A fragment containing gallery of movies downloaded from API
 */
public class GalleryFragment extends Fragment implements IGalleryView {

    /**
     * Number of columns
     */
    private static final int COLUMN_COUNT = 3;

    /**
     * Defining if view is shown on tablet or not
     */
    protected boolean mIsTabletPaneUi;

    /**
     * RecyclerView that is used to render movies
     */
    @Bind(R.id.gallery_recycler_view)
    protected RecyclerView mGallery;

    /**
     * Progress bar that is shown on the start of screen
     */
    @Bind(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    /**
     * Gallery presenter that is controlling flow of the screen
     */
    protected IGalleryPresenter mPresenter;

    /**
     * Scroll listener for lazy loading
     */
    protected EndlessRecyclerOnScrollListener mScrollListener;

    //TODO: implement abstract fragment for presenter and interface of a view
    public GalleryFragment() {
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
        mPresenter = PresenterFactory.getGalleryPresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        ButterKnife.bind(this, rootView);
        initRecyclerView();
        mPresenter.onScreenCreated();

        return rootView;
    }

    /**
     * Initializes {@link GridLayoutManager} and {@link EndlessRecyclerOnScrollListener} for the {@link #mGallery}
     */
    protected void initRecyclerView() {
        //TODO: Implement autofit feature
        GridLayoutManager manager = new GridLayoutManager(getContext(), COLUMN_COUNT);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mGallery.setLayoutManager(manager);

        mScrollListener = new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                if (mPresenter == null) return;

                mPresenter.loadMoreMovies();
            }
        };
        mGallery.addOnScrollListener(mScrollListener);
        mGallery.getRecycledViewPool().setMaxRecycledViews(0, 1000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRefresh() {
        if (mScrollListener != null) {
            mScrollListener.reset();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onScreenResumed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGalleryUpdated(Gallery gallery) {
        if (gallery != null && gallery.getMovies() != null &&
                gallery.getMovies().size() > 0) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        GalleryAdapter adapter = (GalleryAdapter) mGallery.getAdapter();
        if (adapter == null) {
            adapter = new GalleryAdapter(gallery);
            mGallery.setAdapter(adapter);
            return;
        }

        adapter.replaceItems(gallery);
        return;
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
     * Returns settings gallery type saved in preferences
     * @return Gallery type saved in preferences
     */
    @Override
    public String getSettingsGalleryType() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return prefs.getString(getString(R.string.pref_gallery_type_key), null);
    }

    /**
     * Sets tablet mode
     * @param tabletMode <b>true</b> if is tablet mode, <b>false</b> otherwise
     */
    public void setIsTabletMode(boolean tabletMode) {
        mIsTabletPaneUi = tabletMode;
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
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
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
