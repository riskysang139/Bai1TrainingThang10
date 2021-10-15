package com.example.bai1training.base;

import com.example.bai1training.detailFilm.models.Video;

public interface OnClickVideoListener {
    void OnClickVideo(Video video , int position);
    void OnClickStart(int position);
}
