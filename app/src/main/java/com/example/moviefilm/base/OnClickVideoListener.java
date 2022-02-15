package com.example.moviefilm.base;

import com.example.moviefilm.detailFilm.models.Video;

public interface OnClickVideoListener {
    void OnClickVideo(Video video , int position);
    void OnClickStart(int position);
}
