package com.example.moviefilm.film.cart.model;

import com.example.moviefilm.base.Converter;

import java.util.List;

public class FilmBill{
    private String idFilm;
    private String dayBuy;
    public List<CartFB> listFilm;
    private String totalFilm;
    private String totalPrice;

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public String getDayBuy() {
        return dayBuy;
    }

    public String getDayBuyFormat() {
        return Converter.convertDate(dayBuy);
    }

    public void setDayBuy(String dayBuy) {
        this.dayBuy = dayBuy;
    }

    public List<CartFB> getListFilm() {
        return listFilm;
    }

    public void setListFilm(List<CartFB> listFilm) {
        this.listFilm = listFilm;
    }

    public String getTotalFilm() {
        return totalFilm;
    }

    public void setTotalFilm(String totalFilm) {
        this.totalFilm = totalFilm;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static class CartFB {
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

}
