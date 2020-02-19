package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalenderModel {

    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Availability")
    @Expose
    private String Availability;
    @SerializedName("allDay")
    @Expose
    private String allDay;
    @SerializedName("editable")
    @Expose
    private String editable;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("title")
    @Expose
    private String title;


    // Getter Methods 

    public String getId() {
        return Id;
    }

    public String getAvailability() {
        return Availability;
    }

    public String getAllDay() {
        return allDay;
    }

    public String getEditable() {
        return editable;
    }

    public String getColor() {
        return color;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }

    public String getTitle() {
        return title;
    }

    // Setter Methods 

    public void setId( String Id ) {
        this.Id = Id;
    }

    public void setAvailability( String Availability ) {
        this.Availability = Availability;
    }

    public void setAllDay( String allDay ) {
        this.allDay = allDay;
    }

    public void setEditable( String editable ) {
        this.editable = editable;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    public void setEnd( String end ) {
        this.end = end;
    }

    public void setStart( String start ) {
        this.start = start;
    }

    public void setTitle( String title ) {
        this.title = title;
    }
}
