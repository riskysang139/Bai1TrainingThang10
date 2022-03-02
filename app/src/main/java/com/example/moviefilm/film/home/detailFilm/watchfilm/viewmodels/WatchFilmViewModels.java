package com.example.moviefilm.film.home.detailFilm.watchfilm.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.moviefilm.film.home.detailFilm.watchfilm.repo.WatchFilmRepo;
import com.example.moviefilm.roomdb.filmdb.Film;

import io.reactivex.Flowable;

public class WatchFilmViewModels extends AndroidViewModel {

    private WatchFilmRepo watchFilmRepo;
    public WatchFilmViewModels(@NonNull Application application) {
        super(application);
        watchFilmRepo = new WatchFilmRepo(application);
    }


    //Get Movie with id
    public Flowable<Film> getMovieWithId(String id){
        return watchFilmRepo.getFilmWithId(id);
    }

    //Insert Movie
    public void insertFilm(Film film){
        watchFilmRepo.insertFilm(film);
    }

    //Update Movie
    public void updateFilm(Film film){
        watchFilmRepo.updateMovie(film);
    }

}
