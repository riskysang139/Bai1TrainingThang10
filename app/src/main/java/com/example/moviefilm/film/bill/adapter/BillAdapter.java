package com.example.moviefilm.film.bill.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviefilm.R;
import com.example.moviefilm.film.cart.model.FilmBill;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    List<FilmBill> billList;
    Context context;

    public BillAdapter(List<FilmBill> billList, Context context) {
        this.billList = billList;
        this.context = context;
    }

    public void setBillList(List<FilmBill> billList) {
        this.billList = billList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.initView(position);
    }

    @Override
    public int getItemCount() {
        return billList.size() == 0 ? 0 : billList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTotalPayment, txtTotalProduct, txtDate;
        View viewDivided;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txt_date_buy);
            txtTotalPayment = itemView.findViewById(R.id.txt_total_payment);
            txtTotalProduct = itemView.findViewById(R.id.txt_total_product);
            viewDivided = itemView.findViewById(R.id.view_divide);
        }

        @SuppressLint("SetTextI18n")
        private void initView(int position) {
            txtDate.setSelected(true);
            txtTotalPayment.setSelected(true);
            FilmBill filmBill = billList.get(position);
            if (position >= 0)
                viewDivided.setVisibility(View.VISIBLE);
            else if (position == billList.size())
                viewDivided.setVisibility(View.GONE);
            else
                viewDivided.setVisibility(View.GONE);
            txtTotalPayment.setText(filmBill.getTotalPrice() +"$");
            txtDate.setText(filmBill.getDayBuy());
        }
    }
}
