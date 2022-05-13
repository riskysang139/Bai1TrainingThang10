package com.example.moviefilm.film.searchFilm;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.base.FilmApi;
import com.example.moviefilm.base.RetroClass;
import com.example.moviefilm.film.models.ResultResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFilmRepo {
    MutableLiveData<ResultResponse> searchFilmMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ResultResponse> getSearchFilmMutableLiveData() {
        return searchFilmMutableLiveData;
    }

    public SearchFilmRepo(Application application) {
    }

    public void fetchFilmRepo(String apiKey, String query) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<ResultResponse> resultsSearchObservable = filmApi.getSearchMovies(apiKey, query).subscribeOn(Schedulers.io());
        resultsSearchObservable.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                searchFilmMutableLiveData.postValue(resultResponse);
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
