package com.example.moviefilm.film.cart.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.moviefilm.film.cart.repo.CartRepository;
import com.example.moviefilm.film.home.detailFilm.repo.DetailFilmRepo;
import com.example.moviefilm.roomdb.Film;

import java.util.List;

import io.reactivex.Flowable;

public class CartViewModel extends AndroidViewModel{
    private CartRepository cartRepository;
    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
    }
    //Get Movie with cart
    public Flowable<List<Film>> getFilmCart(int isWantBuy){
        return cartRepository.getFilmCart(isWantBuy);
    }

    //Get Movie with watch
    public Flowable<List<Film>> getFilmWatched(int isWatched){
        return cartRepository.getFilmWithWatched(isWatched);
    }

    //Get Movie with love
    public Flowable<List<Film>> getFilmWLoved(int isWatched){
        return cartRepository.getFilmWithLoved(isWatched);
    }
}
