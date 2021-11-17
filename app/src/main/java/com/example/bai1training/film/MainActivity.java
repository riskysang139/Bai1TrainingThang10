package com.example.bai1training.film;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bai1training.R;
import com.example.bai1training.base.SearchActionBarView;
import com.example.bai1training.databinding.ActivityMainBinding;
import com.example.bai1training.searchFilm.SearchFilmActivity;
import com.example.bai1training.searchFilm.SearchFilmViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;
    ViewPagerAdapter adapter;
    public static final String API_KEY = "42ccbde96fdbec040787f337977a26da";
    public static final String HEADER_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    SearchActionBarView searchActionBarView;
    public static String keySearch = "";
    SearchFilmViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(SearchFilmViewModel.class);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        initView();
        setUpViewpager();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewPager);
//        searchActionBarView = findViewById(R.id.sb_search_all);
    }

    private void setUpViewpager() {
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_upcoming).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_top_rate).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_user).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_upcoming:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_top_rate:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_user:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });
        viewPager.setOffscreenPageLimit(4);
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
                    goToSearch(keySearch);
                }

            }
        });
    }

    private void goToSearch(String key) {
        Intent intent = new Intent(this, SearchFilmActivity.class);
        intent.putExtra(SearchFilmActivity.KEY, key);
        startActivity(intent);
    }
}