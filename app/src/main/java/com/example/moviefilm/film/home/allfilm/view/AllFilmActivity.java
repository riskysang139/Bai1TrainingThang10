package com.example.moviefilm.film.home.allfilm.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.base.GridItemDecoration;
import com.example.moviefilm.base.OnClickListener;
import com.example.moviefilm.databinding.ActivityAllFilmBinding;
import com.example.moviefilm.film.home.adapter.EndlessRecyclerViewScrollListener;
import com.example.moviefilm.film.home.adapter.FilmAdapter;
import com.example.moviefilm.film.home.allfilm.viewmodel.AllFilmViewModels;
import com.example.moviefilm.film.home.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.film.models.Results;
import com.example.moviefilm.film.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AllFilmActivity extends AppCompatActivity implements OnClickListener {

    private ActivityAllFilmBinding binding;
    private AllFilmViewModels filmViewModels;
    private List<Results> listFilm;
    private FilmAdapter filmAdapter;
    private static final String TAG = "Tag";
    private String id = "";
    private String title = "";
    private EndlessRecyclerViewScrollListener mLoadMoreListener;
    private String fromScreen = "";

    /**
     * Operator
     * param to loadmore.
     */
    private int offset = 0;
    private int limit = 40;
    private boolean isCanLoadMore;

    private static int page_ = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_film);
        filmViewModels = ViewModelProviders.of(this).get(AllFilmViewModels.class);
        getData();
        initAdapter();
        onBackPress();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromScreen = bundle.getString(DetailFilmActivity.KEY_FROM, "");
            switch (fromScreen) {
                case DetailFilmActivity.FROM_POPULAR:
                    observerPopularFilm(1);
                    title = "Popular Movies";
                    break;
                case DetailFilmActivity.FROM_TOP_RATE:
                    observerTopRateFilm(1);
                    title = "Top Rated Movies";
                    break;
                case DetailFilmActivity.FROM_UP_COMING:
                    observerUpComingFilm(1);
                    title = "Up Coming Movies";
                    break;
                case DetailFilmActivity.FROM_SIMILAR:
                    id = bundle.getString(DetailFilmActivity.ID, "");
                    observerSimilarFilm(1);
                    title = "Similar Movies";
                    break;
                case DetailFilmActivity.FROM_RECOMMEND:
                    id = bundle.getString(DetailFilmActivity.ID, "");
                    observerRecommendFilm(1);
                    title = "Recommend Movies";
                    break;
            }
        }
        binding.txtTitle.setText(title);
    }

    private void observerPopularFilm(int page) {
        filmViewModels.fetchPopularMovies(MainActivity.API_KEY, page);
        filmViewModels.getPopularMutableLiveData().observe(this, resultRespone -> {
                    if (resultRespone != null) {
                        if (filmAdapter != null)
                            filmAdapter.setResultsList(resultRespone.getResults());
                        Log.e(TAG, "result response popular : ");
                    } else
                        Log.e(TAG, "call api popular failure");
                }
        );
    }

    private void observerTopRateFilm(int page) {
        filmViewModels.fetchTopRateMovies(MainActivity.API_KEY, page);
        filmViewModels.getTopRateMutableLiveData().observe(this, resultRespone -> {
                    if (resultRespone != null) {
                        if (filmAdapter != null)
                            filmAdapter.setResultsList(resultRespone.getResults());
                        Log.e(TAG, "result response top rated ");
                    } else
                        Log.e(TAG, "call api top rated failure");
                }
        );
    }

    private void observerUpComingFilm(int page) {
        filmViewModels.fetchUpcomingMovies(MainActivity.API_KEY, page);
        filmViewModels.getUpcomingMutableLiveData().observe(this, resultRespone -> {
                    if (resultRespone != null) {
                        if (filmAdapter != null)
                            filmAdapter.setResultsList(resultRespone.getResults());
                        Log.e(TAG, "result response upcoming ");
                    } else
                        Log.e(TAG, "call api upcoming failure");
                }
        );
    }

    private void observerSimilarFilm(int page) {
        filmViewModels.fetchSimilarFilm(id, MainActivity.API_KEY, page);
        filmViewModels.getSimilarFilmLiveData().observe(this, resultResponse -> {
            if (resultResponse != null) {
                if (filmAdapter != null)
                    filmAdapter.setResultsList(resultResponse.getResults());
                Log.e(TAG, "result response similar ");
            } else
                Log.e(TAG, "call api similar failure");
        });
    }

    private void observerRecommendFilm(int page) {
        filmViewModels.fetchRecommendFilm(id, MainActivity.API_KEY, page);
        filmViewModels.getRecommendFilmLiveData().observe(this, resultResponse -> {
            if (resultResponse != null) {
                if (filmAdapter != null)
                    filmAdapter.setResultsList(resultResponse.getResults());
                Log.e(TAG, "result response recommend ");
            } else
                Log.e(TAG, "call api recommend failure");
        });
    }


    @SuppressLint("NotifyDataSetChanged")
    private void initAdapter() {
        filmAdapter = new FilmAdapter(listFilm, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        binding.allFilm.addItemDecoration(new GridItemDecoration(Converter.dpToPx(this, 30), 2));
        binding.allFilm.setLayoutManager(gridLayoutManager);
        binding.allFilm.setAdapter(filmAdapter);
        mLoadMoreListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page_++;
                switch (fromScreen) {
                    case DetailFilmActivity.FROM_POPULAR:
                        observerPopularFilm(page_);
                        break;
                    case DetailFilmActivity.FROM_TOP_RATE:
                        observerTopRateFilm(page_);
                        break;
                    case DetailFilmActivity.FROM_UP_COMING:
                        observerUpComingFilm(page_);
                        break;
                    case DetailFilmActivity.FROM_SIMILAR:
                        observerSimilarFilm(page_);
                        break;
                    case DetailFilmActivity.FROM_RECOMMEND:
                        observerRecommendFilm(page_);
                        break;
                }
            }
            @Override
            public void onPastVisiblePosition(int pastVisibleItems, int totalItemCount) {

            }
        };
        binding.allFilm.addOnScrollListener(mLoadMoreListener);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(this, DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, resultFilm.getId() + "");
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_POPULAR);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onBackPress() {
        binding.btnBack.setOnClickListener(view -> finish());
    }
}
