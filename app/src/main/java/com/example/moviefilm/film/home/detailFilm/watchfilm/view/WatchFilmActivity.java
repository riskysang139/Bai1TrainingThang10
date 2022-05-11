package com.example.moviefilm.film.home.detailFilm.watchfilm.view;

import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.ActivityWatchFilmBinding;
import com.example.moviefilm.film.home.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.film.home.detailFilm.watchfilm.viewmodels.WatchFilmViewModels;
import com.example.moviefilm.film.view.MainActivity;
import com.example.moviefilm.roomdb.filmdb.Film;
import com.google.firebase.auth.FirebaseAuth;
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
    private PictureInPictureParams.Builder pictureInPictureParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_film);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        getData();
        watchFilmViewModels = ViewModelProviders.of(this).get(WatchFilmViewModels.class);
        observerFilm();
        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(videoId.equals("") ? "kim9qcN3CqE" : videoId, 0);
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("tag", "support picture in picture");
            pictureInPictureParams = new PictureInPictureParams.Builder();
        } else
            Log.d("tag", " not support picture in picture");
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
            if (FirebaseAuth.getInstance().getCurrentUser() != null)
                film.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            watchFilmViewModels.updateFilm(film);
        }
    }

    private void pictureInPictureModel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("tag", "support picture in picture");

            //set up height and width of PIP window
            Rational rational = new Rational(160, 110);
            pictureInPictureParams.setAspectRatio(rational).build();
            enterPictureInPictureMode(pictureInPictureParams.build());
        } else
            Log.d("tag", "not support picture in picture");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        //called when user press home button
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isInPictureInPictureMode()) {
                Log.d("tag", "was not in pip");
                pictureInPictureModel();
            } else
                Log.d("tag", "onUserLeverHint : Already in PIP ");
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            Log.d("tag","Endtered PIP");
            //hide PIP button and acton bar

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
