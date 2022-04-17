package com.example.moviefilm.film.watchfilmlocal.repo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.base.FilmApi;
import com.example.moviefilm.base.RetroClass;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.watchfilmlocal.model.MediaFile;
import com.example.moviefilm.roomdb.cartdb.CartDatabase;
import com.example.moviefilm.roomdb.filmdb.FilmDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchFilmLocalRepo {
    private MutableLiveData<DetailFilm> detailFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MediaFile>> listFilmMutableLiveData = new MutableLiveData<>();
    private Context context;

    public WatchFilmLocalRepo(Application application) {
        this.context = context;
    }

    public void fetchDetailFilm(String id, String apiKey) {
        FilmApi detailFilm = RetroClass.getFilmApi();
        Observable<DetailFilm> detailFilmObservable = detailFilm.getDetailMovies(id, apiKey).subscribeOn(Schedulers.io());
        detailFilmObservable.subscribe(new Observer<DetailFilm>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull DetailFilm detailFilm) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }

            @Override
            public void onComplete() {
            }
        });

    }

    public MutableLiveData<DetailFilm> getDetailFilmMutableLiveData() {
        return detailFilmMutableLiveData;
    }

    public MutableLiveData<ArrayList<MediaFile>> getListFilmMutableLiveData() {
        return listFilmMutableLiveData;
    }

    public void fetchListFileFilm(Context context) {
        ArrayList<MediaFile> mediaFileList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selctionArg = new String[]{"%" + "Movie_Funny" + "%"};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selctionArg, null);
        if (cursor != null && cursor.moveToNext()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                @SuppressLint("Range") String dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
                if (size != null) {
                    if (Long.parseLong(size) != 0) {
                        MediaFile mediaFile = new MediaFile(id, title, displayName, size, duration, path, dateAdded);
                        mediaFileList.add(mediaFile);
                    }
                }
            } while (cursor.moveToNext());
        }
        listFilmMutableLiveData.postValue(mediaFileList);
    }
}
