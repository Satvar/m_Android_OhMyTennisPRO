package com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

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
import com.tech.cloudnausor.ohmytennispro.dto.TournamentDTO;
import com.tech.cloudnausor.ohmytennispro.dto.TournamentDTO;
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

public class TournamentFragment extends Fragment {


    private boolean mSearchCheck;
    private TextView mTxtDownload;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;
    ApiInterface apiInterface;
    String imageString ="";
    Bitmap decodedImage;
    Menu menumain;
    View rootViewTournament;
    SingleTonProcess singleTonProcess;
    String editorvalue= "";

    TournamentDTO.TournamentCourse tournamentCourse = new TournamentDTO.TournamentCourse();
    TournamentDTO.TournamentCourse gettournamentCours = new TournamentDTO.TournamentCourse();

    private static final String TAG = RealMainPageActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    ImageView profile;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    private static final String ARG_PARAM1 = "EVENT_NAME";
    private static final String ARG_PARAM2 = "EVENT_ID";
    private String mParam1;
    private String mParam2;
    Button animation_save,tournament_cancel;



    TextInputLayout tournament_name_of_event_error,tournament_from_error,tournament_to_error,tournament_price_error,tournament_image_error
            ,tournament_description_error,tournament_location_error,tournament_postalcode_error;
    TextInputEditText tournament_name_of_event,tournament_from,tournament_to,tournament_price,tournament_image
            ,tournament_description,tournament_location,tournament_postalcode;
//    CheckBox Tournament_Car,Tournament_Bike,Tournament_Train,Tournament_Bus;
    RadioButton Tournament_Commission,Tournament_Abonnement;

    Button tournament_image_upload ;

    ImageView Tournament_image_preview;

    LinearLayout save_button_hide_show;

    Calendar myCalendarFrom = Calendar.getInstance() ,myCalendarTo = Calendar.getInstance();


    public TournamentFragment newInstance(String text,String eventName,String eventID){
        TournamentFragment mFragment = new TournamentFragment();
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
        rootViewTournament = inflater.inflate(R.layout.activity_tournament, container, false);
        rootViewTournament.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        singleTonProcess = singleTonProcess.getInstance();
        initView();
        askPermissions();
        getTournament();
        return rootViewTournament;
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

        if(mParam1.equals("update")) {
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

    private void previewTint(){

        save_button_hide_show.setVisibility(View.GONE);
        tournament_name_of_event.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        tournament_from.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        tournament_to.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        tournament_price.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        tournament_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        tournament_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        tournament_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        tournament_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        Tournament_Car.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        Tournament_Bike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        Tournament_Train.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        Tournament_Bus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Tournament_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Tournament_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));

        tournament_name_of_event.setEnabled(false);
        tournament_from.setEnabled(false);
        tournament_to.setEnabled(false);
        tournament_price.setEnabled(false);
        tournament_image.setEnabled(false);
        tournament_description.setEnabled(false);
        tournament_location.setEnabled(false);
        tournament_postalcode.setEnabled(false);
//        Tournament_Car.setEnabled(false);
//        Tournament_Bike.setEnabled(false);
//        Tournament_Train.setEnabled(false);
//        Tournament_Bus.setEnabled(false);
        Tournament_Commission.setEnabled(false);
        Tournament_Abonnement.setEnabled(false);

    }

    public void editviewTint(){

        save_button_hide_show.setVisibility(View.VISIBLE);
        menumain.findItem(Menus.EDIT).setVisible(false);

        tournament_name_of_event.setEnabled(true);
        tournament_from.setEnabled(true);
        tournament_to.setEnabled(true);
        tournament_price.setEnabled(true);
        tournament_image.setEnabled(true);
        tournament_description.setEnabled(true);
        tournament_location.setEnabled(true);
        tournament_postalcode.setEnabled(true);
//        Tournament_Car.setEnabled(true);
//        Tournament_Bike.setEnabled(true);
//        Tournament_Train.setEnabled(true);
//        Tournament_Bus.setEnabled(true);
        Tournament_Commission.setEnabled(true);
        Tournament_Abonnement.setEnabled(true);

        tournament_name_of_event.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tournament_from.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tournament_to.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tournament_price.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tournament_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tournament_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tournament_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        tournament_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        Tournament_Car.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        Tournament_Bike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        Tournament_Train.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        Tournament_Bus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Tournament_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Tournament_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
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

        tournament_name_of_event_error =(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_name_of_event_error);
        tournament_from_error=(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_from_error);
        tournament_to_error=(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_to_error);
        tournament_price_error=(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_price_error);
        tournament_image_error=(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_image_error);
        tournament_description_error=(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_description_error);
        tournament_location_error=(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_location_error);
        tournament_postalcode_error=(TextInputLayout)rootViewTournament.findViewById(R.id.tournament_postalcode_error);

        tournament_name_of_event =(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_name_of_event);
        tournament_name_of_event.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(tournament_name_of_event.getText().toString().trim().length()>0){
                    tournament_name_of_event_error.setErrorEnabled(false);

                }else{
                    tournament_name_of_event_error.setErrorEnabled(true);
                    tournament_name_of_event_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_name_of_event.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tournament_name_of_event.getText().toString().trim().length()>0){
                    tournament_name_of_event_error.setErrorEnabled(false);

                }else{
                    tournament_name_of_event_error.setErrorEnabled(true);
                    tournament_name_of_event_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_from=(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_from);
        tournament_from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(tournament_from.getText().toString().trim().length()>0){
                    tournament_from_error.setErrorEnabled(false);

                }else{
                    tournament_from_error.setErrorEnabled(true);
                    tournament_from_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_from.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tournament_from.getText().toString().trim().length()>0){
                    tournament_from_error.setErrorEnabled(false);

                }else{
                    tournament_from_error.setErrorEnabled(true);
                    tournament_from_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        tournament_to=(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_to);
        tournament_to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(tournament_to.getText().toString().trim().length()>0){
                    tournament_to_error.setErrorEnabled(false);

                }else{
                    tournament_to_error.setErrorEnabled(true);
                    tournament_to_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });



        tournament_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tournament_to.getText().toString().trim().length()>0){
                    tournament_to_error.setErrorEnabled(false);

                }else{
                    tournament_to_error.setErrorEnabled(true);
                    tournament_to_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_price=(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_price);
//        tournament_price.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(tournament_price.getText().toString().trim().length()>0){
//                    tournament_price_error.setErrorEnabled(false);
//
//                }else{
//                    tournament_price_error.setErrorEnabled(true);
//                    tournament_price_error.setError(getResources().getString(R.string.user_error));
//                }
//            }
//        });
//        tournament_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(tournament_price.getText().toString().trim().length()>0){
//                    tournament_price_error.setErrorEnabled(false);
//
//                }else{
//                    tournament_price_error.setErrorEnabled(true);
//                    tournament_price_error.setError(getResources().getString(R.string.user_error));
//                }
//            }
//        });
        tournament_image=(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_image);
        tournament_description=(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_description);
        tournament_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(tournament_description.getText().toString().trim().length()>0){
                    tournament_description_error.setErrorEnabled(false);

                }else{
                    tournament_description_error.setErrorEnabled(true);
                    tournament_description_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tournament_description.getText().toString().trim().length()>0){
                    tournament_description_error.setErrorEnabled(false);

                }else{
                    tournament_description_error.setErrorEnabled(true);
                    tournament_description_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_location=(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_location);
        tournament_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(tournament_location.getText().toString().trim().length()>0){
                    tournament_location_error.setErrorEnabled(false);

                }else{
                    tournament_location_error.setErrorEnabled(true);
                    tournament_location_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tournament_location.getText().toString().trim().length()>0){
                    tournament_location_error.setErrorEnabled(false);

                }else{
                    tournament_location_error.setErrorEnabled(true);
                    tournament_location_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        tournament_postalcode=(TextInputEditText)rootViewTournament.findViewById(R.id.tournament_postalcode);
        tournament_postalcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(tournament_postalcode.getText().toString().trim().length()>0){
                    tournament_postalcode_error.setErrorEnabled(false);

                }else{
                    tournament_postalcode_error.setErrorEnabled(true);
                    tournament_postalcode_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        tournament_postalcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tournament_postalcode.getText().toString().trim().length()>0){
                    tournament_postalcode_error.setErrorEnabled(false);

                }else{
                    tournament_postalcode_error.setErrorEnabled(true);
                    tournament_postalcode_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        animation_save = (Button)rootViewTournament.findViewById(R.id.tournament_save);
        tournament_cancel = (Button)rootViewTournament.findViewById(R.id.tournament_cancel);
        tournament_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RealMainPageActivity)getContext()).setLastPosition(Constant.MENU_TOURNAMENT);
                ((RealMainPageActivity)getContext()).setFragmentList(Constant.MENU_TOURNAMENT);
            }
        });

        tournament_image_upload=(Button) rootViewTournament.findViewById(R.id.tournament_image_upload);

//        Tournament_Car = (CheckBox)rootViewTournament.findViewById(R.id.tournament_car);
//        Tournament_Bike = (CheckBox)rootViewTournament.findViewById(R.id.tournament_bike);
//        Tournament_Bus = (CheckBox)rootViewTournament.findViewById(R.id.tournament_bus);
//        Tournament_Train = (CheckBox)rootViewTournament.findViewById(R.id.tournament_train);

        Tournament_Commission  = (RadioButton) rootViewTournament.findViewById(R.id.tournament_commission);
        Tournament_Abonnement  = (RadioButton) rootViewTournament.findViewById(R.id.tournament_abonnement);
        Tournament_image_preview = (ImageView) rootViewTournament.findViewById(R.id.tournament_image_preview) ;
        save_button_hide_show =(LinearLayout) rootViewTournament.findViewById(R.id.save_button_hide_show);

        tournament_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });

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

        tournament_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Objects.requireNonNull(getContext()), dateForm, myCalendarFrom
                        .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                        myCalendarFrom.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tournament_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(Objects.requireNonNull(getContext()), dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        tournament_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });

        if(mParam1.equals("update")){
            getTournament();
        }
        animation_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mParam1.equals("update")){
                    updateTournament();
                }else {
                    insertTournament();
                }
            }
        });
    }

    public void getTournament()
    {
        ((RealMainPageActivity)getContext()).showprocess();
        apiInterface.gettournament(coachid_,mParam2).enqueue(new Callback<TournamentDTO>() {
            @Override
            public void onResponse(@NonNull Call<TournamentDTO> call, @NonNull Response<TournamentDTO> response) {
                assert response.body() != null;

                System.out.println("- --> " + new Gson().toJson(response.body()));

                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    ArrayList<TournamentDTO.TournamentCourse> tournamentCourseArrayList = response.body().getData().getCourse();
                    if(tournamentCourseArrayList.size() != 0){

                        TournamentDTO.TournamentCourse tournamentCourse = tournamentCourseArrayList.get(0);
                        tournament_name_of_event.setText(tournamentCourse.getTournamentname());
                        editorvalue = tournamentCourse.getEventdetails();
                        String utcDateStr = tournamentCourse.getFrom_date();
                        SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Date date = null;
                        try {
                            date = sdf.parse(utcDateStr);
                           //                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
                            tournament_from.setText( new  SimpleDateFormat("yyyy-MM-dd").format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String utcDateStrto = tournamentCourse.getTo_date();
                        SimpleDateFormat sdfto = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Date dateto = null;
                        try {
                            dateto = sdfto.parse(utcDateStrto);
                            //                            Calendar c = Calendar.getInstance();
//                            c.setTime(dateto);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
//                            dateto = c.getTime();
                            tournament_to.setText( new  SimpleDateFormat("yyyy-MM-dd").format(dateto));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tournament_from.setText(tournamentCourse.getFrom_date());
//                        tournament_to.setText(tournamentCourse.getTo_date());
//                        tournament_price.setText(tournamentCourse.getPrice());
                        tournament_image.setText("/"+tournamentCourse.getFilename());
                        tournament_description.setText(tournamentCourse.getDescription());
                        tournament_location.setText(tournamentCourse.getLocation());
                        tournament_postalcode.setText(tournamentCourse.getPostalcode());

                        if (tournamentCourse.getPhoto() != null && tournamentCourse.getPhoto() != "" && !tournamentCourse.getPhoto().matches("http") && !tournamentCourse.getPhoto().contains("WWW.") && !tournamentCourse.getPhoto().contains("https") &&
                                !tournamentCourse.getPhoto().contains(".jpeg") && !tournamentCourse.getPhoto().contains(".png") && !tournamentCourse.getPhoto().contains("undefined")) {
                            imageString = tournamentCourse.getPhoto();
                            if(imageString.contains("data:image/jpeg;base64,")){
                                imageString = imageString.replace("data:image/jpeg;base64,","");
                            }
                            if(imageString.contains("data:image/png;base64,")){
                                imageString = imageString.replace("data:image/png;base64,","");
                            }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
                            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                            decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                            Tournament_image_preview.setImageBitmap(decodedImage);
                            Tournament_image_preview.setVisibility(View.VISIBLE);

                        }else {
                            Tournament_image_preview.setVisibility(View.GONE);
                        }



                        if (tournamentCourse.getPlan() != null) {
                            if (tournamentCourse.getPlan().equals("Commission")) {
                                Tournament_Commission.setChecked(true);
                            } else if (tournamentCourse.getPlan().equals("Abonnement")) {
                                Tournament_Abonnement.setChecked(true);
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
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TournamentDTO> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

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
        Picasso.get().load(url).fit().into(Tournament_image_preview);
        Tournament_image_preview.setVisibility(View.VISIBLE);
//        GlideApp.with(this).load(url)
//                .into(profile);
        Tournament_image_preview.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Tournament_image_preview);
//        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
//                .into(profile);
        Tournament_image_preview.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
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
                TournamentFragment.this.openSettings();
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

    private void updateFrom() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tournament_from.setText(sdf.format(myCalendarFrom.getTime()));
    }

    private void updateTo() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tournament_to.setText(sdf.format(myCalendarTo.getTime()));
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
                        tournament_image.setText("/"+file);
                    } else {
                        file =  filename;
                        tournament_image.setText("/"+file);
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
    
    public void insertTournament(){

//        if(fieldValidation()) {

        ((RealMainPageActivity)getContext()).showprocess();


        tournamentCourse.setTournamentname(tournament_name_of_event.getText().toString());
        tournamentCourse.setDescription(tournament_description.getText().toString());
        tournamentCourse.setLocation(tournament_location.getText().toString());
        tournamentCourse.setPostalcode(tournament_postalcode.getText().toString());
        tournamentCourse.setPrice("0");
        tournamentCourse.setFilename(tournament_image.getText().toString().replace("/",""));
        tournamentCourse.setPhoto(imageString);

        if(Tournament_Abonnement.isChecked()){
            tournamentCourse.setPlan("Abonnement");
        }else if(Tournament_Commission.isChecked()){
            tournamentCourse.setPlan("Commission");
        }

//        if(Tournament_Train.isChecked()){
//            checktext.add("Train");
//        }
//        if(Tournament_Bus.isChecked()){
//            checktext.add("Bus");
//        }
//        if(Tournament_Bike.isChecked()){
//            checktext.add("Vélo");
//        }
//        if(Tournament_Car.isChecked()){
//            checktext.add("Car");
//        }

//        String transport = TextUtils.join(",", checktext);

//        tournamentCourse.setMode_of_transport(transport);
        
        tournamentCourse.setCoach_Id(coachid_);
        tournamentCourse.setFrom_date(tournament_from.getText().toString());
        tournamentCourse.setTo_date(tournament_to.getText().toString());
        if(editorvalue.equals("")){
            tournamentCourse.setEventdetails("-");
        }else {
            tournamentCourse.setEventdetails(editorvalue);
        }
//        tournamentCourse.setPrice(tournament_price.getText().toString());

        System.out.println("tournamentCourse--> "+new Gson().toJson(tournamentCourse));

        apiInterface.setTournamentCourseInsert(tournamentCourse).enqueue(new Callback<TournamentDTO>() {
            @Override
            public void onResponse(@NonNull Call<TournamentDTO> call, @NonNull Response<TournamentDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if (response.body().getIsSuccess().equals("true")) {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    ((RealMainPageActivity)getContext()).onBackPressed();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                } else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TournamentDTO> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> " + call + " " + t);
            }
        });
    }

    public void updateTournament(){
     ((RealMainPageActivity)getContext()).showprocess();
//        if(fieldValidation()) {

        tournamentCourse.setTournamentname(tournament_name_of_event.getText().toString());
        tournamentCourse.setDescription(tournament_description.getText().toString());
        tournamentCourse.setLocation(tournament_location.getText().toString());
        tournamentCourse.setPostalcode(tournament_postalcode.getText().toString());
        tournamentCourse.setPrice("0");
        tournamentCourse.setFilename(tournament_image.getText().toString().replace("/",""));
        tournamentCourse.setPhoto(imageString);
        if(editorvalue == ""){
        tournamentCourse.setEventdetails("-");
        }else {
            tournamentCourse.setEventdetails(editorvalue);
        }
        tournamentCourse.setId(mParam2);
        if(Tournament_Abonnement.isChecked()){
            tournamentCourse.setPlan("Abonnement");
        }else if(Tournament_Commission.isChecked()){
            tournamentCourse.setPlan("Commission");
        }

//        if(Tournament_Train.isChecked()){
//            checktext.add("Train");
//        }
//        if(Tournament_Bus.isChecked()){
//            checktext.add("Bus");
//        }
//        if(Tournament_Bike.isChecked()){
//            checktext.add("Vélo");
//        }
//        if(Tournament_Car.isChecked()){
//            checktext.add("Car");
//        }
//
//        String transport = TextUtils.join(",", checktext);

//        tournamentCourse.setMode_of_transport(transport);
        tournamentCourse.setCoach_Id(coachid_);

        tournamentCourse.setFrom_date(tournament_from.getText().toString());
        tournamentCourse.setTo_date(tournament_to.getText().toString());

        System.out.println("tournamentCourse--> "+tournamentCourse);


//        tournamentCourse.setPrice(tournament_price.getText().toString());

        apiInterface.setTournamentCourseUpdate(tournamentCourse).enqueue(new Callback<TournamentDTO>() {
            @Override
            public void onResponse(@NonNull Call<TournamentDTO> call, @NonNull Response<TournamentDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if (response.body().getIsSuccess().equals("true")) {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    ((RealMainPageActivity)getContext()).onBackPressed();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                } else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TournamentDTO> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> " + call + " " + t);
            }
        });
    }



}