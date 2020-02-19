package com.tech.cloudnausor.ohmytennispro.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennispro.model.RegisterModel;

public class RegisterResponseData {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private RegisterModel data;

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;

    public RegisterModel getData ()
    {
        return data;
    }

    public void setData (RegisterModel data)
    {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getIsSuccess ()
    {
        return isSuccess;
    }

    public void setIsSuccess (String isSuccess)
    {
        this.isSuccess = isSuccess;
    }
}
