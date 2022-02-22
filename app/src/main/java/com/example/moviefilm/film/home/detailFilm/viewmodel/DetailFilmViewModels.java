package com.example.moviefilm.film.home.detailFilm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.home.detailFilm.models.CastResponse;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.home.detailFilm.models.VideoResponse;
import com.example.moviefilm.film.home.detailFilm.repo.DetailFilmRepo;
import com.example.moviefilm.film.models.ResultResponse;
import com.example.moviefilm.roomdb.Film;

import java.util.List;

import io.reactivex.Flowable;

public class DetailFilmViewModels extends AndroidViewModel {
    private MutableLiveData<DetailFilm> detailFilmLiveData;
    private MutableLiveData<VideoResponse> videoFilmLiveData;
    private MutableLiveData<ResultResponse> similarFilmLiveData;
    private MutableLiveData<ResultResponse> recommendFilmLiveData;
    private MutableLiveData<CastResponse> castResponseMutableLiveData;
    private DetailFilmRepo detailFilmRepo;

    public DetailFilmViewModels(@NonNull Application application) {
        super(application);
        detailFilmRepo = new DetailFilmRepo(application);
    }

    public MutableLiveData<DetailFilm> getDetailFilmLiveData() {
        if (detailFilmLiveData == null)
            return detailFilmLiveData = detailFilmRepo.getDetailFilmMutableLiveData();
        return detailFilmLiveData;
    }

    public MutableLiveData<VideoResponse> getVideoFilmLiveData() {
        if (videoFilmLiveData == null)
            return videoFilmLiveData = detailFilmRepo.getVideoTrailerMutableLiveData();
        return videoFilmLiveData;
    }

    public MutableLiveData<ResultResponse> getSimilarFilmLiveData() {
        if (similarFilmLiveData == null)
            return similarFilmLiveData = detailFilmRepo.getSimilarFilmMutableLiveData();
        return similarFilmLiveData;
    }

    public MutableLiveData<ResultResponse> getRecommendFilmLiveData() {
        if (recommendFilmLiveData == null)
            return recommendFilmLiveData = detailFilmRepo.getRecommendMutableLiveData();
        return recommendFilmLiveData;
    }

    public MutableLiveData<CastResponse> getCastResponseMutableLiveData() {
        if (castResponseMutableLiveData == null)
            return castResponseMutableLiveData = detailFilmRepo.getCastResponseMutableLiveData();
        return castResponseMutableLiveData;
    }

    //Get all Movie
    public Flowable<List<Film>> getAllMovies(){
        return detailFilmRepo.getAllFilm();
    }

    //Get Movie with buy
    public Flowable<Film> getMovies(String id, int isWantBuy){
        return detailFilmRepo.getFilm(id, isWantBuy);
    }

    //Get Movie with love
    public Flowable<Film> getMoviesLove(String id){
        return detailFilmRepo.getFilmLove(id);
    }

    //Insert Movie
    public void insertFilm(Film film){
        detailFilmRepo.insertFilm(film);
    }

    //Update Movie
    public void updateFilm(Film film){
        detailFilmRepo.updateMovie(film);
    }

    public void fetchDetailFilm(String id , String apiKey) {
        detailFilmRepo.fetchDetailFilm(id,apiKey);
    }

    public void fetchVideoTrailerFilm(String id , String apiKey) {
        detailFilmRepo.fetchVideoFilm(id,apiKey);
    }
    public void fetchSimilarFilm(String id , String apiKey) {
        detailFilmRepo.fetchSimilarFilm(id,apiKey);
    }
    public void fetchRecommendFilm(String id , String apiKey) {
        detailFilmRepo.fetchRecommendFilm(id,apiKey);
    }
    public void fetchCastFilm(String id , String apiKey) {
        detailFilmRepo.fetchCastFilm(id,apiKey);
    }
}
