package com.example.moviefilm.film.bill.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviefilm.R;
import com.example.moviefilm.base.CommonUtilis;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.databinding.ActivityBillBinding;
import com.example.moviefilm.film.bill.adapter.BillAdapter;
import com.example.moviefilm.film.bill.viewmodel.BillViewModel;
import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.film.cart.viewmodels.CartViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BillActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ActivityBillBinding binding;
    private BillViewModel billViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BillAdapter billAdapter;
    private List<FilmBill> billList;
    private String dateFrom = "";
    private String dateTo = "";
    private Calendar calFrom;
    private Calendar calTo;
    private int typeDate; //0 dateStart, 1 dateEnd
    private DatePickerDialog datePickerStart, datePickerEnd;
    private List<FilmBill> turNoverFilterList = new ArrayList<>();
    private List<FilmBill> turNoverList = new ArrayList<>();
    private float myWallet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill);
        billViewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        billViewModel.fetchFilmBill();
        billViewModel.fetchMyWallet();
        initAdapter();
        observeFilmBill();
        observeFilmWallet();
        initView();
        setUpDatePicker();
    }

    private void observeFilmBill() {
        billViewModel.getBillListResponseLiveData().observe(this, filmBills -> {
            if (filmBills.size() > 0) {
                binding.rcvBill.setVisibility(View.VISIBLE);
                binding.txtNoData.setVisibility(View.GONE);
                filterTurnoverDefault(filmBills);
                turNoverList = filmBills;
            } else {
                binding.rcvBill.setVisibility(View.GONE);
                binding.txtNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void observeFilmWallet() {
        billViewModel.getWalletResponseLiveData().observe(this, filmWallet -> {
            if (filmWallet != null) {
                try {
                    myWallet = Float.parseFloat(filmWallet.getTotalMoney());
                } catch (NumberFormatException e) {
                    myWallet = 0;
                }
                binding.txtMoney.setText(filmWallet.getTotalMoney() + " $");
            }
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

        calFrom = Calendar.getInstance();
        calFrom.add(Calendar.DATE, -30);
        calTo = Calendar.getInstance();
        dateFrom = Converter.convertDateToString(calFrom.getTime(), "dd/MM/yyyy");
        dateTo = Converter.convertDateToString(calTo.getTime(), "dd/MM/yyyy");

        datePickerStart = new DatePickerDialog(this, this,
                calFrom.get(Calendar.YEAR), calFrom.get(Calendar.MONTH), calFrom.get(Calendar.DAY_OF_MONTH));
        datePickerStart.getDatePicker().setMaxDate(new Date().getTime());

        datePickerEnd = new DatePickerDialog(this, this,
                calTo.get(Calendar.YEAR), calTo.get(Calendar.MONTH), calTo.get(Calendar.DAY_OF_MONTH));
        datePickerEnd.getDatePicker().setMaxDate(new Date().getTime());

        binding.txtDateFrom.setText(dateFrom);
        binding.txtDateTo.setText(dateTo);
    }

    private void initView() {
        binding.btnBack.setOnClickListener(view -> finish());
        binding.layoutPayment.setOnClickListener(view -> Toast.makeText(getBaseContext(), "The feature will be update soon", Toast.LENGTH_SHORT).show());
        binding.layoutScan.setOnClickListener(view -> Toast.makeText(getBaseContext(), "The feature will be update soon", Toast.LENGTH_SHORT).show());
        binding.layoutTopUp.setOnClickListener(view -> getPopUpDialog());
        binding.layoutTransfer.setOnClickListener(view -> Toast.makeText(getBaseContext(), "The feature will be update soon", Toast.LENGTH_SHORT).show());
    }

    private void setUpDatePicker() {
        binding.txtDateFrom.setOnClickListener(view -> {
            typeDate = 0;
            datePickerStart.show();
        });
        binding.txtDateTo.setOnClickListener(view -> {
            typeDate = 1;
            datePickerEnd.show();
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (typeDate != 0) {
            calTo.set(year, monthOfYear, dayOfMonth);
            dateTo = Converter.convertDateToString(calTo.getTime(), "dd/MM/yyyy");
            if (calTo.before(calFrom)) {
                calFrom.setTime(calTo.getTime());
                dateFrom = Converter.convertDateToString(calFrom.getTime(), "dd/MM/yyyy");
            }
        } else {
            calFrom.set(year, monthOfYear, dayOfMonth);
            dateFrom = Converter.convertDateToString(calFrom.getTime(), "dd/MM/yyyy");
            if (calFrom.after(calTo)) {
                calTo.setTime(calFrom.getTime());
                dateTo = Converter.convertDateToString(calTo.getTime(), "dd/MM/yyyy");
            }
        }
        binding.txtDateFrom.setText(dateFrom);
        binding.txtDateTo.setText(dateTo);
        filterTurnover();
    }

    @SuppressLint("SetTextI18n")
    private void filterTurnover() {
        if (turNoverList != null) {
            turNoverFilterList.clear();
            for (FilmBill filmBill : turNoverList) {
                if (Converter.cvSDate(filmBill.getDayBuy()).before(Converter.cvSDateAfter(dateTo)) && (Converter.cvSDate(filmBill.getDayBuy()).after(Converter.cvSDateBefore(dateFrom)))) {
                    turNoverFilterList.add(filmBill);
                    Collections.sort(turNoverFilterList, (t1, t2) -> {
                        if (Converter.cvSDate(t2.getDayBuy()).before(Converter.cvSDate(t1.getDayBuy())))
                            return 1;
                        else if (Converter.cvSDate(t2.getDayBuy()).after(Converter.cvSDate(t1.getDayBuy())))
                            return -1;
                        else
                            return 0;
                    });
                }
            }
            if (billAdapter != null && turNoverFilterList != null && turNoverFilterList.size() > 0) {
                binding.txtNoData.setVisibility(View.GONE);
                billAdapter.setBillList(turNoverFilterList);
            } else {
                binding.txtNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    private void filterTurnoverDefault(List<FilmBill> filmBills) {
        turNoverFilterList.clear();
        for (FilmBill filmBill : filmBills) {
            if (Converter.cvSDate(filmBill.getDayBuy()).before(Converter.cvSDateAfter(dateTo)) && (Converter.cvSDate(filmBill.getDayBuy()).after(Converter.cvSDateBefore(dateFrom)))) {
                turNoverFilterList.add(filmBill);
                Collections.sort(turNoverFilterList, (t1, t2) -> {
                    if (Converter.cvSDate(t2.getDayBuy()).before(Converter.cvSDate(t1.getDayBuy())))
                        return 1;
                    else if (Converter.cvSDate(t2.getDayBuy()).after(Converter.cvSDate(t1.getDayBuy())))
                        return -1;
                    else
                        return 0;
                });
            }
        }
        if (billAdapter != null && turNoverFilterList != null && turNoverFilterList.size() > 0) {
            binding.txtNoData.setVisibility(View.GONE);
            billAdapter.setBillList(turNoverFilterList);
        } else {
            binding.txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void getPopUpDialog() {
        EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo_movie_app)
                .setTitle("Top up your wallet")
                .setView(editText)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    float moneyTopUp = 0;
                    try {
                        moneyTopUp = Float.parseFloat(editText.getText().toString());
                    } catch (NumberFormatException e) {
                        moneyTopUp = 0;
                    }
                    billViewModel.updateMyWallet(String.valueOf(moneyTopUp + myWallet));
                    billViewModel.fetchMyWallet();

                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
        }).show();
        editText.requestFocus();
    }
}