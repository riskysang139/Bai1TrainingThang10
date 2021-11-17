package com.example.bai1training.allfilm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1training.R;
import com.example.bai1training.base.GridItemDecoration;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.databinding.ActivityAllFilmBinding;
import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.adapter.FilmAdapter2;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.models.Results;
import com.example.bai1training.film.viewmodels.FilmViewModels;

import java.util.ArrayList;
import java.util.List;

public class AllFilmActivity extends AppCompatActivity implements OnClickListener {

    private ActivityAllFilmBinding binding;
    private AllFilmViewModels filmViewModels;
    private RecyclerView rcvAllFilm;
    private List<Results> popularMoviesList, topRateMovieList, upComingMovieList, similarMovieList, recommendMovieList;
    private FilmAdapter filmAdapter;
    private static final String TAG = "Tag";
    private String fromScreen = "";
    private String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_film);
        filmViewModels = ViewModelProviders.of(this).get(AllFilmViewModels.class);
        initView();
        getData();
    }

    private void initView() {
        rcvAllFilm = findViewById(R.id.all_film);
        popularMoviesList = new ArrayList<>();
        topRateMovieList = new ArrayList<>();
        upComingMovieList = new ArrayList<>();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromScreen = bundle.getString(DetailFilmActivity.KEY_FROM, "");

            if (fromScreen.equals(DetailFilmActivity.FROM_POPULAR))
                observerPopularFilm();
            else if (fromScreen.equals(DetailFilmActivity.FROM_TOP_RATE))
                observerTopRateFilm();
            else if (fromScreen.equals(DetailFilmActivity.FROM_UP_COMING))
                observerUpComingFilm();
            else if (fromScreen.equals(DetailFilmActivity.FROM_SIMILAR)){
                id=bundle.getString(DetailFilmActivity.ID,"");
                observerSimilarFilm();
            }
            else if (fromScreen.equals(DetailFilmActivity.FROM_RECOMMEND)) {
                id=bundle.getString(DetailFilmActivity.ID,"");
                observerRecommendFilm();
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
        filmViewModels.fetchUpcomingMovies(MainActivity.API_KEY, 1);
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

    private void observerSimilarFilm() {
        filmViewModels.fetchSimilarFilm(id, MainActivity.API_KEY);
        filmViewModels.getSimilarFilmLiveData().observe(this, new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                if (resultRespone != null) {
                    similarMovieList = resultRespone.getResults();
                    initRecyclerSimilar();
                }
            }
        });
    }

    private void observerRecommendFilm() {
        filmViewModels.fetchRecommendFilm(id, MainActivity.API_KEY);
        filmViewModels.getRecommendFilmLiveData().observe(this, new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                if (resultRespone != null) {
                    recommendMovieList = resultRespone.getResults();
                    initRecyclerRecommend();
                }
            }
        });
    }

    private void initRecyclerPopular() {
        filmAdapter = new FilmAdapter(popularMoviesList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvAllFilm.addItemDecoration(new GridItemDecoration(com.viettel.vtecommerce.utils.Converter.dpToPx(this, 30), 2));
        rcvAllFilm.setLayoutManager(gridLayoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }

    private void initRecyclerTopRate() {
        filmAdapter = new FilmAdapter(topRateMovieList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvAllFilm.addItemDecoration(new GridItemDecoration(com.viettel.vtecommerce.utils.Converter.dpToPx(this, 30), 2));
        rcvAllFilm.setLayoutManager(gridLayoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }


    private void initRecyclerUpComing() {
        filmAdapter = new FilmAdapter(upComingMovieList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvAllFilm.addItemDecoration(new GridItemDecoration(com.viettel.vtecommerce.utils.Converter.dpToPx(this, 30), 2));
        rcvAllFilm.setLayoutManager(gridLayoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }

    private void initRecyclerSimilar() {
        filmAdapter = new FilmAdapter(similarMovieList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvAllFilm.addItemDecoration(new GridItemDecoration(com.viettel.vtecommerce.utils.Converter.dpToPx(this, 30), 2));
        rcvAllFilm.setLayoutManager(gridLayoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }

    private void initRecyclerRecommend() {
        filmAdapter = new FilmAdapter(recommendMovieList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvAllFilm.addItemDecoration(new GridItemDecoration(com.viettel.vtecommerce.utils.Converter.dpToPx(this, 30), 2));
        rcvAllFilm.setLayoutManager(gridLayoutManager);
        rcvAllFilm.setAdapter(filmAdapter);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(this, DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, resultFilm.getId() + "");
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_POPULAR);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
