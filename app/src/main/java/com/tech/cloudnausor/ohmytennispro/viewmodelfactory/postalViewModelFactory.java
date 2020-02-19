package com.tech.cloudnausor.ohmytennispro.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tech.cloudnausor.ohmytennispro.viewmodel.PostalViewModel;


public class postalViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mExtra;
    public postalViewModelFactory(Application application, String extra) {
        mApplication = application;
        mExtra = extra;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostalViewModel(mApplication, mExtra);
    }
}
