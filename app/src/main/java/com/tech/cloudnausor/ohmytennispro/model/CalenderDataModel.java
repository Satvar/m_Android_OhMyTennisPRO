package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CalenderDataModel {
    @SerializedName("calender")
    @Expose
    ArrayList<CalenderModel> calenderModels;

    public ArrayList<CalenderModel> getCalenderModels() {
        return calenderModels;
    }

    public void setCalenderModels(ArrayList<CalenderModel> calenderModels) {
        this.calenderModels = calenderModels;
    }
}
