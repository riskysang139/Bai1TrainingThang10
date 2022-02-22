package com.example.moviefilm.film.home.detailFilm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviefilm.R;
import com.example.moviefilm.film.home.detailFilm.models.Cast;
import com.example.moviefilm.film.view.MainActivity;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<Cast> castList;
    private Context context;

    public CastAdapter(List<Cast> castList, Context context) {
        this.castList = castList;
        this.context = context;
    }

    public void setCastList(List<Cast> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_cast_film, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cast cast = castList.get(position);
        holder.txtNameCast.setText(cast.getOriginalName());
        Glide.with(context).load(MainActivity.HEADER_URL_IMAGE + cast.getProfilePath()).into(holder.imgCast);
    }

    @Override
    public int getItemCount() {
        return Math.min(castList.size(), 20);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCast;
        private TextView txtNameCast;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCast = itemView.findViewById(R.id.img_cast);
            txtNameCast = itemView.findViewById(R.id.txt_name_cast);
        }
    }
}
