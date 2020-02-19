package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyaccountGetData {
    @SerializedName("coach_list")
            @Expose
    ArrayList<CoachDetailsModel> coachDetailsModelArrayList ;

    public ArrayList<CoachDetailsModel> getCoachDetailsModelArrayList() {
        return coachDetailsModelArrayList;
    }

    public void setCoachDetailsModelArrayList(ArrayList<CoachDetailsModel> coachDetailsModelArrayList) {
        this.coachDetailsModelArrayList = coachDetailsModelArrayList;
    }
}
