package com.example.moviefilm.base;


import com.example.moviefilm.film.detailFilm.models.CastResponse;
import com.example.moviefilm.film.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.detailFilm.models.VideoResponse;
import com.example.moviefilm.film.models.MovieAdverb;
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
    Observable<ResultResponse> getTopRatedFilmResponse(@Query("api_key") String apiKey,
                                                       @Query("page") int page);
    @GET("movie/upcoming")
    Observable<ResultResponse> getUpcomingFilmResponse(@Query("api_key") String apiKey,
                                                       @Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<DetailFilm> getDetailMovies(@Path("movie_id") String id,
                                           @Query("api_key") String apiKey);
    @GET("movie/{movie_id}/videos")
    Observable<VideoResponse> getDetailVideoTrailer(@Path("movie_id") String id,
                                                    @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Observable<ResultResponse> getSimilarFilm(@Path("movie_id") String id,
                                                      @Query("api_key") String apiKey,
                                                      @Query("page") int page);

    @GET("movie/{movie_id}/recommendations")
    Observable<ResultResponse> getRecommendFilm(@Path("movie_id") String id,
                                                        @Query("api_key") String apiKey,
                                                        @Query("page") int page);

    @GET("search/movie")
    Observable<ResultResponse> getSearchMovies(@Query("api_key") String apiKey,
                                               @Query("query") String query);

    @GET("Advertisement")
    Observable<List<MovieAdverb>> getAdverb();

    @GET("movie/{movie_id}/credits")
    Observable<CastResponse> getCastFilm(@Path("movie_id") String id,
                                         @Query("api_key") String apiKey);
}

