package com.example.moviefilm.film.watchfilmlocal.adapter;

import android.annotation.SuppressLint;
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
import com.example.moviefilm.base.Converter;
import com.example.moviefilm.film.watchfilmlocal.model.MediaFile;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class WatchFilmLocalAdapter extends RecyclerView.Adapter<WatchFilmLocalAdapter.ViewHoder> {
    private List<MediaFile> mediaFileList;
    private Context context;
    private setOnClickListener setOnClickListener;

    public WatchFilmLocalAdapter(List<MediaFile> mediaFileList, Context context) {
        this.mediaFileList = mediaFileList;
        this.context = context;
    }

    public void setSetOnClickListener(WatchFilmLocalAdapter.setOnClickListener setOnClickListener) {
        this.setOnClickListener = setOnClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMediaFileList(List<MediaFile> mediaFileList) {
        this.mediaFileList = mediaFileList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WatchFilmLocalAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder_media, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchFilmLocalAdapter.ViewHoder holder, int position) {
        MediaFile mediaFile = mediaFileList.get(position);
        holder.onBind(mediaFile);
    }

    @Override
    public int getItemCount() {
        return mediaFileList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private RoundedImageView imgMedia;
        private ImageView btnEdit;
        private TextView txtTitle, txtSize, txtDateAdded, txtTimeDuration;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            imgMedia = itemView.findViewById(R.id.img_cart_film);
            txtTitle = itemView.findViewById(R.id.title_film);
            txtSize = itemView.findViewById(R.id.txt_size);
            txtDateAdded = itemView.findViewById(R.id.txt_date);
            txtTimeDuration = itemView.findViewById(R.id.txt_time_duration);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
        public void onBind(MediaFile mediaFile) {
            txtTitle.setText(mediaFile.getDisplayName());
            txtSize.setText(android.text.format.Formatter.formatFileSize(context,Long.parseLong(mediaFile.getSize())));
            txtDateAdded.setText(Converter.convertTimeStampToDate(Long.parseLong(mediaFile.getDateAdded())));
            txtTimeDuration.setText(Converter.convertTime(Long.parseLong(mediaFile.getDuration())));
            Glide.with(context).load(mediaFile.getPath()).into(imgMedia);
            btnEdit.setOnClickListener(view -> {
                setOnClickListener.onClick(mediaFile);
            });
        }
    }

    public interface setOnClickListener {
        void onClick(MediaFile mediaFile);
    }
}
