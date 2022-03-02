package com.example.moviefilm.roomdb.billdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface BillDao {
    @Query("SELECT * from bill_database")
    Flowable<List<Bill>> getAll();

    @Insert
    void insertBill(Bill bill);

    @Delete
    void deleteBill(Bill bill);
}
