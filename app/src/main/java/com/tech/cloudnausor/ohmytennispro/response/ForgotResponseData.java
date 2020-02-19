package com.tech.cloudnausor.ohmytennispro.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotResponseData {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Object data;

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;

    public Object getData ()
    {
        return data;
    }

    public void setData (Object data)
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
