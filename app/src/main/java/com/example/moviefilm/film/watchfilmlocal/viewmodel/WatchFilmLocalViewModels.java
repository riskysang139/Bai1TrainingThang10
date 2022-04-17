package com.example.moviefilm.film.watchfilmlocal.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.watchfilmlocal.model.MediaFile;
import com.example.moviefilm.film.watchfilmlocal.repo.WatchFilmLocalRepo;

import java.util.ArrayList;
import java.util.List;

public class WatchFilmLocalViewModels extends AndroidViewModel {
    private WatchFilmLocalRepo watchFilmLocalRepo;
    private MutableLiveData<DetailFilm> detailFilmLiveData;
    private MutableLiveData<ArrayList<MediaFile>> listFilmMutableLiveData;

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

    public MutableLiveData<ArrayList<MediaFile>> getListFilmMutableLiveData() {
        if (listFilmMutableLiveData == null)
            return listFilmMutableLiveData = watchFilmLocalRepo.getListFilmMutableLiveData();
        return listFilmMutableLiveData;
    }
    public void fetchListFilm(Context context) {
        watchFilmLocalRepo.fetchListFileFilm(context);
    }

}
