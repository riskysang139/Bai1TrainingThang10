package com.example.bai1training.film.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1training.R;
import com.example.bai1training.base.GridItemDecoration;
import com.example.bai1training.base.HorizontalItemDecoration;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.databinding.FragmentHomeBinding;
import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.models.Results;
import com.example.bai1training.film.viewmodels.FilmViewModels;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnClickListener {
    private FragmentHomeBinding binding;
    private FilmViewModels filmViewModels;
    private List<Results> popularMoviesList;
    private RecyclerView rcvPopular;
    private FilmAdapter popularAdapter;
    private static final String TAG="Tag";

    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        filmViewModels = ViewModelProviders.of(this).get(FilmViewModels.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        observerPopularFilm();
    }

    private void observerPopularFilm() {
        filmViewModels.fetchPopularMovies(MainActivity.API_KEY, 1);
        filmViewModels.getmPopularMutableLiveData().observe(getActivity(), resultRespone -> {
            if (resultRespone != null) {
                popularMoviesList = resultRespone.getResults();
                initRecycler();
                Log.e(TAG, "result respone : " + popularMoviesList.toString());
            } else
                Log.e(TAG, "call api failure");
        }
        );
    }

    private void initView(View view) {
        rcvPopular = view.findViewById(R.id.rcv_popular);
        popularMoviesList = new ArrayList<>();
    }

    private void initRecycler() {
        popularAdapter = new FilmAdapter(popularMoviesList, getActivity(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvPopular.setLayoutManager(layoutManager);
        rcvPopular.setAdapter(popularAdapter);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, resultFilm.getId() + "");
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_POPULAR);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
