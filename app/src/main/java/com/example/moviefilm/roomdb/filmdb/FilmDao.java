package com.example.moviefilm.roomdb.filmdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FilmDao {
    @Query("SELECT * from movie_database")
    Flowable<List<Film>> getAll();

    @Query("SELECT * from movie_database WHERE film_buy ==:isWantBuy")
    Flowable<List<Film>> getFilmCart(int isWantBuy);

    @Query("SELECT * from movie_database WHERE filmId ==:id")
    Flowable<Film> getFilmWithId(String id);

    @Query("SELECT * from movie_database WHERE film_watch ==:isWatched AND userId ==:userId")
    Flowable<List<Film>> getFilmWatched(int isWatched, String userId);

    @Query("SELECT * from movie_database WHERE film_love ==:isLoved AND userId ==:userId")
    Flowable<List<Film>> getFilmLoved(int isLoved, String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Film... films);

    @Insert
    void insertFilm(Film film);

    @Delete
    void deleteFilm(Film film);

    //Update existing movie
    @Update
    void updateFilm(Film film);
}
