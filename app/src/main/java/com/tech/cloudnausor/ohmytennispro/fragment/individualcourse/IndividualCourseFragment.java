package com.tech.cloudnausor.ohmytennispro.fragment.individualcourse;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.adapter.ClubAvaibilityAdapter;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.ClubAvabilityModelData;
import com.tech.cloudnausor.ohmytennispro.model.GetIndiCoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.IndiCourseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachSchResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetIndiCoachDetailsResponse;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.MyAutoCompleteTextView;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech.cloudnausor.ohmytennispro.session.SessionManagement.PREFER_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link IndividualCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndividualCourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean mSearchCheck;
    ArrayList<String> mod_of_transport = new ArrayList<String>();
    LinearLayout l_heb,l_quo;
    RadioButton r_hab,r_quo;
    private EditText From_Date,To_Date,Indi_Course_Transport,Indi_Course_Description,Indi_Course_Hr,Indi_Course_Ten_Hr,
            Indi_Course_Tech,Indi_Course_Video_1;
    ImageView Edit_Indi;
    ApiInterface apiInterface;
    Toolbar toolbar;
    Calendar now,now1;
    ArrayList<String> weeklist = new ArrayList<String>();
    ArrayAdapter adapter;
    MyAutoCompleteTextView Weeks_Spinner;
    RadioButton Indival_Commission,Indival_Abonnement;
//    RecyclerView clubAvaibility;

    ImageView GoBack;
    ArrayList<String> weekdates = new ArrayList<String>();
    ArrayList<String> tosend_weekdates = new ArrayList<String>();
    SingleTonProcess singleTonProcess;



    TextView Form_Date_week,To_Date_week,Date_Monday,Date_Tuesday,Date_Wendesday,Date_Thursday,Date_Friday,Date_Saturday,Date_Sunday;
    LinearLayout Week_From_Two;
    Menu menumain;
    ImageView tagAdd1, tagAdd2, tagAdd3, tagAdd4, tagAdd5;
    ImageView tagReduce1,tagReduce2,tagReduce3,tagReduce4,tagReduce5;
    EditText offiTag1,offiTag2,offiTag3,offiTag4,offiTag5;
    LinearLayout taglay1,taglay2,taglay3,taglay4,taglay5;

    Dialog dialog;
    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date1;

    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;
    CheckBox Indival_Car,Indival_Bike,Indival_Train,Indival_Bus;

//    ClubAvaibilityAdapter clubAvaibilityAdapter ;
//    ArrayList<ArrayList<ClubAvabilityModelData>> clubavabilityfull = new ArrayList<ArrayList<ClubAvabilityModelData>>();
//
    TextInputLayout indi_course_postalcode_error,indi_course_place_error;
    TextInputEditText indi_course_postalcode,indi_course_place;


    private CheckBox Monday_Morning,Monday_AfterNoon,Monday_Evening,Monday_Day,Tuesday_Morning,Tuesday_AfterNoon,Tuesday_Evening,Tuesday_Day,
            Wendesday_Morning,Wendesday_AfterNoon,Wendesday_Evening,Wendesday_Day,Thursday_Morning,Thursday_AfterNoon,Thursday_Evening,Thursday_Day,
            Friday_Morning,Friday_AfterNoon,Friday_Evening,Friday_Day,Saturday_Morning,Saturday_AfterNoon,Saturday_Evening,Saturday_Day,
            Sunday_Morning,Sunday_AfterNoon,Sunday_Evening,Sunday_Day;
    public String  Monday_String_Morning = "N",Monday_String_AfterNoon="N",Monday_String_Evening="N",Monday_String_Day="N",Tuesday_String_Morning="N",Tuesday_String_AfterNoon="N",Tuesday_String_Evening="N",Tuesday_String_Day="N", wendesday_String_Morning="N",wendesday_String_AfterNoon="N",wendesday_String_Evening="N",wendesday_String_Day="N",Thursday_String_Morning="N",Thursday_String_AfterNoon="N",Thursday_String_Evening="N",Thursday_String_Day="N", Friday_String_Morning="N",Friday_String_AfterNoon="N",Friday_String_Evening="N",Friday_String_Day="N",Saturday_String_Morning="N",Saturday_String_AfterNoon="N",Saturday_String_Evening="N",Saturday_String_Day="N", Sunday_String_Morning="N",Sunday_String_AfterNoon="N",Sunday_String_Evening="N",Sunday_String_Day="N";

    private Button dialog_cancel,dialog_save,page_cancel,page_save,ShowID;

    String week_fromDatetoSend ="";
    String week_toDatetoSend = "";
    String day_fromDatetoSend ="";
    String day_toDatetoSend = "";

//    ViewGroup viewGroup;
//    View dialogView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


//    public IndividualCourseFragment() {
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param text Parameter 1.

     * @return A new instance of fragment IndividualCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public  IndividualCourseFragment newInstance(String text) {
        IndividualCourseFragment mFragment = new IndividualCourseFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view_indi_cou = inflater.inflate(R.layout.fragment_individual_course, container, false);

        ShowID = view_indi_cou.findViewById(R.id.showid);
        Indi_Course_Transport =(EditText)view_indi_cou.findViewById(R.id.indi_course_transport);
        toolbar = view_indi_cou.findViewById(R.id.toolbar_indi);
        GoBack = (ImageView)view_indi_cou.findViewById(R.id.back);

        Indi_Course_Description=(EditText)view_indi_cou.findViewById(R.id.indi_course_description);
        Indi_Course_Hr=(EditText)view_indi_cou.findViewById(R.id.indi_course_hr);
        Indi_Course_Ten_Hr=(EditText)view_indi_cou.findViewById(R.id.indi_course_ten_hr);
        Indi_Course_Tech=(EditText)view_indi_cou.findViewById(R.id.indi_course_tech);
        Indi_Course_Video_1=(EditText)view_indi_cou.findViewById(R.id.indi_course_video_1);
        page_cancel=(Button)view_indi_cou.findViewById(R.id.indi_course_cancel);
        page_save=(Button)view_indi_cou.findViewById(R.id.indi_course_save);
        Edit_Indi=(ImageView)toolbar.findViewById(R.id.edit_indi) ;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        session = new SessionManagement(getContext());
        sharedPreferences = getActivity().getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        tagAdd1 =(ImageView) view_indi_cou.findViewById(R.id.tagadd1);
        tagAdd2 = (ImageView) view_indi_cou.findViewById(R.id.tagadd2);
        tagAdd3 = (ImageView) view_indi_cou.findViewById(R.id.tagadd3);
        tagAdd4 = (ImageView) view_indi_cou.findViewById(R.id.tagadd4);
        tagAdd5 = (ImageView) view_indi_cou.findViewById(R.id.tagadd5);
        tagReduce1 = (ImageView) view_indi_cou.findViewById(R.id.tagreduce1);
        tagReduce2 = (ImageView) view_indi_cou.findViewById(R.id.tagreduce2);
        tagReduce3 = (ImageView) view_indi_cou.findViewById(R.id.tagreduce3) ;
        tagReduce4 = (ImageView) view_indi_cou.findViewById(R.id.tagreduce4);
        tagReduce5 = (ImageView) view_indi_cou.findViewById(R.id.tagreduce5) ;
        offiTag1 =(EditText) view_indi_cou.findViewById(R.id.offiTag1);
        offiTag2 = (EditText) view_indi_cou.findViewById(R.id.offiTag2);
        offiTag3 = (EditText) view_indi_cou.findViewById(R.id.offiTag3);
        offiTag4 = (EditText) view_indi_cou.findViewById(R.id.offiTag4);
        offiTag5 = (EditText) view_indi_cou.findViewById(R.id.offiTag5);
        taglay2 = (LinearLayout) view_indi_cou.findViewById(R.id.taglay2);
        taglay3 = (LinearLayout) view_indi_cou.findViewById(R.id.taglay3);
        taglay4 = (LinearLayout) view_indi_cou.findViewById(R.id.taglay4);
        taglay5 = (LinearLayout) view_indi_cou.findViewById(R.id.taglay5);
        Indival_Commission= (RadioButton) view_indi_cou.findViewById(R.id.indival_commission);
        Indival_Abonnement= (RadioButton) view_indi_cou.findViewById(R.id.indival_abonnement);
        indi_course_postalcode_error = (TextInputLayout) view_indi_cou.findViewById(R.id.indi_course_postalcode_error);
        indi_course_place_error  = (TextInputLayout) view_indi_cou.findViewById(R.id.indi_course_place_error);
        indi_course_postalcode = (TextInputEditText) view_indi_cou.findViewById(R.id.indi_course_postalcode);
        indi_course_place  = (TextInputEditText) view_indi_cou.findViewById(R.id.indi_course_place);


        Indival_Car = (CheckBox) view_indi_cou.findViewById(R.id.indival_car);
        Indival_Bike = (CheckBox) view_indi_cou.findViewById(R.id.indival_bike);
        Indival_Train = (CheckBox) view_indi_cou.findViewById(R.id.indival_train);
        Indival_Bus = (CheckBox) view_indi_cou.findViewById(R.id.indival_bus);



        videotags();

        sharedPreferences = getContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getContext());

        if (sharedPreferences.contains("KEY_Coach_ID"))
        {
            coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");

        }
        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");
        }

//        GoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

//        /* TODO: API Comment
//         TODO: API Comment */
        previewTint();
        getIndivl();

        dialog =new Dialog(getContext());
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
        Edit_Indi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTint();
                Edit_Indi.setVisibility(View.GONE);
            }
        });
//        clubAvaibilityAdapter = new ClubAvaibilityAdapter(getContext(),clubavabilityfull);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        clubAvaibility.setLayoutManager(new LinearLayoutManager(getContext()));
//        clubAvaibility.setAdapter(clubAvaibilityAdapter);
//        page_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        page_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                menumain.findItem(Menus.EDIT).setVisible(true);
//                Edit_Indi.setVisibility(View.VISIBLE);
//                previewTint();
                if(!Indi_Course_Description.getText().toString().equals("") && ! Indi_Course_Tech.getText().toString().equals("") && !
                         Indi_Course_Video_1.getText().toString().equals("") && ! Indi_Course_Hr.getText().toString().equals("") && ! Indi_Course_Ten_Hr.getText().toString().equals("") && !
                        indi_course_place.getText().toString().equals("") && !indi_course_postalcode.getText().toString().equals(""))
                {
                    if (Indival_Car.isChecked() ||
                            Indival_Bike.isChecked() ||
                            Indival_Train.isChecked() ||
                            Indival_Bus.isChecked()) {

                        if (Indival_Commission.isChecked() || Indival_Abonnement.isChecked()) {

                            mod_of_transport.clear();
                            if (Indival_Car.isChecked()) {
                                mod_of_transport.add("Car");
                            }
                            if (Indival_Bike.isChecked()) {
                                mod_of_transport.add("Vélo");
                            }
                            if (Indival_Train.isChecked()) {
                                mod_of_transport.add("Train");
                            }
                            if (Indival_Bus.isChecked()) {
                                mod_of_transport.add("Bus");
                            }
                            String plan = "";
                            if (Indival_Commission.isChecked()) {
                                plan = Indival_Commission.getText().toString();

                            }

                            if (Indival_Abonnement.isChecked()) {
                                plan = Indival_Abonnement.getText().toString();
                            }

                            String transport = TextUtils.join(",", mod_of_transport);

                            apiInterface.getInsertIndividualCourse(coachid_, Indi_Course_Description.getText().toString(), Indi_Course_Tech.getText().toString(),
                                    transport, Indi_Course_Video_1.getText().toString(), Indi_Course_Hr.getText().toString(), Indi_Course_Ten_Hr.getText().toString(), plan
                                    , indi_course_place.getText().toString(), indi_course_postalcode.getText().toString())
                                    .enqueue(new Callback<CoachSchResponseData>() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onResponse(Call<CoachSchResponseData> call, Response<CoachSchResponseData> response) {
                                            assert response.body() != null;
                                            if (response.body().getIsSuccess().equals("true")) {
                                                Edit_Indi.setVisibility(View.GONE);
                                                previewTint();
                                                menumain.findItem(Menus.EDIT).setVisible(true);
                                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                                            }else {
                                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CoachSchResponseData> call, Throwable t) {
                                            previewTint();
                                            menumain.findItem(Menus.EDIT).setVisible(false);

                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Veuillez sélectionner au moins un plan", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Veuillez sélectionner au moins un transport", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Veuillez saisir toutes les données", Toast.LENGTH_LONG).show();
                }
            }
        });
        page_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIndivl();
            }
        });
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
        int j =31;
        for(int i = 0;i<7;i++) {
            now.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            now.set(Calendar.MONTH, 11);//0- january ..4-May
            now.set(Calendar.DATE, j);
            if (now.get(Calendar.WEEK_OF_YEAR) != 1) {
                break;
            }else{
                j = j-1;
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

        for(int i = now1.get(Calendar.WEEK_OF_YEAR); i <= now.get(Calendar.WEEK_OF_YEAR);i++ ){

            weeklist.add("Semaine "+i);
            System.out.println(weeklist);
        }

        ShowID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
//                Rect displayRectangle = new Rect();
//                Window window = getActivity().getWindow();
//                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.DialogTheme);
//                viewGroup = view.findViewById(android.R.id.content);
//                dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.individual_course_calendar_dialog, viewGroup, false);
//                dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
//                dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
////                autocomplete.setOnTouchListener(new View.OnTouchListener() {
////                    @Override
////                    public boolean onTouch(View view, MotionEvent motionEvent) {
////                        autocomplete.showDropDown();
////                        return false;
////                    }
////                });
//
//                builder.setView(dialogView);
//                final AlertDialog alertDialog = builder.create();
//                alertDialog.show();

                List<String> sermaine = weeklist;
                LayoutInflater factory = LayoutInflater.from(getContext());
                View view1  = factory.inflate(R.layout.individual_course_calendar_dialog, null);
                r_quo = (RadioButton)view1.findViewById(R.id.radio_Quo);
                r_hab = (RadioButton)view1.findViewById(R.id.radio_Heb);
                l_quo = (LinearLayout) view1.findViewById(R.id.Quo);
                l_heb = (LinearLayout) view1.findViewById(R.id.heb);
                Monday_Morning=(CheckBox)view1.findViewById(R.id.mon_morg);
                Monday_AfterNoon=(CheckBox)view1.findViewById(R.id.mon_aftn);
                Monday_Evening=(CheckBox)view1.findViewById(R.id.mon_even);
                Monday_Day=(CheckBox)view1.findViewById(R.id.mon_day);
                Tuesday_Morning=(CheckBox)view1.findViewById(R.id.tus_morg);
                Tuesday_AfterNoon=(CheckBox)view1.findViewById(R.id.tus_after);
                Tuesday_Evening=(CheckBox)view1.findViewById(R.id.tus_eve);
                Tuesday_Day=(CheckBox)view1.findViewById(R.id.tus_day);
                Wendesday_Morning=(CheckBox)view1.findViewById(R.id.wed_morg);
                Wendesday_AfterNoon=(CheckBox)view1.findViewById(R.id.wed_after);
                Wendesday_Evening=(CheckBox)view1.findViewById(R.id.wed_eve);
                Wendesday_Day=(CheckBox)view1.findViewById(R.id.wed_day);
                Thursday_Morning=(CheckBox)view1.findViewById(R.id.thurs_morng);
                Thursday_AfterNoon=(CheckBox)view1.findViewById(R.id.thurs_after);
                Thursday_Evening=(CheckBox)view1.findViewById(R.id.thurs_even);
                Thursday_Day=(CheckBox)view1.findViewById(R.id.thurs_day);
                Friday_Morning=(CheckBox)view1.findViewById(R.id.fri_mrng);
                Friday_AfterNoon=(CheckBox)view1.findViewById(R.id.fri_after);
                Friday_Evening=(CheckBox)view1.findViewById(R.id.fri_even);
                Friday_Day=(CheckBox)view1.findViewById(R.id.fri_day);
                Saturday_Morning=(CheckBox)view1.findViewById(R.id.sat_morg);
                Saturday_AfterNoon=(CheckBox)view1.findViewById(R.id.sat_after);
                Saturday_Evening=(CheckBox)view1.findViewById(R.id.sat_even);
                Saturday_Day=(CheckBox)view1.findViewById(R.id.sat_day);
                Sunday_Morning=(CheckBox)view1.findViewById(R.id.sun_morn);
                Sunday_AfterNoon=(CheckBox)view1.findViewById(R.id.sun_after);
                Sunday_Evening=(CheckBox)view1.findViewById(R.id.sun_even);
                Sunday_Day=(CheckBox)view1.findViewById(R.id.sun_day);
                From_Date =(EditText)view1.findViewById(R.id.from_date);
                To_Date =(EditText)view1.findViewById(R.id.to_date);
                dialog_cancel =(Button)view1.findViewById(R.id.indi_d_cancel);
                dialog_save =(Button)view1.findViewById(R.id.indi_d_save);
                Form_Date_week =(TextView)view1.findViewById(R.id.from_date_id);
                To_Date_week =(TextView)view1.findViewById(R.id.to_date_id);
                Week_From_Two=(LinearLayout)view1.findViewById(R.id.week_start_end);
                Weeks_Spinner = (MyAutoCompleteTextView)view1.findViewById(R.id.week_view);
                Date_Monday = (TextView)view1.findViewById(R.id.date_monday);
                Date_Tuesday= (TextView)view1.findViewById(R.id.date_tuesday);
                Date_Wendesday= (TextView)view1.findViewById(R.id.date_wednesday);
                Date_Thursday= (TextView)view1.findViewById(R.id.date_thursday);
                Date_Friday= (TextView)view1.findViewById(R.id.date_friday);
                Date_Saturday= (TextView)view1.findViewById(R.id.date_saturday);
                Date_Sunday= (TextView)view1.findViewById(R.id.date_sunday);

                String[] zz ={"bala","guna"};
//                adapter = new ArrayAdapter<String>(IndividualCourseActivity.this, R.layout.spinner_text, zz);
//                adapter.setDropDownViewResource(R.layout.spinner_item_list);
//                adapter = new ArrayAdapter<String>(IndividualCourseActivity.this,android.R.layout.select_dialog_item, sermaine);
                adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text, sermaine);

                adapter.setDropDownViewResource(R.layout.spinner_item_list);
                Weeks_Spinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        weekdates.clear();
                        String spinnerdata = charSequence.toString();
                        spinnerdata = spinnerdata.replace("Semaine ","");
                        Calendar weekdata = Calendar.getInstance();
                        weekdata.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(spinnerdata));
                        weekdata.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                        int firstDayOfWeek = weekdata.getFirstDayOfWeek();
                        for (int k = firstDayOfWeek; k < firstDayOfWeek + 7; k++) {
                            weekdata.set(Calendar.DAY_OF_WEEK, k);
                            tosend_weekdates.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(weekdata.getTime()));
                            weekdates.add(new SimpleDateFormat("yyyy-MM-dd").format(weekdata.getTime()));
                            System.out.println("weekdates---> "+weekdates);
                        }
                        week_fromDatetoSend = tosend_weekdates.get(0);
                        week_toDatetoSend = tosend_weekdates.get(6);
                        Form_Date_week.setText(weekdates.get(0));
                        To_Date_week.setText(weekdates.get(6));
                        Date_Sunday.setText(weekdates.get(0));
                        Date_Monday.setText(weekdates.get(1));
                        Date_Tuesday.setText(weekdates.get(2));
                        Date_Wendesday.setText(weekdates.get(3));
                        Date_Thursday.setText(weekdates.get(4));
                        Date_Friday.setText(weekdates.get(5));
                        Date_Saturday.setText(weekdates.get(6));
                        Date_Sunday.setVisibility(View.VISIBLE);
                        Date_Monday.setVisibility(View.VISIBLE);
                        Date_Tuesday.setVisibility(View.VISIBLE);
                        Date_Wendesday.setVisibility(View.VISIBLE);
                        Date_Thursday.setVisibility(View.VISIBLE);
                        Date_Friday.setVisibility(View.VISIBLE);
                        Date_Saturday.setVisibility(View.VISIBLE);
                        Week_From_Two.setVisibility(View.VISIBLE);

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

                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(r_quo.isChecked()){
                            if(!From_Date.getText().toString().equals("") && !To_Date.getText().toString().equals("")){

                                apiInterface.getInsertAvailability( "N",day_fromDatetoSend,day_toDatetoSend,Monday_String_Morning,Monday_String_AfterNoon,Monday_String_Evening,Tuesday_String_Morning,Tuesday_String_AfterNoon,Tuesday_String_Evening, wendesday_String_Morning,wendesday_String_AfterNoon,wendesday_String_Evening,Thursday_String_Morning,Thursday_String_AfterNoon,
                                        Thursday_String_Evening, Friday_String_Morning,Friday_String_AfterNoon,Friday_String_Evening,Saturday_String_Morning,
                                        Saturday_String_AfterNoon,Saturday_String_Evening,Sunday_String_Morning,Sunday_String_AfterNoon,Sunday_String_Evening,
                                        coachid_,"CoursIndividuel","Y").enqueue(new Callback<CoachSchResponseData>() {
                                    @Override
                                    public void onResponse(Call<CoachSchResponseData> call, Response<CoachSchResponseData> response) {
                                        System.out.println("------ > error d " + new Gson().toJson(response.body()));

                                        if (response.body().getIsSuccess().equals("true")){
                                            dialog.dismiss();
                                            Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<CoachSchResponseData> call, Throwable t) {
System.out.println("------ > error " + t);
                                    }
                                });

                            }
                        }else if(r_hab.isChecked()){
                            if(!Form_Date_week.getText().toString().equals("") && !To_Date_week.getText().toString().equals("")){

                                apiInterface.getInsertAvailability( "Y",week_fromDatetoSend,week_toDatetoSend,Monday_String_Morning,Monday_String_AfterNoon,Monday_String_Evening,Tuesday_String_Morning,Tuesday_String_AfterNoon,Tuesday_String_Evening, wendesday_String_Morning,wendesday_String_AfterNoon,wendesday_String_Evening,Thursday_String_Morning,Thursday_String_AfterNoon,
                                        Thursday_String_Evening, Friday_String_Morning,Friday_String_AfterNoon,Friday_String_Evening,Saturday_String_Morning,
                                        Saturday_String_AfterNoon,Saturday_String_Evening,Sunday_String_Morning,Sunday_String_AfterNoon,Sunday_String_Evening,
                                        "17","CoursIndividuel","Y").enqueue(new Callback<CoachSchResponseData>() {
                                    @Override
                                    public void onResponse(Call<CoachSchResponseData> call, Response<CoachSchResponseData> response) {
                                        System.out.println("------ > error w" + new Gson().toJson(response.body()));

                                        if (response.body().getIsSuccess().equals("true")){
                                            dialog.dismiss();
                                            Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<CoachSchResponseData> call, Throwable t) {
                                        System.out.println("------ > error we" + t);

                                    }
                                });

                            }
                        }


                    }
                });
                From_Date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(getContext(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });
                To_Date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(getContext(), date1, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });

                Monday_Morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Monday_Morning.isChecked()){Monday_String_Morning ="Y";}else{Monday_String_Morning ="N";}
                        if(Monday_Morning.isChecked() && Monday_AfterNoon.isChecked() && Monday_Evening.isChecked()){
                            Monday_Day.setChecked(true);
                        }else{
                            Monday_Day.setChecked(false);
                        }
                    }
                });
                Monday_AfterNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Monday_AfterNoon.isChecked()){Monday_String_AfterNoon ="Y";}else{Monday_String_AfterNoon ="N";}
                        if(Monday_Morning.isChecked() && Monday_AfterNoon.isChecked() && Monday_Evening.isChecked()){
                            Monday_Day.setChecked(true);
                        }else{
                            Monday_Day.setChecked(false);
                        }
                    }
                });
                Monday_Evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Monday_Evening.isChecked()){Monday_String_Evening ="Y";}else{Monday_String_Evening ="N";}
                        if(Monday_Morning.isChecked() && Monday_AfterNoon.isChecked() && Monday_Evening.isChecked()){
                            Monday_Day.setChecked(true);
                        }else{
                            Monday_Day.setChecked(false);
                        }
                    }
                });
                Monday_Day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Monday_Day.isChecked()){
                            Monday_Morning.setChecked(true);
                            Monday_String_Morning = "Y";
                            Monday_AfterNoon.setChecked(true);
                            Monday_String_AfterNoon= "Y";
                            Monday_Evening.setChecked(true);
                            Monday_String_Evening= "Y";
                        }else {
                            Monday_Morning.setChecked(false);
                            Monday_String_Morning = "N";
                            Monday_AfterNoon.setChecked(false);
                            Monday_String_AfterNoon= "N";
                            Monday_Evening.setChecked(false);
                            Monday_String_Evening= "N";
                        }
                    }
                });
                Tuesday_Morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Tuesday_Morning.isChecked()){Tuesday_String_Morning ="Y";}else{Tuesday_String_Morning ="N";}
                        if(Tuesday_Morning.isChecked() && Tuesday_AfterNoon.isChecked() && Tuesday_Evening.isChecked()){
                            Tuesday_Day.setChecked(true);
                        }else{
                            Tuesday_Day.setChecked(false);
                        }
                    }
                });
                Tuesday_AfterNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Tuesday_Morning.isChecked()){Tuesday_String_Morning ="Y";}else{Tuesday_String_Morning ="N";}
                        if(Tuesday_Morning.isChecked() && Tuesday_AfterNoon.isChecked() && Tuesday_Evening.isChecked()){
                            Tuesday_Day.setChecked(true);
                        }else{
                            Tuesday_Day.setChecked(false);
                        }
                    }
                });
                Tuesday_Evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Tuesday_Morning.isChecked()){Tuesday_String_Morning ="Y";}else{Tuesday_String_Morning ="N";}
                        if(Tuesday_Morning.isChecked() && Tuesday_AfterNoon.isChecked() && Tuesday_Evening.isChecked()){
                            Tuesday_Day.setChecked(true);
                        }else{
                            Tuesday_Day.setChecked(false);
                        }
                    }
                });
                Tuesday_Day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Tuesday_Day.isChecked()){
                            Tuesday_Morning.setChecked(true);
                            Tuesday_String_Morning = "Y";
                            Tuesday_AfterNoon.setChecked(true);
                            Tuesday_String_AfterNoon= "Y";
                            Tuesday_Evening.setChecked(true);
                            Tuesday_String_Evening= "Y";
                        }else {
                            Tuesday_Morning.setChecked(false);
                            Tuesday_String_Morning = "N";
                            Tuesday_AfterNoon.setChecked(false);
                            Tuesday_String_AfterNoon= "N";
                            Tuesday_Evening.setChecked(false);
                            Tuesday_String_Evening= "N";
                        }
                    }
                });
                Wendesday_Morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Wendesday_Morning.isChecked()){wendesday_String_Morning ="Y";}else{wendesday_String_Morning ="N";}
                        if(Wendesday_Morning.isChecked() && Wendesday_AfterNoon.isChecked() && Wendesday_Evening.isChecked()){
                            Wendesday_Day.setChecked(true);
                        }else{
                            Wendesday_Day.setChecked(false);
                        }
                    }
                });
                Wendesday_AfterNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Wendesday_Morning.isChecked()){wendesday_String_Morning ="Y";}else{wendesday_String_Morning ="N";}
                        if(Wendesday_Morning.isChecked() && Wendesday_AfterNoon.isChecked() && Wendesday_Evening.isChecked()){
                            Wendesday_Day.setChecked(true);
                        }else{
                            Wendesday_Day.setChecked(false);
                        }
                    }
                });
                Wendesday_Evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Wendesday_Morning.isChecked()){wendesday_String_Morning ="Y";}else{wendesday_String_Morning ="N";}
                        if(Wendesday_Morning.isChecked() && Wendesday_AfterNoon.isChecked() && Wendesday_Evening.isChecked()){
                            Wendesday_Day.setChecked(true);
                        }else{
                            Wendesday_Day.setChecked(false);
                        }
                    }
                });

                Wendesday_Day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if( Wendesday_Day.isChecked()){
                            Wendesday_Morning.setChecked(true);
                            wendesday_String_Morning = "Y";
                            Wendesday_AfterNoon.setChecked(true);
                            wendesday_String_AfterNoon= "Y";
                            Wendesday_Evening.setChecked(true);
                            wendesday_String_Evening= "Y";
                        }else {
                            Wendesday_Morning.setChecked(false);
                            wendesday_String_Morning = "N";
                            Wendesday_AfterNoon.setChecked(false);
                            wendesday_String_AfterNoon= "N";
                            Wendesday_Evening.setChecked(false);
                            wendesday_String_Evening= "N";
                        }
                    }
                });
                Thursday_Morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Thursday_Morning.isChecked()){Thursday_String_Morning ="Y";}else{Thursday_String_Morning ="N";}
                        if(Thursday_Morning.isChecked() && Thursday_AfterNoon.isChecked() && Thursday_Evening.isChecked()){
                            Thursday_Day.setChecked(true);
                        }else{
                            Thursday_Day.setChecked(false);
                        }
                    }
                });
                Thursday_AfterNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Thursday_Morning.isChecked()){Thursday_String_Morning ="Y";}else{Thursday_String_Morning ="N";}
                        if(Thursday_Morning.isChecked() && Thursday_AfterNoon.isChecked() && Thursday_Evening.isChecked()){
                            Thursday_Day.setChecked(true);
                        }else{
                            Thursday_Day.setChecked(false);
                        }
                    }
                });
                Thursday_Evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Wendesday_Morning.isChecked()){wendesday_String_Morning ="Y";}else{wendesday_String_Morning ="N";}
                        if(Wendesday_Morning.isChecked() && Wendesday_AfterNoon.isChecked() && Wendesday_Evening.isChecked()){
                            Wendesday_Day.setChecked(true);
                        }else{
                            Wendesday_Day.setChecked(false);
                        }
                    }
                });

                Thursday_Day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Thursday_Day.isChecked()){
                            Thursday_Morning.setChecked(true);
                            Thursday_String_Morning = "Y";
                            Thursday_AfterNoon.setChecked(true);
                            Thursday_String_AfterNoon= "Y";
                            Thursday_Evening.setChecked(true);
                            Thursday_String_Evening= "Y";
                        }else {
                            Thursday_Morning.setChecked(false);
                            Thursday_String_Morning = "N";
                            Thursday_AfterNoon.setChecked(false);
                            Thursday_String_AfterNoon= "N";
                            Thursday_Evening.setChecked(false);
                            Thursday_String_Evening= "N";
                        }
                    }
                });
                Friday_Morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Friday_Morning.isChecked()){Friday_String_Morning ="Y";}else{Friday_String_Morning ="N";}
                        if(Friday_Morning.isChecked() && Friday_AfterNoon.isChecked() && Friday_Evening.isChecked()){
                            Friday_Day.setChecked(true);
                        }else{
                            Friday_Day.setChecked(false);
                        }
                    }
                });
                Friday_AfterNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Friday_Morning.isChecked()){Friday_String_Morning ="Y";}else{Friday_String_Morning ="N";}
                        if(Friday_Morning.isChecked() && Friday_AfterNoon.isChecked() && Friday_Evening.isChecked()){
                            Friday_Day.setChecked(true);
                        }else{
                            Friday_Day.setChecked(false);
                        }
                    }
                });
                Friday_Evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Friday_Morning.isChecked()){Friday_String_Morning ="Y";}else{Friday_String_Morning ="N";}
                        if(Friday_Morning.isChecked() && Friday_AfterNoon.isChecked() && Friday_Evening.isChecked()){
                            Friday_Day.setChecked(true);
                        }else{
                            Friday_Day.setChecked(false);
                        }
                    }
                });
                Friday_Day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Friday_Day.isChecked()){
                            Friday_Morning.setChecked(true);
                            Friday_String_Morning = "Y";
                            Friday_AfterNoon.setChecked(true);
                            Friday_String_AfterNoon= "Y";
                            Friday_Evening.setChecked(true);
                            Friday_String_Evening= "Y";
                        }else {
                            Friday_Morning.setChecked(false);
                            Friday_String_Morning = "N";
                            Friday_AfterNoon.setChecked(false);
                            Friday_String_AfterNoon= "N";
                            Friday_Evening.setChecked(false);
                            Friday_String_Evening= "N";
                        }
                    }
                });
                Saturday_Morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Saturday_Morning.isChecked()){Saturday_String_Morning ="Y";}else{Saturday_String_Morning ="N";}
                        if(Saturday_Morning.isChecked() && Saturday_AfterNoon.isChecked() && Saturday_Evening.isChecked()){
                            Saturday_Day.setChecked(true);
                        }else{
                            Saturday_Day.setChecked(false);
                        }
                    }
                });
                Saturday_AfterNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Saturday_Morning.isChecked()){Saturday_String_Morning ="Y";}else{Saturday_String_Morning ="N";}
                        if(Saturday_Morning.isChecked() && Saturday_AfterNoon.isChecked() && Saturday_Evening.isChecked()){
                            Saturday_Day.setChecked(true);
                        }else{
                            Saturday_Day.setChecked(false);
                        }
                    }
                });
                Saturday_Evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Saturday_Morning.isChecked()){Saturday_String_Morning ="Y";}else{Saturday_String_Morning ="N";}
                        if(Saturday_Morning.isChecked() && Saturday_AfterNoon.isChecked() && Saturday_Evening.isChecked()){
                            Saturday_Day.setChecked(true);
                        }else{
                            Monday_Day.setChecked(false);
                        }
                    }
                });
                Saturday_Day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Saturday_Day.isChecked()){
                            Saturday_Morning.setChecked(true);
                            Saturday_String_Morning = "Y";
                            Saturday_AfterNoon.setChecked(true);
                            Saturday_String_AfterNoon= "Y";
                            Saturday_Evening.setChecked(true);
                            Saturday_String_Evening= "Y";
                        }else {
                            Saturday_Morning.setChecked(false);
                            Saturday_String_Morning = "N";
                            Saturday_AfterNoon.setChecked(false);
                            Saturday_String_AfterNoon= "N";
                            Saturday_Evening.setChecked(false);
                            Saturday_String_Evening= "N";
                        }
                    }
                });
                Sunday_Morning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Sunday_Morning.isChecked()){Sunday_String_Morning ="Y";}else{Sunday_String_Morning ="N";}
                        if(Sunday_Morning.isChecked() && Sunday_AfterNoon.isChecked() && Sunday_Evening.isChecked()){
                            Sunday_Day.setChecked(true);
                        }else{
                            Sunday_Day.setChecked(false);
                        }
                    }
                });
                Sunday_AfterNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Sunday_Morning.isChecked()){Sunday_String_Morning ="Y";}else{Sunday_String_Morning ="N";}
                        if(Sunday_Morning.isChecked() && Sunday_AfterNoon.isChecked() && Sunday_Evening.isChecked()){
                            Sunday_Day.setChecked(true);
                        }else{
                            Sunday_Day.setChecked(false);
                        }
                    }
                });
                Sunday_Evening.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Sunday_Morning.isChecked()){Sunday_String_Morning ="Y";}else{Sunday_String_Morning ="N";}
                        if(Sunday_Morning.isChecked() && Sunday_AfterNoon.isChecked() && Sunday_Evening.isChecked()){
                            Sunday_Day.setChecked(true);
                        }else{
                            Sunday_Day.setChecked(false);
                        }
                    }
                });

                Sunday_Day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Sunday_Day.isChecked()){
                            Sunday_Morning.setChecked(true);
                            Sunday_String_Morning = "Y";
                            Sunday_AfterNoon.setChecked(true);
                            Sunday_String_AfterNoon= "Y";
                            Sunday_Evening.setChecked(true);
                            Sunday_String_Evening= "Y";
                        }else {
                            Sunday_Morning.setChecked(false);
                            Sunday_String_Morning = "N";
                            Sunday_AfterNoon.setChecked(false);
                            Sunday_String_AfterNoon= "N";
                            Sunday_Evening.setChecked(false);
                            Sunday_String_Evening= "N";
                        }
                    }
                });

                r_quo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        r_quo.setChecked(true);
                        l_heb.setVisibility(View.VISIBLE);
                        r_hab.setChecked(false);
                        l_quo.setVisibility(View.GONE);

                        Date_Sunday.setVisibility(View.GONE);
                        Date_Monday.setVisibility(View.GONE);
                        Date_Tuesday.setVisibility(View.GONE);
                        Date_Wendesday.setVisibility(View.GONE);
                        Date_Thursday.setVisibility(View.GONE);
                        Date_Friday.setVisibility(View.GONE);
                        Date_Saturday.setVisibility(View.GONE);
                        Week_From_Two.setVisibility(View.GONE);
                    }
                });
                r_hab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Weeks_Spinner.getText().toString() != ""){
                            Date_Sunday.setVisibility(View.VISIBLE);
                            Date_Monday.setVisibility(View.VISIBLE);
                            Date_Tuesday.setVisibility(View.VISIBLE);
                            Date_Wendesday.setVisibility(View.VISIBLE);
                            Date_Thursday.setVisibility(View.VISIBLE);
                            Date_Friday.setVisibility(View.VISIBLE);
                            Date_Saturday.setVisibility(View.VISIBLE);
                            Week_From_Two.setVisibility(View.VISIBLE);
                        }
                        r_hab.setChecked(true);
                        l_quo.setVisibility(View.VISIBLE);
                        r_quo.setChecked(false);
                        l_heb.setVisibility(View.GONE);

                    }
                });
                dialog.setContentView(view1);
                dialog.show();
            }
        });
        // Inflate the layout for this fragment


        // Inflate the layout for this fragment
        return view_indi_cou;


    }

    private void form_dat() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String myFormat_tosend = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf_tosend = new SimpleDateFormat(myFormat_tosend, Locale.US);
        From_Date.setText(sdf.format(myCalendar.getTime()));
        day_fromDatetoSend = sdf_tosend.format(myCalendar.getTime());
    }
    private void to_dat() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String myFormat_tosend = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf_tosend = new SimpleDateFormat(myFormat_tosend, Locale.US);
        To_Date.setText(sdf.format(myCalendar.getTime()));
        day_toDatetoSend = sdf_tosend.format(myCalendar.getTime());

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void previewTint(){
        ShowID.setBackground(getResources().getDrawable(R.drawable.cancelbutton));
        indi_course_place.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        indi_course_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Indi_Course_Transport.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.whitecolor)));
        Indi_Course_Transport.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Indi_Course_Description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Indi_Course_Hr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Indi_Course_Ten_Hr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Indi_Course_Tech.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Indi_Course_Video_1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        page_cancel.setVisibility(View.GONE);
        page_save.setVisibility(View.GONE);
        Indival_Car.setEnabled(false);
        Indival_Bus.setEnabled(false);
        Indival_Train.setEnabled(false);
        Indival_Bike.setEnabled(false);
        Indival_Commission.setEnabled(false);
        Indival_Abonnement.setEnabled(false);
        Indi_Course_Transport.setEnabled(false);
        Indi_Course_Description.setEnabled(false);
        Indi_Course_Hr.setEnabled(false);
        Indi_Course_Ten_Hr.setEnabled(false);
        Indi_Course_Tech.setEnabled(false);
        Indi_Course_Video_1.setEnabled(false);
        ShowID.setEnabled(false);
        indi_course_place.setEnabled(false);
        indi_course_postalcode.setEnabled(false);

    }

    public void EditTint(){
        ShowID.setEnabled(true);
        ShowID.setBackground(getResources().getDrawable(R.drawable.loginbutton));
        Indi_Course_Transport.setEnabled(true);
        Indi_Course_Description.setEnabled(true);
        Indi_Course_Hr.setEnabled(true);
        Indi_Course_Ten_Hr.setEnabled(true);
        Indi_Course_Tech.setEnabled(true);
        Indi_Course_Video_1.setEnabled(true);
        Indival_Car.setEnabled(true);
        Indival_Bus.setEnabled(true);
        Indival_Train.setEnabled(true);
        Indival_Bike.setEnabled(true);
        Indival_Commission.setEnabled(true);
        Indival_Abonnement.setEnabled(true);
        indi_course_place.setEnabled(true);
        indi_course_postalcode.setEnabled(true);
        Indi_Course_Transport.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Indi_Course_Description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Indi_Course_Hr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Indi_Course_Ten_Hr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Indi_Course_Tech.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Indi_Course_Video_1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        indi_course_place.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        indi_course_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        page_cancel.setVisibility(View.VISIBLE);
        page_save.setVisibility(View.VISIBLE);

    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(IndividualCourseActivity.this, HomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(IndividualCourseActivity.this, HomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        menumain = menu;
//        inflater.inflate(R.menu.menu, menumain);
        SearchView searchView = (SearchView) (menu.findItem(Menus.SEARCH).getActionView());
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(OnQuerySearchView);

        menu.findItem(Menus.ADD).setVisible(false);
        menu.findItem(Menus.EDIT).setVisible(true);
        menu.findItem(Menus.UPDATE).setVisible(false);
        menu.findItem(Menus.SEARCH).setVisible(false);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case Menus.ADD:
                break;

            case Menus.EDIT:
                EditTint();
                menumain.findItem(Menus.EDIT).setVisible(false);
                break;


            case Menus.UPDATE:
                break;

            case Menus.SEARCH:
                mSearchCheck = true;
                break;
        }
        return true;
    }
public void videotags(){
    tagAdd1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(offiTag1.getText().toString() == ""){
            }else{
                tagAdd1.setVisibility( View.GONE);
                tagReduce1.setVisibility(View.VISIBLE);
                taglay2.setVisibility(View.VISIBLE);
            }
        }
    });




    tagAdd2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (offiTag2.getText().toString() == "") {
            } else {
                tagAdd2.setVisibility(View.GONE);
                tagReduce2.setVisibility(View.VISIBLE);
                taglay3.setVisibility(View.VISIBLE);
            }
        }

    });
    tagAdd3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (offiTag3.getText().toString() == "") {
            } else {
                tagAdd3.setVisibility(View.GONE);
                tagReduce3.setVisibility(View.VISIBLE);
                taglay4.setVisibility(View.VISIBLE);
            }
        }

    });
    tagAdd4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (offiTag4.getText().toString() == "") {
            } else {
                tagAdd4.setVisibility(View.GONE);
                tagReduce4.setVisibility(View.VISIBLE);
                taglay5.setVisibility(View.VISIBLE);
            }
        }

    });

//        tagAdd5.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//        })

    tagReduce1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (taglay2.getVisibility() == View.VISIBLE || taglay3.getVisibility() == View.VISIBLE || taglay4.getVisibility() == View.VISIBLE || taglay5.getVisibility() == View.VISIBLE) {

                if (offiTag2.getText().toString() != "") {
                    offiTag1.setText(offiTag2.getText().toString());
                    tagAdd1.setVisibility(View.VISIBLE);
                    tagReduce1.setVisibility(View.GONE);
                    taglay2.setVisibility(View.GONE);
                    offiTag2.setText("");
                }
                if (offiTag3.getText().toString() != "") {
                    offiTag2.setText(offiTag3.getText().toString());
                    tagAdd2.setVisibility(View.VISIBLE);
                    tagReduce2.setVisibility(View.VISIBLE);
                    tagAdd1.setVisibility(View.GONE);
                    tagReduce1.setVisibility(View.VISIBLE);
                    taglay2.setVisibility(View.VISIBLE);
                    taglay3.setVisibility(View.GONE);
                    offiTag3.setText("");
                }
                if (offiTag4.getText().toString() != "") {
                    offiTag3.setText(offiTag4.getText().toString());
                    tagAdd3.setVisibility(View.VISIBLE);
                    tagReduce3.setVisibility(View.VISIBLE);
                    tagAdd2.setVisibility(View.VISIBLE);
                    tagReduce2.setVisibility(View.VISIBLE);
                    taglay3.setVisibility(View.VISIBLE);
                    taglay4.setVisibility(View.GONE);
                    offiTag4.setText("");
                }
                if (offiTag5.getText().toString() != "") {
                    offiTag4.setText(offiTag5.getText().toString());
                    tagAdd4.setVisibility(View.VISIBLE);
                    tagReduce4.setVisibility(View.VISIBLE);
                    tagAdd3.setVisibility(View.VISIBLE);
                    tagReduce3.setVisibility(View.VISIBLE);
                    taglay4.setVisibility(View.VISIBLE);
                    taglay5.setVisibility(View.GONE);
                    offiTag5.setText("");
                }
            }
        }

    });

    tagReduce2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (taglay3.getVisibility() == View.VISIBLE || taglay4.getVisibility() == View.VISIBLE || taglay5.getVisibility() == View.VISIBLE) {
                if (offiTag3.getText().toString() != "") {
                    offiTag2.setText(offiTag3.getText().toString());
                    tagAdd2.setVisibility(View.VISIBLE);
                    tagReduce2.setVisibility(View.VISIBLE);
                    tagAdd1.setVisibility(View.GONE);
                    tagReduce1.setVisibility(View.VISIBLE);
                    taglay2.setVisibility(View.VISIBLE);
                    taglay3.setVisibility(View.GONE);
                    offiTag3.setText("");

                }
                if (offiTag4.getText().toString() != "") {
                    offiTag3.setText(offiTag4.getText().toString());
                    tagAdd3.setVisibility(View.VISIBLE);
                    tagReduce3.setVisibility(View.VISIBLE);
                    tagAdd2.setVisibility(View.VISIBLE);
                    tagReduce2.setVisibility(View.VISIBLE);
                    taglay3.setVisibility(View.VISIBLE);
                    taglay4.setVisibility(View.GONE);
                    offiTag4.setText("");

                }
                if (offiTag5.getText().toString() != "") {
                    offiTag4.setText(offiTag5.getText().toString());
                    tagAdd4.setVisibility(View.VISIBLE);
                    tagReduce4.setVisibility(View.VISIBLE);
                    tagAdd3.setVisibility(View.VISIBLE);
                    tagReduce3.setVisibility(View.VISIBLE);
                    taglay4.setVisibility(View.VISIBLE);
                    taglay5.setVisibility(View.GONE);
                    offiTag5.setText("");

                }
            } else {
                tagAdd1.setVisibility(View.VISIBLE);
                tagReduce1.setVisibility(View.GONE);
                taglay2.setVisibility(View.GONE);
                offiTag2.setText("");
            }
        }

    });

    tagReduce3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view){
                if (taglay4.getVisibility() == View.VISIBLE || taglay5.getVisibility() == View.VISIBLE) {
                    if (offiTag4.getText().toString() != "") {
                        offiTag3.setText(offiTag4.getText().toString());
                        tagAdd3.setVisibility(View.VISIBLE);
                        tagReduce3.setVisibility(View.VISIBLE);
                        tagAdd2.setVisibility(View.VISIBLE);
                        tagReduce2.setVisibility(View.VISIBLE);
                        taglay3.setVisibility(View.VISIBLE);
                        taglay4.setVisibility(View.GONE);
                        offiTag4.setText("");

                    }
                    if (offiTag5.getText().toString() != "") {
                        offiTag4.setText(offiTag5.getText().toString());
                        tagAdd4.setVisibility(View.VISIBLE);
                        tagReduce4.setVisibility(View.VISIBLE);
                        tagAdd3.setVisibility(View.VISIBLE);
                        tagReduce3.setVisibility(View.VISIBLE);
                        taglay4.setVisibility(View.VISIBLE);
                        taglay5.setVisibility(View.GONE);
                        offiTag5.setText("");
                    }
                } else {
                    tagAdd2.setVisibility(View.VISIBLE);
                    tagReduce2.setVisibility(View.VISIBLE);
                    taglay3.setVisibility(View.GONE);
                    offiTag3.setText("");
                }
            }
    });

    tagReduce4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view){
                if (taglay5.getVisibility() == View.VISIBLE) {
                    if (offiTag5.getText().toString() != "") {
                        offiTag4.setText(offiTag5.getText().toString());
                        tagAdd4.setVisibility(View.VISIBLE);
                        tagReduce4.setVisibility(View.VISIBLE);
                        tagAdd3.setVisibility(View.VISIBLE);
                        tagReduce3.setVisibility(View.VISIBLE);
                        taglay4.setVisibility(View.VISIBLE);
                        taglay5.setVisibility(View.GONE);
                        offiTag5.setText("");
                    }
                } else {
                    tagAdd3.setVisibility(View.VISIBLE);
                    tagReduce3.setVisibility(View.VISIBLE);
                    taglay4.setVisibility(View.GONE);
                    offiTag4.setText("");
                }
            }

    });

    tagReduce5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view){
                tagAdd4.setVisibility(View.VISIBLE);
                tagReduce4.setVisibility(View.VISIBLE);
                taglay5.setVisibility(View.GONE);
                offiTag5.setText("");
            }
    });
}


    private SearchView.OnQueryTextListener OnQuerySearchView = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onQueryTextChange(String arg0) {
            // TODO Auto-generated method stub
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };

   public void getIndivl()
   {
       apiInterface.getGetIndiCourseDetails(coachid_).enqueue(new Callback<GetIndiCoachDetailsResponse>() {
           @RequiresApi(api = Build.VERSION_CODES.M)
           @Override
           public void onResponse(@NonNull Call<GetIndiCoachDetailsResponse> call, @NonNull Response<GetIndiCoachDetailsResponse> response) {
               assert response.body() != null;
               if(response.body().getIsSuccess().equals("true")){
                   System.out.println("- --> " + new Gson().toJson(response.body()));
                   IndiCourseData indiCourseData = response.body().getGetIndiCoachDetailsModel();
                   if(indiCourseData.getIndicouresedata().size() != 0){

                   GetIndiCoachDetailsModel getIndiCoachDetailsModel = indiCourseData.getIndicouresedata().get(0);
                   Indi_Course_Description.setText(getIndiCoachDetailsModel.getDescription());
                   Indi_Course_Transport.setText(getIndiCoachDetailsModel.getMode_of_Transport());
                   Indi_Course_Tech.setText(getIndiCoachDetailsModel.getTechnical_provided());
                   Indi_Course_Hr.setText(getIndiCoachDetailsModel.getPrice_min());
                   Indi_Course_Ten_Hr.setText(getIndiCoachDetailsModel.getPrice_max());
                   Indi_Course_Video_1.setText(getIndiCoachDetailsModel.getVideo());
                   indi_course_place.setText(getIndiCoachDetailsModel.getLocation());
                   indi_course_postalcode.setText(getIndiCoachDetailsModel.getPostalcode());
                   if (getIndiCoachDetailsModel.getMode_of_Transport() != null) {
                       String ass = getIndiCoachDetailsModel.getMode_of_Transport();
                       String[] stringArrayList = ass.split(",");
                       for (String s : stringArrayList) {
                           switch (s) {
                               case "Car":
                                   Indival_Car.setChecked(true);
                                   break;
                               case "Vélo":
                                   Indival_Bike.setChecked(true);
                                   break;
                               case "Train":
                                   Indival_Train.setChecked(true);
                                   break;
                               case "Bus":
                                   Indival_Bus.setChecked(true);
                                   break;
                           }
                       }
                   }
                   if (getIndiCoachDetailsModel.getPlan() != null) {
                       if (getIndiCoachDetailsModel.getPlan().equals("Commission")) {
                           Indival_Commission.setChecked(true);
                       } else if (getIndiCoachDetailsModel.getPlan().equals("Abonnement")) {
                           Indival_Abonnement.setChecked(true);
                       }

                   }



                   previewTint();
                   menumain.findItem(Menus.EDIT).setVisible(true);
                   }else {
                       previewTint();
                       menumain.findItem(Menus.EDIT).setVisible(true);
                   }

               }else {
                   Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
               }
           }
           @Override
           public void onFailure(Call<GetIndiCoachDetailsResponse> call, Throwable t) {
               System.out.println("---> "+ call +" "+ t);
           }
       });

   }


//   public void Intil(){
//       ArrayList<ClubAvabilityModelData> clubAvabilityModelData = new ArrayList<ClubAvabilityModelData>();
//
//       clubAvabilityModelData.add(new ClubAvabilityModelData("Tennis","bala","1","1","1","1"));
//       clubAvabilityModelData.add(new ClubAvabilityModelData("Tennis","bala","1","1","1","1"));
//       clubAvabilityModelData.add(new ClubAvabilityModelData("Tennis1","bala","1","1","1","1"));
//       for(int i=0;i<clubAvabilityModelData.size();i++){
//           ClubAvabilityModelData clubAvabilityModelData1 = clubAvabilityModelData.get(i);
//           if(clubavabilityfull.size() == 0){
//               for (int k=0;k<clubavabilityfull.size();k++){
//               ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList= clubavabilityfull.get(k);
//               ClubAvabilityModelData clubAvabilityModelData2 = clubAvabilityModelDataArrayList.get(k);
//               if(clubAvabilityModelData2.getCourse().equals(clubAvabilityModelData1.getCourse())){
//                   clubAvabilityModelDataArrayList.add(clubAvabilityModelData1);
//                          clubavabilityfull.set(k,clubAvabilityModelDataArrayList);
//               }else if(clubavabilityfull.size() == k+1){
//                    ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
//                     clubAvabilityModelDataArrayList1.add(clubAvabilityModelData1);
//                     clubavabilityfull.add(clubAvabilityModelDataArrayList1);
//               }
//           }
//       }else {
//        ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
//        clubAvabilityModelDataArrayList1.add(clubAvabilityModelData1);
//        clubavabilityfull.add(clubAvabilityModelDataArrayList1);
//       }
//
//
//}
//   }

}
