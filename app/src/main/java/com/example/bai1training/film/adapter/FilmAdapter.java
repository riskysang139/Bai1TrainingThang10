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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Results> resultsList;
    private Context context;
    private OnClickListener onClickListener;

    public FilmAdapter(List<Results> resultsList, Context context, OnClickListener onClickListener) {
        this.resultsList = resultsList;
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
        Results results = resultsList.get(position);
        holder.txtTitle.setText(results.getTitle());
        holder.txtDate.setText(convertStringToDate(results.getReleaseDate()));
        holder.txtStar.setText(results.getVoteAverage()+"");
        Glide.with(context).load(MainActivity.HEADER_URL_IMAGE+results.getBackdropPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle,txtStar;
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
    private String convertStringToDate(String date) {
        String displayDate = "";
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd/MM/yyyy");
            date = spf.format(newDate);
            displayDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayDate;
    }
}
