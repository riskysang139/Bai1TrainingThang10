package com.example.bai1training.film.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bai1training.base.FilmApi;
import com.example.bai1training.base.RetroClass;
import com.example.bai1training.film.models.MovieAdver;
import com.example.bai1training.film.models.ResultRespone;

import java.util.List;
import java.util.ListIterator;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FilmRepository {
    private MutableLiveData<ResultRespone> mNowPlayingLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> mPopularLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> mTopRateLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> mUpcomingLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieAdver>> mMovieAdverLiveData = new MutableLiveData<>();

    public FilmRepository(Application application) {

    }

    public void fetchPopularMovies(String apiKey, int page) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<ResultRespone> observableFieldPopularMovies = filmApi.getPopularFilmResponse(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultRespone>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultRespone resultRespone) {
                mPopularLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void fetchTopRateMovies(String apiKey, int page) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<ResultRespone> observableFieldPopularMovies = filmApi.getTopRatedFilmRespone(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultRespone>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultRespone resultRespone) {
                mTopRateLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchUpcomingMovies(String apiKey, int page) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<ResultRespone> observableFieldPopularMovies = filmApi.getUpcomingFilmRespone(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultRespone>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultRespone resultRespone) {
                mUpcomingLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchNowPalyingMovies(String apiKey, int page) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<ResultRespone> observableFieldPopularMovies = filmApi.getNowPlayingFilmRespone(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultRespone>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultRespone resultRespone) {
                mNowPlayingLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void fetchMovieAdver() {
        FilmApi filmApi = RetroClass.getAdver();
        Observable<List<MovieAdver>> movieAdverObservable = filmApi.getAdver().subscribeOn(Schedulers.io());
        movieAdverObservable.subscribe(new Observer<List<MovieAdver>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<MovieAdver> movieAdvers) {
                mMovieAdverLiveData.postValue(movieAdvers);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public MutableLiveData<ResultRespone> getmNowPlayingLiveData() {
        return mNowPlayingLiveData;
    }

    public MutableLiveData<ResultRespone> getmPopularLiveData() {
        return mPopularLiveData;
    }

    public MutableLiveData<ResultRespone> getmTopRateLiveData() {
        return mTopRateLiveData;
    }

    public MutableLiveData<ResultRespone> getmUpcomingLiveData() {
        return mUpcomingLiveData;
    }

    public MutableLiveData<List<MovieAdver>> getmMovieAdverLiveData() {
        return mMovieAdverLiveData;
    }
}
