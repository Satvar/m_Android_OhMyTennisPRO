package com.tech.cloudnausor.ohmytennispro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tech.cloudnausor.ohmytennispro.repository.ApiResponsitory;
import com.tech.cloudnausor.ohmytennispro.response.LoginResponseData;


public class LoginViewModel extends AndroidViewModel {

    public MutableLiveData<LoginResponseData> loginResponseDataLiveData;
    public LoginViewModel(@NonNull Application application, String email, String pass) {
        super(application);
        ApiResponsitory apiResponsitory = new ApiResponsitory();
        this.loginResponseDataLiveData = apiResponsitory.getloginDetails(email,pass);
    }
    public LiveData<LoginResponseData> getloginResponseDataLiveData() {
        if (loginResponseDataLiveData == null){
            loginResponseDataLiveData = new MutableLiveData<>();
        }
        return loginResponseDataLiveData;
    }

   }