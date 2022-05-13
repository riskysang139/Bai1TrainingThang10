package com.example.moviefilm.film.user.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.FragmentCartBinding;
import com.example.moviefilm.film.cart.viewmodels.CartViewModel;
import com.example.moviefilm.film.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.roomdb.filmdb.Film;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryFilmActivity extends AppCompatActivity implements HistoryFilmAdapter.OnHistoryFilmClickListener {
    FragmentCartBinding binding;
    private CartViewModel filmViewModels;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private HistoryFilmAdapter filmAdapter;
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
                binding.txtTitle.setText("Video Watched");
                observerFilmWatched(1);
                break;
            case DetailFilmActivity.FROM_LOVED:
                binding.txtTitle.setText("Love History");
                observerFilmLoved(1);
                break;
        }
    }

    private void initAdapter() {
        filmAdapter = new HistoryFilmAdapter(listFilm, getBaseContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setAdapter(filmAdapter);
    }

    private void observerFilmWatched(int isWatched) {
        Disposable disposable = filmViewModels.getFilmWatched(isWatched, FirebaseAuth.getInstance().getCurrentUser().getUid()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(films -> {
                    if (films.size() > 0) {
                        for (Film film : films)
                            film.setChecked2(true);
                        if (filmAdapter != null)
                            filmAdapter.setFilmListDBWithChecked(films, DetailFilmActivity.FROM_LOVED);
                        binding.txtNoData.setVisibility(View.GONE);
                        binding.rcvCart.setVisibility(View.VISIBLE);
                    } else {
                        binding.txtNoData.setVisibility(View.VISIBLE);
                        binding.rcvCart.setVisibility(View.GONE);
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void observerFilmLoved(int isLoved) {
        Disposable disposable = filmViewModels.getFilmWLoved(isLoved, FirebaseAuth.getInstance().getCurrentUser().getUid()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(films -> {
                    if (films.size() > 0) {
                        for (Film film : films)
                            film.setChecked2(true);
                        if (filmAdapter != null)
                            filmAdapter.setFilmListDBWithChecked(films, DetailFilmActivity.FROM_LOVED);
                        binding.txtNoData.setVisibility(View.GONE);
                        binding.rcvCart.setVisibility(View.VISIBLE);
                    } else {
                        binding.txtNoData.setVisibility(View.VISIBLE);
                        binding.rcvCart.setVisibility(View.GONE);
                    }
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
        startActivity(intent);
    }

    @Override
    public void onCLickDelete(Film film, int position) {
        if (fromScreen.equals(DetailFilmActivity.FROM_VIDEO_HISTORY))
            film.setFilmWatch(0);
        else
            film.setFilmLove(0);
        filmViewModels.updateFilm(film);
        filmAdapter.notifyItemRemoved(position);
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