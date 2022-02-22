package com.example.moviefilm.film.home.allfilm.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.home.allfilm.repo.AllFilmRepo;
import com.example.moviefilm.film.models.MovieAdver;
import com.example.moviefilm.film.models.ResultResponse;

import java.util.List;

public class AllFilmViewModels extends AndroidViewModel {
    private MutableLiveData<ResultResponse> mNowPlayingMutableLiveData;

    private MutableLiveData<ResultResponse> mPopularMutableLiveData;
    private MutableLiveData<ResultResponse> mTopRateMutableLiveData;
    private MutableLiveData<ResultResponse> mUpcomingMutableLiveData;
    private MutableLiveData<List<MovieAdver>> movieAdverMutableLiveData;
    private MutableLiveData<ResultResponse> similarFilmLiveData;
    private MutableLiveData<ResultResponse> recommendFilmLiveData;

    private AllFilmRepo filmRepository;

    public AllFilmViewModels(Application application) {
        super(application);
        filmRepository = new AllFilmRepo(application);
    }

    public MutableLiveData<ResultResponse> getmNowPlayingMutableLiveData() {
        if (mNowPlayingMutableLiveData == null)
            return mNowPlayingMutableLiveData = filmRepository.getmNowPlayingLiveData();
        return mNowPlayingMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getmPopularMutableLiveData() {
        if (mPopularMutableLiveData == null)
            return mPopularMutableLiveData = filmRepository.getmPopularLiveData();
        return mPopularMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getmTopRateMutableLiveData() {
        if (mTopRateMutableLiveData == null)
            return mTopRateMutableLiveData = filmRepository.getmTopRateLiveData();
        return mTopRateMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getmUpcomingMutableLiveData() {
        if (mUpcomingMutableLiveData == null)
            return mUpcomingMutableLiveData = filmRepository.getmUpcomingLiveData();
        return mUpcomingMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getSimilarFilmLiveData() {
        if (similarFilmLiveData == null)
            return similarFilmLiveData = filmRepository.getSimilarFilmMutableLiveData();
        return similarFilmLiveData;
    }

    public MutableLiveData<ResultResponse> getRecommendFilmLiveData() {
        if (recommendFilmLiveData == null)
            return recommendFilmLiveData = filmRepository.getRecommendMutableLiveData();
        return recommendFilmLiveData;
    }

    public void fetchPopularMovies(String apiKey , int page) {
        filmRepository.fetchPopularMovies(apiKey,page);
    }

    public void fetchTopRateMovies(String apiKey ,int page) {
        filmRepository.fetchTopRateMovies(apiKey,page);
    }

    public void fetchNowPlayingMovies(String apiKey ,int page) {
        filmRepository.fetchNowPalyingMovies(apiKey,page);
    }

    public void fetchSimilarFilm(String id , String apiKey, int page) {
        filmRepository.fetchSimilarFilm(id,apiKey, page);
    }
    public void fetchRecommendFilm(String id , String apiKey, int page) {
        filmRepository.fetchRecommendFilm(id,apiKey, page);
    }

    public void fetchUpcomingMovies(String apiKey ,int page) { filmRepository.fetchUpcomingMovies(apiKey,page); }

}
