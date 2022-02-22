package com.example.moviefilm.film.home.detailFilm;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.moviefilm.R;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.base.HorizontalItemDecoration;
import com.example.moviefilm.base.LoadingDialog;
import com.example.moviefilm.base.OnClickListener;
import com.example.moviefilm.base.customview.CircleAnimationUtil;
import com.example.moviefilm.base.customview.ExpandableTextView;
import com.example.moviefilm.databinding.ActivityDetailFilmBinding;
import com.example.moviefilm.film.home.adapter.FilmAdapter;
import com.example.moviefilm.film.home.allfilm.view.AllFilmActivity;
import com.example.moviefilm.film.home.detailFilm.adapter.CastAdapter;
import com.example.moviefilm.film.home.detailFilm.models.Cast;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.home.detailFilm.viewmodel.DetailFilmViewModels;
import com.example.moviefilm.film.models.Results;
import com.example.moviefilm.film.view.MainActivity;
import com.example.moviefilm.roomdb.Film;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailFilmActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "TAG";
    ActivityDetailFilmBinding binding;
    private VideoView videoView;

    public static final String KEY_FROM = "_from_screen";
    public static final String FROM_NOW_PLAYING = "FROM_NOW_PLAYING";
    public static final String FROM_UP_COMING = "FROM_UP_COMING";
    public static final String FROM_TOP_RATE = "FROM_TOP_RATE";
    public static final String FROM_POPULAR = "FROM_POPULAR";
    public static final String FROM_SEARCH = "FROM_SEARCH";
    public static final String FROM_DETAIL = "FROM_DETAIL";
    public static final String FROM_SIMILAR = "FROM_SIMILAR";
    public static final String FROM_RECOMMEND = "FROM_RECOMMEND";
    public static final String FROM_CART = "FROM_CART";


    public static final String LINK_HEADER_YOUTUBE = "https://www.youtube.com/watch?v=";

    public static final String ID = "ID_VIDEO";

    private static String id = "";

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public static final String VIDEO_ID = "VIDEO_ID";

    private ImageView btnPlay;
    private RelativeLayout btnBack;
    private TextView txtTitle, txtAdult, txtGenres, txtTimeFilm, txtRelease;
    private ExpandableTextView txtDetail;
    private RatingBar txtRated;
    private ImageView imgFilm;
    private DetailFilmViewModels detailFilmViewModels;
    private DetailFilm detailFilms;
    private List<Results> listReFilm, listSimFilm = new ArrayList<>();
    private FilmAdapter recFilmAdapter, simFilmAdapter;
    private LoadingDialog loadingDialog;
    private Button btnSimilar, btnRecommend;
    private List<Cast> castList = new ArrayList<>();
    private CastAdapter castAdapter;
    private Film filmDB;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final CompositeDisposable compositeDisposableNew = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        detailFilmViewModels = ViewModelProviders.of(this).get(DetailFilmViewModels.class);
        initView();
        getData();
        observerFilm();
        observerDetailFilm();
        observerRecommendFilm();
        observeSimilarFilm();
        observerCastFilm();
        observerVideoTrailerFilm();
        onComeback();
        setUpReFilmAdapter();
        setUpSimFilmAdapter();
        setUpCastAdapter();
        actionMoreFilm();
        openCartFilm();
        refreshLayout();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            id = bundle.getString(ID, "1");
    }

    private void initView() {
        btnPlay = findViewById(R.id.btn_start);
        btnBack = findViewById(R.id.btn_back);
        txtDetail = findViewById(R.id.detail_film);
        txtTitle = findViewById(R.id.title_film);
        txtAdult = findViewById(R.id.tv_adult);
        txtGenres = findViewById(R.id.tv_genres);
        txtRated = findViewById(R.id.tv_rated);
        txtTimeFilm = findViewById(R.id.time_film);
        txtRelease = findViewById(R.id.year_release);
        imgFilm = findViewById(R.id.video_view_click);
        loadingDialog = findViewById(R.id.progress_loading);
        btnRecommend = findViewById(R.id.btn_more_recommend);
        btnSimilar = findViewById(R.id.btn_more_similar);
    }

    private void onComeback() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void observerDetailFilm() {
        detailFilmViewModels.fetchDetailFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getDetailFilmLiveData().observe(this, detailFilm -> {
            detailFilms = detailFilm;
            setUpViewDetail();
            insertMovieLove(detailFilm);
            insertMovieWatch(detailFilm);
            insertMovieCart(detailFilm);
        });
    }

    public void observerVideoTrailerFilm() {
        detailFilmViewModels.fetchVideoTrailerFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getVideoFilmLiveData().observe(DetailFilmActivity.this, videoResponse -> {
            if (videoResponse != null && videoResponse.getResults().size() > 0) {
                watchFilm(videoResponse.getResults().get(0).getKey());
            }
        });
    }

    public void observeSimilarFilm() {
        detailFilmViewModels.fetchSimilarFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getSimilarFilmLiveData().observe(this, resultResponse -> {
            if (resultResponse != null) {
                if (simFilmAdapter != null)
                    simFilmAdapter.setResultsList(resultResponse.getResults());
            }
        });
    }

    public void observerRecommendFilm() {
        detailFilmViewModels.fetchRecommendFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getRecommendFilmLiveData().observe(this, resultResponse -> {
            if (resultResponse != null) {
                if (recFilmAdapter != null)
                    recFilmAdapter.setResultsList(resultResponse.getResults());
            }
        });
    }


    public void observerFilm() {
        Disposable disposable = detailFilmViewModels.getMovies(id, 1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(films -> {
                    Log.d(TAG, "accept: getMovie");
                    filmDB = films;
                    if (films != null) {
                        updateFilmBuy(films);
                        updateFilmLove(films);
                        updateFilmWatch(films);
                    }
                });
        compositeDisposableNew.add(disposable);
    }

    public void observerCastFilm() {
        detailFilmViewModels.fetchCastFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getCastResponseMutableLiveData().observe(this, resultResponse -> {
            if (resultResponse != null) {
                if (castAdapter != null)
                    castAdapter.setCastList(resultResponse.getCast());
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setUpViewDetail() {
        txtTitle.setText(detailFilms.getTitle());
        txtDetail.setText(detailFilms.getOverview() + getString(R.string.read_more));
        Glide.with(this).load(MainActivity.HEADER_URL_IMAGE + detailFilms.getPosterPath()).into(imgFilm);
        txtRated.setRating(Float.parseFloat(detailFilms.getVoteAverage() / 2 + ""));
        if (detailFilms.getGenres().size() == 0 || detailFilms.getGenres().isEmpty())
            txtGenres.setText("•    No Data    •");
        else if (detailFilms.getGenres().size() == 1)
            txtGenres.setText("•    " + detailFilms.getGenres().get(0).getName() + "    •");
        else
            txtGenres.setText("•    " + detailFilms.getGenres().get(0).getName() + ", " + detailFilms.getGenres().get(1).getName() + "    •");
        txtTimeFilm.setText(detailFilms.getRuntime() + " mins");
        if (detailFilms.getAdult())
            txtAdult.setVisibility(View.VISIBLE);
        else
            txtAdult.setVisibility(View.GONE);
        txtRelease.setText(Converter.convertDate(detailFilms.getReleaseDate()));
        binding.txtPrice.setText("Add to cart : " + detailFilms.getVoteAverage() * 2 + " $");
    }

    private void setUpReFilmAdapter() {
        recFilmAdapter = new FilmAdapter(listReFilm, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rcvReconmmend.addItemDecoration(new HorizontalItemDecoration(Converter.dpToPx(this, 15)));
        binding.rcvReconmmend.setItemAnimator(new DefaultItemAnimator());
        binding.rcvReconmmend.setLayoutManager(layoutManager);
        binding.rcvReconmmend.setAdapter(recFilmAdapter);
    }

    private void setUpSimFilmAdapter() {
        simFilmAdapter = new FilmAdapter(listSimFilm, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rcvSimilarFilms.addItemDecoration(new HorizontalItemDecoration(Converter.dpToPx(this, 15)));
        binding.rcvSimilarFilms.setItemAnimator(new DefaultItemAnimator());
        binding.rcvSimilarFilms.setLayoutManager(layoutManager);
        binding.rcvSimilarFilms.setAdapter(simFilmAdapter);
    }

    private void setUpCastAdapter() {
        castAdapter = new CastAdapter(castList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rcvCast.addItemDecoration(new HorizontalItemDecoration(Converter.dpToPx(this, 15)));
        binding.rcvCast.setLayoutManager(layoutManager);
        binding.rcvCast.setAdapter(castAdapter);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(this, DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, resultFilm.getId() + "");
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_DETAIL);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void actionMoreFilm() {
        btnSimilar.setOnClickListener(view -> {
            Intent intent = new Intent(DetailFilmActivity.this, AllFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetailFilmActivity.ID, id + "");
            bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_SIMILAR);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        btnRecommend.setOnClickListener(view -> {
            Intent intent = new Intent(DetailFilmActivity.this, AllFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetailFilmActivity.ID, id + "");
            bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_RECOMMEND);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void watchFilm(String videoId) {
        binding.videoViewClick.setOnClickListener(view -> {
            Intent intent = new Intent(DetailFilmActivity.this, WatchFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(VIDEO_ID, videoId);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }


    private void insertMovieCart(@NonNull DetailFilm detailFilm) {
        binding.payment.setOnClickListener(view -> new CircleAnimationUtil().attachActivity(DetailFilmActivity.this).setTargetView(binding.payment).setMoveDuration(1000).setDestView(binding.rlCart).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (filmDB == null) {
                    Film film = new Film(detailFilm.getId(), detailFilm.getTitle(),
                            MainActivity.HEADER_URL_IMAGE + detailFilm.getPosterPath(),
                            Float.parseFloat(detailFilm.getVoteAverage() / 2 + ""), Converter.convertStringToDate(detailFilms.getReleaseDate()), 0, 1, 0);
                    detailFilmViewModels.insertFilm(film);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).startAnimation());
    }

    private void insertMovieWatch(@NonNull DetailFilm detailFilm) {
        if (filmDB == null) {
            Film film = new Film(detailFilm.getId(), detailFilm.getTitle(),
                    MainActivity.HEADER_URL_IMAGE + detailFilm.getPosterPath(),
                    Float.parseFloat(detailFilm.getVoteAverage() / 2 + ""), Converter.convertStringToDate(detailFilms.getReleaseDate()), 0, 0, 1);
            binding.videoViewClick.setOnClickListener(view -> detailFilmViewModels.insertFilm(film));
        }
    }

    private void insertMovieLove(@NonNull DetailFilm detailFilm) {
        if (filmDB == null) {
            Film film = new Film(detailFilm.getId(), detailFilm.getTitle(),
                    MainActivity.HEADER_URL_IMAGE + detailFilm.getPosterPath(),
                    Float.parseFloat(detailFilm.getVoteAverage() / 2 + ""), Converter.convertStringToDate(detailFilms.getReleaseDate()), 1, 0, 0);
            binding.rlLove.setOnClickListener(view -> {
                binding.imgHeart.setImageResource(R.drawable.heart_red);
                detailFilmViewModels.insertFilm(film);
            });
        }
    }

    private void updateFilmBuy(Film films) {
        if (films.getIsWantBuy() == 0) {
            binding.payment.setOnClickListener(view -> {
                Film film = films;
                film.setIsWantBuy(1);
                detailFilmViewModels.updateFilm(film);
            });
        } else
            binding.payment.setVisibility(View.GONE);
    }


    private void updateFilmLove(Film films) {
        if (films.getFilmLove() == 1) {
            binding.imgHeart.setImageResource(R.drawable.heart_red);
            binding.rlLove.setOnClickListener(view -> {
                Film film = films;
                film.setFilmLove(0);
                detailFilmViewModels.updateFilm(film);
                binding.imgHeart.setImageResource(R.drawable.heart);
            });
        } else {
            binding.rlLove.setOnClickListener(view -> {
                Film film = films;
                film.setFilmLove(1);
                detailFilmViewModels.updateFilm(film);
                binding.imgHeart.setImageResource(R.drawable.heart_red);
            });
        }
    }

    private void updateFilmWatch(Film films) {
        if (films.getFilmWatch() == 0) {
            binding.videoViewClick.setOnClickListener(view -> {
                Film film = films;
                film.setFilmWatch(1);
                detailFilmViewModels.updateFilm(film);
            });
        }
    }

    private void openCartFilm() {
        binding.rlCart.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(DetailFilmActivity.this, MainActivity.class);
            bundle.putString(KEY_FROM, FROM_DETAIL);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void refreshLayout() {
        Handler handler = new Handler();
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        detailFilmViewModels.fetchDetailFilm(id, MainActivity.API_KEY);
                        detailFilmViewModels.fetchRecommendFilm(id, MainActivity.API_KEY );
                        detailFilmViewModels.fetchCastFilm(id, MainActivity.API_KEY);
                        detailFilmViewModels.fetchVideoTrailerFilm(id, MainActivity.API_KEY);
                        detailFilmViewModels.fetchCastFilm(id, MainActivity.API_KEY);
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        compositeDisposableNew.dispose();
    }
}