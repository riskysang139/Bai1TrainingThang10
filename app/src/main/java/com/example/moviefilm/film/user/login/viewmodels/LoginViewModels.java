package com.example.moviefilm.film.user.login.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviefilm.film.user.login.model.User;

public class LoginViewModels extends ViewModel {
    public MutableLiveData<String> emailAdd = new MutableLiveData<>();
    public MutableLiveData<String> passWord = new MutableLiveData<>();
    public MutableLiveData<String> passWordConfirm = new MutableLiveData<>();

    public MutableLiveData<User> userMutableLiveData;

    public MutableLiveData<User> getUserMutableLiveData() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void onClick(View view) {
        User user = new User(emailAdd.getValue(), passWord.getValue(), passWordConfirm.getValue());
        userMutableLiveData.setValue(user);
    }

}
