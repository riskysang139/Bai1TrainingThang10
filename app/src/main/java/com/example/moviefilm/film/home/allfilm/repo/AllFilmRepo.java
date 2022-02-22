package com.example.moviefilm.film.home.allfilm.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.base.FilmApi;
import com.example.moviefilm.base.RetroClass;
import com.example.moviefilm.film.models.ResultResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AllFilmRepo {

    private MutableLiveData<ResultResponse> mNowPlayingLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> mPopularLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> mTopRateLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> mUpcomingLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> similarFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> recommendMutableLiveData = new MutableLiveData<>();

    public AllFilmRepo(Application application) {
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

    public void fetchSimilarFilm(String id, String apiKey, int page) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<ResultResponse> resultsObservable = videoFilm.getSimilarVideoTrailer(id, apiKey, page).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                similarFilmMutableLiveData.postValue(resultResponse);
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

    public void fetchRecommendFilm(String id, String apiKey, int page) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<ResultResponse> resultsObservable = videoFilm.getRecommendVideoTrailer(id, apiKey, page).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                recommendMutableLiveData.postValue(resultResponse);
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

    public MutableLiveData<ResultResponse> getmNowPlayingLiveData() {
        return mNowPlayingLiveData;
    }

    public void setmNowPlayingLiveData(MutableLiveData<ResultResponse> mNowPlayingLiveData) {
        this.mNowPlayingLiveData = mNowPlayingLiveData;
    }

    public MutableLiveData<ResultResponse> getmPopularLiveData() {
        return mPopularLiveData;
    }

    public void setmPopularLiveData(MutableLiveData<ResultResponse> mPopularLiveData) {
        this.mPopularLiveData = mPopularLiveData;
    }

    public MutableLiveData<ResultResponse> getmTopRateLiveData() {
        return mTopRateLiveData;
    }

    public void setmTopRateLiveData(MutableLiveData<ResultResponse> mTopRateLiveData) {
        this.mTopRateLiveData = mTopRateLiveData;
    }

    public MutableLiveData<ResultResponse> getmUpcomingLiveData() {
        return mUpcomingLiveData;
    }

    public void setmUpcomingLiveData(MutableLiveData<ResultResponse> mUpcomingLiveData) {
        this.mUpcomingLiveData = mUpcomingLiveData;
    }
    public MutableLiveData<ResultResponse> getSimilarFilmMutableLiveData() {
        return similarFilmMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getRecommendMutableLiveData() {
        return recommendMutableLiveData;
    }
}
