package com.example.bai1training.searchFilm;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bai1training.base.FilmApi;
import com.example.bai1training.base.RetroClass;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.models.Results;

import javax.xml.transform.Result;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFilmRepo {
    MutableLiveData<ResultRespone> searchFilmMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ResultRespone> getSearchFilmMutableLiveData() {
        return searchFilmMutableLiveData;
    }

    public SearchFilmRepo(Application application) {
    }

    public void fetchFilmRepo(String apiKey, String query) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<ResultRespone> resultsSearchObservable = filmApi.getSearchMovies(apiKey, query).subscribeOn(Schedulers.io());
        resultsSearchObservable.subscribe(new Observer<ResultRespone>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultRespone resultRespone) {
                searchFilmMutableLiveData.postValue(resultRespone);
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

}
