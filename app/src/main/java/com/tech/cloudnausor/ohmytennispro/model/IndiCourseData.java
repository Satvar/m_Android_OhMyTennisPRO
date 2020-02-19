package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.http.Query;

public class IndiCourseData {
    @SerializedName("course")
    @Expose
    ArrayList<GetIndiCoachDetailsModel> indicouresedata;

    public ArrayList<GetIndiCoachDetailsModel> getIndicouresedata() {
        return indicouresedata;
    }

    public void setIndicouresedata(ArrayList<GetIndiCoachDetailsModel> indicouresedata) {
        this.indicouresedata = indicouresedata;
    }
}
