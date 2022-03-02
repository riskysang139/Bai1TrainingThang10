package com.example.moviefilm.roomdb.cartdb;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_database")
public class Cart {
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

    private boolean isChecked = false;

    public Cart(int filmId, String filmName, String filmImage, float filmRate, String filmReleaseDate) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.filmImage = filmImage;
        this.filmRate = filmRate;
        this.filmReleaseDate = filmReleaseDate;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public float getFilmRate() {
        return filmRate;
    }

    public void setFilmRate(float filmRate) {
        this.filmRate = filmRate;
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
}
