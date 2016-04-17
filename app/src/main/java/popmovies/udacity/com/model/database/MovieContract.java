/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import popmovies.udacity.com.model.beans.Gallery;

/**
 * Defines table and column names for the movie database.
 */
public class MovieContract {

    /**
     * Content authority for movie content provider
     */
    public static final String CONTENT_AUTHORITY = "popmovies.udacity.com";

    /**
     * Base content URI
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Movie content provider subpath
     */
    public static final String PATH_MOVIE = "movie";

    /**
     * Video content provider subpath
     */
    public static final String PATH_VIDEO = "video";

    /**
     * Review content provider subpath
     */
    public static final String PATH_REVIEW = "review";

    /**
     * Defines table contents of the review table
     */
    public static final class ReviewEntry implements BaseColumns {

        /**
         * Content URI
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        /**
         * Table name
         */
        public static final String TABLE_NAME = "review";

        /**
         * Author column
         */
        public static final String COLUMN_AUTHOR = "author";

        /**
         * Content column
         */
        public static final String COLUMN_CONTENT = "content";

        /**
         * Column for foreign movie ID key
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static Uri buildReviewsWithMovieId(long movieId) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId)).build();
        }

        public static long getMovieIdFromUri(Uri uri) {
            String movieId = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if (null != movieId && movieId.length() > 0) {
                return Long.parseLong(movieId);
            }

            return 0;
        }
    }

    /**
     * Defines table contents of the video table
     */
    public static final class VideoEntry implements BaseColumns {

        /**
         * Content URI
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEO;

        /**
         * Table name
         */
        public static final String TABLE_NAME = "video";

        /**
         * Name column
         */
        public static final String COLUMN_NAME = "name";

        /**
         * Youtube key column
         */
        public static final String COLUMN_YOUTUBE_KEY = "youtube_key";

        /**
         * Column for foreign movie ID key
         */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static Uri buildVideosWithMovieId(long movieId) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_MOVIE_ID, Long.toString(movieId)).build();
        }

        public static long getMovieIdFromUri(Uri uri) {
            String movieId = uri.getQueryParameter(COLUMN_MOVIE_ID);
            if (null != movieId && movieId.length() > 0) {
                return Long.parseLong(movieId);
            }

            return 0;
        }
    }

    /**
     * Defines table contents of the video table
     */
    public static final class MovieEntry implements BaseColumns {

        /**
         * Content URI
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        /**
         * Table name
         */
        public static final String TABLE_NAME = "movie";

        /**
         * Title column
         */
        public static final String COLUMN_TITLE = "title";

        /**
         * Poster path column
         */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /**
         * Overview column
         */
        public static final String COLUMN_OVERVIEW = "overview";

        /**
         * User rating column
         */
        public static final String COLUMN_USER_RATING = "user_rating";

        /**
         * Release date column
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /**
         * Sort by query parameter
         */
        private static final String SORT_TYPE_PARAMETER = "sort_by";

        public static Uri buildMoviesWithType(Gallery.GalleryType galleryType) {
            String galleryTypeParameterValue = Integer.toString(galleryType.ordinal());
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(SORT_TYPE_PARAMETER, galleryTypeParameterValue).build();
        }

        public static Gallery.GalleryType getSortByFromUri(Uri uri) {
            String galleryTypeParameterValue = uri.getQueryParameter(SORT_TYPE_PARAMETER);
            if (null != galleryTypeParameterValue && galleryTypeParameterValue.length() > 0) {
                int galleryTypeOrdinal = Integer.parseInt(galleryTypeParameterValue);
                return Gallery.GalleryType.fromOrdinal(galleryTypeOrdinal);
            }

            return Gallery.GalleryType.POPULAR;
        }
    }
}
