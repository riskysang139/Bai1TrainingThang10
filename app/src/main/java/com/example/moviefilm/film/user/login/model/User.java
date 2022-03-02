package com.example.moviefilm.film.user.login.model;

import android.util.Patterns;

public class User {
    private String email;
    private String passWord;
    private String passWordConfirm;

    public User(String email, String passWord, String passWordConfirm) {
        this.email = email;
        this.passWord = passWord;
        this.passWordConfirm = passWordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getPassWordConfirm() {
        return passWordConfirm;
    }

    public void setPassWordConfirm(String passWordConfirm) {
        this.passWordConfirm = passWordConfirm;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail().trim()).matches();
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getPassWord().length() > 5;
    }

    public boolean isPasswordAndConfirm() {
        return this.passWordConfirm.equals(this.passWord);
    }
}
