package com.example.bai1training.film.toprate;

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

import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.models.Results;
import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.viewmodels.FilmViewModels;
import com.example.bai1training.R;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.databinding.FragmentTopRateBinding;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.base.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TopRateFragment extends Fragment implements OnClickListener {
    private FragmentTopRateBinding binding;
    private FilmViewModels filmViewModels;
    private List<Results> topRateMoviesList;
    private RecyclerView rcvTopRate ;
    private FilmAdapter topRateAdapter;

    public static TopRateFragment getInstance() {
        Bundle args = new Bundle();
        TopRateFragment fragment = new TopRateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_rate, container, false);
        View view = binding.getRoot();
        filmViewModels = ViewModelProviders.of(this).get(FilmViewModels.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        observerTopRateFilm();
        if (topRateMoviesList == null) {
            Log.e("Sang", "failed :");
            filmViewModels.fetchTopRateMovies(MainActivity.API_KEY,1);
        }else
            Log.e("Sang", topRateMoviesList.toString());
    }
    private void observerTopRateFilm() {
        filmViewModels.getmTopRateMutableLiveData().observe(getActivity(), new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                if (resultRespone == null)
                    Log.e("Sang", "onChange :");
                else {
                    topRateMoviesList=new ArrayList<>();
                    topRateMoviesList = resultRespone.getResults();
                    intRecycler();
                    Log.e("Sang", "onChange :" + topRateMoviesList.toString());
                }
            }
        });
    }
    private void initView(View view) {
        rcvTopRate = view.findViewById(R.id.rcv_top_rate);
    }

    private void intRecycler() {
        topRateAdapter = new FilmAdapter(topRateMoviesList, requireActivity(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rcvTopRate.addItemDecoration(new GridItemDecoration(60, 2));
        rcvTopRate.setLayoutManager(gridLayoutManager);
        rcvTopRate.setItemAnimator(new DefaultItemAnimator());
        rcvTopRate.setAdapter(topRateAdapter);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID,resultFilm.getId()+"");
        bundle.putString(DetailFilmActivity.KEY_FROM,DetailFilmActivity.FROM_TOP_RATE);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
