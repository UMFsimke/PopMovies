/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.mappers;

import android.os.Parcel;
import android.support.annotation.NonNull;

import popmovies.udacity.com.model.beans.Genre;

/**
 * Defines set of static methods used to create and map {@link Genre}
 */
public class GenreMapper {

    /**
     * Writes {@link Genre} object to {@link Parcel}
     * @param parcel Parcel object
     * @param genre Genre object
     */
    public static void writeToParcel(@NonNull Parcel parcel, @NonNull Genre genre) {
        parcel.writeInt(genre.getId());
        parcel.writeString(genre.getName());
    }

    /**
     * Constructs {@link Genre} from {@link Parcel}
     * @param parcel Parcel object
     * @return Genre object constructed from {@link Parcel}
     */
    public static Genre constructFromParcel(@NonNull Parcel parcel) {
        Genre genre = new Genre();
        genre.setId(parcel.readInt());
        genre.setName(parcel.readString());
        return genre;
    }
}
