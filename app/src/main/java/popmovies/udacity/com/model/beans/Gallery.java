/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import popmovies.udacity.com.model.mappers.GalleryMapper;

/**
 * Abstraction of a single gallery which contains list of movies
 */
public class Gallery implements Parcelable {

    /**
     * Gallery can contain popular or top rated movies
     */
    public enum GalleryType {
        POPULAR,
        TOP_RATED,
        UNDEFINED;

        /**
         * Returns {@link GalleryType} for given ordinal
         * @param ordinal Ordinal of an enum
         * @return {@link GalleryType} for given ordinal
         */
        public static GalleryType fromOrdinal(int ordinal) {
            return values()[ordinal];
        }
    }

    /**
     * List of movies saved in a gallery
     */
    protected List<Movie> mMoviesList;

    /**
     * Gallery type
     */
    protected GalleryType mGalleryType;

    /**
     * Defines if there are more movies in gallery to load
     */
    protected boolean mHasMore;

    /**
     * Last loaded page
     */
    protected int mLastLoadedPage;

    /**
     * Creates single instance of a gallery.
     */
    public Gallery() {
        mGalleryType = GalleryType.UNDEFINED;
        mHasMore = true;
        mLastLoadedPage = -1;
    }

    /**
     * Adds list of movies to the existing collection of movies
     * to the end of the existing list.
     * @param movies List of movies that should be added to the gallery
     */
    public void addMovies(List<Movie> movies) {
        if (mMoviesList == null) {
            mMoviesList = new ArrayList<>();
        }

        if (movies == null || movies.size() == 0) return;

        mMoviesList.addAll(movies);
    }

    /**
     * Adds a movie at the end of the existing collection of movies
     * @param movie Movie that should be added to the gallery
     */
    public void addMovie(Movie movie) {
        if (mMoviesList == null) {
            mMoviesList = new ArrayList<>();
        }

        if (movie == null) return;

        mMoviesList.add(movie);
    }

    /**
     * Clears the list of movies in the gallery
     */
    public void clear() {
        if (mMoviesList == null) return;

        mMoviesList.clear();
    }

    public List<Movie> getMovies() {
        return mMoviesList;
    }

    public void setGalleryType(GalleryType galleryType) {
        mGalleryType = galleryType;
    }

    public GalleryType getGalleryType() {
        return mGalleryType;
    }

    public void setHasMore(boolean hasMore) {
        mHasMore = hasMore;
    }

    public boolean hasMore() {
        return mHasMore;
    }

    public void setLastLoadedPage(int lastLoadedPage) {
        mLastLoadedPage = lastLoadedPage;
    }

    public int getLastLoadedPage() {
        return mLastLoadedPage;
    }

    /**
     * Returns number of next page to load or null if previous not set
     * @return number of next page to load or null if previous not set
     */
    public Integer getNextPageToLoad() {
        if (mLastLoadedPage == -1) {
            return null;
        }

        return mLastLoadedPage + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        GalleryMapper.writeToParcel(dest, this);
    }

    /**
     * Creator that generates instances of class from a Parcel
     */
    public static final Parcelable.Creator<Gallery> CREATOR = new Parcelable.Creator<Gallery>() {
        @Override
        public Gallery createFromParcel(Parcel in) {
            return GalleryMapper.constructFromParcel(in);
        }

        @Override
        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }
}
