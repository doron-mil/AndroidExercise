package com.academy.fundamentals.ex3.rest;

import com.academy.fundamentals.ex3.model.MovieListResult;
import com.academy.fundamentals.ex3.model.VideosListResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesService {
    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
    String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";
    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    String POPULAR = "movie/popular";

    String apiKey = "718e13b7d522be3f4a51466b7a828bd1";
    String keyQuery= "?api_key=" + apiKey;

    String POPULAR_QUERY_PATH = POPULAR + keyQuery;

    String MOVIE_ID_KEY = "movie_id";
    String VIDEOS = "movie/{" + MOVIE_ID_KEY + "}/videos";

    String VIDEOS_QUERY_PATH = VIDEOS + keyQuery;

    @GET( MoviesService.POPULAR_QUERY_PATH )
    Call<MovieListResult> getPopularMovies();

    @GET( MoviesService.VIDEOS_QUERY_PATH )
    Call<VideosListResult> getVideos(@Path(MoviesService.MOVIE_ID_KEY) int movieId);

}
