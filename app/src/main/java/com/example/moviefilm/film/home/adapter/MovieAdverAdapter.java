package com.example.moviefilm.film.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.moviefilm.R;
import com.example.moviefilm.film.models.MovieAdver;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MovieAdverAdapter extends RecyclerView.Adapter<MovieAdverAdapter.ViewHoder> {
    private List<MovieAdver> movieAdverList;
    private ViewPager2 viewPager2;
    private Context context;
    public MovieAdverAdapter(List<MovieAdver> movieAdverList, ViewPager2 viewPager2, Context context) {
        this.movieAdverList = movieAdverList;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_adv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        MovieAdver movieAdver = movieAdverList.get(position);
        Glide.with(context).load(movieAdver.getImage()).into(holder.roundedImageView);
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
