package com.tech.cloudnausor.ohmytennispro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.setavaibility.SetAvaibilityData;
import com.tech.cloudnausor.ohmytennispro.model.setavaibility.SetAvaibilityTime;
import com.tech.cloudnausor.ohmytennispro.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.MyAutoCompleteTextView;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderAvaibility extends AppCompatActivity {

    Button Avaibility_Sunday, Avaibility_Monday, Avaibility_Tuesday, Avaibility_Wednesday, Avaibility_Thursday, Avaibility_Friday, Avaibility_Saturday;
    Button Avaibility_Time_8to9, Avaibility_Time_9to10, Avaibility_Time_10to11, Avaibility_Time_11to12, Avaibility_Time_12to13, Avaibility_Time_13to14, Avaibility_Time_14to15,
            Avaibility_Time_15to16, Avaibility_Time_16to17, Avaibility_Time_17to18, Avaibility_Time_18to19, Avaibility_Time_19to20, Avaibility_Time_20to21, Avaibility_Time_21to22;
    CheckBox Avaibility_Time_All;
    String Current_Selected_Day = "";

    LinearLayout l_heb, l_quo;
    RadioButton r_hab, r_quo;
    private EditText From_Date, To_Date;
    ImageView Edit_Indi;
    ApiInterface apiInterface;
    ImageView backicon;
    Toolbar toolbar;
    Calendar now, now1;
    ArrayList<String> weeklist = new ArrayList<String>();
    ArrayAdapter adapter;
    MyAutoCompleteTextView Weeks_Spinner;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String edit_sting = null, loginObject;
    String coachid_ = null;
    String uPassword = null; SingleTonProcess singleTonProcess;

    ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayList = new ArrayList<SetAvaibilityTime>();
    ImageView GoBack;
    ArrayList<String> weekdates = new ArrayList<String>();
    ArrayList<String> weekday_name = new ArrayList<String>();
    ArrayList<String> tosend_weekdates = new ArrayList<String>();

    SetAvaibilityTime Sunday_SetAvaibilityTime = new SetAvaibilityTime("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "", "", "", "");
    SetAvaibilityTime Monday_SetAvaibilityTime = new SetAvaibilityTime("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "", "", "", "");
    SetAvaibilityTime Tuesday_SetAvaibilityTime = new SetAvaibilityTime("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "", "", "", "");
    SetAvaibilityTime Wednesday_SetAvaibilityTime = new SetAvaibilityTime("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "", "", "", "");
    SetAvaibilityTime Thursday_SetAvaibilityTime = new SetAvaibilityTime("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "", "", "", "");
    SetAvaibilityTime Friday_SetAvaibilityTime = new SetAvaibilityTime("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "", "", "", "");
    SetAvaibilityTime Saturday_SetAvaibilityTime = new SetAvaibilityTime("N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "", "", "", "");

    Button Avaibility_Clear_All;
    TextView Form_Date_week, To_Date_week;
    LinearLayout Week_From_Two;

    SetAvaibilityData avaibilityData = new SetAvaibilityData();


    Dialog dialog;
    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date1;
    ArrayList<Date> daily_dates = new ArrayList<Date>();
    Button Avaibility_Main_ok, Avaibility_Main_Cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.layout_avaibility_calender);
        Avaibility_Main_ok = (Button) findViewById(R.id.avaibility_main_ok);
        Avaibility_Main_Cancel = (Button) findViewById(R.id.avaibility_main_cancel);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        singleTonProcess = singleTonProcess.getInstance();

        Avaibility_Clear_All = (Button) findViewById(R.id.avaibility_clear_all);
        Avaibility_Sunday = (Button) findViewById(R.id.avaibility_sunday);
        Avaibility_Monday = (Button) findViewById(R.id.avaibility_monday);
        Avaibility_Tuesday = (Button) findViewById(R.id.avaibility_tuesday);
        Avaibility_Wednesday = (Button) findViewById(R.id.avaibility_wednesday);
        Avaibility_Thursday = (Button) findViewById(R.id.avaibility_thursday);
        Avaibility_Friday = (Button) findViewById(R.id.avaibility_friday);
        Avaibility_Saturday = (Button) findViewById(R.id.avaibility_saturday);
        backicon = (ImageView) findViewById(R.id.backicon);
        Avaibility_Time_8to9 = (Button) findViewById(R.id.avaibility_time_8to9);
        Avaibility_Time_9to10 = (Button) findViewById(R.id.avaibility_time_9to10);
        Avaibility_Time_10to11 = (Button) findViewById(R.id.avaibility_time_10to11);
        Avaibility_Time_11to12 = (Button) findViewById(R.id.avaibility_time_11to12);
        Avaibility_Time_12to13 = (Button) findViewById(R.id.avaibility_time_12to13);
        Avaibility_Time_13to14 = (Button) findViewById(R.id.avaibility_time_13to14);
        Avaibility_Time_14to15 = (Button) findViewById(R.id.avaibility_time_14to15);
        Avaibility_Time_15to16 = (Button) findViewById(R.id.avaibility_time_15to16);
        Avaibility_Time_16to17 = (Button) findViewById(R.id.avaibility_time_16to17);
        Avaibility_Time_17to18 = (Button) findViewById(R.id.avaibility_time_17to18);
        Avaibility_Time_18to19 = (Button) findViewById(R.id.avaibility_time_18to19);
        Avaibility_Time_19to20 = (Button) findViewById(R.id.avaibility_time_19to20);
        Avaibility_Time_20to21 = (Button) findViewById(R.id.avaibility_time_20to21);
        Avaibility_Time_21to22 = (Button) findViewById(R.id.avaibility_time_21to22);

        Avaibility_Time_All = (CheckBox) findViewById(R.id.avaibility_time_all);

        Avaibility_Main_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        r_quo = (RadioButton) findViewById(R.id.radio_Quo);
        r_hab = (RadioButton) findViewById(R.id.radio_Heb);
        l_quo = (LinearLayout) findViewById(R.id.Quo);
        l_heb = (LinearLayout) findViewById(R.id.heb);
        From_Date = (EditText) findViewById(R.id.from_date);
        To_Date = (EditText) findViewById(R.id.to_date);
        Form_Date_week = (TextView) findViewById(R.id.from_date_id);
        To_Date_week = (TextView) findViewById(R.id.to_date_id);
        Week_From_Two = (LinearLayout) findViewById(R.id.week_start_end);
        Weeks_Spinner = (MyAutoCompleteTextView) findViewById(R.id.week_view);

        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());

        if (sharedPreferences.contains("KEY_Coach_ID")) {
            coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");

        }
        if (sharedPreferences.contains("Email")) {
            uPassword = sharedPreferences.getString("Email", "");

        }

        if (sharedPreferences.contains("IsMyEditString")) {
            edit_sting = sharedPreferences.getString("IsMyEditString", "");
        }
        Avaibility_Clear_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalenderAvaibility.this, CalenderAvaibility.class);
                startActivity(intent);
            }
        });
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                form_dat();
            }
        };
        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                to_dat();
            }
        };


        now = Calendar.getInstance();

        int j = 31;
        for (int i = 0; i < 7; i++) {
            now.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            now.set(Calendar.MONTH, 11);//0- january ..4-May
            now.set(Calendar.DATE, j);
            if (now.get(Calendar.WEEK_OF_YEAR) != 1) {
                break;
            } else {
                j = j - 1;
            }
        }

        now1 = Calendar.getInstance();
        now1.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        now1.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));//0- january ..4-May
        now1.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
        System.out.println("Current week of " +
                now1);
        System.out.println("Current week of month is : " +
                now1.get(Calendar.WEEK_OF_MONTH));
        System.out.println("Current week of year is : " +
                now1.get(Calendar.WEEK_OF_YEAR));

        for (int i = now1.get(Calendar.WEEK_OF_YEAR); i <= now.get(Calendar.WEEK_OF_YEAR); i++) {
            weeklist.add("Semaine " + i);
            System.out.println(weeklist);
        }
        From_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CalenderAvaibility.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        From_Date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!From_Date.getText().toString().equals("") && !To_Date.getText().toString().equals("")) {
                    enableAvaibility();
                    getDates();
                    time_all();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        To_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CalenderAvaibility.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        To_Date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!From_Date.getText().toString().equals("") && !To_Date.getText().toString().equals("")) {
                    enableAvaibility();
                    getDates();
                    time_all();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        r_quo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r_quo.setChecked(true);
                l_heb.setVisibility(View.VISIBLE);
                r_hab.setChecked(false);
                l_quo.setVisibility(View.GONE);
                Week_From_Two.setVisibility(View.GONE);
                if (!Form_Date_week.getText().toString().equals("") && !To_Date.getText().toString().equals("")) {
                    enableAvaibility();
                } else {
                    disEnableAvaibility();
                }
            }
        });

        r_hab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Weeks_Spinner.getText().toString().equals("")) {
                    Week_From_Two.setVisibility(View.VISIBLE);
                    enableAvaibility();
                } else {
                    disEnableAvaibility();
                }
                r_hab.setChecked(true);
                l_quo.setVisibility(View.VISIBLE);
                r_quo.setChecked(false);
                l_heb.setVisibility(View.GONE);

            }
        });

        List<String> sermaine = weeklist;
        adapter = new ArrayAdapter<String>(CalenderAvaibility.this, R.layout.spinner_text, sermaine);

        adapter.setDropDownViewResource(R.layout.spinner_item_list);

        Weeks_Spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                weekdates.clear();
                weekday_name.clear();
                String spinnerdata = charSequence.toString();
                spinnerdata = spinnerdata.replace("Semaine ", "");
                Calendar weekdata = Calendar.getInstance();
                weekdata.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(spinnerdata));
                if ((Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER) && Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) == 1) {

                    System.out.println("Calendar.getInstance()--> " + Calendar.getInstance().get(Calendar.YEAR));

                    weekdata.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
                } else {
                    weekdata.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                }

                int firstDayOfWeek = weekdata.getFirstDayOfWeek();
                for (int k = firstDayOfWeek; k < firstDayOfWeek + 7; k++) {
                    weekdata.set(Calendar.DAY_OF_WEEK, k);
                    tosend_weekdates.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(weekdata.getTime()));
                    weekdates.add(new SimpleDateFormat("yyyy-MM-dd").format(weekdata.getTime()));
                    weekday_name.add(new SimpleDateFormat("EEEE").format(weekdata.getTime()));
                    System.out.println("weekdates---> " + new SimpleDateFormat("EEEE").format(weekdata.getTime()));
                }

                for (int d = 0; d < weekday_name.size(); d++) {
                    switch (weekday_name.get(d)) {
                        case "Monday":
//                             Monday_SetAvaibilityTime.setH8("N");
//                             Monday_SetAvaibilityTime.setH9("N");
//                             Monday_SetAvaibilityTime.setH10("N");
//                             Monday_SetAvaibilityTime.setH11("N");
//                             Monday_SetAvaibilityTime.setH12("N");
//                             Monday_SetAvaibilityTime.setH13("N");
//                             Monday_SetAvaibilityTime.setH14("N");
//                             Monday_SetAvaibilityTime.setH15("N");
//                             Monday_SetAvaibilityTime.setH16("N");
//                             Monday_SetAvaibilityTime.setH17("N");
//                             Monday_SetAvaibilityTime.setH18("N");
//                             Monday_SetAvaibilityTime.setH19("N");
//                             Monday_SetAvaibilityTime.setH20("N");
//                             Monday_SetAvaibilityTime.setH21("N");
//                             Monday_SetAvaibilityTime.setH22("N");
                            Monday_SetAvaibilityTime.setCoach_Id(coachid_);
                            Monday_SetAvaibilityTime.setDate(weekdates.get(d));
                            Monday_SetAvaibilityTime.setStart_Date(weekdates.get(0));
                            Monday_SetAvaibilityTime.setEnd_Date(weekdates.get(6));
                            day_time_selectyion();
                            System.out.println("weekdates---> Monday --> " + new Gson().toJson(Monday_SetAvaibilityTime));
                            break;
                        case "Tuesday":
//                            Tuesday_SetAvaibilityTime.setH8("N");
//                            Tuesday_SetAvaibilityTime.setH9("N");
//                            Tuesday_SetAvaibilityTime.setH10("N");
//                            Tuesday_SetAvaibilityTime.setH11("N");
//                            Tuesday_SetAvaibilityTime.setH12("N");
//                            Tuesday_SetAvaibilityTime.setH13("N");
//                            Tuesday_SetAvaibilityTime.setH14("N");
//                            Tuesday_SetAvaibilityTime.setH15("N");
//                            Tuesday_SetAvaibilityTime.setH16("N");
//                            Tuesday_SetAvaibilityTime.setH17("N");
//                            Tuesday_SetAvaibilityTime.setH18("N");
//                            Tuesday_SetAvaibilityTime.setH19("N");
//                            Tuesday_SetAvaibilityTime.setH20("N");
//                            Tuesday_SetAvaibilityTime.setH21("N");
//                            Tuesday_SetAvaibilityTime.setH22("N");
                            Tuesday_SetAvaibilityTime.setCoach_Id(coachid_);
                            Tuesday_SetAvaibilityTime.setDate(weekdates.get(d));
                            Tuesday_SetAvaibilityTime.setStart_Date(weekdates.get(0));
                            Tuesday_SetAvaibilityTime.setEnd_Date(weekdates.get(6));
                            System.out.println("weekdates---> Tuesday --> " + new Gson().toJson(Tuesday_SetAvaibilityTime));

                            break;
                        case "Wednesday":
//                            Wednesday_SetAvaibilityTime.setH8("N");
//                            Wednesday_SetAvaibilityTime.setH9("N");
//                            Wednesday_SetAvaibilityTime.setH10("N");
//                            Wednesday_SetAvaibilityTime.setH11("N");
//                            Wednesday_SetAvaibilityTime.setH12("N");
//                            Wednesday_SetAvaibilityTime.setH13("N");
//                            Wednesday_SetAvaibilityTime.setH14("N");
//                            Wednesday_SetAvaibilityTime.setH15("N");
//                            Wednesday_SetAvaibilityTime.setH16("N");
//                            Wednesday_SetAvaibilityTime.setH17("N");
//                            Wednesday_SetAvaibilityTime.setH18("N");
//                            Wednesday_SetAvaibilityTime.setH19("N");
//                            Wednesday_SetAvaibilityTime.setH20("N");
//                            Wednesday_SetAvaibilityTime.setH21("N");
//                            Wednesday_SetAvaibilityTime.setH22("N");
                            Wednesday_SetAvaibilityTime.setCoach_Id(coachid_);
                            Wednesday_SetAvaibilityTime.setDate(weekdates.get(d));
                            Wednesday_SetAvaibilityTime.setStart_Date(weekdates.get(0));
                            Wednesday_SetAvaibilityTime.setEnd_Date(weekdates.get(6));
                            System.out.println("weekdates---> Wednesday --> " + new Gson().toJson(Wednesday_SetAvaibilityTime));
                            break;

                        case "Thursday":
//                            Thursday_SetAvaibilityTime.setH8("N");
//                            Thursday_SetAvaibilityTime.setH9("N");
//                            Thursday_SetAvaibilityTime.setH10("N");
//                            Thursday_SetAvaibilityTime.setH11("N");
//                            Thursday_SetAvaibilityTime.setH12("N");
//                            Thursday_SetAvaibilityTime.setH13("N");
//                            Thursday_SetAvaibilityTime.setH14("N");
//                            Thursday_SetAvaibilityTime.setH15("N");
//                            Thursday_SetAvaibilityTime.setH16("N");
//                            Thursday_SetAvaibilityTime.setH17("N");
//                            Thursday_SetAvaibilityTime.setH18("N");
//                            Thursday_SetAvaibilityTime.setH19("N");
//                            Thursday_SetAvaibilityTime.setH20("N");
//                            Thursday_SetAvaibilityTime.setH21("N");
//                            Thursday_SetAvaibilityTime.setH22("N");
                            Thursday_SetAvaibilityTime.setCoach_Id(coachid_);
                            Thursday_SetAvaibilityTime.setDate(weekdates.get(d));
                            Thursday_SetAvaibilityTime.setStart_Date(weekdates.get(0));
                            Thursday_SetAvaibilityTime.setEnd_Date(weekdates.get(6));
                            System.out.println("weekdates---> Thursday --> " + new Gson().toJson(Thursday_SetAvaibilityTime));

                            break;

                        case "Friday":
//                            Friday_SetAvaibilityTime.setH8("N");
//                            Friday_SetAvaibilityTime.setH9("N");
//                            Friday_SetAvaibilityTime.setH10("N");
//                            Friday_SetAvaibilityTime.setH11("N");
//                            Friday_SetAvaibilityTime.setH12("N");
//                            Friday_SetAvaibilityTime.setH13("N");
//                            Friday_SetAvaibilityTime.setH14("N");
//                            Friday_SetAvaibilityTime.setH15("N");
//                            Friday_SetAvaibilityTime.setH16("N");
//                            Friday_SetAvaibilityTime.setH17("N");
//                            Friday_SetAvaibilityTime.setH18("N");
//                            Friday_SetAvaibilityTime.setH19("N");
//                            Friday_SetAvaibilityTime.setH20("N");
//                            Friday_SetAvaibilityTime.setH21("N");
//                            Friday_SetAvaibilityTime.setH22("N");
                            Friday_SetAvaibilityTime.setCoach_Id(coachid_);
                            Friday_SetAvaibilityTime.setDate(weekdates.get(d));
                            Friday_SetAvaibilityTime.setStart_Date(weekdates.get(0));
                            Friday_SetAvaibilityTime.setEnd_Date(weekdates.get(6));
                            System.out.println("weekdates---> Friday --> " + new Gson().toJson(Friday_SetAvaibilityTime));

                            break;
                        case "Saturday":
//                            Saturday_SetAvaibilityTime.setH8("N");
//                            Saturday_SetAvaibilityTime.setH9("N");
//                            Saturday_SetAvaibilityTime.setH10("N");
//                            Saturday_SetAvaibilityTime.setH11("N");
//                            Saturday_SetAvaibilityTime.setH12("N");
//                            Saturday_SetAvaibilityTime.setH13("N");
//                            Saturday_SetAvaibilityTime.setH14("N");
//                            Saturday_SetAvaibilityTime.setH15("N");
//                            Saturday_SetAvaibilityTime.setH16("N");
//                            Saturday_SetAvaibilityTime.setH17("N");
//                            Saturday_SetAvaibilityTime.setH18("N");
//                            Saturday_SetAvaibilityTime.setH19("N");
//                            Saturday_SetAvaibilityTime.setH20("N");
//                            Saturday_SetAvaibilityTime.setH21("N");
//                            Saturday_SetAvaibilityTime.setH22("N");
                            Saturday_SetAvaibilityTime.setCoach_Id(coachid_);
                            Saturday_SetAvaibilityTime.setDate(weekdates.get(d));
                            Saturday_SetAvaibilityTime.setStart_Date(weekdates.get(0));
                            Saturday_SetAvaibilityTime.setEnd_Date(weekdates.get(6));
                            System.out.println("weekdates---> Saturday --> " + new Gson().toJson(Saturday_SetAvaibilityTime));
                            break;
                        case "Sunday":
//                            Sunday_SetAvaibilityTime.setH8("N");
//                            Sunday_SetAvaibilityTime.setH9("N");
//                            Sunday_SetAvaibilityTime.setH10("N");
//                            Sunday_SetAvaibilityTime.setH11("N");
//                            Sunday_SetAvaibilityTime.setH12("N");
//                            Sunday_SetAvaibilityTime.setH13("N");
//                            Sunday_SetAvaibilityTime.setH14("N");
//                            Sunday_SetAvaibilityTime.setH15("N");
//                            Sunday_SetAvaibilityTime.setH16("N");
//                            Sunday_SetAvaibilityTime.setH17("N");
//                            Sunday_SetAvaibilityTime.setH18("N");
//                            Sunday_SetAvaibilityTime.setH19("N");
//                            Sunday_SetAvaibilityTime.setH20("N");
//                            Sunday_SetAvaibilityTime.setH21("N");
//                            Sunday_SetAvaibilityTime.setH22("N");
                            Sunday_SetAvaibilityTime.setCoach_Id(coachid_);
                            Sunday_SetAvaibilityTime.setDate(weekdates.get(d));
                            Sunday_SetAvaibilityTime.setStart_Date(weekdates.get(0));
                            Sunday_SetAvaibilityTime.setEnd_Date(weekdates.get(6));
                            System.out.println("weekdates---> Sunday --> " + new Gson().toJson(Sunday_SetAvaibilityTime));

                            break;
                    }
                }
                Form_Date_week.setText(weekdates.get(0));
                To_Date_week.setText(weekdates.get(6));
                Week_From_Two.setVisibility(View.VISIBLE);
                time_all();
                if (!Weeks_Spinner.getText().toString().equals("")) {
                    enableAvaibility();
                } else {
                    disEnableAvaibility();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Weeks_Spinner.setAdapter(adapter);
        Weeks_Spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weeks_Spinner.showDropDown();
            }
        });

        Avaibility_Sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_Selected_Day = "Sunday";
                Avaibility_Sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Avaibility_Sunday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Monday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Tuesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Wednesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Thursday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Friday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Saturday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                day_time_selectyion();
                time_all();
            }
        });
        Avaibility_Monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_Selected_Day = "Monday";
                Avaibility_Sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Sunday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Avaibility_Monday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Tuesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Wednesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Thursday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Friday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Saturday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                day_time_selectyion();
                time_all();


            }
        });
        Avaibility_Tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_Selected_Day = "Tuesday";
                Avaibility_Sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Sunday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Monday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Avaibility_Tuesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Wednesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Thursday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Friday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Saturday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                day_time_selectyion();
                time_all();


            }
        });
        Avaibility_Wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_Selected_Day = "Wednesday";
                Avaibility_Sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Sunday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Monday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Tuesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Avaibility_Wednesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Thursday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Friday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Saturday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                day_time_selectyion();
                time_all();


            }
        });
        Avaibility_Thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Current_Selected_Day = "Thursday";
                Avaibility_Sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Sunday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Monday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Tuesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Wednesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Avaibility_Thursday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Friday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Saturday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                day_time_selectyion();
                time_all();


            }
        });
        Avaibility_Friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_Selected_Day = "Friday";
                Avaibility_Sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Sunday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Monday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Tuesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Wednesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Thursday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Avaibility_Friday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Saturday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                day_time_selectyion();
                time_all();

            }
        });

        Avaibility_Saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_Selected_Day = "Saturday";
                Avaibility_Sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Sunday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Monday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Tuesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Wednesday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Thursday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                Avaibility_Friday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                Avaibility_Saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Avaibility_Saturday.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                day_time_selectyion();
                time_all();


            }
        });

        Avaibility_Time_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Avaibility_Time_All.isChecked()) {
                    switch (Current_Selected_Day) {
                        case "Monday":
                            Monday_SetAvaibilityTime.setH8("Y");
                            Monday_SetAvaibilityTime.setH9("Y");
                            Monday_SetAvaibilityTime.setH10("Y");
                            Monday_SetAvaibilityTime.setH11("Y");
                            Monday_SetAvaibilityTime.setH12("Y");
                            Monday_SetAvaibilityTime.setH13("Y");
                            Monday_SetAvaibilityTime.setH14("Y");
                            Monday_SetAvaibilityTime.setH15("Y");
                            Monday_SetAvaibilityTime.setH16("Y");
                            Monday_SetAvaibilityTime.setH17("Y");
                            Monday_SetAvaibilityTime.setH18("Y");
                            Monday_SetAvaibilityTime.setH19("Y");
                            Monday_SetAvaibilityTime.setH20("Y");
                            Monday_SetAvaibilityTime.setH21("Y");
                            Monday_SetAvaibilityTime.setH22("Y");

                            day_time_selectyion();

                            break;
                        case "Tuesday":
                            Tuesday_SetAvaibilityTime.setH8("Y");
                            Tuesday_SetAvaibilityTime.setH9("Y");
                            Tuesday_SetAvaibilityTime.setH10("Y");
                            Tuesday_SetAvaibilityTime.setH11("Y");
                            Tuesday_SetAvaibilityTime.setH12("Y");
                            Tuesday_SetAvaibilityTime.setH13("Y");
                            Tuesday_SetAvaibilityTime.setH14("Y");
                            Tuesday_SetAvaibilityTime.setH15("Y");
                            Tuesday_SetAvaibilityTime.setH16("Y");
                            Tuesday_SetAvaibilityTime.setH17("Y");
                            Tuesday_SetAvaibilityTime.setH18("Y");
                            Tuesday_SetAvaibilityTime.setH19("Y");
                            Tuesday_SetAvaibilityTime.setH20("Y");
                            Tuesday_SetAvaibilityTime.setH21("Y");
                            Tuesday_SetAvaibilityTime.setH22("Y");

                            day_time_selectyion();

                            break;
                        case "Wednesday":
                            Wednesday_SetAvaibilityTime.setH8("Y");
                            Wednesday_SetAvaibilityTime.setH9("Y");
                            Wednesday_SetAvaibilityTime.setH10("Y");
                            Wednesday_SetAvaibilityTime.setH11("Y");
                            Wednesday_SetAvaibilityTime.setH12("Y");
                            Wednesday_SetAvaibilityTime.setH13("Y");
                            Wednesday_SetAvaibilityTime.setH14("Y");
                            Wednesday_SetAvaibilityTime.setH15("Y");
                            Wednesday_SetAvaibilityTime.setH16("Y");
                            Wednesday_SetAvaibilityTime.setH17("Y");
                            Wednesday_SetAvaibilityTime.setH18("Y");
                            Wednesday_SetAvaibilityTime.setH19("Y");
                            Wednesday_SetAvaibilityTime.setH20("Y");
                            Wednesday_SetAvaibilityTime.setH21("Y");
                            Wednesday_SetAvaibilityTime.setH22("Y");

                            day_time_selectyion();

                            break;
                        case "Thursday":
                            Thursday_SetAvaibilityTime.setH8("Y");
                            Thursday_SetAvaibilityTime.setH9("Y");
                            Thursday_SetAvaibilityTime.setH10("Y");
                            Thursday_SetAvaibilityTime.setH11("Y");
                            Thursday_SetAvaibilityTime.setH12("Y");
                            Thursday_SetAvaibilityTime.setH13("Y");
                            Thursday_SetAvaibilityTime.setH14("Y");
                            Thursday_SetAvaibilityTime.setH15("Y");
                            Thursday_SetAvaibilityTime.setH16("Y");
                            Thursday_SetAvaibilityTime.setH17("Y");
                            Thursday_SetAvaibilityTime.setH18("Y");
                            Thursday_SetAvaibilityTime.setH19("Y");
                            Thursday_SetAvaibilityTime.setH20("Y");
                            Thursday_SetAvaibilityTime.setH21("Y");
                            Thursday_SetAvaibilityTime.setH22("Y");
                            day_time_selectyion();

                            break;
                        case "Friday":
                            Friday_SetAvaibilityTime.setH8("Y");
                            Friday_SetAvaibilityTime.setH9("Y");
                            Friday_SetAvaibilityTime.setH10("Y");
                            Friday_SetAvaibilityTime.setH11("Y");
                            Friday_SetAvaibilityTime.setH12("Y");
                            Friday_SetAvaibilityTime.setH13("Y");
                            Friday_SetAvaibilityTime.setH14("Y");
                            Friday_SetAvaibilityTime.setH15("Y");
                            Friday_SetAvaibilityTime.setH16("Y");
                            Friday_SetAvaibilityTime.setH17("Y");
                            Friday_SetAvaibilityTime.setH18("Y");
                            Friday_SetAvaibilityTime.setH19("Y");
                            Friday_SetAvaibilityTime.setH20("Y");
                            Friday_SetAvaibilityTime.setH21("Y");
                            Friday_SetAvaibilityTime.setH22("Y");
                            day_time_selectyion();

                            break;
                        case "Saturday":
                            Saturday_SetAvaibilityTime.setH8("Y");
                            Saturday_SetAvaibilityTime.setH9("Y");
                            Saturday_SetAvaibilityTime.setH10("Y");
                            Saturday_SetAvaibilityTime.setH11("Y");
                            Saturday_SetAvaibilityTime.setH12("Y");
                            Saturday_SetAvaibilityTime.setH13("Y");
                            Saturday_SetAvaibilityTime.setH14("Y");
                            Saturday_SetAvaibilityTime.setH15("Y");
                            Saturday_SetAvaibilityTime.setH16("Y");
                            Saturday_SetAvaibilityTime.setH17("Y");
                            Saturday_SetAvaibilityTime.setH18("Y");
                            Saturday_SetAvaibilityTime.setH19("Y");
                            Saturday_SetAvaibilityTime.setH20("Y");
                            Saturday_SetAvaibilityTime.setH21("Y");
                            Saturday_SetAvaibilityTime.setH22("Y");
                            day_time_selectyion();

                            break;
                        case "Sunday":
                            Sunday_SetAvaibilityTime.setH8("Y");
                            Sunday_SetAvaibilityTime.setH9("Y");
                            Sunday_SetAvaibilityTime.setH10("Y");
                            Sunday_SetAvaibilityTime.setH11("Y");
                            Sunday_SetAvaibilityTime.setH12("Y");
                            Sunday_SetAvaibilityTime.setH13("Y");
                            Sunday_SetAvaibilityTime.setH14("Y");
                            Sunday_SetAvaibilityTime.setH15("Y");
                            Sunday_SetAvaibilityTime.setH16("Y");
                            Sunday_SetAvaibilityTime.setH17("Y");
                            Sunday_SetAvaibilityTime.setH18("Y");
                            Sunday_SetAvaibilityTime.setH19("Y");
                            Sunday_SetAvaibilityTime.setH20("Y");
                            Sunday_SetAvaibilityTime.setH21("Y");
                            Sunday_SetAvaibilityTime.setH22("Y");
                            day_time_selectyion();
                            break;
                    }

                } else if (!Avaibility_Time_All.isChecked()) {
                    switch (Current_Selected_Day) {
                        case "Monday":
                            Monday_SetAvaibilityTime.setH8("N");
                            Monday_SetAvaibilityTime.setH9("N");
                            Monday_SetAvaibilityTime.setH10("N");
                            Monday_SetAvaibilityTime.setH11("N");
                            Monday_SetAvaibilityTime.setH12("N");
                            Monday_SetAvaibilityTime.setH13("N");
                            Monday_SetAvaibilityTime.setH14("N");
                            Monday_SetAvaibilityTime.setH15("N");
                            Monday_SetAvaibilityTime.setH16("N");
                            Monday_SetAvaibilityTime.setH17("N");
                            Monday_SetAvaibilityTime.setH18("N");
                            Monday_SetAvaibilityTime.setH19("N");
                            Monday_SetAvaibilityTime.setH20("N");
                            Monday_SetAvaibilityTime.setH21("N");
                            Monday_SetAvaibilityTime.setH22("N");
                            day_time_selectyion();


                            break;
                        case "Tuesday":
                            Tuesday_SetAvaibilityTime.setH8("N");
                            Tuesday_SetAvaibilityTime.setH9("N");
                            Tuesday_SetAvaibilityTime.setH10("N");
                            Tuesday_SetAvaibilityTime.setH11("N");
                            Tuesday_SetAvaibilityTime.setH12("N");
                            Tuesday_SetAvaibilityTime.setH13("N");
                            Tuesday_SetAvaibilityTime.setH14("N");
                            Tuesday_SetAvaibilityTime.setH15("N");
                            Tuesday_SetAvaibilityTime.setH16("N");
                            Tuesday_SetAvaibilityTime.setH17("N");
                            Tuesday_SetAvaibilityTime.setH18("N");
                            Tuesday_SetAvaibilityTime.setH19("N");
                            Tuesday_SetAvaibilityTime.setH20("N");
                            Tuesday_SetAvaibilityTime.setH21("N");
                            Tuesday_SetAvaibilityTime.setH22("N");

                            day_time_selectyion();


                            break;
                        case "Wednesday":
                            Wednesday_SetAvaibilityTime.setH8("N");
                            Wednesday_SetAvaibilityTime.setH9("N");
                            Wednesday_SetAvaibilityTime.setH10("N");
                            Wednesday_SetAvaibilityTime.setH11("N");
                            Wednesday_SetAvaibilityTime.setH12("N");
                            Wednesday_SetAvaibilityTime.setH13("N");
                            Wednesday_SetAvaibilityTime.setH14("N");
                            Wednesday_SetAvaibilityTime.setH15("N");
                            Wednesday_SetAvaibilityTime.setH16("N");
                            Wednesday_SetAvaibilityTime.setH17("N");
                            Wednesday_SetAvaibilityTime.setH18("N");
                            Wednesday_SetAvaibilityTime.setH19("N");
                            Wednesday_SetAvaibilityTime.setH20("N");
                            Wednesday_SetAvaibilityTime.setH21("N");
                            Wednesday_SetAvaibilityTime.setH22("N");
                            day_time_selectyion();

                            break;
                        case "Thursday":
                            Thursday_SetAvaibilityTime.setH8("N");
                            Thursday_SetAvaibilityTime.setH9("N");
                            Thursday_SetAvaibilityTime.setH10("N");
                            Thursday_SetAvaibilityTime.setH11("N");
                            Thursday_SetAvaibilityTime.setH12("N");
                            Thursday_SetAvaibilityTime.setH13("N");
                            Thursday_SetAvaibilityTime.setH14("N");
                            Thursday_SetAvaibilityTime.setH15("N");
                            Thursday_SetAvaibilityTime.setH16("N");
                            Thursday_SetAvaibilityTime.setH17("N");
                            Thursday_SetAvaibilityTime.setH18("N");
                            Thursday_SetAvaibilityTime.setH19("N");
                            Thursday_SetAvaibilityTime.setH20("N");
                            Thursday_SetAvaibilityTime.setH21("N");
                            Thursday_SetAvaibilityTime.setH22("N");
                            day_time_selectyion();
                            break;
                        case "Friday":
                            Friday_SetAvaibilityTime.setH8("N");
                            Friday_SetAvaibilityTime.setH9("N");
                            Friday_SetAvaibilityTime.setH10("N");
                            Friday_SetAvaibilityTime.setH11("N");
                            Friday_SetAvaibilityTime.setH12("N");
                            Friday_SetAvaibilityTime.setH13("N");
                            Friday_SetAvaibilityTime.setH14("N");
                            Friday_SetAvaibilityTime.setH15("N");
                            Friday_SetAvaibilityTime.setH16("N");
                            Friday_SetAvaibilityTime.setH17("N");
                            Friday_SetAvaibilityTime.setH18("N");
                            Friday_SetAvaibilityTime.setH19("N");
                            Friday_SetAvaibilityTime.setH20("N");
                            Friday_SetAvaibilityTime.setH21("N");
                            Friday_SetAvaibilityTime.setH22("N");
                            day_time_selectyion();
                            break;
                        case "Saturday":
                            Saturday_SetAvaibilityTime.setH8("N");
                            Saturday_SetAvaibilityTime.setH9("N");
                            Saturday_SetAvaibilityTime.setH10("N");
                            Saturday_SetAvaibilityTime.setH11("N");
                            Saturday_SetAvaibilityTime.setH12("N");
                            Saturday_SetAvaibilityTime.setH13("N");
                            Saturday_SetAvaibilityTime.setH14("N");
                            Saturday_SetAvaibilityTime.setH15("N");
                            Saturday_SetAvaibilityTime.setH16("N");
                            Saturday_SetAvaibilityTime.setH17("N");
                            Saturday_SetAvaibilityTime.setH18("N");
                            Saturday_SetAvaibilityTime.setH19("N");
                            Saturday_SetAvaibilityTime.setH20("N");
                            Saturday_SetAvaibilityTime.setH21("N");
                            Saturday_SetAvaibilityTime.setH22("N");
                            day_time_selectyion();
                            break;
                        case "Sunday":
                            Sunday_SetAvaibilityTime.setH8("N");
                            Sunday_SetAvaibilityTime.setH9("N");
                            Sunday_SetAvaibilityTime.setH10("N");
                            Sunday_SetAvaibilityTime.setH11("N");
                            Sunday_SetAvaibilityTime.setH12("N");
                            Sunday_SetAvaibilityTime.setH13("N");
                            Sunday_SetAvaibilityTime.setH14("N");
                            Sunday_SetAvaibilityTime.setH15("N");
                            Sunday_SetAvaibilityTime.setH16("N");
                            Sunday_SetAvaibilityTime.setH17("N");
                            Sunday_SetAvaibilityTime.setH18("N");
                            Sunday_SetAvaibilityTime.setH19("N");
                            Sunday_SetAvaibilityTime.setH20("N");
                            Sunday_SetAvaibilityTime.setH21("N");
                            Sunday_SetAvaibilityTime.setH22("N");
                            day_time_selectyion();
                            break;
                    }

                }
            }
        });

        Avaibility_Time_8to9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":
                        if (Monday_SetAvaibilityTime.getH8().equals("Y")) {
                            Monday_SetAvaibilityTime.setH8("N");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();

                        } else if (Monday_SetAvaibilityTime.getH8().equals("N")) {
                            Monday_SetAvaibilityTime.setH8("Y");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();

                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH8().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH8("N");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();

                        } else if (Tuesday_SetAvaibilityTime.getH8().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH8("Y");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();

                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH8().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH8("N");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH8().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH8("Y");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();

                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH8().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH8("N");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();

                        } else if (Thursday_SetAvaibilityTime.getH8().equals("N")) {
                            Thursday_SetAvaibilityTime.setH8("Y");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();

                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH8().equals("Y")) {
                            Friday_SetAvaibilityTime.setH8("N");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH8().equals("N")) {
                            Friday_SetAvaibilityTime.setH8("Y");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();

                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH8().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH8("N");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH8().equals("N")) {
                            Saturday_SetAvaibilityTime.setH8("Y");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();

                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH8().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH8("N");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH8().equals("N")) {
                            Sunday_SetAvaibilityTime.setH8("Y");
                            Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();

                        }
                        break;
                }

            }
        });
        Avaibility_Time_9to10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH9().equals("Y")) {
                            Monday_SetAvaibilityTime.setH9("N");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();

                        } else if (Monday_SetAvaibilityTime.getH9().equals("N")) {
                            Monday_SetAvaibilityTime.setH9("Y");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH9().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH9("N");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Tuesday_SetAvaibilityTime.getH9().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH9("Y");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH9().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH9("N");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH9().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH9("Y");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH9().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH9("N");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH9().equals("N")) {
                            Thursday_SetAvaibilityTime.setH9("Y");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH9().equals("Y")) {
                            Friday_SetAvaibilityTime.setH9("N");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH9().equals("N")) {
                            Friday_SetAvaibilityTime.setH9("Y");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH9().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH9("N");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH9().equals("N")) {
                            Saturday_SetAvaibilityTime.setH9("Y");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH9().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH9("N");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH9().equals("N")) {
                            Sunday_SetAvaibilityTime.setH9("Y");
                            Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_10to11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH10().equals("Y")) {
                            Monday_SetAvaibilityTime.setH10("N");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH10().equals("N")) {
                            Monday_SetAvaibilityTime.setH10("Y");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH10().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH10("N");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH10().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH10("Y");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH10().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH10("N");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH10().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH10("Y");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH10().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH10("N");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH10().equals("N")) {
                            Thursday_SetAvaibilityTime.setH10("Y");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH10().equals("Y")) {
                            Friday_SetAvaibilityTime.setH10("N");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH10().equals("N")) {
                            Friday_SetAvaibilityTime.setH10("Y");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH10().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH10("N");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH10().equals("N")) {
                            Saturday_SetAvaibilityTime.setH10("Y");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH10().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH10("N");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH10().equals("N")) {
                            Sunday_SetAvaibilityTime.setH10("Y");
                            Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_11to12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH11().equals("Y")) {
                            Monday_SetAvaibilityTime.setH11("N");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH11().equals("N")) {
                            Monday_SetAvaibilityTime.setH11("Y");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH11().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH11("N");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH11().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH11("Y");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH11().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH11("N");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH11().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH11("Y");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH11().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH11("N");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH11().equals("N")) {
                            Thursday_SetAvaibilityTime.setH11("Y");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH11().equals("Y")) {
                            Friday_SetAvaibilityTime.setH11("N");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH11().equals("N")) {
                            Friday_SetAvaibilityTime.setH11("Y");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH11().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH11("N");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH11().equals("N")) {
                            Saturday_SetAvaibilityTime.setH11("Y");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH11().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH11("N");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH11().equals("N")) {
                            Sunday_SetAvaibilityTime.setH11("Y");
                            Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_12to13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH12().equals("Y")) {
                            Monday_SetAvaibilityTime.setH12("N");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH12().equals("N")) {
                            Monday_SetAvaibilityTime.setH12("Y");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH12().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH12("N");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH12().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH12("Y");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH12().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH12("N");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH12().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH12("Y");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH12().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH12("N");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH12().equals("N")) {
                            Thursday_SetAvaibilityTime.setH12("Y");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH12().equals("Y")) {
                            Friday_SetAvaibilityTime.setH12("N");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH12().equals("N")) {
                            Friday_SetAvaibilityTime.setH12("Y");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH12().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH12("N");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH12().equals("N")) {
                            Saturday_SetAvaibilityTime.setH12("Y");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH12().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH12("N");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH12().equals("N")) {
                            Sunday_SetAvaibilityTime.setH12("Y");
                            Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Sunday_SetAvaibilityTime.getH8().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH12().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH10().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH11().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH12().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH13().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH14().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH15().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH16().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH17().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH18().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH19().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH20().equals("Y")
                                    && Sunday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                }

            }
        });
        Avaibility_Time_13to14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH13().equals("Y")) {
                            Monday_SetAvaibilityTime.setH13("N");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH13().equals("N")) {
                            Monday_SetAvaibilityTime.setH13("Y");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH13().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH13("N");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH13().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH13("Y");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH13().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH13("N");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH13().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH13("Y");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Wednesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Wednesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH13().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH13("N");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH13().equals("N")) {
                            Thursday_SetAvaibilityTime.setH13("Y");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH13().equals("Y")) {
                            Friday_SetAvaibilityTime.setH13("N");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH13().equals("N")) {
                            Friday_SetAvaibilityTime.setH13("Y");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH13().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH13("N");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH13().equals("N")) {
                            Saturday_SetAvaibilityTime.setH13("Y");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH13().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH13("N");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH13().equals("N")) {
                            Sunday_SetAvaibilityTime.setH13("Y");
                            Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_14to15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH14().equals("Y")) {
                            Monday_SetAvaibilityTime.setH14("N");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH14().equals("N")) {
                            Monday_SetAvaibilityTime.setH14("Y");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH14().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH14("N");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH14().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH14("Y");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH14().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH14("N");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH14().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH14("Y");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH14().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH14("N");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH14().equals("N")) {
                            Thursday_SetAvaibilityTime.setH14("Y");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH14().equals("Y")) {
                            Friday_SetAvaibilityTime.setH14("N");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH14().equals("N")) {
                            Friday_SetAvaibilityTime.setH14("Y");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH14().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH14("N");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH14().equals("N")) {
                            Saturday_SetAvaibilityTime.setH14("Y");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH14().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH14("N");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH14().equals("N")) {
                            Sunday_SetAvaibilityTime.setH14("Y");
                            Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_15to16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH15().equals("Y")) {
                            Monday_SetAvaibilityTime.setH15("N");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH15().equals("N")) {
                            Monday_SetAvaibilityTime.setH15("Y");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH15().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH15("N");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH15().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH15("Y");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH15().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH15("N");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH15().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH15("Y");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH15().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH15("N");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH15().equals("N")) {
                            Thursday_SetAvaibilityTime.setH15("Y");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH15().equals("Y")) {
                            Friday_SetAvaibilityTime.setH15("N");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH15().equals("N")) {
                            Friday_SetAvaibilityTime.setH15("Y");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH15().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH15("N");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH15().equals("N")) {
                            Saturday_SetAvaibilityTime.setH15("Y");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH15().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH15("N");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH15().equals("N")) {
                            Sunday_SetAvaibilityTime.setH15("Y");
                            Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_16to17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":
                        if (Monday_SetAvaibilityTime.getH16().equals("Y")) {
                            Monday_SetAvaibilityTime.setH16("N");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH16().equals("N")) {
                            Monday_SetAvaibilityTime.setH16("Y");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH16().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH16("N");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH16().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH16("Y");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH16().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH16("N");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH16().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH16("Y");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH16().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH16("N");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH16().equals("N")) {
                            Thursday_SetAvaibilityTime.setH16("Y");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH16().equals("Y")) {
                            Friday_SetAvaibilityTime.setH16("N");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH16().equals("N")) {
                            Friday_SetAvaibilityTime.setH16("Y");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH16().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH16("N");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH16().equals("N")) {
                            Saturday_SetAvaibilityTime.setH16("Y");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH16().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH16("N");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH16().equals("N")) {
                            Sunday_SetAvaibilityTime.setH16("Y");
                            Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_17to18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH17().equals("Y")) {
                            Monday_SetAvaibilityTime.setH17("N");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH17().equals("N")) {
                            Monday_SetAvaibilityTime.setH17("Y");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH17().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH17("N");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH17().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH17("Y");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH17().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH17("N");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH17().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH17("Y");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH17().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH17("N");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH17().equals("N")) {
                            Thursday_SetAvaibilityTime.setH17("Y");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH17().equals("Y")) {
                            Friday_SetAvaibilityTime.setH17("N");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH17().equals("N")) {
                            Friday_SetAvaibilityTime.setH17("Y");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH17().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH17("N");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH17().equals("N")) {
                            Saturday_SetAvaibilityTime.setH17("Y");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH17().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH17("N");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH17().equals("N")) {
                            Sunday_SetAvaibilityTime.setH17("Y");
                            Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });

        Avaibility_Time_18to19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH18().equals("Y")) {
                            Monday_SetAvaibilityTime.setH18("N");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH18().equals("N")) {
                            Monday_SetAvaibilityTime.setH18("Y");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH18().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH18("N");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH18().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH18("Y");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH18().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH18("N");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH18().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH18("Y");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH18().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH18("N");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH18().equals("N")) {
                            Thursday_SetAvaibilityTime.setH18("Y");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH18().equals("Y")) {
                            Friday_SetAvaibilityTime.setH18("N");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH18().equals("N")) {
                            Friday_SetAvaibilityTime.setH18("Y");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH18().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH18("N");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH18().equals("N")) {
                            Saturday_SetAvaibilityTime.setH18("Y");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH18().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH18("N");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH18().equals("N")) {
                            Sunday_SetAvaibilityTime.setH18("Y");
                            Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });
        Avaibility_Time_19to20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH19().equals("Y")) {
                            Monday_SetAvaibilityTime.setH19("N");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH19().equals("N")) {
                            Monday_SetAvaibilityTime.setH19("Y");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH19().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH19("N");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH19().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH19("Y");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH19().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH19("N");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH19().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH19("Y");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH19().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH19("N");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH19().equals("N")) {
                            Thursday_SetAvaibilityTime.setH19("Y");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH19().equals("Y")) {
                            Friday_SetAvaibilityTime.setH19("N");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH19().equals("N")) {
                            Friday_SetAvaibilityTime.setH19("Y");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH19().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH19("N");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH19().equals("N")) {
                            Saturday_SetAvaibilityTime.setH19("Y");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH19().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH19("N");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH19().equals("N")) {
                            Sunday_SetAvaibilityTime.setH19("Y");
                            Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });

        Avaibility_Time_20to21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH20().equals("Y")) {
                            Monday_SetAvaibilityTime.setH20("N");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH20().equals("N")) {
                            Monday_SetAvaibilityTime.setH20("Y");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH20().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH20("N");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH20().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH20("Y");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH20().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH20("N");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH20().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH20("Y");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH20().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH20("N");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH20().equals("N")) {
                            Thursday_SetAvaibilityTime.setH20("Y");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH20().equals("Y")) {
                            Friday_SetAvaibilityTime.setH20("N");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH20().equals("N")) {
                            Friday_SetAvaibilityTime.setH20("Y");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH20().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH20("N");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH20().equals("N")) {
                            Saturday_SetAvaibilityTime.setH20("Y");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH20().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH20("N");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH20().equals("N")) {
                            Sunday_SetAvaibilityTime.setH20("Y");
                            Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });

        Avaibility_Time_21to22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Current_Selected_Day) {
                    case "Monday":

                        if (Monday_SetAvaibilityTime.getH21().equals("Y")) {
                            Monday_SetAvaibilityTime.setH21("N");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Monday_SetAvaibilityTime.getH21().equals("N")) {
                            Monday_SetAvaibilityTime.setH21("Y");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Monday_SetAvaibilityTime.getH8().equals("Y")
                                    && Monday_SetAvaibilityTime.getH9().equals("Y")
                                    && Monday_SetAvaibilityTime.getH10().equals("Y")
                                    && Monday_SetAvaibilityTime.getH11().equals("Y")
                                    && Monday_SetAvaibilityTime.getH12().equals("Y")
                                    && Monday_SetAvaibilityTime.getH13().equals("Y")
                                    && Monday_SetAvaibilityTime.getH14().equals("Y")
                                    && Monday_SetAvaibilityTime.getH15().equals("Y")
                                    && Monday_SetAvaibilityTime.getH16().equals("Y")
                                    && Monday_SetAvaibilityTime.getH17().equals("Y")
                                    && Monday_SetAvaibilityTime.getH18().equals("Y")
                                    && Monday_SetAvaibilityTime.getH19().equals("Y")
                                    && Monday_SetAvaibilityTime.getH20().equals("Y")
                                    && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }

                        break;
                    case "Tuesday":
                        if (Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                            Tuesday_SetAvaibilityTime.setH21("N");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        } else if (Tuesday_SetAvaibilityTime.getH21().equals("N")) {
                            Tuesday_SetAvaibilityTime.setH21("Y");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                                    && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                                Avaibility_Time_All.setChecked(true);
                            } else {
                                Avaibility_Time_All.setChecked(false);

                            }
                        }
                        break;
                    case "Wednesday":
                        if (Wednesday_SetAvaibilityTime.getH21().equals("Y")) {
                            Wednesday_SetAvaibilityTime.setH21("N");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Wednesday_SetAvaibilityTime.getH21().equals("N")) {
                            Wednesday_SetAvaibilityTime.setH21("Y");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Thursday":
                        if (Thursday_SetAvaibilityTime.getH21().equals("Y")) {
                            Thursday_SetAvaibilityTime.setH21("N");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Thursday_SetAvaibilityTime.getH21().equals("N")) {
                            Thursday_SetAvaibilityTime.setH21("Y");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }

                        break;
                    case "Friday":
                        if (Friday_SetAvaibilityTime.getH21().equals("Y")) {
                            Friday_SetAvaibilityTime.setH21("N");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Friday_SetAvaibilityTime.getH21().equals("N")) {
                            Friday_SetAvaibilityTime.setH21("Y");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Saturday":
                        if (Saturday_SetAvaibilityTime.getH21().equals("Y")) {
                            Saturday_SetAvaibilityTime.setH21("N");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Saturday_SetAvaibilityTime.getH21().equals("N")) {
                            Saturday_SetAvaibilityTime.setH21("Y");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                    case "Sunday":
                        if (Sunday_SetAvaibilityTime.getH21().equals("Y")) {
                            Sunday_SetAvaibilityTime.setH21("N");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            time_all();
                        } else if (Sunday_SetAvaibilityTime.getH21().equals("N")) {
                            Sunday_SetAvaibilityTime.setH21("Y");
                            Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                            Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                            time_all();
                        }
                        break;
                }

            }
        });


        Avaibility_Main_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleTonProcess.show(CalenderAvaibility.this);
                if (r_hab.isChecked()) {
                    if ((!Weeks_Spinner.getText().toString().equals(""))) {
                        setAvaibilityTimeArrayList.clear();
                        setAvaibilityTimeArrayList.add(Monday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Tuesday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Wednesday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Thursday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Friday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Saturday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Sunday_SetAvaibilityTime);
                        SetAvaibilityData avaibilityData = new SetAvaibilityData();
                        avaibilityData.setSetAvaibilityTimes(setAvaibilityTimeArrayList);
                        apiInterface.coachsetinsertavailabilty(avaibilityData).enqueue(new Callback<GetCoachCollectiveOnDemandResponseData>() {
                            @Override
                            public void onResponse(@NonNull Call<GetCoachCollectiveOnDemandResponseData> call, @NonNull Response<GetCoachCollectiveOnDemandResponseData> response) {
                                assert response.body() != null;
                                if (response.body().getIsSuccess().equals("true")) {
                                     singleTonProcess.dismiss();
                                    Toast.makeText(CalenderAvaibility.this, "Inséré avec succès", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else {
                                     singleTonProcess.dismiss();
                                    Toast.makeText(CalenderAvaibility.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<GetCoachCollectiveOnDemandResponseData> call, Throwable t) {
                                 singleTonProcess.dismiss();
                                System.out.println("---> " + call + " " + t);
                            }
                        });

                    } else {
                         singleTonProcess.dismiss();
                        Toast.makeText(getApplicationContext(), "Veuillez sélectionner la semaine", Toast.LENGTH_LONG).show();
                    }
                } else if (r_quo.isChecked()) {

                    if (((!From_Date.getText().toString().equals("") && !To_Date.getText().toString().equals("")))) {
                      String stage_from = "",stage_to = "";
                        SimpleDateFormat sdfto = new  SimpleDateFormat("yyyy-MM-dd");
                        Date dateto = null;
                        try {
                            dateto = sdfto.parse(From_Date.getText().toString());
                            stage_from = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(dateto);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            dateto = sdfto.parse(To_Date.getText().toString());
                            stage_to = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(dateto);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                        Monday_SetAvaibilityTime.setStart_Date(stage_from);
                        Monday_SetAvaibilityTime.setEnd_Date(stage_to);
                        Monday_SetAvaibilityTime.setDate("");
                        Monday_SetAvaibilityTime.setCoach_Id(coachid_);
                        Tuesday_SetAvaibilityTime.setStart_Date(stage_from);
                        Tuesday_SetAvaibilityTime.setEnd_Date(stage_to);
                        Tuesday_SetAvaibilityTime.setDate("Invaild Date");
                        Tuesday_SetAvaibilityTime.setCoach_Id(coachid_);
                        Wednesday_SetAvaibilityTime.setStart_Date(stage_from);
                        Wednesday_SetAvaibilityTime.setEnd_Date(stage_to);
                        Wednesday_SetAvaibilityTime.setDate("Invaild Date");
                        Wednesday_SetAvaibilityTime.setCoach_Id(coachid_);
                        Thursday_SetAvaibilityTime.setStart_Date(stage_from);
                        Thursday_SetAvaibilityTime.setEnd_Date(stage_to);
                        Thursday_SetAvaibilityTime.setDate("Invaild Date");
                        Thursday_SetAvaibilityTime.setCoach_Id(coachid_);
                        Friday_SetAvaibilityTime.setStart_Date(stage_from);
                        Friday_SetAvaibilityTime.setEnd_Date(stage_to);
                        Friday_SetAvaibilityTime.setDate("Invaild Date");
                        Friday_SetAvaibilityTime.setCoach_Id(coachid_);
                        Saturday_SetAvaibilityTime.setStart_Date(stage_from);
                        Saturday_SetAvaibilityTime.setEnd_Date(stage_to);
                        Saturday_SetAvaibilityTime.setDate("Invaild Date");
                        Saturday_SetAvaibilityTime.setCoach_Id(coachid_);
                        Sunday_SetAvaibilityTime.setStart_Date(stage_from);
                        Sunday_SetAvaibilityTime.setEnd_Date(stage_to);
                        Sunday_SetAvaibilityTime.setDate("Invaild Date");
                        Sunday_SetAvaibilityTime.setCoach_Id(coachid_);

                        setAvaibilityTimeArrayList.clear();
                        setAvaibilityTimeArrayList.add(Monday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Tuesday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Wednesday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Thursday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Friday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Saturday_SetAvaibilityTime);
                        setAvaibilityTimeArrayList.add(Sunday_SetAvaibilityTime);
                        SetAvaibilityData avaibilityData = new SetAvaibilityData();
                        avaibilityData.setSetAvaibilityTimes(setAvaibilityTimeArrayList);

//                        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListAll = new ArrayList<SetAvaibilityTime>();

//                        setAvaibilityTimeArrayListAll.addAll(getMonday(From_Date.getText().toString(),To_Date.getText().toString()));
//                        setAvaibilityTimeArrayListAll.addAll(getTuesday(From_Date.getText().toString(), To_Date.getText().toString()));
//                        setAvaibilityTimeArrayListAll.addAll(getWednesday(From_Date.getText().toString(), To_Date.getText().toString()));
//                        setAvaibilityTimeArrayListAll.addAll(getThursday(From_Date.getText().toString(), To_Date.getText().toString()));
//                        setAvaibilityTimeArrayListAll.addAll(getFriday(From_Date.getText().toString(), To_Date.getText().toString()));
//                        setAvaibilityTimeArrayListAll.addAll(getSaturday(From_Date.getText().toString(), To_Date.getText().toString()));
//                        setAvaibilityTimeArrayListAll.addAll(getSunday(From_Date.getText().toString(),To_Date.getText().toString()));
//
////                        System.out.println("ccc---> " + new Gson().toJson(getSunday(From_Date.getText().toString(), To_Date.getText().toString())));
////                        setAvaibilityTimeArrayList.add(Monday_SetAvaibilityTime);
////                        setAvaibilityTimeArrayList.add(Tuesday_SetAvaibilityTime);
////                        setAvaibilityTimeArrayList.add(Wednesday_SetAvaibilityTime);
////                        setAvaibilityTimeArrayList.add(Thursday_SetAvaibilityTime);
////                        setAvaibilityTimeArrayList.add(Friday_SetAvaibilityTime);
////                        setAvaibilityTimeArrayList.add(Saturday_SetAvaibilityTime);
////                        setAvaibilityTimeArrayList.add(Sunday_SetAvaibilityTime);
//
//                        SetAvaibilityData avaibilityData = new SetAvaibilityData();
//                        avaibilityData.setSetAvaibilityTimes(setAvaibilityTimeArrayListAll);



                        for (int i = 0; avaibilityData.getSetAvaibilityTimes().size() > i; i++) {
                            System.out.println("avaibilityData.getSetAvaibilityTimes()---> " + i + " " + new Gson().toJson(avaibilityData.getSetAvaibilityTimes().get(i)));
                        }
//                        Log.d("tag", new Gson().toJsonTree(avaibilityData).toString());
//
//                        System.out.println("avaibilityData---> "+ new Gson().toJsonTree(avaibilityData));

                        apiInterface.coachsetinsertavailabilty(avaibilityData).enqueue(new Callback<GetCoachCollectiveOnDemandResponseData>() {
                            @Override
                            public void onResponse(@NonNull Call<GetCoachCollectiveOnDemandResponseData> call, @NonNull Response<GetCoachCollectiveOnDemandResponseData> response) {
                                assert response.body() != null;
                                if (response.body().getIsSuccess().equals("true")) {
                                     singleTonProcess.dismiss();
                                    Toast.makeText(CalenderAvaibility.this, "Inséré avec succès", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else {
                                     singleTonProcess.dismiss();
                                    Toast.makeText(CalenderAvaibility.this, response.body().getMessage() , Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<GetCoachCollectiveOnDemandResponseData> call, Throwable t) {
                                 singleTonProcess.dismiss();
                                System.out.println("---> " + call + " " + t);
                            }
                        });

                    } else {
                         singleTonProcess.dismiss();
                        Toast.makeText(getApplicationContext(), "Veuillez sélectionner la date", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


    }


    private void form_dat() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String myFormat_tosend = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf_tosend = new SimpleDateFormat(myFormat_tosend, Locale.US);
        From_Date.setText(sdf.format(myCalendar.getTime()));
    }

    private void to_dat() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String myFormat_tosend = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf_tosend = new SimpleDateFormat(myFormat_tosend, Locale.US);
        To_Date.setText(sdf.format(myCalendar.getTime()));
    }

    private void enableAvaibility() {

        Avaibility_Sunday.setEnabled(true);
        Avaibility_Monday.setEnabled(true);
        Avaibility_Tuesday.setEnabled(true);
        Avaibility_Wednesday.setEnabled(true);
        Avaibility_Thursday.setEnabled(true);
        Avaibility_Friday.setEnabled(true);
        Avaibility_Saturday.setEnabled(true);

        Avaibility_Time_8to9.setEnabled(true);
        Avaibility_Time_9to10.setEnabled(true);
        Avaibility_Time_10to11.setEnabled(true);
        Avaibility_Time_11to12.setEnabled(true);
        Avaibility_Time_12to13.setEnabled(true);
        Avaibility_Time_13to14.setEnabled(true);
        Avaibility_Time_14to15.setEnabled(true);
        Avaibility_Time_15to16.setEnabled(true);
        Avaibility_Time_16to17.setEnabled(true);
        Avaibility_Time_17to18.setEnabled(true);
        Avaibility_Time_18to19.setEnabled(true);
        Avaibility_Time_19to20.setEnabled(true);
        Avaibility_Time_20to21.setEnabled(true);
        Avaibility_Time_21to22.setEnabled(true);
        Avaibility_Time_All.setEnabled(true);

    }

    private void disEnableAvaibility() {

        Avaibility_Sunday.setEnabled(false);
        Avaibility_Monday.setEnabled(false);
        Avaibility_Tuesday.setEnabled(false);
        Avaibility_Wednesday.setEnabled(false);
        Avaibility_Thursday.setEnabled(false);
        Avaibility_Friday.setEnabled(false);
        Avaibility_Saturday.setEnabled(false);

        Avaibility_Time_8to9.setEnabled(false);
        Avaibility_Time_9to10.setEnabled(false);
        Avaibility_Time_10to11.setEnabled(false);
        Avaibility_Time_11to12.setEnabled(false);
        Avaibility_Time_12to13.setEnabled(false);
        Avaibility_Time_13to14.setEnabled(false);
        Avaibility_Time_14to15.setEnabled(false);
        Avaibility_Time_15to16.setEnabled(false);
        Avaibility_Time_16to17.setEnabled(false);
        Avaibility_Time_17to18.setEnabled(false);
        Avaibility_Time_18to19.setEnabled(false);
        Avaibility_Time_19to20.setEnabled(false);
        Avaibility_Time_20to21.setEnabled(false);
        Avaibility_Time_21to22.setEnabled(false);
        Avaibility_Time_All.setEnabled(false);

    }

    public void day_time_selectyion() {

        switch (Current_Selected_Day) {
            case "Monday":

                if (Monday_SetAvaibilityTime.getH8().equals("Y")) {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH9().equals("Y")) {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH10().equals("Y")) {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH11().equals("Y")) {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH12().equals("Y")) {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH13().equals("Y")) {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH14().equals("Y")) {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH15().equals("Y")) {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH16().equals("Y")) {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH17().equals("Y")) {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH18().equals("Y")) {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH19().equals("Y")) {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH20().equals("Y")) {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Monday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }

                break;
            case "Tuesday":

                if (Tuesday_SetAvaibilityTime.getH8().equals("Y")) {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH9().equals("Y")) {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH10().equals("Y")) {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH11().equals("Y")) {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH12().equals("Y")) {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH13().equals("Y")) {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH14().equals("Y")) {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH15().equals("Y")) {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH16().equals("Y")) {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH17().equals("Y")) {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH18().equals("Y")) {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH19().equals("Y")) {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH20().equals("Y")) {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }

                break;
            case "Wednesday":

                if (Wednesday_SetAvaibilityTime.getH8().equals("Y")) {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH9().equals("Y")) {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH10().equals("Y")) {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH11().equals("Y")) {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH12().equals("Y")) {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH13().equals("Y")) {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH14().equals("Y")) {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH15().equals("Y")) {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH16().equals("Y")) {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH17().equals("Y")) {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH18().equals("Y")) {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH19().equals("Y")) {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH20().equals("Y")) {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Wednesday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                break;
            case "Thursday":

                if (Thursday_SetAvaibilityTime.getH8().equals("Y")) {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH9().equals("Y")) {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH10().equals("Y")) {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH11().equals("Y")) {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH12().equals("Y")) {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH13().equals("Y")) {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH14().equals("Y")) {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH15().equals("Y")) {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH16().equals("Y")) {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH17().equals("Y")) {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH18().equals("Y")) {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH19().equals("Y")) {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH20().equals("Y")) {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Thursday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                break;
            case "Friday":

                if (Friday_SetAvaibilityTime.getH8().equals("Y")) {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH9().equals("Y")) {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH10().equals("Y")) {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH11().equals("Y")) {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH12().equals("Y")) {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH13().equals("Y")) {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH14().equals("Y")) {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH15().equals("Y")) {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH16().equals("Y")) {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH17().equals("Y")) {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH18().equals("Y")) {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH19().equals("Y")) {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH20().equals("Y")) {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Friday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                break;
            case "Saturday":

                if (Saturday_SetAvaibilityTime.getH8().equals("Y")) {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH9().equals("Y")) {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH10().equals("Y")) {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH11().equals("Y")) {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH12().equals("Y")) {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH13().equals("Y")) {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH14().equals("Y")) {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH15().equals("Y")) {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH16().equals("Y")) {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH17().equals("Y")) {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH18().equals("Y")) {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH19().equals("Y")) {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH20().equals("Y")) {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Saturday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                break;
            case "Sunday":

                if (Sunday_SetAvaibilityTime.getH8().equals("Y")) {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_8to9.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_8to9.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH9().equals("Y")) {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_9to10.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_9to10.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH10().equals("Y")) {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_10to11.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_10to11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH11().equals("Y")) {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_11to12.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_11to12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH12().equals("Y")) {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_12to13.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_12to13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH13().equals("Y")) {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_13to14.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_13to14.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH14().equals("Y")) {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_14to15.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_14to15.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH15().equals("Y")) {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_15to16.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_15to16.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH16().equals("Y")) {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_16to17.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_16to17.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH17().equals("Y")) {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_17to18.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_17to18.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH18().equals("Y")) {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_18to19.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_18to19.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH19().equals("Y")) {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_19to20.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_19to20.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH20().equals("Y")) {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_20to21.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_20to21.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }
                if (Sunday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGoogleGreen)));
                } else {
                    Avaibility_Time_21to22.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blackcolor)));
                    Avaibility_Time_21to22.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
                }

                break;
        }

    }

    public void time_all() {
        switch (Current_Selected_Day) {
            case "Monday":
                if (Monday_SetAvaibilityTime.getH8().equals("Y")
                        && Monday_SetAvaibilityTime.getH9().equals("Y")
                        && Monday_SetAvaibilityTime.getH10().equals("Y")
                        && Monday_SetAvaibilityTime.getH11().equals("Y")
                        && Monday_SetAvaibilityTime.getH12().equals("Y")
                        && Monday_SetAvaibilityTime.getH13().equals("Y")
                        && Monday_SetAvaibilityTime.getH14().equals("Y")
                        && Monday_SetAvaibilityTime.getH15().equals("Y")
                        && Monday_SetAvaibilityTime.getH16().equals("Y")
                        && Monday_SetAvaibilityTime.getH17().equals("Y")
                        && Monday_SetAvaibilityTime.getH18().equals("Y")
                        && Monday_SetAvaibilityTime.getH19().equals("Y")
                        && Monday_SetAvaibilityTime.getH20().equals("Y")
                        && Monday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_All.setChecked(true);
                } else {
                    Avaibility_Time_All.setChecked(false);
                }

                break;

            case "Tuesday":

                if (Tuesday_SetAvaibilityTime.getH8().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH9().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH10().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH11().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH12().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH13().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH14().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH15().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH16().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH17().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH18().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH19().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH20().equals("Y")
                        && Tuesday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_All.setChecked(true);
                } else {
                    Avaibility_Time_All.setChecked(false);

                }

                break;
            case "Wednesday":

                if (Wednesday_SetAvaibilityTime.getH8().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH9().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH10().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH11().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH12().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH13().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH14().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH15().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH16().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH17().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH18().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH19().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH20().equals("Y")
                        && Wednesday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_All.setChecked(true);
                } else {
                    Avaibility_Time_All.setChecked(false);

                }

                break;
            case "Thursday":

                if (Thursday_SetAvaibilityTime.getH8().equals("Y")
                        && Thursday_SetAvaibilityTime.getH9().equals("Y")
                        && Thursday_SetAvaibilityTime.getH10().equals("Y")
                        && Thursday_SetAvaibilityTime.getH11().equals("Y")
                        && Thursday_SetAvaibilityTime.getH12().equals("Y")
                        && Thursday_SetAvaibilityTime.getH13().equals("Y")
                        && Thursday_SetAvaibilityTime.getH14().equals("Y")
                        && Thursday_SetAvaibilityTime.getH15().equals("Y")
                        && Thursday_SetAvaibilityTime.getH16().equals("Y")
                        && Thursday_SetAvaibilityTime.getH17().equals("Y")
                        && Thursday_SetAvaibilityTime.getH18().equals("Y")
                        && Thursday_SetAvaibilityTime.getH19().equals("Y")
                        && Thursday_SetAvaibilityTime.getH20().equals("Y")
                        && Thursday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_All.setChecked(true);
                } else {
                    Avaibility_Time_All.setChecked(false);

                }


                break;

            case "Friday":
                if (Friday_SetAvaibilityTime.getH8().equals("Y")
                        && Friday_SetAvaibilityTime.getH9().equals("Y")
                        && Friday_SetAvaibilityTime.getH10().equals("Y")
                        && Friday_SetAvaibilityTime.getH11().equals("Y")
                        && Friday_SetAvaibilityTime.getH12().equals("Y")
                        && Friday_SetAvaibilityTime.getH13().equals("Y")
                        && Friday_SetAvaibilityTime.getH14().equals("Y")
                        && Friday_SetAvaibilityTime.getH15().equals("Y")
                        && Friday_SetAvaibilityTime.getH16().equals("Y")
                        && Friday_SetAvaibilityTime.getH17().equals("Y")
                        && Friday_SetAvaibilityTime.getH18().equals("Y")
                        && Friday_SetAvaibilityTime.getH19().equals("Y")
                        && Friday_SetAvaibilityTime.getH20().equals("Y")
                        && Friday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_All.setChecked(true);
                } else {
                    Avaibility_Time_All.setChecked(false);

                }

                break;
            case "Saturday":

                if (Saturday_SetAvaibilityTime.getH8().equals("Y")
                        && Saturday_SetAvaibilityTime.getH9().equals("Y")
                        && Saturday_SetAvaibilityTime.getH10().equals("Y")
                        && Saturday_SetAvaibilityTime.getH11().equals("Y")
                        && Saturday_SetAvaibilityTime.getH12().equals("Y")
                        && Saturday_SetAvaibilityTime.getH13().equals("Y")
                        && Saturday_SetAvaibilityTime.getH14().equals("Y")
                        && Saturday_SetAvaibilityTime.getH15().equals("Y")
                        && Saturday_SetAvaibilityTime.getH16().equals("Y")
                        && Saturday_SetAvaibilityTime.getH17().equals("Y")
                        && Saturday_SetAvaibilityTime.getH18().equals("Y")
                        && Saturday_SetAvaibilityTime.getH19().equals("Y")
                        && Saturday_SetAvaibilityTime.getH20().equals("Y")
                        && Saturday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_All.setChecked(true);
                } else {
                    Avaibility_Time_All.setChecked(false);

                }

                break;
            case "Sunday":

                if (Sunday_SetAvaibilityTime.getH8().equals("Y")
                        && Sunday_SetAvaibilityTime.getH9().equals("Y")
                        && Sunday_SetAvaibilityTime.getH10().equals("Y")
                        && Sunday_SetAvaibilityTime.getH11().equals("Y")
                        && Sunday_SetAvaibilityTime.getH12().equals("Y")
                        && Sunday_SetAvaibilityTime.getH13().equals("Y")
                        && Sunday_SetAvaibilityTime.getH14().equals("Y")
                        && Sunday_SetAvaibilityTime.getH15().equals("Y")
                        && Sunday_SetAvaibilityTime.getH16().equals("Y")
                        && Sunday_SetAvaibilityTime.getH17().equals("Y")
                        && Sunday_SetAvaibilityTime.getH18().equals("Y")
                        && Sunday_SetAvaibilityTime.getH19().equals("Y")
                        && Sunday_SetAvaibilityTime.getH20().equals("Y")
                        && Sunday_SetAvaibilityTime.getH21().equals("Y")) {
                    Avaibility_Time_All.setChecked(true);
                } else {
                    Avaibility_Time_All.setChecked(false);

                }

                break;
        }

    }

    public void getDates() {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(From_Date.getText().toString());
            date2 = df1.parse(To_Date.getText().toString());
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);


            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            while (cal1.before(cal2) || cal1.equals(cal2)) {
//                System.out.println("cal2.getTime()" +cal2.getTime());
//
//                System.out.println("cal1.getTime()" +cal1.getTime());
                dates.add(cal1.getTime());
                cal1.add(Calendar.DATE, 1);
                System.out.println("dates.add(cal1.getTime())" + dates);
            }
//            if(cal1.equals(cal2)){
//                dates.add(cal1.getTime());
//                System.out.println("dates.add(cal1.getTime())" + dates);
//            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public ArrayList<SetAvaibilityTime> getMonday(String fdate, String ldate) {

        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListMonday = new ArrayList<SetAvaibilityTime>();

        try {
            //  mondayDates.clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar scal = Calendar.getInstance();
            scal.setTime(dateFormat.parse(fdate));
            Calendar ecal = Calendar.getInstance();
            ecal.setTime(dateFormat.parse(ldate));
            scal.add(Calendar.DATE, 0);
            while (!scal.equals(ecal)) {
                if (scal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    SetAvaibilityTime Monday = new SetAvaibilityTime();
                    Monday.setH8(Monday_SetAvaibilityTime.getH8());
                    Monday.setH9(Monday_SetAvaibilityTime.getH9());
                    Monday.setH10(Monday_SetAvaibilityTime.getH10());
                    Monday.setH11(Monday_SetAvaibilityTime.getH11());
                    Monday.setH12(Monday_SetAvaibilityTime.getH12());
                    Monday.setH13(Monday_SetAvaibilityTime.getH13());
                    Monday.setH14(Monday_SetAvaibilityTime.getH14());
                    Monday.setH15(Monday_SetAvaibilityTime.getH15());
                    Monday.setH16(Monday_SetAvaibilityTime.getH16());
                    Monday.setH17(Monday_SetAvaibilityTime.getH17());
                    Monday.setH18(Monday_SetAvaibilityTime.getH18());
                    Monday.setH19(Monday_SetAvaibilityTime.getH19());
                    Monday.setH20(Monday_SetAvaibilityTime.getH20());
                    Monday.setH21(Monday_SetAvaibilityTime.getH21());
                    Monday.setH22(Monday_SetAvaibilityTime.getH22());

                    Monday.setCoach_Id(coachid_);
                    Monday.setDate(simpleDateFormat.format(scal.getTime()));
                    Monday.setStart_Date(fdate);
                    Monday.setEnd_Date(ldate);

                    System.out.println(simpleDateFormat.format(scal.getTime()));
//                    Monday_SetAvaibilityTime.setCoach_Id(coachid_);
//                    Monday_SetAvaibilityTime.setDate(simpleDateFormat.format(scal.getTime()));
//                    Monday_SetAvaibilityTime.setStart_Date(fdate);
//                    Monday_SetAvaibilityTime.setEnd_Date(ldate);
                    System.out.println("Monday---> " + new Gson().toJson(Monday));
                    setAvaibilityTimeArrayListMonday.add(Monday);
//                    dayWiselistObjects.add(new DayWiselistObject("Monday",simpleDateFormat.format(scal.getTime())));
                }
                scal.add(Calendar.DATE, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return setAvaibilityTimeArrayListMonday;
    }

    public ArrayList<SetAvaibilityTime> getTuesday(String fdate, String ldate) {
        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListTuesday = new ArrayList<SetAvaibilityTime>();

        try {
            //  thuesdayDates.clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar scal = Calendar.getInstance();
            scal.setTime(dateFormat.parse(fdate));
            Calendar ecal = Calendar.getInstance();
            ecal.setTime(dateFormat.parse(ldate));
            scal.add(Calendar.DATE, 0);
            while (!scal.equals(ecal)) {
                if (scal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    SetAvaibilityTime Tuesday = new SetAvaibilityTime();
                    Tuesday.setH8(Tuesday_SetAvaibilityTime.getH8());
                    Tuesday.setH9(Tuesday_SetAvaibilityTime.getH9());
                    Tuesday.setH10(Tuesday_SetAvaibilityTime.getH10());
                    Tuesday.setH11(Tuesday_SetAvaibilityTime.getH11());
                    Tuesday.setH12(Tuesday_SetAvaibilityTime.getH12());
                    Tuesday.setH13(Tuesday_SetAvaibilityTime.getH13());
                    Tuesday.setH14(Tuesday_SetAvaibilityTime.getH14());
                    Tuesday.setH15(Tuesday_SetAvaibilityTime.getH15());
                    Tuesday.setH16(Tuesday_SetAvaibilityTime.getH16());
                    Tuesday.setH17(Tuesday_SetAvaibilityTime.getH17());
                    Tuesday.setH18(Tuesday_SetAvaibilityTime.getH18());
                    Tuesday.setH19(Tuesday_SetAvaibilityTime.getH19());
                    Tuesday.setH20(Tuesday_SetAvaibilityTime.getH20());
                    Tuesday.setH21(Tuesday_SetAvaibilityTime.getH21());
                    Tuesday.setH22(Tuesday_SetAvaibilityTime.getH22());

                    Tuesday.setCoach_Id(coachid_);
                    Tuesday.setDate(simpleDateFormat.format(scal.getTime()));
                    Tuesday.setStart_Date(fdate);
                    Tuesday.setEnd_Date(ldate);
                    //     thuesdayDates.add(simpleDateFormat.format(scal.getTime()));
//                    Tuesday_SetAvaibilityTime.setCoach_Id(coachid_);
//                    Tuesday_SetAvaibilityTime.setDate(simpleDateFormat.format(scal.getTime()));
//                    Tuesday_SetAvaibilityTime.setStart_Date(fdate);
//                    Tuesday_SetAvaibilityTime.setEnd_Date(ldate);
                    System.out.println("Tuesday---> " + new Gson().toJson(Tuesday));

                    setAvaibilityTimeArrayListTuesday.add(Tuesday);
                }

                scal.add(Calendar.DATE, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return setAvaibilityTimeArrayListTuesday;
    }

    public ArrayList<SetAvaibilityTime> getWednesday(String fdate, String ldate) {

        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListWednesday = new ArrayList<SetAvaibilityTime>();

        try {
            // wednesdayDates.clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar scal = Calendar.getInstance();
            scal.setTime(dateFormat.parse(fdate));
            Calendar ecal = Calendar.getInstance();
            ecal.setTime(dateFormat.parse(ldate));
            scal.add(Calendar.DATE, 0);
            while (!scal.equals(ecal)) {
                if (scal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    SetAvaibilityTime Wednesday = new SetAvaibilityTime();
                    Wednesday.setH8(Wednesday_SetAvaibilityTime.getH8());
                    Wednesday.setH9(Wednesday_SetAvaibilityTime.getH9());
                    Wednesday.setH10(Wednesday_SetAvaibilityTime.getH10());
                    Wednesday.setH11(Wednesday_SetAvaibilityTime.getH11());
                    Wednesday.setH12(Wednesday_SetAvaibilityTime.getH12());
                    Wednesday.setH13(Wednesday_SetAvaibilityTime.getH13());
                    Wednesday.setH14(Wednesday_SetAvaibilityTime.getH14());
                    Wednesday.setH15(Wednesday_SetAvaibilityTime.getH15());
                    Wednesday.setH16(Wednesday_SetAvaibilityTime.getH16());
                    Wednesday.setH17(Wednesday_SetAvaibilityTime.getH17());
                    Wednesday.setH18(Wednesday_SetAvaibilityTime.getH18());
                    Wednesday.setH19(Wednesday_SetAvaibilityTime.getH19());
                    Wednesday.setH20(Wednesday_SetAvaibilityTime.getH20());
                    Wednesday.setH21(Wednesday_SetAvaibilityTime.getH21());
                    Wednesday.setH22(Wednesday_SetAvaibilityTime.getH22());

                    Wednesday.setCoach_Id(coachid_);
                    Wednesday.setDate(simpleDateFormat.format(scal.getTime()));
                    Wednesday.setStart_Date(fdate);
                    Wednesday.setEnd_Date(ldate);
                    //wednesdayDates.add(simpleDateFormat.format(scal.getTime()));
//                    Wednesday_SetAvaibilityTime.setCoach_Id(coachid_);
//                    Wednesday_SetAvaibilityTime.setDate(simpleDateFormat.format(scal.getTime()));
//                    Wednesday_SetAvaibilityTime.setStart_Date(fdate);
//                    Wednesday_SetAvaibilityTime.setEnd_Date(ldate);
                    System.out.println("Wednesday---> " + new Gson().toJson(Wednesday));

                    setAvaibilityTimeArrayListWednesday.add(Wednesday);
                }
                scal.add(Calendar.DATE, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return setAvaibilityTimeArrayListWednesday;

    }

    public ArrayList<SetAvaibilityTime> getThursday(String fdate, String ldate) {

        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListThursday = new ArrayList<SetAvaibilityTime>();

        try {
            //  thursdayDates.clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar scal = Calendar.getInstance();
            scal.setTime(dateFormat.parse(fdate));
            Calendar ecal = Calendar.getInstance();
            ecal.setTime(dateFormat.parse(ldate));
            scal.add(Calendar.DATE, 0);

            while (!scal.equals(ecal)) {
                if (scal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    SetAvaibilityTime Thursday = new SetAvaibilityTime();
                    Thursday.setH8(Thursday_SetAvaibilityTime.getH8());
                    Thursday.setH9(Thursday_SetAvaibilityTime.getH9());
                    Thursday.setH10(Thursday_SetAvaibilityTime.getH10());
                    Thursday.setH11(Thursday_SetAvaibilityTime.getH11());
                    Thursday.setH12(Thursday_SetAvaibilityTime.getH12());
                    Thursday.setH13(Thursday_SetAvaibilityTime.getH13());
                    Thursday.setH14(Thursday_SetAvaibilityTime.getH14());
                    Thursday.setH15(Thursday_SetAvaibilityTime.getH15());
                    Thursday.setH16(Thursday_SetAvaibilityTime.getH16());
                    Thursday.setH17(Thursday_SetAvaibilityTime.getH17());
                    Thursday.setH18(Thursday_SetAvaibilityTime.getH18());
                    Thursday.setH19(Thursday_SetAvaibilityTime.getH19());
                    Thursday.setH20(Thursday_SetAvaibilityTime.getH20());
                    Thursday.setH21(Thursday_SetAvaibilityTime.getH21());
                    Thursday.setH22(Thursday_SetAvaibilityTime.getH22());

                    Thursday.setCoach_Id(coachid_);
                    Thursday.setDate(simpleDateFormat.format(scal.getTime()));
                    Thursday.setStart_Date(fdate);
                    Thursday.setEnd_Date(ldate);

                    // thursdayDates.add(simpleDateFormat.format(scal.getTime()));
//                    Thursday_SetAvaibilityTime.setCoach_Id(coachid_);
//                    Thursday_SetAvaibilityTime.setDate(simpleDateFormat.format(scal.getTime()));
//                    Thursday_SetAvaibilityTime.setStart_Date(fdate);
//                    Thursday_SetAvaibilityTime.setEnd_Date(ldate);

                    System.out.println("Thursday---> " + new Gson().toJson(Thursday));

                    setAvaibilityTimeArrayListThursday.add(Thursday);
                }

                scal.add(Calendar.DATE, 1);

            }

            scal.add(Calendar.DATE, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return setAvaibilityTimeArrayListThursday;
    }

    public ArrayList<SetAvaibilityTime> getFriday(String fdate, String ldate) {
        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListFriday = new ArrayList<SetAvaibilityTime>();

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar scal = Calendar.getInstance();
            scal.setTime(dateFormat.parse(fdate));
            Calendar ecal = Calendar.getInstance();
            ecal.setTime(dateFormat.parse(ldate));
            scal.add(Calendar.DATE, 0);
            while (!scal.equals(ecal)) {
                if (scal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    SetAvaibilityTime Friday = new SetAvaibilityTime();
                    Friday.setH8(Friday_SetAvaibilityTime.getH8());
                    Friday.setH9(Friday_SetAvaibilityTime.getH9());
                    Friday.setH10(Friday_SetAvaibilityTime.getH10());
                    Friday.setH11(Friday_SetAvaibilityTime.getH11());
                    Friday.setH12(Friday_SetAvaibilityTime.getH12());
                    Friday.setH13(Friday_SetAvaibilityTime.getH13());
                    Friday.setH14(Friday_SetAvaibilityTime.getH14());
                    Friday.setH15(Friday_SetAvaibilityTime.getH15());
                    Friday.setH16(Friday_SetAvaibilityTime.getH16());
                    Friday.setH17(Friday_SetAvaibilityTime.getH17());
                    Friday.setH18(Friday_SetAvaibilityTime.getH18());
                    Friday.setH19(Friday_SetAvaibilityTime.getH19());
                    Friday.setH20(Friday_SetAvaibilityTime.getH20());
                    Friday.setH21(Friday_SetAvaibilityTime.getH21());
                    Friday.setH22(Friday_SetAvaibilityTime.getH22());

                    Friday.setCoach_Id(coachid_);
                    Friday.setDate(simpleDateFormat.format(scal.getTime()));
                    Friday.setStart_Date(fdate);
                    Friday.setEnd_Date(ldate);
                    // fridayDates.add(simpleDateFormat.format(scal.getTime()));
//                    Friday_SetAvaibilityTime.setCoach_Id(coachid_);
//                    Friday_SetAvaibilityTime.setDate(simpleDateFormat.format(scal.getTime()));
//                    Friday_SetAvaibilityTime.setStart_Date(fdate);
//                    Friday_SetAvaibilityTime.setEnd_Date(ldate);
                    System.out.println(" Friday---> " + new Gson().toJson(Friday));

                    setAvaibilityTimeArrayListFriday.add(Friday);
                }

                scal.add(Calendar.DATE, 1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return setAvaibilityTimeArrayListFriday;
    }

    public ArrayList<SetAvaibilityTime> getSaturday(String fdate, String ldate) {

        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListSaturday = new ArrayList<SetAvaibilityTime>();

        try {
            //saturdayDates.clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar scal = Calendar.getInstance();
            scal.setTime(dateFormat.parse(fdate));
            Calendar ecal = Calendar.getInstance();
            ecal.setTime(dateFormat.parse(ldate));
            scal.add(Calendar.DATE, 0);
            while (!scal.equals(ecal)) {

                if (scal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    SetAvaibilityTime Saturday = new SetAvaibilityTime();
                    Saturday.setH8(Saturday_SetAvaibilityTime.getH8());
                    Saturday.setH9(Saturday_SetAvaibilityTime.getH9());
                    Saturday.setH10(Saturday_SetAvaibilityTime.getH10());
                    Saturday.setH11(Saturday_SetAvaibilityTime.getH11());
                    Saturday.setH12(Saturday_SetAvaibilityTime.getH12());
                    Saturday.setH13(Saturday_SetAvaibilityTime.getH13());
                    Saturday.setH14(Saturday_SetAvaibilityTime.getH14());
                    Saturday.setH15(Saturday_SetAvaibilityTime.getH15());
                    Saturday.setH16(Saturday_SetAvaibilityTime.getH16());
                    Saturday.setH17(Saturday_SetAvaibilityTime.getH17());
                    Saturday.setH18(Saturday_SetAvaibilityTime.getH18());
                    Saturday.setH19(Saturday_SetAvaibilityTime.getH19());
                    Saturday.setH20(Saturday_SetAvaibilityTime.getH20());
                    Saturday.setH21(Saturday_SetAvaibilityTime.getH21());
                    Saturday.setH22(Saturday_SetAvaibilityTime.getH22());

                    Saturday.setCoach_Id(coachid_);
                    Saturday.setDate(simpleDateFormat.format(scal.getTime()));
                    Saturday.setStart_Date(fdate);
                    Saturday.setEnd_Date(ldate);
                    // saturdayDates.add(simpleDateFormat.format(scal.getTime()));
//                    Saturday_SetAvaibilityTime.setCoach_Id(coachid_);
//                    Saturday_SetAvaibilityTime.setDate(simpleDateFormat.format(scal.getTime()));
//                    Saturday_SetAvaibilityTime.setStart_Date(fdate);
//                    Saturday_SetAvaibilityTime.setEnd_Date(ldate);
                    System.out.println("Saturday---> " + new Gson().toJson(Saturday));
                    setAvaibilityTimeArrayListSaturday.add(Saturday);
                    System.out.println("setAvaibilityTimeArrayListSaturday---> " + new Gson().toJson(setAvaibilityTimeArrayListSaturday));
                }

                scal.add(Calendar.DATE, 1);
            }
            return setAvaibilityTimeArrayListSaturday;
        } catch (Exception e) {
            e.printStackTrace();
            return setAvaibilityTimeArrayListSaturday;
        }

    }

    public ArrayList<SetAvaibilityTime> getSunday(String fdate, String ldate)  {
        ArrayList<SetAvaibilityTime> setAvaibilityTimeArrayListSunday = new ArrayList<SetAvaibilityTime>();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(dateFormat.parse(fdate));

//        Calendar endCalendar = new GregorianCalendar();
//        endCalendar.setTime(dateFormat.parse(fdate));
//        boolean reachedAFriday = false;
//
//        while (calendar.before(endCalendar)) {
//            Date result = calendar.getTime();
//            calendar.add(Calendar.DATE, 1);
//
//            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
//                reachedAFriday = true;
//            }
//            if (reachedAFriday) {
//                calendar.add(Calendar.DATE, 7);
//            } else {
//                calendar.add(Calendar.DATE, 1);
//            }
//        }

        try {
            //sundayDates.clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar scal = Calendar.getInstance();
            scal.setTime(dateFormat.parse(fdate));
            Calendar ecal = Calendar.getInstance();
            ecal.setTime(dateFormat.parse(ldate));
            scal.add(Calendar.DATE, 0);
            while (!scal.equals(ecal)) {

                if (scal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //   sundayDates.add(simpleDateFormat.format(scal.getTime()));
                    SetAvaibilityTime sunday = new SetAvaibilityTime();
                    sunday.setH8(Sunday_SetAvaibilityTime.getH8());
                    sunday.setH9(Sunday_SetAvaibilityTime.getH9());
                    sunday.setH10(Sunday_SetAvaibilityTime.getH10());
                    sunday.setH11(Sunday_SetAvaibilityTime.getH11());
                    sunday.setH12(Sunday_SetAvaibilityTime.getH12());
                    sunday.setH13(Sunday_SetAvaibilityTime.getH13());
                    sunday.setH14(Sunday_SetAvaibilityTime.getH14());
                    sunday.setH15(Sunday_SetAvaibilityTime.getH15());
                    sunday.setH16(Sunday_SetAvaibilityTime.getH16());
                    sunday.setH17(Sunday_SetAvaibilityTime.getH17());
                    sunday.setH18(Sunday_SetAvaibilityTime.getH18());
                    sunday.setH19(Sunday_SetAvaibilityTime.getH19());
                    sunday.setH20(Sunday_SetAvaibilityTime.getH20());
                    sunday.setH21(Sunday_SetAvaibilityTime.getH21());
                    sunday.setH22(Sunday_SetAvaibilityTime.getH22());

                    sunday.setCoach_Id(coachid_);
                    sunday.setDate(simpleDateFormat.format(scal.getTime()));
                    sunday.setStart_Date(fdate);
                    sunday.setEnd_Date(ldate);

//                    Sunday_SetAvaibilityTime.setCoach_Id(coachid_);
//                    Sunday_SetAvaibilityTime.setDate(simpleDateFormat.format(scal.getTime()));
//                    Sunday_SetAvaibilityTime.setStart_Date(fdate);
//                    Sunday_SetAvaibilityTime.setEnd_Date(ldate);
//                    System.out.println("Sunday---> "+ new Gson().toJson(Sunday_SetAvaibilityTime));
//                    setAvaibilityTimeArrayListSunday.add(i,Sunday_SetAvaibilityTime);

                    System.out.println("Sunday---> " + new Gson().toJson(sunday));
                    setAvaibilityTimeArrayListSunday.add( sunday);

//                    avaibilityData.setAvaibilityTimes.add(sunday);
//                    System.out.println("setAvaibilityTimeArrayListSaturday---> " + new Gson().toJson(avaibilityData.setAvaibilityTimes));

                }
                scal.add(Calendar.DATE, 1);
            }

            return setAvaibilityTimeArrayListSunday;
        } catch (Exception e) {
            e.printStackTrace();
            return setAvaibilityTimeArrayListSunday;
        }


    }

}
