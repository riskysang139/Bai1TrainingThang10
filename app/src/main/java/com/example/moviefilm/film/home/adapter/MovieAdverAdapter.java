package com.example.moviefilm.film.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.moviefilm.R;
import com.example.moviefilm.film.models.MovieAdverb;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MovieAdverAdapter extends RecyclerView.Adapter<MovieAdverAdapter.ViewHoder> {
    private List<MovieAdverb> movieAdvertList;
    private ViewPager2 viewPager2;
    private Context context;

    public MovieAdverAdapter(List<MovieAdverb> movieAdvertList, ViewPager2 viewPager2, Context context) {
        this.movieAdvertList = movieAdvertList;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    public void setMovieAdvertList(List<MovieAdverb> movieAdvertList) {
        this.movieAdvertList = movieAdvertList;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_adv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        MovieAdverb movieAdverb = movieAdvertList.get(position);
        Glide.with(context).load(movieAdverb.getImage()).into(holder.roundedImageView);
        if (position == movieAdvertList.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        if (movieAdvertList == null)
            return 0;
        else
            return movieAdvertList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.imgPhoto);
        }
    }

    private Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            movieAdvertList.addAll(movieAdvertList);
            notifyDataSetChanged();
        }
    };
}
