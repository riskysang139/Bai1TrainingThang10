package com.example.moviefilm.film.cart.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.moviefilm.film.cart.repo.CartRepository;
import com.example.moviefilm.roomdb.billdb.Bill;
import com.example.moviefilm.roomdb.cartdb.Cart;
import com.example.moviefilm.roomdb.filmdb.Film;

import java.util.List;

import io.reactivex.Flowable;

public class CartViewModel extends AndroidViewModel {
    private CartRepository cartRepository;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
    }

    //Get Movie with cart
    public Flowable<List<Cart>> getFilmCart() {
        return cartRepository.getAllCart();
    }

    //Get Movie with love
    public Flowable<List<Film>> getFilmWLoved(int isWatched) {
        return cartRepository.getFilmWithLoved(isWatched);
    }

    //Get Movie with watch
    public Flowable<List<Film>> getFilmWatched(int isWatched) {
        return cartRepository.getFilmWithWatched(isWatched);
    }

    //Delete Film Cart
    public void deleteFilm(Cart cart){
        cartRepository.deleteFilmID(cart);
    }

    //Update Movie
    public void updateFilm(Film film){
        cartRepository.updateMovie(film);
    }

    //Delete All Film Cart
    public void deleteAllFilm(){
        cartRepository.deleteAllMovies();
    }

    //Insert Bill
    public void insertBill(Bill bill){
        cartRepository.insertBill(bill);
    }
}
