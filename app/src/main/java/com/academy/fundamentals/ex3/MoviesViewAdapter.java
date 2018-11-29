package com.academy.fundamentals.ex3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MoviesViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<MovieModel> mDataSource;
    OnMovieClickListener mMovieClickListener;

    public MoviesViewAdapter(Context context, ArrayList<MovieModel> items,
                             OnMovieClickListener onMovieClickListener) {
        mDataSource = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = this.mInflater.inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view, this.mMovieClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(this.mDataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mDataSource.size();
    }
}
