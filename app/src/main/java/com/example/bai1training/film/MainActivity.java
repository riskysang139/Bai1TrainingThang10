package com.example.bai1training.film;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bai1training.R;
import com.example.bai1training.databinding.ActivityMainBinding;
import com.example.bai1training.film.viewmodels.FilmViewModels;
import com.example.bai1training.searchFilm.SearchFilmActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FilmViewModels filmViewModels;
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;
    ViewPagerAdapter adapter;
    public static final String API_KEY ="42ccbde96fdbec040787f337977a26da";
    public static final String HEADER_URL_IMAGE ="https://image.tmdb.org/t/p/w500";
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        initView();
        setUpViewpager();
        goToSearch();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewPager);
        relativeLayout=findViewById(R.id.rl_shop_detail_bar);
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
                        bottomNavigationView.getMenu().findItem(R.id.navigation_popular).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_upcoming).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_top_rate).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_now_playing).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_popular:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_upcoming:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_top_rate:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_now_playing:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });
        viewPager.setOffscreenPageLimit(4);
    }

    private void goToSearch(){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SearchFilmActivity.class);
                startActivity(intent);
            }
        });

    }
}