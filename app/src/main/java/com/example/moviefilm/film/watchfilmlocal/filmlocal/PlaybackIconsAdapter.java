package com.example.moviefilm.film.watchfilmlocal.filmlocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;

import org.w3c.dom.Text;

import java.util.List;

public class PlaybackIconsAdapter extends RecyclerView.Adapter<PlaybackIconsAdapter.ViewHoler> {
    private List<IconModel> iconModelList;
    private Context context;
    private setOnCLickListener setOnCLickListener;

    public PlaybackIconsAdapter(List<IconModel> iconModelList, Context context) {
        this.iconModelList = iconModelList;
        this.context = context;
    }

    public void setSetOnCLickListener(PlaybackIconsAdapter.setOnCLickListener setOnCLickListener) {
        this.setOnCLickListener = setOnCLickListener;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_play_back, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        ViewHoler viewHolder = holder;
        IconModel iconModel = iconModelList.get(position);
        viewHolder.onBind(iconModel, position);
    }

    @Override
    public int getItemCount() {
        return iconModelList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView txtName;
        private RelativeLayout view;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_ic);
            txtName = itemView.findViewById(R.id.txt_name);
            view = itemView.findViewById(R.id.view);
        }

        public void onBind(IconModel iconModel, int position) {
            txtName.setText(iconModel.getIconTitle());
            imgIcon.setImageResource(iconModel.getImageView());
            view.setOnClickListener(view -> {
                setOnCLickListener.onClick(iconModel, position);

            });
        }
    }

    public interface setOnCLickListener{
        void onClick(IconModel iconModel, int position);
    }
}
