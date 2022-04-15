package com.example.moviefilm.film.watchfilmlocal.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.watchfilmlocal.repo.WatchFilmLocalRepo;

public class WatchFilmLocalViewModels extends AndroidViewModel {
    private WatchFilmLocalRepo watchFilmLocalRepo;
    private MutableLiveData<DetailFilm> detailFilmLiveData;

    public WatchFilmLocalViewModels(@NonNull Application application) {
        super(application);
        watchFilmLocalRepo = new WatchFilmLocalRepo(application);
    }

    public MutableLiveData<DetailFilm> getDetailFilmLiveData() {
        if (detailFilmLiveData == null)
            return detailFilmLiveData = watchFilmLocalRepo.getDetailFilmMutableLiveData();
        return detailFilmLiveData;
    }

    public void fetchDetailFilm(String id, String apiKey) {
        watchFilmLocalRepo.fetchDetailFilm(id, apiKey);
    }
}
