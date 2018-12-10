package com.academy.fundamentals.ex3.details;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.academy.fundamentals.ex3.R;

public class MovieDetails extends AppCompatActivity {

    public static final String EXTRA_ITEM_POSITION = "init-position-data";
    private ViewPager mDetailsViewPager;
    private MovieDetailsViewAdapter mMovieDetailsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        this.mDetailsViewPager = (ViewPager) super.findViewById(R.id.detailsViewPager);
        this.mMovieDetailsViewAdapter =
                new MovieDetailsViewAdapter(super.getSupportFragmentManager());
        this.mDetailsViewPager.setAdapter(this.mMovieDetailsViewAdapter);

        int startPosition = getIntent().getIntExtra(MovieDetails.EXTRA_ITEM_POSITION, 0);
        this.mDetailsViewPager.setCurrentItem(startPosition, false);

    }
}
