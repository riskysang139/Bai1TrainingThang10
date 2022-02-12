package com.example.bai1training.film.adapter;

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
import com.example.bai1training.R;
import com.example.bai1training.base.Converter;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.base.OnClickListener2;
import com.example.bai1training.film.MainActivity;
import com.example.bai1training.film.models.Results;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FilmAdapter2 extends RecyclerView.Adapter<FilmAdapter2.ViewHolder> {
    private List<Results> resultsList;
    private Context context;
    private OnClickListener2 onClickListener;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public FilmAdapter2(List<Results> resultsList, Context context, OnClickListener2 onClickListener) {
        this.resultsList = resultsList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Results results = resultsList.get(position);
        holder.txtTitle.setText(results.getTitle());
        holder.txtDate.setText(Converter.convertStringToDate(results.getReleaseDate()));
        holder.txtStar.setRating(Float.parseFloat(results.getVoteAverage()/2+""));
        Picasso.get().load(MainActivity.HEADER_URL_IMAGE + results.getPosterPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        RatingBar txtStar;
        TextView txtDate;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_film);
            txtTitle = itemView.findViewById(R.id.title_film);
            txtDate = itemView.findViewById(R.id.txt_date);
            relativeLayout = itemView.findViewById(R.id.rl_film);
            txtStar = itemView.findViewById(R.id.txt_rate);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickNowDetailFilm2(resultsList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
