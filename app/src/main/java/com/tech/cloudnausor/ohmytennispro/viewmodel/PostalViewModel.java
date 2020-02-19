package com.tech.cloudnausor.ohmytennispro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.tech.cloudnausor.ohmytennispro.repository.ApiResponsitory;
import com.tech.cloudnausor.ohmytennispro.response.RegisterPostalCodeList;


public class PostalViewModel extends AndroidViewModel {
    private ApiResponsitory apiResponsitory;
    private String aa;
    private LiveData<RegisterPostalCodeList> postalCodeListLiveData;

    public PostalViewModel(@NonNull Application application,String a) {
        super(application);
        apiResponsitory = new ApiResponsitory();
        aa = a;
//        this.postalCodeListLiveData = apiResponsitory.getPostalDtails(aa);
        postalCodeListLiveData.removeObservers((LifecycleOwner) this);
    }

    public LiveData<RegisterPostalCodeList> getPostalCodeListLiveData() {
        return postalCodeListLiveData;
    }



}
