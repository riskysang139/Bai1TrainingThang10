package com.example.moviefilm.admin;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.bill.model.BillResult;
import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.roomdb.billdb.BillDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminRepo {

    private FirebaseFirestore firebaseDB;
    private List<FilmBill> billList;
    private MutableLiveData<List<FilmBill>> billListResponseLiveData = new MutableLiveData<>();

    public AdminRepo(Application application) {
        firebaseDB = FirebaseFirestore.getInstance();
    }

    public void fetchFilmBill() {
        billList = new ArrayList<>();
        firebaseDB.collection("FilmBill")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BillResult billResult = document.toObject(BillResult.class);
                            billList.addAll(billResult.getBill());
                            Log.d("TAG", document.getId() + " => " + document.getData());
                        }
                        billListResponseLiveData.postValue(billList);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    public MutableLiveData<List<FilmBill>> getBillResponseLiveData() {
        return billListResponseLiveData;
    }
}
