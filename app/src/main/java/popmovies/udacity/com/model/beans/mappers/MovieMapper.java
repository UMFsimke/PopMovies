/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.beans.mappers;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.List;

import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.model.beans.Review;
import popmovies.udacity.com.model.beans.Video;
import popmovies.udacity.com.model.database.MovieContract;

/**
 * Defines set of static methods used to create and map {@link Movie}
 */
public class MovieMapper {

    private static final int COLUMN_MOVIE_ID = 0;
    private static final int COLUMN_TITLE = 1;
    private static final int COLUMN_POSTER_PATH = 2;
    private static final int COLUMN_OVERVIEW = 3;
    private static final int COLUMN_USER_RATING = 4;
    private static final int COLUMN_RELEASE_DATE = 5;

    public static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_USER_RATING,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE
    };

    /**
     * Writes {@link Movie} object to {@link Parcel}
     * @param parcel Parcel object
     * @param movie Movie object
     */
    public static void writeToParcel(@NonNull Parcel parcel, @NonNull Movie movie) {
        parcel.writeString(movie.getId());
        parcel.writeString(movie.getTitle());
        parcel.writeString(movie.getPosterPath());
        parcel.writeString(movie.getPlotOverview());
        parcel.writeDouble(movie.getUserRating());
        parcel.writeString(movie.getReleaseDate());
        parcel.writeByte(movie.isFavorite() ? (byte) 1 : 0);
        parcel.writeTypedList(movie.getReviews());
        parcel.writeTypedList(movie.getVideos());
    }

    /**
     * Constructs {@link Movie} from {@link Parcel}
     * @param parcel Parcel object
     * @return Movie object constructed from {@link Parcel}
     */
    public static Movie constructFromParcel(@NonNull Parcel parcel) {
        Movie movie = new Movie();
        movie.setId(parcel.readString());
        movie.setTitle(parcel.readString());
        movie.setPosterPath(parcel.readString());
        movie.setPlotOverview(parcel.readString());
        movie.setUserRating(parcel.readDouble());
        movie.setReleaseDate(parcel.readString());
        movie.setFavorite(parcel.readByte() == 1);
        List<Review> reviews = parcel.createTypedArrayList(Review.CREATOR);
        movie.setReviews(reviews);

        List<Video> videos = parcel.createTypedArrayList(Video.CREATOR);
        movie.setVideos(videos);
        return movie;
    }

    /**
     * Construct {@link ContentValues} from a given movie
     * @param movie Movie
     * @return {@link ContentValues}
     */
    public static ContentValues constructContentValues(@NonNull Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry._ID, movie.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getPlotOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_USER_RATING, movie.getUserRating());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        return contentValues;
    }

    public static Movie constructFromCursor(@NonNull Cursor cursor, @NonNull Movie movie) {
        movie.setId(cursor.getString(COLUMN_MOVIE_ID));
        movie.setTitle(cursor.getString(COLUMN_TITLE));
        movie.setPlotOverview(cursor.getString(COLUMN_OVERVIEW));
        movie.setReleaseDate(cursor.getString(COLUMN_RELEASE_DATE));
        movie.setUserRating(cursor.getDouble(COLUMN_USER_RATING));
        movie.setPosterPath(cursor.getString(COLUMN_POSTER_PATH));
        movie.setFavorite(true);
        return movie;
    }
}
