package com.tech.cloudnausor.ohmytennispro.calenderactivity;


import com.tech.cloudnausor.ohmytennispro.Calenderpackage.event.CalendarEvent;

public class MyEvent extends CalendarEvent {

    private String mTitle;
    private String mTime;

    MyEvent(String title, String time, long startTimeInMillis, int indicatorColor) {
        super(startTimeInMillis, indicatorColor);

        mTitle = title;
        mTime = time;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getTime() {
        return mTime;
    }

}
