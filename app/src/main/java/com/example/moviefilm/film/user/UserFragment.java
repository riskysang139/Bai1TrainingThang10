package com.example.moviefilm.film.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.moviefilm.R;
import com.example.moviefilm.databinding.FragmentUserBinding;
import com.example.moviefilm.film.home.allfilm.view.AllFilmActivity;
import com.example.moviefilm.film.home.allfilm.view.HistoryFilmActivity;
import com.example.moviefilm.film.user.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserInfo;

public class UserFragment extends Fragment {

    FragmentUserBinding binding;
//    UserInfo profile;

    public static UserFragment getInstance() {
        Bundle args = new Bundle();
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        checkUser();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        openVideoHistory();
    }

    private void initView() {
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        Glide.with(getActivity()).load(R.drawable.logo_movie_app).into(binding.imgProfile);
    }

    private void checkUser() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = "";
        Uri photo = null;
//        if (firebaseUser == null) {
//            Glide.with(getContext()).load(R.drawable.logomovieapp).circleCrop().into(binding.imgProfile);
//            binding.btnDangXuat.setVisibility(View.GONE);
//            binding.btnHistory.setVisibility(View.GONE);
//            binding.btnnoAdver.setBackgroundResource(R.drawable.shapelinearbtnup);
//
//        } else {
//            binding.btnHistory.setVisibility(View.VISIBLE);
//            binding.linearSignInUp.setVisibility(View.INVISIBLE);
//            binding.txtUserID.setText(firebaseUser.getUid());
//            binding.txtEmail.setText(firebaseUser.getEmail());
//            if (firebaseUser.getPhotoUrl() == null) {
//                Glide.with(getContext()).load(R.drawable.logomovieapp).circleCrop().into(binding.imgProfile);
//            } else
//                Glide.with(getContext()).load(firebaseUser.getPhotoUrl()).circleCrop().into(binding.imgProfile);
//            binding.btnDangXuat.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    firebaseAuth.signOut();
//                    getActivity().finish();
//                    startActivity(getActivity().getIntent());
//
//                }
//            });
//        }
    }

    private void login() {
//        binding.btndangNhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//            }
//        });
    }

    private void register() {
//        binding.btndangKi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), SignUpActivity.class));
//            }
//        });
    }

    private void openVideoHistory() {
        binding.btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HistoryFilmActivity.class);
            startActivity(intent);
        });
    }

    private void openLoveHistory() {
        binding.btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HistoryFilmActivity.class);
            startActivity(intent);
        });
    }
}