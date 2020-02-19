package com.tech.cloudnausor.ohmytennispro.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennispro.model.CalenderDataModel;
import com.tech.cloudnausor.ohmytennispro.model.CalenderModel;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;

import java.util.ArrayList;

public class CalenderResponseData {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private CalenderDataModel calenderDataModel;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CalenderDataModel getGetIndiCoachDetailsModel() {
        return calenderDataModel;
    }

    public void setGetIndiCoachDetailsModel(CalenderDataModel indiCourseData) {
        this.calenderDataModel = indiCourseData;
    }
}
