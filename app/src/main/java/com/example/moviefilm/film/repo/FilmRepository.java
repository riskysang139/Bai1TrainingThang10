package com.example.moviefilm.film.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.base.FilmApi;
import com.example.moviefilm.base.RetroClass;
import com.example.moviefilm.film.models.MovieAdver;
import com.example.moviefilm.film.models.ResultResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FilmRepository {
    private MutableLiveData<ResultResponse> mNowPlayingLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> mPopularLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> mTopRateLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> mUpcomingLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MovieAdver>> mMovieAdverLiveData = new MutableLiveData<>();

    public FilmRepository(Application application) {

    }

    public void fetchPopularMovies(String apiKey, int page) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<ResultResponse> observableFieldPopularMovies = filmApi.getPopularFilmResponse(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                mPopularLiveData.postValue(resultResponse);
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
        Observable<ResultResponse> observableFieldPopularMovies = filmApi.getTopRatedFilmRespone(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                mTopRateLiveData.postValue(resultResponse);
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
        Observable<ResultResponse> observableFieldPopularMovies = filmApi.getUpcomingFilmRespone(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                mUpcomingLiveData.postValue(resultResponse);
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
        Observable<ResultResponse> observableFieldPopularMovies = filmApi.getNowPlayingFilmRespone(apiKey, page)
                .subscribeOn(Schedulers.io());
        observableFieldPopularMovies.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                mNowPlayingLiveData.postValue(resultResponse);
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

    public MutableLiveData<ResultResponse> getmNowPlayingLiveData() {
        return mNowPlayingLiveData;
    }

    public MutableLiveData<ResultResponse> getmPopularLiveData() {
        return mPopularLiveData;
    }

    public MutableLiveData<ResultResponse> getmTopRateLiveData() {
        return mTopRateLiveData;
    }

    public MutableLiveData<ResultResponse> getmUpcomingLiveData() {
        return mUpcomingLiveData;
    }

    public MutableLiveData<List<MovieAdver>> getmMovieAdverLiveData() {
        return mMovieAdverLiveData;
    }
}
