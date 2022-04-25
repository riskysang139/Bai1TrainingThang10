package com.example.moviefilm.film.cart.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class CartFB {
    private int filmId;
    private String filmName;
    private String filmImage;
    private float filmRate;
    private String filmReleaseDate;
    private boolean isChecked = false;

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmImage() {
        return filmImage;
    }

    public void setFilmImage(String filmImage) {
        this.filmImage = filmImage;
    }

    public float getFilmRate() {
        return filmRate;
    }

    public void setFilmRate(float filmRate) {
        this.filmRate = filmRate;
    }

    public String getFilmReleaseDate() {
        return filmReleaseDate;
    }

    public void setFilmReleaseDate(String filmReleaseDate) {
        this.filmReleaseDate = filmReleaseDate;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
