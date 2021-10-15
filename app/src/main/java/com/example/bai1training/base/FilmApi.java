package com.example.bai1training.base;


import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.detailFilm.models.VideoResponse;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.models.Results;

import java.sql.ResultSet;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmApi {
    @GET("movie/popular")
    Observable<ResultRespone> getPopularFilmResponse(@Query("api_key") String apiKey,
                                                     @Query("page") int page);

    @GET("movie/top_rated")
    Observable<ResultRespone> getTopRatedFilmRespone(@Query("api_key") String apiKey,
                                                          @Query("page") int page);
    @GET("movie/upcoming")
    Observable<ResultRespone> getUpcomingFilmRespone(@Query("api_key") String apiKey,
                                                     @Query("page") int page);
    @GET("movie/now_playing")
    Observable<ResultRespone> getNowPlayingFilmRespone(@Query("api_key") String apiKey,
                                                     @Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<DetailFilm> getDetailMovies(@Path("movie_id") String id,
                                           @Query("api_key") String apiKey);
    @GET("movie/{movie_id}/videos")
    Observable<VideoResponse> getDetailVideoTrailer(@Path("movie_id") String id,
                                                    @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Observable<Results> getSimilarVideoTrailer(@Path("movie_id") String id,
                                                    @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/recommendations")
    Observable<Results> getRecommendVideoTrailer(@Path("movie_id") String id,
                                                 @Query("api_key") String apiKey);

}

