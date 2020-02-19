package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetClubDataModel {
    @SerializedName("course")
    @Expose
    ArrayList<GetClubModel> getClubModels;
    @SerializedName("availablity")
    @Expose
    ArrayList<ClubAvabilityModelData> clubAvabilityModelData;

    public ArrayList<GetClubModel> getGetClubModels() {
        return getClubModels;
    }

    public void setGetClubModels(ArrayList<GetClubModel> getClubModels) {
        this.getClubModels = getClubModels;
    }

    public ArrayList<ClubAvabilityModelData> getClubAvabilityModelData() {
        return clubAvabilityModelData;
    }

    public void setClubAvabilityModelData(ArrayList<ClubAvabilityModelData> clubAvabilityModelData) {
        this.clubAvabilityModelData = clubAvabilityModelData;
    }
}
