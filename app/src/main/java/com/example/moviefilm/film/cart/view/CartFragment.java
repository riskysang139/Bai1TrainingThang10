package com.example.moviefilm.film.cart.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moviefilm.R;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.databinding.FragmentCartBinding;
import com.example.moviefilm.film.cart.adapter.CartAdapter;
import com.example.moviefilm.film.cart.viewmodels.CartViewModel;
import com.example.moviefilm.film.home.adapter.FilmAdapter;
import com.example.moviefilm.film.home.allfilm.view.AllFilmActivity;
import com.example.moviefilm.film.home.detailFilm.DetailFilmActivity;
import com.example.moviefilm.roomdb.Film;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartFragment extends Fragment implements CartAdapter.OnCartClickListener {
    FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private List<Film> filmList;
    private CartViewModel cartViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CartAdapter.OnCartClickListener onCartClickListener;
    private String keyFrom = "";
    public static float totalPrice = 0;

    public static CartFragment getInstance() {
        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        totalPrice = 0;
        View view = binding.getRoot();
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        observerCartFilm();
        keyFrom = getArguments().getString(DetailFilmActivity.KEY_FROM, "");
        binding.totalPayment.setSelected(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void observerCartFilm() {
        Disposable disposable = cartViewModel.getFilmCart(1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Film>>() {
                    @Override
                    public void accept(List<Film> films) throws Exception {
                        filmList = films;
                        if (films.size() > 0) {
                            initAdapter(films);
                            binding.txtNoData.setVisibility(View.GONE);
                        } else {
                            binding.txtNoData.setVisibility(View.VISIBLE);
                        }

                    }
                });
        compositeDisposable.add(disposable);
    }

    @SuppressLint("SetTextI18n")
    private void initAdapter(List<Film> filmList) {
        cartAdapter = new CartAdapter(filmList, getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setAdapter(cartAdapter);
        slPaymentAllFilm(filmList);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClickCart(int position, boolean isChoose, int numberChoice) {
        if (isChoose)
            totalPrice += (filmList.get(position).getFilmRate() * 4);
        else
            totalPrice -= (filmList.get(position).getFilmRate() * 4);
        binding.totalPayment.setText("Total Payment : " + Converter.df.format(totalPrice) + " $");
        binding.btnPayment.setText("Payment (" + numberChoice + ")");
    }

    @Override
    public void onClickDetail(String id) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, id);
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_CART);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void slPaymentAllFilm(List<Film> filmList) {
        binding.cbSlAll.setOnClickListener(view -> {
            if (binding.cbSlAll.isChecked()) {
                if (cartAdapter != null) {
                    for (Film film : filmList)
                        film.setChecked(true);
                    cartAdapter.setFilmListDB(filmList);
                    cartAdapter.notifyDataSetChanged();
                    for (int i = 0; i < filmList.size(); i++)
                        totalPrice += (filmList.get(i).getFilmRate() * 4);
                    CartAdapter.numberChoice = filmList.size();
                    binding.btnPayment.setText("Payment (" + filmList.size() + ")");
                }
            } else {
                for (Film film : filmList)
                    film.setChecked(false);
                cartAdapter.setFilmListDB(filmList);
                cartAdapter.notifyDataSetChanged();
                totalPrice = 0;
                CartAdapter.numberChoice = 0;
                binding.btnPayment.setText("Payment (" + 0 + ")");
            }
            binding.totalPayment.setText("Total Payment : " + Converter.df.format(totalPrice) + " $");
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }
}
