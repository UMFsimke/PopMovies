/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.mappers;

import android.os.Parcel;
import android.support.annotation.NonNull;

import popmovies.udacity.com.model.beans.Video;

/**
 * Defines set of static methods used to create and map {@link Video}
 */
public class VideoMapper {

    /**
     * Writes {@link Video} object to {@link Parcel}
     * @param parcel Parcel object
     * @param video Video object
     */
    public static void writeToParcel(@NonNull Parcel parcel, @NonNull Video video) {
        parcel.writeLong(video.getId());
        parcel.writeString(video.getName());
        parcel.writeString(video.getYoutubeKey());
        parcel.writeLong(video.getMovieId());
    }

    /**
     * Constructs {@link Video} from {@link Parcel}
     * @param parcel Parcel object
     * @return Video object constructed from {@link Parcel}
     */
    public static Video constructFromParcel(@NonNull Parcel parcel) {
        Video video = new Video();
        video.setId(parcel.readLong());
        video.setName(parcel.readString());
        video.setYoutubeKey(parcel.readString());
        video.setMovieId(parcel.readLong());
        return video;
    }
}
