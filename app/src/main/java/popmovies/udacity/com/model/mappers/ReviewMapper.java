/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.mappers;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.List;

import popmovies.udacity.com.model.beans.Review;

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
        //TODO: add id
        parcel.writeString(review.getAuthor());
        parcel.writeString(review.getContent());
    }

    /**
     * Constructs {@link Review} from {@link Parcel}
     * @param parcel Parcel object
     * @return Review object constructed from {@link Parcel}
     */
    public static Review constructFromParcel(@NonNull Parcel parcel) {
        Review review = new Review();
        review.setAuthor(parcel.readString());
        review.setContent(parcel.readString());
        return review;
    }
}
