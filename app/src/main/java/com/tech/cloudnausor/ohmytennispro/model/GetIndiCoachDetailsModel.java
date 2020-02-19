package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIndiCoachDetailsModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Coach_Id")
    @Expose
    private String Coach_Id;
    @SerializedName("Mode_of_Transport")
    @Expose
    private String Mode_of_Transport;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Price_min")
    @Expose
    private String Price_min;
    @SerializedName("Price_max")
    @Expose
    private String Price_max;
    @SerializedName("Technical_provided")
    @Expose
    private String Technical_provided;
    @SerializedName("Video")
    @Expose
    private String Video;
    @SerializedName("Plan")
    @Expose
    private String Plan;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("Location")
    @Expose
    private String Location;
    @SerializedName("Postalcode")
    @Expose
    private String Postalcode;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPostalcode() {
        return Postalcode;
    }

    public void setPostalcode(String postalcode) {
        Postalcode = postalcode;
    }

// Getter Methods

    public String getId() {
        return id;
    }

    public String getCoach_Id() {
        return Coach_Id;
    }

    public String getMode_of_Transport() {
        return Mode_of_Transport;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice_min() {
        return Price_min;
    }

    public String getPrice_max() {
        return Price_max;
    }

    public String getTechnical_provided() {
        return Technical_provided;
    }

    public String getVideo() {
        return Video;
    }

    public String getPlan() {
        return Plan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // Setter Methods 

    public void setId( String id ) {
        this.id = id;
    }

    public void setCoach_Id( String Coach_Id ) {
        this.Coach_Id = Coach_Id;
    }

    public void setMode_of_Transport( String Mode_of_Transport ) {
        this.Mode_of_Transport = Mode_of_Transport;
    }

    public void setDescription( String Description ) {
        this.Description = Description;
    }

    public void setPrice_min( String Price_min ) {
        this.Price_min = Price_min;
    }

    public void setPrice_max( String Price_max ) {
        this.Price_max = Price_max;
    }

    public void setTechnical_provided( String Technical_provided ) {
        this.Technical_provided = Technical_provided;
    }

    public void setVideo( String Video ) {
        this.Video = Video;
    }

    public void setPlan( String Plan ) {
        this.Plan = Plan;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt( String updatedAt ) {
        this.updatedAt = updatedAt;
    }
}
