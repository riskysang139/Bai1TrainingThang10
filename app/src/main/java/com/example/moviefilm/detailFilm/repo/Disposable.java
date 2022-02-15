package com.example.moviefilm.detailFilm.repo;

import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;

public class Disposable {
    private static final String TAG = Disposable.class.getSimpleName();
    private static CompositeDisposable compositeDisposable;

    public static void add(io.reactivex.disposables.Disposable disposable) {
        Log.e(TAG,"add");
        getCompositeDisposable().add(disposable);
    }

    public static void dispose() {
        Log.e(TAG,"dispose");
        getCompositeDisposable().dispose();
    }

    private static CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    private Disposable() {}

}
