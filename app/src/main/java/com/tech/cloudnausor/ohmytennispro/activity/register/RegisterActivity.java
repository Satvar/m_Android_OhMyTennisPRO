package com.tech.cloudnausor.ohmytennispro.activity.register;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.RegisterModel;
import com.tech.cloudnausor.ohmytennispro.model.postalCodeList;
import com.tech.cloudnausor.ohmytennispro.response.RegisterPostalCodeList;
import com.tech.cloudnausor.ohmytennispro.response.RegisterResponseData;
import com.tech.cloudnausor.ohmytennispro.utils.AutoCompleteDropDown;
import com.tech.cloudnausor.ohmytennispro.utils.CustomEditText;
import com.tech.cloudnausor.ohmytennispro.utils.DrawableClickListener;
import com.tech.cloudnausor.ohmytennispro.utils.MyAutoCompleteTextView;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.viewmodel.PostalViewModel;
import com.tech.cloudnausor.ohmytennispro.viewmodel.RegisterViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tech.cloudnausor.ohmytennispro.apicall.ApiClient.*;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout R_User_Name_Error,R_User_Id_Error,R_User_Pass_Error,R_User_C_Pass_Error,
            R_User_Name_nd_error,R_Code_Portal_Error;
    TextView GoLoginr,R_Spinner_Error;
    ImageView R_Name1_Icon,R_Name2_Icon,R_Email_Icon,R_Pass_Icon,R_Code_Icon,R_Number_Icon,R_Location_Icon;
    MyAutoCompleteTextView R_Spinner;
    String password_status = "show";
    String CityId = "",RoleID="2";
    CheckBox checkBox;
    TextInputEditText R_User_Name,R_User_Id,R_User_C_Pass,R_User_Name_Nd,R_Code_Portal;
    CustomEditText R_User_Pass;
    Button R_Button;
    RadioButton radio_one,radio_two;
    ArrayList<String> postal_name_list =new ArrayList<String>();
    ArrayAdapter adapter;
    RadioButton Civi_One,Civi_Two;
    RegisterModel registerModel = new RegisterModel();
    private ArrayList<postalCodeList> registerPostalCodeLists = new ArrayList<postalCodeList>();
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
    ArrayList<String> postal_code_list =new ArrayList<String>();
    PostalViewModel postalViewModel;
    RegisterViewModel registerViewModel;
    private ApiInterface apiRequest = getClient().create(ApiInterface.class);
    private Pattern pattern;
    private Matcher matcher;
    SingleTonProcess singleTonProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_register);
        singleTonProcess = singleTonProcess.getInstance();


        GoLoginr =(TextView)findViewById(R.id.gorloginid);
        checkBox =(CheckBox)findViewById(R.id.checkboc_id);
        pattern = Pattern.compile(PASSWORD_PATTERN);
        R_User_Name =(TextInputEditText)findViewById(R.id.r_user_name);
        R_User_Id =(TextInputEditText)findViewById(R.id.r_user_id);
        R_User_Pass =(CustomEditText) findViewById(R.id.r_user_pass);
        R_User_C_Pass =(TextInputEditText)findViewById(R.id.r_user_c_pass);
        R_Spinner =(MyAutoCompleteTextView)findViewById(R.id.r_spinner);
        R_User_Name_Nd =(TextInputEditText)findViewById(R.id.r_user_name_nd);
        R_Code_Portal =(TextInputEditText)findViewById(R.id.r_code_portal);
        radio_one =(RadioButton)findViewById(R.id.radio1);
        radio_two =(RadioButton)findViewById(R.id.radio2);

        R_User_Name_Error =(TextInputLayout)findViewById(R.id.r_user_name_error);
        R_User_Id_Error =(TextInputLayout)findViewById(R.id.r_user_id_error);
        R_User_Pass_Error =(TextInputLayout)findViewById(R.id.r_user_pass_error);
        R_User_C_Pass_Error =(TextInputLayout)findViewById(R.id.r_user_c_pass_error);
        R_Spinner_Error =(TextView)findViewById(R.id.r_spinner_error);
        R_User_Name_nd_error =(TextInputLayout)findViewById(R.id.r_user_name_nd_error);
        R_Code_Portal_Error =(TextInputLayout)findViewById(R.id.r_code_portal_error);
//        R_Civi_Error =(TextView)findViewById(R.id.r_civi_error);

        R_Name1_Icon = (ImageView) findViewById(R.id.r_name1_icon);
        R_Name2_Icon = (ImageView) findViewById(R.id.r_name2_icon);
        R_Pass_Icon = (ImageView) findViewById(R.id.r_password_icon);
        R_Location_Icon= (ImageView) findViewById(R.id.r_location_icon);
        R_Code_Icon = (ImageView) findViewById(R.id.r_code_icon);
        R_Number_Icon = (ImageView) findViewById(R.id.r_number_icon);
        R_Email_Icon = (ImageView) findViewById(R.id.r_email_icon);
        R_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        R_User_Pass.setDrawableClickListener(new DrawableClickListener() {

            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here
                        if(password_status.equals("show")){
                            password_status = "hide";
                            R_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT);
                            R_User_Pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off_black_24dp, 0);
                        }else if(password_status.equals("hide")){
                            password_status = "show";
                            R_User_Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            R_User_Pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic__eye_black_24dp, 0);
                        }
                        break;

                    default:
                        break;
                }
            }

        });
        R_Spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                if(!postal_name_list.isEmpty()){
                    R_Spinner.showDropDown();
                }else {
                    Toast.makeText(RegisterActivity.this,"No Data",Toast.LENGTH_LONG).show();
                }
            }
        });

        R_User_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (R_User_Name.getText().toString().trim().length() > 0) {
                    R_User_Name_Error.setErrorEnabled(false);

                } else {
                    R_User_Name_Error.setErrorEnabled(true);
                    R_User_Name_Error.setError(getResources().getString(R.string.user_name_error));
                }
            }
        });
        R_User_Id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (R_User_Id.getText().toString().trim().matches(LoginActivity.emailPattern) && R_User_Id.getText().toString().trim().length() > 0) {
                    R_User_Id_Error.setErrorEnabled(false);

                }else {
                    R_User_Id_Error.setErrorEnabled(true);
                    R_User_Id_Error.setError(getResources().getString(R.string.email_id_error));

                }
            }
        });
        R_User_C_Pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (R_User_C_Pass.getText().toString().trim().length() == 10) {
                    R_User_C_Pass_Error.setErrorEnabled(false);

                } else {
                    R_User_C_Pass_Error.setErrorEnabled(true);
                    R_User_C_Pass_Error.setError(getResources().getString(R.string.r_mobile_error));
                }
            }
        });
        R_User_Name_Nd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (R_User_Name_Nd.getText().toString().trim().length() > 0) {
                    R_User_Name_nd_error.setErrorEnabled(false);

                } else {
                    R_User_Name_nd_error.setErrorEnabled(true);
                    R_User_Name_nd_error.setError(getResources().getString(R.string.user_name_nd_error));
                }
            }
        });
        R_User_Pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validate(R_User_Pass.getText().toString())) {
                    R_User_Pass_Error.setErrorEnabled(false);

                } else {
                    R_User_Pass_Error.setErrorEnabled(true);
                    R_User_Pass_Error.setError(getResources().getString(R.string.r_password_error));

                }
            }
        });
        R_User_Pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (validate(R_User_Pass.getText().toString())) {
                    R_User_Pass_Error.setErrorEnabled(false);
                } else {
                    R_User_Pass_Error.setErrorEnabled(true);
                    R_User_Pass_Error.setError(getResources().getString(R.string.r_password_error));

                }

            }
        });

        R_User_Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (R_User_Name.getText().toString().trim().length() > 0) {
                    R_User_Name_Error.setErrorEnabled(false);

                } else {
                    R_User_Name_Error.setErrorEnabled(true);
                    R_User_Name_Error.setError(getResources().getString(R.string.user_name_error));
                }
            }
        });

        R_User_Id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (R_User_Id.getText().toString().trim().matches(LoginActivity.emailPattern) && R_User_Id.getText().toString().trim().length() > 0) {
                    R_User_Id_Error.setErrorEnabled(false);

                }else {
                    R_User_Id_Error.setErrorEnabled(true);
                    R_User_Id_Error.setError(getResources().getString(R.string.email_id_error));

                }
            }
        });

        R_User_C_Pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (R_User_C_Pass.getText().toString().trim().length() == 10) {
                    R_User_C_Pass_Error.setErrorEnabled(false);

                } else {
                    R_User_C_Pass_Error.setErrorEnabled(true);
                    R_User_C_Pass_Error.setError(getResources().getString(R.string.r_mobile_error));
                }

            }
        });

        R_User_Name_Nd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (R_User_Name_Nd.getText().toString().trim().length() > 0) {
                    R_User_Name_nd_error.setErrorEnabled(false);

                } else {
                    R_User_Name_nd_error.setErrorEnabled(true);
                    R_User_Name_nd_error.setError(getResources().getString(R.string.user_name_nd_error));
                }

            }
        });




        R_Code_Portal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String aa= charSequence.toString();
                if(aa.length() == 5){
                    System.out.println(aa);
                    singleTonProcess.show(RegisterActivity.this);
                    registerPostalCodeLists.clear();
                    postal_name_list.clear();
                    postal_code_list.clear();
                    apiRequest.getPostalField(aa).enqueue(new Callback<RegisterPostalCodeList>() {
                        @Override
                        public void onResponse(Call<RegisterPostalCodeList> call, Response<RegisterPostalCodeList> response) {
                            if(response.body() != null){

//                            List<postalCodeList> postalCodeDetails = response.body().getPostalDeatils();
                                if(response.body().getData() != null){
//                                    System.out.println("aa " + response.body().getData().getPostalCodeListArrayList().get(0).getId());
                               if(response.body().getData().getPostalCodeListArrayList() != null){
                                   R_Code_Portal_Error.setErrorEnabled(false);
                                    registerPostalCodeLists.addAll(response.body().getData().getPostalCodeListArrayList());
                                    for(int i =0;i<registerPostalCodeLists.size();i++){
                                        System.out.println("aa " + response.body().getData().getPostalCodeListArrayList().get(0).getId());

                                        postalCodeList p = registerPostalCodeLists.get(i);
                                        System.out.println("aa11 " + p.getId());

                                        if(!postal_name_list.contains(p.getLibelle_acheminement()))
                                            postal_name_list.add(p.getLibelle_acheminement());
                                        System.out.println("aa121 " + postal_name_list);
                                        adapter = new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.select_dialog_item, postal_name_list);
                                        R_Spinner.setAdapter(adapter);

                                        if(postal_name_list.size() != 0) {
                                            R_Spinner.setText(postal_name_list.get(0));
                                        }

                                        R_Code_Portal.setEnabled(true);
                                        if(!postal_code_list.contains(p.getId()))
                                            postal_code_list.add(p.getId());
                                    }
                                     singleTonProcess.dismiss();
                               }else {
                                    singleTonProcess.dismiss();
                                   R_Code_Portal_Error.setErrorEnabled(true);
                                   R_Code_Portal_Error.setError(getResources().getString(R.string.r_code_portal_errror));
                               }
//                                System.out.println(postal_name_list);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterPostalCodeList> call, Throwable t) {
                             singleTonProcess.dismiss();
                            System.out.println("error 2 "+ t);

                        }
                    });

//                postalViewModel = ViewModelProviders.of(RegisterActivity.this,new postalViewModelFactory(RegisterActivity.this.getApplication(),aa)).get(PostalViewModel.class);
//                postalViewModel.getPostalCodeListLiveData().observe(RegisterActivity.this, new Observer<RegisterPostalCodeList>() {
//                    @Override
//                    public void onChanged(RegisterPostalCodeList postalCodeList) {
//                        if(postalCodeList != null){
//                            List<postalCodeList> postalCodeDetails = postalCodeList.getPostalDeatils();
////                            if(postalCodeDetails != null){
////                                registerPostalCodeLists.addAll(postalCodeDetails);
////                                for(int i =0;i<registerPostalCodeLists.size();i++){
////                                    postalCodeList p = registerPostalCodeLists.get(i);
////                                    postal_name_list.add(p.getPostalCity());
////                                }
////                                System.out.println(postal_name_list);
////                            }
//                        }
//                    }
//                });

                    List<String> a = postal_name_list;

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        R_Button = (Button)findViewById(R.id.r_button);

        GoLoginr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        checkBox.setText(Html.fromHtml("<a>En vous connectant, vous acceptez les <font color='#e75b00'>conditions d'utilisation</font> de l'application mobile OhMyTennis ainsi que la politique de  confidentialit'e accessible dans les <font color='#e75b00'>mentions l'egales</font></a>"));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Dialog dialog = new Dialog(RegisterActivity.this);
//                dialog.setContentView(R.layout.checkboxdialog);
//                dialog.show();
            }
        });

        R_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleTonProcess.show(RegisterActivity.this);
                if(validateRegistor()){
                    if(radio_one.isChecked() || radio_two.isChecked()) {
                        String radio_value = "";
                        if (radio_one.isChecked()) {
                            radio_value = "female";
                        } else {
                            radio_value = "male";
                        }
                        if(postal_name_list.contains(R_Spinner.getText().toString())){
                            int z = postal_name_list.indexOf(R_Spinner.getText().toString());
                            CityId = postal_code_list.get(z);
                        }
                        apiRequest.getRegisterFieldDetails(radio_value,R_User_Name.getText().toString(),
                                R_User_Name_Nd.getText().toString(), R_User_Id.getText().toString(), R_User_Pass.getText().toString(), R_Code_Portal.getText().toString(),
                                R_Spinner.getText().toString(), R_User_C_Pass.getText().toString(),CityId,RoleID).enqueue(new Callback<RegisterResponseData>() {
                            @Override
                            public void onResponse(Call<RegisterResponseData> call, Response<RegisterResponseData> response) {
                                if (response.body() != null) {
                                    switch (response.body().getIsSuccess()) {
                                        case "true":
                                                 singleTonProcess.dismiss();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(getApplicationContext(), "L'entraîneur s'inscrit avec succès", Toast.LENGTH_LONG).show();
                                            break;
//                                        case "200":
//                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                            startActivity(intent);
//                                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                                            break;
//                                        case "500":
//                                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                                            break;
                                        default:
                                             singleTonProcess.dismiss();

                                             System.out.println(" register check---> "+new Gson().toJson(response.body()));

                                            Toast.makeText(getApplicationContext(),response.body().getMessage() , Toast.LENGTH_LONG).show();
                                            break;
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterResponseData> call, Throwable t) {
                                Log.e("e_getRegisterFieldDetai", t.toString());

                            }
                        });


                    }else {
                         singleTonProcess.dismiss();
                        Toast.makeText(RegisterActivity.this,"Sélectionner le civilité",Toast.LENGTH_LONG).show();
                    }

//                registerViewModel = ViewModelProviders.of(RegisterActivity.this,new RegisterViewModelFactory(RegisterActivity.this.getApplication(),R_User_Name.getText().toString(),
//                        R_User_Name_Nd.getText().toString(),R_User_Id.getText().toString(),R_User_Pass.getText().toString(),R_Code_Portal.getText().toString(),
//                        R_Spinner.getText().toString(),R_User_C_Pass.getText().toString())).get(RegisterViewModel.class);
//                registerViewModel.getRegisterResponseDataLiveData().observe(RegisterActivity.this, new Observer<RegisterResponseData>() {
//                    @Override
//                    public void onChanged(RegisterResponseData registerResponseData) {
//                        if(registerResponseData != null){
//                            String registerResponseDataStatus = registerResponseData.getStatus();
////                                String registerResponseDatadata = registerResponseData.getData();
//                            String registerResponseDataMessage = registerResponseData.getMessage();
//
//                            if(registerResponseDataStatus.equals("200")){
//
//
//                            }
//                            System.out.println("registerResponseDataStatus --->"  +registerResponseDataStatus+"   "+"    "+registerResponseDataMessage);
//                        }
//                    }
//                });
                }else {
                     singleTonProcess.dismiss();
                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.M)
    public Boolean validateRegistor() {
        if (R_User_Name.getText().toString().trim().length() > 0) {
            R_User_Name_Error.setErrorEnabled(false);
            if (R_User_Name_Nd.getText().toString().trim().length() > 0) {
                R_User_Name_nd_error.setErrorEnabled(false);
                if (R_User_C_Pass.getText().toString().trim().length() == 10) {
                    R_User_C_Pass_Error.setErrorEnabled(false);
                    if (R_User_Id.getText().toString().trim().matches(LoginActivity.emailPattern) && R_User_Id.getText().toString().trim().length() > 0) {
                        R_User_Id_Error.setErrorEnabled(false);
                        if (validate(R_User_Pass.getText().toString()) && R_User_Pass.getText().toString().trim().length() >= 8) {
                            R_User_Pass_Error.setErrorEnabled(false);
                            if (R_Code_Portal.getText().toString().trim().length() > 0) {
                                R_Code_Portal_Error.setErrorEnabled(false);
                                if (R_Spinner.getText().toString().trim().length() > 0) {
                                    R_Spinner_Error.setVisibility(View.GONE);
                                    if(checkBox.isChecked()){
                                        return true;
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Accepter Termes et conditions",Toast.LENGTH_LONG).show();
                                        return false;
                                    }

                                } else {
                                    R_Spinner_Error.setVisibility(View.VISIBLE);
                                    R_Spinner_Error.setText(R.string.spinner_error);
                                    return false;
                                }
                            } else {
                                R_Code_Portal_Error.setErrorEnabled(true);
                                R_Code_Portal_Error.setError(getResources().getString(R.string.r_code_portal_errror));
                                return false;
                            }
                        } else {
                            R_User_Pass_Error.setErrorEnabled(true);
                            R_User_Pass_Error.setError(getResources().getString(R.string.r_password_error));
                            return false;
                        }
                    }else {
                        R_User_Id_Error.setErrorEnabled(true);
                        R_User_Id_Error.setError(getResources().getString(R.string.email_id_error));
                        return false;
                    }
                } else {
                    R_User_C_Pass_Error.setErrorEnabled(true);
                    R_User_C_Pass_Error.setError(getResources().getString(R.string.r_mobile_error));
                    return false;
                }
            } else {
                R_User_Name_nd_error.setErrorEnabled(true);
                R_User_Name_nd_error.setError(getResources().getString(R.string.user_name_nd_error));
                return false;
            }
        } else {
            R_User_Name_Error.setErrorEnabled(true);
            R_User_Name_Error.setError(getResources().getString(R.string.user_name_error));
            return false;
        }
    }

    public void UpdateUI(RegisterPostalCodeList postalCodeList ) {

    }

    public boolean validate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

}
