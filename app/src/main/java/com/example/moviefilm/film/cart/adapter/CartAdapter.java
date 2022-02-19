package com.example.moviefilm.film.cart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.moviefilm.R;
import com.example.moviefilm.roomdb.Film;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Film> filmListDB;
    private Context context;
    public OnCartClickListener onCartClickListener;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public static int numberChoice = 0;

    public CartAdapter(List<Film> filmListDB, Context context, OnCartClickListener onCartClickListener) {
        this.filmListDB = filmListDB;
        this.context = context;
        this.onCartClickListener = onCartClickListener;
    }

    public void setFilmListDB(List<Film> filmListDB) {
        this.filmListDB = filmListDB;
    }

    public List<Film> getFilmListDB() {
        return filmListDB;
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
        return filmListDB.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameFilm, txtReleaseDate, txtPrice;
        private ImageView imgFilm;
        private CheckBox cbCart;
        private SwipeRevealLayout swipeRevealLayout;
        private RelativeLayout rlInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameFilm = itemView.findViewById(R.id.title_film);
            txtReleaseDate = itemView.findViewById(R.id.txt_date);
            txtPrice = itemView.findViewById(R.id.txt_price);
            imgFilm = itemView.findViewById(R.id.img_cart_film);
            cbCart = itemView.findViewById(R.id.cb_film);
            swipeRevealLayout = itemView.findViewById(R.id.dl_layout);
            rlInfo = itemView.findViewById(R.id.rl_info);
        }

        @SuppressLint("SetTextI18n")
        private void initView(int position) {
            Film film = filmListDB.get(position);
            Glide.with(context).load(film.getFilmImage()).into(imgFilm);
            txtPrice.setText(film.getFilmRate() * 4 + " $");
            txtNameFilm.setText(film.getFilmName());
            txtReleaseDate.setText(film.getFilmReleaseDate());
            cbCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cbCart.isChecked()) {
                        numberChoice++;
                        onCartClickListener.onClickCart(position, true, numberChoice);
                    } else {
                        numberChoice--;
                        onCartClickListener.onClickCart(position, false, numberChoice);
                    }
                }
            });
            if (film.isChecked()) {
                cbCart.setChecked(true);
            } else
                cbCart.setChecked(false);
            viewBinderHelper.bind(swipeRevealLayout, String.valueOf(film.getFilmId()));

            swipeRevealLayout.setOnClickListener(view -> {

            });
            rlInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCartClickListener.onClickDetail(film.getFilmId()+"");
                }
            });

        }
    }

    public interface OnCartClickListener {
        void onClickCart(int position, boolean isChoose, int numberChoice);

        void onClickDetail(String id);
    }

}
