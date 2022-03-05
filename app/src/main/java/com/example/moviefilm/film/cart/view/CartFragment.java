package com.example.moviefilm.film.cart.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.moviefilm.R;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.databinding.FragmentCartBinding;
import com.example.moviefilm.film.cart.adapter.CartAdapter;
import com.example.moviefilm.film.cart.viewmodels.CartViewModel;
import com.example.moviefilm.film.home.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.film.user.login.view.LoginActivity;
import com.example.moviefilm.film.view.MainActivity;
import com.example.moviefilm.roomdb.billdb.Bill;
import com.example.moviefilm.roomdb.cartdb.Cart;
import com.example.moviefilm.roomdb.filmdb.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartFragment extends Fragment implements CartAdapter.OnCartClickListener {
    FragmentCartBinding binding;
    private CartAdapter cartAdapter;
    private List<Cart> filmList;
    private CartViewModel cartViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CartAdapter.OnCartClickListener onCartClickListener;
    private String keyFrom = "";
    public static float totalPrice = 0;
    private FirebaseAuth firebaseAuth;
    private List<Cart> filmListBuy;

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
        firebaseAuth = FirebaseAuth.getInstance();
        totalPrice = 0;
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        observerCartFilm();
        keyFrom = getArguments().getString(DetailFilmActivity.KEY_FROM, "");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filmListBuy = new ArrayList<>();
        binding.totalPayment.setSelected(true);
        binding.btnBack.setVisibility(View.GONE);
        binding.rlPayment.setVisibility(View.VISIBLE);
        binding.btnPayment.setOnClickListener(view1 -> insertBill());
        initAdapter();
    }

    private void observerCartFilm() {
        Disposable disposable = cartViewModel.getFilmCart().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        filmList = carts;
                        if (carts.size() > 0) {
                            binding.txtNoData.setVisibility(View.GONE);
                            binding.rlPayment.setVisibility(View.VISIBLE);
                            if (cartAdapter != null) {
                                cartAdapter.setFilmListDB(carts);
                                slPaymentAllFilm(carts);
                            }
                        } else {
                            binding.txtNoData.setVisibility(View.VISIBLE);
                            binding.rlPayment.setVisibility(View.GONE);
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        if (filmList == null)
            filmList = new ArrayList<>();
        cartAdapter = new CartAdapter(filmList, getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setAdapter(cartAdapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClickCart(int position, boolean isChoose, int numberChoice, Cart cart) {
        if (isChoose) {
            totalPrice += (filmList.get(position).getFilmRate() * 4);
            if (cart != null)
                filmListBuy.add(cart);
        } else {
            totalPrice -= (filmList.get(position).getFilmRate() * 4);
            if (cart != null)
                filmListBuy.remove(cart);
        }
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
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCLickDelete(Cart film, int position) {
        cartViewModel.deleteFilm(film);
        cartAdapter.notifyItemRemoved(position);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void slPaymentAllFilm(List<Cart> filmList) {
        binding.cbSlAll.setOnClickListener(view -> {
            if (binding.cbSlAll.isChecked()) {
                if (cartAdapter != null) {
                    for (Cart film : filmList)
                        film.setChecked(true);
                    cartAdapter.setFilmListDB(filmList);
                    cartAdapter.notifyDataSetChanged();
                    for (int i = 0; i < filmList.size(); i++)
                        totalPrice += (filmList.get(i).getFilmRate() * 4);
                    CartAdapter.numberChoice = filmList.size();
                    binding.btnPayment.setText("Payment (" + filmList.size() + ")");
                }
            } else {
                for (Cart film : filmList)
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

    @SuppressLint("NotifyDataSetChanged")
    private void insertBill() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Warning !!!")
                    .setMessage("Please login to payment your bill, Thanh you !")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(LoginActivity.KEY_FROM,DetailFilmActivity.FROM_CART);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            if (CartAdapter.numberChoice == 0)
                Toast.makeText(getContext(), "Please Choose Film You Want To Buy !", Toast.LENGTH_LONG).show();
            else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Buy Film !!!")
                        .setMessage("Do you want to buy it !")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                                cartViewModel.insertBill(new Bill(CartAdapter.numberChoice, totalPrice, timeStamp, firebaseUser.getEmail()));
                                if (binding.cbSlAll.isChecked()) {
                                    cartViewModel.deleteAllFilm();
                                    filmList.clear();
                                    binding.cbSlAll.setSelected(false);
                                    CartAdapter.numberChoice = 0;
                                } else {
                                    if (filmListBuy.size() == 0)
                                        Toast.makeText(getContext(), "Please Choose Film You Want To Buy !", Toast.LENGTH_LONG).show();
                                    else {
                                        for (Cart cart : filmListBuy)
                                            cartViewModel.deleteFilm(cart);
                                        filmListBuy.clear();
                                        CartAdapter.numberChoice = filmList.size();
                                    }
                                }
                                Toast.makeText(getContext(), "Buy Film SuccessFully !", Toast.LENGTH_LONG).show();
                                cartAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }
}
