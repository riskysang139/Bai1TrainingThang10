package com.example.moviefilm.film.home.detailFilm.watchfilm.repo;

import android.app.Application;
import android.util.Log;

import com.example.moviefilm.roomdb.filmdb.Film;
import com.example.moviefilm.roomdb.filmdb.FilmDao;
import com.example.moviefilm.roomdb.filmdb.FilmDatabase;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class WatchFilmRepo {
    private FilmDao filmDao;
    private static final String TAG = "TAG";

    public WatchFilmRepo(Application application) {
        FilmDatabase filmDatabase = FilmDatabase.getInstance(application);
        filmDao = filmDatabase.filmDao();
    }


    //Get film with id
    public Flowable<Film> getFilmWithId(String id){
        return filmDao.getFilmWithId(id);
    }

    //Insert film
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
}
