package com.example.bai1training.film.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appxemphim.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdverAdapter extends RecyclerView.Adapter<com.example.appxemphim.TrangChu.Advertist.MovieAdverAdapter.ViewHoder> {
    private List<MovieAdver> movieAdverList;
    private ViewPager2 viewPager2;
    public MovieAdverAdapter(List<MovieAdver> movieAdverList, ViewPager2 viewPager2) {
        this.movieAdverList = movieAdverList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_adv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        MovieAdver movieAdver = movieAdverList.get(position);
        Picasso.get()
                .load(movieAdver.getImage())
                .into(holder.roundedImageView);
        if (position == movieAdverList.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return movieAdverList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.imgPhoto);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            movieAdverList.addAll(movieAdverList);
            notifyDataSetChanged();
        }
    };
}
