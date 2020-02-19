package com.tech.cloudnausor.ohmytennispro.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tech.cloudnausor.ohmytennispro.viewmodel.ForgotViewModel;
public class ForgotCiewModelFactory implements ViewModelProvider.Factory
{
    private Application mApplication;
    private String email;

    public ForgotCiewModelFactory(Application mApplication,  String email, String pass) {
        this.mApplication = mApplication;
        this.email = email;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ForgotViewModel(this.mApplication,this.email);
    }
}
