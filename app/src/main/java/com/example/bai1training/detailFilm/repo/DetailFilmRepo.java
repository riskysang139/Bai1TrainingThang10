package com.example.bai1training.detailFilm.repo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.bai1training.base.FilmApi;
import com.example.bai1training.base.RetroClass;
import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.detailFilm.models.VideoResponse;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.models.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;

public class DetailFilmRepo {

    private MutableLiveData<DetailFilm> detailFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<VideoResponse> videoTrailerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> similarFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> recommendMutableLiveData = new MutableLiveData<>();


    public DetailFilmRepo(Application application) {
    }

    public MutableLiveData<DetailFilm> getDetailFilmMutableLiveData() {
        return detailFilmMutableLiveData;
    }

    public MutableLiveData<VideoResponse> getVideoTrailerMutableLiveData() {
        return videoTrailerMutableLiveData;
    }

    public MutableLiveData<ResultRespone> getSimilarFilmMutableLiveData() {
        return similarFilmMutableLiveData;
    }

    public MutableLiveData<ResultRespone> getRecommendMutableLiveData() {
        return recommendMutableLiveData;
    }

    public void fetchDetailFilm(String id, String apiKey) {
        FilmApi detailFilm = RetroClass.getFilmApi();
        Observable<DetailFilm> detailFilmObservable = detailFilm.getDetailMovies(id, apiKey).subscribeOn(Schedulers.io());
        detailFilmObservable.subscribe(new Observer<DetailFilm>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull DetailFilm detailFilm) {
                detailFilmMutableLiveData.postValue(detailFilm);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void fetchVideoFilm(String id, String apiKey) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<VideoResponse> videoResponseObservable = videoFilm.getDetailVideoTrailer(id, apiKey).subscribeOn(Schedulers.io());
        videoResponseObservable.subscribe(new Observer<VideoResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull VideoResponse videoResponse) {
                videoTrailerMutableLiveData.postValue(videoResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }
            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchSimilarFilm(String id, String apiKey) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<ResultRespone> resultsObservable = videoFilm.getSimilarVideoTrailer(id, apiKey).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<ResultRespone>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultRespone resultRespone) {
                similarFilmMutableLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }
            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchRecommendFilm(String id, String apiKey) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<ResultRespone> resultsObservable = videoFilm.getRecommendVideoTrailer(id, apiKey).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<ResultRespone>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultRespone resultRespone) {
                recommendMutableLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }
            @Override
            public void onComplete() {

            }
        });
    }
}
