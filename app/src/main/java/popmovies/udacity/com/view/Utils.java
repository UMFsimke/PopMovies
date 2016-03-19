/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view;

import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.view.Display;

/**
 * Utility static methods
 */
public class Utils {

    /**
     * Returns width of screen in pixels
     * @param activity Calling activity
     * @return Width of screen in pixels
     */
    public static int getScreenWidth(FragmentActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
