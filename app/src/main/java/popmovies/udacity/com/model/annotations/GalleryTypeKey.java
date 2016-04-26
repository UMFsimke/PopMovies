package popmovies.udacity.com.model.annotations;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import popmovies.udacity.com.model.beans.Gallery;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifier name for shared prefs {@link Gallery.GalleryType} key
 */
@Qualifier
@Retention(RUNTIME)
public @interface GalleryTypeKey {
}
