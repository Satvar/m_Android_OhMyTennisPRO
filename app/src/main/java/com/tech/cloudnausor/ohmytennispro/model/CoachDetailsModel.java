package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class CoachDetailsModel {
    @SerializedName("Coach_ID")
    @Expose
    private String Coach_ID;
    @SerializedName("Coach_Fname")
    @Expose
    private String Coach_Fname;
    @SerializedName("Coach_Lname")
    @Expose
    private String Coach_Lname;
    @SerializedName("Coach_Email")
    @Expose
    private String Coach_Email;
    @SerializedName("Coach_Phone")
    @Expose
    private String Coach_Phone;
    @SerializedName("Coach_Password")
    @Expose
    private String Coach_Password;
    @SerializedName("Coach_Price")
    @Expose
    private String Coach_Price ;
    @SerializedName("Coach_PriceX10")
    @Expose
    private String Coach_PriceX10 ;
    @SerializedName("Coach_Aviailability")
    @Expose
    private String Coach_Aviailability;
    @SerializedName("Coach_Rayon")
    @Expose
    private String Coach_Rayon;
    @SerializedName("Coach_Services")
    @Expose
    private String Coach_Services;
    @SerializedName("Active_City")
    @Expose
    private String Active_City;
    @SerializedName("Coach_transport")
    @Expose
    private String Coach_transport;
    @SerializedName("Coach_City")
    @Expose
    private String Coach_City;
    @SerializedName("Coach_Image")
    @Expose
    private String Coach_Image;
    @SerializedName("Coach_Resume")
    @Expose
    private String Coach_Resume;
    @SerializedName("Coach_Status")
    @Expose
    private String Coach_Status;
    @SerializedName("Coach_Description")
    @Expose
    private String Coach_Description ;
    @SerializedName("Active_Status")
    @Expose
    private String Active_Status;
    @SerializedName("Availability_StartDate")
    @Expose
    private String Availability_StartDate;
    @SerializedName("Availability_EndDate")
    @Expose
    private String Availability_EndDate;
    @SerializedName("Coach_payment_type")
    @Expose
    private String Coach_payment_type;
    @SerializedName("Coach_Bank_Name")
    @Expose
    private String Coach_Bank_Name;
    @SerializedName("Branch_Code")
    @Expose
    private String Branch_Code;
    @SerializedName("Coach_Bank_ACCNum")
    @Expose
    private String Coach_Bank_ACCNum ;
    @SerializedName("Coach_Bank_City")
    @Expose
    private String Coach_Bank_City ;
    @SerializedName("User_type")
    @Expose
    private String User_type;
    @SerializedName("createdAt")
    @Expose
    private String createdAt ;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt ;
    @SerializedName("InstagramURL")
    @Expose
    private String InstagramURL ;
    @SerializedName("TwitterURL")
    @Expose
    private String TwitterURL ;
    @SerializedName("FacebookURL")
    @Expose
    private String FacebookURL ;

    public CoachDetailsModel() {

    }

    public CoachDetailsModel(String coach_Fname, String coach_Lname, String coach_Email, String coach_Phone, String coach_Price, String coach_PriceX10, String coach_Rayon, String coach_Services, String active_City, String coach_transport, String coach_Image, String coach_Resume, String coach_Description, String active_Status, String coach_payment_type, String coach_Bank_Name, String branch_Code, String coach_Bank_ACCNum, String coach_Bank_City, String user_type,String instagramURL,String twitterURL,String facebookURL) {
        Coach_Fname = coach_Fname;
        Coach_Lname = coach_Lname;
        Coach_Email = coach_Email;
        Coach_Phone = coach_Phone;
        Coach_Price = coach_Price;
        Coach_PriceX10 = coach_PriceX10;
        Coach_Rayon = coach_Rayon;
        Coach_Services = coach_Services;
        Coach_transport = coach_transport;
        Coach_Image = coach_Image;
        Coach_Resume = coach_Resume;
        Coach_Description = coach_Description;
        Active_Status = active_Status;
        Coach_payment_type = coach_payment_type;
        Coach_Bank_Name = coach_Bank_Name;
        Branch_Code = branch_Code;
        Coach_Bank_ACCNum = coach_Bank_ACCNum;
        Coach_Bank_City = coach_Bank_City;
        User_type = user_type;
        InstagramURL = instagramURL;
        TwitterURL = twitterURL;
        FacebookURL = facebookURL;
    }

    //    public CoachDetailsModel() {
//    }
//
//    public CoachDetailsModel(String coach_ID, String coach_Fname, String coach_Lname, String coach_Email, String coach_Phone, String coach_Password, String coach_Price, String coach_PriceX10, String coach_Rayon, String coach_Services, String active_City, String coach_transport, String coach_City, String coach_Image, String coach_Resume, String coach_Status, String coach_Description, String active_Status, String coach_payment_type, String coach_Bank_Name, String branch_Code, String coach_Bank_ACCNum, String coach_Bank_City, String user_type) {
//        Coach_ID = coach_ID;
//        Coach_Fname = coach_Fname;
//        Coach_Lname = coach_Lname;
//        Coach_Email = coach_Email;
//        Coach_Phone = coach_Phone;
//        Coach_Password = coach_Password;
//        Coach_Price = coach_Price;
//        Coach_PriceX10 = coach_PriceX10;
//        Coach_Rayon = coach_Rayon;
//        Coach_Services = coach_Services;
//        Active_City = active_City;
//        Coach_transport = coach_transport;
//        Coach_City = coach_City;
//        Coach_Image = coach_Image;
//        Coach_Resume = coach_Resume;
//        Coach_Status = coach_Status;
//        Coach_Description = coach_Description;
//        Active_Status = active_Status;
//        Coach_payment_type = coach_payment_type;
//        Coach_Bank_Name = coach_Bank_Name;
//        Branch_Code = branch_Code;
//        Coach_Bank_ACCNum = coach_Bank_ACCNum;
//        Coach_Bank_City = coach_Bank_City;
//        User_type = user_type;
//
//    }

// Getter Methods

    public String getCoach_ID() {
        return Coach_ID;
    }

    public String getCoach_Fname() {
        return Coach_Fname;
    }

    public String getCoach_Lname() {
        return Coach_Lname;
    }

    public String getCoach_Email() {
        return Coach_Email;
    }

    public String getCoach_Phone() {
        return Coach_Phone;
    }

    public String getCoach_Password() {
        return Coach_Password;
    }

    public String getCoach_Price() {
        return Coach_Price;
    }

    public String getCoach_PriceX10() {
        return Coach_PriceX10;
    }

    public String getCoach_Aviailability() {
        return Coach_Aviailability;
    }

    public String getCoach_Rayon() {
        return Coach_Rayon;
    }

    public String getCoach_Services() {
        return Coach_Services;
    }

    public String getActive_City() {
        return Active_City;
    }

    public String getCoach_transport() {
        return Coach_transport;
    }

    public String getCoach_City() {
        return Coach_City;
    }

    public String getCoach_Image() {
        return Coach_Image;
    }

    public String getCoach_Resume() {
        return Coach_Resume;
    }

    public String getCoach_Status() {
        return Coach_Status;
    }

    public String getCoach_Description() {
        return Coach_Description;
    }

    public String getActive_Status() {
        return Active_Status;
    }

    public String getAvailability_StartDate() {
        return Availability_StartDate;
    }

    public String getAvailability_EndDate() {
        return Availability_EndDate;
    }

    public String getCoach_payment_type() {
        return Coach_payment_type;
    }

    public String getCoach_Bank_Name() {
        return Coach_Bank_Name;
    }

    public String getBranch_Code() {
        return Branch_Code;
    }

    public String getCoach_Bank_ACCNum() {
        return Coach_Bank_ACCNum;
    }

    public String getCoach_Bank_City() {
        return Coach_Bank_City;
    }

    public String getUser_type() {
        return User_type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // Setter Methods

    public void setCoach_ID( String Coach_ID ) {
        this.Coach_ID = Coach_ID;
    }

    public void setCoach_Fname( String Coach_Fname ) {
        this.Coach_Fname = Coach_Fname;
    }

    public void setCoach_Lname( String Coach_Lname ) {
        this.Coach_Lname = Coach_Lname;
    }

    public void setCoach_Email( String Coach_Email ) {
        this.Coach_Email = Coach_Email;
    }

    public void setCoach_Phone( String Coach_Phone ) {
        this.Coach_Phone = Coach_Phone;
    }

    public void setCoach_Password( String Coach_Password ) {
        this.Coach_Password = Coach_Password;
    }

    public void setCoach_Price( String Coach_Price ) {
        this.Coach_Price = Coach_Price;
    }

    public void setCoach_PriceX10( String Coach_PriceX10 ) {
        this.Coach_PriceX10 = Coach_PriceX10;
    }

    public void setCoach_Aviailability( String Coach_Aviailability ) {
        this.Coach_Aviailability = Coach_Aviailability;
    }

    public void setCoach_Rayon( String Coach_Rayon ) {
        this.Coach_Rayon = Coach_Rayon;
    }

    public void setCoach_Services( String Coach_Services ) {
        this.Coach_Services = Coach_Services;
    }

    public void setActive_City( String Active_City ) {
        this.Active_City = Active_City;
    }

    public void setCoach_transport( String Coach_transport ) {
        this.Coach_transport = Coach_transport;
    }

    public void setCoach_City( String Coach_City ) {
        this.Coach_City = Coach_City;
    }

    public void setCoach_Image( String Coach_Image ) {
        this.Coach_Image = Coach_Image;
    }

    public void setCoach_Resume( String Coach_Resume ) {
        this.Coach_Resume = Coach_Resume;
    }

    public void setCoach_Status( String Coach_Status ) {
        this.Coach_Status = Coach_Status;
    }

    public void setCoach_Description( String Coach_Description ) {
        this.Coach_Description = Coach_Description;
    }

    public void setActive_Status( String Active_Status ) {
        this.Active_Status = Active_Status;
    }

    public void setAvailability_StartDate( String Availability_StartDate ) {
        this.Availability_StartDate = Availability_StartDate;
    }

    public void setAvailability_EndDate( String Availability_EndDate ) {
        this.Availability_EndDate = Availability_EndDate;
    }

    public void setCoach_payment_type( String Coach_payment_type ) {
        this.Coach_payment_type = Coach_payment_type;
    }

    public void setCoach_Bank_Name( String Coach_Bank_Name ) {
        this.Coach_Bank_Name = Coach_Bank_Name;
    }

    public void setBranch_Code( String Branch_Code ) {
        this.Branch_Code = Branch_Code;
    }

    public void setCoach_Bank_ACCNum( String Coach_Bank_ACCNum ) {
        this.Coach_Bank_ACCNum = Coach_Bank_ACCNum;
    }

    public void setCoach_Bank_City( String Coach_Bank_City ) {
        this.Coach_Bank_City = Coach_Bank_City;
    }

    public void setUser_type( String User_type ) {
        this.User_type = User_type;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt( String updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public String getInstagramURL() {
        return InstagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        InstagramURL = instagramURL;
    }

    public String getTwitterURL() {
        return TwitterURL;
    }

    public void setTwitterURL(String twitterURL) {
        TwitterURL = twitterURL;
    }

    public String getFacebookURL() {
        return FacebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        FacebookURL = facebookURL;
    }
}
