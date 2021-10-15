package com.example.bai1training.film.upcoming;


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
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.models.Results;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.viewmodels.FilmViewModels;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.R;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.databinding.FragmentUpcomingBinding;
import com.example.bai1training.base.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class UpComingFragment extends Fragment implements OnClickListener {
    private FragmentUpcomingBinding binding;
    private FilmViewModels filmViewModels;
    private List<Results> upComingMoviesList;
    private FilmAdapter upcomingAdapter;
    private RecyclerView rcvUpcoming;

    public static UpComingFragment getInstance() {
        Bundle args = new Bundle();
        UpComingFragment fragment = new UpComingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false);
        View view = binding.getRoot();
        filmViewModels = ViewModelProviders.of(this).get(FilmViewModels.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        observerUpcomingMovies();
        if (upComingMoviesList == null) {
            Log.e("Sang", "failed :");
            filmViewModels.fetchUpcomingMovies(MainActivity.API_KEY,1);
        } else
            Log.e("Sang", upComingMoviesList.toString());
    }

    private void observerUpcomingMovies() {
        filmViewModels.getmUpcomingMutableLiveData().observe(getActivity(), new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                if (resultRespone == null)
                    Log.e("Sang", "onChange :");
                else {
                    upComingMoviesList = new ArrayList<>();
                    upComingMoviesList = resultRespone.getResults();
                    intRecycler();
                    Log.e("Sang", "onChange :" + upComingMoviesList.toString());
                }
            }
        });
    }

    private void initView(View view) {
        rcvUpcoming = view.findViewById(R.id.rcv_upcoming);
    }

    private void intRecycler() {
        upcomingAdapter = new FilmAdapter(upComingMoviesList, requireActivity(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rcvUpcoming.addItemDecoration(new GridItemDecoration(46, 2));
        rcvUpcoming.setLayoutManager(gridLayoutManager);
        rcvUpcoming.setItemAnimator(new DefaultItemAnimator());
        rcvUpcoming.setAdapter(upcomingAdapter);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID,resultFilm.getId()+"");
        bundle.putString(DetailFilmActivity.KEY_FROM,DetailFilmActivity.FROM_UP_COMING);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
