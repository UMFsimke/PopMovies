/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

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
}
