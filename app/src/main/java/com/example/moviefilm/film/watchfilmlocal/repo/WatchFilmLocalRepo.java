package com.example.moviefilm.film.watchfilmlocal.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.base.FilmApi;
import com.example.moviefilm.base.RetroClass;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.roomdb.cartdb.CartDatabase;
import com.example.moviefilm.roomdb.filmdb.FilmDatabase;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchFilmLocalRepo {
    private MutableLiveData<DetailFilm> detailFilmMutableLiveData = new MutableLiveData<>();

    public WatchFilmLocalRepo(Application application) {

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

    public MutableLiveData<DetailFilm> getDetailFilmMutableLiveData() {
        return detailFilmMutableLiveData;
    }
}
