package com.example.bai1training.allfilm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bai1training.film.models.MovieAdver;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.repo.FilmRepository;

import java.util.List;

public class AllFilmViewModels extends AndroidViewModel {
    private MutableLiveData<ResultRespone> mNowPlayingMutableLiveData;

    private MutableLiveData<ResultRespone> mPopularMutableLiveData;
    private MutableLiveData<ResultRespone> mTopRateMutableLiveData;
    private MutableLiveData<ResultRespone> mUpcomingMutableLiveData;
    private MutableLiveData<List<MovieAdver>> movieAdverMutableLiveData;

    private AllFilmRepo filmRepository;

    public AllFilmViewModels(Application application) {
        super(application);
        filmRepository = new AllFilmRepo(application);
    }

    public MutableLiveData<ResultRespone> getmNowPlayingMutableLiveData() {
        if (mNowPlayingMutableLiveData == null)
            return mNowPlayingMutableLiveData = filmRepository.getmNowPlayingLiveData();
        return mNowPlayingMutableLiveData;
    }

    public MutableLiveData<ResultRespone> getmPopularMutableLiveData() {
        if (mPopularMutableLiveData == null)
            return mPopularMutableLiveData = filmRepository.getmPopularLiveData();
        return mPopularMutableLiveData;
    }

    public MutableLiveData<ResultRespone> getmTopRateMutableLiveData() {
        if (mTopRateMutableLiveData == null)
            return mTopRateMutableLiveData = filmRepository.getmTopRateLiveData();
        return mTopRateMutableLiveData;
    }

    public MutableLiveData<ResultRespone> getmUpcomingMutableLiveData() {
        if (mUpcomingMutableLiveData == null)
            return mUpcomingMutableLiveData = filmRepository.getmUpcomingLiveData();
        return mUpcomingMutableLiveData;
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

    public void fetchUpcomingMovies(String apiKey ,int page) { filmRepository.fetchUpcomingMovies(apiKey,page); }

}
