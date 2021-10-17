package com.example.bai1training.searchFilm;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1training.R;
import com.example.bai1training.base.GridItemDecoration;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.base.OnClickVideoListener;
import com.example.bai1training.base.SearchActionBarView;
import com.example.bai1training.databinding.ActivitySearchFilmBinding;
import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.adapter.FilmAdapter;
import com.example.bai1training.film.models.ResultRespone;
import com.example.bai1training.film.models.Results;
import com.example.bai1training.film.viewmodels.FilmViewModels;

import java.util.ArrayList;
import java.util.List;

public class SearchFilmActivity extends AppCompatActivity implements OnClickListener {
    ActivitySearchFilmBinding binding;
    private FilmAdapter searchFilmAdapter;
    List<Results> resultSearchResultsList ;
    RecyclerView rcvSearchFilm;
    SearchFilmViewModel mViewModel;
    RelativeLayout rlSearch;
    ImageView imgBack;
    public static final String KEY="KEY";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_film);
        mViewModel= ViewModelProviders.of(this).get(SearchFilmViewModel.class);
        initView();
        getData();
        obServerData();
        onComeBack();
    }
    private void initView() {
        resultSearchResultsList=new ArrayList<>();
        rcvSearchFilm= findViewById(R.id.rcv_search_film);
        rlSearch=findViewById(R.id.rl_search_header);
        imgBack=findViewById(R.id.img_back);
    }

    private void obServerData(){
        mViewModel.getResultResponeLiveData().observe(this, new Observer<ResultRespone>() {
            @Override
            public void onChanged(ResultRespone resultRespone) {
                resultSearchResultsList=resultRespone.getResults();
                setUpAdapter();
            }
        });
    }
    private void setUpAdapter() {
        searchFilmAdapter = new FilmAdapter(resultSearchResultsList,this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rcvSearchFilm.addItemDecoration(new GridItemDecoration(46, 2));
        rcvSearchFilm.setLayoutManager(gridLayoutManager);
        rcvSearchFilm.setItemAnimator(new DefaultItemAnimator());
        rcvSearchFilm.setAdapter(searchFilmAdapter);
    }


    @Override
    public void onClickNowDetailFilm(Results resultFilm, int position) {
        Intent intent = new Intent(this, DetailFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DetailFilmActivity.ID,resultFilm.getId()+"");
        bundle.putString(DetailFilmActivity.KEY_FROM,DetailFilmActivity.FROM_SEARCH);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getData(){
        Intent intent=getIntent();
        if(intent.getStringExtra(KEY) !=null){
            String key = intent.getStringExtra(KEY);
            mViewModel.fetchSearchResponse(MainActivity.API_KEY,key);
        }
    }

    private void onComeBack() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
