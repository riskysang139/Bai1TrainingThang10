package com.example.moviefilm.film.watchfilmlocal.filmlocal;

import android.app.PictureInPictureParams;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.ActivityVideoLocalBinding;
import com.example.moviefilm.film.watchfilmlocal.model.MediaFile;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class VideoLocalActivity extends AppCompatActivity implements View.OnClickListener, PlaybackIconsAdapter.setOnCLickListener {
    ActivityVideoLocalBinding binding;
    SimpleExoPlayer playerView;
    private ArrayList<MediaFile> mediaFileList;
    private int position;
    private ConcatenatingMediaSource concatenatingMediaSource;

    private TextView txtTile;
    private ImageView imgNextExo, imgPreviouExo, btnBack, btnPlay, btnPlayMain;
    private static int playVideo = 0;
    private PictureInPictureParams.Builder pictureInPictureParams;
    private boolean isCrossChecked;
    private PlaybackIconsAdapter playbackIconsAdapter;
    private List<IconModel> iconModelList;
    private RecyclerView rcvIcon;
    private static boolean isMute = false;
    private static boolean isLightMode = false;

    public enum ControlMode {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_local);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("tag", "support picture in picture");
            pictureInPictureParams = new PictureInPictureParams.Builder();
        } else
            Log.d("tag", " not support picture in picture");
        initView();
        getData();
//        initYoutube();
        initAdapterIcon();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mediaFileList = bundle.getParcelableArrayList("mediaFile");
            position = bundle.getInt("position");
            playVideo();
        }
    }

    private void initView() {
        rcvIcon = findViewById(R.id.rcv_icon);
        txtTile = findViewById(R.id.title_film);
        imgNextExo = findViewById(R.id.img_next);
        imgPreviouExo = findViewById(R.id.img_previous);
        btnPlay = findViewById(R.id.img_play);
        btnBack = findViewById(R.id.img_back);
        btnPlayMain = findViewById(R.id.img_play_main);
        btnBack.setOnClickListener(this);
        imgPreviouExo.setOnClickListener(this);
        imgNextExo.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPlayMain.setOnClickListener(this);
    }

    private void playVideo() {
        if (mediaFileList.size() > 0) {
            Uri uri = Uri.parse(mediaFileList.get(position).getPath());
            playerView = new SimpleExoPlayer.Builder(VideoLocalActivity.this).build();
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "app"));
            concatenatingMediaSource = new ConcatenatingMediaSource();
            for (int i = 0; i < mediaFileList.size(); i++) {
                new File(String.valueOf(mediaFileList.get(i)));
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(String.valueOf(uri)));
                concatenatingMediaSource.addMediaSource(mediaSource);
            }
            binding.exoplayerView.setPlayer(playerView);
            binding.exoplayerView.setKeepScreenOn(true);
            playerView.prepare(concatenatingMediaSource);
            playerView.seekTo(position, C.TIME_UNSET);
            txtTile.setText(mediaFileList.get(position).getDisplayName());
            playerError();
        }
    }

    private void playerError() {
        playerView.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Player.EventListener.super.onPlayerError(error);
            }
        });
        playerView.setPlayWhenReady(true);
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (playerView.isPlaying()) {
            playerView.stop();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPause() {
        super.onPause();
        playerView.setPlayWhenReady(false);
        playerView.getPlaybackState();
        if (isInPictureInPictureMode()) {
            playerView.setPlayWhenReady(true);
        } else {
            playerView.setPlayWhenReady(false);
            playerView.getPlaybackState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.setPlayWhenReady(true);
        playerView.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        playerView.setPlayWhenReady(true);
        playerView.getPlaybackState();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_next:
                try {
                    if (position == mediaFileList.size() - 1) {
                        Toast.makeText(getBaseContext(), "No next video", Toast.LENGTH_SHORT).show();
                    } else {
                        playerView.stop();
                        position++;
                        playVideo();
                        btnPlay.setImageResource(R.drawable.pause_button);
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "No next video", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.img_previous:
                try {
                    playerView.stop();
                    position--;
                    playVideo();
                    btnPlay.setImageResource(R.drawable.pause_button);
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "No previous video", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.img_back:
                if (playerView != null) {
                    playerView.release();
                }
                finish();
                break;
            case R.id.img_play:
            case R.id.img_play_main:
                if (playVideo == 0) {
                    playVideo = 1;
                    playerView.stop();
                    btnPlay.setImageResource(R.drawable.play);
                    btnPlayMain.setImageResource(R.drawable.play);
                } else {
                    playVideo = 0;
                    btnPlay.setImageResource(R.drawable.pause_button);
                    btnPlayMain.setImageResource(R.drawable.pause_button);
                    playerView.prepare();
                }
                break;
        }
    }

    private void pictureInPictureModel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("tag", "support picture in picture");

            //set up height and width of PIP window
            Rational rational = new Rational(16, 9);
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
        isCrossChecked = isInPictureInPictureMode;
        if (isInPictureInPictureMode) {
            binding.exoplayerView.hideController();
        } else {
            binding.exoplayerView.showController();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isCrossChecked) {
            playerView.release();
            finish();
        }
    }

    private void initAdapterIcon() {
        iconModelList = new ArrayList<>();
        iconModelList.add(new IconModel("mute", R.drawable.vtecom_ic_sound, "Mute"));
        iconModelList.add(new IconModel("rotate", R.drawable.ic_baseline_screen_rotation_24, "Rotate"));
        iconModelList.add(new IconModel("dark_mode", R.drawable.ic_baseline_nights_stay_24, "Dark mode"));

        playbackIconsAdapter = new PlaybackIconsAdapter(iconModelList, getBaseContext());
        playbackIconsAdapter.setSetOnCLickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rcvIcon.setLayoutManager(layoutManager);
        rcvIcon.setAdapter(playbackIconsAdapter);
    }

    @Override
    public void onClick(IconModel iconModel, int position) {
        switch (iconModel.getId()) {
            case "mute":
                if (isMute) {
                    isMute = false;
                    iconModel.setImageView(R.drawable.vtecom_ic_sound);
                    playbackIconsAdapter.notifyItemChanged(position);
                    playerView.setVolume(70);
                } else {
                    isMute = true;
                    iconModel.setImageView(R.drawable.vtecom_ic_mute_sound);
                    playbackIconsAdapter.notifyItemChanged(position);
                    playerView.setVolume(0);
                }
                break;
            case "rotate":
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case "dark_mode":
                if (isLightMode) {
                    isLightMode = false;
                    iconModel.setImageView(R.drawable.ic_baseline_nights_stay_24);
                    iconModel.setIconTitle("Dark mode");
                    playbackIconsAdapter.notifyItemChanged(position);
                    binding.nightMode.setVisibility(View.GONE);
                } else {
                    isLightMode = true;
                    iconModel.setImageView(R.drawable.ic_baseline_wb_sunny_24);
                    iconModel.setIconTitle("Light mode");
                    playbackIconsAdapter.notifyItemChanged(position);
                    binding.nightMode.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}