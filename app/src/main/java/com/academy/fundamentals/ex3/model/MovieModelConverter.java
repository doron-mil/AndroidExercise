package com.academy.fundamentals.ex3.model;


import com.academy.fundamentals.ex3.R;
import com.academy.fundamentals.ex3.rest.MoviesService;

import java.util.ArrayList;
import java.util.List;

public class MovieModelConverter {

    public static ArrayList<MovieModel> convertResult(MovieListResult movieListResult) {

        ArrayList<MovieModel> result = new ArrayList<>();
        for (MovieResult movieResult : movieListResult.getResults()) {
            MovieModel movieModel = new MovieModel(movieResult.getId(), movieResult.getTitle(), movieResult.getOverview(),
                    movieResult.getReleaseDate(), MoviesService.POSTER_BASE_URL + movieResult.getPosterPath(),
                    MoviesService.BACKDROP_BASE_URL + movieResult.getBackdropPath(),
                    "" , movieResult.getPopularity()
            );
            movieModel.setImageResourceId(R.drawable.the_meg);
            movieModel.setBackImageResourceId(R.drawable.the_meg_back);
            result.add(movieModel);
        }

        return result;
    }

    public static VideoModel convertVideoResult(VideosListResult videosListResult) {
        List<VideoResult> results = videosListResult.getResults();
        if (results != null && !results.isEmpty()) {
            VideoResult videoResult = results.get(0);
            return new VideoModel(videosListResult.getId(), videoResult.getId(), videoResult.getKey());
        }
        return null;
    }

}
