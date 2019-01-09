package com.academy.fundamentals.ex3.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.model.MovieModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final ImageView ivImage;
    public final TextView tvTitle;
    public final TextView tvOverview;
    private OnMovieClickListener mMovieClickListener;
    private Picasso picasso;

    public ViewHolder(@NonNull View view, OnMovieClickListener onMovieClickListener) {
        super(view);
        this.ivImage = view.findViewById(R.id.item_movie_iv);
        this.tvTitle = view.findViewById(R.id.item_movie_tv_title);
        this.tvOverview = view.findViewById(R.id.item_movie_tv_overview);
        this.mMovieClickListener = onMovieClickListener;

        view.setOnClickListener(this);

        picasso = Picasso.get();
    }

    public void bind(final MovieModel movieModel) {
        // this.ivImage.setImageResource(movieModel.getImageResourceId());

        // this.ivImage.setImageURI(Uri.parse(movieModel.getImageResourceUri()));
        picasso.load(movieModel.getImageResourceUri())
                .into(ivImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i("doron - onSuccess", "onSuccess");
                    }

                    @Override
                    public void onError(Exception e) {
                        ivImage.setImageResource(movieModel.getImageResourceId());
                        Log.d("doron - onError", "onError() called with: e = [" + e + "]");
                    }
                });

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
