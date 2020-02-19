package com.tech.cloudnausor.ohmytennispro.activity.forgot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennispro.activity.register.RegisterActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.response.ForgotResponseData;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {
    TextView GoLogin,F_User_Id_Error,AlertMessage;
    Button F_Cancel,F_Send_Mail,AlertPositive;
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText F_User_Id;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    SingleTonProcess singleTonProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_forgot);

        singleTonProcess = singleTonProcess.getInstance();

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        GoLogin =(TextView)findViewById(R.id.gofloginid);
        F_User_Id =(EditText)findViewById(R.id.f_user_id);
        F_Cancel =(Button) findViewById(R.id.f_cancel);
        F_Send_Mail = (Button) findViewById(R.id.f_find);
        F_User_Id_Error =(TextView)findViewById(R.id.f_user_id_error) ;

        GoLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        F_Cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        F_Send_Mail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                singleTonProcess.show(ForgotActivity.this);
                if(validationForgot()) {
                    apiInterface.getForgotFieldDetails(F_User_Id.getText().toString().trim()).enqueue(new Callback<ForgotResponseData>() {
                        @Override
                        public void onResponse(Call<ForgotResponseData> call, Response<ForgotResponseData> response) {
                            switch (response.body().getIsSuccess()){
                                case "true":
                                    singleTonProcess.show(ForgotActivity.this);
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ForgotActivity.this);
                                    LayoutInflater factory = ForgotActivity.this.getLayoutInflater();
                                    View view1  = factory.inflate(R.layout.alert_dialog, null);
                                    AlertMessage = (TextView)view1.findViewById(R.id.alertMessage);
                                    AlertPositive =(Button)view1.findViewById(R.id.alertPositive);
                                    AlertMessage.setText("Réinitialiser le lien du mot de passe, envoyer avec succès à l’email.");
                                    AlertPositive.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    dialogBuilder.setView(view1);
                                    AlertDialog alertDialog = dialogBuilder.create();
                                    alertDialog.show();
                                    break;
                                case "500":
                                    singleTonProcess.dismiss();
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                    break;
                                 default:
                                     singleTonProcess.dismiss();
                                     Toast.makeText(getApplicationContext(),"Check Network/Try after Sometime",Toast.LENGTH_LONG).show();
                                     break;
                            }
                        }
                        @Override
                        public void onFailure(Call<ForgotResponseData> call, Throwable t) {
                            singleTonProcess.dismiss();

                        }
                    });
                }else {
                    singleTonProcess.dismiss();
                }
            }
        });


    }

    public Boolean validationForgot(){
        if(F_User_Id.getText().toString().trim().matches(emailPattern) && F_User_Id.getText().toString().trim().length()>0){
            F_User_Id_Error.setVisibility(View.GONE);
            return true;
        }else{
            F_User_Id_Error.setVisibility(View.VISIBLE);
            F_User_Id_Error.setText(R.string.user_error);
            return false;
        }
    }
}
