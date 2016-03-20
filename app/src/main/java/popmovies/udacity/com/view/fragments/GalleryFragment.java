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

import java.util.List;

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
     * Number of columns for tablet
     */
    private static final int TABLET_COLUMN_COUNT = 4;

    /**
     * Number of columns for phone
     */
    private static final int PHONE_COLUMN_COUNT = 3;

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

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mScrollListener != null) {
            mScrollListener.reset();
        }

        mPresenter.onScreenCreated();
    }

    /**
     * Initializes {@link GridLayoutManager} and {@link EndlessRecyclerOnScrollListener} for the {@link #mGallery}
     */
    protected void initRecyclerView() {
        //TODO: Implement autofit feature
        int numOfColumns = mIsTabletPaneUi ? TABLET_COLUMN_COUNT : PHONE_COLUMN_COUNT;

        GridLayoutManager manager = new GridLayoutManager(getContext(), numOfColumns);
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

    @Override
    public String getSettingsGalleryType() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return prefs.getString(getString(R.string.pref_gallery_type_key), null);
    }
}
