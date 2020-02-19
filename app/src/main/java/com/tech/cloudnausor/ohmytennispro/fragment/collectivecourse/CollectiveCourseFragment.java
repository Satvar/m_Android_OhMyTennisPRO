package com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.collectivecourse.CollectiveCourseActivity;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.GetIndiCoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.IndiCourseData;
import com.tech.cloudnausor.ohmytennispro.model.OnDeamndModel;
import com.tech.cloudnausor.ohmytennispro.model.OndemandCourseDataModel;
import com.tech.cloudnausor.ohmytennispro.model.OndemandCourseModel;
import com.tech.cloudnausor.ohmytennispro.response.CoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetIndiCoachDetailsResponse;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectiveCourseFragment extends Fragment {

    private boolean mSearchCheck;
    int intvalue = 0;
    private TextView mTxtDownload;
    Menu menumain;
    EditText CC_Transport;
    String transportValue = "";
    ApiInterface apiInterface;
    ArrayList<String> mod_of_transport = new ArrayList<String>();

    Button Ondemand_Cancel,Ondemand_Save;

    CheckBox Transportcheck_1,Transportcheck_2,Transportcheck_3,Transportcheck_4;
    ArrayList<String> transportHolder = new ArrayList<>();
    Button Transport_cancel,Transport_ok;
   TextInputLayout Ondemand_min_person_error,Ondemand_max_person_error,ondemand_location_error,ondemand_description_error,ondemand_price_for_2_persons_error,
           ondemand_price_for_3_persons_error,ondemand_price_for_4_persons_error,ondemand_price_for_5_persons_error,ondemand_price_for_6_persons_error
           ,ondemand_postalcode_error;
    TextInputLayout ondemand_price_sunday_error,ondemand_price_monday_error,ondemand_price_tuesday_error,ondemand_price_wednesday_error,
    ondemand_price_thursday_error,ondemand_price_friday_error,ondemand_price_saturday_error;
    TextInputEditText Ondemand_min_person,Ondemand_max_person,ondemand_location,ondemand_description,ondemand_price_for_2_persons,
            ondemand_price_for_3_persons,ondemand_price_for_4_persons,ondemand_price_for_5_persons,ondemand_price_for_6_persons
            ,ondemand_postalcode;
    TextInputEditText ondemand_price_sunday,ondemand_price_monday,ondemand_price_tuesday,ondemand_price_wednesday,
            ondemand_price_thursday,ondemand_price_friday,ondemand_price_saturday;
  CheckBox Ondemand_Car,Ondemand_Bike,Ondemand_Train,Ondemand_Bus;
  RadioButton Ondemand_Commission,Ondemand_Abonnement;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    LinearLayout save_button_hide_show;
    String coachid_ = null;
    String uPassword =null;
    SingleTonProcess singleTonProcess;

    public CollectiveCourseFragment newInstance(String text){
        CollectiveCourseFragment mFragment = new CollectiveCourseFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.activity_collective_course, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        singleTonProcess = singleTonProcess.getInstance();

        Ondemand_min_person_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_min_person_error);
        Ondemand_max_person_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_max_person_error);
        ondemand_location_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_location_error);
        ondemand_description_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_description_error);
        ondemand_price_for_2_persons_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_price_for_2_persons_error);
        ondemand_price_for_3_persons_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_price_for_3_persons_error);
        ondemand_price_for_4_persons_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_price_for_4_persons_error);
        ondemand_price_for_5_persons_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_price_for_5_persons_error);
        ondemand_price_for_6_persons_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_price_for_6_persons_error);

        ondemand_price_sunday_error=(TextInputLayout)rootView.findViewById(R.id.ondemand_price_sunday_error);
        ondemand_price_monday_error=(TextInputLayout)rootView.findViewById(R.id.ondemand_price_monday_error);
        ondemand_price_tuesday_error=(TextInputLayout)rootView.findViewById(R.id.ondemand_price_tuesday_error);
        ondemand_price_wednesday_error=(TextInputLayout)rootView.findViewById(R.id.ondemand_price_wednesday_error);
        ondemand_price_thursday_error=(TextInputLayout)rootView.findViewById(R.id.ondemand_price_thursday_error);
        ondemand_price_friday_error=(TextInputLayout)rootView.findViewById(R.id.ondemand_price_friday_error);
        ondemand_price_saturday_error=(TextInputLayout)rootView.findViewById(R.id.ondemand_price_saturday_error);
        ondemand_postalcode_error =(TextInputLayout)rootView.findViewById(R.id.ondemand_postalcode_error);

        ondemand_price_sunday=(TextInputEditText)rootView.findViewById(R.id.ondemand_price_sunday);
        ondemand_price_monday=(TextInputEditText)rootView.findViewById(R.id.ondemand_price_monday);
        ondemand_price_tuesday=(TextInputEditText)rootView.findViewById(R.id.ondemand_price_tuesday);
        ondemand_price_wednesday=(TextInputEditText)rootView.findViewById(R.id.ondemand_price_wednesday);
        ondemand_price_thursday=(TextInputEditText)rootView.findViewById(R.id.ondemand_price_thursday);
        ondemand_price_friday=(TextInputEditText)rootView.findViewById(R.id.ondemand_price_friday);
        ondemand_price_saturday=(TextInputEditText)rootView.findViewById(R.id.ondemand_price_saturday);

        save_button_hide_show = (LinearLayout)rootView.findViewById(R.id.save_button_hide_show);
        Ondemand_min_person =(TextInputEditText)rootView.findViewById(R.id.ondemand_min_person);
        Ondemand_max_person =(TextInputEditText)rootView.findViewById(R.id.ondemand_max_person);
        ondemand_location =(TextInputEditText)rootView.findViewById(R.id.ondemand_location);
        ondemand_description =(TextInputEditText)rootView.findViewById(R.id.ondemand_description);
        ondemand_price_for_2_persons =(TextInputEditText)rootView.findViewById(R.id.ondemand_price_for_2_persons);
        ondemand_price_for_3_persons =(TextInputEditText)rootView.findViewById(R.id.ondemand_price_for_3_persons);
        ondemand_price_for_4_persons =(TextInputEditText)rootView.findViewById(R.id.ondemand_price_for_4_persons);
        ondemand_price_for_5_persons =(TextInputEditText)rootView.findViewById(R.id.ondemand_price_for_5_persons);
        ondemand_price_for_6_persons =(TextInputEditText)rootView.findViewById(R.id.ondemand_price_for_6_persons);
        ondemand_postalcode =(TextInputEditText)rootView.findViewById(R.id.ondemand_postalcode);


        Ondemand_Car =(CheckBox) rootView.findViewById(R.id.ondemand_car);
        Ondemand_Bike =(CheckBox)rootView.findViewById(R.id.ondemand_bike);
        Ondemand_Train =(CheckBox)rootView.findViewById(R.id.ondemand_train);
        Ondemand_Bus  =(CheckBox)rootView.findViewById(R.id.ondemand_bus);

        Ondemand_Commission =(RadioButton)rootView.findViewById(R.id.ondemand_commission);
        Ondemand_Abonnement =(RadioButton)rootView.findViewById(R.id.ondemand_abonnement);

        Ondemand_Cancel = (Button)rootView.findViewById(R.id.ondemand_cancel) ;
        Ondemand_Save = (Button)rootView.findViewById(R.id.ondemand_save) ;


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

        Ondemand_min_person.addTextChangedListener(new TextWatcher() {
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

            }
        });

        Ondemand_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOndemand();
            }
        });

        Ondemand_min_person.addTextChangedListener(new TextWatcher() {
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
                    Ondemand_min_person_error.setErrorEnabled(true);
                    Ondemand_min_person_error.setError("Indiquez Votre Nombre De Personnes.");
                }else if(intvalue < 2  ){
                    Ondemand_min_person_error.setErrorEnabled(true);
                    Ondemand_min_person_error.setError("Indiquez Votre Nombre De Personnes.");
                }else if(intvalue > 5){
                    Ondemand_min_person_error.setErrorEnabled(true);
                    Ondemand_min_person_error.setError("Indiquez Votre Nombre De Personnes.");
                }else {
                    Ondemand_min_person_error.setErrorEnabled(false);
                    Ondemand_min_person_error.setError(null);
                }
            }
        });

        Ondemand_max_person.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }



            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();
                if(!editvalue.equals("")){
                    intvalue =  Integer.parseInt(editvalue);
                }
                if(editvalue.equals("")){
                    Ondemand_max_person_error.setErrorEnabled(true);
                    Ondemand_max_person_error.setError("Indiquez Votre Nombre De Personnes.");
                }else if(intvalue < 3  ){
                    Ondemand_max_person_error.setErrorEnabled(true);
                    Ondemand_max_person_error.setError("Indiquez Votre Nombre De Personnes.");
                }else if(intvalue > 6){
                    Ondemand_max_person_error.setErrorEnabled(true);
                    Ondemand_max_person_error.setError("Indiquez Votre Nombre De Personnes.");
                }else {
                    Ondemand_max_person_error.setErrorEnabled(false);
                    Ondemand_max_person_error.setError(null);
                }
            }
        });

        ondemand_location.addTextChangedListener(new TextWatcher() {
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
                    ondemand_location_error.setErrorEnabled(true);
                    ondemand_location_error.setError("Indiquez Votre Emplacement.");
                }else {
                    ondemand_location_error.setErrorEnabled(false);
                    ondemand_location_error.setError(null);
                }
            }
        });

        ondemand_description .addTextChangedListener(new TextWatcher() {
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
                    ondemand_description_error.setErrorEnabled(true);
                    ondemand_description_error.setError("Indiquez Votre Description.");
                }else {
                    ondemand_description_error.setErrorEnabled(false);
                    ondemand_description_error.setError(null);
                }
            }
        });

        ondemand_price_for_2_persons.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_for_2_persons_error.setErrorEnabled(true);
                    ondemand_price_for_2_persons_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_for_2_persons_error.setErrorEnabled(false);
                    ondemand_price_for_2_persons_error.setError(null);
                }
            }
        });

        ondemand_price_for_3_persons .addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_for_3_persons_error.setErrorEnabled(true);
                    ondemand_price_for_3_persons_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_for_3_persons_error.setErrorEnabled(false);
                    ondemand_price_for_3_persons_error.setError(null);
                }
            }
        });

        ondemand_postalcode .addTextChangedListener(new TextWatcher() {
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
                    ondemand_postalcode_error.setErrorEnabled(true);
                    ondemand_postalcode_error.setError("Indiquez Votre Postal code.");
                }else {
                    ondemand_postalcode_error.setErrorEnabled(false);
                    ondemand_postalcode_error.setError(null);
                }
            }
        });


        ondemand_price_for_4_persons.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_for_4_persons_error.setErrorEnabled(true);
                    ondemand_price_for_4_persons_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_for_4_persons_error.setErrorEnabled(false);
                    ondemand_price_for_4_persons_error.setError(null);
                }
            }
        });

        ondemand_price_for_5_persons.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_for_5_persons_error.setErrorEnabled(true);
                    ondemand_price_for_5_persons_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_for_5_persons_error.setErrorEnabled(false);
                    ondemand_price_for_5_persons_error.setError(null);
                }
            }
        });

        ondemand_price_for_6_persons.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_for_6_persons_error.setErrorEnabled(true);
                    ondemand_price_for_6_persons_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_for_6_persons_error.setErrorEnabled(false);
                    ondemand_price_for_6_persons_error.setError(null);
                }
            }
        });

        ondemand_price_sunday.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_sunday_error.setErrorEnabled(true);
                    ondemand_price_sunday_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_sunday_error.setErrorEnabled(false);
                    ondemand_price_sunday_error.setError(null);
                }
            }
        });

        ondemand_price_monday.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_monday_error.setErrorEnabled(true);
                    ondemand_price_monday_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_monday_error.setErrorEnabled(false);
                    ondemand_price_monday_error.setError(null);
                }
            }
        });

        ondemand_price_tuesday.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_tuesday_error.setErrorEnabled(true);
                    ondemand_price_tuesday_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_tuesday_error.setErrorEnabled(false);
                    ondemand_price_tuesday_error.setError(null);
                }
            }
        });

        ondemand_price_wednesday.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_wednesday_error.setErrorEnabled(true);
                    ondemand_price_wednesday_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_wednesday_error.setErrorEnabled(false);
                    ondemand_price_wednesday_error.setError(null);
                }
            }
        });

        ondemand_price_thursday.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_thursday_error.setErrorEnabled(true);
                    ondemand_price_thursday_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_thursday_error.setErrorEnabled(false);
                    ondemand_price_thursday_error.setError(null);
                }
            }
        });

        ondemand_price_friday.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_friday_error.setErrorEnabled(true);
                    ondemand_price_friday_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_friday_error.setErrorEnabled(false);
                    ondemand_price_friday_error.setError(null);
                }
            }
        });

        ondemand_price_saturday.addTextChangedListener(new TextWatcher() {
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
                    ondemand_price_saturday_error.setErrorEnabled(true);
                    ondemand_price_saturday_error.setError("Indiquez Votre Prix.");
                }else {
                    ondemand_price_saturday_error.setErrorEnabled(false);
                    ondemand_price_saturday_error.setError(null);
                }
            }
        });



        Ondemand_max_person.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Ondemand_max_person.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }
                    if(editvalue.equals("")){
                        Ondemand_max_person_error.setErrorEnabled(true);
                        Ondemand_max_person_error.setError("Indiquez Votre Nombre De Personnes.");
                    }else if(intvalue < 3  ){
                        Ondemand_max_person_error.setErrorEnabled(true);
                        Ondemand_max_person_error.setError("Indiquez Votre Nombre De Personnes.");
                    }else if(intvalue > 6){
                        Ondemand_max_person_error.setErrorEnabled(true);
                        Ondemand_max_person_error.setError("Indiquez Votre Nombre De Personnes.");
                    }else {
                        Ondemand_max_person_error.setErrorEnabled(false);
                        Ondemand_max_person_error.setError(null);
                    }
                }
            }
        });

        Ondemand_min_person.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Ondemand_max_person.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }
                    if(editvalue.equals("")){
                        Ondemand_min_person_error.setErrorEnabled(true);
                        Ondemand_min_person_error.setError("Indiquez Votre Nombre De Personnes.");
                    }else if(intvalue < 2  ){
                        Ondemand_min_person_error.setErrorEnabled(true);
                        Ondemand_min_person_error.setError("Indiquez Votre Nombre De Personnes.");
                    }else if(intvalue > 5){
                        Ondemand_min_person_error.setErrorEnabled(true);
                        Ondemand_min_person_error.setError("Indiquez Votre Nombre De Personnes.");
                    }else {
                        Ondemand_min_person_error.setErrorEnabled(false);
                        Ondemand_min_person_error.setError(null);
                    }
                }
            }
        });
        ondemand_location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_location.getText().toString();


                    if(editvalue.equals("")){
                        ondemand_location_error.setErrorEnabled(true);
                        ondemand_location_error.setError("Indiquez Votre Emplacement.");
                    }else {
                        ondemand_location_error.setErrorEnabled(false);
                        ondemand_location_error.setError(null);
                    }
                }
            }
        });

        ondemand_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_description.getText().toString();
                    if(editvalue.equals("")){
                        ondemand_description_error.setErrorEnabled(true);
                        ondemand_description_error.setError("Indiquez Votre Description.");
                    }else {
                        ondemand_description_error.setErrorEnabled(false);
                        ondemand_description_error.setError(null);
                    }
                }
            }
        });

        ondemand_price_for_2_persons.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_for_2_persons.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }
                    if(editvalue.equals("")){
                        ondemand_price_for_2_persons_error.setErrorEnabled(true);
                        ondemand_price_for_2_persons_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_for_2_persons_error.setErrorEnabled(false);
                        ondemand_price_for_2_persons_error.setError(null);
                    }
                }
            }
        });

        ondemand_price_for_3_persons.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_for_3_persons.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }
                    if(editvalue.equals("")){
                        ondemand_price_for_3_persons_error.setErrorEnabled(true);
                        ondemand_price_for_3_persons_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_for_3_persons_error.setErrorEnabled(false);
                        ondemand_price_for_3_persons_error.setError(null);
                    }
                }
            }
        });

        ondemand_price_for_4_persons.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_for_4_persons.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_for_4_persons_error.setErrorEnabled(true);
                        ondemand_price_for_4_persons_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_for_4_persons_error.setErrorEnabled(false);
                        ondemand_price_for_4_persons_error.setError(null);
                    }
                }
            }
        });

        ondemand_price_for_5_persons.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_for_5_persons.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_for_5_persons_error.setErrorEnabled(true);
                        ondemand_price_for_5_persons_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_for_5_persons_error.setErrorEnabled(false);
                        ondemand_price_for_5_persons_error.setError(null);
                    }
                }
            }
        });

        Ondemand_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOndemand();
            }
        });

        ondemand_price_for_6_persons.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_for_6_persons.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_for_6_persons_error.setErrorEnabled(true);
                        ondemand_price_for_6_persons_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_for_6_persons_error.setErrorEnabled(false);
                        ondemand_price_for_6_persons_error.setError(null);
                    }
                }
            }
        });

        ondemand_price_sunday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_sunday.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_sunday_error.setErrorEnabled(true);
                        ondemand_price_sunday_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_sunday_error.setErrorEnabled(false);
                        ondemand_price_sunday_error.setError(null);
                    }
                }
            }
        });

        ondemand_price_monday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_monday.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_monday_error.setErrorEnabled(true);
                        ondemand_price_monday_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_monday_error.setErrorEnabled(false);
                        ondemand_price_monday_error.setError(null);
                    }
                }
            }
        });
        ondemand_price_tuesday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_tuesday.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_tuesday_error.setErrorEnabled(true);
                        ondemand_price_tuesday_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_tuesday_error.setErrorEnabled(false);
                        ondemand_price_tuesday_error.setError(null);
                    }
                }
            }
        });
        ondemand_price_wednesday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_wednesday.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_wednesday_error.setErrorEnabled(true);
                        ondemand_price_wednesday_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_wednesday_error.setErrorEnabled(false);
                        ondemand_price_wednesday_error.setError(null);
                    }
                }
            }
        });
        ondemand_price_thursday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_thursday.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_thursday_error.setErrorEnabled(true);
                        ondemand_price_thursday_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_thursday_error.setErrorEnabled(false);
                        ondemand_price_thursday_error.setError(null);
                    }
                }
            }
        });
        ondemand_price_friday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_friday.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_friday_error.setErrorEnabled(true);
                        ondemand_price_friday_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_friday_error.setErrorEnabled(false);
                        ondemand_price_friday_error.setError(null);
                    }
                }
            }
        });

        ondemand_price_saturday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_price_saturday.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_price_saturday_error.setErrorEnabled(true);
                        ondemand_price_saturday_error.setError("Indiquez Votre Prix.");
                    }else {
                        ondemand_price_saturday_error.setErrorEnabled(false);
                        ondemand_price_saturday_error.setError(null);
                    }
                }
            }
        });

        ondemand_postalcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = ondemand_postalcode.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }

                    if(editvalue.equals("")){
                        ondemand_postalcode_error.setErrorEnabled(true);
                        ondemand_postalcode_error.setError("Indiquez Votre Postal code.");
                    }else {
                        ondemand_postalcode_error.setErrorEnabled(false);
                        ondemand_postalcode_error.setError(null);
                    }
                }
            }
        });

//        Ondemand_min_person_error.setErrorEnabled(true);
//        Ondemand_min_person_error.setError("ddd");

        CC_Transport = (EditText)rootView.findViewById(R.id.cc_transport);
//        GoBack = (ImageView)rootView.findViewById(R.id.back);
//        Button Page_Cancel = (Button)rootView.findViewById(R.id.page_cancel) ;
//        Page_Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//
//            }
//        });
//
//        GoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        CC_Transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Rect displayRectangle = new Rect();
                    Window window = getActivity().getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.DialogTheme);
                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dailog_transport, viewGroup, false);
                    dialogView.setMinimumWidth((int)(displayRectangle.width()* 1f));
//            dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();
                    WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
                    wmlp.gravity = Gravity.TOP;
                    Transportcheck_1 =(CheckBox)dialogView.findViewById(R.id.transportcheck_1);
                    Transportcheck_2 =(CheckBox)dialogView.findViewById(R.id.transportcheck_2);
                    Transportcheck_3 =(CheckBox)dialogView.findViewById(R.id.transportcheck_3);
                    Transportcheck_4 =(CheckBox)dialogView.findViewById(R.id.transportcheck_4);

                    Transport_cancel =(Button) dialogView.findViewById(R.id.transport_cancel);
                    Transport_ok =(Button) dialogView.findViewById(R.id.transport_ok);
                    Transport_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            transportHolder.clear();
                            if(Transportcheck_1.isChecked()){
                                transportHolder.add(Transportcheck_1.getText().toString());
                            }
                            if(Transportcheck_2.isChecked()){
                                transportHolder.add(Transportcheck_2.getText().toString());
                            }
                            if(Transportcheck_3.isChecked()){
                                transportHolder.add(Transportcheck_3.getText().toString());
                            }
                            if(Transportcheck_4.isChecked()){
                                transportHolder.add(Transportcheck_4.getText().toString());
                            }
                            String s = TextUtils.join(",", transportHolder);
                            CC_Transport.setText(s);
                            alertDialog.dismiss();
                        }
                    });
                    Transport_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        preview();
        getOndemand();

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
//    private void validateEditText(Editable s) {
//        if (!TextUtils.isEmpty(s)) {
//
////            layoutEdtPhone.setError(null);
//        }
//        else{
////            layoutEdtPhone.setError(getString(R.string.ui_no_password_toast));
//        }
//    }

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
 private void getOndemand(){
     ((RealMainPageActivity)getContext()).showprocess();
     apiInterface.getcousecollectivedemanad(coachid_).enqueue(new Callback<GetCoachCollectiveOnDemandResponseData>() {
         @Override
         public void onResponse(@NonNull Call<GetCoachCollectiveOnDemandResponseData> call, @NonNull Response<GetCoachCollectiveOnDemandResponseData> response) {
             assert response.body() != null;
             if(response.body().getIsSuccess().equals("true")) {
                 if (!response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().isEmpty()){
                     if (response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0) != null) {
                         System.out.println(" ondemandCourseModel---> " + new Gson().toJson(response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0)) );
                         OndemandCourseModel onDeamndModel = response.body().getGetIndiCoachDetailsModel().getOndemandCourseModels().get(0);
                         Ondemand_max_person.setText(onDeamndModel.getMax_People());
                         Ondemand_min_person.setText(onDeamndModel.getMin_People());
                         ondemand_location.setText(onDeamndModel.getLocation());
                         ondemand_description.setText(onDeamndModel.getDescription());
                         ondemand_price_for_2_persons.setText(onDeamndModel.getPrice_2pl_1hr());
                         ondemand_price_for_3_persons.setText(onDeamndModel.getPrice_3pl_1hr());
                         ondemand_price_for_4_persons.setText(onDeamndModel.getPrice_4pl_1hr());
                         ondemand_price_for_5_persons.setText(onDeamndModel.getPrice_5pl_1hr());
                         ondemand_price_for_6_persons.setText(onDeamndModel.getPrice_6pl_1hr());
                         ondemand_price_sunday.setText(onDeamndModel.getPrice_Sun());
                         ondemand_price_monday.setText(onDeamndModel.getPrice_Mon());
                         ondemand_price_tuesday.setText(onDeamndModel.getPrice_Tue());
                         ondemand_price_wednesday.setText(onDeamndModel.getPrice_Wed());
                         ondemand_price_thursday.setText(onDeamndModel.getPrice_Thr());
                         ondemand_price_friday.setText(onDeamndModel.getPrice_Fri());
                         ondemand_price_saturday.setText(onDeamndModel.getPrice_Sat());
                         ondemand_postalcode.setText(onDeamndModel.getPostalcode());
                         if (onDeamndModel.getMode_of_transport() != null) {
                             String ass = onDeamndModel.getMode_of_transport();
                             String[] stringArrayList = ass.split(",");
                             for (String s : stringArrayList) {
                                 switch (s) {
                                     case "Car":
                                         Ondemand_Car.setChecked(true);
                                         break;
                                     case "Vélo":
                                         Ondemand_Bike.setChecked(true);
                                         break;
                                     case "Train":
                                         Ondemand_Train.setChecked(true);
                                         break;
                                     case "Bus":
                                         Ondemand_Bus.setChecked(true);
                                         break;
                                 }
                             }
                         }
                         if (onDeamndModel.getPlan() != null) {
                             if (onDeamndModel.getPlan().equals("Commission")) {
                                 Ondemand_Commission.setChecked(true);
                             } else if (onDeamndModel.getPlan().equals("Abonnement")) {
                                 Ondemand_Abonnement.setChecked(true);
                             }

                         }
                         preview();
                         menumain.findItem(Menus.EDIT).setVisible(true);
                          ((RealMainPageActivity)getContext()).dismissprocess();
                     }else {
                          ((RealMainPageActivity)getContext()).dismissprocess();
                     }
             }else {
                      ((RealMainPageActivity)getContext()).dismissprocess();
                     preview();
//                     Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                 }
             }else {
                  ((RealMainPageActivity)getContext()).dismissprocess();
                 preview();
                 Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
             }
         }
         @Override
         public void onFailure(Call<GetCoachCollectiveOnDemandResponseData> call, Throwable t) {
              ((RealMainPageActivity)getContext()).dismissprocess();
             System.out.println("---> "+ call +" "+ t);
         }
     });
 }

    private void setOndemand(){
      OndemandCourseModel ondemandCourseModel = new OndemandCourseModel("","","","","","","0","","0","","0"
              ,"","","0","",coachid_,"0","0","0","0","0","0","0","");
     if(( !Ondemand_min_person_error.isErrorEnabled() &&
             !Ondemand_max_person_error.isErrorEnabled() &&
        !ondemand_location_error.isErrorEnabled() &&
        !ondemand_description_error.isErrorEnabled() &&
        !ondemand_price_for_2_persons_error.isErrorEnabled() &&
        !ondemand_price_for_3_persons_error.isErrorEnabled() &&
        !ondemand_price_for_4_persons_error.isErrorEnabled() &&
        !ondemand_price_for_5_persons_error.isErrorEnabled() &&
        !ondemand_price_for_6_persons_error.isErrorEnabled() &&
                     !ondemand_price_sunday_error.isErrorEnabled() &&
                     !ondemand_price_monday_error.isErrorEnabled() &&
                     !ondemand_price_tuesday_error.isErrorEnabled() &&
                     !ondemand_price_wednesday_error.isErrorEnabled() &&
                     !ondemand_price_thursday_error.isErrorEnabled() &&
                     !ondemand_price_friday_error.isErrorEnabled() &&
                     !ondemand_price_saturday_error.isErrorEnabled())
     && ( !Ondemand_min_person.getText().equals("") &&
             !Ondemand_max_person.getText().equals("") &&
             !ondemand_location.getText().equals("") &&
             !ondemand_description.getText().equals("") &&
             !ondemand_price_for_2_persons.getText().equals("") &&
             !ondemand_price_for_3_persons.getText().equals("") &&
             !ondemand_price_for_4_persons.getText().equals("") &&
             !ondemand_price_for_5_persons.getText().equals("") &&
             !ondemand_price_for_6_persons.getText().equals("") &&
             !ondemand_price_sunday.getText().equals("") &&
             !ondemand_price_monday.getText().equals("") &&
             !ondemand_price_tuesday.getText().equals("") &&
             !ondemand_price_wednesday.getText().equals("") &&
             !ondemand_price_thursday.getText().equals("") &&
             !ondemand_price_friday.getText().equals("") &&
             !ondemand_price_saturday.getText().equals("")) ){

         if( Ondemand_Car.isChecked() ||
         Ondemand_Bike.isChecked() ||
         Ondemand_Train.isChecked() ||
         Ondemand_Bus.isChecked() ){
    if(Ondemand_Commission.isChecked() || Ondemand_Abonnement.isChecked()){

             mod_of_transport.clear();
             if(Ondemand_Car.isChecked()){
                 mod_of_transport.add("Car");
             }
             if(Ondemand_Bike.isChecked()){
                 mod_of_transport.add("Vélo");
             }
             if(Ondemand_Train.isChecked()){
                 mod_of_transport.add("Train");
             }
             if(Ondemand_Bus.isChecked()){
                 mod_of_transport.add("Bus");
             }
              String plan = "";
             if(Ondemand_Commission.isChecked()){
                 plan= Ondemand_Commission.getText().toString();

             }

         if(Ondemand_Abonnement.isChecked()){
             plan= Ondemand_Abonnement.getText().toString();
         }

             String transport = TextUtils.join(",",mod_of_transport);

             ondemandCourseModel.setMax_People(Ondemand_max_person.getText().toString());
              ondemandCourseModel.setMin_People(Ondemand_min_person.getText().toString());
             ondemandCourseModel.setLocation(ondemand_location.getText().toString());
             ondemandCourseModel.setDescription(ondemand_description.getText().toString());
             ondemandCourseModel.setPrice_2pl_1hr(ondemand_price_for_2_persons.getText().toString());
             ondemandCourseModel.setPrice_3pl_1hr(ondemand_price_for_3_persons.getText().toString());
             ondemandCourseModel.setPrice_4pl_1hr(ondemand_price_for_4_persons.getText().toString());
             ondemandCourseModel.setPrice_5pl_1hr(ondemand_price_for_5_persons.getText().toString());
             ondemandCourseModel.setPrice_6pl_1hr(ondemand_price_for_6_persons.getText().toString());

             ondemandCourseModel.setPrice_Sun(ondemand_price_sunday.getText().toString());
             ondemandCourseModel.setPrice_Mon(ondemand_price_monday.getText().toString());
             ondemandCourseModel.setPrice_Tue(ondemand_price_tuesday.getText().toString());
             ondemandCourseModel.setPrice_Wed(ondemand_price_wednesday.getText().toString());
             ondemandCourseModel.setPrice_Thr(ondemand_price_thursday.getText().toString());
             ondemandCourseModel.setPrice_Fri(ondemand_price_friday.getText().toString());
             ondemandCourseModel.setPrice_Sat(ondemand_price_saturday.getText().toString());

             ondemandCourseModel.setMode_of_transport(transport);
             ondemandCourseModel.setPlan(plan);
             ondemandCourseModel.setPostalcode(ondemand_postalcode.getText().toString());


                System.out.println(" ondemandCourseModel---> " + new Gson().toJson(ondemandCourseModel));

             apiInterface.setcousecollectivedemanad(ondemandCourseModel).enqueue(new Callback<GetCoachCollectiveOnDemandResponseData>() {
                 @Override
                 public void onResponse(@NonNull Call<GetCoachCollectiveOnDemandResponseData> call, @NonNull Response<GetCoachCollectiveOnDemandResponseData> response) {
                     assert response.body() != null;
                     if(response.body().getIsSuccess().equals("true")){
                         getOndemand();
                         Toast.makeText(getActivity(),"Cours collectif à la demande mis à jour avec succès",Toast.LENGTH_LONG).show();
                     }else {
                         Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                     }
                 }
                 @Override
                 public void onFailure(Call<GetCoachCollectiveOnDemandResponseData> call, Throwable t) {
                     System.out.println("---> "+ call +" "+ t);
                 }
             });
    }else {
        Toast.makeText(getActivity(),"Veuillez sélectionner au moins un plan",Toast.LENGTH_LONG).show();
    }
         }else {
             Toast.makeText(getActivity(),"Veuillez sélectionner au moins un transport",Toast.LENGTH_LONG).show();
         }

     }else {
         Toast.makeText(getActivity(),"Veuillez saisir toutes les données",Toast.LENGTH_LONG).show();
     }


    }

 public void preview(){

     Ondemand_min_person.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     Ondemand_max_person.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_description .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_for_2_persons.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_for_3_persons .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_for_4_persons.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_for_5_persons .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_for_6_persons .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_postalcode .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_price_saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
     ondemand_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));

     Ondemand_Car.setEnabled(false);
     Ondemand_Bike .setEnabled(false);
     Ondemand_Train.setEnabled(false);
     Ondemand_Bus .setEnabled(false);
     ondemand_price_sunday.setEnabled(false);
     ondemand_price_monday.setEnabled(false);
     ondemand_price_tuesday.setEnabled(false);
     ondemand_price_wednesday.setEnabled(false);
     ondemand_price_thursday.setEnabled(false);
     ondemand_price_friday.setEnabled(false);
     ondemand_price_saturday.setEnabled(false);

     Ondemand_min_person.setEnabled(false);
     Ondemand_max_person.setEnabled(false);
     ondemand_location.setEnabled(false);
     ondemand_description .setEnabled(false);
     ondemand_price_for_2_persons.setEnabled(false);
     ondemand_price_for_3_persons .setEnabled(false);
     ondemand_price_for_4_persons.setEnabled(false);
     ondemand_price_for_5_persons .setEnabled(false);
     ondemand_price_for_6_persons .setEnabled(false);
     Ondemand_Abonnement.setEnabled(false);
     Ondemand_Commission.setEnabled(false);
     ondemand_postalcode.setEnabled(false);
     save_button_hide_show.setVisibility(View.GONE);

    
 }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void EditView(){

        Ondemand_Car.setEnabled(true);
        Ondemand_Bike .setEnabled(true);
        Ondemand_Train.setEnabled(true);
        Ondemand_Bus .setEnabled(true);
        Ondemand_Abonnement.setEnabled(true);
        Ondemand_Commission.setEnabled(true);
        save_button_hide_show.setVisibility(View.VISIBLE);
        Ondemand_min_person.setEnabled(true);
        Ondemand_max_person.setEnabled(true);
        ondemand_location.setEnabled(true);
        ondemand_description .setEnabled(true);
        ondemand_price_for_2_persons.setEnabled(true);
        ondemand_price_for_3_persons .setEnabled(true);
        ondemand_price_for_4_persons.setEnabled(true);
        ondemand_price_for_5_persons .setEnabled(true);
        ondemand_price_for_6_persons .setEnabled(true);
        ondemand_postalcode.setEnabled(true);
        ondemand_price_sunday.setEnabled(true);
        ondemand_price_monday.setEnabled(true);
        ondemand_price_tuesday.setEnabled(true);
        ondemand_price_wednesday.setEnabled(true);
        ondemand_price_thursday.setEnabled(true);
        ondemand_price_friday.setEnabled(true);
        ondemand_price_saturday.setEnabled(true);

        Ondemand_min_person.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Ondemand_max_person.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_description .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_2_persons.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_3_persons .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_4_persons.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_5_persons .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_6_persons .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_postalcode .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Ondemand_min_person.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Ondemand_max_person.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_location.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_description .setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_2_persons.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_3_persons .setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_4_persons.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_5_persons .setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_for_6_persons .setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_thursday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        ondemand_price_saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));


    }

}