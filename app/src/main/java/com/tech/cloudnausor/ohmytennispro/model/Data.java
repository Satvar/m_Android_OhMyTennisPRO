package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {
    @SerializedName("city_list")
    @Expose
    ArrayList<postalCodeList> postalCodeListArrayList ;

    public ArrayList<postalCodeList> getPostalCodeListArrayList() {
        return postalCodeListArrayList;
    }

    public void setPostalCodeListArrayList(ArrayList<postalCodeList> postalCodeListArrayList) {
        this.postalCodeListArrayList = postalCodeListArrayList;
    }
}
