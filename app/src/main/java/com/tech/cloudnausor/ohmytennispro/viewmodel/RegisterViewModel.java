package com.tech.cloudnausor.ohmytennispro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tech.cloudnausor.ohmytennispro.repository.ApiResponsitory;
import com.tech.cloudnausor.ohmytennispro.response.RegisterResponseData;


public class RegisterViewModel extends AndroidViewModel {

    private LiveData<RegisterResponseData> registerResponseDataLiveData;
    public RegisterViewModel(@NonNull Application application, String firstName,String secondName,String email,String pass,String postalCode,String postalCitiy,String telephone) {
        super(application);
        System.out.println("firstName -- >" + firstName);
        ApiResponsitory apiResponsitory = new ApiResponsitory();
//        this.registerResponseDataLiveData = apiResponsitory.getRegisterDetails( firstName,secondName,email,pass,postalCode,postalCitiy,telephone);
    }
    public LiveData<RegisterResponseData> getRegisterResponseDataLiveData() {
        return registerResponseDataLiveData;
    }


}
