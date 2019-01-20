package com.academy.fundamentals.ex3.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.model.MovieModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment {

    private static final String TAG = "MovieDetailsFragment";
    private static final String ARG_MOVIE = "MovieModel-data";
    private ImageView ivImage;
    private ImageView ivBackImage;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private MovieModel movieModel;
    private Picasso picasso;

    public static MovieDetailsFragment newInstance(MovieModel movieModel) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.picasso = Picasso.get();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movieModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.movieModel = getArguments().getParcelable(MovieDetailsFragment.ARG_MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.ivImage = view.findViewById(R.id.image_view);
        this.ivBackImage = view.findViewById(R.id.secondary_view);
        this.tvTitle = view.findViewById(R.id.detailsTitle);
        this.tvOverview = view.findViewById(R.id.detailsDescription);


        this.setMovie();
    }

    private void setMovie() {
        if (movieModel == null) return;

        // ivBackImage.setImageResource(movieModel.getImageResourceId());
        // ivImage.setImageResource(movieModel.getBackImageResourceId());

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

        picasso.load(movieModel.getBackImageResourceUri())
                .into(ivBackImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i("doron - onSuccess", "onSuccess");
                    }

                    @Override
                    public void onError(Exception e) {
                        ivBackImage.setImageResource(movieModel.getImageResourceId());
                        Log.d("doron - onError", "onError() called with: e = [" + e + "]");
                    }
                });


        tvTitle.setText(movieModel.getName());
//        tvReleaseDate.setText(movieModel.getReleaseDate());
        tvOverview.setText(movieModel.getOverview());
    }
}
