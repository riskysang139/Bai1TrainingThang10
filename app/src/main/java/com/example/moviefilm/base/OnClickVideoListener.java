package com.example.moviefilm.base;

import com.example.moviefilm.film.home.detailFilm.models.Video;

public interface OnClickVideoListener {
    void OnClickVideo(Video video , int position);
    void OnClickStart(int position);
}
