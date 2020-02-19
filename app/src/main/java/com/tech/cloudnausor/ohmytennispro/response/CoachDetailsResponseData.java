package com.tech.cloudnausor.ohmytennispro.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennispro.model.MyaccountGetData;

public class CoachDetailsResponseData {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private MyaccountGetData myaccountGetData;

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;

    public MyaccountGetData getData ()
    {
        return myaccountGetData;
    }

    public void setData (MyaccountGetData data)
    {
        this.myaccountGetData = data;
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