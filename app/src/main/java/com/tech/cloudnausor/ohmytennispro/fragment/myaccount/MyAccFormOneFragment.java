package com.tech.cloudnausor.ohmytennispro.fragment.myaccount;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.MyaccountGetData;
import com.tech.cloudnausor.ohmytennispro.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachUpdateResponse;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.ImagePickerActivity;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAccFormOneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAccFormOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccFormOneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String PREFER_NAME = "Reg";
    private static final String ARG_PARAM2 = "param2";
    String filePath;
    String filename;
    CoachDetailsModel coachDetailsModel = new CoachDetailsModel();
    CoachDetailsModel coachDetailsModel1 = new CoachDetailsModel();
    Drawable drawable,drawable1;
    Bitmap decodedImage;
    SingleTonProcess singleTonProcess;


    ViewpagerInterface viewpagerInterface;


    String edit_mode = "0";
    ApiInterface apiInterface;
    TextInputLayout Form_One_First_Name_error,Form_One_Second_Name_error,Form_One_Email_error,Form_One_Mobile_error,Form_One_Description_error,Form_One_Facebook_error,Form_One_Instagram_error,Form_One_Twitter_error,Form_one_postal_error;
    TextInputEditText Form_One_First_Name,Form_One_Second_Name,Form_One_Email,Form_One_Mobile,Form_One_Description,Form_One_Facebook,Form_One_Instagram,Form_One_Twitter,Form_one_postal;
    Button Form_One_Save_Continue,Modifier;
    SessionManagement session;
    SharedPreferences sharedPreferences;


    SharedPreferences.Editor editor;
    private static final String TAG = RealMainPageActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    String imageString ="";
    ImageView profile;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    Bitmap mBitmap;
    String edit_sting = null,loginObject;
    String uPassword =null;

    String coachid_ = null;




    FragmentManager fragement ;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyAccFormOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccFormOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccFormOneFragment newInstance(String param1, String param2) {
        MyAccFormOneFragment fragment = new MyAccFormOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        singleTonProcess = singleTonProcess.getInstance();
        // Inflate the layout for this fragment
        View view_myaccformone = inflater.inflate(R.layout.fragment_my_acc_form_one, container, false);
        Modifier = (Button)view_myaccformone.findViewById(R.id.modifier_one);
        Form_One_First_Name =(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_first_name);
        Form_One_Second_Name =(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_second_name);
        Form_One_Email =(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_email);
        Form_One_Mobile =(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_mobile);
        Form_One_Description =(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_description);
        Form_One_Facebook=(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_facebook);
        Form_One_Instagram=(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_instagram);
        Form_One_Twitter=(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_twitter);
        Form_one_postal=(TextInputEditText)view_myaccformone.findViewById(R.id.form_one_postal);

        Form_One_First_Name_error =(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_first_name_error);
        Form_One_Second_Name_error =(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_second_name_error);
        Form_One_Email_error =(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_email_error);
        Form_One_Mobile_error =(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_mobile_error);
        Form_One_Description_error =(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_description_error);
        Form_One_Facebook_error=(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_facebook_error);
        Form_One_Instagram_error=(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_instagram_error);
        Form_One_Twitter_error=(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_twitter_error);
        Form_one_postal_error=(TextInputLayout)view_myaccformone.findViewById(R.id.form_one_postal_error);

        Form_One_Save_Continue  =(Button) view_myaccformone.findViewById(R.id.form_one_save_continue);
        profile =(ImageView)view_myaccformone.findViewById(R.id.form_one_profile);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        session = new SessionManagement(getContext());
        sharedPreferences = getContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        askPermissions();

        final String mode = sharedPreferences.getString("edit_mode","");
        String mode_one = sharedPreferences.getString("edit_mode_one","");


        if(mode.equals("false") && mode_one.equals("false")){
            edit_mode ="1";
            Modifier.setVisibility(View.GONE);
            EditTint();
            getPageData();
        }else{
            edit_mode ="0";
            Modifier.setVisibility(View.VISIBLE);
            previewTint();
            getPageData();


//            previewTint();
//            getPageData();
        }

        if(sharedPreferences.contains("IsMyEditString")){
            edit_sting = sharedPreferences.getString("IsMyEditString","");
        }

        if (sharedPreferences.contains("KEY_Coach_ID"))
        {
            coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");
        }
        if(sharedPreferences.contains("LoginObject")){
            loginObject = sharedPreferences.getString("LoginObject","");
            Gson gson = new Gson();
            coachDetailsModel1 = gson.fromJson(loginObject,CoachDetailsModel.class);
        }

//        Hi Anandan K, can you have phone call to discuss about  that media files upload and download api's
        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");

        }

//        if(edit_mode.equals("0")) {
//            previewTint();
//            getPageData();
//        }else {
//            EditTint();
//        }

        Modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_mode ="1";
                editor.putString("edit_mode","false");
                editor.putString("edit_mode_one","false");
                editor.putString("edit_mode_two","false");
                editor.putString("edit_mode_three","false");
                editor.commit();
                Modifier.setVisibility(View.GONE);
                EditTint();
//                ((MyAccountHomeFragment)getParentFragment()).moreOne();
            }
        });


//        if(!session.checkEdit()) {
//            previewTint();
//        }else {
//            EditTint();
//
//        }

        Form_One_Save_Continue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                edit_mode ="0";
                editor.putString("edit_mode_one","true");
                multipartImageUpload();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProfileImageClick();
//                startActivityForResult(onProfileImageClick(), IMAGE_RESULT);
            }
        });

        Form_One_First_Name.addTextChangedListener(new TextWatcher() {
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
                    Form_One_First_Name_error.setErrorEnabled(true);
                    Form_One_First_Name_error.setError("Indiquez Votre Prénom.");
                }else {
                    Form_One_First_Name_error.setErrorEnabled(false);
                    Form_One_First_Name_error.setError(null);
                }
            }
        });
        Form_One_Second_Name.addTextChangedListener(new TextWatcher() {
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
                    Form_One_Second_Name_error.setErrorEnabled(true);
                    Form_One_Second_Name_error.setError("Indiquez Votre Nom.");
                }else {
                    Form_One_Second_Name_error.setErrorEnabled(false);
                    Form_One_Second_Name_error.setError(null);
                }
            }
        });
        Form_One_Email.addTextChangedListener(new TextWatcher() {
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
                    Form_One_Email_error.setErrorEnabled(true);
                    Form_One_Email_error.setError("Indiquez Votre Emplacement.");
                }else {
                    Form_One_Email_error.setErrorEnabled(false);
                    Form_One_Email_error.setError(null);
                }
            }
        });

        Form_One_Mobile.addTextChangedListener(new TextWatcher() {
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
                    Form_One_Mobile_error.setErrorEnabled(true);
                    Form_One_Mobile_error.setError("Indiquez Votre Téléphone.");
                }else if(editvalue.length() < 10){
                    Form_One_Mobile_error.setErrorEnabled(true);
                    Form_One_Mobile_error.setError("Indiquez Votre Téléphone.");
                }else {
                    Form_One_Mobile_error.setErrorEnabled(false);
                    Form_One_Mobile_error.setError(null);
                }
            }
        });

        Form_one_postal.addTextChangedListener(new TextWatcher() {
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
                    Form_one_postal_error.setErrorEnabled(true);
                    Form_one_postal_error.setError("Indiquez Votre Code Postal.");
                }else if(editvalue.length() < 5){
                    Form_one_postal_error.setErrorEnabled(true);
                    Form_one_postal_error.setError("Indiquez Votre Code Postal.");
                }else {
                    Form_one_postal_error.setErrorEnabled(false);
                    Form_one_postal_error.setError(null);
                }
            }
        });

        Form_One_Description.addTextChangedListener(new TextWatcher() {
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
                    Form_One_Description_error.setErrorEnabled(true);
                    Form_One_Description_error.setError("Indiquez Votre Description.");
                }else {
                    Form_One_Description_error.setErrorEnabled(false);
                    Form_One_Description_error.setError(null);
                }
            }
        });

        Form_One_Facebook.addTextChangedListener(new TextWatcher() {
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
                    Form_One_Facebook_error.setErrorEnabled(true);
                    Form_One_Facebook_error.setError("Indiquez Votre Téléphone.");
                }else {
                    Form_One_Facebook_error.setErrorEnabled(false);
                    Form_One_Facebook_error.setError(null);
                }
            }
        });

        Form_One_Instagram.addTextChangedListener(new TextWatcher() {
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
                    Form_One_Instagram_error.setErrorEnabled(true);
                    Form_One_Instagram_error.setError("Indiquez Votre Téléphone.");
                }else {
                    Form_One_Instagram_error.setErrorEnabled(false);
                    Form_One_Instagram_error.setError(null);
                }
            }
        });
        Form_One_Twitter.addTextChangedListener(new TextWatcher() {
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
                    Form_One_Twitter_error.setErrorEnabled(true);
                    Form_One_Twitter_error.setError("Indiquez Votre Téléphone.");
                }else {
                    Form_One_Twitter_error.setErrorEnabled(false);
                    Form_One_Twitter_error.setError(null);
                }
            }
        });




        Form_One_First_Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_First_Name.getText().toString();


                    if(editvalue.equals("")){
                        Form_One_First_Name_error.setErrorEnabled(true);
                        Form_One_First_Name_error.setError("Indiquez Votre Prénom.");
                    }else {
                        Form_One_First_Name_error.setErrorEnabled(false);
                        Form_One_First_Name_error.setError(null);
                    }
                }
            }
        });
        Form_One_Second_Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_Second_Name.getText().toString();


                    if(editvalue.equals("")){
                        Form_One_Second_Name_error.setErrorEnabled(true);
                        Form_One_Second_Name_error.setError("Indiquez Votre Nom.");
                    }else {
                        Form_One_Second_Name_error.setErrorEnabled(false);
                        Form_One_Second_Name_error.setError(null);
                    }
                }
            }
        });
        Form_One_Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_Email.getText().toString();
                    if(editvalue.equals("")){
                        Form_One_Email_error.setErrorEnabled(true);
                        Form_One_Email_error.setError("Indiquez Votre Emplacement.");
                    }else {
                        Form_One_Email_error.setErrorEnabled(false);
                        Form_One_Email_error.setError(null);
                    }
                }
            }
        });
        Form_One_Mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_Mobile.getText().toString();
                    if(editvalue.equals("")){
                        Form_One_Mobile_error.setErrorEnabled(true);
                        Form_One_Mobile_error.setError("Indiquez Votre Téléphone.");
                    }else if(editvalue.length() < 10){
                        Form_One_Mobile_error.setErrorEnabled(true);
                        Form_One_Mobile_error.setError("Indiquez Votre Téléphone.");
                    }else {
                        Form_One_Mobile_error.setErrorEnabled(false);
                        Form_One_Mobile_error.setError(null);
                    }
                }
            }
        });

        Form_one_postal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_one_postal.getText().toString();
                    if(editvalue.equals("")){
                        Form_one_postal_error.setErrorEnabled(true);
                        Form_one_postal_error.setError("Indiquez Votre Code Postal.");
                    }else if(editvalue.length() < 5){
                        Form_one_postal_error.setErrorEnabled(true);
                        Form_one_postal_error.setError("Indiquez Votre Code Postal.");
                    }else {
                        Form_one_postal_error.setErrorEnabled(false);
                        Form_one_postal_error.setError(null);
                    }
                }
            }
        });

        Form_One_Description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_Description.getText().toString();


                    if(editvalue.equals("")){
                        Form_One_Description_error.setErrorEnabled(true);
                        Form_One_Description_error.setError("Indiquez Votre Description.");
                    }else {
                        Form_One_Description_error.setErrorEnabled(false);
                        Form_One_Description_error.setError(null);
                    }
                }
            }
        });
        Form_One_Twitter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_Twitter.getText().toString();


                    if(editvalue.equals("")){
                        Form_One_Twitter_error.setErrorEnabled(true);
                        Form_One_Twitter_error.setError("Indiquez Votre Twitter Url.");
                    }else {
                        Form_One_Twitter_error.setErrorEnabled(false);
                        Form_One_Twitter_error.setError(null);
                    }
                }
            }
        });
        Form_One_Facebook.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_Facebook.getText().toString();


                    if(editvalue.equals("")){
                        Form_One_Facebook_error.setErrorEnabled(true);
                        Form_One_Facebook_error.setError("Indiquez Votre Facebook Url.");
                    }else {
                        Form_One_Facebook_error.setErrorEnabled(false);
                        Form_One_Facebook_error.setError(null);
                    }
                }
            }
        });

        Form_One_Instagram.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_One_Instagram.getText().toString();


                    if(editvalue.equals("")){
                        Form_One_Instagram_error.setErrorEnabled(true);
                        Form_One_Instagram_error.setError("Indiquez Votre Instagram Url.");
                    }else {
                        Form_One_Instagram_error.setErrorEnabled(false);
                        Form_One_Instagram_error.setError(null);
                    }
                }
            }
        });



        return view_myaccformone;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void previewTint(){

        Form_One_Facebook.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Instagram.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Twitter.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_First_Name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Second_Name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Email.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Mobile.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Save_Continue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_one_postal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Save_Continue.setVisibility(View.GONE);
        profile.setEnabled(false);
        Form_one_postal.setEnabled(false);
        Form_One_First_Name.setEnabled(false);
        Form_One_Second_Name.setEnabled(false);
        Form_One_Email.setEnabled(false);
        Form_One_Mobile.setEnabled(false);
        Form_One_Description.setEnabled(false);
        Form_One_Facebook.setEnabled(false);
        Form_One_Instagram.setEnabled(false);
        Form_One_Twitter.setEnabled(false);
    }

    public void EditTint(){
        profile.setEnabled(true);
        Form_One_First_Name.setEnabled(true);
        Form_One_Second_Name.setEnabled(true);
        Form_One_Email.setEnabled(false);
        Form_One_Mobile.setEnabled(true);
        Form_One_Description.setEnabled(true);
        Form_One_Facebook.setEnabled(true);
        Form_One_Instagram.setEnabled(true);
        Form_One_Twitter.setEnabled(true);
        Form_one_postal.setEnabled(true);
        Form_one_postal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Facebook.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Instagram.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Twitter.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_First_Name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Second_Name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Email.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Form_One_Mobile.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Save_Continue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_One_Save_Continue.setVisibility(View.VISIBLE);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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
                            Picasso.get().load(url).fit().into(profile);
//        GlideApp.with(this).load(url)
//                .into(profile);
        profile.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void loadProfileDefault() {

        Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(profile);
//        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
//                .into(profile);
        profile.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
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
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
//        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
//        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
//        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    InputStream in = getContext().getContentResolver().openInputStream(uri);

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

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                MyAccFormOneFragment.this.openSettings();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            picUri = savedInstanceState.getParcelable("pic_uri");
//            Drawable drawable = savedInstanceState.getParcelable("profileimage");
//            profile.setImageDrawable(drawable);
            //Restore the fragment's state here
        }
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

    private void multipartImageUpload() {

        ((RealMainPageActivity)getContext()).showprocess();

        if(!Form_One_First_Name.getText().toString().equals("") && !Form_One_Second_Name.getText().toString().equals("") && !Form_One_Email.getText().toString().equals("") &&
                !Form_One_Mobile.getText().toString().equals("") && !Form_One_Description.getText().toString().equals("") && !Form_One_Facebook.getText().toString().toString().equals("")
                && !Form_One_Twitter.getText().toString().toString().equals("") && !Form_One_Instagram.getText().toString().toString().equals("") && !Form_one_postal.getText().toString().toString().equals("")){

            if(!imageString.equals("")) {

                String json = sharedPreferences.getString("MyObject", "");
                coachDetailsModel1 = new Gson().fromJson(json, CoachDetailsModel.class);
                if (imageString != null & imageString != "") {
                    if (imageString.contains("/9j/")) {
                        imageString = "data:image/jpeg;base64," + imageString;
                    } else {
                        imageString = "data:image/png;base64," + imageString;
                    }
                }

                coachDetailsModel1.setCoach_Fname(Form_One_First_Name.getText().toString());
                coachDetailsModel1.setCoach_Lname(Form_One_Second_Name.getText().toString());
                coachDetailsModel1.setCoach_Email(Form_One_Email.getText().toString());
                coachDetailsModel1.setCoach_Phone(Form_One_Mobile.getText().toString());
                coachDetailsModel1.setCoach_Price(coachDetailsModel1.getCoach_Price());
                coachDetailsModel1.setCoach_PriceX10(coachDetailsModel1.getCoach_PriceX10());
                coachDetailsModel1.setCoach_Services(coachDetailsModel1.getCoach_Services());
                coachDetailsModel1.setCoach_transport(coachDetailsModel1.getCoach_transport());
                coachDetailsModel1.setCoach_City(Form_one_postal.getText().toString());
//            coachDetailsModel1.setCoach_Image("base64");
                coachDetailsModel1.setCoach_Image(imageString);
                coachDetailsModel1.setCoach_Resume(coachDetailsModel1.getCoach_Resume());
                coachDetailsModel1.setCoach_Description(Form_One_Description.getText().toString());
                coachDetailsModel1.setCoach_payment_type(coachDetailsModel1.getCoach_payment_type());
                coachDetailsModel1.setCoach_Bank_Name(coachDetailsModel1.getCoach_Bank_Name());
                coachDetailsModel1.setBranch_Code(coachDetailsModel1.getBranch_Code());
                coachDetailsModel1.setCoach_Bank_ACCNum(coachDetailsModel1.getCoach_Bank_ACCNum());
                coachDetailsModel1.setCoach_Rayon(coachDetailsModel1.getCoach_Rayon());
                coachDetailsModel1.setCoach_Bank_City(coachDetailsModel1.getCoach_Bank_City());
                coachDetailsModel1.setFacebookURL(Form_One_Facebook.getText().toString());
                coachDetailsModel1.setInstagramURL(Form_One_Instagram.getText().toString());
                coachDetailsModel1.setTwitterURL(Form_One_Twitter.getText().toString());

                Gson gson = new Gson();
                String coachDetaildsModel = gson.toJson(coachDetailsModel1);
                editor.putString("MyObject", coachDetaildsModel);
                editor.putString("edit_mode_one", "true");
                editor.commit();
                getPageData();
                String json1 = sharedPreferences.getString("MyObject", "");
                coachDetailsModel1 = new Gson().fromJson(json1, CoachDetailsModel.class);
                System.out.println("one.one-->" + coachDetailsModel1);
                Modifier.setVisibility(View.VISIBLE);
                ((RealMainPageActivity) getContext()).dismissprocess();
                ((MyAccountHomeFragment) getParentFragment()).moreTwo();


//    Call<CoachUpdateResponse> req = apiInterface.getDetailedInsertCoach(Form_One_First_Name.getText().toString(),Form_One_Second_Name.getText().toString(),
//            Form_One_Email.getText().toString(),Form_One_Mobile.getText().toString(),coachDetailsModel1.getCoach_Price(),coachDetailsModel1.getCoach_PriceX10(),
//            coachDetailsModel1.getCoach_Services(),coachDetailsModel1.getCoach_transport(),imageString,coachDetailsModel1.getCoach_Resume(),Form_One_Description.getText().toString(),
//            coachDetailsModel1.getActive_Status(),coachDetailsModel1.getCoach_payment_type(),
//            coachDetailsModel1.getCoach_Bank_Name(),coachDetailsModel1.getBranch_Code(),coachDetailsModel1.getCoach_Bank_ACCNum(),coachDetailsModel1.getCoach_Rayon(),coachDetailsModel1.getCoach_Bank_City(),
//            coachDetailsModel1.getCoach_Resume());

//    req.enqueue(new Callback<CoachUpdateResponse>() {
//        @Override
//        public void onResponse(Call<CoachUpdateResponse> call, Response<CoachUpdateResponse> response) {
//            if (response.code() == 200) {
//                getPageData();
//                Modifier.setVisibility(View.VISIBLE);
////                        textView.setText("Uploaded Successfully!");
////                        textView.setTextColor(Color.BLUE);
////                        Log.e("response.body()",new Gson(response.body().toString()));
////                        System.out.println(" response.body().getCoachUpdateRetrunList().get(0).getCoach_Image() " + response.body().getCoachUpdateRetrunList().get(0).getCoach_Image());
//1
//            }
//
////                    Toast.makeText(getContext(), response.code() + " Uploaded Successfully! ", Toast.LENGTH_LONG).show();
//            System.out.println(" Successfully  --> 12 " + new Gson().toJson(response.body()));
//        }
//
//        @Override
//        public void onFailure(Call<CoachUpdateResponse> call, Throwable t) {
////                    textView.setText("Uploaded Failed!");
////                    textView.setTextColor(Color.RED);
//            System.out.println(" Successfully  --> 13 "+t.toString());
//
//            Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
//            t.printStackTrace();
//        }
//    });



//            getChildFragmentManager().beginTransaction().replace(R.id.view_pager_fragement,new MyAccFormTwoFragment(),"MyAccFormTwoFragment");

            }else {
                ((RealMainPageActivity)getContext()).dismissprocess();
                Toast.makeText(getContext(), "Please upload profile photo", Toast.LENGTH_SHORT).show();
            }
    }else {
             ((RealMainPageActivity)getContext()).dismissprocess();
            Toast.makeText(getContext(), "Please enter all data", Toast.LENGTH_SHORT).show();
        }

    }

    public void getPageData(){
       ((RealMainPageActivity)getContext()).showprocess();
        String json = sharedPreferences.getString("MyObject","");
        coachDetailsModel1 = new Gson().fromJson(json,CoachDetailsModel.class);

        System.out.println("one-->"+new Gson().toJson(coachDetailsModel1));

//        apiInterface.getCoachDeatils(uPassword).enqueue(new Callback<CoachDetailsResponseData>() {
//            @Override
//            public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
//                assert response.body() != null;
//                if (response.body().getData() != null) {
//                    MyaccountGetData myaccountGetData = response.body().getData();
//                    coachDetailsModel1 = myaccountGetData.getCoachDetailsModelArrayList().get(0);

                    if (coachDetailsModel1.getCoach_Fname() != null)
                        Form_One_First_Name.setText(coachDetailsModel1.getCoach_Fname());
                    if (coachDetailsModel1.getCoach_Fname() != null)
                        Form_One_Second_Name.setText(coachDetailsModel1.getCoach_Lname());
                    if (coachDetailsModel1.getCoach_Fname() != null)
                        Form_One_Email.setText(coachDetailsModel1.getCoach_Email());
                    if (coachDetailsModel1.getCoach_Fname() != null)
                        Form_One_Mobile.setText(coachDetailsModel1.getCoach_Phone());
                    if (coachDetailsModel1.getCoach_Fname() != null)
                        Form_One_Description.setText(coachDetailsModel1.getCoach_Description());
                    else
                        Form_One_Description.setText("-");
        if (coachDetailsModel1.getFacebookURL() != null)
            Form_One_Facebook.setText(coachDetailsModel1.getFacebookURL());
        else
            Form_One_Facebook.setText("-");
        if (coachDetailsModel1.getTwitterURL() != null)
            Form_One_Twitter.setText(coachDetailsModel1.getTwitterURL());
        else
            Form_One_Twitter.setText("-");
        if (coachDetailsModel1.getInstagramURL() != null)
            Form_One_Instagram.setText(coachDetailsModel1.getInstagramURL());
        else
            Form_One_Instagram.setText("-");

        if (coachDetailsModel1.getCoach_City() != null)
            Form_one_postal.setText(coachDetailsModel1.getCoach_City());
        else
            Form_one_postal.setText("-");


                    if (coachDetailsModel1.getCoach_Image() != null && coachDetailsModel1.getCoach_Image() != "" && !coachDetailsModel1.getCoach_Image().matches("http") && !coachDetailsModel1.getCoach_Image().contains("WWW.") && !coachDetailsModel1.getCoach_Image().contains("https") &&
                            !coachDetailsModel1.getCoach_Image().contains(".jpeg") && !coachDetailsModel1.getCoach_Image().contains(".png") && !coachDetailsModel1.getCoach_Image().contains("undefined")) {
                        imageString = coachDetailsModel1.getCoach_Image();
                        if(imageString.contains("data:image/jpeg;base64,")){
                            imageString = imageString.replace("data:image/jpeg;base64,","");
                        }
                        if(imageString.contains("data:image/png;base64,")){
                            imageString = imageString.replace("data:image/png;base64,","");
                        }
                        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
//                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                         decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//                        drawable = new BitmapDrawable(Objects.requireNonNull(getActivity()).getResources(), decodedImage);
//                        Drawable drawable = (Drawable)new BitmapDrawable(decodedImage);
                         profile.setImageBitmap(decodedImage);

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
                         ((RealMainPageActivity)getContext()).dismissprocess();
                    }
                    else
                        loadProfileDefault();

                       ((RealMainPageActivity)getContext()).dismissprocess();
//                    Picasso.get().load("http://172.107.175.10:3000/upload/"+coachDetailsModel1.getCoach_Image()).fit().into(profile);
                    System.out.println("sys---> bala " + coachDetailsModel1.getCoach_Image());

                }

                public void setViewpagerInterface(ViewpagerInterface viewpagerInterface){
                    this.viewpagerInterface = viewpagerInterface;
                }

//            }
//
//            @Override
//            public void onFailure(Call<CoachDetailsResponseData> call, Throwable t) {
//                System.out.println(" sys---> " + t);
//            }
//        });





//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.fab:
//                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
//                break;
//
////            case R.id.fabUpload:
////                if (mBitmap != null)
////                    multipartImageUpload();
////                else {
////                    Toast.makeText(getApplicationContext(), "Bitmap is null. Try again", Toast.LENGTH_SHORT).show();
////                }
//                break;
//        }
//    }



}