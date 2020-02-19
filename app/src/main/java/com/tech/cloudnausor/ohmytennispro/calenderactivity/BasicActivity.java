package com.tech.cloudnausor.ohmytennispro.calenderactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tech.cloudnausor.ohmytennispro.Calenderpackage.CalendarView;
import com.tech.cloudnausor.ohmytennispro.Calenderpackage.OnDateSelectedListener;
import com.tech.cloudnausor.ohmytennispro.Calenderpackage.OnLoadEventsListener;
import com.tech.cloudnausor.ohmytennispro.Calenderpackage.OnMonthChangedListener;
import com.tech.cloudnausor.ohmytennispro.Calenderpackage.event.CalendarEvent;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.HomeActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.CalenderModel;
import com.tech.cloudnausor.ohmytennispro.response.CalenderResponseData;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BasicActivity extends AppCompatActivity implements OnMonthChangedListener,
        OnDateSelectedListener, OnLoadEventsListener {
    private static final String PREFER_NAME = "Reg";
    SessionManagement session;
    private SharedPreferences sharedPreferences;

    private CalendarMonthNameFormatter mFormatter = new CalendarMonthNameFormatter();

    private EventsAdapter mEventsAdapter;
    TextView CTitle;
    int year_s,month_s,day_s;
    CalenderModel testing;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    List<MyEvent> events = new ArrayList<>();
    ArrayList<CalenderModel> calenderModels=new ArrayList<CalenderModel>();
    ArrayList<String> startDate=new ArrayList<String>(),endDate=new ArrayList<String>(),startTime=new ArrayList<String>(),
            EndTime=new ArrayList<String>(),EventTitle=new ArrayList<String>(),Eventcolor=new ArrayList<String>();
    Calendar calendar1;
    RecyclerView mRecyclerView;
    ImageView GoBack;
    CalendarView mCalendarView;
    String uName = null;
    String uPassword =null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_basic);

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView); ;
        mCalendarView =(CalendarView)findViewById(R.id.calendarView);
        GoBack = (ImageView)findViewById(R.id.back);
        mEventsAdapter = new EventsAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEventsAdapter);
        CTitle = (TextView)findViewById(R.id.cTitle);
        mCalendarView.setOnMonthChangedListener(this);
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnLoadEventsListener(this);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.JANUARY, 1);
        mCalendarView.setMinimumDate(calendar.getTimeInMillis());
        calendar.set(2020, Calendar.DECEMBER, 1);
        mCalendarView.setMaximumDate(calendar.getTimeInMillis());
        session = new SessionManagement(getApplicationContext());
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains("KEY_Coach_ID"))
        {
            uName = sharedPreferences.getString("KEY_Coach_ID", "");

        }

        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");

        }
        calendar1 = Calendar.getInstance();

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


//        apiInterface.getGetCalendarMyCalendars(uName).enqueue(new Callback<CalenderResponseData>() {
//            @Override
//            public void onResponse(Call<CalenderResponseData> call, Response<CalenderResponseData> response) {
//                if(response.body().getStatus().equals("200")){
//                    System.out.println("calenderarray-------> "+response.body().getCalendarList());
//                    calenderModels.addAll(response.body().getCalendarList());
//                    callhhh();
//                    System.out.println("calenderarray-------> 123"+response.body().getCalendarList());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CalenderResponseData> call, Throwable t) {
//
//            }
//        });





        String utcDateStr = "2018-07-31T12:00:00Z";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date1 = null;
        try {
            date1 = sdf.parse(utcDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("MM/dd/yyyy formatted date : " + new SimpleDateFormat("yyyy").format(date1));
        System.out.println("yyyy-MM-dd formatted date : " + new SimpleDateFormat("yyyy-MM-dd").format(date1));

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setElevation(0);
//            getSupportActionBar().setBackgroundDrawable(
//                    new ColorDrawable(mCalendarView.getBackgroundColor()));
//        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//


//                for(CalenderModel calenderarray:calenderModels){
//                    String utcDateStr1 = calenderarray.getStart().toString();
//                    DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
//                    Date date11 = null;
//                    try {
//                        date11 = sdf1.parse(utcDateStr1);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    calendar1.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format(date11)));
//                    calendar1.set(Calendar.MONTH, Integer.parseInt(new SimpleDateFormat("MM").format(date11)) -1 );
//                    calendar1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(new SimpleDateFormat("dd").format(date11)));
//                    MyEvent event = new MyEvent("Example event", "Hello",calendar1.getTimeInMillis(), getRandomColor());
//                    System.out.println("jfjjfjfjfj"+calendar1.getTimeInMillis());
//                    events.add(event);
//                }
//        calendar1.set(Calendar.YEAR, 2019);
//        calendar1.set(Calendar.MONTH, 7);
//        calendar1.set(Calendar.DAY_OF_MONTH, 2);
//        MyEvent event = new MyEvent("Example event", "Hello",calendar1.getTimeInMillis(), getRandomColor());
//        System.out.println("jfjjfjfjfj"+calendar1.getTimeInMillis());
//        events.add(event);
//                calendar1.set(Calendar.YEAR, year_s);
//                calendar1.set(Calendar.MONTH, month_s);
//
//        int[] daysNumbs = getRandomNumbs(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
//                10); // Events count
//        for (int dayNumb : daysNumbs) {

//                calendar1.set(Calendar.DAY_OF_MONTH, dayNumb);
//                    MyEvent event = new MyEvent("Example event", "Hello",calendar1.getTimeInMillis(), getRandomColor());
//                    System.out.println("jfjjfjfjfj"+calendar1.getTimeInMillis());
//                    events.add(event);
//
//                calendar.set(Calendar.DAY_OF_MONTH, day_s);

                mCalendarView.setOnLoadEventsListener(BasicActivity.this);
                mCalendarView.setOnMonthChangedListener(BasicActivity.this);
                mCalendarView.setOnDateSelectedListener(BasicActivity.this);

//                mCalendarView.setOnMonthChangedListener(BasicActivity.this);
//                Snackbar.make(getApplicationContext(), "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//            }
//        });

//        mCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

    }

    @Override
    public void onMonthChanged(Calendar monthCalendar) {

//        Date date = null;
//        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
//        now.setTime(date);

        year_s = monthCalendar.get(Calendar.YEAR);
        month_s = monthCalendar.get(Calendar.MONTH);

//        System.out.println("year_s -->" +year_s);
//        System.out.println("year_s -->" +month_s);
//        System.out.println("year_s -->" +monthCalendar);

        CTitle.setText(mFormatter.format(monthCalendar)+"  "+year_s);
        mCalendarView.setOnDateSelectedListener(BasicActivity.this);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(mFormatter.format(monthCalendar));
//        }
    }

    @Override
    public void onDateSelected(Calendar dayCalendar, @Nullable List<CalendarEvent> events) {
        mEventsAdapter.clearList();
        System.out.println(mCalendarView.selected_day());
        day_s = mCalendarView.selected_day();
        if (events == null) {
            return;
        }

        for (CalendarEvent event : events) {
            if (event instanceof MyEvent) {
                mEventsAdapter.addEvent((MyEvent) event);
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void callhhh(){
        System.out.println("calenderarray------->111 "+calenderModels.size());
        for (int i =0;i<calenderModels.size();i++){
            testing = calenderModels.get(i);
            String utcDateStr1 = testing.getStart().toString();
            DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            Date date11 = null;
            try {
                date11 = sdf1.parse(utcDateStr1);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("jfjjfjfjfj444"+e);
            }
            System.out.println("jfjjfjfjfj"+ Integer.parseInt(new SimpleDateFormat("yyyy").format(date11)));
            calendar1.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format(date11)));
            calendar1.set(Calendar.MONTH, Integer.parseInt(new SimpleDateFormat("M").format(date11))-1);
            calendar1.set(Calendar.DAY_OF_MONTH,Integer.parseInt(new SimpleDateFormat("d").format(date11)));
            MyEvent event = new MyEvent(testing.getTitle().toString(), "Hello",calendar1.getTimeInMillis(), getRandomColor());
            events.add(event);
            mCalendarView.setOnLoadEventsListener(BasicActivity.this);
        }
    }


    public List<? extends CalendarEvent> onLoadEvents(int year, int month) {
        // Fill by random events
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2019);
//        calendar.set(Calendar.MONTH, 8-1);
////
////        int[] daysNumbs = getRandomNumbs(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
////                10); // Events count
////        for (int dayNumb : daysNumbs) {
//           System.out.println("Calendar.DAY_OF_MONTH --->" +Calendar.DAY_OF_MONTH);
//            calendar.set(Calendar.DAY_OF_MONTH, 4);
//            MyEvent event = new MyEvent("Example event", "Hello",calendar.getTimeInMillis(), getRandomColor());
//            events.add(event);
//        }
        return events;
    }

    private int[] getRandomNumbs(int start, int end, int count) {
        int[] numbs = new int[count];

        for (int i = 0; i < numbs.length; i++) {
            int numb = start + (int) (Math.random() * end);
            numbs[i] = numb;
        }

        return numbs;
    }

    private int getRandomColor() {
        Resources resources = getResources();

        int rand = (int) (Math.random() * 4);
        switch (rand) {
            case 0:
                return resources.getColor(R.color.colorGoogleRed);
            case 1:
                return resources.getColor(R.color.colorGoogleYellow);
            case 2:
                return resources.getColor(R.color.colorGoogleBlue);
            case 3:
                return resources.getColor(R.color.colorGoogleGreen);
        }

        return 0x000000;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BasicActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
