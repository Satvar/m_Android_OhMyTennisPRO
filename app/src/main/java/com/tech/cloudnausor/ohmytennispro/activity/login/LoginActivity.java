package com.tech.cloudnausor.ohmytennispro.activity.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.HomeActivity;
import com.tech.cloudnausor.ohmytennispro.activity.forgot.ForgotActivity;
import com.tech.cloudnausor.ohmytennispro.activity.forgot.ResetPassword;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.activity.register.RegisterActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.dto.DeleteClubDTO;
import com.tech.cloudnausor.ohmytennispro.model.LoginModel;
import com.tech.cloudnausor.ohmytennispro.response.LoginResponseData;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.CustomEditText;
import com.tech.cloudnausor.ohmytennispro.utils.CustomToast;
import com.tech.cloudnausor.ohmytennispro.utils.DrawableClickListener;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.viewmodel.LoginViewModel;
import com.tech.cloudnausor.ohmytennispro.viewmodelfactory.LoginViewModelFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity
{
    TextView ForgotId,Goreigister;
            TextInputLayout L_User_Pass_Error, L_User_ID_Error;
    Button L_Button;
    SingleTonProcess singleTonProcess;
    TextInputEditText L_User_ID;
    String password_status = "show";
    CustomEditText L_User_Pass;
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    List<LoginModel> userdata;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Uri dataqq ;

    LinearLayout content;
    LoginModel loginModel;
    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);
//    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);
        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());
        singleTonProcess = singleTonProcess.getInstance();
        Intent intent22 = getIntent();
        String action = intent22.getAction();
        Uri data = intent22.getData();
        System.out.println("intent.getData-->" + data);
        URL url = null;
        HttpURLConnection con = null;
        if(data != null){
        try {
            url = new URL(data.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

//        ?id=Y2FsbWFuQHlvcG1haWwuY29t
//        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
//        if(sharedpreferences.getBoolean(
//        LoginStatus,false)){
//
//        }else{
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(intent);
//        }

        ArrayList<String> weeklist = new ArrayList<String>();


//        Calendar now = Calendar.getInstance();
//int j =31;
//for(int i = 0;i<7;i++) {
//    now.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
//    now.set(Calendar.MONTH, 11);//0- january ..4-May
//    now.set(Calendar.DATE, j);
//    if (now.get(Calendar.WEEK_OF_YEAR) != 1) {
//        System.out.println("Current week of " +
//                now);
//        System.out.println("Current week of month is : " +
//                now.get(Calendar.WEEK_OF_MONTH));
//        System.out.println("Current week of year is : " +
//                now.get(Calendar.WEEK_OF_YEAR));
//        System.out.println("less days : " + i);
//        break;
//    }else{
//        j = j-1;
//    }
//
//}
////
//        Calendar now1 = Calendar.getInstance();
//        now1.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
//        now1.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));//0- january ..4-May
//        now1.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
//        System.out.println("Current week of " +
//                now1);
//        System.out.println("Current week of month is : " +
//                now1.get(Calendar.WEEK_OF_MONTH));
//        System.out.println("Current week of year is : " +
//                now1.get(Calendar.WEEK_OF_YEAR));
//
//        for(int k = now1.get(Calendar.WEEK_OF_YEAR); k <= now.get(Calendar.WEEK_OF_YEAR);k++ ){
//            weeklist.add("Sermine "+k);
//            System.out.println(weeklist);
//        }
//
//        Calendar weekdata = Calendar.getInstance();
//        weekdata.set(Calendar.WEEK_OF_YEAR, 52);
//        weekdata.set(Calendar.YEAR, 2019);
//        int firstDayOfWeek = weekdata.getFirstDayOfWeek();
//        System.out.println("firstDayOfWeek---> "+firstDayOfWeek);
//        ArrayList<String> weekdates = new ArrayList<String>();
//        for (int k = firstDayOfWeek; k < firstDayOfWeek + 7; k++) {
//            weekdata.set(Calendar.DAY_OF_WEEK, k);
//            System.out.println(" weekdata.set(Calendar.DAY_OF_WEEK, k)---> "+k);
//
////            tosend_weekdates.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(weekdata.getTime()));
//            weekdates.add(new SimpleDateFormat("yyyy-MM-dd").format(weekdata.getTime()));
//            System.out.println("weekdates---> "+weekdates);
//        }

//        Calendar now = Calendar.getInstance();
//        now.set(Calendar.YEAR,2019);
//        now.set(Calendar.MONTH,11);
//        now.set(Calendar.DATE,31);
//        System.out.println("Current week of month is : " +
//                now.get(Calendar.WEEK_OF_MONTH));
//
//        System.out.println("Current week of year is : " +
//                now.get(Calendar.WEEK_OF_YEAR));




//        LocalDate startDate = LocalDate.of(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)+1, Calendar.getInstance().get(Calendar.DATE));
//        LocalDate endDate = LocalDate.of(2020, Month.JANUARY, 1);
//        long weeksInYear = ChronoUnit.WEEKS.between(startDate, endDate);

//        LocalDate firstMondayOfMonth = LocalDate.of(2019, 8, 1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
//        LocalDate lastDayOfMonth  = LocalDate.of(2019,8,31).with(TemporalAdjusters.lastDayOfMonth());
////        LocalDate lastSundayOfMonth = lastDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
//        long weeksBetweenDates = ChronoUnit.WEEKS.between(firstMondayOfMonth, lastDayOfMonth);

//        System.out.println("weeksBetweenDates--->"+weeksInYear);


        content =(LinearLayout)findViewById(R.id.content_shake);
        ForgotId = (TextView)findViewById(R.id.forgotid);
        Goreigister =(TextView)findViewById(R.id.goregisterid);
        L_Button =(Button)findViewById(R.id.l_button);
        L_User_ID =(TextInputEditText) findViewById(R.id.l_user_id);
        L_User_Pass =(CustomEditText) findViewById(R.id.l_user_pass);
        L_User_ID_Error =(TextInputLayout) findViewById(R.id.l_user_id_error);
        L_User_Pass_Error=(TextInputLayout)findViewById(R.id.l_user_pass_error);
        L_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


//        editor.putBoolean("myaccstatus",false);

        if(!session.checkLogin()){

            Intent intent23 = getIntent();
            String action1 = intent23.getAction();
             dataqq = intent23.getData();
//            http://172.107.175.10:4001/login?id=Y2FsbWFuQHlvcG1haWwuY29t

            if(dataqq != null) {
                String uriString = data.toString();
                if (uriString.startsWith("http://172.107.175.10:4001/login?id=")) {
                    String stt = uriString.replace("http://172.107.175.10:4001/login?id=", "");

                    byte[] datazz = Base64.decode(stt, Base64.DEFAULT);
                    String text = new String(datazz, StandardCharsets.UTF_8);

                    System.out.println("intent.getData-->" + text);

                    apiRequest.userVerification(text).enqueue(new Callback<DeleteClubDTO>() {
                        @Override
                        public void onResponse(Call<DeleteClubDTO> call, Response<DeleteClubDTO> response) {
                            if(response.body() != null){
                                if(response.body().getIsSuccess().equals("true")){
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteClubDTO> call, Throwable t) {

                        }
                    });
                }else  if(uriString.startsWith("http://172.107.175.10:4001/setnewpassword?hash=")){
                    Intent intent = new Intent(LoginActivity.this, ResetPassword.class);
                    String stt = uriString.replace("http://172.107.175.10:4001/setnewpassword?hash=","");
                    intent.putExtra("hashkey", stt);
                    dataqq  = null;
                    data = null;
                    uriString = "";
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }

        ForgotId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);

            }
        });

        Goreigister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

            L_User_Pass.setDrawableClickListener(new DrawableClickListener() {


                public void onClick(DrawablePosition target) {
                    switch (target) {
                        case RIGHT:
                            //Do something here
                            if(password_status.equals("show")){
                                password_status = "hide";
                            L_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT);
                            L_User_Pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off_black_24dp, 0);
                            }else if(password_status.equals("hide")){
                                password_status = "show";
                                L_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                L_User_Pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic__eye_black_24dp, 0);
                            }
                            break;

                        default:
                            break;
                    }
                }

            });

            L_User_ID.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(Objects.requireNonNull(L_User_ID.getText()).toString().trim().matches(emailPattern) && L_User_ID.getText().toString().trim().length()>0){
                        L_User_ID_Error.setErrorEnabled(false);

                    }else{
                        L_User_ID_Error.setErrorEnabled(true);
                        L_User_ID_Error.setError(getResources().getString(R.string.user_error));
                    }
                }
            });

            L_User_ID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(Objects.requireNonNull(L_User_ID.getText()).toString().trim().matches(emailPattern) && L_User_ID.getText().toString().trim().length()>0){
                        L_User_ID_Error.setErrorEnabled(false);

                    }else{
                        L_User_ID_Error.setErrorEnabled(true);
                        L_User_ID_Error.setError(getResources().getString(R.string.user_error));
                    }
                }
            });

            L_User_Pass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if( L_User_Pass.getText().toString().trim().length()>0){
                        L_User_Pass_Error.setErrorEnabled(false);

                    }else{
                        L_User_Pass_Error.setErrorEnabled(true);
                        L_User_Pass_Error.setError(getResources().getString(R.string.pass_error));
                    }
                }
            });

            L_User_Pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if( L_User_Pass.getText().toString().trim().length()>0){
                        L_User_Pass_Error.setErrorEnabled(false);

                    }else{
                        L_User_Pass_Error.setErrorEnabled(true);
                        L_User_Pass_Error.setError(getResources().getString(R.string.pass_error));
                    }
                }
            });

        L_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleTonProcess.show(LoginActivity.this);
                if(validation()) {
                    apiRequest.getLoginFieldDetails(L_User_ID.getText().toString().trim(), L_User_Pass.getText().toString().trim()).enqueue(new Callback<LoginResponseData>() {
                        @Override
                        public void onResponse(retrofit2.Call<LoginResponseData> call, Response<LoginResponseData> response) {
                            if(response.body() != null) {
                                if ("true".equals(response.body().getIsSuccess())) {
//                                     singleTonProcess.show(LoginActivity.this);
                                    loginModel = response.body().getData();
                                        System.out.println(new Gson().toJson(loginModel));
                                        if (loginModel.getRoleId().equals("2")) {
                                                editor.putString("KEY_Coach_ID", loginModel.getId());
                                                editor.putString("Email", loginModel.getEmail());
                                                editor.putBoolean("IsUserLogIn", true);
                                                editor.commit();
                                                CustomToast.makeText(getApplicationContext(),"Connectez-vous avec succès",CustomToast.LENGTH_LONG,CustomToast.TYPE_SUCCESS).show();
                                                 singleTonProcess.dismiss();

//                                                Toast.makeText(getApplicationContext(), "true " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(LoginActivity.this, RealMainPageActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                        } else {
                                             singleTonProcess.dismiss();

                                            CustomToast.makeText(getApplicationContext(),"Entrez le bon ID utilisateur",CustomToast.LENGTH_LONG,CustomToast.TYPE_WARNING).show();
//                                            Toast.makeText(getApplicationContext(),"Entrez le bon ID utilisateur", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                     singleTonProcess.dismiss();

                                    CustomToast.makeText(getApplicationContext(), response.body().getMessage(),CustomToast.LENGTH_LONG,CustomToast.TYPE_ERROR).show();

//                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }


//                                    case "201":
//                                        editor.putString("KEY_Coach_ID", response.body().getCoachlist().get(0).getUser_ID());
//                                        editor.putString("Email",response.body().getCoachlist().get(0).getUser_ID());
//                                        editor.putBoolean("IsUserLogIn",true);
//                                        editor.commit();
//                                        Intent intent = new Intent(LoginActivity.this, IndividualCourseHomeActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                        break;
//                                    case "500":
//                                        content.startAnimation(shake);
//                                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                                        break;
//                                    case "501":
//                                                        content.startAnimation(shake);
//                                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                                        break;
//                                    case "502":
//                                                        content.startAnimation(shake);
//                                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                                        break;

                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponseData> call, Throwable t) {
                             singleTonProcess.dismiss();

//                            CustomToast.makeText(getApplicationContext(), "",CustomToast.LENGTH_LONG,CustomToast.TYPE_ERROR);

                            Log.e("e_getLoginFieldDetails", t.toString());
                        }
                    });

                }else{

                }


//
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//                content.startAnimation(shake);

//if(validation()) {
//    apiRequest.getLoginFieldDetails(L_User_ID.getText().toString().trim(), L_User_Pass.getText().toString().trim()).enqueue(new Callback<LoginResponseData>() {
//        @Override
//        public void onResponse( Call<LoginResponseData> call, Response<LoginResponseData> response) {
//            System.out.println("testing--->"+response.body());
//            if(response.body() != null) {
//                System.out.println("testing--->"+response.body());
//                switch (response.body().getCode()) {
//                    case "200":
////                        if (response.body().getCoachlist() != null) {
////                            userdata = response.body().getCoachlist();
////                        }
//
//                        Gson gson = new Gson();
//                        String gSt = gson.toJson(response.body().getCoachlist().get(0));
//                        editor.putString("KEY_Coach_ID", response.body().getCoachlist().get(0).getCoach_ID());
//                        System.out.println("testing--->"+ response.body().getCoachlist().get(0).getCoach_ID());
//                        editor.putString("Email",response.body().getCoachlist().get(0).getCoach_Email());
//                        editor.putString("IsMyEditString","0");
//                        editor.putString("LoginObject",gSt);
//                        editor.putBoolean("IsUserLoggedIn",true);
//                        editor.commit();
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        break;
//                    case "500":
////                        content.startAnimation(shake);
//                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                        break;
//                    case "501":
////                        content.startAnimation(shake);
//                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                        break;
//                    case "502":
////                        content.startAnimation(shake);
//                        Toast.makeText(getApplicationContext(), response.body().getSuccess(), Toast.LENGTH_LONG).show();
//                        break;
//                        default:
//                            Toast.makeText(getApplicationContext(),"Check Network/Try after Sometime",Toast.LENGTH_LONG).show();
//                            break;
//                }
//            }
//        }
//        @Override
//        public void onFailure( Call<LoginResponseData> call,  Throwable t) {
//            System.out.println("testing--->"+t);
//            Log.e("e_getLoginFieldDetails", t.toString());
//        }
//    });
//
//
////    loginViewModel = ViewModelProviders.of(LoginActivity.this, new LoginViewModelFactory(LoginActivity.this.getApplication(), L_User_ID.getText().toString().trim(), L_User_Pass.getText().toString().trim())).get(LoginViewModel.class);
//
//
////    loginViewModel.getloginResponseDataLiveData().observe(LoginActivity.this, new Observer<LoginResponseData>() {
////        @Override
////        public void onChanged(LoginResponseData loginResponseData) {
////                if(!loginResponseData.getCode().equals("")){
////
////                if(loginResponseData.getCode().equals("200")){
////                    loginViewModel.getloginResponseDataLiveData().removeObservers(LoginActivity.this);
////                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
////                    startActivity(intent);
////                }else if(loginResponseData.getCode().equals("204")){
////                    loginViewModel.getloginResponseDataLiveData().removeObservers(LoginActivity.this);
////                    Toast.makeText(getApplicationContext(),loginResponseData.getSuccess(),Toast.LENGTH_LONG).show();
////                }else if(loginResponseData.getCode().equals("203")){
////                    loginViewModel.getloginResponseDataLiveData().removeObservers(LoginActivity.this);
////                    Toast.makeText(getApplicationContext(),loginResponseData.getSuccess(),Toast.LENGTH_LONG).show();
////                }
////
////
////            }
////
////        }
////    });
//}



//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//               if(validation()){
//                   Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_SHORT).show();
//               }else {
//                   Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
//               }

            }
        });
        }else{


            Intent intent = new Intent(LoginActivity.this, RealMainPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Boolean validation(){
        if(L_User_ID.getText().toString().trim().matches(emailPattern) && L_User_ID.getText().toString().trim().length()>0){
            L_User_ID_Error.setErrorEnabled(false);
            if(L_User_Pass.getText().toString().trim().length()>0){
                L_User_Pass_Error.setErrorEnabled(false);
                return true;
            }else{
                L_User_Pass_Error.setErrorEnabled(true);
                L_User_Pass_Error.setError(getResources().getString(R.string.pass_error));
                return false;
            }

        }else{
            L_User_ID_Error.setErrorEnabled(true);
            L_User_ID_Error.setError(getResources().getString(R.string.user_error));
            return false;
        }


    }

    public  Boolean loginVerification(){
        if(loginModel.getIsEmailConfirmed().equals("1")){
            if(loginModel.getIsActive().equals("1")){
                System.out.println("check-->"+"1");
                return true;
            }else {
                System.out.println("check-->"+"2");
                return false;
            }
        }else {
            System.out.println("check-->"+"3");
            return false;
        }
    }


}
