package com.example.moviefilm.film.user.bill.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.moviefilm.film.user.bill.repo.BillRepository;
import com.example.moviefilm.roomdb.billdb.Bill;

import java.util.List;

import io.reactivex.Flowable;

public class BillViewModel extends AndroidViewModel {
    private BillRepository billRepository;

    public BillViewModel(@NonNull Application application) {
        super(application);
        billRepository = new BillRepository(application);
    }

    //Get All Bill
    public Flowable<List<Bill>> getAllBill(){
        return billRepository.getAllBill();
    }

    //Delete Bill
    public void deleteBill(Bill bill){
        billRepository.deleteBill(bill);
    }
}
