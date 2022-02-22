package com.example.moviefilm.film.home.detailFilm.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.base.FilmApi;
import com.example.moviefilm.base.RetroClass;
import com.example.moviefilm.film.home.detailFilm.models.CastResponse;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.home.detailFilm.models.VideoResponse;
import com.example.moviefilm.film.models.ResultResponse;
import com.example.moviefilm.roomdb.Film;
import com.example.moviefilm.roomdb.FilmDao;
import com.example.moviefilm.roomdb.FilmDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class DetailFilmRepo {

    private static final String TAG = "TAG";
    private MutableLiveData<DetailFilm> detailFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<VideoResponse> videoTrailerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> similarFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> recommendMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CastResponse> castResponseMutableLiveData = new MutableLiveData<>();
    private FilmDao filmDao;
    private Flowable<List<Film>> allFilm;

    public DetailFilmRepo(Application application) {
        FilmDatabase filmDatabase = FilmDatabase.getInstance(application);
        filmDao = filmDatabase.filmDao();
    }

    //Get all film
    public Flowable<List<Film>> getAllFilm(){
        return filmDao.getAll();
    }

    //Get film
    public Flowable<Film> getFilm(String id, int isWantBuy){
        return filmDao.getFilm(id, isWantBuy);
    }

    //Get film love
    public Flowable<Film> getFilmLove(String id){
        return filmDao.getFilmLove(id);
    }
    //insert film love
    public void insertFilm (final Film film) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                filmDao.insertFilm(film);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Update Movie
    public void updateMovie(final Film film){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                filmDao.updateFilm(film);
            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    public MutableLiveData<DetailFilm> getDetailFilmMutableLiveData() {
        return detailFilmMutableLiveData;
    }

    public MutableLiveData<VideoResponse> getVideoTrailerMutableLiveData() {
        return videoTrailerMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getSimilarFilmMutableLiveData() {
        return similarFilmMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getRecommendMutableLiveData() {
        return recommendMutableLiveData;
    }

    public MutableLiveData<CastResponse> getCastResponseMutableLiveData() {
        return castResponseMutableLiveData;
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
        Observable<ResultResponse> resultsObservable = videoFilm.getSimilarVideoTrailer(id, apiKey, 1).subscribeOn(Schedulers.io());
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

    public void fetchRecommendFilm(String id, String apiKey) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<ResultResponse> resultsObservable = videoFilm.getRecommendVideoTrailer(id, apiKey, 1).subscribeOn(Schedulers.io());
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

    public void fetchCastFilm(String id, String apiKey) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<CastResponse> resultsObservable = filmApi.getCastFilm(id, apiKey).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<CastResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull CastResponse resultRespone) {
                castResponseMutableLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("cast response", e.toString());
            }
            @Override
            public void onComplete() {

            }
        });
    }
}
