package com.example.moviefilm.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_database")
public class Film {
    @PrimaryKey
    private int filmId;

    @ColumnInfo(name = "film_name")
    private String filmName;

    @ColumnInfo(name = "film_image")
    private String filmImage;

    @ColumnInfo(name = "film_rate")
    private float filmRate;

    @ColumnInfo(name = "film_release_date")
    private String filmReleaseDate;

    @ColumnInfo(name = "film_love")
    private int filmLove;

    @ColumnInfo(name = "film_buy")
    private int isWantBuy;

    @ColumnInfo(name = "film_watch")
    private int filmWatch;

    private boolean isChecked = false;

    private float totalPrice;

    public Film(int filmId, String filmName, String filmImage, float filmRate, String filmReleaseDate, int filmLove, int isWantBuy, int filmWatch) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.filmImage = filmImage;
        this.filmRate = filmRate;
        this.filmReleaseDate = filmReleaseDate;
        this.filmLove = filmLove;
        this.isWantBuy = isWantBuy;
        this.filmWatch = filmWatch;
    }

    public Film() {
    }

    public int getIsWantBuy() {
        return isWantBuy;
    }

    public void setIsWantBuy(int isWantBuy) {
        this.isWantBuy = isWantBuy;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

    public String getFilmReleaseDate() {
        return filmReleaseDate;
    }

    public void setFilmReleaseDate(String filmReleaseDate) {
        this.filmReleaseDate = filmReleaseDate;
    }

    public float getFilmRate() {
        return filmRate;
    }

    public void setFilmRate(float filmRate) {
        this.filmRate = filmRate;
    }

    public int getFilmLove() {
        return filmLove;
    }

    public void setFilmLove(int filmLove) {
        this.filmLove = filmLove;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getFilmWatch() {
        return filmWatch;
    }

    public void setFilmWatch(int filmWatch) {
        this.filmWatch = filmWatch;
    }
}
