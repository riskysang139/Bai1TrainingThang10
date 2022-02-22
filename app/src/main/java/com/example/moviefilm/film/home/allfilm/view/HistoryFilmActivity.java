package com.example.moviefilm.film.home.allfilm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.FragmentCartBinding;
import com.example.moviefilm.film.cart.adapter.CartAdapter;
import com.example.moviefilm.film.cart.viewmodels.CartViewModel;
import com.example.moviefilm.film.home.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.roomdb.Film;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryFilmActivity extends AppCompatActivity implements CartAdapter.OnCartClickListener {
    FragmentCartBinding binding;
    private CartViewModel filmViewModels;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CartAdapter filmAdapter;
    private List<Film> listFilm = new ArrayList<>();
    private String fromScreen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_cart);
        filmViewModels = ViewModelProviders.of(this).get(CartViewModel.class);
        getData();
        initAdapter();
        initView();
    }

    private void initView() {
        binding.txtTitle.setText("Video Watched");
        binding.rlPayment.setVisibility(View.GONE);
        binding.btnBack.setVisibility(View.VISIBLE);
        finishes();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromScreen = bundle.getString(DetailFilmActivity.KEY_FROM, "");
        }
        switch (fromScreen) {
            case DetailFilmActivity.FROM_VIDEO_HISTORY:
                observerFilmWatched(1);
                break;
            case DetailFilmActivity.FROM_LOVED:
                observerFilmLoved(1);
                break;
        }
    }

    private void initAdapter() {
        filmAdapter = new CartAdapter(listFilm, getBaseContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setAdapter(filmAdapter);
    }

    private void observerFilmWatched(int isWatched) {
        Disposable disposable = filmViewModels.getFilmWatched(isWatched).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(films -> {
                    if (films != null) {
                        for (Film film : films)
                            film.setChecked2(true);
                        if (filmAdapter != null)
                            filmAdapter.setFilmListDB(films);
                        binding.txtNoData.setVisibility(View.GONE);
                    } else
                        binding.txtNoData.setVisibility(View.VISIBLE);
                });
        compositeDisposable.add(disposable);
    }

    private void observerFilmLoved(int isLoved) {
        Disposable disposable = filmViewModels.getFilmWatched(isLoved).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(films -> {
                    if (films != null) {
                        for (Film film : films)
                            film.setChecked2(true);
                        if (filmAdapter != null)
                            filmAdapter.setFilmListDB(films);
                        binding.txtNoData.setVisibility(View.GONE);
                    } else
                        binding.txtNoData.setVisibility(View.VISIBLE);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onClickCart(int position, boolean isChoose, int numberChoice) {
        // not use
    }

    @Override
    public void onClickDetail(String id) {
        Intent intent = new Intent(getBaseContext(), DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, id);
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_VIDEO_HISTORY);
        intent.putExtras(bundle);
        getBaseContext().startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void finishes() {
        binding.btnBack.setOnClickListener(view -> finish());
    }

}