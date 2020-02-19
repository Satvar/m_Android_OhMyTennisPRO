package com.tech.cloudnausor.ohmytennispro.model;

public class ClubAvabilityModelData {
    private String CoachId;
    private String Weekday;
    private String StartTime;
    private String EndTime;
    private String MaxCount;
    private String Price;
    private String Course;
    private String Id;



    public ClubAvabilityModelData(String coachId, String weekday, String startTime, String endTime, String maxCount, String price, String course, String id) {
        CoachId = coachId;
        Weekday = weekday;
        StartTime = startTime;
        EndTime = endTime;
        MaxCount = maxCount;
        Price = price;
        Course = course;
        Id = id;
    }

// Getter Methods

    public String getCoachId() {
        return CoachId;
    }

    public String getWeekday() {
        return Weekday;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getMaxCount() {
        return MaxCount;
    }

    public String getPrice() {
        return Price;
    }

    public String getCourse() {
        return Course;
    }

    public String getId() {
        return Id;
    }

    // Setter Methods

    public void setCoachId( String CoachId ) {
        this.CoachId = CoachId;
    }

    public void setWeekday( String Weekday ) {
        this.Weekday = Weekday;
    }

    public void setStartTime( String StartTime ) {
        this.StartTime = StartTime;
    }

    public void setEndTime( String EndTime ) {
        this.EndTime = EndTime;
    }

    public void setMaxCount( String MaxCount ) {
        this.MaxCount = MaxCount;
    }

    public void setPrice( String Price ) {
        this.Price = Price;
    }

    public void setCourse( String Course ) {
        this.Course = Course;
    }

    public void setId( String Id ) {
        this.Id = Id;
    }
}
