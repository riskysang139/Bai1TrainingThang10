package com.example.moviefilm.film.home.view;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moviefilm.R;
import com.example.moviefilm.film.home.allfilm.view.AllFilmActivity;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.base.HorizontalItemDecoration;
import com.example.moviefilm.base.OnClickListener;
import com.example.moviefilm.base.customview.SearchActionBarView;
import com.example.moviefilm.databinding.FragmentHomeBinding;
import com.example.moviefilm.film.home.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.film.view.MainActivity;
import com.example.moviefilm.film.home.adapter.FilmAdapter;
import com.example.moviefilm.film.home.adapter.MovieAdverAdapter;
import com.example.moviefilm.film.models.MovieAdverb;
import com.example.moviefilm.film.models.Results;
import com.example.moviefilm.film.viewmodels.FilmViewModels;
import com.example.moviefilm.film.home.searchFilm.SearchFilmActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnClickListener {
    private FragmentHomeBinding binding;
    private FilmViewModels filmViewModels;
    private List<Results> popularMoviesList, topRateMovieList, upComingMovieList = new ArrayList<>();
    private FilmAdapter popularFilmAdapter, upComingFilmAdapter, topRatedFilmAdapter;
    private MovieAdverAdapter movieAdverAdapter;
    private Handler handler = new Handler();
    private static final String TAG = "Tag";

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
        filmViewModels = ViewModelProviders.of(this).get(FilmViewModels.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observerVerMovieAdverb();
        observerPopularFilm();
        observerTopRateFilm();
        observerUpComingFilm();
        initRecyclerPopular();
        initRecyclerTopRate();
        initRecyclerUpComing();
        onRefresh();
        onClickAllFilm();
        initCallBackForSearchBar();
    }

    private void observerVerMovieAdverb() {
        filmViewModels.fetchMovieAdverb();
        filmViewModels.getMovieAdverbMutableLiveData().observe(getActivity(), movieAdverb -> {
            initViewAdverb(movieAdverb);
        });
    }

    private void observerPopularFilm() {
        filmViewModels.fetchPopularMovies(MainActivity.API_KEY, 1);
        filmViewModels.getPopularMutableLiveData().observe(getActivity(), resultResponse -> {
                    if (resultResponse != null) {
                        if (popularFilmAdapter != null)
                            popularFilmAdapter.setResultsList(resultResponse.getResults());
                    } else
                        Log.e(TAG, "call api failure");
                }
        );
    }

    private void observerTopRateFilm() {
        filmViewModels.fetchTopRateMovies(MainActivity.API_KEY, 1);
        filmViewModels.getTopRateMutableLiveData().observe(getActivity(), resultResponse -> {
                    if (resultResponse != null) {
                        topRateMovieList = resultResponse.getResults();
                        for (Results results : topRateMovieList)
                            results.setType(1);
                        if (topRatedFilmAdapter != null)
                            topRatedFilmAdapter.setResultsList(topRateMovieList);
                    } else
                        Log.e(TAG, "call api failure");
                }
        );
    }

    private void observerUpComingFilm() {
        filmViewModels.fetchUpcomingMovies(MainActivity.API_KEY, 1);
        filmViewModels.getUpcomingMutableLiveData().observe(getActivity(), resultResponse -> {
                    if (resultResponse != null) {
                        if (upComingFilmAdapter != null)
                            upComingFilmAdapter.setResultsList(resultResponse.getResults());
                    } else
                        Log.e(TAG, "call api failure");
                }
        );
    }

    private void initViewAdverb(List<MovieAdverb> movieAdverbList) {
        movieAdverAdapter = new MovieAdverAdapter(movieAdverbList, binding.viewAdver, getContext());
        binding.viewAdver.setAdapter(movieAdverAdapter);
        onAutoScrollAD();
    }

    private void initRecyclerPopular() {
        popularFilmAdapter = new FilmAdapter(popularMoviesList, getActivity(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rcvPopular.addItemDecoration(new HorizontalItemDecoration(Converter.dpToPx(requireContext(), 15)));
        binding.rcvPopular.setItemAnimator(new DefaultItemAnimator());
        binding.rcvPopular.setLayoutManager(layoutManager);
        binding.rcvPopular.setAdapter(popularFilmAdapter);
    }

    private void initRecyclerTopRate() {
        topRatedFilmAdapter = new FilmAdapter(topRateMovieList, getActivity(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.HORIZONTAL, false);
        binding.rcvTopRate.setLayoutManager(gridLayoutManager);
        binding.rcvTopRate.setAdapter(topRatedFilmAdapter);
    }

    private void initRecyclerUpComing() {
        upComingFilmAdapter = new FilmAdapter(upComingMovieList, getActivity(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.rcvUpComing.setLayoutManager(layoutManager);
        binding.rcvUpComing.addItemDecoration(new HorizontalItemDecoration(Converter.dpToPx(requireContext(), 15)));
        binding.rcvUpComing.setItemAnimator(new DefaultItemAnimator());
        binding.rcvUpComing.setAdapter(upComingFilmAdapter);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            binding.viewAdver.setCurrentItem(binding.viewAdver.getCurrentItem() + 1);
        }
    };

    private void onAutoScrollAD() {
        binding.viewAdver.setClipToPadding(false);
        binding.viewAdver.setClipChildren(false);
        binding.viewAdver.setOffscreenPageLimit(3);
        binding.viewAdver.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.8f + r * 0.2f);
        });
        binding.viewAdver.setPageTransformer(compositePageTransformer);
        binding.viewAdver.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    public void onRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                filmViewModels.fetchPopularMovies(MainActivity.API_KEY, 1);
                filmViewModels.fetchTopRateMovies(MainActivity.API_KEY, 1);
                filmViewModels.fetchUpcomingMovies(MainActivity.API_KEY, 1);
                filmViewModels.fetchMovieAdverb();
                binding.swipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });
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

    private void onClickAllFilm() {
        binding.btnMorePopular.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AllFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_POPULAR);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
        binding.btnTopRated.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AllFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_TOP_RATE);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
        binding.btnUpComing.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AllFilmActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_UP_COMING);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        });
    }

    /**
     * callback của searchBar
     */
    private void initCallBackForSearchBar() {
        binding.sbSearchAll.setSearchViewCallBack(new SearchActionBarView.SearchViewCallback() {
            @Override
            public void onTextChange(String s) {
//                if ("".equals(s)) {
//                    mViewModel.getObsStateSearch().postValue(StateSearch.FOCUS_SEARCH);
//                } else {
//                    mViewModel.getObsStateSearch().postValue(StateSearch.SEARCHING);
//                }
            }

            @Override
            public void onSubmitSearch(String s) {
                if ("".equals(s.trim())) {
                    Toast.makeText(getContext(), "Not Input Data", Toast.LENGTH_LONG).show();
                } else {
                    String keySearch = s.toLowerCase();
                    keySearch = keySearch.trim();
                    Intent intent = new Intent(getActivity(), SearchFilmActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(SearchFilmActivity.KEY, keySearch);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                    binding.sbSearchAll.setTextSearch("");
                }

            }
        });
    }
}
