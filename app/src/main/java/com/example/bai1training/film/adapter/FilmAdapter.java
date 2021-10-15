package com.example.bai1training.film.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bai1training.R;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.models.Results;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Results> nowPlayingMoviesList;
    private Context context;
    private OnClickListener onClickListener;

    public FilmAdapter(List<Results> nowPlayingMoviesList, Context context, OnClickListener onClickListener) {
        this.nowPlayingMoviesList = nowPlayingMoviesList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_films, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Results nowPlayingMovies = nowPlayingMoviesList.get(position);
        holder.txtTitle.setText(nowPlayingMovies.getTitle());
        holder.txtCategory.setText(nowPlayingMovies.getReleaseDate());
        Glide.with(context).load(MainActivity.HEADER_URL_IMAGE+nowPlayingMovies.getBackdropPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return nowPlayingMoviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtCategory;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageDacSac);
            txtTitle = itemView.findViewById(R.id.textDacSac);
            txtCategory = itemView.findViewById(R.id.textCategory);
            relativeLayout = itemView.findViewById(R.id.rl_film);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickNowDetailFilm(nowPlayingMoviesList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
