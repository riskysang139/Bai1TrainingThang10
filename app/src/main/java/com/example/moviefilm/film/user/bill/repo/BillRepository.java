package com.example.moviefilm.film.user.bill.repo;

import android.app.Application;
import android.util.Log;

import com.example.moviefilm.roomdb.billdb.Bill;
import com.example.moviefilm.roomdb.billdb.BillDao;
import com.example.moviefilm.roomdb.billdb.BillDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class BillRepository {
    private static final String TAG = "TAG";
    private BillDao billDao;
    private Flowable<List<Bill>> allBill;

    public BillRepository(Application application) {
        BillDatabase billDatabase = BillDatabase.getInstance(application);
        billDao = billDatabase.billDao();
    }


    //Get all bill
    public Flowable<List<Bill>> getAllBill() {
        return billDao.getAll();
    }

    //Delete bill
    public void deleteBill(final Bill bill) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                billDao.deleteBill(bill);
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
}
