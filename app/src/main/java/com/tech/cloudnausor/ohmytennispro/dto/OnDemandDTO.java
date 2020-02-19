package com.tech.cloudnausor.ohmytennispro.dto;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class OnDemandDTO {
    @SerializedName("isSuccess")
    @Expose
        private boolean isSuccess;
    @SerializedName("data")
    @Expose
        Data data;
    @SerializedName("message")
    @Expose
        private String message;


        // Getter Methods

        public boolean getIsSuccess() {
            return isSuccess;
        }

        public Data getData() {
            return data;
        }

        public String getMessage() {
            return message;
        }

        // Setter Methods

        public void setIsSuccess( boolean isSuccess ) {
            this.isSuccess = isSuccess;
        }

        public void setData( Data data ) {
            this.data = data;
        }

        public void setMessage( String message ) {
            this.message = message;
        }
        
     public  class Data {
            @SerializedName("availabilty")
                    @Expose

        ArrayList<Avaibility> availabilty = new ArrayList<Avaibility>();

        public ArrayList<Avaibility> getAvailabilty() {
            return availabilty;
        }

        public void setAvailabilty(ArrayList<Avaibility> availabilty) {
            this.availabilty = availabilty;
        }
     }
    
    public class Avaibility{
            @SerializedName("id")
            @Expose
        private String id;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("User_Level")
        @Expose
        private String User_Level = null;
        @SerializedName("User_Team")
        @Expose
        private String User_Team;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("address")
        @Expose
        private String address = null;
        @SerializedName("User_Location")
        @Expose
        private String User_Location;
        @SerializedName("postalCode")
        @Expose
        private String postalCode;
        @SerializedName("cityId")
        @Expose
        private String cityId;
        @SerializedName("roleId")
        @Expose
        private String roleId;
        @SerializedName("isOtpVerified")
        @Expose
        private String isOtpVerified;
        @SerializedName("User_Image")
        @Expose
        private String User_Image = null;
        @SerializedName("isActive")
        @Expose
        private String isActive;
        @SerializedName("hashKey")
        @Expose
        private String hashKey = null;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;


        // Getter Methods 

        public String getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getGender() {
            return gender;
        }

        public String getPassword() {
            return password;
        }

        public String getUser_Level() {
            return User_Level;
        }

        public String getUser_Team() {
            return User_Team;
        }

        public String getMobile() {
            return mobile;
        }

        public String getAddress() {
            return address;
        }

        public String getUser_Location() {
            return User_Location;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getCityId() {
            return cityId;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getIsOtpVerified() {
            return isOtpVerified;
        }

        public String getUser_Image() {
            return User_Image;
        }

        public String getIsActive() {
            return isActive;
        }

        public String getHashKey() {
            return hashKey;
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

        public void setFirstName( String firstName ) {
            this.firstName = firstName;
        }

        public void setLastName( String lastName ) {
            this.lastName = lastName;
        }

        public void setEmail( String email ) {
            this.email = email;
        }

        public void setGender( String gender ) {
            this.gender = gender;
        }

        public void setPassword( String password ) {
            this.password = password;
        }

        public void setUser_Level( String User_Level ) {
            this.User_Level = User_Level;
        }

        public void setUser_Team( String User_Team ) {
            this.User_Team = User_Team;
        }

        public void setMobile( String mobile ) {
            this.mobile = mobile;
        }

        public void setAddress( String address ) {
            this.address = address;
        }

        public void setUser_Location( String User_Location ) {
            this.User_Location = User_Location;
        }

        public void setPostalCode( String postalCode ) {
            this.postalCode = postalCode;
        }

        public void setCityId( String cityId ) {
            this.cityId = cityId;
        }

        public void setRoleId( String roleId ) {
            this.roleId = roleId;
        }

        public void setIsOtpVerified( String isOtpVerified ) {
            this.isOtpVerified = isOtpVerified;
        }

        public void setUser_Image( String User_Image ) {
            this.User_Image = User_Image;
        }

        public void setIsActive( String isActive ) {
            this.isActive = isActive;
        }

        public void setHashKey( String hashKey ) {
            this.hashKey = hashKey;
        }

        public void setCreatedAt( String createdAt ) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt( String updatedAt ) {
            this.updatedAt = updatedAt;
        }
    }
}


