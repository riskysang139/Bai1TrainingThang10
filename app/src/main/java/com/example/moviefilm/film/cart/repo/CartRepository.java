package com.example.moviefilm.film.cart.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.cart.model.CartFB;
import com.example.moviefilm.film.cart.model.CartResult;
import com.example.moviefilm.roomdb.billdb.Bill;
import com.example.moviefilm.roomdb.billdb.BillDao;
import com.example.moviefilm.roomdb.billdb.BillDatabase;
import com.example.moviefilm.roomdb.cartdb.Cart;
import com.example.moviefilm.roomdb.cartdb.CartDao;
import com.example.moviefilm.roomdb.cartdb.CartDatabase;
import com.example.moviefilm.roomdb.filmdb.Film;
import com.example.moviefilm.roomdb.filmdb.FilmDao;
import com.example.moviefilm.roomdb.filmdb.FilmDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class CartRepository {
    private static final String TAG = "TAG";
    private CartDao cartDao;
    private BillDao billDao;
    private FilmDao filmDao;
    private List<CartFB> cartList;
    private MutableLiveData<List<CartFB>> cartListResponseLiveData = new MutableLiveData<>();
    private FirebaseFirestore firebaseDB;
    private Application application;

    public CartRepository(Application application) {
        FilmDatabase filmDatabase = FilmDatabase.getInstance(application);
        filmDao = filmDatabase.filmDao();
        CartDatabase cartDatabase = CartDatabase.getInstance(application);
        cartDao = cartDatabase.cartDao();
        BillDatabase billDatabase = BillDatabase.getInstance(application);
        billDao = billDatabase.billDao();
        firebaseDB = FirebaseFirestore.getInstance();
        this.application = application;
    }
    //Get all film cart
    public Flowable<List<Cart>> getAllCart(){
        return cartDao.getCart();
    }

    //Insert film
    public void insertBill(final Bill bill) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                billDao.insertBill(bill);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Get all film watch
    public Flowable<List<Film>> getFilmWithWatched(int isWatched){
        return filmDao.getFilmWatched(isWatched);
    }

    //Get all film watch
    public Flowable<List<Film>> getFilmWithLoved(int isWatched) {
        return filmDao.getFilmLoved(isWatched);
    }

    //Delete Movie Id
    public void deleteFilmID(Cart cart){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                cartDao.deleteMovies(cart);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+ e.getMessage());
                    }
                });
    }

    //Update Movie
    public void updateMovie(final Film film){
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    filmDao.updateFilm(film);
                }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+ e.getMessage());
                    }
                });
    }

    //Delete all Movie in Cart
    public void deleteAllMovies(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                cartDao.deleteAllMovies();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    public void fetchFilmCart() {
        cartList = new ArrayList<>();
        firebaseDB.collection("FilmCart")
                .addSnapshotListener((value, error) -> {
                    if (value.getDocumentChanges() != null) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getDocument().getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    CartResult cartResult = doc.getDocument().toObject(CartResult.class);
                                    if (cartResult.getCart() != null) {
                                        cartList.addAll(cartResult.getCart());
                                        cartListResponseLiveData.postValue(cartList);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
        if (cartListResponseLiveData.getValue() == null)
            cartListResponseLiveData.postValue(cartList);
    }

    public MutableLiveData<List<CartFB>> getCartListResponseLiveData() {
        return cartListResponseLiveData;
    }

}
