package com.academy.fundamentals.ex3.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.details.MovieDetails;
import com.academy.fundamentals.ex3.model.MoviesContent;
import com.academy.fundamentals.ex3.threads.AsyncTaskActivity;
import com.academy.fundamentals.ex3.threads.ThreadsActivity;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_async_task:
                // Open Async Task Activity
                startActivity(new Intent(MoviesActivity.this, AsyncTaskActivity.class));
                return true;

            case R.id.action_open_thread_handler:
                // Open Thread Handler Activity
                startActivity(new Intent(MoviesActivity.this, ThreadsActivity.class));
                return true;

            default:
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
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
