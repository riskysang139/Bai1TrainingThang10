package com.example.moviefilm.film.home.detailFilm.watchfilm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviefilm.R;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.databinding.ActivityWatchFilmBinding;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.home.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.film.home.detailFilm.watchfilm.viewmodels.WatchFilmViewModels;
import com.example.moviefilm.film.view.MainActivity;
import com.example.moviefilm.roomdb.Film;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchFilmActivity extends AppCompatActivity {
    ActivityWatchFilmBinding binding;
    private String videoId = "", id = "";
    private WatchFilmViewModels watchFilmViewModels;
    private final CompositeDisposable compositeDisposableNew = new CompositeDisposable();
    private Film filmDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_film);
        getData();
        watchFilmViewModels = ViewModelProviders.of(this).get(WatchFilmViewModels.class);
        observerFilm();
        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.exitFullScreen();
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(videoId.equals("") ? "kim9qcN3CqE" : videoId, 0);
            }
        });
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            videoId = bundle.getString(DetailFilmActivity.VIDEO_ID, "");
            id = bundle.getString(DetailFilmActivity.ID, "");
        }
    }

    private void observerFilm() {
        Disposable disposable = watchFilmViewModels.getMovieWithId(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(films -> {
                    filmDB = films;
                    if (films != null) {
                        updateFilmWatch(films);
                    }
                });
        compositeDisposableNew.add(disposable);
    }

    private void updateFilmWatch(Film films) {
        if (films.getFilmWatch() == 0) {
            Film film = films;
            film.setFilmWatch(1);
            watchFilmViewModels.updateFilm(film);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.youtubePlayerView.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposableNew.dispose();
    }
}
