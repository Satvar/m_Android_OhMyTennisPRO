package com.tech.cloudnausor.ohmytennispro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tech.cloudnausor.ohmytennispro.repository.ApiResponsitory;
import com.tech.cloudnausor.ohmytennispro.response.ForgotResponseData;

public class ForgotViewModel extends AndroidViewModel {
    private LiveData<ForgotResponseData> forgotResponseDataLiveData;
    public ForgotViewModel(@NonNull Application application, String email) {
        super(application);
        ApiResponsitory apiResponsitory = new ApiResponsitory();
        this.forgotResponseDataLiveData = apiResponsitory.getForgotDetails(email);
    }
    public LiveData<ForgotResponseData> getforgotResponseDataLiveData() {
        return forgotResponseDataLiveData;
    }

}
