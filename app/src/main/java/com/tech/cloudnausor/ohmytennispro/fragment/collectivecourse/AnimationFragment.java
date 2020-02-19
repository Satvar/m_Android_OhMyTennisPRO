package com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.tech.cloudnausor.ohmytennispro.dto.AnimationCourse;
import com.tech.cloudnausor.ohmytennispro.dto.AnimationDTO;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.ImagePickerActivity;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class AnimationFragment extends Fragment {

    private boolean mSearchCheck;
    private TextView mTxtDownload;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;
    ApiInterface apiInterface;
    View rootViewAnimation;
    String imageString ="";
    Bitmap decodedImage;
    Menu menumain;
    SingleTonProcess singleTonProcess;
    AnimationCourse setAnimationCourse = new AnimationCourse();
    AnimationCourse animationCourse = new AnimationCourse();

    TextInputLayout animation_image_error
            ,animation_description_error,animation_location_error,animation_postalcode_error;
    TextInputEditText animation_image
            ,animation_description,animation_location,animation_postalcode;
    RadioButton Animation_Commission,Animation_Abonnement;

    ImageView Animation_image_preview;

    LinearLayout save_button_hide_show;
    Button animation_image_upload;

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
    Button animation_save,animation_cancel;

    public AnimationFragment newInstance(String text,String eventName,String eventID){
        AnimationFragment mFragment = new AnimationFragment();
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
        singleTonProcess = singleTonProcess.getInstance();
        rootViewAnimation = inflater.inflate(R.layout.activity_animation, container, false);
        rootViewAnimation.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        initView();
        return rootViewAnimation;
    }

    private void previewTint(){

        save_button_hide_show.setVisibility(View.GONE);
        animation_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        animation_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        animation_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        animation_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));

        Animation_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Animation_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));


        animation_image.setEnabled(false);
        animation_description.setEnabled(false);
        animation_location.setEnabled(false);
        animation_postalcode.setEnabled(false);

        Animation_Commission.setEnabled(false);
        Animation_Abonnement.setEnabled(false);

    }

    public void editviewTint(){

        save_button_hide_show.setVisibility(View.VISIBLE);
        menumain.findItem(Menus.EDIT).setVisible(false);


        animation_image.setEnabled(false);
        animation_description.setEnabled(true);
        animation_location.setEnabled(true);
        animation_postalcode.setEnabled(true);

        Animation_Commission.setEnabled(true);
        Animation_Abonnement.setEnabled(true);


        animation_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        animation_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        animation_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        animation_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Animation_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Animation_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));


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
       
        animation_image_error=(TextInputLayout)rootViewAnimation.findViewById(R.id.animation_image_error);
        animation_description_error=(TextInputLayout)rootViewAnimation.findViewById(R.id.animation_description_error);
        animation_location_error=(TextInputLayout)rootViewAnimation.findViewById(R.id.animation_location_error);
        animation_postalcode_error=(TextInputLayout)rootViewAnimation.findViewById(R.id.animation_postalcode_error);

        animation_image=(TextInputEditText)rootViewAnimation.findViewById(R.id.animation_image);

        animation_description=(TextInputEditText)rootViewAnimation.findViewById(R.id.animation_description);

        animation_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(animation_description.getText().toString().trim().length()>0){
                    animation_description_error.setErrorEnabled(false);

                }else{
                    animation_description_error.setErrorEnabled(true);
                    animation_description_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });
        animation_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(animation_description.getText().toString().trim().length()>0){
                    animation_description_error.setErrorEnabled(false);

                }else{
                    animation_description_error.setErrorEnabled(true);
                    animation_description_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        animation_location=(TextInputEditText)rootViewAnimation.findViewById(R.id.animation_location);
        animation_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(animation_location.getText().toString().trim().length()>0){
                    animation_location_error.setErrorEnabled(false);

                }else{
                    animation_location_error.setErrorEnabled(true);
                    animation_location_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        animation_location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(animation_location.getText().toString().trim().length()>0){
                    animation_location_error.setErrorEnabled(false);

                }else{
                    animation_location_error.setErrorEnabled(true);
                    animation_location_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        animation_postalcode=(TextInputEditText)rootViewAnimation.findViewById(R.id.animation_postalcode);

        animation_postalcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(animation_postalcode.getText().toString().trim().length()>0){
                    animation_postalcode_error.setErrorEnabled(false);
                }else{
                    animation_postalcode_error.setErrorEnabled(true);
                    animation_postalcode_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });



        animation_postalcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(animation_postalcode.getText().toString().trim().length()>0){
                    animation_postalcode_error.setErrorEnabled(false);

                }else{
                    animation_postalcode_error.setErrorEnabled(true);
                    animation_postalcode_error.setError(getResources().getString(R.string.user_error));
                }
            }
        });

        Animation_Commission  = (RadioButton) rootViewAnimation.findViewById(R.id.animation_commission);
        Animation_Abonnement  = (RadioButton) rootViewAnimation.findViewById(R.id.animation_abonnement);

        Animation_image_preview = (ImageView) rootViewAnimation.findViewById(R.id.animation_image_preview) ;


        save_button_hide_show =(LinearLayout) rootViewAnimation.findViewById(R.id.save_button_hide_show);

        animation_save = (Button)rootViewAnimation.findViewById(R.id.animation_save);
        animation_cancel = (Button)rootViewAnimation.findViewById(R.id.animation_cancel);
        animation_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RealMainPageActivity)getContext()).setLastPosition(Constant.MENU_ANIMATION);
                ((RealMainPageActivity)getContext()).setFragmentList(Constant.MENU_ANIMATION);
            }
        });

        animation_image_upload =(Button) rootViewAnimation.findViewById(R.id.animation_image_upload);

        animation_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });

       if(mParam1.equals("update")){
        getAnimation();
       }

        animation_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mParam1.equals("update")){
                    updateAnimation();
                }else {
                    insertAnimation();


                }
            }
        });
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

    public void insertAnimation(){
//        if(fieldValidation()) {
        ((RealMainPageActivity)getContext()).showprocess();

            setAnimationCourse.setDescription(animation_description.getText().toString());
            setAnimationCourse.setDescription(animation_description.getText().toString());
            setAnimationCourse.setLocation(animation_location.getText().toString());
            setAnimationCourse.setPostalcode(animation_postalcode.getText().toString());
            setAnimationCourse.setFilename(animation_image.getText().toString().replace("/",""));
            setAnimationCourse.setPhoto(imageString);
            setAnimationCourse.setPlan("Commission");
            setAnimationCourse.setCoach_Id(coachid_);
            setAnimationCourse.setPrice("0");

        if(animationCourse.getEventdetails() != null && animationCourse.getEventdetails().equals("")) {
            setAnimationCourse.setEventdetails(animationCourse.getEventdetails());
        }else {
            setAnimationCourse.setEventdetails("-");
        }

        apiInterface.setanimationinsert(setAnimationCourse).enqueue(new Callback<AnimationDTO>() {
                @Override
                public void onResponse(@NonNull Call<AnimationDTO> call, @NonNull Response<AnimationDTO> response) {
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
                public void onFailure(Call<AnimationDTO> call, Throwable t) {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    System.out.println("---> " + call + " " + t);
                }
            });
        }

    public void updateAnimation(){
//        if(fieldValidation()) {
        ((RealMainPageActivity)getContext()).showprocess();

        setAnimationCourse.setDescription(animation_description.getText().toString());
        setAnimationCourse.setLocation(animation_location.getText().toString());
        setAnimationCourse.setPostalcode(animation_postalcode.getText().toString());
        setAnimationCourse.setFilename(animation_image.getText().toString().replace("/",""));
        setAnimationCourse.setPhoto(imageString);
        setAnimationCourse.setPlan("Commission");
        setAnimationCourse.setCoach_Id(coachid_);
        setAnimationCourse.setPrice("0");
        setAnimationCourse.setId(mParam2);
        if(animationCourse.getEventdetails() != null && animationCourse.getEventdetails().equals("")) {
            setAnimationCourse.setEventdetails(animationCourse.getEventdetails());
        }else {
            setAnimationCourse.setEventdetails("-");
        }

        apiInterface.setanimationupdate(setAnimationCourse).enqueue(new Callback<AnimationDTO>() {
            @Override
            public void onResponse(@NonNull Call<AnimationDTO> call, @NonNull Response<AnimationDTO> response) {
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
            public void onFailure(Call<AnimationDTO> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> " + call + " " + t);
            }
        });
    }

//    }

    private boolean fieldValidation() {

        return true;
    }

    public void getAnimation()
    {
        ((RealMainPageActivity)getContext()).showprocess();

        apiInterface.getanimation(coachid_,mParam2).enqueue(new Callback<AnimationDTO>() {
            @Override
            public void onResponse(@NonNull Call<AnimationDTO> call, @NonNull Response<AnimationDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    ArrayList<AnimationCourse>   animationCourseArrayList = response.body().getData().getCourse();
                    if(animationCourseArrayList.size() != 0){

                        animationCourse = animationCourseArrayList.get(0);
                        animation_image.setText("/"+animationCourse.getFilename());
                        animation_description.setText(animationCourse.getDescription());
                        animation_location.setText(animationCourse.getLocation());
                        animation_postalcode.setText(animationCourse.getPostalcode());
                        if (animationCourse.getPhoto() != null && animationCourse.getPhoto() != "" && !animationCourse.getPhoto().matches("http") && !animationCourse.getPhoto().contains("WWW.") && !animationCourse.getPhoto().contains("https") &&
                                !animationCourse.getPhoto().contains(".jpeg") && !animationCourse.getPhoto().contains(".png") && !animationCourse.getPhoto().contains("undefined")) {
                            imageString = animationCourse.getPhoto();
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
                            Animation_image_preview.setImageBitmap(decodedImage);
                            Animation_image_preview.setVisibility(View.VISIBLE);
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
                            Animation_image_preview.setVisibility(View.GONE);
                        }


//                        if (animationCourse.getMode_of_transport() != null) {
//                            String ass = animationCourse.getMode_of_transport();
//                            String[] stringArrayList = ass.split(",");
//                            for (String s : stringArrayList) {
//                                switch (s) {
//                                    case "Car":
//                                        Animation_Car.setChecked(true);
//                                        break;
//                                    case "Vélo":
//                                        Animation_Bike.setChecked(true);
//                                        break;
//                                    case "Train":
//                                        Animation_Train.setChecked(true);
//                                        break;
//                                    case "Bus":
//                                        Animation_Bus.setChecked(true);
//                                        break;
//                                }
//                            }
//                        }

                        if (animationCourse.getPlan() != null) {
                            if (animationCourse.getPlan().equals("Commission")) {
                                Animation_Commission.setChecked(true);
                            } else if (animationCourse.getPlan().equals("Abonnement")) {
                                Animation_Abonnement.setChecked(true);
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
            public void onFailure(Call<AnimationDTO> call, Throwable t) {
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
        Picasso.get().load(url).fit().into(Animation_image_preview);
        Animation_image_preview.setVisibility(View.VISIBLE);
//        GlideApp.with(this).load(url)
//                .into(profile);
        Animation_image_preview.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(Animation_image_preview);
//        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
//                .into(profile);
        Animation_image_preview.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
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
                AnimationFragment.this.openSettings();
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
                        animation_image.setText("/"+file);
                    } else {
                        file =  filename;
                        animation_image.setText("/"+file);
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
//                    InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
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

}