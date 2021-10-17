package com.example.bai1training.film.nowplaying;

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
import com.example.bai1training.databinding.FragmentNowPlayingBinding;
import com.example.bai1training.base.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingFragment extends Fragment implements OnClickListener {
    FragmentNowPlayingBinding binding;
    private FilmViewModels filmViewModels;
    private List<Results> nowPlayingMoviesList;
    private FilmAdapter filmAdapter;
    private RecyclerView rcvNowplaying;
    public static NowPlayingFragment getInstance(){
        Bundle args = new Bundle();
        NowPlayingFragment fragment = new NowPlayingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing,container,false);
        View view=binding.getRoot();
        filmViewModels = ViewModelProviders.of(this).get(FilmViewModels.class);
        observerNowPlayingFilm();
        if (nowPlayingMoviesList == null) {
            Log.e("Sang", "failed :");
            filmViewModels.fetchNowPlayingMovies(MainActivity.API_KEY,1);
        }else
            Log.e("Sang", nowPlayingMoviesList.toString());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void observerNowPlayingFilm() {
        filmViewModels.getmNowPlayingMutableLiveData().observe(getActivity(), resultRespone -> {
            if (resultRespone == null)
                Log.e("Sang", "onChange :");
            else {
                nowPlayingMoviesList=new ArrayList<>();
                nowPlayingMoviesList = resultRespone.getResults();
                Log.e("Sang", "onChange :" + nowPlayingMoviesList.toString());
                intRecycler();
            }
        });
    }
    private void initView(View view) {
        rcvNowplaying=view.findViewById(R.id.rcv_now_playing);
    }
    private void intRecycler() {
        filmAdapter = new FilmAdapter(nowPlayingMoviesList,requireActivity(),this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rcvNowplaying.addItemDecoration(new GridItemDecoration(60, 2));
        rcvNowplaying.setLayoutManager(gridLayoutManager);
        rcvNowplaying.setItemAnimator(new DefaultItemAnimator());
        rcvNowplaying.setAdapter(filmAdapter);
    }

    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID,resultFilm.getId()+"");
        bundle.putString(DetailFilmActivity.KEY_FROM,DetailFilmActivity.FROM_DETAIL);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
