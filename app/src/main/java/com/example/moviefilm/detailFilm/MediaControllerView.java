package com.example.moviefilm.detailFilm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.moviefilm.R;


public class MediaControllerView extends LinearLayout {

    private ViewGroup mViewGroup;

    private TextView mTvStart, mTvEnd;

    private SeekBar mSeekBar;

    private ImageView mImvSound;

    private OnMediaControllerListener mediaControllerListener;

    private double mVolume = 0f;

    public void setMediaControllerListener(OnMediaControllerListener mediaControllerListener) {
        this.mediaControllerListener = mediaControllerListener;
    }

    public MediaControllerView(Context context) {
        super(context);
        initView();
    }

    public MediaControllerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MediaControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mViewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.vtecom_custom_media_controller, this);
        mTvStart = mViewGroup.findViewById(R.id.tv_start);
        mTvEnd = mViewGroup.findViewById(R.id.tv_end);
        mSeekBar = mViewGroup.findViewById(R.id.seek_bar_progress);
        mImvSound = mViewGroup.findViewById(R.id.imv_sound);
        addActionSeekBar();
        mTvStart.setText("00:00");
        mTvEnd.setText("00:00");
        volumeControl();
    }

    private void addActionSeekBar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaControllerListener != null)
                    mediaControllerListener.onSeekBarStartTrackingTouch(seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaControllerListener != null)
                    mediaControllerListener.onSeekBarStopTrackingTouch(seekBar);
            }
        });
    }

    private void volumeControl() {
        mImvSound.setOnClickListener(view -> {
            mediaControllerListener.onClickVolume(mVolume == 0f);

        });
    }

    public void setVolume(double volume) {
        mVolume = volume;
        if (volume == 0f) {
            mImvSound.setImageResource(R.drawable.vtecom_ic_mute_sound);
        } else {
            mImvSound.setImageResource(R.drawable.vtecom_ic_sound);
        }
    }

    public interface OnMediaControllerListener {
        void onSeekBarStartTrackingTouch(SeekBar seekBar);

        void onSeekBarStopTrackingTouch(SeekBar seekBar);

        void onClickVolume(boolean mute);
    }

    public void setSeekBarProgress(int progress) {
        mSeekBar.setProgress(progress);
    }
    public void setMaxSeekbar(int progress){
        mSeekBar.setMax(progress);
    }

    @SuppressLint("SetTextI18n")
    public void setCurrentDurationText(String text) {
        mTvStart.setText(text + "");
    }

    @SuppressLint("SetTextI18n")
    public void setTotalDurationText(String totalDurationText) {
        mTvEnd.setText(totalDurationText + "");
    }
}
