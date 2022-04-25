package com.example.moviefilm.film.home.detailFilm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.home.detailFilm.models.CastResponse;
import com.example.moviefilm.film.home.detailFilm.models.DetailFilm;
import com.example.moviefilm.film.home.detailFilm.models.VideoResponse;
import com.example.moviefilm.film.home.detailFilm.repo.DetailFilmRepo;
import com.example.moviefilm.film.models.ResultResponse;
import com.example.moviefilm.film.user.model.FilmLove;
import com.example.moviefilm.roomdb.cartdb.Cart;
import com.example.moviefilm.film.cart.model.CartFB;
import com.example.moviefilm.roomdb.filmdb.Film;

import java.util.List;

import io.reactivex.Flowable;

public class DetailFilmViewModels extends AndroidViewModel {
    private MutableLiveData<DetailFilm> detailFilmLiveData;
    private MutableLiveData<VideoResponse> videoFilmLiveData;
    private MutableLiveData<ResultResponse> similarFilmLiveData;
    private MutableLiveData<ResultResponse> recommendFilmLiveData;
    private MutableLiveData<CastResponse> castResponseMutableLiveData;
    private MutableLiveData<List<CartFB>> cartListMutableLiveData;
    private MutableLiveData<List<FilmLove>> filmLoveListMutableLiveData;
    private DetailFilmRepo detailFilmRepo;

    public DetailFilmViewModels(@NonNull Application application) {
        super(application);
        detailFilmRepo = new DetailFilmRepo(application);
    }

    public MutableLiveData<DetailFilm> getDetailFilmLiveData() {
        if (detailFilmLiveData == null)
            return detailFilmLiveData = detailFilmRepo.getDetailFilmMutableLiveData();
        return detailFilmLiveData;
    }

    public MutableLiveData<VideoResponse> getVideoFilmLiveData() {
        if (videoFilmLiveData == null)
            return videoFilmLiveData = detailFilmRepo.getVideoTrailerMutableLiveData();
        return videoFilmLiveData;
    }

    public MutableLiveData<ResultResponse> getSimilarFilmLiveData() {
        if (similarFilmLiveData == null)
            return similarFilmLiveData = detailFilmRepo.getSimilarFilmMutableLiveData();
        return similarFilmLiveData;
    }

    public MutableLiveData<ResultResponse> getRecommendFilmLiveData() {
        if (recommendFilmLiveData == null)
            return recommendFilmLiveData = detailFilmRepo.getRecommendMutableLiveData();
        return recommendFilmLiveData;
    }

    public MutableLiveData<CastResponse> getCastResponseMutableLiveData() {
        if (castResponseMutableLiveData == null)
            return castResponseMutableLiveData = detailFilmRepo.getCastResponseMutableLiveData();
        return castResponseMutableLiveData;
    }

    //Get Movie with id
    public Flowable<Film> getMovieWithId(String id){
        return detailFilmRepo.getFilmWithId(id);
    }

    //Insert Movie
    public void insertFilm(Film film){
        detailFilmRepo.insertFilm(film);
    }

    //Update Movie
    public void updateFilm(Film film){
        detailFilmRepo.updateMovie(film);
    }

    //Get Movie with id
    public Flowable<Cart> getMovieCart(String id){
        return detailFilmRepo.getFilmCart(id);
    }

    //Get Movie with id
    public Flowable<List<Cart>> getListMovieCart(){
        return detailFilmRepo.getListFilmCart();
    }

    //Insert Cart
    public void insertFilmCart(Cart cart){
        detailFilmRepo.insertFilmCart(cart);
    }


    public void fetchDetailFilm(String id , String apiKey) {
        detailFilmRepo.fetchDetailFilm(id,apiKey);
    }

    public void fetchVideoTrailerFilm(String id , String apiKey) {
        detailFilmRepo.fetchVideoFilm(id,apiKey);
    }
    public void fetchSimilarFilm(String id , String apiKey) {
        detailFilmRepo.fetchSimilarFilm(id,apiKey);
    }
    public void fetchRecommendFilm(String id , String apiKey) {
        detailFilmRepo.fetchRecommendFilm(id,apiKey);
    }
    public void fetchCastFilm(String id , String apiKey) {
        detailFilmRepo.fetchCastFilm(id,apiKey);
    }

    public void insertFilmCartFirebase(List<CartFB> cartList) {
        detailFilmRepo.insertFilmCartFirebase(cartList);
    }

    public void fetchFilmCart() {
        detailFilmRepo.fetchFilmCart();
    }

    public MutableLiveData<List<CartFB>> getCartListMutableLiveData() {
        if (cartListMutableLiveData == null)
            return detailFilmRepo.getCartListResponseLiveData();
        return cartListMutableLiveData;
    }

    public void setCartListMutableLiveData(MutableLiveData<List<CartFB>> cartListMutableLiveData) {
        this.cartListMutableLiveData = cartListMutableLiveData;
    }


    public void insertFilmLoveFirebase(List<FilmLove> filmLoveList) {
        detailFilmRepo.insertFilmLoveFirebase(filmLoveList);
    }

    public void fetchFilmLove() {
        detailFilmRepo.fetchFilmLove();
    }

    public MutableLiveData<List<FilmLove>> getFilmLoveListMutableLiveData() {
        if (filmLoveListMutableLiveData == null)
            return detailFilmRepo.getFilmLoveListResponseLiveData();
        return filmLoveListMutableLiveData;
    }

    public void setFilmLoveListMutableLiveData(MutableLiveData<List<FilmLove>> filmLoveListMutableLiveData) {
        this.filmLoveListMutableLiveData = filmLoveListMutableLiveData;
    }

    public void deleteFilmLoveFirebase(int position) {
        detailFilmRepo.deleteFilmLoveFirebase(position);
    }
}
