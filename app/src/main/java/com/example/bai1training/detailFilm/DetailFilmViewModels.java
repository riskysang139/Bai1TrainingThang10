package com.example.bai1training.detailFilm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.detailFilm.models.VideoResponse;
import com.example.bai1training.detailFilm.repo.DetailFilmRepo;

public class DetailFilmViewModels extends AndroidViewModel {
    private MutableLiveData<DetailFilm> detailFilmLiveData;
    private MutableLiveData<VideoResponse> videoFilmLiveData;
    private DetailFilmRepo detailFilmRepo;

    public DetailFilmViewModels(@NonNull Application application) {
        super(application);
        detailFilmRepo = new DetailFilmRepo(application);
    }

    public MutableLiveData<DetailFilm> getDetailFilmLiveData() {
        if (detailFilmLiveData == null)
            return detailFilmLiveData = detailFilmRepo.getDetailFilmMutableLiveData();
        return detailFilmLiveData;
    }

    public MutableLiveData<VideoResponse> getVideoFilmLiveData() {
        if (detailFilmLiveData == null)
            return videoFilmLiveData = detailFilmRepo.getVideoTrailerMutableLiveData();
        return videoFilmLiveData;
    }

    public void fetchDetailFilm(String id , String apiKey) {
        detailFilmRepo.fetchDetailFilm(id,apiKey);
    }

    public void fetchVideoTrailerFilm(String id , String apiKey) {
        detailFilmRepo.fetchVideoFilm(id,apiKey);
    }
}
