package com.example.bai1training.allfilm;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bai1training.base.FilmApi;
import com.example.bai1training.base.RetroClass;
import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.detailFilm.models.VideoResponse;
import com.example.bai1training.film.models.ResultRespone;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AllFilmRepo {

    private MutableLiveData<ResultRespone> mNowPlayingLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> mPopularLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> mTopRateLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> mUpcomingLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> similarFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultRespone> recommendMutableLiveData = new MutableLiveData<>();

    public AllFilmRepo(Application application) {
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

    public MutableLiveData<ResultRespone> getmNowPlayingLiveData() {
        return mNowPlayingLiveData;
    }

    public void setmNowPlayingLiveData(MutableLiveData<ResultRespone> mNowPlayingLiveData) {
        this.mNowPlayingLiveData = mNowPlayingLiveData;
    }

    public MutableLiveData<ResultRespone> getmPopularLiveData() {
        return mPopularLiveData;
    }

    public void setmPopularLiveData(MutableLiveData<ResultRespone> mPopularLiveData) {
        this.mPopularLiveData = mPopularLiveData;
    }

    public MutableLiveData<ResultRespone> getmTopRateLiveData() {
        return mTopRateLiveData;
    }

    public void setmTopRateLiveData(MutableLiveData<ResultRespone> mTopRateLiveData) {
        this.mTopRateLiveData = mTopRateLiveData;
    }

    public MutableLiveData<ResultRespone> getmUpcomingLiveData() {
        return mUpcomingLiveData;
    }

    public void setmUpcomingLiveData(MutableLiveData<ResultRespone> mUpcomingLiveData) {
        this.mUpcomingLiveData = mUpcomingLiveData;
    }
    public MutableLiveData<ResultRespone> getSimilarFilmMutableLiveData() {
        return similarFilmMutableLiveData;
    }

    public MutableLiveData<ResultRespone> getRecommendMutableLiveData() {
        return recommendMutableLiveData;
    }
}
