package com.example.moviefilm.searchFilm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;
import com.example.moviefilm.base.OnClickListener;
import com.example.moviefilm.base.customview.SearchActionBarView;
import com.example.moviefilm.databinding.ActivitySearchFilmBinding;
import com.example.moviefilm.detailFilm.DetailFilmActivity;
import com.example.moviefilm.film.MainActivity;
import com.example.moviefilm.film.adapter.FilmAdapter;
import com.example.moviefilm.film.models.ResultRespone;
import com.example.moviefilm.film.models.Results;

import java.util.ArrayList;
import java.util.List;

public class SearchFilmActivity extends AppCompatActivity implements OnClickListener {
    private ActivitySearchFilmBinding binding;
    private FilmAdapter searchFilmAdapter;
    private List<Results> resultSearchResultsList;
    private RecyclerView rcvSearchFilm;
    private SearchFilmViewModel mViewModel;
    private SearchActionBarView rlSearch;
    private RelativeLayout imgBack;
    private TextView txtNoData;
    public static final String KEY = "KEY";
    private String key="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_film);
        mViewModel = ViewModelProviders.of(this).get(SearchFilmViewModel.class);
        initView();
        getData();
        obServerData();
        onComeBack();
        initCallBackForSearchBar();
    }

    private void initView() {
        resultSearchResultsList = new ArrayList<>();
        rcvSearchFilm = findViewById(R.id.rcv_search_film);
        rlSearch = findViewById(R.id.sb_search_all);
        imgBack = findViewById(R.id.btn_back);
        txtNoData = findViewById(R.id.txt_no_data);
    }

    private void obServerData() {
        mViewModel.getResultResponeLiveData().observe(this, new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                resultSearchResultsList = resultRespone.getResults();
                if(resultSearchResultsList.size()==0)
                    txtNoData.setVisibility(View.VISIBLE);
                else
                    txtNoData.setVisibility(View.GONE);
                setUpAdapter();
            }
        });

    }

    private void setUpAdapter() {
        searchFilmAdapter = new FilmAdapter(resultSearchResultsList, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvSearchFilm.setLayoutManager(gridLayoutManager);
        rcvSearchFilm.setAdapter(searchFilmAdapter);
    }


    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(this, DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID, resultFilm.getId() + "");
        bundle.putString(DetailFilmActivity.KEY_FROM, DetailFilmActivity.FROM_SEARCH);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.getStringExtra(KEY) != null) {
            key = intent.getStringExtra(KEY);
            rlSearch.setTextSearch(key);
            mViewModel.fetchSearchResponse(MainActivity.API_KEY, key);
        }
    }

    private void onComeBack() {
        imgBack.setOnClickListener(v -> finish());
    }

    /**
     * callback của searchBar
     */
    private void initCallBackForSearchBar() {
        rlSearch.setSearchViewCallBack(new SearchActionBarView.SearchViewCallback() {
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
                    Toast.makeText(getBaseContext(),"Not Input Data",Toast.LENGTH_LONG).show();
                } else {
                    String keySearch = s.toLowerCase();
                    key = keySearch.trim();
                    mViewModel.fetchSearchResponse(MainActivity.API_KEY,key);
                    searchFilmAdapter.notifyDataSetChanged();
                }

            }
        });
    }
}
