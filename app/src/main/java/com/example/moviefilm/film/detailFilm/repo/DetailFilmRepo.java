package com.example.moviefilm.film.detailFilm.repo;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.base.FilmApi;
import com.example.moviefilm.base.RetroClass;
import com.example.moviefilm.film.cart.model.CartResult;
import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.film.detailFilm.models.CastResponse;
import com.example.moviefilm.film.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.detailFilm.models.VideoResponse;
import com.example.moviefilm.film.models.ResultResponse;
import com.example.moviefilm.film.user.model.FilmLove;
import com.example.moviefilm.film.user.model.FilmLoveResult;
import com.example.moviefilm.roomdb.cartdb.Cart;
import com.example.moviefilm.roomdb.cartdb.CartDao;
import com.example.moviefilm.roomdb.cartdb.CartDatabase;
import com.example.moviefilm.roomdb.filmdb.Film;
import com.example.moviefilm.roomdb.filmdb.FilmDao;
import com.example.moviefilm.roomdb.filmdb.FilmDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class DetailFilmRepo {

    private static final String TAG = "TAG";
    private MutableLiveData<DetailFilm> detailFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<VideoResponse> videoTrailerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> similarFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultResponse> recommendMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<CastResponse> castResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<FilmBill.CartFB>> cartListResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<List<FilmLove>> filmLoveListResponseLiveData = new MutableLiveData<>();
    private FilmDao filmDao;
    private CartDao cartDao;
    private FirebaseFirestore firebaseDB;
    private Application application;
    private List<FilmBill.CartFB> cartList;
    private List<FilmLove> filmLoveList;


    public List<FilmBill.CartFB> getCartList() {
        return cartList;
    }

    public DetailFilmRepo(Application application) {
        FilmDatabase filmDatabase = FilmDatabase.getInstance(application);
        filmDao = filmDatabase.filmDao();
        CartDatabase cartDatabase = CartDatabase.getInstance(application);
        cartDao = cartDatabase.cartDao();
        firebaseDB = FirebaseFirestore.getInstance();
        this.application = application;
    }

    //Get film with id
    public Flowable<Film> getFilmWithId(String id) {
        return filmDao.getFilmWithId(id);
    }

    //Insert film
    public void insertFilm(final Film film) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                filmDao.insertFilm(film);
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
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    //Update Movie
    public void updateMovie(final Film film) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                filmDao.updateFilm(film);
            }
        }).observeOn(Schedulers.io())
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
                        Log.d(TAG, "onError: Called" + e.getMessage());
                    }
                });
    }

    //Get film with id
    public Flowable<Cart> getFilmCart(String id) {
        return cartDao.getCart(id);
    }

    //Get film with
    public Flowable<List<Cart>> getListFilmCart() {
        return cartDao.getCart();
    }

    //Insert film to cart
    public void insertFilmCart(final Cart cart) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                cartDao.insertCart(cart);
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
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }


    public MutableLiveData<DetailFilm> getDetailFilmMutableLiveData() {
        return detailFilmMutableLiveData;
    }

    public MutableLiveData<VideoResponse> getVideoTrailerMutableLiveData() {
        return videoTrailerMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getSimilarFilmMutableLiveData() {
        return similarFilmMutableLiveData;
    }

    public MutableLiveData<ResultResponse> getRecommendMutableLiveData() {
        return recommendMutableLiveData;
    }

    public MutableLiveData<CastResponse> getCastResponseMutableLiveData() {
        return castResponseMutableLiveData;
    }


    public void fetchDetailFilm(String id, String apiKey) {
        FilmApi detailFilm = RetroClass.getFilmApi();
        Observable<DetailFilm> detailFilmObservable = detailFilm.getDetailMovies(id, apiKey).subscribeOn(Schedulers.io());
        detailFilmObservable.subscribe(new Observer<DetailFilm>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull DetailFilm detailFilm) {
                detailFilmMutableLiveData.postValue(detailFilm);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void fetchVideoFilm(String id, String apiKey) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<VideoResponse> videoResponseObservable = videoFilm.getDetailVideoTrailer(id, apiKey).subscribeOn(Schedulers.io());
        videoResponseObservable.subscribe(new Observer<VideoResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull VideoResponse videoResponse) {
                videoTrailerMutableLiveData.postValue(videoResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchSimilarFilm(String id, String apiKey) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<ResultResponse> resultsObservable = videoFilm.getSimilarFilm(id, apiKey, 1).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                similarFilmMutableLiveData.postValue(resultResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchRecommendFilm(String id, String apiKey) {
        FilmApi videoFilm = RetroClass.getFilmApi();
        Observable<ResultResponse> resultsObservable = videoFilm.getRecommendFilm(id, apiKey, 1).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<ResultResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultResponse resultResponse) {
                recommendMutableLiveData.postValue(resultResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Sang", e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchCastFilm(String id, String apiKey) {
        FilmApi filmApi = RetroClass.getFilmApi();
        Observable<CastResponse> resultsObservable = filmApi.getCastFilm(id, apiKey).subscribeOn(Schedulers.io());
        resultsObservable.subscribe(new Observer<CastResponse>() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {

            }

            @Override
            public void onNext(@NonNull CastResponse resultRespone) {
                castResponseMutableLiveData.postValue(resultRespone);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("cast response", e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void insertFilmCartFirebase(List<FilmBill.CartFB> cartList) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("Cart", cartList);

        firebaseDB.collection("FilmCart")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(docData)
                .addOnCompleteListener(task -> {
                    Toast.makeText(application, "Add film to cart successfully!", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(application, "Add film to cart failed!", Toast.LENGTH_LONG).show();
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
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
        cartListResponseLiveData.postValue(cartList);
    }

    public MutableLiveData<List<FilmBill.CartFB>> getCartListResponseLiveData() {
        return cartListResponseLiveData;
    }


    public void insertFilmLoveFirebase(List<FilmLove> filmLoves) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("Love", filmLoves);

        firebaseDB.collection("FilmLove")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(docData)
                .addOnCompleteListener(task -> {
                    Toast.makeText(application, "Add film to list love successfully!", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(application, "Add film to list love failed!", Toast.LENGTH_LONG).show();
                });
    }

    public void deleteFilmLoveFirebase(int position) {
        firebaseDB.collection("FilmLove")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("Love", FieldValue.arrayRemove(position + ""));
    }

    public void fetchFilmLove() {
        filmLoveList = new ArrayList<>();
        firebaseDB.collection("FilmLove")
                .addSnapshotListener((value, error) -> {
                    if (value.getDocumentChanges() != null) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getDocument().getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    FilmLoveResult filmLoveResult = doc.getDocument().toObject(FilmLoveResult.class);
                                    if (filmLoveResult.getLove() != null) {
                                        filmLoveList.addAll(filmLoveResult.getLove());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
        filmLoveListResponseLiveData.postValue(filmLoveList);
    }

    public MutableLiveData<List<FilmLove>> getFilmLoveListResponseLiveData() {
        return filmLoveListResponseLiveData;
    }

}
