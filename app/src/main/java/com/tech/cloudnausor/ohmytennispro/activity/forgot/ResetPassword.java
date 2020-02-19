package com.tech.cloudnausor.ohmytennispro.activity.forgot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.activity.register.RegisterActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.dto.CourseMoreDetailsDTO;
import com.tech.cloudnausor.ohmytennispro.dto.DeleteClubDTO;
import com.tech.cloudnausor.ohmytennispro.response.LoginResponseData;
import com.tech.cloudnausor.ohmytennispro.utils.CustomEditText;
import com.tech.cloudnausor.ohmytennispro.utils.CustomToast;
import com.tech.cloudnausor.ohmytennispro.utils.DrawableClickListener;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    Button cancel,ok;
    EditText emailnewpassword ;
    CustomEditText new_password,psaaword;
    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);

    SingleTonProcess singleTonProcess;

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";

    private Pattern pattern;
    private Matcher matcher;
     String password_status = "hide",con_password_status = "hide";

    String hashkey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_reset_password);
        singleTonProcess = singleTonProcess.getInstance();
        cancel = (Button)findViewById(R.id.reset_cancel);
        ok = (Button)findViewById(R.id.reset_pass);
        psaaword = (CustomEditText) findViewById(R.id.newpassword);
        new_password = (CustomEditText) findViewById(R.id.newconpassword);
        emailnewpassword = (EditText) findViewById(R.id.emailnewpassword);
        pattern = Pattern.compile(PASSWORD_PATTERN);
        Intent intent22 = getIntent();
        String action = intent22.getAction();
        Uri data = intent22.getData();
        System.out.println("intent.getData-->" + data);
        if(data != null) {
            String uriString = data.toString();
            System.out.println("intent.uriString-->" + uriString);
            if (uriString.startsWith("http://172.107.175.10:4001/setnewpassword?hash=")) {
                hashkey = uriString.replace("http://172.107.175.10:4001/setnewpassword?hash=", "");
                byte[] data2 = Base64.decode(hashkey, Base64.DEFAULT);
                emailnewpassword.setText(new String(data2, StandardCharsets.UTF_8));
            }
        }

        if(getIntent().getStringExtra("hashkey") != null){
            hashkey = getIntent().getStringExtra("hashkey");
//            byte[] data2 = Base64.decode(hashkey, Base64.DEFAULT);
//            emailnewpassword.setText(new String(data2, StandardCharsets.UTF_8));
        }

        psaaword.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        if(password_status.equals("show")){
                            password_status = "hide";
                            psaaword.setInputType(InputType.TYPE_CLASS_TEXT);
                            psaaword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off_black_24dp, 0);
                        }else if(password_status.equals("hide")){
                            password_status = "show";
                            psaaword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            psaaword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic__eye_black_24dp, 0);
                        }
                        break;

                    default:
                        break;
                }
            }

        });

        new_password.setDrawableClickListener(new DrawableClickListener() {


            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        if(con_password_status.equals("show")){
                            con_password_status = "hide";
                            new_password.setInputType(InputType.TYPE_CLASS_TEXT);
                            new_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off_black_24dp, 0);
                        }else if(con_password_status.equals("hide")){
                            con_password_status = "show";
                            new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            new_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic__eye_black_24dp, 0);
                        }
                        break;

                    default:
                        break;
                }
            }

        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    ok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(vaild()){
        if(!psaaword.getText().toString().equals("") && !new_password.getText().toString().equals("")  ) {
            if (psaaword.getText().toString().equals(new_password.getText().toString()) ) {
                apiRequest.setRest(emailnewpassword.getText().toString(),hashkey, psaaword.getText().toString()).enqueue(new Callback<DeleteClubDTO>() {
                    @Override
                    public void onResponse(Call<DeleteClubDTO> call, Response<DeleteClubDTO> response) {
                        if (response.body() != null) {
                            if (response.body().getIsSuccess().equals("true")) {
                                Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<DeleteClubDTO> call, Throwable t) {

                    }
                });
            }else {
                Toast.makeText(getApplicationContext(),"Password mismatch", Toast.LENGTH_LONG).show();

            }
        }else {
            Toast.makeText(getApplicationContext(),"Enter the password", Toast.LENGTH_LONG).show();
        }
    }
    }

});

    }

   public Boolean vaild(){

       if(!emailnewpassword.getText().toString().trim().matches(emailPattern)){
           Toast.makeText(getApplicationContext(),getResources().getString(R.string.user_error),Toast.LENGTH_LONG).show();
            return false;
       }else if(!validate(new_password.getText().toString())){

           Toast.makeText(getApplicationContext(),getResources().getString(R.string.user_error),Toast.LENGTH_LONG).show();

           return false;

       }
       return true;

    }

    public boolean validate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

}
