package com.tech.cloudnausor.ohmytennispro.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OhMyEventsDTO {
    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("data")
    @Expose
    OhMyEventData DataObject;
    @SerializedName("message")
    @Expose
    private String message;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getDataObject() {
        return DataObject;
    }

    public void setDataObject(OhMyEventData dataObject) {
        DataObject = dataObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class  OhMyEventData {

        String eventname;
        String eventdate;
        String eventimage;
        String eventdescribution;

        public OhMyEventData() {
        }

        public OhMyEventData(String eventname, String eventdate, String eventimage, String eventdescribution) {
            this.eventname = eventname;
            this.eventdate = eventdate;
            this.eventimage = eventimage;
            this.eventdescribution = eventdescribution;
        }

        public String getEventname() {
            return eventname;
        }

        public void setEventname(String eventname) {
            this.eventname = eventname;
        }

        public String getEventdate() {
            return eventdate;
        }

        public void setEventdate(String eventdate) {
            this.eventdate = eventdate;
        }

        public String getEventimage() {
            return eventimage;
        }

        public void setEventimage(String eventimage) {
            this.eventimage = eventimage;
        }

        public String getEventdescribution() {
            return eventdescribution;
        }

        public void setEventdescribution(String eventdescribution) {
            this.eventdescribution = eventdescribution;
        }
    }
}
