package com.tech.cloudnausor.ohmytennispro.fragment.myaccount;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.MyaccountGetData;
import com.tech.cloudnausor.ohmytennispro.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachUpdateResponse;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.MyAutoCompleteTextView;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.res.ColorStateList.valueOf;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAccFormThreeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAccFormThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccFormThreeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String PREFER_NAME = "Reg";
    private static final String ARG_PARAM2 = "param2";
    String edit_mode = "0";
    SingleTonProcess singleTonProcess;


//    CoachDetailsModel coachDetailsModel = new CoachDetailsModel();

    CoachDetailsModel coachDetailsModel3 = new CoachDetailsModel();



    private MyAutoCompleteTextView Form_Three_Payment;
    ApiInterface apiInterface2;
    TextInputLayout Form_Three_Bank_Name_error,Form_Three_Bank_Account_error,Form_Three_Bank_Code_error,Form_Three_Bank_City_error;
    TextInputEditText Form_Three_Bank_Name,Form_Three_Bank_Account,Form_Three_Bank_Code,Form_Three_Bank_City;
    Button Form_Three_Save,Modifier;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Button Payment_Save,Payment_Cancel;
    private CheckBox Payment_Check_One,Payment_Check_Two,Payment_Check_Three;
    ArrayList<String> paymentHolder = new ArrayList<>();
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyAccFormThreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccFormThreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccFormThreeFragment newInstance(String param1, String param2) {
        MyAccFormThreeFragment fragment = new MyAccFormThreeFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_myaccformthree = inflater.inflate(R.layout.fragment_my_acc_form_three, container, false);

        Form_Three_Payment = (MyAutoCompleteTextView)view_myaccformthree.findViewById(R.id.form_three_payment);
        Form_Three_Bank_Name= (TextInputEditText) view_myaccformthree.findViewById(R.id.form_three_bank_name);
        Form_Three_Bank_Account= (TextInputEditText) view_myaccformthree.findViewById(R.id.form_three_bank_account);
        Form_Three_Bank_Code= (TextInputEditText) view_myaccformthree.findViewById(R.id.form_three_bank_code);
        Form_Three_Bank_City= (TextInputEditText) view_myaccformthree.findViewById(R.id.form_three_bank_city);
        Form_Three_Bank_Name_error= (TextInputLayout) view_myaccformthree.findViewById(R.id.form_three_bank_name_error);
        Form_Three_Bank_Account_error= (TextInputLayout) view_myaccformthree.findViewById(R.id.form_three_bank_account_error);
        Form_Three_Bank_Code_error= (TextInputLayout) view_myaccformthree.findViewById(R.id.form_three_bank_code_error);
        Form_Three_Bank_City_error= (TextInputLayout) view_myaccformthree.findViewById(R.id.form_three_bank_city_error);
        Modifier = (Button)view_myaccformthree.findViewById(R.id.modifier_three);
        Payment_Check_One =(CheckBox) view_myaccformthree.findViewById(R.id.paymentcheck_1);
        Payment_Check_Two =(CheckBox) view_myaccformthree.findViewById(R.id.paymentcheck_2);
        Payment_Check_Three =(CheckBox) view_myaccformthree.findViewById(R.id.paymentcheck_3);

        Form_Three_Save = (Button) view_myaccformthree.findViewById(R.id.form_three_save);
        session = new SessionManagement(getContext());
        sharedPreferences = getContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getContext());
        apiInterface2 = ApiClient.getClient().create(ApiInterface.class);

        if(sharedPreferences.contains("IsMyEditString")){
            edit_sting = sharedPreferences.getString("IsMyEditString","");
        }
        if (sharedPreferences.contains("KEY_Coach_ID"))
        {
            coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");

        }
        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");

        }


        final String mode = sharedPreferences.getString("edit_mode","");
        String mode_one = sharedPreferences.getString("edit_mode_three","");


        if(mode.equals("false") && mode_one.equals("false")){
            edit_mode ="1";
            Modifier.setVisibility(View.GONE);
            EditTint();
            loadUpdate();
        }else{
            edit_mode ="0";
            Modifier.setVisibility(View.VISIBLE);
            previewTint();
            loadUpdate();
//            previewTint();
//            getPageData();
        }

//        if(sharedPreferences.contains("LoginObject")){
//            loginObject = sharedPreferences.getString("LoginObject","");
//            Gson gson = new Gson();
//            coachDetailsModel3 = gson.fromJson(loginObject,CoachDetailsModel.class);
//        }

//        if(edit_mode.equals("0")) {
//            previewTint();
//            loadUpdate();
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
                ((MyAccountHomeFragment)getParentFragment()).moreOne();
            }
        });

//        if(edit_sting.equals("0")) {
//            previewTint();
//        }else {
//            EditTint();
//        }
//        if(!session.checkEdit()) {
//            System.out.println("");
//            previewTint();
//
//        }else{            EditTint();
//
//        }

//        loadUpdate();

        Form_Three_Bank_Name.addTextChangedListener(new TextWatcher() {
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
                    Form_Three_Bank_Name_error.setErrorEnabled(true);
                    Form_Three_Bank_Name_error.setError("Indiquez Votre Nom de la banque.");
                }else {
                    Form_Three_Bank_Name_error.setErrorEnabled(false);
                    Form_Three_Bank_Name_error.setError(null);
                }
            }
        });
        Form_Three_Bank_Account.addTextChangedListener(new TextWatcher() {
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
                    Form_Three_Bank_Account_error.setErrorEnabled(true);
                    Form_Three_Bank_Account_error.setError("Indiquez Votre Numéro de compte.");
                }else {
                    Form_Three_Bank_Account_error.setErrorEnabled(false);
                    Form_Three_Bank_Account_error.setError(null);
                }
            }
        });
        Form_Three_Bank_Code.addTextChangedListener(new TextWatcher() {
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
                    Form_Three_Bank_Code_error.setErrorEnabled(true);
                    Form_Three_Bank_Code_error.setError("Indiquez Votre Code banque.");
                }else {
                    Form_Three_Bank_Code_error.setErrorEnabled(false);
                    Form_Three_Bank_Code_error.setError(null);
                }
            }
        });
        Form_Three_Bank_City.addTextChangedListener(new TextWatcher() {
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
                    Form_Three_Bank_City_error.setErrorEnabled(true);
                    Form_Three_Bank_City_error.setError("Indiquez Votre Ville de la banque.");
                }else {
                    Form_Three_Bank_City_error.setErrorEnabled(false);
                    Form_Three_Bank_City_error.setError(null);
                }
            }
        });

        Form_Three_Bank_Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_Three_Bank_Name.getText().toString();
                    if(editvalue.equals("")){
                        Form_Three_Bank_Name_error.setErrorEnabled(true);
                        Form_Three_Bank_Name_error.setError("Indiquez Votre Nom de la banque.");
                    }else {
                        Form_Three_Bank_Name_error.setErrorEnabled(false);
                        Form_Three_Bank_Name_error.setError(null);
                    }
                }
            }
        });
        Form_Three_Bank_Account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_Three_Bank_Account.getText().toString();


                    if(editvalue.equals("")){
                        Form_Three_Bank_Account_error.setErrorEnabled(true);
                        Form_Three_Bank_Account_error.setError("Indiquez Votre Numéro de compte.");
                    }else {
                        Form_Three_Bank_Account_error.setErrorEnabled(false);
                        Form_Three_Bank_Account_error.setError(null);
                    }
                }
            }
        });
        Form_Three_Bank_Code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_Three_Bank_Code.getText().toString();


                    if(editvalue.equals("")){
                        Form_Three_Bank_Code_error.setErrorEnabled(true);
                        Form_Three_Bank_Code_error.setError("Indiquez Votre Code banque.");
                    }else {
                        Form_Three_Bank_Code_error.setErrorEnabled(false);
                        Form_Three_Bank_Code_error.setError(null);
                    }
                }
            }
        });
        Form_Three_Bank_City.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_Three_Bank_Name.getText().toString();


                    if(editvalue.equals("")){
                        Form_Three_Bank_City_error.setErrorEnabled(true);
                        Form_Three_Bank_City_error.setError("Indiquez Votre Ville de la banque.");
                    }else {
                        Form_Three_Bank_City_error.setErrorEnabled(false);
                        Form_Three_Bank_City_error.setError(null);
                    }
                }
            }
        });

//        Form_Three_Payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                paymentHolder.clear();
//                Rect displayRectangle = new Rect();
//                Window window = getActivity().getWindow();
//                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
//                ViewGroup viewGroup = view.findViewById(android.R.id.content);
//                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dailog_payment, viewGroup, false);
//                dialogView.setMinimumWidth((int)(displayRectangle.width()* 1f));
//                builder.setView(dialogView);
//                final AlertDialog alertDialog = builder.create();
//                WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
//                wmlp.gravity = Gravity.TOP;
//                Payment_Save =(Button)dialogView.findViewById(R.id.payment_Ok);
//                Payment_Cancel  =(Button)dialogView.findViewById(R.id.payment_cancel);
//                Payment_Check_One =(CheckBox) dialogView.findViewById(R.id.paymentcheck_1);
//                Payment_Check_Two =(CheckBox) dialogView.findViewById(R.id.paymentcheck_2);
//                Payment_Check_Three =(CheckBox) dialogView.findViewById(R.id.paymentcheck_3);
//                Payment_Save.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(Payment_Check_One.isChecked()){
//                            paymentHolder.add(Payment_Check_One.getText().toString());
//                        }
//                        if(Payment_Check_Two.isChecked()){
//                            paymentHolder.add(Payment_Check_Two.getText().toString());
//                        }
//                        if(Payment_Check_Three.isChecked()){
//                            paymentHolder.add(Payment_Check_Three.getText().toString());
//                        }
//                        String s = TextUtils.join(",", paymentHolder);
//                        Form_Three_Payment.setText(s);
//                        alertDialog.dismiss();
//                    }
//                });
//                Payment_Cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog.dismiss();
//                    }
//                });
//
////            dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
//                alertDialog.show();
//            }
//        });

        Form_Three_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editor.putString("IsMyEditString","0").apply();
//                editor.commit();
                upadateLive();

//                startActivity(getActivity().getIntent());

            }
        });

        return view_myaccformthree;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void previewTint(){

        Form_Three_Payment.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Three_Bank_Name.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Three_Bank_Account.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Three_Bank_Code.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Three_Bank_City.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Three_Payment.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Three_Bank_Name.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Three_Bank_Account.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Three_Bank_Code.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Three_Bank_City.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Three_Save.setVisibility(View.GONE);
        Form_Three_Payment.setEnabled(false);
        Form_Three_Bank_Name.setEnabled(false);
        Form_Three_Bank_Account.setEnabled(false);
        Form_Three_Bank_Code.setEnabled(false);
        Form_Three_Bank_City.setEnabled(false);
        Payment_Check_One.setEnabled(false);
        Payment_Check_Two.setEnabled(false);
        Payment_Check_Three.setEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void EditTint(){
        Form_Three_Payment.setEnabled(true);
        Form_Three_Bank_Name.setEnabled(true);
        Form_Three_Bank_Account.setEnabled(true);
        Form_Three_Bank_Code.setEnabled(true);
        Form_Three_Bank_City.setEnabled(true);
        Payment_Check_One.setEnabled(true);
        Payment_Check_Two.setEnabled(true);
        Payment_Check_Three.setEnabled(true);
        Form_Three_Payment.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_Name.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_Account.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_Code.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_City.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Payment.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_Name.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_Account.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_Code.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Bank_City.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Three_Save.setVisibility(View.VISIBLE);

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void upadateLive(){
        ((RealMainPageActivity)getContext()).showprocess();
if(!Form_Three_Bank_Name.getText().toString().equals("") && !Form_Three_Bank_Account.getText().toString().equals("") && !Form_Three_Bank_Code.getText().toString().equals("") && !Form_Three_Bank_City.getText().toString().equals("")) {
    if(Payment_Check_One.isChecked() || Payment_Check_Two.isChecked() || Payment_Check_Three.isChecked()) {
        paymentHolder.clear();
        if (Payment_Check_One.isChecked()) {
            paymentHolder.add("Cheque");
        }
        if (Payment_Check_Two.isChecked()) {
            paymentHolder.add("VirementEnligne");
        }
        if (Payment_Check_Three.isChecked()) {
            paymentHolder.add("VirementBanque");
        }

        String s_pay = TextUtils.join(",", paymentHolder);
        System.out.println(" Successfully  --> 111 " +  Form_Three_Bank_Name.getText().toString() +" - "+ Form_Three_Bank_Code.getText().toString()+" - " + Form_Three_Bank_Account.getText().toString());
        String json = sharedPreferences.getString("MyObject","");
        coachDetailsModel3 = new Gson().fromJson(json,CoachDetailsModel.class);
        coachDetailsModel3.setCoach_Fname( coachDetailsModel3.getCoach_Fname());
        coachDetailsModel3.setCoach_Lname(coachDetailsModel3.getCoach_Lname());
        coachDetailsModel3.setCoach_Email(coachDetailsModel3.getCoach_Email());
        coachDetailsModel3.setCoach_Phone(coachDetailsModel3.getCoach_Phone());
        coachDetailsModel3.setCoach_Price(coachDetailsModel3.getCoach_Price());
        coachDetailsModel3.setCoach_PriceX10(coachDetailsModel3.getCoach_PriceX10());
        coachDetailsModel3.setCoach_Services(coachDetailsModel3.getCoach_Services());
        coachDetailsModel3.setCoach_transport(coachDetailsModel3.getCoach_transport());
        coachDetailsModel3.setCoach_City(coachDetailsModel3.getCoach_City());
//        coachDetailsModel3.setCoach_Image("base64");
        coachDetailsModel3.setCoach_Image(coachDetailsModel3.getCoach_Image());
        coachDetailsModel3.setCoach_Resume(coachDetailsModel3.getCoach_Resume());
//        coachDetailsModel3.setCoach_Image("");
//        coachDetailsModel3.setCoach_Resume("");
        coachDetailsModel3.setCoach_Description(coachDetailsModel3.getCoach_Description());
        coachDetailsModel3.setCoach_payment_type(s_pay);
        coachDetailsModel3.setCoach_Bank_Name(Form_Three_Bank_Name.getText().toString());
        coachDetailsModel3.setBranch_Code(Form_Three_Bank_Code.getText().toString());
        coachDetailsModel3.setCoach_Bank_ACCNum(Form_Three_Bank_Account.getText().toString());
        coachDetailsModel3.setCoach_Rayon(coachDetailsModel3.getCoach_Rayon());
        coachDetailsModel3.setCoach_Bank_City(Form_Three_Bank_City.getText().toString());

        coachDetailsModel3.setFacebookURL(coachDetailsModel3.getFacebookURL());
        coachDetailsModel3.setInstagramURL(coachDetailsModel3.getInstagramURL());
        coachDetailsModel3.setTwitterURL(coachDetailsModel3.getTwitterURL());

        Gson gson = new Gson();
        String coachDetaildsModel = gson.toJson(coachDetailsModel3);
        editor.putString("MyObject",coachDetaildsModel);
        editor.commit();
        String json1 = sharedPreferences.getString("MyObject","");
        coachDetailsModel3 = new Gson().fromJson(json1,CoachDetailsModel.class);

        Log.e(" Successfully  --> 12", new Gson().toJson(coachDetailsModel3));
        Log.e("image-->", coachDetailsModel3.getCoach_Image());
        Log.e("resume-->", coachDetailsModel3.getCoach_Resume());

//            Call<CoachUpdateResponse> req = apiInterface.getDetailedInsertCoach(coachDetailsModel.getCoach_Aviailability(),Form_One_First_Name.getText().toString(),Form_One_Second_Name.getText().toString(),
//                    Form_One_Email.getText().toString(),Form_One_Mobile.getText().toString(),coachDetailsModel.getCoach_Price(),coachDetailsModel.getCoach_PriceX10(),
//                    coachDetailsModel.getCoach_Services(),coachDetailsModel.getActive_City(),coachDetailsModel.getCoach_transport(),body,name1,coachDetailsModel.getCoach_Resume(),coachDetailsModel.getCoach_Description(),
//                    coachDetailsModel.getAvailability_StartDate(),coachDetailsModel.getAvailability_EndDate(),coachDetailsModel.getCoach_payment_type(),
//                    coachDetailsModel.getCoach_Bank_Name(),coachDetailsModel.getBranch_Code(),coachDetailsModel.getCoach_Bank_ACCNum(),coachDetailsModel.getCoach_Bank_City(),
//                    coachDetailsModel.getCoach_Resume());
        System.out.println(" Successfully  --> 12 " + new Gson().toJson(coachDetailsModel3));
        Call<CoachUpdateResponse> req = apiInterface2.getDetailedInsertCoach(coachDetailsModel3);
//        Call<CoachUpdateResponse> req = apiInterface2.getDetailedInsertCoach(coachDetailsModel3.getCoach_Fname(), coachDetailsModel3.getCoach_Lname(),
//                coachDetailsModel3.getCoach_Email(), coachDetailsModel3.getCoach_Phone(), coachDetailsModel3.getCoach_Price(), coachDetailsModel3.getCoach_PriceX10(),
//                coachDetailsModel3.getCoach_Services(), coachDetailsModel3.getCoach_transport(), coachDetailsModel3.getCoach_Image(), coachDetailsModel3.getCoach_Resume(),
//                coachDetailsModel3.getCoach_Description(),"Yes", s_pay,
//                Form_Three_Bank_Name.getText().toString(), Form_Three_Bank_Code.getText().toString(), Form_Three_Bank_Account.getText().toString(), coachDetailsModel3.getCoach_Rayon(), Form_Three_Bank_City.getText().toString(),
//                "coach");
        req.enqueue(new Callback<CoachUpdateResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<CoachUpdateResponse> call, Response<CoachUpdateResponse> response) {
                if (response.body().getIsSuccess().equals("true")) {
                    ((RealMainPageActivity)getContext()).dismissprocess();
                    previewTint();
                    loadUpdate();
                    Modifier.setVisibility(View.VISIBLE);
                    editor.putString("edit_mode","true");
                    editor.putString("edit_mode_one","true");
                    editor.putString("edit_mode_two","true");
                    editor.putString("edit_mode_three","true");
                    editor.commit();
                    Toast.makeText(getContext(),"Mis à jour avec succés",Toast.LENGTH_LONG).show();
                    ((MyAccountHomeFragment)getParentFragment()).moreOne();
//                        textView.setText("Uploaded Successfully!");
//                        textView.setTextColor(Color.BLUE);
//                        Log.e("response.body()",new Gson(response.body().toString()));
//                        System.out.println(" response.body().getCoachUpdateRetrunList().get(0).getCoach_Image() " + response.body().getCoachUpdateRetrunList().get(0).getCoach_Image());

                }else {
                    ((RealMainPageActivity)getContext()).dismissprocess();
                    System.out.println(" Successfully  --> 12 " + new Gson().toJson(response.body()));
                }

//                    Toast.makeText(getContext(), response.code() + " Uploaded Successfully! ", Toast.LENGTH_LONG).show();
                System.out.println(" Successfully  --> 12 " + new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<CoachUpdateResponse> call, Throwable t) {
//                    textView.setText("Uploaded Failed!");
//                    textView.setTextColor(Color.RED);
                System.out.println(" Successfully  --> 13 " + t.toString());
                ((RealMainPageActivity)getContext()).dismissprocess();

                Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }else {
        ((RealMainPageActivity)getContext()).dismissprocess();
        Toast.makeText(getContext(),"please select payment method",Toast.LENGTH_LONG).show();
    }
}else {
    ((RealMainPageActivity)getContext()).dismissprocess();
    Toast.makeText(getContext(),"Please enter all data",Toast.LENGTH_LONG).show();
}


    }

    public  void loadUpdate(){
        ((RealMainPageActivity)getContext()).showprocess();
        String json = sharedPreferences.getString("MyObject","");
        coachDetailsModel3 = new Gson().fromJson(json,CoachDetailsModel.class);


//        apiInterface2.getCoachDeatils(uPassword).enqueue(new Callback<CoachDetailsResponseData>() {
//            @Override
//            public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
//                assert response.body() != null;
//                if(response.body().getData() != null) {
//                    MyaccountGetData myaccountGetData = response.body().getData();
//                    coachDetailsModel3 = myaccountGetData.getCoachDetailsModelArrayList().get(0);


                    if(coachDetailsModel3.getCoach_payment_type() != null)
                        Form_Three_Payment.setText(coachDetailsModel3.getCoach_payment_type());
                    else
                        Form_Three_Payment.setText("-");
                    if(coachDetailsModel3.getCoach_Bank_Name() != null && !coachDetailsModel3.getCoach_Bank_Name().equals("null") && !coachDetailsModel3.getCoach_Bank_Name().equals(""))
                        Form_Three_Bank_Name.setText(coachDetailsModel3.getCoach_Bank_Name());
                    else
                        Form_Three_Bank_Name.setText("-");
                    if(coachDetailsModel3.getCoach_Bank_ACCNum() != null && !coachDetailsModel3.getCoach_Bank_ACCNum().equals("null") && !coachDetailsModel3.getCoach_Bank_ACCNum().equals(""))
                        Form_Three_Bank_Account.setText(coachDetailsModel3.getCoach_Bank_ACCNum() );
                    else
                        Form_Three_Bank_Account.setText("-");
                    if(coachDetailsModel3.getBranch_Code() != null && !coachDetailsModel3.getBranch_Code().equals("null") && !coachDetailsModel3.getBranch_Code().equals(""))
                        Form_Three_Bank_Code.setText(coachDetailsModel3.getBranch_Code());
                    else
                        Form_Three_Bank_Code.setText("-");
                    if(coachDetailsModel3.getCoach_Bank_City() != null && !coachDetailsModel3.getCoach_Bank_City().equals("null") && !coachDetailsModel3.getCoach_Bank_City().equals(""))
                        Form_Three_Bank_City.setText(coachDetailsModel3.getCoach_Bank_City());
                    else
                        Form_Three_Bank_City.setText("-");

                    if(coachDetailsModel3.getCoach_payment_type() != null){
                        String ass = coachDetailsModel3.getCoach_payment_type();
                        String[] stringArrayList = ass.split(",");
                        for(String s:stringArrayList){
                            switch (s) {
                                case "Cheque":
                                    Payment_Check_One.setChecked(true);
                                    break;
                                case "VirementEnligne":
                                    Payment_Check_Two.setChecked(true);
                                    break;
                                case "VirementBanque":
                                    Payment_Check_Three.setChecked(true);
                                    break;

                            }
                        }
                    }

        ((RealMainPageActivity)getContext()).dismissprocess();


                }

////                System.out.println("sys---> 1 " + response.body().getData().ge());
//            }
//            @Override
//            public void onFailure(Call<CoachDetailsResponseData> call, Throwable t) {
//                System.out.println(" sys---> "+t);
//            }
//        });



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



}
