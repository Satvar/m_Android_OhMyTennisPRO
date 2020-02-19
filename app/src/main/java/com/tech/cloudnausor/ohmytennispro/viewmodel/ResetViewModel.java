package com.tech.cloudnausor.ohmytennispro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tech.cloudnausor.ohmytennispro.repository.ApiResponsitory;
import com.tech.cloudnausor.ohmytennispro.response.ForgotResponseData;
import com.tech.cloudnausor.ohmytennispro.response.ResetResponseData;

public class ResetViewModel extends AndroidViewModel {
    private LiveData<ResetResponseData> resetResponseDataLiveData;
    public ResetViewModel(@NonNull Application application, String email,String password) {
        super(application);
        ApiResponsitory apiResponsitory = new ApiResponsitory();
        this.resetResponseDataLiveData = apiResponsitory.getResetDetails(email,password);
    }
    public LiveData<ResetResponseData> getforgotResponseDataLiveData() {
        return resetResponseDataLiveData;
    }

}
