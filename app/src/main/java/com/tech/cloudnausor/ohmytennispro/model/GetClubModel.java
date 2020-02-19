package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetClubModel {

    @SerializedName("Course_Id")
    @Expose
    private String Course_Id;
    @SerializedName("Coach_Id")
    @Expose
    private String Coach_Id;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Price")
    @Expose
    private String Price;
    @SerializedName("Photo")
    @Expose
    private String Photo;
    @SerializedName("Mode_of_Transport")
    @Expose
    private String Mode_of_Transport;
    @SerializedName("Technical_Provided")
    @Expose
    private String Technical_Provided;
    @SerializedName("Video")
    @Expose
    private String Video;
    @SerializedName("Plan")
    @Expose
    private String Plan;
    @SerializedName("CreatedAt")
    @Expose
    private String CreatedAt;
    @SerializedName("Club_Name")
    @Expose
    private String Club_Name;
    @SerializedName("Place")
    @Expose
    private String Place;
    @SerializedName("MaxCount")
    @Expose
    private String MaxCount;
    @SerializedName("Postalcode")
    @Expose
    private String Postalcode;
    @SerializedName("availablity")
    @Expose
    ArrayList<ClubAvabilityModelData> clubAvabilityModelData;



    public GetClubModel(String coach_Id, String description, String price, String photo, String mode_of_Transport,
                        String technical_Provided, String video, String plan,
                         String club_Name, String place, String maxCount, ArrayList<ClubAvabilityModelData> clubAvabilityModelData) {
        Coach_Id = coach_Id;
        Description = description;
        Price = price;
        Photo = photo;
        Mode_of_Transport = mode_of_Transport;
        Technical_Provided = technical_Provided;
        Video = video;
        Plan = plan;
        Club_Name = club_Name;
        Place = place;
        MaxCount = maxCount;
        this.clubAvabilityModelData = clubAvabilityModelData;
    }
// Getter Methods

    public String getPostalcode() {
        return Postalcode;
    }

    public void setPostalcode(String postalcode) {
        Postalcode = postalcode;
    }

    public ArrayList<ClubAvabilityModelData> getClubAvabilityModelData() {
        return clubAvabilityModelData;
    }

    public void setClubAvabilityModelData(ArrayList<ClubAvabilityModelData> clubAvabilityModelData) {
        this.clubAvabilityModelData = clubAvabilityModelData;
    }

    public String getCourse_Id() {
        return Course_Id;
    }

    public String getCoach_Id() {
        return Coach_Id;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

    public String getPhoto() {
        return Photo;
    }

    public String getMode_of_Transport() {
        return Mode_of_Transport;
    }

    public String getTechnical_Provided() {
        return Technical_Provided;
    }

    public String getVideo() {
        return Video;
    }

    public String getPlan() {
        return Plan;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public String getClub_Name() {
        return Club_Name;
    }

    public String getPlace() {
        return Place;
    }

    public String getMaxCount() {
        return MaxCount;
    }

    // Setter Methods 

    public void setCourse_Id( String Course_Id ) {
        this.Course_Id = Course_Id;
    }

    public void setCoach_Id( String Coach_Id ) {
        this.Coach_Id = Coach_Id;
    }

    public void setDescription( String Description ) {
        this.Description = Description;
    }

    public void setPrice( String Price ) {
        this.Price = Price;
    }

    public void setPhoto( String Photo ) {
        this.Photo = Photo;
    }

    public void setMode_of_Transport( String Mode_of_Transport ) {
        this.Mode_of_Transport = Mode_of_Transport;
    }

    public void setTechnical_Provided( String Technical_Provided ) {
        this.Technical_Provided = Technical_Provided;
    }

    public void setVideo( String Video ) {
        this.Video = Video;
    }

    public void setPlan( String Plan ) {
        this.Plan = Plan;
    }

    public void setCreatedAt( String CreatedAt ) {
        this.CreatedAt = CreatedAt;
    }

    public void setClub_Name( String Club_Name ) {
        this.Club_Name = Club_Name;
    }

    public void setPlace( String Place ) {
        this.Place = Place;
    }

    public void setMaxCount( String MaxCount ) {
        this.MaxCount = MaxCount;
    }
}
