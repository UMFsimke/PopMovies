/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.mappers;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.List;

import popmovies.udacity.com.model.beans.Gallery;
import popmovies.udacity.com.model.beans.Movie;

/**
 * Defines set of static methods used to create and map {@link Gallery}
 */
public class GalleryMapper {

    private static final byte BOOLEAN_TRUE = 1;
    private static final byte BOOLEAN_FALSE = 0;

    /**
     * Writes {@link Gallery} object to {@link Parcel}
     * @param parcel Parcel object
     * @param gallery Gallery object
     */
    public static void writeToParcel(@NonNull Parcel parcel, @NonNull Gallery gallery) {
        parcel.writeByte(gallery.hasMore() ? BOOLEAN_TRUE : BOOLEAN_FALSE);
        parcel.writeInt(gallery.getGalleryType().ordinal());
        parcel.writeInt(gallery.getLastLoadedPage());
        parcel.writeTypedList(gallery.getMovies());
    }

    /**
     * Constructs {@link Gallery} from {@link Parcel}
     * @param parcel Parcel object
     * @return Gallery object constructed from {@link Parcel}
     */
    public static Gallery constructFromParcel(@NonNull Parcel parcel) {
        Gallery gallery = new Gallery();
        gallery.setHasMore(parcel.readByte() == BOOLEAN_TRUE);
        gallery.setGalleryType(
                Gallery.GalleryType.fromOrdinal(parcel.readInt())
        );
        gallery.setLastLoadedPage(parcel.readInt());

        List<Movie> movies = parcel.createTypedArrayList(Movie.CREATOR);
        gallery.addMovies(movies);
        return gallery;
    }
}
