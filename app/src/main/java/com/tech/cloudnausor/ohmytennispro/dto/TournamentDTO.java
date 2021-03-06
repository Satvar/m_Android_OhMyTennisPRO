package com.tech.cloudnausor.ohmytennispro.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TournamentDTO {
    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("data")
    @Expose
    TournamentData DataObject;
    @SerializedName("message")
    @Expose
    private String message;

    // Getter Methods

    public String getIsSuccess() {
        return isSuccess;
    }

    public TournamentData getData() {
        return DataObject;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setIsSuccess( String isSuccess ) {
        this.isSuccess = isSuccess;
    }

    public void setData( TournamentData dataObject ) {
        this.DataObject = dataObject;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
    public class TournamentData {
        @SerializedName("course")
        @Expose
        ArrayList<TournamentCourse> course = new ArrayList<TournamentCourse>();

        public ArrayList<TournamentCourse> getCourse() {
            return course;
        }

        public void setCourse(ArrayList<TournamentCourse> course) {
            this.course = course;
        }

    }
    public static class TournamentCourse{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("Tournamentname")
        @Expose
        private String Tournamentname;
        @SerializedName("from_date")
        @Expose
        private String from_date;
        @SerializedName("to_date")
        @Expose
        private String to_date;
        @SerializedName("Description")
        @Expose
        private String Description;
        @SerializedName("Location")
        @Expose
        private String Location;
        @SerializedName("Postalcode")
        @Expose
        private String Postalcode;
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


        // Getter Methods

        public String getId() {
            return id;
        }


        public String getTournamentname() {
            return Tournamentname;
        }

        public void setTournamentname(String tournamentname) {
            Tournamentname = tournamentname;
        }

        public String getFrom_date() {
            return from_date;
        }

        public String getTo_date() {
            return to_date;
        }

        public String getDescription() {
            return Description;
        }

        public String getLocation() {
            return Location;
        }

        public String getPostalcode() {
            return Postalcode;
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

        public void setFrom_date( String from_date ) {
            this.from_date = from_date;
        }

        public void setTo_date( String to_date ) {
            this.to_date = to_date;
        }
        public void setDescription( String Description ) {
            this.Description = Description;
        }
        public void setLocation( String Location ) {
            this.Location = Location;
        }
        public void setPostalcode( String Postalcode ) {
            this.Postalcode = Postalcode;
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
