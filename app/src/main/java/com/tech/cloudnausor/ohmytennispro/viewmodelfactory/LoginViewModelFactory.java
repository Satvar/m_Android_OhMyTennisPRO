package com.tech.cloudnausor.ohmytennispro.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tech.cloudnausor.ohmytennispro.viewmodel.LoginViewModel;


public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String email;
    private String pass;


    public LoginViewModelFactory(Application mApplication,  String email, String pass) {
        this.mApplication = mApplication;
        this.email = email;
        this.pass = pass;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(this.mApplication,this.email,this.pass);
    }
}