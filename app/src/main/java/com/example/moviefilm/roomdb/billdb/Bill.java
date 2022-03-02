package com.example.moviefilm.roomdb.billdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bill_database")
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private int billId;

    @ColumnInfo(name = "total_film_buy")
    private int totalFilmBuy;

    @ColumnInfo(name = "total_payment")
    private float totalPayment;

    @ColumnInfo(name = "date_buy")
    private String dateBuy;

    @ColumnInfo(name = "id_user")
    private String idUser;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getTotalFilmBuy() {
        return totalFilmBuy;
    }

    public void setTotalFilmBuy(int totalFilmBuy) {
        this.totalFilmBuy = totalFilmBuy;
    }

    public float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(String dateBuy) {
        this.dateBuy = dateBuy;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Bill(int totalFilmBuy, float totalPayment, String dateBuy, String idUser) {
        this.totalFilmBuy = totalFilmBuy;
        this.totalPayment = totalPayment;
        this.dateBuy = dateBuy;
        this.idUser = idUser;
    }
}
