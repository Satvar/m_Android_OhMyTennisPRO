package com.tech.cloudnausor.ohmytennispro.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingRequestStatusResponse {

    @SerializedName("isSuccess")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    // Getter Methods

    public String getIsSuccess() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setIsSuccess( String status ) {
        this.status = status;
    }

    public void setMessage( String message ) {
        this.message = message;
    }


}
