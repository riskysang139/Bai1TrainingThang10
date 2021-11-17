package com.example.bai1training.detailFilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.bai1training.R;
import com.example.bai1training.base.HorizontalItemDecoration;
import com.example.bai1training.base.LoadingDialog;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.base.OnClickVideoListener;
import com.example.bai1training.databinding.ActivityDetailFilmBinding;
import com.example.bai1training.detailFilm.adapter.VideoTrailerAdapter;
import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.detailFilm.models.Video;
import com.example.bai1training.detailFilm.models.VideoResponse;
import com.example.bai1training.detailFilm.viewmodel.DetailFilmViewModels;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.models.Results;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailFilmActivity extends AppCompatActivity implements OnClickVideoListener, OnClickListener {
    ActivityDetailFilmBinding binding;
    private VideoView videoView;

    public static final String KEY_FROM = "_from_screen";
    public static final String FROM_NOW_PLAYING = "FROM_NOW_PLAYING";
    public static final String FROM_UP_COMING = "FROM_UP_COMING";
    public static final String FROM_TOP_RATE = "FROM_TOP_RATE";
    public static final String FROM_POPULAR = "FROM_POPULAR";
    public static final String FROM_SEARCH = "FROM_SEARCH";
    public static final String FROM_DETAIL = "FROM_DETAIL";

    public static final String LINK_HEADER_YOUTUBE = "https://www.youtube.com/watch?v=";

    public static final String ID = "ID_VIDEO";

    private static String id = "";

    private static final DecimalFormat df = new DecimalFormat("0.0");

    private ImageView btnPlay;
    private RelativeLayout btnBack;
    private TextView txtTitle, txtDetail, txtAdult, txtGenres, txtTimeFilm, txtRelease;
    private RatingBar txtRated;
    private ImageView imgFilm;
    private DetailFilmViewModels detailFilmViewModels;
    private DetailFilm detailFilms;
    private List<Video> videoList;
    private VideoTrailerAdapter videoTrailerAdapter;
    private RecyclerView rcvTrailer, rcvSimilarFilm, rcvRecommendFilm;
    private List<Results> listSimilarFilm, listRecommendFilm;
    private FilmAdapter filmAdapter;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        detailFilmViewModels = ViewModelProviders.of(this).get(DetailFilmViewModels.class);
        initView();
        getData();
        observerDetailFilm();
        observerVideoTrailerFilm();
        observerRecommendFilm();
        observeSimilarFilm();
        onComeback();
        delayLoading();
    }

    private void initView() {
//        videoView = findViewById(R.id.video_view);
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
        rcvTrailer = findViewById(R.id.rcv_trailer_films);
        rcvRecommendFilm = findViewById(R.id.rcv_reconmmend);
        rcvSimilarFilm = findViewById(R.id.rcv_similar_films);
        loadingDialog = findViewById(R.id.progress_loading);
        videoList = new ArrayList<>();
        listRecommendFilm = new ArrayList<>();
        listSimilarFilm = new ArrayList<>();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString(ID, "1");
        } else
            Log.e("Sang", "No data");
    }

    private void setUpVideoView(String linkVideo) {
        MediaController mediaController = new MediaController(this);
        Uri uri = Uri.parse(linkVideo);
        videoView.setVideoURI(uri);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        onPlayVideo();
        videoView.setOnPreparedListener(mp -> btnPlay.setVisibility(View.VISIBLE));
        videoView.setOnCompletionListener(mp -> btnPlay.setVisibility(View.VISIBLE));

    }

    private void onPlayVideo() {
        btnPlay.setOnClickListener(v -> {
            videoView.start();
            btnPlay.setVisibility(View.GONE);
        });


    }

    private void onComeback() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void observerDetailFilm() {
        detailFilmViewModels.fetchDetailFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getDetailFilmLiveData().observe(this, detailFilm -> {
            detailFilms = detailFilm;
            setUpViewDetail();
        });
    }

    public void observerVideoTrailerFilm() {
        detailFilmViewModels.fetchVideoTrailerFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getVideoFilmLiveData().observe(DetailFilmActivity.this, new Observer<VideoResponse>() {
            @Override
            public void onChanged(VideoResponse videoResponse) {
                if (videoResponse != null) {
                    videoList = videoResponse.getResults();
                    setUpVideoAdapter();
                }

            }
        });

    }

    public void observeSimilarFilm() {
        detailFilmViewModels.fetchSimilarFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getSimilarFilmLiveData().observe(this, new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                if (resultRespone != null) {
                    listSimilarFilm = resultRespone.getResults();
                    setUpSimilarFilmAdapter();
                }
            }
        });

    }

    public void observerRecommendFilm() {
        detailFilmViewModels.fetchRecommendFilm(id, MainActivity.API_KEY);
        detailFilmViewModels.getRecommendFilmLiveData().observe(this, new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                if (resultRespone != null) {
                    listRecommendFilm = resultRespone.getResults();
                    setUpRecommendFilmAdapter();
                }
            }
        });

    }

    private void setUpViewDetail() {
        txtTitle.setText(detailFilms.getTitle());
        txtDetail.setText(detailFilms.getOverview());
        Glide.with(this).load(MainActivity.HEADER_URL_IMAGE + detailFilms.getPosterPath()).into(imgFilm);
        txtRated.setRating(Float.parseFloat(detailFilms.getVoteAverage() / 2 + ""));
        txtGenres.setText("•    " + detailFilms.getGenres().get(0).getName() + ", " + detailFilms.getGenres().get(1).getName() + "    •");
        txtTimeFilm.setText(detailFilms.getRuntime() + " mins");
        if (detailFilms.getAdult())
            txtAdult.setVisibility(View.VISIBLE);
        else
            txtAdult.setVisibility(View.GONE);
        txtRelease.setText(convertDate(detailFilms.getReleaseDate()));
    }

    private void setUpVideoAdapter() {
        videoTrailerAdapter = new VideoTrailerAdapter(videoList, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvTrailer.setLayoutManager(layoutManager);
        rcvTrailer.setAdapter(videoTrailerAdapter);
    }

    private void setUpRecommendFilmAdapter() {
        filmAdapter = new FilmAdapter(listRecommendFilm, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvRecommendFilm.setLayoutManager(layoutManager);
        rcvRecommendFilm.setAdapter(filmAdapter);
    }

    private void setUpSimilarFilmAdapter() {
        filmAdapter = new FilmAdapter(listSimilarFilm, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvSimilarFilm.setLayoutManager(layoutManager);
        rcvSimilarFilm.setAdapter(filmAdapter);
    }

    @Override
    public void OnClickVideo(Video video, int position) {
        setUpVideoView(DetailFilmActivity.LINK_HEADER_YOUTUBE + video.getKey());
    }

    @Override
    public void OnClickStart(int position) {

    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(this, DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, resultFilm.getId() + "");
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_DETAIL);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private String convertDate(String date) {
        String year = date.substring(0, 4);
        return year;
    }
    private void delayLoading() {
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.setVisibility(View.GONE);
            }
        },3000);
        loadingDialog.setVisibility(View.GONE);
    }
}