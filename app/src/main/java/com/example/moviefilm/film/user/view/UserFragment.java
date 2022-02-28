package com.example.moviefilm.film.user.view;

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

import com.bumptech.glide.Glide;
import com.example.moviefilm.R;
import com.example.moviefilm.databinding.FragmentUserBinding;
import com.example.moviefilm.film.home.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.film.user.login.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFragment extends Fragment {

    FragmentUserBinding binding;
    private FirebaseAuth firebaseAuth;

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
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login();
        register();
        openVideoHistory();
        openLoveHistory();
        logOut();
    }

    private void checkUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Glide.with(UserFragment.this).load(R.drawable.logo_movie_app).circleCrop().into(binding.imgProfile);
            binding.btnLogOut.setVisibility(View.GONE);
            binding.btnHistory.setVisibility(View.GONE);
            binding.bnLoveHis.setVisibility(View.GONE);
            binding.btnFreeData.setBackgroundResource(R.drawable.shape_btn_down_up);
            binding.linearSignInUp.setVisibility(View.VISIBLE);
            binding.txtUserId.setText("Movie Funny");
            binding.txtEmail.setText("Movie Funny");
            Glide.with(UserFragment.this).load(R.drawable.logo_movie_app).into(binding.imgProfile);
        } else {
            binding.btnFreeData.setBackgroundResource(R.drawable.shape_linear_btn_down);
            binding.btnLogOut.setVisibility(View.VISIBLE);
            binding.btnHistory.setVisibility(View.VISIBLE);
            binding.bnLoveHis.setVisibility(View.VISIBLE);
            binding.linearSignInUp.setVisibility(View.INVISIBLE);
            binding.txtUserId.setText(firebaseUser.getDisplayName());
            binding.txtEmail.setText(firebaseUser.getEmail());
            Glide.with(getContext()).load(firebaseUser.getPhotoUrl()).circleCrop().into(binding.imgProfile);
        }
    }

    private void login() {
        binding.btnLogin.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class)));
    }

    private void register() {
        binding.btnRegister.setOnClickListener(v -> {
//                startActivity(new Intent(getActivity(), SignUpActivity.class));
        });
    }

    private void openVideoHistory() {
        binding.btnHistory.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HistoryFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_VIDEO_HISTORY);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void openLoveHistory() {
        binding.bnLoveHis.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HistoryFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_LOVED);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void logOut() {
        binding.btnLogOut.setOnClickListener(view -> {
            firebaseAuth.signOut();
            Toast.makeText(getContext(), "Log out Success !!!", Toast.LENGTH_SHORT).show();
            checkUser();
        });
    }
}