package com.academy.fundamentals.ex3.details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.academy.fundamentals.ex3.model.MoviesContent;

public class MovieDetailsViewAdapter  extends FragmentStatePagerAdapter {
    public MovieDetailsViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int aPosition) {
        return MovieDetailsFragment.newInstance(MoviesContent.MOVIES.get(aPosition));
    }

    @Override
    public int getCount() {
        return MoviesContent.size();
    }
}
