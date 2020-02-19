package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OndemandCourseDataModel {
    @SerializedName("course")
    @Expose
    ArrayList<OndemandCourseModel> ondemandCourseModels;

    public ArrayList<OndemandCourseModel> getOndemandCourseModels() {
        return ondemandCourseModels;
    }

    public void setOndemandCourseModels(ArrayList<OndemandCourseModel> ondemandCourseModels) {
        this.ondemandCourseModels = ondemandCourseModels;
    }
}
