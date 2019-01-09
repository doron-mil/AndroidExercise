package com.academy.fundamentals.ex3.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientManager {
    static String BASE_URL = "https://api.themoviedb.org";
    static String BASE_API_URL = BASE_URL + "/3/";

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder().
            baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    static public MoviesService moviesService = retrofit.create(MoviesService.class);


}
