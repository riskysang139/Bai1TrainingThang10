package com.example.bai1training.detailFilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.bai1training.R;
import com.example.bai1training.base.OnClickVideoListener;
import com.example.bai1training.databinding.ActivityDetailFilmBinding;
import com.example.bai1training.detailFilm.adaptert.VideoTrailerAdapter;
import com.example.bai1training.detailFilm.models.DetailFilm;
import com.example.bai1training.detailFilm.models.Video;
import com.example.bai1training.detailFilm.models.VideoResponse;
import com.example.bai1training.film.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailFilmActivity extends AppCompatActivity implements OnClickVideoListener {
    ActivityDetailFilmBinding binding;
    private VideoView videoView;

    public static final String KEY_FROM = "_from_screen";
    public static final String FROM_NOW_PLAYING = "FROM_NOW_PLAYING";
    public static final String FROM_UP_COMING = "FROM_UP_COMING";
    public static final String FROM_TOP_RATE = "FROM_TOP_RATE";
    public static final String FROM_POPULAR = "FROM_POPULAR";
    public static final String FROM_SEARCH = "FROM_SEARCH";

    public static final String LINK_HEADER_YOUTUBE="https://www.youtube.com/watch?v";

    public static final String ID = "ID_VIDEO";

    private String video_url = "";
    private static String id ="";
    private MediaController mediaController;
    private ImageView btnPlay,btnBack;
    private TextView txtTitle,txtDetail;
    private ImageView imgFilm;
    private DetailFilmViewModels detailFilmViewModels,movieTralerFilmViewModels;
    private DetailFilm detailFilms;
    private List<Video> videoList;
    private VideoTrailerAdapter videoTrailerAdapter;
    private RecyclerView rcvTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_film);
        detailFilmViewModels = ViewModelProviders.of(this).get(DetailFilmViewModels.class);
        movieTralerFilmViewModels = ViewModelProviders.of(this).get(DetailFilmViewModels.class);
        initView();
        getData();
        observerDetailFilm();
        setUpVideoView();
        onComeback();
    }

    private void initView() {
//        videoView = findViewById(R.id.video_view);
        btnPlay = findViewById(R.id.btn_start);
        btnBack= findViewById(R.id.btn_back);
        txtDetail=findViewById(R.id.detail_film);
        txtTitle = findViewById(R.id.title_film);
        imgFilm = findViewById(R.id.video_view_click);
        rcvTrailer  = findViewById(R.id.rcv_trailer_films);
        videoList=new ArrayList<>();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id =bundle.getString(ID,"1");
            detailFilmViewModels.fetchDetailFilm(id,MainActivity.API_KEY);
        } else
            Log.e("Sang", "No data");
    }

    private void setUpVideoView() {
//        mediaController = new MediaController(this);
//        Uri uri = Uri.parse(video_url);
//        videoView.setVideoURI(uri);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//        onPlayVideo();
//        videoView.setOnPreparedListener(mp -> btnPlay.setVisibility(View.VISIBLE));
//        videoView.setOnCompletionListener(mp -> btnPlay.setVisibility(View.VISIBLE));

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

    private void observerDetailFilm(){
        detailFilmViewModels.getDetailFilmLiveData().observe(this, detailFilm -> {
            detailFilms = detailFilm;
            setUpViewDetail();
        });
    }
    public void observerVideoTrailerFilm() {

        movieTralerFilmViewModels.fetchVideoTrailerFilm(id,MainActivity.API_KEY);
        movieTralerFilmViewModels.getVideoFilmLiveData().observe(DetailFilmActivity.this, new Observer<VideoResponse>() {
            @Override
            public void onChanged(VideoResponse videoResponse) {
                videoList=videoResponse.getResults();
                setUpVideoAdapter();
            }
        });
    }
    private void setUpViewDetail(){
        txtTitle.setText(detailFilms.getTitle());
        txtDetail.setText(detailFilms.getOverview());
        Glide.with(this).load(MainActivity.HEADER_URL_IMAGE+detailFilms.getBackdropPath()).into(imgFilm);
    }

    private void setUpVideoAdapter(){
        videoTrailerAdapter=new VideoTrailerAdapter(videoList,this,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvTrailer.setLayoutManager(layoutManager);
        rcvTrailer.setAdapter(videoTrailerAdapter);
    }

    @Override
    public void OnClickVideo(Video video, int position) {

    }

    @Override
    public void OnClickStart(int position) {

    }


}