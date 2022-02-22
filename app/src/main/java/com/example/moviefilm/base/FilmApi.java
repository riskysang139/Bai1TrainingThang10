package com.example.moviefilm.base;


import com.example.moviefilm.film.home.detailFilm.models.CastResponse;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.home.detailFilm.models.VideoResponse;
import com.example.moviefilm.film.models.MovieAdver;
import com.example.moviefilm.film.models.ResultResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmApi {
    @GET("movie/popular")
    Observable<ResultResponse> getPopularFilmResponse(@Query("api_key") String apiKey,
                                                      @Query("page") int page);

    @GET("movie/top_rated")
    Observable<ResultResponse> getTopRatedFilmRespone(@Query("api_key") String apiKey,
                                                      @Query("page") int page);
    @GET("movie/upcoming")
    Observable<ResultResponse> getUpcomingFilmRespone(@Query("api_key") String apiKey,
                                                      @Query("page") int page);
    @GET("movie/now_playing")
    Observable<ResultResponse> getNowPlayingFilmRespone(@Query("api_key") String apiKey,
                                                        @Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<DetailFilm> getDetailMovies(@Path("movie_id") String id,
                                           @Query("api_key") String apiKey);
    @GET("movie/{movie_id}/videos")
    Observable<VideoResponse> getDetailVideoTrailer(@Path("movie_id") String id,
                                                    @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Observable<ResultResponse> getSimilarVideoTrailer(@Path("movie_id") String id,
                                                      @Query("api_key") String apiKey,
                                                      @Query("page") int page);

    @GET("movie/{movie_id}/recommendations")
    Observable<ResultResponse> getRecommendVideoTrailer(@Path("movie_id") String id,
                                                        @Query("api_key") String apiKey,
                                                        @Query("page") int page);

    @GET("search/movie")
    Observable<ResultResponse> getSearchMovies(@Query("api_key") String apiKey,
                                               @Query("query") String query);

    @GET("Advertisement")
    Observable<List<MovieAdver>> getAdver();

    @GET("movie/{movie_id}/credits")
    Observable<CastResponse> getCastFilm(@Path("movie_id") String id,
                                         @Query("api_key") String apiKey);
}

