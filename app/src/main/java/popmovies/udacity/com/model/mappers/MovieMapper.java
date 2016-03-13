/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.mappers;

import android.os.Parcel;
import android.support.annotation.NonNull;

import popmovies.udacity.com.model.beans.Movie;

/**
 * Defines set of static methods used to create and map {@link Movie}
 */
public class MovieMapper {

    /**
     * Writes {@link Movie} object to {@link Parcel}
     * @param parcel Parcel object
     * @param movie Movie object
     */
    public static void writeToParcel(@NonNull Parcel parcel, @NonNull Movie movie) {
        parcel.writeInt(movie.getId());
        parcel.writeString(movie.getTitle());
        parcel.writeString(movie.getPosterPath());
        parcel.writeString(movie.getPlotOverview());
        parcel.writeDouble(movie.getUserRating());
        parcel.writeString(movie.getReleaseDate());
    }

    /**
     * Constructs {@link Movie} from {@link Parcel}
     * @param parcel Parcel object
     * @return Movie object constructed from {@link Parcel}
     */
    public static Movie constructFromParcel(@NonNull Parcel parcel) {
        Movie movie = new Movie();
        movie.setId(parcel.readInt());
        movie.setTitle(parcel.readString());
        movie.setPosterPath(parcel.readString());
        movie.setPlotOverview(parcel.readString());
        movie.setUserRating(parcel.readDouble());
        movie.setReleaseDate(parcel.readString());
        return movie;
    }
}
