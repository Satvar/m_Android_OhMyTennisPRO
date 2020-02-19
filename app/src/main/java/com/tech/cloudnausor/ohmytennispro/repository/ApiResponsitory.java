package com.tech.cloudnausor.ohmytennispro.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.response.ForgotResponseData;
import com.tech.cloudnausor.ohmytennispro.response.LoginResponseData;
import com.tech.cloudnausor.ohmytennispro.response.RegisterPostalCodeList;
import com.tech.cloudnausor.ohmytennispro.response.RegisterResponseData;
import com.tech.cloudnausor.ohmytennispro.response.ResetResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiResponsitory {
    private static final String TAG = ApiResponsitory.class.getSimpleName();
    private ApiInterface apiRequest;
     MutableLiveData<RegisterPostalCodeList> postalDetails = new MutableLiveData<>();
     MutableLiveData<RegisterResponseData> registerDetails = new MutableLiveData<>();
     MutableLiveData<LoginResponseData> loginDetails = new MutableLiveData<>();
     MutableLiveData<ForgotResponseData> forgotDetails = new MutableLiveData<>();
     MutableLiveData<ResetResponseData> resetDetails = new MutableLiveData<>();


    public ApiResponsitory() {
        apiRequest = ApiClient.getClient().create(ApiInterface.class);
    }

//    public LiveData<RegisterPostalCodeList> getPostalDtails( String a) {
//        apiRequest.getPostalField(a)
//                .enqueue(new Callback<RegisterPostalCodeList>() {
//                    @Override
//                    public void onResponse(Call<RegisterPostalCodeList> call, Response<RegisterPostalCodeList> response) {
//
//                        if (response.body() != null) {
//                            postalDetails.setValue(response.body());
//                        }
//
//                    }
//                    @Override
//                    public void onFailure(Call<RegisterPostalCodeList> call, Throwable t) {
//                        postalDetails.setValue(null);
//                    }
//                });
//        return postalDetails;
//    }

//    public LiveData<RegisterResponseData> getRegisterDetails( String firstName, String secondName, String email, String pass, String postalCode, String postalCitiy, String telephone) {
//        System.out.println(" nnnn -- > " + firstName);
//        apiRequest.getRegisterFieldDetails(firstName,secondName,email,pass,postalCode,postalCitiy,telephone)
//                .enqueue(new Callback<RegisterResponseData>() {
//                    @Override
//                    public void onResponse(Call<RegisterResponseData> call, Response<RegisterResponseData> response) {
//                        if (response.body() != null) {
//                            System.out.println("response.body() -->  "+response.body());
//                            registerDetails.postValue(response.body());
//                            System.out.println("response.body() -->"+response.body());
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<RegisterResponseData> call, Throwable t) {
//                        System.out.println("response.body() -->"+t);
//                        registerDetails.postValue(null);
//                    }
//                });
//        return registerDetails;
//    }


    public MutableLiveData<LoginResponseData> getloginDetails(String email, String password) {

        apiRequest.getLoginFieldDetails(email,password)
                .enqueue(new Callback<LoginResponseData>() {
                    @Override
                    public void onResponse(Call<LoginResponseData> call, Response<LoginResponseData> response) {
                        if (response.body() != null) {
                            loginDetails.postValue(response.body());
                            System.out.println("response.body() -->"+"");
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponseData> call, Throwable t) {
                        System.out.println("response.body() error -->"+t);
                        loginDetails.postValue(null);
                    }
                });
        return loginDetails;
    }

    public LiveData<ForgotResponseData> getForgotDetails(String email) {
        apiRequest.getForgotFieldDetails(email)
                .enqueue(new Callback<ForgotResponseData>() {
                    @Override
                    public void onResponse(Call<ForgotResponseData> call, Response<ForgotResponseData> response) {
                        if (response.body() != null) {
                            forgotDetails.setValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<ForgotResponseData> call, Throwable t) {
                        forgotDetails.setValue(null);
                    }
                });
        return forgotDetails;
    }

    public LiveData<ResetResponseData> getResetDetails(String email,String password) {
        apiRequest.getResetFieldDetails(email,password)
                .enqueue(new Callback<ResetResponseData>() {
                    @Override
                    public void onResponse(Call<ResetResponseData> call, Response<ResetResponseData> response) {
                        if (response.body() != null) {
                            resetDetails.setValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResetResponseData> call, Throwable t) {
                        resetDetails.setValue(null);
                    }
                });
        return resetDetails;
    }

}
