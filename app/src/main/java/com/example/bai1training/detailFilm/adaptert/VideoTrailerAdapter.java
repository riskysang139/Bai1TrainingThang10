package com.example.bai1training.detailFilm.adaptert;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1training.R;
import com.example.bai1training.base.OnClickListener;
import com.example.bai1training.base.OnClickVideoListener;
import com.example.bai1training.detailFilm.DetailFilmActivity;
import com.example.bai1training.detailFilm.MediaControllerView;
import com.example.bai1training.detailFilm.models.Video;
import com.example.bai1training.detailFilm.models.VideoResponse;

import java.util.List;

public class VideoTrailerAdapter extends RecyclerView.Adapter<VideoTrailerAdapter.ViewHolder> {
    private List<Video> videoList;
    private Context context;
    private OnClickVideoListener onClickVideoListener;

    public VideoTrailerAdapter(List<Video> videoList, Context context, OnClickVideoListener onClickVideoListener) {
        this.videoList = videoList;
        this.context = context;
        this.onClickVideoListener = onClickVideoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videoList.get(position);

        MediaController mediaController = new MediaController(context);
        String video_url=DetailFilmActivity.LINK_HEADER_YOUTUBE+video.getKey();
        Uri uri = Uri.parse(video_url);
        holder.videoView.setVideoURI(uri);
        mediaController.setAnchorView(holder.videoView);
        holder.videoView.setMediaController(mediaController);


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView btnStart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            btnStart = itemView.findViewById(R.id.btn_start_adapter);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickVideoListener.OnClickStart(getAdapterPosition());
                }
            });
            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickVideoListener.OnClickVideo(videoList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    private void setUpVideo(String video_url) {


    }
}
