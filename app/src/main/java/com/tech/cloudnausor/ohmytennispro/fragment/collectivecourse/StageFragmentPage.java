package com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.dto.StageDTO;
import com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccFormOneFragment;
import com.tech.cloudnausor.ohmytennispro.model.GetIndiCoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.IndiCourseData;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.ImagePickerActivity;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccFormOneFragment.REQUEST_IMAGE;

public class StageFragmentPage extends Fragment {


    private boolean mSearchCheck;
    private TextView mTxtDownload;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;
    ApiInterface apiInterface;
    View rootView;
    String imageString ="";
    Bitmap decodedImage;
    Menu menumain;
    SingleTonProcess singleTonProcess;


    private static final String ARG_PARAM1 = "EVENT_NAME";
    private static final String ARG_PARAM2 = "EVENT_ID";
    private String mParam1;
    private String mParam2;

    TextInputLayout stage_name_of_event_error,stage_from_error,stage_to_error,stage_price_error,stage_image_error
            ,stage_description_error,stage_location_error,stage_postalcode_error;
    TextInputEditText stage_name_of_event,stage_from,stage_to,stage_price,stage_image
            ,stage_description,stage_location,stage_postalcode;
    CheckBox Stage_Car,Stage_Bike,Stage_Train,Stage_Bus;
    ArrayList<String> checktext = new ArrayList<String>();
    RadioButton Stage_Commission,Stage_Abonnement;
    ArrayList<StageDTO.StageCourse> stageCourseArrayList = new ArrayList<StageDTO.StageCourse>();

    ImageView Stage_image_preview;

    LinearLayout save_button_hide_show;

    Button stage_image_upload;
    Calendar myCalendarFrom = Calendar.getInstance() ,myCalendarTo = Calendar.getInstance();

    StageDTO.StageCourse setStageCourse = new StageDTO.StageCourse();

    private static final String TAG = RealMainPageActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    ImageView profile;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    Button animation_save,stage_cancel;

    public StageFragmentPage newInstance(String text,String eventName,String eventID){
        StageFragmentPage mFragment = new StageFragmentPage();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mBundle.putString("EVENT_NAME", eventName);
        mBundle.putString("EVENT_ID", eventID);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        rootView = inflater.inflate(R.layout.fragment_stage_fragment_page, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        askPermissions();
        initView();
        singleTonProcess = singleTonProcess.getInstance();
        return rootView;
    }

    private void previewTint(){
        save_button_hide_show.setVisibility(View.GONE);
        stage_name_of_event.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_from.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_to.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_price.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Stage_Car.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Stage_Bike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Stage_Train.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Stage_Bus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Stage_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Stage_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_image_upload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));

        stage_name_of_event.setEnabled(false);
        stage_from.setEnabled(false);
        stage_to.setEnabled(false);
        stage_price.setEnabled(false);
        stage_image.setEnabled(false);
        stage_description.setEnabled(false);
        stage_location.setEnabled(false);
        stage_postalcode.setEnabled(false);
        Stage_Car.setEnabled(false);
        Stage_Bike.setEnabled(false);
        Stage_Train.setEnabled(false);
        Stage_Bus.setEnabled(false);
        Stage_Commission.setEnabled(false);
        Stage_Abonnement.setEnabled(false);
        stage_image_upload.setEnabled(false);

    }

    public void editviewTint(){

        save_button_hide_show.setVisibility(View.VISIBLE);
        menumain.findItem(Menus.EDIT).setVisible(false);

        stage_name_of_event.setEnabled(true);
        stage_from.setEnabled(true);
        stage_to.setEnabled(true);
        stage_price.setEnabled(true);
        stage_image.setEnabled(false);
        stage_description.setEnabled(true);
        stage_location.setEnabled(true);
        stage_postalcode.setEnabled(true);
        Stage_Car.setEnabled(true);
        Stage_Bike.setEnabled(true);
        Stage_Train.setEnabled(true);
        Stage_Bus.setEnabled(true);
        Stage_Commission.setEnabled(true);
        Stage_Abonnement.setEnabled(true);
        stage_image_upload.setEnabled(true);

        stage_name_of_event.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        stage_from.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        stage_to.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        stage_price.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        stage_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        stage_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        stage_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        stage_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Stage_Car.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Stage_Bike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Stage_Train.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Stage_Bus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Stage_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Stage_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        stage_image_upload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

    }

    private void initView(){

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
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
        stage_name_of_event_error =(TextInputLayout)rootView.findViewById(R.id.stage_name_of_event_error);
        stage_from_error=(TextInputLayout)rootView.findViewById(R.id.stage_from_error);
        stage_to_error=(TextInputLayout)rootView.findViewById(R.id.stage_to_error);
        stage_price_error=(TextInputLayout)rootView.findViewById(R.id.stage_price_error);
        stage_image_error=(TextInputLayout)rootView.findViewById(R.id.stage_image_error);
        stage_description_error=(TextInputLayout)rootView.findViewById(R.id.stage_description_error);
        stage_location_error=(TextInputLayout)rootView.findViewById(R.id.stage_location_error);
        stage_postalcode_error=(TextInputLayout)rootView.findViewById(R.id.stage_postalcode_error);

        stage_name_of_event =(TextInputEditText)rootView.findViewById(R.id.stage_name_of_event);
        stage_name_of_event.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_name_of_event.getText().toString().trim().length()>0){
                    stage_name_of_event_error.setErrorEnabled(false);

                }else{
                    stage_name_of_event_error.setErrorEnabled(true);
                    stage_name_of_event_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_name_of_event.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_name_of_event.getText().toString().trim().length()>0){
                    stage_name_of_event_error.setErrorEnabled(false);

                }else{
                    stage_name_of_event_error.setErrorEnabled(true);
                    stage_name_of_event_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_from=(TextInputEditText)rootView.findViewById(R.id.stage_from);
        stage_from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_from.getText().toString().trim().length()>0){
                    stage_from_error.setErrorEnabled(false);

                }else{
                    stage_from_error.setErrorEnabled(true);
                    stage_from_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_from.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_from.getText().toString().trim().length()>0){
                    stage_from_error.setErrorEnabled(false);

                }else{
                    stage_from_error.setErrorEnabled(true);
                    stage_from_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_to=(TextInputEditText)rootView.findViewById(R.id.stage_to);
        stage_to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_to.getText().toString().trim().length()>0){
                    stage_to_error.setErrorEnabled(false);

                }else{
                    stage_to_error.setErrorEnabled(true);
                    stage_to_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_to.getText().toString().trim().length()>0){
                    stage_to_error.setErrorEnabled(false);

                }else{
                    stage_to_error.setErrorEnabled(true);
                    stage_to_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_price=(TextInputEditText)rootView.findViewById(R.id.stage_price);
        stage_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_price.getText().toString().trim().length()>0){
                    stage_price_error.setErrorEnabled(false);

                }else{
                    stage_price_error.setErrorEnabled(true);
                    stage_price_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_price.getText().toString().trim().length()>0){
                    stage_price_error.setErrorEnabled(false);

                }else{
                    stage_price_error.setErrorEnabled(true);
                    stage_price_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_image=(TextInputEditText)rootView.findViewById(R.id.stage_image);
        stage_image.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_image.getText().toString().trim().length()>0){
                    stage_image_error.setErrorEnabled(false);

                }else{
                    stage_image_error.setErrorEnabled(true);
                    stage_image_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_image.getText().toString().trim().length()>0){
                    stage_image_error.setErrorEnabled(false);

                }else{
                    stage_image_error.setErrorEnabled(true);
                    stage_image_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        stage_image.setEnabled(false);
        stage_image.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.button_cancel)));
        stage_description=(TextInputEditText)rootView.findViewById(R.id.stage_description);
        stage_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_description.getText().toString().trim().length()>0){
                    stage_description_error.setErrorEnabled(false);

                }else{
                    stage_description_error.setErrorEnabled(true);
                    stage_description_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_description.getText().toString().trim().length()>0){
                    stage_description_error.setErrorEnabled(false);

                }else{
                    stage_description_error.setErrorEnabled(true);
                    stage_description_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_location=(TextInputEditText)rootView.findViewById(R.id.stage_location);
        stage_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_location.getText().toString().trim().length()>0){
                    stage_location_error.setErrorEnabled(false);

                }else{
                    stage_location_error.setErrorEnabled(true);
                    stage_location_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_location.getText().toString().trim().length()>0){
                    stage_location_error.setErrorEnabled(false);

                }else{
                    stage_location_error.setErrorEnabled(true);
                    stage_location_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_postalcode=(TextInputEditText)rootView.findViewById(R.id.stage_postalcode);
        stage_postalcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(stage_postalcode.getText().toString().trim().length()>0){
                    stage_postalcode_error.setErrorEnabled(false);

                }else{
                    stage_postalcode_error.setErrorEnabled(true);
                    stage_postalcode_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        stage_postalcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(stage_postalcode.getText().toString().trim().length()>0){
                    stage_postalcode_error.setErrorEnabled(false);

                }else{
                    stage_postalcode_error.setErrorEnabled(true);
                    stage_postalcode_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        Stage_Car = (CheckBox)rootView.findViewById(R.id.stage_car);
        Stage_Bike = (CheckBox)rootView.findViewById(R.id.stage_bike);
        Stage_Bus = (CheckBox)rootView.findViewById(R.id.stage_bus);
        Stage_Train = (CheckBox)rootView.findViewById(R.id.stage_train);

        Stage_Commission  = (RadioButton) rootView.findViewById(R.id.stage_commission);
        Stage_Abonnement  = (RadioButton) rootView.findViewById(R.id.stage_abonnement);

        Stage_image_preview = (ImageView) rootView.findViewById(R.id.stage_image_preview) ;

        stage_image_upload = (Button) rootView.findViewById(R.id.stage_image_upload) ;
        animation_save = (Button)rootView.findViewById(R.id.stage_save);
        stage_cancel = (Button)rootView.findViewById(R.id.stage_cancel);

        stage_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RealMainPageActivity)getContext()).setLastPosition(Constant.MENU_STAGE);
                ((RealMainPageActivity)getContext()).setFragmentList(Constant.MENU_STAGE);
            }
        });

        save_button_hide_show =(LinearLayout) rootView.findViewById(R.id.save_button_hide_show);

        final DatePickerDialog.OnDateSetListener dateForm = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarFrom.set(Calendar.YEAR, year);
                myCalendarFrom.set(Calendar.MONTH, monthOfYear);
                myCalendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFrom();
            }
        };

        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTo();
            }
        };

        stage_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Objects.requireNonNull(getContext()), dateForm, myCalendarFrom
                        .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                        myCalendarFrom.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        stage_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(Objects.requireNonNull(getContext()), dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        stage_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });

        if(mParam1.equals("update")){
            getStage();
        }

        animation_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mParam1.equals("update")){
                    updateStage();
                }else {
                    insertStage();
                }
            }
        });

//        getStage();

    }

    private void updateFrom() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        stage_from.setText(sdf.format(myCalendarFrom.getTime()));
    }

    private void updateTo() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        stage_to.setText(sdf.format(myCalendarTo.getTime()));
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
        if(mParam1.equals("update")){
            menu.findItem(Menus.EDIT).setVisible(true);
        }else {
            menu.findItem(Menus.EDIT).setVisible(false);
        }
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
                editviewTint();
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

    public void getStage()
    {
        ((RealMainPageActivity)getContext()).showprocess();
        apiInterface.getstage(mParam2,coachid_).enqueue(new Callback<StageDTO>() {
            @Override
            public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                  stageCourseArrayList = response.body().getData().getCourse();
                    if(stageCourseArrayList.size() != 0){

                        StageDTO.StageCourse stageCourse = stageCourseArrayList.get(0);
                        stage_name_of_event.setText(stageCourse.getEventname());

                        String utcDateStr = stageCourse.getFrom_date();
                        SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Date date = null;
                        try {
                            date = sdf.parse(utcDateStr);
                           //                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
//                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();

//                            date = date +1 ;
                            stage_from.setText( new  SimpleDateFormat("yyyy-MM-dd").format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String utcDateStrto = stageCourse.getTo_date();
                        SimpleDateFormat sdfto = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Date dateto = null;
                        try {
                            dateto = sdfto.parse(utcDateStrto);
                            //                            Calendar c = Calendar.getInstance();
//                            c.setTime(dateto);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();

//                            Calendar c = Calendar.getInstance();
//                            c.setTime(dateto);
//                            c.add(Calendar.DATE, 1);
//                            dateto = c.getTime();

                            stage_to.setText( new  SimpleDateFormat("yyyy-MM-dd").format(dateto));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        stage_from.setText(stageCourse.getFrom_date());
//                        stage_to.setText(stageCourse.getTo_date());
                        stage_price.setText(stageCourse.getPrice());
                        stage_image.setText("/"+stageCourse.getFilename());
                        stage_description.setText(stageCourse.getDescription());
                        stage_location.setText(stageCourse.getLocation());
                        stage_postalcode.setText(stageCourse.getPostalcode());

                        if (stageCourse.getPhoto() != null && stageCourse.getPhoto() != "" && !stageCourse.getPhoto().matches("http") && !stageCourse.getPhoto().contains("WWW.") && !stageCourse.getPhoto().contains("https") &&
                                !stageCourse.getPhoto().contains(".jpeg") && !stageCourse.getPhoto().contains(".png") && !stageCourse.getPhoto().contains("undefined")) {
                            imageString = stageCourse.getPhoto();
                            if(imageString.contains("data:image/jpeg;base64,")){
                                imageString = imageString.replace("data:image/jpeg;base64,","");
                            }
                            if(imageString.contains("data:image/png;base64,")){
                                imageString = imageString.replace("data:image/png;base64,","");
                            }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                            decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                        drawable = new BitmapDrawable(Objects.requireNonNull(getActivity()).getResources(), decodedImage);
//                        Drawable drawable = (Drawable)new BitmapDrawable(getActivity().getResources(),decodedImage);
                            Stage_image_preview.setImageBitmap(decodedImage);
                            Stage_image_preview.setVisibility(View.VISIBLE);
                            //                        Picasso.get().load().into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                            }
//
//                            @Override
//                            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                            }
//                        });

//                        Picasso.get().load(decodedImage).into(profile);
//                        profile.setImageBitmap(decodedImage);}
                        }else {
                            Stage_image_preview.setVisibility(View.GONE);
                        }


                        if (stageCourse.getMode_of_transport() != null) {
                            String ass = stageCourse.getMode_of_transport();
                            String[] stringArrayList = ass.split(",");
                            for (String s : stringArrayList) {
                                switch (s) {
                                    case "Car":
                                        Stage_Car.setChecked(true);
                                        break;
                                    case "Vélo":
                                        Stage_Bike.setChecked(true);
                                        break;
                                    case "Train":
                                        Stage_Train.setChecked(true);
                                        break;
                                    case "Bus":
                                        Stage_Bus.setChecked(true);
                                        break;
                                }
                            }
                        }
                        if (stageCourse.getPlan() != null) {
                            if (stageCourse.getPlan().equals("Commission")) {
                                Stage_Commission.setChecked(true);
                            } else if (stageCourse.getPlan().equals("Abonnement")) {
                                Stage_Abonnement.setChecked(true);
                            }
                        }

                        previewTint();
                        menumain.findItem(Menus.EDIT).setVisible(true);
                         ((RealMainPageActivity)getContext()).dismissprocess();
                    }else {
                         ((RealMainPageActivity)getContext()).dismissprocess();
                        previewTint();
                        menumain.findItem(Menus.EDIT).setVisible(true);
                    }

                }else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                     if(!response.body().getMessage().equals("")){
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();}
                }
            }
            @Override
            public void onFailure(Call<StageDTO> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void askPermissions() {

        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

//    private void initRetrofitClient() {
//        OkHttpClient client = new OkHttpClient.Builder().build();
//
//        apiService = new Retrofit.Builder().baseUrl("http://192.168.88.65:3000").client(client).build().create(ApiService.class);
//    }


    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Picasso.get().load(url).fit().into(Stage_image_preview);
        Stage_image_preview.setVisibility(View.VISIBLE);
//        GlideApp.with(this).load(url)
//                .into(profile);
        Stage_image_preview.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Stage_image_preview);
//        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
//                .into(profile);
        Stage_image_preview.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
    }

    private void onProfileImageClick() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                StageFragmentPage.this.openSettings();
            }
        });

        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
//        outState.putParcelable("profileimage", (Parcelable) drawable);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            picUri = savedInstanceState.getParcelable("pic_uri");
//            Drawable drawable = savedInstanceState.getParcelable("profileimage");
//            profile.setImageDrawable(drawable);
            //Restore the fragment's state here
        }
    }

    //    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        // get the file url
//        picUri = savedInstanceState.getParcelable("pic_uri");
//    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (Objects.requireNonNull(getActivity()).checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Uri uri = data.getParcelableExtra("path");

                try {
                    InputStream in = getContext().getContentResolver().openInputStream(uri);
                    String path = uri.getPath();
                    String filename = path.substring(path.lastIndexOf("/")+1);
                    String file;
                    if (filename.indexOf(".") > 0) {
                        file = filename.substring(0, filename.lastIndexOf("."));
                        stage_image.setText("/"+file);
                    } else {
                        file =  filename;
                        stage_image.setText("/"+file);
                    }

                    byte[] bytes=getBytes(in);
                    Log.d("data", "onActivityResult: bytes size="+bytes.length);

                    Log.d("data", "onActivityResult: Base64string="+ Base64.encodeToString(bytes,Base64.DEFAULT));
                    imageString = Base64.encodeToString(bytes,Base64.DEFAULT);

//                    Bitmap bitmap =BitmapFactory.decodeFile(data.getData().toString());
                    // You can update this bitmap to your server
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    // loading profile image from local cache
                    loadProfile(uri.toString());
//                     InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
//
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream .toByteArray();
//                    imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    Bitmap bitmap64 = BitmapFactory.decodeStream(imageStream);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    byte[] imageBytes = baos.toByteArray();
//                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertStage(){
        ((RealMainPageActivity)getContext()).showprocess();

//        if(fieldValidation()) {

//        stage_name_of_event.setText(stageCourse.getEventname());
//        stage_from.setText(stageCourse.getFrom_date());
//        stage_to.setText(stageCourse.getTo_date());
//        stage_price.setText(stageCourse.getPrice());
//        stage_image.setText("/"+stageCourse.getFilename());
//        stage_description.setText(stageCourse.getDescription());
//        stage_location.setText(stageCourse.getLocation());
//        stage_postalcode.setText(stageCourse.getPostalcode());

        setStageCourse.setEventname(stage_name_of_event.getText().toString());
        setStageCourse.setDescription(stage_description.getText().toString());
        setStageCourse.setLocation(stage_location.getText().toString());
        setStageCourse.setPostalcode(stage_postalcode.getText().toString());
        setStageCourse.setFilename(stage_image.getText().toString().replace("/",""));
        setStageCourse.setPhoto(imageString);

         if(Stage_Abonnement.isChecked()){
           setStageCourse.setPlan("Abonnement");
          }else if(Stage_Commission.isChecked()){
           setStageCourse.setPlan("Commission");
         }

         if(Stage_Train.isChecked()){
             checktext.add("Train");
         }
         if(Stage_Bus.isChecked()){
             checktext.add("Bus");
         }
         if(Stage_Bike.isChecked()){
             checktext.add("Vélo");
         }
         if(Stage_Car.isChecked()){
             checktext.add("Car");
         }

        String transport = TextUtils.join(",", checktext);

         setStageCourse.setMode_of_transport(transport);
        setStageCourse.setCoach_Id(coachid_);
        setStageCourse.setFrom_date(stage_from.getText().toString());
        setStageCourse.setTo_date(stage_to.getText().toString());
        setStageCourse.setPrice(stage_price.getText().toString());


        System.out.println("setStageCourse--> " + new Gson().toJson(setStageCourse));
        apiInterface.setstagecourseinsert(setStageCourse).enqueue(new Callback<StageDTO>() {
            @Override
            public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if (response.body().getIsSuccess().equals("true")) {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    ((RealMainPageActivity)getContext()).onBackPressed();
//                    ((RealMainPageActivity)getContext()).setLastPosition(Constant.MENU_STAGE);
//                    ((RealMainPageActivity)getContext()).setFragmentList(Constant.MENU_STAGE);
                    if(!response.body().getMessage().equals("")){

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();}
                } else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    if(!response.body().getMessage().equals("")){
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();}
                }
            }

            @Override
            public void onFailure(Call<StageDTO> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> " + call + " " + t);
            }
        });
    }

    public void updateStage(){
((RealMainPageActivity)getContext()).showprocess();
//        if(fieldValidation()) {

        setStageCourse.setEventname(stage_name_of_event.getText().toString());
        setStageCourse.setDescription(stage_description.getText().toString());
        setStageCourse.setLocation(stage_location.getText().toString());
        setStageCourse.setPostalcode(stage_postalcode.getText().toString());
        setStageCourse.setFilename(stage_image.getText().toString().replace("/",""));
        setStageCourse.setPhoto(imageString);
        setStageCourse.setId(mParam2);
        if(Stage_Abonnement.isChecked()){
            setStageCourse.setPlan("Abonnement");
        }else if(Stage_Commission.isChecked()){
            setStageCourse.setPlan("Commission");
        }

        if(Stage_Train.isChecked()){
            checktext.add("Train");
        }
        if(Stage_Bus.isChecked()){
            checktext.add("Bus");
        }
        if(Stage_Bike.isChecked()){
            checktext.add("Vélo");
        }
        if(Stage_Car.isChecked()){
            checktext.add("Car");
        }

        String transport = TextUtils.join(",", checktext);

        setStageCourse.setMode_of_transport(transport);
        setStageCourse.setCoach_Id(coachid_);
        setStageCourse.setFrom_date(stage_from.getText().toString());
        setStageCourse.setTo_date(stage_to.getText().toString());
        setStageCourse.setPrice(stage_price.getText().toString());

        apiInterface.setstagecourseupdate(setStageCourse).enqueue(new Callback<StageDTO>() {
            @Override
            public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if (response.body().getIsSuccess().equals("true")) {
                    ((RealMainPageActivity)getContext()).onBackPressed();
                    if(!response.body().getMessage().equals("")){
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();}
                     ((RealMainPageActivity)getContext()).dismissprocess();

                } else {
                    if(!response.body().getMessage().equals("")){
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();}
                    ((RealMainPageActivity)getContext()).dismissprocess();
                }
            }

            @Override
            public void onFailure(Call<StageDTO> call, Throwable t) {
                System.out.println("---> " + call + " " + t);
                 ((RealMainPageActivity)getContext()).dismissprocess();
            }
        });
    }


}