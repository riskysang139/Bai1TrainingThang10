package com.example.moviefilm.admin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.bill.repo.BillRepository;
import com.example.moviefilm.film.cart.model.FilmBill;

import java.util.List;

public class AdminViewModel extends AndroidViewModel {
    private AdminRepo adminRepo;
    private MutableLiveData<List<FilmBill>> billListResponseLiveData;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        adminRepo = new AdminRepo(application);
    }

    public void fetchFilmBill() {
        adminRepo.fetchFilmBill();
    }

    public MutableLiveData<List<FilmBill>> getBillListResponseLiveData() {
        if (billListResponseLiveData == null)
            return adminRepo.getBillResponseLiveData();
        return billListResponseLiveData;
    }
}
