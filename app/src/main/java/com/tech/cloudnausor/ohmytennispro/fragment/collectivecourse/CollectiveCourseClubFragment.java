package com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.adapter.ClubAvaibilityAdapter;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.ClubAvabilityModelData;
import com.tech.cloudnausor.ohmytennispro.model.GetClubDataModel;
import com.tech.cloudnausor.ohmytennispro.model.GetClubModel;
import com.tech.cloudnausor.ohmytennispro.model.OndemandCourseModel;
import com.tech.cloudnausor.ohmytennispro.response.GetClubResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectiveCourseClubFragment extends Fragment {


    private boolean mSearchCheck;
    private TextView mTxtDownload;
    Menu menumain;
    ApiInterface apiInterface;
    ArrayList<String> mod_of_transport = new ArrayList<String>();

    TextInputLayout Club_name_error,Club_location_error,Club_description_error,Club_price_error,
            Club_video_error,Club_technic_error, Club_Max_Person_error,club_postalcode_error;
    TextInputEditText Club_name,Club_location,Club_description,Club_price,
            Club_video,Club_technic,Club_Max_Person,club_postalcode;
    int intvalue = 0;
    CheckBox Club_Car,Club_Bike,Club_Train,Club_Bus;
    RadioButton Club_Commission;
    LinearLayout save_button_hide_show;
    Button Club_Cancel,Club_Save;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;
    RecyclerView clubAvaibility;
    Button Add_club_course;
    ClubAvaibilityAdapter clubAvaibilityAdapter ;
    ArrayList<ArrayList<ClubAvabilityModelData>> clubavabilityfull = new ArrayList<ArrayList<ClubAvabilityModelData>>();
    AlertDialog.Builder dialogBuilder;
    LayoutInflater inflater;
    TextInputEditText club_course_name,club_course_start,club_course_end,club_max_person_name,club_prix_name;
    TextInputLayout club_course_name_error,club_course_star_errort,club_course_end_error,club_max_person_error,club_prix_name_error;

    Spinner type_spinner;
    AlertDialog alertDialog;
    View dialogView;
    Button avaibility_ok,avaibility_cancel;
    String enable = "0";
    SingleTonProcess singleTonProcess;



    public CollectiveCourseClubFragment newInstance(String text){
        CollectiveCourseClubFragment mFragment = new CollectiveCourseClubFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_collective_course_club, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Club_name_error =(TextInputLayout)rootView.findViewById(R.id.club_name_error);
        Club_location_error =(TextInputLayout)rootView.findViewById(R.id.club_location_error);
        Club_description_error =(TextInputLayout)rootView.findViewById(R.id.club_description_error);
        Club_Max_Person_error =(TextInputLayout)rootView.findViewById(R.id.club_max_person_error);
        club_postalcode_error =(TextInputLayout)rootView.findViewById(R.id.club_postalcode_error);
        Club_price_error =(TextInputLayout)rootView.findViewById(R.id.club_price_error);
        Club_video_error =(TextInputLayout)rootView.findViewById(R.id.club_video_error);
        Club_technic_error =(TextInputLayout)rootView.findViewById(R.id.club_technic_error);
        Club_name =(TextInputEditText)rootView.findViewById(R.id.club_name);
        Club_location =(TextInputEditText)rootView.findViewById(R.id.club_location);
        Club_description =(TextInputEditText)rootView.findViewById(R.id.club_description);
        Club_price =(TextInputEditText)rootView.findViewById(R.id.club_price);
        save_button_hide_show = (LinearLayout)rootView.findViewById(R.id.save_button_hide_show);
        Add_club_course =(Button)rootView.findViewById(R.id.Add_club_course) ;
        club_postalcode =(TextInputEditText)rootView.findViewById(R.id.club_postalcode);
        singleTonProcess = singleTonProcess.getInstance();


        Club_video =(TextInputEditText)rootView.findViewById(R.id.club_video);
        Club_technic =(TextInputEditText)rootView.findViewById(R.id.club_technic);
        Club_Max_Person =(TextInputEditText)rootView.findViewById(R.id.club_max_person);
        Club_Car=(CheckBox) rootView.findViewById(R.id.club_car);
        Club_Bike=(CheckBox)rootView.findViewById(R.id.club_bike);
        Club_Train=(CheckBox)rootView.findViewById(R.id.club_train);
        Club_Bus=(CheckBox)rootView.findViewById(R.id.club_bus);
        Club_Commission =(RadioButton) rootView.findViewById(R.id.club_commission);

        Club_Cancel = (Button)rootView.findViewById(R.id.club_cancel) ;
        Club_Save = (Button)rootView.findViewById(R.id.club_save) ;

        sharedPreferences = getContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getContext());
        clubAvaibility=(RecyclerView)rootView.findViewById(R.id.clubAvaibility);


        if (sharedPreferences.contains("KEY_Coach_ID"))
        {
            coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");

        }
        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");
        }

        clubAvaibilityAdapter = new ClubAvaibilityAdapter(getContext(),clubavabilityfull,this.getActivity(),"1",coachid_);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        clubAvaibility.setLayoutManager(new LinearLayoutManager(getContext()));
        clubAvaibility.setAdapter(clubAvaibilityAdapter);

        Club_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClub();
            }
        });

        Add_club_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(view.getContext()));
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_club_avaibility, null);
                dialogBuilder.setView(dialogView);

                club_course_name =(TextInputEditText) dialogView.findViewById(R.id.club_course_name);
                club_course_start=(TextInputEditText) dialogView.findViewById(R.id.club_course_start);
                club_course_end=(TextInputEditText) dialogView.findViewById(R.id.club_course_end);
                club_max_person_name=(TextInputEditText) dialogView.findViewById(R.id.club_max_person_name);
                club_prix_name=(TextInputEditText) dialogView.findViewById(R.id.club_prix_name);
                type_spinner = (Spinner)dialogView.findViewById(R.id.type_spinner);
                club_course_name_error =(TextInputLayout) dialogView.findViewById(R.id.club_course_name_error);
                club_course_star_errort=(TextInputLayout) dialogView.findViewById(R.id.club_course_start_error);
                club_course_end_error=(TextInputLayout) dialogView.findViewById(R.id.club_course_end_error);
                club_max_person_error=(TextInputLayout) dialogView.findViewById(R.id.club_max_person_error);
                club_prix_name_error=(TextInputLayout) dialogView.findViewById(R.id.club_prix_name_error);
                avaibility_ok =(Button)dialogView.findViewById(R.id.avaibility_ok) ;
                avaibility_cancel =(Button)dialogView.findViewById(R.id.avaibility_cancel) ;
                avaibility_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!club_course_name.getText().toString().equals("") && !club_course_start.getText().toString().equals("") && !club_course_end.getText().toString().equals("")
                                && !club_prix_name.getText().toString().equals("")){
                            ClubAvabilityModelData clubAvabilityModelData = new ClubAvabilityModelData(coachid_,"","","",""
                                    ,"","","0");
//                club_course_name =(TextInputEditText) dialogView.findViewById(R.id.club_course_name);
//                club_course_start=(TextInputEditText) dialogView.findViewById(R.id.club_course_start);
//                club_course_end=(TextInputEditText) dialogView.findViewById(R.id.club_course_end);
//                club_max_person_name=(TextInputEditText) dialogView.findViewById(R.id.club_max_person_name);
//                club_prix_name=(TextInputEditText) dialogView.findViewById(R.id.club_prix_name)
                            clubAvabilityModelData.setCourse(club_course_name.getText().toString());
//                     clubAvabilityModelData.setWeekday(club_c.getText().toString());
                            clubAvabilityModelData.setStartTime(club_course_start.getText().toString());
                            clubAvabilityModelData.setEndTime(club_course_end.getText().toString());
                            clubAvabilityModelData.setMaxCount(club_max_person_name.getText().toString());
                            clubAvabilityModelData.setPrice(club_prix_name.getText().toString());
                            String text = type_spinner.getSelectedItem().toString();
                            clubAvabilityModelData.setWeekday(text);

//                     clubAvabilityModelData.setWeekday(type_spinner.getAdapter());


                            if(clubavabilityfull.size() != 0){
                                for (int k=0;k < clubavabilityfull.size();k++){
                                    ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList = clubavabilityfull.get(k);
                                    System.out.println("ArrayList---> "+new Gson().toJson(clubAvabilityModelDataArrayList));
                                    ClubAvabilityModelData clubAvabilityModelData2 = clubAvabilityModelDataArrayList.get(0);
                                    if(clubAvabilityModelData2.getCourse().equals(clubAvabilityModelData.getCourse()) && clubAvabilityModelData2.getWeekday().equals(clubAvabilityModelData.getWeekday())){
                                        clubAvabilityModelDataArrayList.add(clubAvabilityModelData);
                                        clubavabilityfull.set(k,clubAvabilityModelDataArrayList);
                                        break;
                                    }else if(clubavabilityfull.size() == k+1){
                                        ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
                                        clubAvabilityModelDataArrayList1.add(clubAvabilityModelData);
                                        clubavabilityfull.add(clubAvabilityModelDataArrayList1);
                                        break;
                                    }
                                }
                                ((RealMainPageActivity)getContext()).dismissprocess();
                            }else {
                                ((RealMainPageActivity)getContext()).dismissprocess();
                                ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
                                clubAvabilityModelDataArrayList1.add(clubAvabilityModelData);
                                clubavabilityfull.add(clubAvabilityModelDataArrayList1);
                            }

                            clubAvaibilityAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }else{
                            ((RealMainPageActivity)getContext()).dismissprocess();
                            Toast.makeText(getActivity(), "Please enter all data.", Toast.LENGTH_LONG).show();

                        }
                    }
                });

                club_course_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        club_course_start.setText( selectedHour + ":" + selectedMinute);
                                club_course_start.setText(String.format("%02d:%02d", selectedHour, selectedMinute));

                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });

                club_course_end.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                club_course_end.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });

                avaibility_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog = dialogBuilder.create();
                alertDialog.show();

            }
        });

        ((RealMainPageActivity)getContext()).showprocess();





        Club_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String editvalue = charSequence.toString();
//               if(editvalue.equals("")){
//
//               }else if(){
//
//               }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();
                if(editvalue.equals("")){
                    Club_name_error.setErrorEnabled(true);
                    Club_name_error.setError("Indiquez Votre Club nom.");
                }else {
                    Club_name_error.setErrorEnabled(false);
                    Club_name_error.setError(null);
                }
            }
        });
        Club_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();
                if(editvalue.equals("")){
                    Club_location_error.setErrorEnabled(true);
                    Club_location_error.setError("Indiquez Votre Emplacement.");
                }else {
                    Club_location_error.setErrorEnabled(false);
                    Club_location_error.setError(null);
                }
            }
        });

        Club_description .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();

                if(editvalue.equals("")){
                    Club_description_error.setErrorEnabled(true);
                    Club_description_error.setError("Indiquez Votre Description.");
                }else {
                    Club_description_error.setErrorEnabled(false);
                    Club_description_error.setError(null);
                }
            }
        });
        club_postalcode .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();

                if(editvalue.equals("")){
                    club_postalcode_error.setErrorEnabled(true);
                    club_postalcode_error.setError("Indiquez Votre Postal code.");
                }else {
                    club_postalcode_error.setErrorEnabled(false);
                    club_postalcode_error.setError(null);
                }
            }
        });


        Club_Max_Person .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();

                if(editvalue.equals("")){
                    Club_Max_Person_error.setErrorEnabled(true);
                    Club_Max_Person_error.setError("Indiquez Votre Nombre de personnes.");
                }else {
                    Club_Max_Person_error.setErrorEnabled(false);
                    Club_Max_Person_error.setError(null);
                }
            }
        });

        Club_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();
                if(!editvalue.equals("")){
                    intvalue =  Integer.parseInt(editvalue);
                }
                if(editvalue.equals("")){
                    Club_price_error.setErrorEnabled(true);
                    Club_price_error.setError("Indiquez Votre Prix.");
                }else {
                    Club_price_error.setErrorEnabled(false);
                    Club_price_error.setError(null);
                }
            }
        });
        Club_video .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();
                if(editvalue.equals("")){
                    Club_video_error.setErrorEnabled(true);
                    Club_video_error.setError("Indiquez Votre Vidéo URL.");
                }else {
                    Club_video_error.setErrorEnabled(false);
                    Club_video_error.setError(null);
                }
            }
        });
        Club_technic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();
                if(editvalue.equals("")){
                    Club_technic_error.setErrorEnabled(true);
                    Club_technic_error.setError("Indiquez Votre Technique Fournie.");
                }else {
                    Club_technic_error.setErrorEnabled(false);
                    Club_technic_error.setError(null);
                }
            }
        });

        club_postalcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = club_postalcode.getText().toString();
                    if(editvalue.equals("")){
                        club_postalcode_error.setErrorEnabled(true);
                        club_postalcode_error.setError("Indiquez Votre  Club nom.");
                    }else {
                        club_postalcode_error.setErrorEnabled(false);
                        club_postalcode_error.setError(null);
                    }
                }
            }
        });
        Club_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Club_name.getText().toString();
                    if(editvalue.equals("")){
                        Club_name_error.setErrorEnabled(true);
                        Club_name_error.setError("Indiquez Votre  Club nom.");
                    }else {
                        Club_name_error.setErrorEnabled(false);
                        Club_name_error.setError(null);
                    }
                }
            }
        });


        Club_location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Club_location.getText().toString();
                    if(editvalue.equals("")){
                        Club_location_error.setErrorEnabled(true);
                        Club_location_error.setError("Indiquez Votre Emplacement.");
                    }else {
                        Club_location_error.setErrorEnabled(false);
                        Club_location_error.setError(null);
                    }
                }
            }
        });
        Club_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Club_description.getText().toString();
                    if(editvalue.equals("")){
                        Club_description_error.setErrorEnabled(true);
                        Club_description_error.setError("Indiquez Votre Description.");
                    }else {
                        Club_description_error.setErrorEnabled(false);
                        Club_description_error.setError(null);
                    }
                }
            }
        });
        Club_Max_Person.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Club_Max_Person.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }
                    if(editvalue.equals("")){
                        Club_Max_Person_error.setErrorEnabled(true);
                        Club_Max_Person_error.setError("Indiquez Votre Nombre de personnes.");
                    }else {
                        Club_Max_Person_error.setErrorEnabled(false);
                        Club_Max_Person_error.setError(null);
                    }
                }
            }
        });
        Club_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Club_price.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }
                    if(editvalue.equals("")){
                        Club_price_error.setErrorEnabled(true);
                        Club_price_error.setError("Indiquez Votre Prix.");
                    }else {
                        Club_price_error.setErrorEnabled(false);
                        Club_price_error.setError(null);
                    }
                }
            }
        });
        Club_video.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Club_video.getText().toString();
                    if(editvalue.equals("")){
                        Club_video_error.setErrorEnabled(true);
                        Club_video_error.setError("Indiquez Votre Vidéo URL.");
                    }else {
                        Club_video_error.setErrorEnabled(false);
                        Club_video_error.setError(null);
                    }
                }
            }
        });
        Club_technic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Club_technic.getText().toString();
                    if(editvalue.equals("")){
                        Club_technic_error.setErrorEnabled(true);
                        Club_technic_error.setError("Indiquez Votre Technique Fournie.");
                    }else {
                        Club_technic_error.setErrorEnabled(false);
                        Club_technic_error.setError(null);
                    }
                }
            }
        });
        Club_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClub();
                menumain.findItem(Menus.EDIT).setVisible(true);

            }
        });
        preview();
        getClub();
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return rootView;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case Menus.ADD:
                break;

                case Menus.EDIT:
                    EditView();
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



    private void getClub(){
        ((RealMainPageActivity)getContext()).showprocess();
        apiInterface.getcousecollectiveclub(coachid_).enqueue(new Callback<GetClubResponseData>() {
            @Override
            public void onResponse(@NonNull Call<GetClubResponseData> call, @NonNull Response<GetClubResponseData> response) {
                assert response.body() != null;
                if(response.body().getIsSuccess().equals("true")) {
                    if (!response.body().getClubDataModel().getGetClubModels().isEmpty()){
                        if (response.body().getClubDataModel().getGetClubModels().get(0) != null) {
                            GetClubModel getClubModel = response.body().getClubDataModel().getGetClubModels().get(0);
                            Club_name.setText(getClubModel.getClub_Name());
                            Club_location.setText(getClubModel.getPlace());
                            Club_description.setText(getClubModel.getDescription());
                            Club_price.setText(getClubModel.getPrice());
                            Club_video.setText(getClubModel.getVideo());
                            Club_technic.setText(getClubModel.getTechnical_Provided());
//                            Club_Max_Person.setText(getClubModel.getMaxCount());
                            club_postalcode.setText(getClubModel.getPostalcode());

                            if (getClubModel.getMode_of_Transport() != null) {
                                String ass = getClubModel.getMode_of_Transport();
                                String[] stringArrayList = ass.split(",");
                                for (String s : stringArrayList) {
                                    switch (s) {
                                        case "Car":
                                            Club_Car.setChecked(true);
                                            break;
                                        case "Vélo":
                                            Club_Bike.setChecked(true);
                                            break;
                                        case "Train":
                                            Club_Train.setChecked(true);
                                            break;
                                        case "Bus":
                                            Club_Bus.setChecked(true);
                                            break;
                                    }
                                }
                            }
                            if (getClubModel.getPlan() != null) {
                                if (getClubModel.getPlan().equals("Commission")) {
                                    Club_Commission.setChecked(true);
                                }
                            }
                            preview();
                            enable = "0";
                            clubAvaibilityAdapter.notifyDataSetChanged();
                            menumain.findItem(Menus.EDIT).setVisible(true);
                        }

                        if(response.body().getClubDataModel().getClubAvabilityModelData() != null){
                            if(response.body().getClubDataModel().getClubAvabilityModelData().size() == 1){
                                if(!response.body().getClubDataModel().getClubAvabilityModelData().get(0).getCoachId().equals("") && !response.body().getClubDataModel().getClubAvabilityModelData().get(0).getCourse().equals("")){
                                    Intil(response.body().getClubDataModel().getClubAvabilityModelData());
                                }
                            }else {
                                 Intil(response.body().getClubDataModel().getClubAvabilityModelData());
                            }
                        }
                         ((RealMainPageActivity)getContext()).dismissprocess();
                }else {
                         ((RealMainPageActivity)getContext()).dismissprocess();
                        preview();
//                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
                }else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                        preview();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<GetClubResponseData> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });
    }

    private void setClub(){
        ((RealMainPageActivity)getContext()).showprocess();

        ArrayList<ClubAvabilityModelData> sendClubAvabilityModelDataArrayList = new ArrayList<ClubAvabilityModelData>();
        GetClubModel getClubModel = new GetClubModel(coachid_,"","","base64","","","","","","0"
                ,"",sendClubAvabilityModelDataArrayList);
        if( !Club_name_error.isErrorEnabled() &&
        !Club_location_error.isErrorEnabled() &&
        !Club_description_error.isErrorEnabled() &&
        !club_postalcode_error.isErrorEnabled() &&
        !Club_video_error.isErrorEnabled() &&
        !Club_technic_error.isErrorEnabled()
             ) {
           if(clubavabilityfull.size() != 0){
            if (Club_Car.isChecked() ||
                    Club_Bike.isChecked() ||
                    Club_Train.isChecked() ||
                    Club_Bus.isChecked()) {
                if (Club_Commission.isChecked()) {

                    mod_of_transport.clear();
                    if (Club_Car.isChecked()) {
                        mod_of_transport.add("Car");
                    }
                    if (Club_Bike.isChecked()) {
                        mod_of_transport.add("Vélo");
                    }
                    if (Club_Train.isChecked()) {
                        mod_of_transport.add("Train");
                    }
                    if (Club_Bus.isChecked()) {
                        mod_of_transport.add("Bus");
                    }
                    String plan = "";
                    if (Club_Commission.isChecked()) {
                        plan = Club_Commission.getText().toString();
                    }

//                    Club_name.setText(getClubModel.getClub_Name());
//                    Club_location.setText(getClubModel.getPlace());
//                    Club_description.setText(getClubModel.getDescription());
//                    Club_price .setText(getClubModel.getPrice());
//                    Club_video.setText(getClubModel.getVideo());
//                    Club_technic.setText(getClubModel.getTechnical_Provided());
//                    Club_Max_Person.setText(getClubModel.getMaxCount());
                    for (int w = 0; w < clubavabilityfull.size(); w++) {
                        sendClubAvabilityModelDataArrayList.addAll(clubavabilityfull.get(w));
                    }

                    String transport = TextUtils.join(",", mod_of_transport);
                    getClubModel.setClub_Name(Club_name.getText().toString());
                    getClubModel.setPlace(Club_location.getText().toString());
                    getClubModel.setDescription(Club_description.getText().toString());
                    getClubModel.setPrice(Club_price.getText().toString());
                    getClubModel.setVideo(Club_video.getText().toString());
                    getClubModel.setTechnical_Provided(Club_technic.getText().toString());
//                    getClubModel.setMaxCount(Club_Max_Person.getText().toString());
                    getClubModel.setPostalcode(club_postalcode.getText().toString());
                    getClubModel.setMode_of_Transport(transport);
                    getClubModel.setPlan(plan);
                    getClubModel.setClubAvabilityModelData(sendClubAvabilityModelDataArrayList);

                    apiInterface.setcousecollectivedemanad(getClubModel).enqueue(new Callback<GetClubResponseData>() {
                        @Override
                        public void onResponse(@NonNull Call<GetClubResponseData> call, @NonNull Response<GetClubResponseData> response) {
                            assert response.body() != null;
                            if (response.body().getIsSuccess().equals("true")) {
                                 ((RealMainPageActivity)getContext()).dismissprocess();
                                getClub();
                                Toast.makeText(getActivity(), "Club Collectif  mis à jour avec succès", Toast.LENGTH_LONG).show();
                            } else {
                                 ((RealMainPageActivity)getContext()).dismissprocess();
                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetClubResponseData> call, Throwable t) {
                             ((RealMainPageActivity)getContext()).dismissprocess();
                            System.out.println("---> " + call + " " + t);
                        }
                    });
                } else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(), "Veuillez sélectionner au moins un  plan", Toast.LENGTH_LONG).show();
                }

            } else {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                Toast.makeText(getActivity(), "Veuillez sélectionner au moins un transport", Toast.LENGTH_LONG).show();
            }
        }else {
                ((RealMainPageActivity)getContext()).dismissprocess();
            Toast.makeText(getActivity(),"Veuillez sélectionner au moins un cours",Toast.LENGTH_LONG).show();
        }
        }else {
             ((RealMainPageActivity)getContext()).dismissprocess();
            Toast.makeText(getActivity(),"Veuillez saisir toutes les données",Toast.LENGTH_LONG).show();
        }


    }

    public void preview(){

        Club_name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Club_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Club_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Club_price .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Club_video.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Club_technic .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Club_technic .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Club_Max_Person.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        club_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));

        Club_Car.setEnabled(false);
        Club_Bike .setEnabled(false);
        Club_Train.setEnabled(false);
        Club_Bus .setEnabled(false);
        Club_name.setEnabled(false);
        Club_location.setEnabled(false);
        Club_description.setEnabled(false);
        Club_price .setEnabled(false);
        Club_video.setEnabled(false);
        Club_technic .setEnabled(false);
        Club_Max_Person.setEnabled(false);
        Add_club_course.setEnabled(false);
        club_postalcode.setEnabled(false);
        save_button_hide_show.setVisibility(View.GONE);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void EditView(){
         enable = "1";
         clubAvaibilityAdapter.notifyDataSetChanged();
        Club_Car.setEnabled(true);
        Club_Bike .setEnabled(true);
        Club_Train.setEnabled(true);
        Club_Bus .setEnabled(true);
        Club_name.setEnabled(true);
        Club_location.setEnabled(true);
        Club_description.setEnabled(true);
        Club_price .setEnabled(true);
        Club_video.setEnabled(true);
        Club_technic .setEnabled(true);
        Club_Max_Person.setEnabled(true);
        Add_club_course.setEnabled(true);
        club_postalcode.setEnabled(true);
        save_button_hide_show.setVisibility(View.VISIBLE);
        club_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Club_name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Club_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Club_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Club_price .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Club_video.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Club_technic .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Club_Max_Person.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));



    }

    public void Intil(ArrayList<ClubAvabilityModelData> clubAvabilityModelData){
//        ArrayList<ClubAvabilityModelData> clubAvabilityModelData = new ArrayList<ClubAvabilityModelData>();
//
//        clubAvabilityModelData.add(new ClubAvabilityModelData("","bala","1","1","1","1","Tennis",""));
//        clubAvabilityModelData.add(new ClubAvabilityModelData("","bala","1","1","1","1","Tennis",""));
//        clubAvabilityModelData.add(new ClubAvabilityModelData("","bala","1","1","1","1","Tennis1",""));
        clubavabilityfull.clear();
        for(int i=0;i<clubAvabilityModelData.size();i++){
            ClubAvabilityModelData clubAvabilityModelData1 = clubAvabilityModelData.get(i);
            if(clubavabilityfull.size() != 0){
                for (int k=0;k < clubavabilityfull.size();k++){
                    ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList = clubavabilityfull.get(k);
                    System.out.println("ArrayList---> "+new Gson().toJson(clubAvabilityModelDataArrayList));
                    ClubAvabilityModelData clubAvabilityModelData2 = clubAvabilityModelDataArrayList.get(0);
                    if(clubAvabilityModelData2.getCourse().equals(clubAvabilityModelData1.getCourse()) && clubAvabilityModelData2.getWeekday().equals(clubAvabilityModelData1.getWeekday())){
                        clubAvabilityModelDataArrayList.add(clubAvabilityModelData1);
                        clubavabilityfull.set(k,clubAvabilityModelDataArrayList);
                        break;
                    }else if(clubavabilityfull.size() == k+1){
                        ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
                        clubAvabilityModelDataArrayList1.add(clubAvabilityModelData1);
                        clubavabilityfull.add(clubAvabilityModelDataArrayList1);
                        break;
                    }
                }
            }else {
                ArrayList<ClubAvabilityModelData> clubAvabilityModelDataArrayList1= new ArrayList<ClubAvabilityModelData>();
                clubAvabilityModelDataArrayList1.add(clubAvabilityModelData1);
                clubavabilityfull.add(clubAvabilityModelDataArrayList1);
            }
        }

        clubAvaibilityAdapter.notifyDataSetChanged();
    }

}