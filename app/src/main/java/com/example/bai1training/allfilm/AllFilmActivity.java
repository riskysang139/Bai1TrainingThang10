package com.example.bai1training.allfilm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1training.R;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.databinding.ActivityAllFilmBinding;
import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.adapter.FilmAdapter2;
import com.example.bai1training.film.models.Results;
import com.example.bai1training.film.viewmodels.FilmViewModels;

import java.util.ArrayList;
import java.util.List;

public class AllFilmActivity extends AppCompatActivity implements OnClickListener {

    private ActivityAllFilmBinding binding;
    private AllFilmViewModels filmViewModels;
    private RecyclerView rcvAllFilm;
    private List<Results> popularMoviesList, topRateMovieList, nowPlayingMovieList, upComingMovieList;
    private FilmAdapter filmAdapter;
    private static final String TAG = "Tag";
    private String fromScreen = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_film);
        filmViewModels = ViewModelProviders.of(this).get(AllFilmViewModels.class);
        getData();
        initView();
    }

    private void initView() {
        rcvAllFilm = findViewById(R.id.all_film);
        popularMoviesList = new ArrayList<>();
        topRateMovieList = new ArrayList<>();
        upComingMovieList = new ArrayList<>();
        nowPlayingMovieList = new ArrayList<>();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (fromScreen.equals(DetailFilmActivity.FROM_POPULAR)) {

            }
            if (fromScreen.equals(DetailFilmActivity.FROM_TOP_RATE)) {

            }
            if (fromScreen.equals(DetailFilmActivity.FROM_UP_COMING)) {

            }
        }
    }

    private void observerPopularFilm() {
        filmViewModels.fetchPopularMovies(MainActivity.API_KEY, 1);
        filmViewModels.getmPopularMutableLiveData().observe(this, resultRespone -> {
                    if (resultRespone != null) {
                        popularMoviesList = resultRespone.getResults();
                        initRecyclerPopular();
                        Log.e(TAG, "result respone : " + popularMoviesList.toString());
                    } else
                        Log.e(TAG, "call api failure");
                }
        );
    }

    private void observerTopRateFilm() {
        filmViewModels.fetchTopRateMovies(MainActivity.API_KEY, 1);
        filmViewModels.getmTopRateMutableLiveData().observe(this, resultRespone -> {
                    if (resultRespone != null) {
                        topRateMovieList = resultRespone.getResults();
                        initRecyclerTopRate();
                        Log.e(TAG, "result respone : " + topRateMovieList.toString());
                    } else
                        Log.e(TAG, "call api failure");
                }
        );
    }

    private void observerUpComingFilm() {
        filmViewModels.fetchUpcomingMovies(MainActivity.API_KEY, 3);
        filmViewModels.getmUpcomingMutableLiveData().observe(this, resultRespone -> {
                    if (resultRespone != null) {
                        upComingMovieList = resultRespone.getResults();
                        initRecyclerUpComing();
                        Log.e(TAG, "result respone : " + upComingMovieList.toString());
                    } else
                        Log.e(TAG, "call api failure");
                }
        );
    }

    private void initRecyclerPopular() {
        filmAdapter = new FilmAdapter(popularMoviesList, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvAllFilm.setLayoutManager(layoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }

    private void initRecyclerTopRate() {
        filmAdapter = new FilmAdapter(topRateMovieList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        rcvAllFilm.setLayoutManager(gridLayoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }

    private void initRecyclerNowPlaying() {
        filmAdapter = new FilmAdapter(nowPlayingMovieList, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvAllFilm.setLayoutManager(layoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }

    private void initRecyclerUpComing() {
        filmAdapter = new FilmAdapter(upComingMovieList, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvAllFilm.setLayoutManager(layoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }


    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {

    }
}
