package com.academy.fundamentals.ex3.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.details.MovieDetails;
import com.academy.fundamentals.ex3.model.MoviesContent;


public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        this.loadMovies();

        this.initRecyclerView();
    }

    private void initRecyclerView() {
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mAdapter = new MoviesViewAdapter(this, MoviesContent.MOVIES, this);
        this.mRecyclerView.setAdapter(this.mAdapter);
    }


    private void loadMovies() {
        MoviesContent.loadMovies();
    }

    @Override
    public void onMovieClicked(int itemPosition) {
        if (itemPosition < 0 || itemPosition >= MoviesContent.size()) return;

        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra(MovieDetails.EXTRA_ITEM_POSITION, itemPosition);
        super.startActivity(intent);

//        MovieModel movieModel = this.movies.get(itemPosition);

//        Toast.makeText(this, movieModel.getName(), Toast.LENGTH_SHORT).show();

    }
}
