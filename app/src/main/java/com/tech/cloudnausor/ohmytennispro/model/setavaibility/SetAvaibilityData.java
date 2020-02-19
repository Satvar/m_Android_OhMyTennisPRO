package com.tech.cloudnausor.ohmytennispro.model.setavaibility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SetAvaibilityData {
    @SerializedName("availability")
            @Expose
    public ArrayList<SetAvaibilityTime> setAvaibilityTimes;

    public ArrayList<SetAvaibilityTime> getSetAvaibilityTimes() {
        return setAvaibilityTimes;
    }

    public void setSetAvaibilityTimes(ArrayList<SetAvaibilityTime> setAvaibilityTimes) {
        this.setAvaibilityTimes = setAvaibilityTimes;
    }
}
