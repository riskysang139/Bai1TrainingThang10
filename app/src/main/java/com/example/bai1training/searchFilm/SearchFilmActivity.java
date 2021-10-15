package com.example.bai1training.searchFilm;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bai1training.R;
import com.example.bai1training.base.SearchActionBarView;
import com.example.bai1training.databinding.ActivitySearchFilmBinding;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.viewmodels.FilmViewModels;

public class SearchFilmActivity extends AppCompatActivity {
    ActivitySearchFilmBinding binding;
    SearchActionBarView searchActionBarView;
    SearchFilmViewModel mViewModel;
    public static String keySearch = "";
    private FilmAdapter filmAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_film);
        mViewModel=ViewModelProviders.of(this).get(SearchFilmViewModel.class);
        initView();
        initCallBackForSearchBar();
    }
    private void initView() {
        searchActionBarView = findViewById(R.id.sb_search_all);
    }

    private void initCallBackForSearchBar() {
        searchActionBarView.setSearchViewCallBack(new SearchActionBarView.SearchViewCallback() {
            @Override
            public void onTextChange(String s) {
                if ("".equals(s)) {
//                    mViewModel.getObsStateSearch().postValue(StateSearch.FOCUS_SEARCH);
//                } else {
//                    mViewModel.getObsStateSearch().postValue(StateSearch.SEARCHING);
//                }}
                }
            }

            @Override
            public void onSubmitSearch(String s) {
                if ("".equals(s.trim())) {
                    Toast.makeText(getApplicationContext(), "Bạn phải nhập từ khóa tìm kiếm", Toast.LENGTH_LONG).show();
                } else {
                    keySearch = s.toLowerCase();
                    keySearch = keySearch.trim();
                    mViewModel.fetchSearchResponse(MainActivity.API_KEY,keySearch);
                }

            }
        });
    }

    private void obServerSearchFilm() {
        mViewModel.getResultResponeLiveData().observe(this, new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {

            }
        });
    }
}
