package com.example.moviefilm.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.databinding.ActivityAdminBinding;
import com.example.moviefilm.film.cart.model.FilmBill;
import com.example.moviefilm.film.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityAdminBinding binding;
    private AdminViewModel adminViewModel;
    private AdminAdapter adminAdapter;
    private List<FilmBill> turNoverList = new ArrayList<>();
    private List<FilmBill> turNoverFilterList = new ArrayList<>();
    private List<Turnover> turnoverAllList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
    private float totalMoney = 0;
    private int totalQuantity = 0;
    private String dateFrom = "";
    private String dateTo = "";
    private Calendar calFrom;
    private Calendar calTo;
    private int typeDate; //0 dateStart, 1 dateEnd
    private DatePickerDialog datePickerStart, datePickerEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        initRcv();
        initView();
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);
        observerBill();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        turnoverAllList = new ArrayList<>();
        setUpDatePicker();
    }

    private void initView() {
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

        binding.txtDescription.setText(String.format(getString(R.string.revenue), dateFrom, dateTo));
        binding.txtDateFrom.setText(dateFrom);
        binding.txtDateTo.setText(dateTo);
    }

    @SuppressLint("SetTextI18n")
    private void observerBill() {
        adminViewModel.fetchFilmBill();
        adminViewModel.getBillListResponseLiveData().observe(this, filmBills -> {
            if (filmBills != null && filmBills.size() > 0) {
                filterTurnoverDefault(filmBills);
                turNoverList = filmBills;
            } else {
                binding.txtNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initRcv() {
        if (turNoverFilterList == null)
            turnoverAllList = new ArrayList<>();
        adminAdapter = new AdminAdapter(turNoverFilterList, getBaseContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rcvTurnover.setLayoutManager(layoutManager);
        binding.rcvTurnover.setAdapter(adminAdapter);
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
        binding.txtLogout.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Notification")
                    .setMessage("Do you want to log out !")
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        startActivity(new Intent(AdminActivity.this, MainActivity.class));
                        finishAffinity();
                        Toast.makeText(this, "Log out Success !!!", Toast.LENGTH_SHORT).show();
                    }).setIcon(R.drawable.logo_movie_app)
                    .show();
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
        binding.txtDescription.setText(String.format(getString(R.string.revenue), dateFrom, dateTo));
        filterTurnover();
    }

    @SuppressLint("SetTextI18n")
    private void filterTurnover() {
        if (turNoverList != null) {
            totalMoney = 0;
            turNoverFilterList.clear();
            for (FilmBill filmBill : turNoverList) {
                if (Converter.cvSDate(filmBill.getDayBuy()).before(Converter.cvSDateAfter(dateTo)) && (Converter.cvSDate(filmBill.getDayBuy()).after(Converter.cvSDateBefore(dateFrom)))) {
                    try {
                        totalMoney += Float.parseFloat(filmBill.getTotalPrice());
                        turNoverFilterList.add(filmBill);
                    } catch (NumberFormatException e) {
                        totalMoney = 0;
                    }
                    Collections.sort(turNoverFilterList, (t1, t2) -> {
                        if (Converter.cvSDate(t2.getDayBuy()).before(Converter.cvSDate(t1.getDayBuy())))
                            return  1;
                        else if (Converter.cvSDate(t2.getDayBuy()).after(Converter.cvSDate(t1.getDayBuy())))
                            return -1;
                        else
                            return 0;
                    });
                }
            }
            binding.txtMoneyTurnover.setText(Converter.df.format(totalMoney) + " $");
            if (adminAdapter != null && turNoverFilterList != null && turNoverFilterList.size() > 0) {
                binding.txtNoData.setVisibility(View.GONE);
                adminAdapter.setBillList(turNoverFilterList);
            } else {
                binding.txtNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    private void filterTurnoverDefault(List<FilmBill> filmBills) {
        totalMoney = 0;
        turNoverFilterList.clear();
        for (FilmBill filmBill : filmBills) {
            if (Converter.cvSDate(filmBill.getDayBuy()).before(Converter.cvSDateAfter(dateTo)) && (Converter.cvSDate(filmBill.getDayBuy()).after(Converter.cvSDateBefore(dateFrom)))) {
                try {
                    totalMoney += Float.parseFloat(filmBill.getTotalPrice());
                    turNoverFilterList.add(filmBill);
                } catch (NumberFormatException e) {
                    totalMoney = 0;
                }
                Collections.sort(turNoverFilterList, (t1, t2) -> {
                    if (Converter.cvSDate(t2.getDayBuy()).before(Converter.cvSDate(t1.getDayBuy())))
                        return  1;
                    else if (Converter.cvSDate(t2.getDayBuy()).after(Converter.cvSDate(t1.getDayBuy())))
                        return -1;
                    else
                        return 0;
                });
            }
        }
        binding.txtMoneyTurnover.setText(Converter.df.format(totalMoney) + " $");
        if (adminAdapter != null && turNoverFilterList != null && turNoverFilterList.size() > 0) {
            binding.txtNoData.setVisibility(View.GONE);
            adminAdapter.setBillList(turNoverFilterList);
        } else {
            binding.txtNoData.setVisibility(View.VISIBLE);
        }
    }
}