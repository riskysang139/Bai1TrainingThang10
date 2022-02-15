package com.example.moviefilm.base;


import com.example.moviefilm.detailFilm.models.CastResponse;
import com.example.moviefilm.detailFilm.models.DetailFilm;
import com.example.moviefilm.detailFilm.models.VideoResponse;
import com.example.moviefilm.film.models.MovieAdver;
import com.example.moviefilm.film.models.ResultRespone;

import java.util.List;

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
    Observable<ResultRespone> getSimilarVideoTrailer(@Path("movie_id") String id,
                                                    @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/recommendations")
    Observable<ResultRespone> getRecommendVideoTrailer(@Path("movie_id") String id,
                                                 @Query("api_key") String apiKey);

    @GET("search/movie")
    Observable<ResultRespone> getSearchMovies(@Query("api_key") String apiKey,
                                        @Query("query") String query);

    @GET("Advertisement")
    Observable<List<MovieAdver>> getAdver();

    @GET("movie/{movie_id}/credits")
    Observable<CastResponse> getCastFilm(@Path("movie_id") String id,
                                         @Query("api_key") String apiKey);
}

