/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import popmovies.udacity.com.R;
import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.view.controls.GalleryThumbnail;

/**
 * Adapter for gallery that renders movies thumbnails
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{

    /**
     * Gallery items that will be rendered
     */
    protected Gallery mGallery;

    /**
     * Creates an instance of an adapter
     * @param gallery Gallery that will be rendered
     */
    public GalleryAdapter(Gallery gallery) {
        replaceItems(gallery);
    }

    /**
     * Replaces gallery in the adapter and renders new one
     * @param gallery Gallery to render
     */
    public void replaceItems(Gallery gallery) {
        mGallery = gallery;
        notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gallery, parent, false);
        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        holder.render(mGallery.getMovies().get(position));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return (mGallery == null || mGallery.getMovies() == null) ?
                0 :
                mGallery.getMovies().size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Gallery thumbnail
         */
        @Bind(R.id.thumbnail)
        protected GalleryThumbnail mThumnbail;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Renders a movie in grid cell
         * @param movie Movie to render
         */
        public void render(Movie movie) {
            if (movie == null) return;

            Picasso.with(mThumnbail.getContext().getApplicationContext())
                    .load(movie.getMoviePosterFullUrl())
                    .fit()
                    .centerCrop()
                    /*.error(R.drawable.thumbnail_placeholder)
                    .placeholder(R.drawable.thumbnail_placeholder)*/
                    .into(mThumnbail);
        }
    }
}
