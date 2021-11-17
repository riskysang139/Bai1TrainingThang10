package com.example.bai1training.base;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClass {
    private static final String BASE_URL ="http://demo1329882.mockable.io/";

    private static final String BASE_URL_MOVIE_DB ="https://api.themoviedb.org/3/";

    private static Retrofit getInstance() {
        return new Retrofit.Builder().baseUrl(BASE_URL_MOVIE_DB)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

    }

    private static Retrofit getInstanceAdver() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static FilmApi getFilmApi(){
        return getInstance().create(FilmApi.class);
    }

    public static FilmApi getAdver(){
        return getInstanceAdver().create(FilmApi.class);
    }

}
