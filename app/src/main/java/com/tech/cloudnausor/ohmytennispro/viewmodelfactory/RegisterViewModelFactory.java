package com.tech.cloudnausor.ohmytennispro.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tech.cloudnausor.ohmytennispro.viewmodel.RegisterViewModel;


public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String firstName;
    private String secondName;
    private String email;
    private String pass;
    private String postalCode;
    private String postalCitiy;
    private String telephone;

    public RegisterViewModelFactory(Application mApplication, String firstName, String secondName, String email, String pass, String postalCode, String postalCitiy, String telephone) {
        this.mApplication = mApplication;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.pass = pass;
        this.postalCode = postalCode;
        this.postalCitiy = postalCitiy;
        this.telephone = telephone;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RegisterViewModel(this.mApplication, this.firstName, this.secondName,this.email,this.pass,this.postalCode,this.postalCitiy,this.telephone);
    }
}
