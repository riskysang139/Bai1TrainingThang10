package com.example.moviefilm.film.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviefilm.R;
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.base.OnClickListener;
import com.example.moviefilm.film.view.MainActivity;
import com.example.moviefilm.film.models.Results;

import java.text.DecimalFormat;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Results> resultsList;
    private Context context;
    private OnClickListener onClickListener;
    private static final int VIEW_TYPE_SECTION = 1;

    public FilmAdapter(List<Results> resultsList, Context context, OnClickListener onClickListener) {
        this.resultsList = resultsList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setResultsList(List<Results> resultsList) {
        this.resultsList = resultsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_SECTION)
            return new ViewHolderHorizontal(layoutInflater.inflate(R.layout.item_film_horizontal, parent, false));
        else
            return new ViewHolder(layoutInflater.inflate(R.layout.item_films, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_SECTION) {
            ViewHolderHorizontal viewHolder = (ViewHolderHorizontal) holder;
            Results results = resultsList.get(position);
            viewHolder.txtTitle.setText(results.getTitle());
            viewHolder.txtDate.setText(Converter.convertStringToDate(results.getReleaseDate()));
            viewHolder.txtStar.setRating(Float.parseFloat(results.getVoteAverage() / 2 + ""));
            Glide.with(context).load(MainActivity.HEADER_URL_IMAGE + results.getPosterPath()).into(viewHolder.imageView);
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            Results results = resultsList.get(position);
            viewHolder.txtTitle.setText(results.getTitle());
            viewHolder.txtDate.setText(Converter.convertStringToDate(results.getReleaseDate()));
            viewHolder.txtStar.setText(Converter.df.format(results.getVoteAverage() / 2));
            Glide.with(context).load(MainActivity.HEADER_URL_IMAGE + results.getPosterPath()).into(viewHolder.imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Results results = resultsList.get(position);
        return results.getType();
    }

    @Override
    public int getItemCount() {
        if (resultsList == null)
            return 0;
        else
            return resultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle, txtStar;
        TextView txtDate;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_film);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
            relativeLayout = itemView.findViewById(R.id.rl_film);
            txtStar = itemView.findViewById(R.id.txt_rate);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickNowDetailFilm(resultsList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public class ViewHolderHorizontal extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        RatingBar txtStar;
        TextView txtDate;
        RelativeLayout relativeLayout;

        public ViewHolderHorizontal(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_film);
            txtTitle = itemView.findViewById(R.id.title_film);
            txtDate = itemView.findViewById(R.id.txt_date);
            relativeLayout = itemView.findViewById(R.id.rl_film);
            txtStar = itemView.findViewById(R.id.txt_rate);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickNowDetailFilm(resultsList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

}
