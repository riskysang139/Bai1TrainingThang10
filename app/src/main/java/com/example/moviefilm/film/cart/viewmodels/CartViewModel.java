package com.example.moviefilm.film.cart.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.film.cart.repo.CartRepository;
import com.example.moviefilm.film.bill.model.Wallet;
import com.example.moviefilm.roomdb.billdb.Bill;
import com.example.moviefilm.roomdb.cartdb.Cart;
import com.example.moviefilm.roomdb.filmdb.Film;

import java.util.List;

import io.reactivex.Flowable;

public class CartViewModel extends AndroidViewModel {
    private CartRepository cartRepository;
    private MutableLiveData<List<FilmBill.CartFB>> cartListMutableLiveData;
    private MutableLiveData<Wallet.WalletResult> walletResponseLiveData;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
    }

    //Get Movie with cart
    public Flowable<List<Cart>> getFilmCart() {
        return cartRepository.getAllCart();
    }

    //Get Movie with love
    public Flowable<List<Film>> getFilmWLoved(int isWatched, String userId) {
        return cartRepository.getFilmWithLoved(isWatched, userId);
    }

    //Get Movie with watch
    public Flowable<List<Film>> getFilmWatched(int isWatched, String userId) {
        return cartRepository.getFilmWithWatched(isWatched, userId);
    }

    //Update Movie
    public void updateFilm(Film film){
        cartRepository.updateMovie(film);
    }

    //Delete All Film Cart
    public void deleteAllFilm(){
        cartRepository.deleteAllMovies();
    }

    //Insert FilmBill
    public void insertBill(Bill bill){
        cartRepository.insertBill(bill);
    }

    public MutableLiveData<List<FilmBill.CartFB>> getCartListMutableLiveData() {
        if (cartListMutableLiveData == null)
            return cartRepository.getCartListResponseLiveData();
        return cartListMutableLiveData;
    }

    public void setCartListMutableLiveData(MutableLiveData<List<FilmBill.CartFB>> cartListMutableLiveData) {
        this.cartListMutableLiveData = cartListMutableLiveData;
    }

    public void fetchFilmCart() {
        cartRepository.fetchFilmCart();
    }

    public void deleteFilmCart(int position, FilmBill.CartFB cartFB) {
        cartRepository.deleteFilmCartFirebase(position, cartFB);
    }

    public void deleteAllFilmCart() {
        cartRepository.deleteAllFilmCartFirebase();
    }

    public void insertFilmBuy(List<FilmBill.CartFB> cartFBList, String totalFilm, String timeStamp, String id) {
        cartRepository.insertFilmBillFirebase(cartFBList, totalFilm, timeStamp, id);
    }

    public void fetchMyWallet() {
        cartRepository.fetchMyWallet();
    }

    public void updateMyWallet(String myMoney) {
        cartRepository.updateMyWallet(myMoney);
    }


    public MutableLiveData<Wallet.WalletResult> getWalletResponseLiveData() {
        if (walletResponseLiveData == null)
            return cartRepository.getWalletResponseLiveData();
        return walletResponseLiveData;
    }
}
