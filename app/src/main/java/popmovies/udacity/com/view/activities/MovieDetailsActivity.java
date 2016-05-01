/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import popmovies.udacity.com.R;
import popmovies.udacity.com.model.beans.Movie;
import popmovies.udacity.com.view.fragments.MovieDetailFragment;

/**
 * Shows the details of the movie
 */
public class MovieDetailsActivity extends AppCompatActivity {

    /**
     * Toolbar of the activity
     */
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Movie movie = getIntent().getParcelableExtra(MovieDetailFragment.EXTRA_MOVIE_KEY);
            MovieDetailFragment fragment = MovieDetailFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_fragment_container,
                            fragment,
                            MovieDetailFragment.TAG)
                    .commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}
