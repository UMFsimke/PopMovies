/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import popmovies.udacity.com.R;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.model.beans.Review;
import popmovies.udacity.com.model.beans.Video;

/**
 * Adapter that renders movie details
 */
public class MovieDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * View tyep for movie details
     */
    private static final byte VIEW_TYPE_MOVIE_DETAILS = 0;

    /**
     * View type for review section header
     */
    private static final byte VIEW_TYPE_REVIEW_HEADER = 1;

    /**
     * View type for video section header
     */
    private static final byte VIEW_TYPE_VIDEO_HEADER = 2;

    /**
     * View type for trailer
     */
    private static final byte VIEW_TYPE_VIDEO = 3;

    /**
     * View type for review
     */
    private static final byte VIEW_TYPE_REVIEW = 4;

    /**
     * View type for placeholder
     */
    private static final byte VIEW_TYPE_PLACEHOLDER = 5;

    /**
     * First video starts from position two, first position are movie details, second
     * section header
     */
    private static final byte FIRST_VIDEO_OFFSET_POSITION = 2;

    /**
     * First review has offset of 1 comparing to his title in position
     */
    private static final byte FIRST_REVIEW_OFFSET = 1;

    /**
     * Movie that will be rendered
     */
    protected Movie mMovie;

    /**
     * Creates an instance of an adapter
     * @param movie Movie that will be rendered
     */
    public MovieDetailsAdapter(Movie movie) {
        replaceMovie(movie);
    }

    /**
     * Replaces movie in the adapter and renders new one
     * @param movie Movie to render
     */
    public void replaceMovie(Movie movie) {
        mMovie = movie;
        notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_MOVIE_DETAILS:
                view = layoutInflater.inflate(R.layout.list_item_movie_details, parent, false);
                return new MovieDetailsViewHolder(view);
            case VIEW_TYPE_REVIEW:
                view = layoutInflater.inflate(R.layout.list_item_movie_review, parent, false);
                return new ReviewViewHolder(view);
            case VIEW_TYPE_REVIEW_HEADER:
            case VIEW_TYPE_VIDEO_HEADER:
                view = layoutInflater
                        .inflate(R.layout.list_item_movie_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            case VIEW_TYPE_VIDEO:
                view = layoutInflater.inflate(R.layout.list_item_movie_trailer, parent, false);
                return new TrailerViewHolder(view);
            default:
                view = layoutInflater.inflate(
                        R.layout.list_item_movie_details_placeholder, parent, false);
                return new PlaceholderViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (shouldShowPlaceholder()) {
            return VIEW_TYPE_PLACEHOLDER;
        }

        if (shouldShowMovieDetails(position)) {
            return VIEW_TYPE_MOVIE_DETAILS;
        }

        if (shouldShowTrailersSectionHeader(position)) {
            return VIEW_TYPE_VIDEO_HEADER;
        }

        if (shouldShowTrailer(position)) {
            return VIEW_TYPE_VIDEO;
        }

        if (shouldShowReviewSectionHeader(position)) {
            return VIEW_TYPE_REVIEW_HEADER;
        }

        return VIEW_TYPE_REVIEW;
    }

    boolean shouldShowPlaceholder() {
        return mMovie == null;
    }

    boolean shouldShowMovieDetails(int position) {
        return position == 0 && mMovie != null;
    }

    boolean shouldShowTrailersSectionHeader(int position) {
        return position == 1 && mMovie.getVideos() != null && mMovie.getVideos().size() > 0;
    }

    boolean shouldShowTrailer(int position) {
        int bottomTrailerDelimiter = FIRST_VIDEO_OFFSET_POSITION + mMovie.getVideos().size();
        return position > 1 && position < bottomTrailerDelimiter;
    }

    boolean shouldShowReviewSectionHeader(int position) {
        if (mMovie.getVideos() == null || mMovie.getVideos().size() == 0) {
            return position == 1 && mMovie.getReviews() != null && mMovie.getReviews().size() > 0;
        } else {
            return (position == FIRST_VIDEO_OFFSET_POSITION + mMovie.getVideos().size())
                    && mMovie.getReviews() != null && mMovie.getReviews().size() > 0;
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_REVIEW_HEADER:
                ((SectionHeaderViewHolder) holder).renderReviewsTitle();
                break;
            case VIEW_TYPE_REVIEW:
                ((ReviewViewHolder) holder).render(getReviewByPosition(position));
                break;
            case VIEW_TYPE_VIDEO:
                ((TrailerViewHolder) holder).render(getVideoByPosition(position));
                break;
            case VIEW_TYPE_MOVIE_DETAILS:
                ((MovieDetailsViewHolder) holder).render(mMovie);
                break;
            case VIEW_TYPE_VIDEO_HEADER:
                ((SectionHeaderViewHolder) holder).renderTrailersTitle();
                break;
            default:
                break;
        }
    }

    Review getReviewByPosition(int position) {
        if (mMovie.getVideos() == null || mMovie.getVideos().size() == 0) {
            return mMovie.getReviews().get(position - FIRST_VIDEO_OFFSET_POSITION);
        } else {
            int reviewPosition = position -
                    FIRST_VIDEO_OFFSET_POSITION - mMovie.getVideos().size() - FIRST_REVIEW_OFFSET;
            return mMovie.getReviews().get(reviewPosition);
        }
    }

    Video getVideoByPosition(int position) {
        return mMovie.getVideos().get(position - FIRST_VIDEO_OFFSET_POSITION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        if (mMovie == null) return 1;

        int rowCount = 1;
        if (mMovie.getVideos() != null && mMovie.getVideos().size() > 0) {
            rowCount += mMovie.getVideos().size() + 1;
        }

        if (mMovie.getReviews() != null && mMovie.getReviews().size() > 0) {
            rowCount += mMovie.getReviews().size() + 1;
        }

        return rowCount;
    }

    protected static class MovieDetailsViewHolder extends RecyclerView.ViewHolder {

        /**
         * Movie title
         */
        @Bind(R.id.movie_detail_title) protected TextView mTitle;

        /**
         * Movie information
         */
        @Bind(R.id.movie_detail_info) protected TextView mMovieInfo;

        /**
         * Thumbnail
         */
        @Bind(R.id.movie_detail_thumbnail) protected ImageView mThumnbail;

        /**
         * Overview
         */
        @Bind(R.id.movie_detail_overview) protected TextView mOverview;

        /**
         * Creates view holder for a view
         * @param itemView View
         */
        public MovieDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Renders a movie details
         * @param movie Movie to render
         */
        public void render(Movie movie) {
            if (movie == null) return;

            Context context = mThumnbail.getContext();
            mTitle.setText(movie.getTitle());
            mOverview.setText(movie.getPlotOverview());

            String releaseDateString = TextUtils.isEmpty(movie.getReleaseDate()) ?
                    context.getString(R.string.date_not_defined) :
                    movie.getReleaseDate();

            String ratingString = movie.getUserRating() == 0 ?
                    context.getString(R.string.rating_not_defined) :
                    String.valueOf(movie.getUserRating());
            String movieInfo = String.format(
                    context.getString(R.string.format_movie_details),
                    releaseDateString,
                    ratingString);

            mMovieInfo.setText(movieInfo);
            Picasso.with(mThumnbail.getContext().getApplicationContext())
                    .load(movie.getMoviePosterFullUrl())
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_stop)
                    .placeholder(R.drawable.ic_action_refresh)
                    .into(mThumnbail);

            itemView.setContentDescription(movie.getTitle());
        }
    }

    protected static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

        /**
         * Section title
         */
        @Bind(R.id.header_title) protected TextView mTitle;

        /**
         * Creates view holder for a view
         * @param itemView View
         */
        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Renders title for reviews section
         */
        public void renderReviewsTitle() {
            mTitle.setText(R.string.reviews_title);
        }

        /**
         * Renders title for trailers section
         */
        public void renderTrailersTitle() {
            mTitle.setText(R.string.trailers_title);
        }
    }

    protected static class ReviewViewHolder extends RecyclerView.ViewHolder {

        /**
         * Review author
         */
        @Bind(R.id.review_author) protected TextView mAuthor;

        /**
         * Review content
         */
        @Bind(R.id.review) protected TextView mContent;

        /**
         * Creates view holder for a view
         * @param itemView View
         */
        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Renders review
         * @param review Review to render
         */
        public void render(Review review) {
            mAuthor.setText(review.getAuthor());
            mContent.setText(review.getContent());
        }
    }

    protected static class TrailerViewHolder extends RecyclerView.ViewHolder {

        /**
         * Trailer name
         */
        @Bind(R.id.trailer_name) protected TextView mTrailerName;

        /**
         * Creates view holder for a view
         * @param itemView View
         */
        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Renders trailer
         * @param trailer Trailer to render
         */
        public void render(Video trailer) {
            mTrailerName.setText(trailer.getName());
            mTrailerName.setTag(trailer.getYoutubeUrl());
        }

        @OnClick(R.id.trailer_name)
        void onTrailerClicked(View v) {
            String url = (String) mTrailerName.getTag();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            v.getContext().startActivity(i);
        }
    }

    protected static class PlaceholderViewHolder extends RecyclerView.ViewHolder {

        public PlaceholderViewHolder(View itemView) {
            super(itemView);
        }
    }
}