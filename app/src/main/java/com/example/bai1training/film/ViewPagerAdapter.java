package com.example.bai1training.film;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bai1training.film.home.HomeFragment;
import com.example.bai1training.user.UserFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return HomeFragment.getInstance();
//            case 1:
//                return UpComingFragment.getInstance();
//            case 2:
//                return TopRateFragment.getInstance();
            case 3:
                return UserFragment.getInstance();
            default:
                return HomeFragment.getInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
