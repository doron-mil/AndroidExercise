package com.academy.fundamentals.ex3.details;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.db.AppDatabase;
import com.academy.fundamentals.ex3.download.DownloadActivity;
import com.academy.fundamentals.ex3.model.MovieModel;
import com.academy.fundamentals.ex3.model.MovieModelConverter;
import com.academy.fundamentals.ex3.model.VideoModel;
import com.academy.fundamentals.ex3.model.VideoResult;
import com.academy.fundamentals.ex3.model.VideosListResult;
import com.academy.fundamentals.ex3.rest.MoviesService;
import com.academy.fundamentals.ex3.rest.RestClientManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MovieDetailsFragment";
    private static final String ARG_MOVIE = "MovieModel-data";
    private ImageView ivImage;
    private ImageView ivBackImage;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private MovieModel movieModel;
    private Button btnTrailer;
    private ImageButton btnDownload;
    private Picasso picasso;
    private boolean isTrue;

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

        this.btnTrailer = view.findViewById(R.id.detailsPlayTrailer);
        this.btnTrailer.setOnClickListener(this);

        this.btnDownload = view.findViewById(R.id.imageButton_download);
        this.btnDownload.setOnClickListener(this);


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

    @Override
    public void onClick(View view) {
        Log.d("doron - clickButton", "pressed");

        if (movieModel == null) return;

        switch (view.getId()) {
            case R.id.detailsPlayTrailer:
                this.viewTrailer();
                break;

            case R.id.imageButton_download:
                this.downloadImage();
                break;
        }

    }

    private void downloadImage() {
        Log.d("doron - download", "download");
        Context context = getContext();
        if (movieModel == null || context == null) return;
        DownloadActivity.startActivity(context, this.movieModel);
    }

    private void viewTrailer() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        final Context context = activity.getApplicationContext();
        if (context == null) {
            return;
        }

        final VideoModel videoModel = AppDatabase.getInstance(context).videoDao().getVideo(movieModel.getMovieId());
        if (videoModel != null) {
            playTrailer(videoModel.getKey());
            Log.d("doron - play trailer", "fromDB");
            return;
        }

        this.setButtonLoadingStatus();
        isTrue = !isTrue;
        // if (isTrue) {
        //     this.setButtonLoadingStatus();
        // }else {
        //     this.resetButtonStatus();
        // }

        Log.d("doron - play trailer", "from Internet");

        MoviesService moviesService = RestClientManager.moviesService;
        moviesService.getVideos(movieModel.getMovieId())
                .enqueue(new retrofit2.Callback<VideosListResult>() {

                    @Override
                    public void onResponse(Call<VideosListResult> call, Response<VideosListResult> response) {
                        VideosListResult body = response.body();
                        if (body != null) {
                            List<VideoResult> results = body.getResults();
                            if (results != null && !results.isEmpty()) {
                                VideoModel convertedVideoModel = MovieModelConverter.convertVideoResult(body);
                                if (convertedVideoModel != null) {
                                    AppDatabase.getInstance(context).videoDao().insert(convertedVideoModel);
                                    String key = convertedVideoModel.getKey();
                                    playTrailer(key);
                                }
                            }
                        }
                        resetButtonStatus();
                    }

                    @Override
                    public void onFailure(Call<VideosListResult> call, Throwable t) {
                        Toast.makeText(getContext(), R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
                        resetButtonStatus();
                    }
                });

    }

    private void playTrailer(String key) {
        String trailerUrl = MoviesService.YOUTUBE_BASE_URL + key;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(browserIntent);
    }

    private void setButtonLoadingStatus() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        RotateDrawable rotateDrawable = (RotateDrawable) ContextCompat.getDrawable(context, R.drawable.progress_animation);
        ObjectAnimator anim = ObjectAnimator.ofInt(rotateDrawable, "level", 0, 10000);
        anim.setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
        btnTrailer.setText(R.string.details_loading_trailer_text);
        btnTrailer.setCompoundDrawablesWithIntrinsicBounds(rotateDrawable, null, null, null);
    }

    private void resetButtonStatus() {
        btnTrailer.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        btnTrailer.setText(R.string.details_trailer_text);
    }
}

