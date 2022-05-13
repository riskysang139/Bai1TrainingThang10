package com.example.moviefilm.admin;

import com.example.moviefilm.film.cart.model.FilmBill;

import java.util.List;

public class Turnover {
    private String totalMoney;
    private String totalQuantity;

    public String getTotalMoney() {
        return totalMoney == null ? "" : totalMoney;
    }

    public String getTotalQuantity() {
        return totalQuantity == null ? "" : totalQuantity;
    }

    public Turnover(String totalMoney, String totalQuantity) {
        this.totalMoney = totalMoney;
        this.totalQuantity = totalQuantity;
    }
}
