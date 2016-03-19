/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import popmovies.udacity.com.R;

/**
 * Shows the movies in a grid obtained from the API.
 */
public class PopMoviesActivity extends AppCompatActivity {

    /**
     * Toolbar of the activity
     */
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_movies);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }
}
