package com.example.moviefilm.film.bill.model;

import com.example.moviefilm.film.cart.model.FilmBill;

import java.util.List;

public class BillResult {
    private List<FilmBill> Bill;

    public List<FilmBill> getBill() {
        return Bill;
    }

    public void setBill(List<FilmBill> bill) {
        Bill = bill;
    }
}
