package com.academy.fundamentals.ex3.rest;

import com.academy.fundamentals.ex3.model.MovieListResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesService {
    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
    String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";

    String POPULAR = "movie/popular";

    String apiKey = "718e13b7d522be3f4a51466b7a828bd1";
    String keyQuery= "?api_key=" + apiKey;

    String POPULAR_QUERY_PATH = POPULAR + keyQuery;

    @GET( MoviesService.POPULAR_QUERY_PATH )
    Call<MovieListResult> getPopularMovies();


}
