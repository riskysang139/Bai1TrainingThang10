package com.example.moviefilm.film.bill.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.film.bill.model.BillResult;
import com.example.moviefilm.film.bill.model.Wallet;
import com.example.moviefilm.roomdb.billdb.Bill;
import com.example.moviefilm.roomdb.billdb.BillDao;
import com.example.moviefilm.roomdb.billdb.BillDatabase;
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
import io.reactivex.schedulers.Schedulers;

public class BillRepository {
    private static final String TAG = "TAG";
    private BillDao billDao;
    private FirebaseFirestore firebaseDB;
    private List<FilmBill> billList;
    private MutableLiveData<List<FilmBill>> billListResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Wallet.WalletResult> walletResponseLiveData = new MutableLiveData<>();

    public BillRepository(Application application) {
        BillDatabase billDatabase = BillDatabase.getInstance(application);
        billDao = billDatabase.billDao();
        firebaseDB = FirebaseFirestore.getInstance();
    }


    //Get all bill
    public Flowable<List<Bill>> getAllBill() {
        return billDao.getAll();
    }

    //Delete bill
    public void deleteBill(final Bill bill) {
        Completable.fromAction(() -> billDao.deleteBill(bill)).subscribeOn(Schedulers.io())
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

    public void fetchFilmBill() {
        billList = new ArrayList<>();
        firebaseDB.collection("FilmBill")
                .addSnapshotListener((value, error) -> {
                    if (value.getDocumentChanges() != null) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getDocument().getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    BillResult billResult = doc.getDocument().toObject(BillResult.class);
                                    if (billResult.getBill() != null) {
                                        billList.addAll(billResult.getBill());
                                        billListResponseLiveData.postValue(billList);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public MutableLiveData<List<FilmBill>> getBillResponseLiveData() {
        return billListResponseLiveData;
    }

    public void fetchMyWallet() {
        firebaseDB.collection("FilmWallet")
                .addSnapshotListener((value, error) -> {
                    if (value.getDocumentChanges() != null) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getDocument().getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    Wallet wallet = doc.getDocument().toObject(Wallet.class);
                                    if (wallet.getWallet() != null) {
                                        walletResponseLiveData.postValue(wallet.getWallet());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public MutableLiveData<Wallet.WalletResult> getWalletResponseLiveData() {
        return walletResponseLiveData;
    }
}
