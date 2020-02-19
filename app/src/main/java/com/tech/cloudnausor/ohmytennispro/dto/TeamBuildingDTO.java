package com.tech.cloudnausor.ohmytennispro.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TeamBuildingDTO {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("data")
    @Expose
    TeamBuildingData DataObject;
    @SerializedName("message")
    @Expose
    private String message;

    // Getter Methods

    public String getIsSuccess() {
        return isSuccess;
    }

    public TeamBuildingData getData() {
        return DataObject;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setIsSuccess( String isSuccess ) {
        this.isSuccess = isSuccess;
    }

    public void setData( TeamBuildingData dataObject ) {
        this.DataObject = dataObject;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public class TeamBuildingData {
        @SerializedName("course")
        @Expose
        ArrayList<TeamBuildingCourse> course = new ArrayList<TeamBuildingCourse>();

        public ArrayList<TeamBuildingCourse> getCourse() {
            return course;
        }

        public void setCourse(ArrayList<TeamBuildingCourse> course) {
            this.course = course;
        }

    }

    public static class TeamBuildingCourse{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("Description")
        @Expose
        private String Description;
        @SerializedName("Mode_of_transport")
        @Expose
        private String Mode_of_transport;
        @SerializedName("Eventdetails")
        @Expose
        private String Eventdetails;
        @SerializedName("Photo")
        @Expose
        private String Photo;
        @SerializedName("filename")
        @Expose
        private String filename;
        @SerializedName("Price")
        @Expose
        private String Price;
        @SerializedName("Plan")
        @Expose
        private String Plan;
        @SerializedName("Coach_Id")
        @Expose
        private String Coach_Id;
        @SerializedName("Postalcode")
        @Expose
        private String Postalcode;



        // Getter Methods


        public String getPostalcode() {
            return Postalcode;
        }

        public void setPostalcode(String postalcode) {
            Postalcode = postalcode;
        }

        public String getId() {
            return id;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getMode_of_transport() {
            return Mode_of_transport;
        }

        public String getEventdetails() {
            return Eventdetails;
        }

        public String getPhoto() {
            return Photo;
        }

        public String getFilename() {
            return filename;
        }

        public String getPrice() {
            return Price;
        }

        public String getPlan() {
            return Plan;
        }

        public String getCoach_Id() {
            return Coach_Id;
        }

        // Setter Methods

        public void setId( String id ) {
            this.id = id;
        }

        public void setMode_of_transport( String Mode_of_transport ) {
            this.Mode_of_transport = Mode_of_transport;
        }

        public void setEventdetails( String Eventdetails ) {
            this.Eventdetails = Eventdetails;
        }

        public void setPhoto( String Photo ) {
            this.Photo = Photo;
        }

        public void setFilename( String filename ) {
            this.filename = filename;
        }

        public void setPrice( String Price ) {
            this.Price = Price;
        }

        public void setPlan( String Plan ) {
            this.Plan = Plan;
        }

        public void setCoach_Id( String Coach_Id ) {
            this.Coach_Id = Coach_Id;
        }
    }


}
