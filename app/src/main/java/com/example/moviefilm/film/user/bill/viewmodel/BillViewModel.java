package com.example.moviefilm.film.user.bill.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.film.user.bill.model.Wallet;
import com.example.moviefilm.film.user.bill.repo.BillRepository;
import com.example.moviefilm.roomdb.billdb.Bill;

import java.util.List;

import io.reactivex.Flowable;

public class BillViewModel extends AndroidViewModel {
    private BillRepository billRepository;
    private MutableLiveData<List<FilmBill>> billListResponseLiveData;
    private MutableLiveData<Wallet.WalletResult> walletResponseLiveData;

    public BillViewModel(@NonNull Application application) {
        super(application);
        billRepository = new BillRepository(application);
    }

    //Get All FilmBill
    public Flowable<List<Bill>> getAllBill() {
        return billRepository.getAllBill();
    }

    //Delete FilmBill
    public void deleteBill(Bill bill) {
        billRepository.deleteBill(bill);
    }

    public void fetchFilmBill() {
        billRepository.fetchFilmBill();
    }

    public MutableLiveData<List<FilmBill>> getBillListResponseLiveData() {
        if (billListResponseLiveData == null)
            return billRepository.getBillResponseLiveData();
        return billListResponseLiveData;
    }

    public void fetchMyWallet() {
        billRepository.fetchMyWallet();
    }


    public MutableLiveData<Wallet.WalletResult> getWalletResponseLiveData() {
        if (walletResponseLiveData == null)
            return billRepository.getWalletResponseLiveData();
        return walletResponseLiveData;
    }
}
