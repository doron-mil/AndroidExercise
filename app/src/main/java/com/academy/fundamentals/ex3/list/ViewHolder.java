package com.academy.fundamentals.ex3.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.model.MovieModel;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final ImageView ivImage;
    public final TextView tvTitle;
    public final TextView tvOverview;
    private OnMovieClickListener mMovieClickListener;

    public ViewHolder(@NonNull View view, OnMovieClickListener onMovieClickListener) {
        super(view);
        this.ivImage = view.findViewById(R.id.item_movie_iv);
        this.tvTitle = view.findViewById(R.id.item_movie_tv_title);
        this.tvOverview = view.findViewById(R.id.item_movie_tv_overview);
        this.mMovieClickListener = onMovieClickListener;

        view.setOnClickListener(this);
    }

    public void bind(MovieModel movieModel) {
        this.ivImage.setImageResource(movieModel.getImageResourceId());
        this.tvTitle.setText(movieModel.getName());
        this.tvOverview.setText(movieModel.getOverview());
    }

    @Override
    public void onClick(View v) {
        this.mMovieClickListener.onMovieClicked(super.getAdapterPosition());

        Log.v("MyActivity :xxxxxxxxx11x", this.tvTitle.getText().toString() +
                ':' + super.getAdapterPosition());
    }
}
