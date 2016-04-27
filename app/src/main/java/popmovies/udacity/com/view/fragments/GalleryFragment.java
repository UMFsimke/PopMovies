/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import popmovies.udacity.com.PopMovies;
import popmovies.udacity.com.R;
import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.presenter.interfaces.presenter.IGalleryPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IGalleryView;
import popmovies.udacity.com.view.adapter.GalleryAdapter;
import popmovies.udacity.com.view.controls.EndlessRecyclerOnScrollListener;

/**
 * A fragment containing gallery of movies downloaded from API
 */
public class GalleryFragment extends BaseFragment<IGalleryPresenter> implements IGalleryView {

    /**
     * RecyclerView that is used to render movies
     */
    @Bind(R.id.gallery_recycler_view) protected RecyclerView mGallery;

    /**
     * Scroll listener for lazy loading
     */
    protected EndlessRecyclerOnScrollListener mScrollListener;

    @Override
    protected void inject() {
        PopMovies.getInstance().graph().inject(this);
    }

    @NonNull
    @Override
    protected Integer getLayout() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void onPrepareLayoutFinished() {
        initRecyclerView();
    }

    /**
     * Initializes {@link GridLayoutManager} and {@link EndlessRecyclerOnScrollListener} for the {@link #mGallery}
     */
    protected void initRecyclerView() {
        //TODO: Implement autofit feature
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mGallery.setLayoutManager(manager);

        mScrollListener = new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore() {
                if (mPresenter == null) return;
                mPresenter.loadMovies();
            }
        };
        mGallery.addOnScrollListener(mScrollListener);
        mGallery.getRecycledViewPool().setMaxRecycledViews(0, 1000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetScroll() {
        if (mScrollListener != null) {
            mScrollListener.reset();
        }

        mGallery.scrollToPosition(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderGallery(Gallery gallery) {
        GalleryAdapter adapter = (GalleryAdapter) mGallery.getAdapter();
        if (adapter == null) {
            adapter = new GalleryAdapter(gallery);
            mGallery.setAdapter(adapter);
            return;
        }

        adapter.replaceItems(gallery);
    }

    @Override
    protected void setContentVisibility(int visibility) {
        mGallery.setVisibility(visibility);
    }
}