package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDataDetails {
    @SerializedName("booking_Id")
    @Expose
    private String booking_Id;
    @SerializedName("Coach_ID")
    @Expose
    private String Coach_ID;
    @SerializedName("user_Id")
    @Expose
    private String user_Id;
    @SerializedName("payment_Id")
    @Expose
    private String payment_Id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("bookingStart")
    @Expose
    private String bookingStart;
    @SerializedName("bookingEnd")
    @Expose
    private String bookingEnd;
    @SerializedName("bookingCourse")
    @Expose
    private String bookingCourse;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("discount_club")
    @Expose
    private String discount_club;
    @SerializedName("coach_Email")
    @Expose
    private String coach_Email;
    @SerializedName("user_Email")
    @Expose
    private String user_Email;
    @SerializedName("coach_Name")
    @Expose
    private String coach_Name;
    @SerializedName("user_Name")
    @Expose
    private String user_Name;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("totalHrs")
    @Expose
    private String totalHrs;
    @SerializedName("UsedHrs")
    @Expose
    private String UsedHrs;
    @SerializedName("BookingTime")
    @Expose
    private String BookingTime = null;
    @SerializedName("NoofPeople")
    @Expose
    private String NoofPeople = null;
    @SerializedName("firstName")
    @Expose
    private String firstName = null;
    @SerializedName("lastName")
    @Expose
    private String lastName = null;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

// Getter Methods

    public String getBooking_Id() {
        return booking_Id;
    }

    public String getCoach_ID() {
        return Coach_ID;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public String getPayment_Id() {
        return payment_Id;
    }

    public String getStatus() {
        return status;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingStart() {
        return bookingStart;
    }

    public String getBookingEnd() {
        return bookingEnd;
    }

    public String getBookingCourse() {
        return bookingCourse;
    }

    public String getAmount() {
        return amount;
    }

    public String getDiscount_club() {
        return discount_club;
    }

    public String getCoach_Email() {
        return coach_Email;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public String getCoach_Name() {
        return coach_Name;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getTotalHrs() {
        return totalHrs;
    }

    public String getUsedHrs() {
        return UsedHrs;
    }

    public String getBookingTime() {
        return BookingTime;
    }

    public String getNoofPeople() {
        return NoofPeople;
    }

    // Setter Methods 

    public void setBooking_Id( String booking_Id ) {
        this.booking_Id = booking_Id;
    }

    public void setCoach_ID( String Coach_ID ) {
        this.Coach_ID = Coach_ID;
    }

    public void setUser_Id( String user_Id ) {
        this.user_Id = user_Id;
    }

    public void setPayment_Id( String payment_Id ) {
        this.payment_Id = payment_Id;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setBookingDate( String bookingDate ) {
        this.bookingDate = bookingDate;
    }

    public void setBookingStart( String bookingStart ) {
        this.bookingStart = bookingStart;
    }

    public void setBookingEnd( String bookingEnd ) {
        this.bookingEnd = bookingEnd;
    }

    public void setBookingCourse( String bookingCourse ) {
        this.bookingCourse = bookingCourse;
    }

    public void setAmount( String amount ) {
        this.amount = amount;
    }

    public void setDiscount_club( String discount_club ) {
        this.discount_club = discount_club;
    }

    public void setCoach_Email( String coach_Email ) {
        this.coach_Email = coach_Email;
    }

    public void setUser_Email( String user_Email ) {
        this.user_Email = user_Email;
    }

    public void setCoach_Name( String coach_Name ) {
        this.coach_Name = coach_Name;
    }

    public void setUser_Name( String user_Name ) {
        this.user_Name = user_Name;
    }

    public void setPaymentStatus( String paymentStatus ) {
        this.paymentStatus = paymentStatus;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt( String updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public void setTotalHrs( String totalHrs ) {
        this.totalHrs = totalHrs;
    }

    public void setUsedHrs( String UsedHrs ) {
        this.UsedHrs = UsedHrs;
    }

    public void setBookingTime( String BookingTime ) {
        this.BookingTime = BookingTime;
    }

    public void setNoofPeople( String NoofPeople ) {
        this.NoofPeople = NoofPeople;
    }
}
