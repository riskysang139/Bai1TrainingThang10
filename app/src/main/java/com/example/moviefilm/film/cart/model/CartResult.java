package com.example.moviefilm.film.cart.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResult {
    private List<CartFB> Cart;

    public List<CartFB> getCart() {
        return Cart;
    }

    public void setCart(List<CartFB> cart) {
        Cart = cart;
    }
}
