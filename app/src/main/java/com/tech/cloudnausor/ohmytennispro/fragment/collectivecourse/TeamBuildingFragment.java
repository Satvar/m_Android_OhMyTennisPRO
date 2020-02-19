package com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.system.ErrnoException;
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
import com.tech.cloudnausor.ohmytennispro.dto.TeamBuildingDTO;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.ImagePickerActivity;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class TeamBuildingFragment extends Fragment {

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
    View rootViewTeamBuilding;
    Button teambuilding_image_upload,teambuilding_save,teambuilding_cancel;
    ArrayList<String> checktext = new ArrayList<String>();
    SingleTonProcess singleTonProcess;


    private static final String TAG = RealMainPageActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    ImageView profile;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;
    TeamBuildingDTO.TeamBuildingCourse getteambuildingCourse = new TeamBuildingDTO.TeamBuildingCourse();

    TextInputLayout teambuilding_description_error,teambuilding_image_error;
    TextInputEditText teambuilding_description,teambuilding_image;
    CheckBox TeamBuilding_Car,TeamBuilding_Bike,TeamBuilding_Train,TeamBuilding_Bus;
    RadioButton TeamBuilding_Commission,TeamBuilding_Abonnement;

    ImageView TeamBuilding_image_preview;

    LinearLayout save_button_hide_show;

    public TeamBuildingFragment newInstance(String text){
        TeamBuildingFragment mFragment = new TeamBuildingFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        rootViewTeamBuilding = inflater.inflate(R.layout.activity_team_building, container, false);
        rootViewTeamBuilding.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
       singleTonProcess = singleTonProcess.getInstance();
        initView();
        getTeamBuilding();
        return rootViewTeamBuilding;
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

    private void previewTint(){

        save_button_hide_show.setVisibility(View.GONE);
//        teambuilding_name_of_event.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        teambuilding_from.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        teambuilding_to.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        teambuilding_price.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        teambuilding_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        teambuilding_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        teambuilding_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        teambuilding_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        TeamBuilding_Car.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        TeamBuilding_Bike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        TeamBuilding_Train.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        TeamBuilding_Bus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        TeamBuilding_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        TeamBuilding_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        teambuilding_image_upload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
//        teambuilding_name_of_event.setEnabled(false);
//        teambuilding_from.setEnabled(false);
//        teambuilding_to.setEnabled(false);
//        teambuilding_price.setEnabled(false);
        teambuilding_image.setEnabled(false);
        teambuilding_description.setEnabled(false);
//        teambuilding_location.setEnabled(false);
//        teambuilding_postalcode.setEnabled(false);
        TeamBuilding_Car.setEnabled(false);
        TeamBuilding_Bike.setEnabled(false);
        TeamBuilding_Train.setEnabled(false);
        TeamBuilding_Bus.setEnabled(false);
        TeamBuilding_Commission.setEnabled(false);
        TeamBuilding_Abonnement.setEnabled(false);
        teambuilding_image_upload.setEnabled(false);

    }

    public void editviewTint(){

        save_button_hide_show.setVisibility(View.VISIBLE);
        menumain.findItem(Menus.EDIT).setVisible(false);

//        teambuilding_name_of_event.setEnabled(true);
//        teambuilding_from.setEnabled(true);
//        teambuilding_to.setEnabled(true);
//        teambuilding_price.setEnabled(true);
        teambuilding_image.setEnabled(false);
        teambuilding_description.setEnabled(true);
//        teambuilding_location.setEnabled(true);
//        teambuilding_postalcode.setEnabled(true);
        TeamBuilding_Car.setEnabled(true);
        TeamBuilding_Bike.setEnabled(true);
        TeamBuilding_Train.setEnabled(true);
        TeamBuilding_Bus.setEnabled(true);
        TeamBuilding_Commission.setEnabled(true);
        TeamBuilding_Abonnement.setEnabled(true);
        teambuilding_image_upload.setEnabled(true);

//        teambuilding_name_of_event.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        teambuilding_from.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        teambuilding_to.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        teambuilding_price.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        teambuilding_image.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        teambuilding_description.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        teambuilding_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
//        teambuilding_postalcode.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        TeamBuilding_Car.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        TeamBuilding_Bike.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        TeamBuilding_Train.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        TeamBuilding_Bus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        TeamBuilding_Commission.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        TeamBuilding_Abonnement.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        teambuilding_image_upload.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.attach)));

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

        teambuilding_image_error=(TextInputLayout)rootViewTeamBuilding.findViewById(R.id.teambuilding_image_error);
        teambuilding_description_error=(TextInputLayout)rootViewTeamBuilding.findViewById(R.id.teambuilding_description_error);

        teambuilding_image=(TextInputEditText)rootViewTeamBuilding.findViewById(R.id.teambuilding_image);
        teambuilding_description=(TextInputEditText)rootViewTeamBuilding.findViewById(R.id.teambuilding_description);
        teambuilding_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(teambuilding_description.getText().toString().trim().length()>0){
                    teambuilding_description_error.setErrorEnabled(false);

                }else{
                    teambuilding_description_error.setErrorEnabled(true);
                    teambuilding_description_error.setError("Indiquez votre Description");
                }
            }
        });
        teambuilding_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(teambuilding_description.getText().toString().trim().length()>0){
                    teambuilding_description_error.setErrorEnabled(false);

                }else{
                    teambuilding_description_error.setErrorEnabled(true);
                    teambuilding_description_error.setError("Indiquez votre Description");
                }
            }
        });

        TeamBuilding_Car = (CheckBox)rootViewTeamBuilding.findViewById(R.id.teambuilding_car);
        TeamBuilding_Bike = (CheckBox)rootViewTeamBuilding.findViewById(R.id.teambuilding_bike);
        TeamBuilding_Bus = (CheckBox)rootViewTeamBuilding.findViewById(R.id.teambuilding_bus);
        TeamBuilding_Train = (CheckBox)rootViewTeamBuilding.findViewById(R.id.teambuilding_train);
        teambuilding_image_upload = (Button) rootViewTeamBuilding.findViewById(R.id.teambuilding_image_upload);

        TeamBuilding_Commission  = (RadioButton) rootViewTeamBuilding.findViewById(R.id.teambuilding_commission);
        TeamBuilding_Abonnement  = (RadioButton) rootViewTeamBuilding.findViewById(R.id.teambuilding_abonnement);
        TeamBuilding_image_preview = (ImageView) rootViewTeamBuilding.findViewById(R.id.teambuilding_image_preview) ;
        teambuilding_save = (Button) rootViewTeamBuilding.findViewById(R.id.teambuilding_save);
        teambuilding_cancel = (Button) rootViewTeamBuilding.findViewById(R.id.teambuilding_cancel);

        save_button_hide_show =(LinearLayout) rootViewTeamBuilding.findViewById(R.id.save_button_hide_show);
        teambuilding_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });
        teambuilding_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTeamBuilding();
            }
        });
        teambuilding_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTeamBuilding();
            }
        });
        getTeamBuilding();
    }



    public void getTeamBuilding()
    {
        ((RealMainPageActivity)getContext()).showprocess();

        apiInterface.getteambuildingcourse(coachid_).enqueue(new Callback<TeamBuildingDTO>() {
            @Override
            public void onResponse(@NonNull Call<TeamBuildingDTO> call, @NonNull Response<TeamBuildingDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    ArrayList<TeamBuildingDTO.TeamBuildingCourse> teambuildingCourseArrayList = response.body().getData().getCourse();
                    if(teambuildingCourseArrayList.size() != 0){

                      getteambuildingCourse = teambuildingCourseArrayList.get(0);
//                        teambuilding_price.setText(teambuildingCourse.getPrice());
                        teambuilding_image.setText("/"+getteambuildingCourse.getFilename());
                        teambuilding_description.setText(getteambuildingCourse.getDescription());
                        if (getteambuildingCourse.getPhoto() != null && getteambuildingCourse.getPhoto() != "" && !getteambuildingCourse.getPhoto().matches("http") && !getteambuildingCourse.getPhoto().contains("WWW.") && !getteambuildingCourse.getPhoto().contains("https") &&
                                !getteambuildingCourse.getPhoto().contains(".jpeg") && !getteambuildingCourse.getPhoto().contains(".png") && !getteambuildingCourse.getPhoto().contains("undefined")) {
                            imageString = getteambuildingCourse.getPhoto();
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
                            TeamBuilding_image_preview.setImageBitmap(decodedImage);
                            TeamBuilding_image_preview.setVisibility(View.VISIBLE);

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
                            TeamBuilding_image_preview.setVisibility(View.GONE);
                        }

                        if (getteambuildingCourse.getMode_of_transport() != null) {
                            String ass = getteambuildingCourse.getMode_of_transport();
                            String[] stringArrayList = ass.split(",");
                            for (String s : stringArrayList) {
                                switch (s) {
                                    case "Car":
                                        TeamBuilding_Car.setChecked(true);
                                        break;
                                    case "Vélo":
                                        TeamBuilding_Bike.setChecked(true);
                                        break;
                                    case "Train":
                                        TeamBuilding_Train.setChecked(true);
                                        break;
                                    case "Bus":
                                        TeamBuilding_Bus.setChecked(true);
                                        break;
                                }
                            }
                        }
                        if (getteambuildingCourse.getPlan() != null) {
                            if (getteambuildingCourse.getPlan().equals("Commission")) {
                                TeamBuilding_Commission.setChecked(true);
                            } else if (getteambuildingCourse.getPlan().equals("Abonnement")) {
                                TeamBuilding_Abonnement.setChecked(true);
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
            public void onFailure(Call<TeamBuildingDTO> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }


    public void setTeamBuilding()
    {
        ((RealMainPageActivity)getContext()).showprocess();

        TeamBuildingDTO.TeamBuildingCourse teamBuildingCourse = new TeamBuildingDTO.TeamBuildingCourse();

        checktext.clear();
        teamBuildingCourse.setCoach_Id(coachid_);
        teamBuildingCourse.setId(getteambuildingCourse.getId());
        teamBuildingCourse.setFilename(teambuilding_image.getText().toString().replace("/",""));
        teamBuildingCourse.setPhoto(imageString);
        teamBuildingCourse.setDescription(teambuilding_description.getText().toString());
        if(getteambuildingCourse.getEventdetails() != null && !getteambuildingCourse.getEventdetails().equals("")){
        teamBuildingCourse.setEventdetails(getteambuildingCourse.getEventdetails());
        }else {
            teamBuildingCourse.setEventdetails("-");
        }

        if(TeamBuilding_Abonnement.isChecked()){
            teamBuildingCourse.setPlan("Abonnement");
        }else if(TeamBuilding_Commission.isChecked()){
            teamBuildingCourse.setPlan("Commission");
        }

        if(TeamBuilding_Train.isChecked()){
            checktext.add("Train");
        }
        if(TeamBuilding_Bus.isChecked()){
            checktext.add("Bus");
        }
        if(TeamBuilding_Bike.isChecked()){
            checktext.add("Vélo");
        }
        if(TeamBuilding_Car.isChecked()){
            checktext.add("Car");
        }

        String transport = TextUtils.join(",", checktext);
        teamBuildingCourse.setMode_of_transport(transport);
        teamBuildingCourse.setPrice("");
        teamBuildingCourse.setPostalcode("");
        System.out.println("- --> " + new Gson().toJson(teamBuildingCourse));

        apiInterface.setteambuildingcourse(teamBuildingCourse).enqueue(new Callback<TeamBuildingDTO>() {
            @Override
            public void onResponse(@NonNull Call<TeamBuildingDTO> call, @NonNull Response<TeamBuildingDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    getTeamBuilding();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TeamBuildingDTO> call, Throwable t) {
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
        Picasso.get().load(url).fit().into(TeamBuilding_image_preview);
        TeamBuilding_image_preview.setVisibility(View.VISIBLE);
//        GlideApp.with(this).load(url)
//                .into(profile);
        TeamBuilding_image_preview.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(TeamBuilding_image_preview);
//        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
//                .into(profile);
        TeamBuilding_image_preview.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
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
                TeamBuildingFragment.this.openSettings();
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
                        teambuilding_image.setText("/"+file);
                    } else {
                        file =  filename;
                        teambuilding_image.setText("/"+file);
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

}