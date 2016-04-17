/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Content provider for favorite movies
 */
public class MovieProvider extends ContentProvider {

    /**
     * Uri matcher
     */
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     * Database helper
     */
    private DatabaseHelper mDatabaseHelper;

    /**
     * Static codes for URI matching
     */
    static final int MOVIE = 100;
    static final int VIDEO = 200;
    static final int FAVORITE_MOVIE_VIDEOS = 201;
    static final int REVIEW = 300;
    static final int FAVORITE_MOVIE_REVIEWS = 301;

    /**
     * Builds {@link UriMatcher} for content provider
     * @return {@link UriMatcher} that matches {@link Uri}
     */
    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_REVIEW, REVIEW);
        matcher.addURI(authority, MovieContract.PATH_VIDEO, VIDEO);
        matcher.addURI(authority, MovieContract.PATH_VIDEO + "/*", FAVORITE_MOVIE_VIDEOS);
        matcher.addURI(authority, MovieContract.PATH_REVIEW + "/*", FAVORITE_MOVIE_REVIEWS);
        return matcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case VIDEO:
            case FAVORITE_MOVIE_VIDEOS:
                return MovieContract.VideoEntry.CONTENT_TYPE;
            case REVIEW:
            case FAVORITE_MOVIE_REVIEWS:
                return MovieContract.ReviewEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_MOVIE_VIDEOS: {
                retCursor = getMovieVideos(uri, projection, sortOrder);
                break;
            }
            case FAVORITE_MOVIE_REVIEWS: {
                retCursor = getMovieReviews(uri, projection, sortOrder);
                break;
            }
            case MOVIE: {
                retCursor = mDatabaseHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case VIDEO: {
                retCursor = mDatabaseHelper.getReadableDatabase().query(
                        MovieContract.VideoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case REVIEW: {
                retCursor = mDatabaseHelper.getReadableDatabase().query(
                        MovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /**
     * Queries database for videos of a single movie
     * @param uri matched {@link Uri}
     * @param projection A list of which columns to return. Passing null will return all columns,
     * which is discouraged to prevent reading data from storage that isn't going to be used.
     * @param sortOrder Sort order
     * @return A {@link Cursor} object, which is positioned before the first entry.
     * Note that Cursors are not synchronized, see the documentation for more details.
     */
    private Cursor getMovieVideos(Uri uri, String[] projection, String sortOrder) {
        String movieId = MovieContract.VideoEntry.getMovieIdFromUri(uri);

        String selection = MovieContract.VideoEntry.COLUMN_MOVIE_ID + " = ?";
        String[] selectionArgs = new String[] { movieId };
        return mDatabaseHelper.getReadableDatabase().query(
                MovieContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    /**
     * Queries database for reviews of a single movie
     * @param uri matched {@link Uri}
     * @param projection A list of which columns to return. Passing null will return all columns,
     * which is discouraged to prevent reading data from storage that isn't going to be used.
     * @param sortOrder Sort order
     * @return A {@link Cursor} object, which is positioned before the first entry.
     * Note that Cursors are not synchronized, see the documentation for more details.
     */
    private Cursor getMovieReviews(Uri uri, String[] projection, String sortOrder) {
        String movieId = MovieContract.ReviewEntry.getMovieIdFromUri(uri);

        String selection = MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = ?";
        String[] selectionArgs = new String[] { movieId };
        return mDatabaseHelper.getReadableDatabase().query(
                MovieContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIE: {
                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case VIDEO: {
                long _id = db.insert(MovieContract.VideoEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.VideoEntry.buildVideoUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case REVIEW: {
                long _id = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.ReviewEntry.buildReviewUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        String tableName = null;
        switch (match) {
            case MOVIE:
                tableName = MovieContract.MovieEntry.TABLE_NAME;
                break;
            case VIDEO:
                tableName = MovieContract.VideoEntry.TABLE_NAME;
                break;
            case REVIEW:
                tableName = MovieContract.ReviewEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        rowsDeleted = db.delete(tableName, selection, selectionArgs);
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        String tableName = null;
        switch (match) {
            case MOVIE:
                tableName = MovieContract.MovieEntry.TABLE_NAME;
                break;
            case VIDEO:
                tableName = MovieContract.VideoEntry.TABLE_NAME;
                break;
            case REVIEW:
                tableName = MovieContract.ReviewEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        rowsUpdated = db.update(tableName, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        String tableName = null;
        switch (match) {
            case REVIEW:
                tableName = MovieContract.ReviewEntry.TABLE_NAME;
                break;
            case VIDEO:
                tableName = MovieContract.VideoEntry.TABLE_NAME;
                break;
            case MOVIE:
                tableName = MovieContract.MovieEntry.TABLE_NAME;
                break;
            default:
                return super.bulkInsert(uri, values);
        }

        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(tableName, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown() {
        mDatabaseHelper.close();
        super.shutdown();
    }
}