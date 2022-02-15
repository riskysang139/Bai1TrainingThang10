package com.example.moviefilm.detailFilm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.detailFilm.models.CastResponse;
import com.example.moviefilm.detailFilm.models.DetailFilm;
import com.example.moviefilm.detailFilm.models.VideoResponse;
import com.example.moviefilm.detailFilm.repo.DetailFilmRepo;
import com.example.moviefilm.film.models.ResultRespone;
import com.example.moviefilm.roomdatabase.Film;

import java.util.List;

import io.reactivex.Flowable;

public class DetailFilmViewModels extends AndroidViewModel {
    private MutableLiveData<DetailFilm> detailFilmLiveData;
    private MutableLiveData<VideoResponse> videoFilmLiveData;
    private MutableLiveData<ResultRespone> similarFilmLiveData;
    private MutableLiveData<ResultRespone> recommendFilmLiveData;
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

    public MutableLiveData<ResultRespone> getSimilarFilmLiveData() {
        if (similarFilmLiveData == null)
            return similarFilmLiveData = detailFilmRepo.getSimilarFilmMutableLiveData();
        return similarFilmLiveData;
    }

    public MutableLiveData<ResultRespone> getRecommendFilmLiveData() {
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

    //Get Movie
    public Flowable<Film> getMovies(String id){
        return detailFilmRepo.getFilm(id);
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
