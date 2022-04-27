package com.example.moviefilm.film.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResult {
    private List<FilmBill.CartFB> Cart;

    public List<FilmBill.CartFB> getCart() {
        return Cart;
    }

    public void setCart(List<FilmBill.CartFB> cart) {
        Cart = cart;
    }
}
