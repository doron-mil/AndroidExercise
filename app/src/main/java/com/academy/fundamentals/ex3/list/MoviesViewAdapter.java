package com.academy.fundamentals.ex3.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<MovieModel> mDataSource = new ArrayList<MovieModel>();
    OnMovieClickListener mMovieClickListener;

    public MoviesViewAdapter(Context context, ArrayList<MovieModel> items,
                             OnMovieClickListener onMovieClickListener) {
        mDataSource.addAll(items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mMovieClickListener = onMovieClickListener;
    }

    public void setData(List<MovieModel> items) {
        mDataSource.clear();
        mDataSource.addAll(items);
        notifyDataSetChanged();
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
