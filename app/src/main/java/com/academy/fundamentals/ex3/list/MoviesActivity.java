package com.academy.fundamentals.ex3.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.academy.fundamentals.ex3.BackgroundServices.BGServiceActivity;
import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.db.AppDatabase;
import com.academy.fundamentals.ex3.db.MovieDao;
import com.academy.fundamentals.ex3.details.MovieDetails;
import com.academy.fundamentals.ex3.model.MovieListResult;
import com.academy.fundamentals.ex3.model.MovieModel;
import com.academy.fundamentals.ex3.model.MovieModelConverter;
import com.academy.fundamentals.ex3.model.MoviesContent;
import com.academy.fundamentals.ex3.rest.RestClientManager;
import com.academy.fundamentals.ex3.threads.AsyncTaskActivity;
import com.academy.fundamentals.ex3.threads.ThreadsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    private RecyclerView mRecyclerView;
    private MoviesViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        this.initRecyclerView();

        this.loadMovies();

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

            case R.id.action_open_background_services:
                // Open Thread Handler Activity
                startActivity(new Intent(MoviesActivity.this, BGServiceActivity.class));
                return true;

            case R.id.delete_db:
                AppDatabase appDatabase = AppDatabase.getInstance(this);
                appDatabase.movieDao().deleteAll();
                appDatabase.videoDao().deleteAll();
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
    }


    private void loadMovies() {
        // MoviesContent.loadMovies();

        List<MovieModel> cachedMovies = AppDatabase.getInstance(this).movieDao().getAll();
        MoviesContent.loadMovies(cachedMovies);
        mAdapter = new MoviesViewAdapter(MoviesActivity.this,
                MoviesContent.MOVIES, MoviesActivity.this);
        // Log.v("doron - x", MoviesContent.MOVIES.size() + "   "  + mRecyclerView );
        mRecyclerView.setAdapter(mAdapter);

        // New API Service call
        Call<MovieListResult> call = RestClientManager.moviesService.getPopularMovies();
        call.enqueue(new Callback<MovieListResult>() {
            @Override
            public void onResponse(Call<MovieListResult> call, Response<MovieListResult> response) {
                if (response.isSuccessful()) {
                    ArrayList<MovieModel> movieModelArrayList = MovieModelConverter.convertResult(response.body());

                    MoviesContent.loadMovies(movieModelArrayList);

                    // Log.v("doron - y1", MoviesContent.MOVIES.size() + "");
                    mAdapter.setData(MoviesContent.MOVIES);

                    MovieDao movieDao = AppDatabase.getInstance(MoviesActivity.this).movieDao();
                    movieDao.deleteAll();
                    movieDao.insertAll(MoviesContent.MOVIES);
                }
            }

            @Override
            public void onFailure(Call<MovieListResult> call, Throwable t) {
                Toast.makeText(MoviesActivity.this, R.string.something_went_wrong_text,
                        Toast.LENGTH_SHORT).show();
            }

        });

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
