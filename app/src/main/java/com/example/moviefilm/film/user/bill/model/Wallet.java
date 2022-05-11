package com.example.moviefilm.film.user.bill.model;

public class Wallet {

    private WalletResult Wallet;

    public WalletResult getWallet() {
        return Wallet;
    }

    public void setWallet(WalletResult wallet) {
        Wallet = wallet;
    }

    public static class WalletResult {
        private String totalMoney;

        public String getTotalMoney() {
            return totalMoney;
        }
        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }
    }
}
