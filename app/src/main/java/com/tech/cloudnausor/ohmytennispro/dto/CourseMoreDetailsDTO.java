package com.tech.cloudnausor.ohmytennispro.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CourseMoreDetailsDTO {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("data")
    @Expose
    BookingArray bookingArray;
    @SerializedName("message")
    @Expose
    private String message;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public BookingArray getBookingArray() {
        return bookingArray;
    }

    public void setBookingArray(BookingArray bookingArray) {
        this.bookingArray = bookingArray;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class BookingArray{

         @SerializedName("booking")
         @Expose
         ArrayList<Booking> bookings ;

        public ArrayList<Booking> getBookings() {
            return bookings;
        }

        public void setBookings(ArrayList<Booking> bookings) {
            this.bookings = bookings;
        }
    }

     public static class Booking{

         @SerializedName("Id")
         @Expose
         private String Id;
         @SerializedName("booking_Id")
         @Expose
         private String booking_Id;
         @SerializedName("Coach_Id")
         @Expose
         private String Coach_Id;
         @SerializedName("User_Id")
         @Expose
         private String User_Id;
         @SerializedName("Course")
         @Expose
         private String Course;
         @SerializedName("Name_of_company")
         @Expose
         private String Name_of_company;
         @SerializedName("Email")
         @Expose
         private String Email;
         @SerializedName("Mobile")
         @Expose
         private String Mobile;
         @SerializedName("Date")
         @Expose
         private String Date;
         @SerializedName("Address")
         @Expose
         private String Address = null;
         @SerializedName("Postalcode")
         @Expose
         private String Postalcode = null;
         @SerializedName("Level")
         @Expose
         private String Level;
         @SerializedName("Number_of_person")
         @Expose
         private String Number_of_person;


         // Getter Methods 

         public String getId() {
             return Id;
         }

         public String getBooking_Id() {
             return booking_Id;
         }

         public String getCoach_Id() {
             return Coach_Id;
         }

         public String getUser_Id() {
             return User_Id;
         }

         public String getCourse() {
             return Course;
         }

         public String getName_of_company() {
             return Name_of_company;
         }

         public String getEmail() {
             return Email;
         }

         public String getMobile() {
             return Mobile;
         }

         public String getDate() {
             return Date;
         }

         public String getAddress() {
             return Address;
         }

         public String getPostalcode() {
             return Postalcode;
         }

         public String getLevel() {
             return Level;
         }

         public String getNumber_of_person() {
             return Number_of_person;
         }

         // Setter Methods 

         public void setId( String Id ) {
             this.Id = Id;
         }

         public void setBooking_Id( String booking_Id ) {
             this.booking_Id = booking_Id;
         }

         public void setCoach_Id( String Coach_Id ) {
             this.Coach_Id = Coach_Id;
         }

         public void setUser_Id( String User_Id ) {
             this.User_Id = User_Id;
         }

         public void setCourse( String Course ) {
             this.Course = Course;
         }

         public void setName_of_company( String Name_of_company ) {
             this.Name_of_company = Name_of_company;
         }

         public void setEmail( String Email ) {
             this.Email = Email;
         }

         public void setMobile( String Mobile ) {
             this.Mobile = Mobile;
         }

         public void setDate( String Date ) {
             this.Date = Date;
         }

         public void setAddress( String Address ) {
             this.Address = Address;
         }

         public void setPostalcode( String Postalcode ) {
             this.Postalcode = Postalcode;
         }

         public void setLevel( String Level ) {
             this.Level = Level;
         }

         public void setNumber_of_person( String Number_of_person ) {
             this.Number_of_person = Number_of_person;
         }
     }

}
