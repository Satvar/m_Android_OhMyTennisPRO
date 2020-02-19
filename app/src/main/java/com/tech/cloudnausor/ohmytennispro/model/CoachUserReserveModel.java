package com.tech.cloudnausor.ohmytennispro.model;

import java.util.ArrayList;

public class CoachUserReserveModel {

    String reservationHeading;
    String reserveName;
    String reserveredDate;
    String reserveredTime;
    String reserveStatus ;

    public CoachUserReserveModel(String reservationHeading, String reserveName, String reserveredDate, String reserveredTime, String reserveStatus) {
        this.reservationHeading = reservationHeading;
        this.reserveName = reserveName;
        this.reserveredDate = reserveredDate;
        this.reserveredTime = reserveredTime;
        this.reserveStatus = reserveStatus;
    }

    public String getReservationHeading() {
        return reservationHeading;
    }

    public void setReservationHeading(String reservationHeading) {
        this.reservationHeading = reservationHeading;
    }

    public String getReserveName() {
        return reserveName;
    }

    public void setReserveName(String reserveName) {
        this.reserveName = reserveName;
    }

    public String getReserveredDate() {
        return reserveredDate;
    }

    public void setReserveredDate(String reserveredDate) {
        this.reserveredDate = reserveredDate;
    }

    public String getReserveredTime() {
        return reserveredTime;
    }

    public void setReserveredTime(String reserveredTime) {
        this.reserveredTime = reserveredTime;
    }

    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }
}
