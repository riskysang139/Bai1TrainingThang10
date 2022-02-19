package com.example.moviefilm.film.cart.repo;

import android.app.Application;
import android.util.Log;

import com.example.moviefilm.roomdb.Film;
import com.example.moviefilm.roomdb.FilmDao;
import com.example.moviefilm.roomdb.FilmDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class CartRepository {
    private static final String TAG = "TAG";
    private FilmDao filmDao;
    private Flowable<List<Film>> allFilm;

    public CartRepository(Application application) {
        FilmDatabase filmDatabase = FilmDatabase.getInstance(application);
        filmDao = filmDatabase.filmDao();
    }

    //Get all film
    public Flowable<List<Film>> getAllFilm(){
        return filmDao.getAll();
    }

    //Update Movie
    public void updateCartMovie(final Film film){
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
}
