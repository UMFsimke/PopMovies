/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.beans.mappers;

import android.content.ContentValues;
import android.os.Parcel;
import android.support.annotation.NonNull;

import popmovies.udacity.com.model.beans.Review;
import popmovies.udacity.com.model.database.MovieContract;

/**
 * Defines set of static methods used to create and map {@link Review}
 */
public class ReviewMapper {

    /**
     * Writes {@link Review} object to {@link Parcel}
     * @param parcel Parcel object
     * @param review Review object
     */
    public static void writeToParcel(@NonNull Parcel parcel, @NonNull Review review) {
        parcel.writeString(review.getId());
        parcel.writeString(review.getAuthor());
        parcel.writeString(review.getContent());
        parcel.writeString(review.getMovieId());
    }

    /**
     * Constructs {@link Review} from {@link Parcel}
     * @param parcel Parcel object
     * @return Review object constructed from {@link Parcel}
     */
    public static Review constructFromParcel(@NonNull Parcel parcel) {
        Review review = new Review();
        review.setId(parcel.readString());
        review.setAuthor(parcel.readString());
        review.setContent(parcel.readString());
        review.setMovieId(parcel.readString());
        return review;
    }

    /**
     * Construct {@link ContentValues} from a given review
     * @param review Review
     * @return {@link ContentValues}
     */
    public static ContentValues constructContentValues(@NonNull Review review) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.ReviewEntry._ID, review.getId());
        contentValues.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
        contentValues.put(MovieContract.ReviewEntry.COLUMN_CONTENT, review.getContent());
        contentValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, review.getMovieId());
        return contentValues;
    }
}
