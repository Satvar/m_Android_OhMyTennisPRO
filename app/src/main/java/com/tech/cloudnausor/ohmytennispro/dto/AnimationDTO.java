package com.tech.cloudnausor.ohmytennispro.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimationDTO {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("data")
    @Expose
    AnimationData DataObject;
    @SerializedName("message")
    @Expose
    private String message;

    // Getter Methods

    public String getIsSuccess() {
        return isSuccess;
    }

    public AnimationData getData() {
        return DataObject;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setIsSuccess( String isSuccess ) {
        this.isSuccess = isSuccess;
    }

    public void setData( AnimationData dataObject ) {
        this.DataObject = dataObject;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
    public class AnimationData {
        @SerializedName("course")
        @Expose
        ArrayList<AnimationCourse> course = new ArrayList<AnimationCourse>();

        public ArrayList<AnimationCourse> getCourse() {
            return course;
        }

        public void setCourse(ArrayList<AnimationCourse> course) {
            this.course = course;
        }

    }

}
