package com.example.moviefilm.film.user.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.moviefilm.R;
import com.example.moviefilm.film.detailFilm.view.DetailFilmActivity;
import com.example.moviefilm.roomdb.filmdb.Film;

import java.util.List;

public class HistoryFilmAdapter extends RecyclerView.Adapter<HistoryFilmAdapter.ViewHolder> {
    private List<Film> filmListDB;
    private final Context context;
    public OnHistoryFilmClickListener onCartClickListener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public static int numberChoice = 0;
    private String fromLoved = "";

    public HistoryFilmAdapter(List<Film> filmListDB, Context context, OnHistoryFilmClickListener onCartClickListener) {
        this.filmListDB = filmListDB;
        this.context = context;
        this.onCartClickListener = onCartClickListener;
    }

    public void setFilmListDBWithChecked(List<Film> films, String fromLoved) {
        this.filmListDB = films;
        notifyDataSetChanged();
        this.fromLoved = fromLoved;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.initView(position);
    }

    @Override
    public int getItemCount() {
        if (filmListDB == null)
            return 0;
        else
            return filmListDB.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameFilm, txtReleaseDate, txtPrice;
        private ImageView imgFilm;
        private CheckBox cbCart;
        private SwipeRevealLayout swipeRevealLayout;
        private LinearLayout llInfo;
        private RelativeLayout rlDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameFilm = itemView.findViewById(R.id.title_film);
            txtReleaseDate = itemView.findViewById(R.id.txt_date);
            txtPrice = itemView.findViewById(R.id.txt_price);
            imgFilm = itemView.findViewById(R.id.img_cart_film);
            cbCart = itemView.findViewById(R.id.cb_film);
            swipeRevealLayout = itemView.findViewById(R.id.dl_layout);
            llInfo = itemView.findViewById(R.id.ll_info);
            rlDelete = itemView.findViewById(R.id.layout_delete);
        }

        @SuppressLint("SetTextI18n")
        private void initView(int position) {
            Film film = filmListDB.get(position);
            Glide.with(context).load(film.getFilmImage()).into(imgFilm);
            txtPrice.setText(film.getFilmRate() * 4 + " $");
            txtNameFilm.setText(film.getFilmName());
            txtReleaseDate.setText(film.getFilmReleaseDate());
            cbCart.setOnClickListener(view -> {
                if (cbCart.isChecked()) {
                    numberChoice++;
                    onCartClickListener.onClickCart(position, true, numberChoice);
                } else {
                    numberChoice--;
                    onCartClickListener.onClickCart(position, false, numberChoice);
                }
            });
            if (film.isChecked()) {
                cbCart.setChecked(true);
            } else {
                cbCart.setChecked(false);
            }
            viewBinderHelper.bind(swipeRevealLayout, String.valueOf(film.getFilmId()));
//            swipeRevealLayout.setOnClickListener(view -> {
//                onCartClickListener.onCLickDelete(film);
//            });
            rlDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filmListDB.remove(position);
                    onCartClickListener.onCLickDelete(film, position);
                }
            });
            llInfo.setOnClickListener(view -> onCartClickListener.onClickDetail(film.getFilmId() + ""));

            if (film.isChecked2() && fromLoved.equals(DetailFilmActivity.FROM_LOVED))
                cbCart.setVisibility(View.GONE);
            else
                cbCart.setVisibility(View.VISIBLE);
        }
    }

    public interface OnHistoryFilmClickListener {
        void onClickCart(int position, boolean isChoose, int numberChoice);

        void onClickDetail(String id);

        void onCLickDelete(Film film, int position);
    }

}
