package com.example.moviefilm.film.user.bill.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.ActivityBillBinding;
import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.film.user.bill.adapter.BillAdapter;
import com.example.moviefilm.film.user.bill.viewmodel.BillViewModel;
import com.example.moviefilm.roomdb.billdb.Bill;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BillActivity extends AppCompatActivity {
    ActivityBillBinding binding;
    private BillViewModel billViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BillAdapter billAdapter;
    private List<FilmBill> billList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill);
        billViewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        billViewModel.fetchFilmBill();
        billViewModel.fetchMyWallet();
        initAdapter();
        observeFilmBill();
        observeFilmWallet();
        initView();
    }

    private void observeFilmBill() {
        billViewModel.getBillListResponseLiveData().observe(this, filmBills -> {
            if (filmBills.size() > 0) {
                binding.rcvBill.setVisibility(View.VISIBLE);
                binding.txtNoData.setVisibility(View.GONE);
                if (billAdapter != null)
                    billAdapter.setBillList(filmBills);
            } else {
                binding.rcvBill.setVisibility(View.GONE);
                binding.txtNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void observeFilmWallet() {
        billViewModel.getWalletResponseLiveData().observe(this, filmWallet -> {
            if (filmWallet != null)
                binding.txtMoney.setText(Long.parseLong(filmWallet.getTotalMoney()) + " $");
        });
    }

    private void initAdapter() {
        if (billList == null)
            billList = new ArrayList<>();
        billAdapter = new BillAdapter(billList, getBaseContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcvBill.setLayoutManager(layoutManager);
        binding.rcvBill.setAdapter(billAdapter);
    }

    private void initView() {
        binding.btnBack.setOnClickListener(view -> finish());
        binding.layoutPayment.setOnClickListener(view -> Toast.makeText(getBaseContext(), "The feature will be update soon", Toast.LENGTH_SHORT).show());
        binding.layoutScan.setOnClickListener(view -> Toast.makeText(getBaseContext(), "The feature will be update soon", Toast.LENGTH_SHORT).show());
        binding.layoutTopUp.setOnClickListener(view -> Toast.makeText(getBaseContext(), "The feature will be update soon", Toast.LENGTH_SHORT).show());
        binding.layoutTransfer.setOnClickListener(view -> Toast.makeText(getBaseContext(), "The feature will be update soon", Toast.LENGTH_SHORT).show());
    }
}