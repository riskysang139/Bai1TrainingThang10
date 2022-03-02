package com.example.moviefilm.roomdb.cartdb;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CartDao {
    @Query("SELECT * from cart_database")
    Flowable<List<Cart>> getCart();

    @Query("SELECT * from cart_database where filmId ==:id")
    Flowable<Cart> getCart(String id);

    @Insert
    void insertCart(Cart cart);

    @Delete
    void deleteMovies(Cart cart);

    //Delete all movies from cart;
    @Query("DELETE FROM cart_database")
    void deleteAllMovies();
}
