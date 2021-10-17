package com.example.bai1training.film.popular;

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

import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.R;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.viewmodels.FilmViewModels;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.models.Results;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.databinding.FragmentPopularBinding;
import com.example.bai1training.base.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment implements OnClickListener {
    private FragmentPopularBinding binding;
    private FilmViewModels filmViewModels;
    private List<Results> popularMoviesList;
    private RecyclerView rcvPopular;
    private FilmAdapter popularAdapter;

    public static PopularFragment getInstance() {
        Bundle args = new Bundle();
        PopularFragment fragment = new PopularFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_popular, container, false);
        View view = binding.getRoot();
        filmViewModels = ViewModelProviders.of(this).get(FilmViewModels.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        observerPopularFilm();
        if (popularMoviesList == null) {
            Log.e("Sang", "failed :");
            filmViewModels.fetchPopularMovies(MainActivity.API_KEY,1);
        } else
            Log.e("Sang", popularMoviesList.toString());
    }

    private void observerPopularFilm() {
        filmViewModels.getmPopularMutableLiveData().observe(getActivity(), new Observer<ResultRespone>() {
                    @Override
                    public void onChanged(ResultRespone resultRespone) {
                        if (resultRespone == null)
                            Log.e("Sang", "onChange :");
                        else {
                            popularMoviesList = new ArrayList<>();
                            popularMoviesList= resultRespone.getResults();
                            intRecycler();
                            Log.e("Sang", "onChange :" + popularMoviesList.toString());
                        }
                    }
                }
        );
    }

    private void initView(View view) {
        rcvPopular = view.findViewById(R.id.rcv_popular);
    }

    private void intRecycler() {
        popularAdapter = new FilmAdapter(popularMoviesList, getActivity(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rcvPopular.addItemDecoration(new GridItemDecoration(60, 2));
        rcvPopular.setLayoutManager(gridLayoutManager);
        rcvPopular.setItemAnimator(new DefaultItemAnimator());
        rcvPopular.setAdapter(popularAdapter);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID,resultFilm.getId()+"");
        bundle.putString(DetailFilmActivity.KEY_FROM,DetailFilmActivity.FROM_POPULAR);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
