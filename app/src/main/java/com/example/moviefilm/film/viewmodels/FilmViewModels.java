package com.example.moviefilm.film.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.models.MovieAdverb;
import com.example.moviefilm.film.models.ResultResponse;
import com.example.moviefilm.film.repo.FilmRepository;

import java.util.List;

public class FilmViewModels extends AndroidViewModel {
    private MutableLiveData<ResultResponse> mNowPlayingMutableLiveData;

    private MutableLiveData<ResultResponse> mPopularMutableLiveData;
    private MutableLiveData<ResultResponse> mTopRateMutableLiveData;
    private MutableLiveData<ResultResponse> mUpcomingMutableLiveData;
    private MutableLiveData<List<MovieAdverb>> movieAdverMutableLiveData;

    private FilmRepository filmRepository;

    public FilmViewModels(Application application) {
        super(application);
        filmRepository = new FilmRepository(application);
    }

    public MutableLiveData<ResultResponse> getPopularMutableLiveData() {
        if (mPopularMutableLiveData == null)
            return mPopularMutableLiveData = filmRepository.getPopularLiveData();
        return mPopularMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getTopRateMutableLiveData() {
        if (mTopRateMutableLiveData == null)
            return mTopRateMutableLiveData = filmRepository.getTopRateLiveData();
        return mTopRateMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getUpcomingMutableLiveData() {
        if (mUpcomingMutableLiveData == null)
            return mUpcomingMutableLiveData = filmRepository.getUpcomingLiveData();
        return mUpcomingMutableLiveData;
    }

    public MutableLiveData<List<MovieAdverb>> getMovieAdverbMutableLiveData() {
        if(movieAdverMutableLiveData==null)
            return movieAdverMutableLiveData = filmRepository.gemMovieAdverbLiveData();
        return movieAdverMutableLiveData;
    }

    public void fetchPopularMovies(String apiKey , int page) {
        filmRepository.fetchPopularMovies(apiKey,page);
    }

    public void fetchTopRateMovies(String apiKey ,int page) {
        filmRepository.fetchTopRateMovies(apiKey,page);
    }

    public void fetchUpcomingMovies(String apiKey ,int page) { filmRepository.fetchUpcomingMovies(apiKey,page); }

    public void fetchMovieAdverb() { filmRepository.fetchMovieAdverb(); }
}
