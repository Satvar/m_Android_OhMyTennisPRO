package com.tech.cloudnausor.ohmytennispro.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennispro.model.OndemandCourseDataModel;

public class GetCoachCollectiveOnDemandResponseData {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private OndemandCourseDataModel ondemandCourseDataModel;

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

    public OndemandCourseDataModel getGetIndiCoachDetailsModel() {
        return ondemandCourseDataModel;
    }

    public void setGetIndiCoachDetailsModel(OndemandCourseDataModel indiCourseData) {
        this.ondemandCourseDataModel = indiCourseData;
    }

}
