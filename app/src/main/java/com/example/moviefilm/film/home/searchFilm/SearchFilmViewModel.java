package com.example.moviefilm.film.home.searchFilm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.models.ResultRespone;

public class SearchFilmViewModel extends AndroidViewModel {
    private MutableLiveData<ResultRespone> resultResponeLiveData ;
    private SearchFilmRepo searchFilmRepo;

    public SearchFilmViewModel(@NonNull Application application) {
        super(application);
        searchFilmRepo=new SearchFilmRepo(application);
    }

    public MutableLiveData<ResultRespone> getResultResponeLiveData() {
        if(resultResponeLiveData ==null)
            return resultResponeLiveData=searchFilmRepo.getSearchFilmMutableLiveData();
        return resultResponeLiveData;
    }

    public void fetchSearchResponse(String apiKey, String query){
        searchFilmRepo.fetchFilmRepo(apiKey,query);
    }

}
