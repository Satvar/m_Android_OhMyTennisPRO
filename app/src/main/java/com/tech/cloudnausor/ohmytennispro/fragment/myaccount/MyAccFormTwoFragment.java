package com.tech.cloudnausor.ohmytennispro.fragment.myaccount;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.shockwave.pdfium.PdfDocument;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.MyaccountGetData;
import com.tech.cloudnausor.ohmytennispro.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachUpdateResponse;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.FilePath;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.res.ColorStateList.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAccFormTwoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAccFormTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 */
//
//ViewPager.OnPageChangeListener, Loader.OnLoadCompleteListener,
//        OnPageErrorListener, OnPageChangeListener, OnLoadCompleteListener
public class MyAccFormTwoFragment extends Fragment   {

    public static final String UPLOAD_URL = "http://internetfaqs.net/AndroidPdfUpload/upload.php";


    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;


    //Uri to store the image uri
    private Uri filePath;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String PREFER_NAME = "Reg";
    private static final String ARG_PARAM2 = "param2";
    CoachDetailsModel coachDetailsModel = new CoachDetailsModel();
    CoachDetailsModel coachDetailsModel2  = new CoachDetailsModel();
    String base64pdfstring = "";
    TextInputLayout Form_Two_Service_error,Form_Two_Ray_error,Form_Two_Prive_Hr_error,Form_Two_Price_Ten_Hr_error,Form_Two_Transport_error;
    TextInputEditText Form_Two_Ray,Form_Two_Prive_Hr,Form_Two_Price_Ten_Hr;
    EditText Form_Two_Service,Form_Two_Transport;
    Button Form_Two_Cv1,Form_Two_Cv2;
    Button Form_Two_Save_and_Continue,Service_cancel,Service_ok,Transport_cancel,Transport_ok,Modifier;
    CheckBox Servicecheck_1,Servicecheck_2,Servicecheck_3,Servicecheck_4,Servicecheck_5,Servicecheck_6,Servicecheck_7;
    CheckBox Transportcheck_1,Transportcheck_2,Transportcheck_3,Transportcheck_4;
    ArrayList<String> serviceHolder = new ArrayList<>();
    ArrayList<String> transportHolder = new ArrayList<>();
    TextView rayonError;
    int intvalue = 0;
    SingleTonProcess singleTonProcess;


    ApiInterface apiInterface1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int pageNumber = 0;
    String edit_mode = "0";


    private String pdfFileName;
    private PDFView pdfView;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;



    private OnFragmentInteractionListener mListener;

    public MyAccFormTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccFormTwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccFormTwoFragment newInstance(String param1, String param2) {
        MyAccFormTwoFragment fragment = new MyAccFormTwoFragment();
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

        View view_myaccformtwo = inflater.inflate(R.layout.fragment_my_acc_form_two, container, false);
        Form_Two_Cv1 =(Button) view_myaccformtwo.findViewById(R.id.form_two_cv1);
        Form_Two_Cv2=(Button) view_myaccformtwo.findViewById(R.id.form_two_cv2);
        Modifier = (Button)view_myaccformtwo.findViewById(R.id.modifier_two);
        Form_Two_Service=(EditText) view_myaccformtwo.findViewById(R.id.form_two_service);
        Form_Two_Ray=(TextInputEditText)view_myaccformtwo.findViewById(R.id.form_two_ray);
        Form_Two_Prive_Hr=(TextInputEditText)view_myaccformtwo.findViewById(R.id.form_two_prive_hr);
        Form_Two_Price_Ten_Hr=(TextInputEditText)view_myaccformtwo.findViewById(R.id.form_two_price_ten_hr);
        Form_Two_Ray_error=(TextInputLayout) view_myaccformtwo.findViewById(R.id.form_two_ray_error);
        Form_Two_Prive_Hr_error=(TextInputLayout)view_myaccformtwo.findViewById(R.id.form_two_prive_hr_error);
        Form_Two_Price_Ten_Hr_error=(TextInputLayout)view_myaccformtwo.findViewById(R.id.form_two_price_ten_hr_error);
        Form_Two_Transport=(EditText)view_myaccformtwo.findViewById(R.id.form_two_transport);
        Form_Two_Save_and_Continue=(Button) view_myaccformtwo.findViewById(R.id.form_two_save_and_continue);
        rayonError =(TextView)view_myaccformtwo.findViewById(R.id.rayonError) ;
        Servicecheck_1 =(CheckBox)view_myaccformtwo.findViewById(R.id.servicecheck_1);
        Servicecheck_2 =(CheckBox)view_myaccformtwo.findViewById(R.id.servicecheck_2);
        Servicecheck_3 =(CheckBox)view_myaccformtwo.findViewById(R.id.servicecheck_3);
        Servicecheck_4 =(CheckBox)view_myaccformtwo.findViewById(R.id.servicecheck_4);
        Servicecheck_5 =(CheckBox)view_myaccformtwo.findViewById(R.id.servicecheck_5);
        Servicecheck_6 =(CheckBox)view_myaccformtwo.findViewById(R.id.servicecheck_6);
        Servicecheck_7 =(CheckBox)view_myaccformtwo.findViewById(R.id.servicecheck_7);
        Transportcheck_1 =(CheckBox)view_myaccformtwo.findViewById(R.id.transportcheck_1);
        Transportcheck_2 =(CheckBox)view_myaccformtwo.findViewById(R.id.transportcheck_2);
        Transportcheck_3 =(CheckBox)view_myaccformtwo.findViewById(R.id.transportcheck_3);
        Transportcheck_4 =(CheckBox)view_myaccformtwo.findViewById(R.id.transportcheck_4);
        apiInterface1 = ApiClient.getClient().create(ApiInterface.class);

        sharedPreferences = getContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getContext());

        final String mode = sharedPreferences.getString("edit_mode","");
        String mode_one = sharedPreferences.getString("edit_mode_two","");


        if(mode.equals("false") && mode_one.equals("false")){
            edit_mode ="1";
            Modifier.setVisibility(View.GONE);
            loadUpdata();
            EditTint();
        }else{
            edit_mode ="0";
            Modifier.setVisibility(View.VISIBLE);
            previewTint();
            loadUpdata();
//            previewTint();
//            getPageData();
        }

        if(sharedPreferences.contains("LoginObject")){
            System.out.println("LoginObject--->");
            loginObject = sharedPreferences.getString("LoginObject","");
            Gson gson = new Gson();
            coachDetailsModel2 = gson.fromJson(loginObject,CoachDetailsModel.class);
        }


        if (sharedPreferences.contains("KEY_Coach_ID"))
        {
            coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");

        }
        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");

        }

        if(sharedPreferences.contains("IsMyEditString")){
            edit_sting = sharedPreferences.getString("IsMyEditString","");
        }



//        if(edit_sting.equals("0")) {
//            previewTint();
//        }else {
//            EditTint();
//
//        }
//        if(!session.checkEdit()) {
//            previewTint();
//
//        }else{            EditTint();
//
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

        Form_Two_Cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission();
          /*      initDialog();
                launchPicker();

                */


                showFileChooser();
            }
        });

        Form_Two_Save_and_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMultipart();
                Modifier.setVisibility(View.VISIBLE);
//                editor.putString("IsMyEditString","0").apply();
//                editor.commit();
//                startActivity(getActivity().getIntent());

            }
        });
        // Inflate the layout for this fragment
//        Form_Two_Service.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Form_Two_Service.isClickable()){
//                Rect displayRectangle = new Rect();
//                Window window = getActivity().getWindow();
//                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
//                ViewGroup viewGroup = view.findViewById(android.R.id.content);
//                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dailog_service, viewGroup, false);
//                dialogView.setMinimumWidth((int)(displayRectangle.width()* 1f));
////            dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
//                builder.setView(dialogView);
//                final AlertDialog alertDialog = builder.create();
//                WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
//                wmlp.gravity = Gravity.TOP;
//                    Servicecheck_1 =(CheckBox)dialogView.findViewById(R.id.servicecheck_1);
//                    Servicecheck_2 =(CheckBox)dialogView.findViewById(R.id.servicecheck_2);
//                    Servicecheck_3 =(CheckBox)dialogView.findViewById(R.id.servicecheck_3);
//                    Servicecheck_4 =(CheckBox)dialogView.findViewById(R.id.servicecheck_4);
//                    Servicecheck_5 =(CheckBox)dialogView.findViewById(R.id.servicecheck_5);
//                    Servicecheck_6 =(CheckBox)dialogView.findViewById(R.id.servicecheck_6);
//                    Servicecheck_7 =(CheckBox)dialogView.findViewById(R.id.servicecheck_7);
//                    Service_cancel =(Button) dialogView.findViewById(R.id.service_cancel);
//                    Service_ok =(Button) dialogView.findViewById(R.id.service_ok);
//                    Service_ok.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(Servicecheck_1.isChecked()){
//                                serviceHolder.add(Servicecheck_1.getText().toString());
//                            }
//                            if(Servicecheck_2.isChecked()){
//                                serviceHolder.add(Servicecheck_2.getText().toString());
//                            }
//                            if(Servicecheck_3.isChecked()){
//                                serviceHolder.add(Servicecheck_3.getText().toString());
//                            }
//                            if(Servicecheck_4.isChecked()){
//                                serviceHolder.add(Servicecheck_4.getText().toString());
//                            }
//                            if(Servicecheck_5.isChecked()){
//                                serviceHolder.add(Servicecheck_5.getText().toString());
//                            }
//                            if(Servicecheck_6.isChecked()){
//                                serviceHolder.add(Servicecheck_6.getText().toString());
//                            }
//                            if(Servicecheck_7.isChecked()){
//                                serviceHolder.add(Servicecheck_7.getText().toString());
//                            }
//                            String s = TextUtils.join(",", serviceHolder);
//                            Form_Two_Service.setText(s);
//                            alertDialog.dismiss();
//                        }
//                    });
//                    Service_cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//                        }
//                    });
//
//
//                    alertDialog.show();
//            }
//            }
//        });

//        Form_Two_Transport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Form_Two_Transport.isClickable()){
//                    Rect displayRectangle = new Rect();
//                    Window window = getActivity().getWindow();
//                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
//                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
//                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dailog_transport, viewGroup, false);
//                    dialogView.setMinimumWidth((int)(displayRectangle.width()* 1f));
////            dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
//                    builder.setView(dialogView);
//                    final AlertDialog alertDialog = builder.create();
//                    WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
//                    wmlp.gravity = Gravity.TOP;
//                    Transportcheck_1 =(CheckBox)dialogView.findViewById(R.id.transportcheck_1);
//                    Transportcheck_2 =(CheckBox)dialogView.findViewById(R.id.transportcheck_2);
//                    Transportcheck_3 =(CheckBox)dialogView.findViewById(R.id.transportcheck_3);
//                    Transportcheck_4 =(CheckBox)dialogView.findViewById(R.id.transportcheck_4);
//
//                    Transport_cancel =(Button) dialogView.findViewById(R.id.transport_cancel);
//                    Transport_ok =(Button) dialogView.findViewById(R.id.transport_ok);
//                    Transport_ok.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(Transportcheck_1.isChecked()){
//                                transportHolder.add(Transportcheck_1.getText().toString());
//                            }
//                            if(Transportcheck_2.isChecked()){
//                                transportHolder.add(Transportcheck_2.getText().toString());
//                            }
//                            if(Transportcheck_3.isChecked()){
//                                transportHolder.add(Transportcheck_3.getText().toString());
//                            }
//                            if(Transportcheck_4.isChecked()){
//                                transportHolder.add(Transportcheck_4.getText().toString());
//                            }
//                            String s = TextUtils.join(",", transportHolder);
//                            Form_Two_Transport.setText(s);
//                            alertDialog.dismiss();
//                        }
//                    });
//                    Transport_cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            alertDialog.dismiss();
//                        }
//                    });
//                    alertDialog.show();
//                }
//            }
//        });


//        if(edit_mode.equals("0")) {
//            previewTint();
//            loadUpdata();
//        }else {
//            EditTint();
//        }
        Form_Two_Cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dwldsPath = new File("/storage/emulated/0/Download/" + coachDetailsModel2.getCoach_Fname()+".pdf");
                byte[] pdfAsBytes = Base64.decode(base64pdfstring, 0);
                Log.d("data", "onActivityResult: pdfAsBytes size="+pdfAsBytes.length);
                FileOutputStream os;
                try {
                    os = new FileOutputStream(dwldsPath, false);
                    os.write(pdfAsBytes);
                    os.flush();
                    os.close();
                    Toast.makeText(getActivity(),"Téléchargement réussi.",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("jhsjkgskjdgjksdg"+e);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("jhsjkgskjdfhdrfhdhfdgjksdg"+e);

                }

            }
        });

        Form_Two_Ray.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editvalue = editable.toString();
                if(!editvalue.equals("")){
                    intvalue =  Integer.parseInt(editvalue);
                }
                if(editvalue.equals("")){
                    rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                    rayonError.setVisibility(View.VISIBLE);
//                    Form_Two_Ray_error.setErrorEnabled(true);
//                    Form_Two_Ray_error.setError("Indiquez Votre Rayon.");
                }else if(intvalue < 1  ){
                    rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                    rayonError.setVisibility(View.VISIBLE);
//                    Form_Two_Ray_error.setErrorEnabled(true);
//                    Form_Two_Ray_error.setError("Indiquez Votre Nombre De Personnes.");
                }else if(intvalue > 20){
                    rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                    rayonError.setVisibility(View.VISIBLE);
//                    Form_Two_Ray_error.setErrorEnabled(true);
//                    Form_Two_Ray_error.setError("Indiquez Votre Nombre De Personnes.");
                }else {
                    rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                    rayonError.setVisibility(View.GONE);
                    Form_Two_Ray_error.setErrorEnabled(false);
                    Form_Two_Ray_error.setError(null);
                }
            }
        });
        Form_Two_Prive_Hr.addTextChangedListener(new TextWatcher() {
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
                    Form_Two_Prive_Hr_error.setErrorEnabled(true);
                    Form_Two_Prive_Hr_error.setError("Indiquez Votre Prix.");
                }else {
                    Form_Two_Prive_Hr_error.setErrorEnabled(false);
                    Form_Two_Prive_Hr_error.setError(null);
                }
            }
        });

        Form_Two_Price_Ten_Hr.addTextChangedListener(new TextWatcher() {
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
                    Form_Two_Price_Ten_Hr_error.setErrorEnabled(true);
                    Form_Two_Price_Ten_Hr_error.setError("Indiquez Votre Prix.");
                }else {
                    Form_Two_Price_Ten_Hr_error.setErrorEnabled(false);
                    Form_Two_Price_Ten_Hr_error.setError(null);
                }
            }
        });

        Form_Two_Ray.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_Two_Ray.getText().toString();
                    if(!editvalue.equals("")){
                        intvalue =  Integer.parseInt(editvalue);
                    }
                    if(editvalue.equals("")){
                        rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                        rayonError.setVisibility(View.VISIBLE);
//                        Form_Two_Ray_error.setErrorEnabled(true);
//                        Form_Two_Ray_error.setError("Indiquez Votre Rayon. Rayon(1-20 mile)");
                    }else if(intvalue < 1  ){
                        rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                        rayonError.setVisibility(View.VISIBLE);
//                        Form_Two_Ray_error.setErrorEnabled(true);
//                        Form_Two_Ray_error.setError("Indiquez Votre Rayon.Rayon(1-20 mile)");
                    }else if(intvalue > 20){
                        rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                        rayonError.setVisibility(View.VISIBLE);
//                        Form_Two_Ray_error.setErrorEnabled(true);
//                        Form_Two_Ray_error.setError("Indiquez Votre Rayon.Rayon(1-20 mile)");
                    }else {
                        rayonError.setText("Indiquez Votre Rayon. Rayon(1-20 mile)");
                        rayonError.setVisibility(View.GONE);
                        Form_Two_Ray_error.setErrorEnabled(false);
                        Form_Two_Ray_error.setError(null);
                    }
                }
            }
        });

        Form_Two_Prive_Hr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_Two_Prive_Hr.getText().toString();
                    if(editvalue.equals("")){
                        Form_Two_Prive_Hr_error.setErrorEnabled(true);
                        Form_Two_Prive_Hr_error.setError("Indiquez Votre Prix.");
                    }else {
                        Form_Two_Prive_Hr_error.setErrorEnabled(false);
                        Form_Two_Prive_Hr_error.setError(null);
                    }
                }
            }
        });

        Form_Two_Price_Ten_Hr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String editvalue = Form_Two_Price_Ten_Hr.getText().toString();
                    if(editvalue.equals("")){
                        Form_Two_Price_Ten_Hr_error.setErrorEnabled(true);
                        Form_Two_Price_Ten_Hr_error.setError("Indiquez Votre Prix.");
                    }else {
                        Form_Two_Price_Ten_Hr_error.setErrorEnabled(false);
                        Form_Two_Price_Ten_Hr_error.setError(null);
                    }
                }
            }
        });
        return view_myaccformtwo;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void previewTint(){
        Form_Two_Cv1.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Two_Cv2.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Two_Service.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Two_Ray.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Two_Prive_Hr.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Two_Price_Ten_Hr.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Two_Transport.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        Form_Two_Cv1.setClickable(true);
        Form_Two_Cv2.setClickable(false);
        Form_Two_Service.setClickable(false);
        Form_Two_Ray.setClickable(false);
        Form_Two_Prive_Hr.setClickable(false);
        Form_Two_Price_Ten_Hr.setClickable(false);
        Form_Two_Transport.setClickable(false);
        Form_Two_Cv1.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Two_Cv2.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Two_Service.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Two_Ray.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Two_Prive_Hr.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Two_Price_Ten_Hr.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Two_Transport.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.whitecolor)));
        Form_Two_Save_and_Continue.setVisibility(View.GONE);
        Form_Two_Cv1.setEnabled(false);
        Form_Two_Cv2.setEnabled(false);
        Form_Two_Service.setEnabled(false);
        Form_Two_Ray.setEnabled(false);
        Form_Two_Prive_Hr.setEnabled(false);
        Form_Two_Price_Ten_Hr.setEnabled(false);
        Form_Two_Transport.setEnabled(false);
        Servicecheck_1.setEnabled(false);
        Servicecheck_2.setEnabled(false);
        Servicecheck_3.setEnabled(false);
        Servicecheck_4.setEnabled(false);
        Servicecheck_5.setEnabled(false);
        Servicecheck_6.setEnabled(false);
        Servicecheck_7.setEnabled(false);
        Transportcheck_1.setEnabled(false);
        Transportcheck_2.setEnabled(false);
        Transportcheck_3.setEnabled(false);
        Transportcheck_4.setEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void EditTint(){

        Form_Two_Cv1.setEnabled(true);
        Form_Two_Cv2.setEnabled(true);
        Form_Two_Service.setEnabled(true);
        Form_Two_Ray.setEnabled(true);
        Form_Two_Prive_Hr.setEnabled(true);
        Form_Two_Price_Ten_Hr.setEnabled(true);
        Form_Two_Transport.setEnabled(true);
        Servicecheck_1.setEnabled(true);
        Servicecheck_2.setEnabled(true);
        Servicecheck_3.setEnabled(true);
        Servicecheck_4.setEnabled(true);
        Servicecheck_5.setEnabled(true);
        Servicecheck_6.setEnabled(true);
        Servicecheck_7.setEnabled(false);
        Transportcheck_1.setEnabled(true);
        Transportcheck_2.setEnabled(true);
        Transportcheck_3.setEnabled(true);
        Transportcheck_4.setEnabled(true);
        Form_Two_Cv1.setBackgroundTintList(valueOf(getResources().getColor(R.color.attach)));
        Form_Two_Cv2.setBackgroundTintList(valueOf(getResources().getColor(R.color.download)));
        Form_Two_Service.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Ray.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Prive_Hr.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Price_Ten_Hr.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Transport.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Cv1.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.attach)));
        Form_Two_Cv2.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.download)));
        Form_Two_Service.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Ray.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Prive_Hr.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Price_Ten_Hr.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Transport.setCompoundDrawableTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        Form_Two_Save_and_Continue.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        /* old pdf */
////        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
////            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
////            Log.d("Path: ", path);
////
////            File file = new File(path);
////            displayFromFile(file);
////            if (path != null) {
////                Log.d("Path: ", path);
////                pdfPath = path;
////                Form_Two_Cv1.setText(pdfPath);
////
////            }
////        }
//
//        /* old pdf */
//
//    }

    /* old pdf */
/*
    private void uploadFile() {
        if (pdfPath == null) {
            return;
        } else {
            showpDialog();

            // Map is used to multipart the file using okhttp3.RequestBody
            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(pdfPath);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
            map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
//            ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
//            Call<ServerResponse> call = getResponse.upload("token", map);
//            call.enqueue(new Callback<ServerResponse>() {
//                @Override
//                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                    if (response.isSuccessful()){
//                        if (response.body() != null){
//                            hidepDialog();
//                            ServerResponse serverResponse = response.body();
//                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }else {
//                        hidepDialog();
//                        Toast.makeText(getApplicationContext(), "problem image", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ServerResponse> call, Throwable t) {
//                    hidepDialog();
//                    Log.v("Response gotten is", t.getMessage());
//                    Toast.makeText(getApplicationContext(), "problem uploading image " + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            });
        }
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("");
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }



    @Override
    public void onLoadComplete(@NonNull Loader loader, @Nullable Object data) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageError(int page, Throwable t) {

    }

    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(getActivity())
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle("Select PDF file")
                .start();
    }

    private void displayFromFile(File file) {

        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        pdfFileName = getFileName(uri);

        pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getActivity()))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

*/
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
//    private void displayFromFile(File file) {
//
//        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
//        pdfFileName = getFileName(uri);
//
//        pdfView.fromFile(file)
//                .defaultPage(pageNumber)
//                .onPageChange(this)
//                .enableAnnotationRendering(true)
//                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(getActivity()))
//                .spacing(10) // in dp
//                .onPageError(this)
//                .load();
//    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void uploadMultipart() {

        ((RealMainPageActivity)getContext()).showprocess();

        if(!Form_Two_Ray.getText().toString().equals("") && !Form_Two_Prive_Hr.getText().toString().equals("") && !Form_Two_Price_Ten_Hr.getText().toString().equals("") ){
            if(!base64pdfstring .equals("")){
        if(Servicecheck_1.isChecked() || Servicecheck_2.isChecked() || Servicecheck_3.isChecked()|| Servicecheck_4.isChecked()|| Servicecheck_5.isChecked() || Servicecheck_6.isChecked()|| Servicecheck_7.isChecked()) {

            if(Transportcheck_1.isChecked() || Transportcheck_2.isChecked() || Transportcheck_3.isChecked()||Transportcheck_4.isChecked()) {
                serviceHolder.clear();
                if (Servicecheck_1.isChecked()) {
                    serviceHolder.add("CoursIndividuel");
                }
                if (Servicecheck_2.isChecked()) {
                    serviceHolder.add("CoursCollectifOndemand");
                }
                if (Servicecheck_3.isChecked()) {
                    serviceHolder.add("CoursCollectifClub");
                }
                if (Servicecheck_4.isChecked()) {
                    serviceHolder.add("Stage");
                }
                if (Servicecheck_5.isChecked()) {
                    serviceHolder.add("TeamBuilding");
                }
                if (Servicecheck_6.isChecked()) {
                    serviceHolder.add("Animations");
                }
                String s = TextUtils.join(",", serviceHolder);

                transportHolder.clear();
                if (Transportcheck_1.isChecked()) {
                    transportHolder.add("Car");
                }
                if (Transportcheck_2.isChecked()) {
                    transportHolder.add("Vélo");
                }
                if (Transportcheck_3.isChecked()) {
                    transportHolder.add("Train");
                }
                if (Transportcheck_4.isChecked()) {
                    transportHolder.add("Bus");
                }
                String s_trans = TextUtils.join(",", transportHolder);

                Form_Two_Transport.setText(s);
//                System.out.println("323434" + coachDetailsModel2.getCoach_Fname() + " -- " + coachDetailsModel2.getCoach_Lname() + " -- " +
//                        coachDetailsModel2.getCoach_Email() + " -- " + coachDetailsModel2.getCoach_Phone() + " -- " + Form_Two_Prive_Hr.getText().toString().toString() + " -- " + Form_Two_Price_Ten_Hr.getText().toString().toString() + " -- " +
//                        s + " -- " + s_trans + " -- " + coachDetailsModel2.getCoach_Image() + " -- " + base64pdfstring + " -- " + coachDetailsModel2.getCoach_Description() + " -- " +
//                        coachDetailsModel2.getActive_Status() + " -- " + coachDetailsModel2.getCoach_payment_type() + " -- " +
//                        coachDetailsModel2.getCoach_Bank_Name() + " -- " + coachDetailsModel2.getBranch_Code() + " -- " + coachDetailsModel2.getCoach_Bank_ACCNum() + " -- " + coachDetailsModel2.getCoach_Rayon() + " -- " + coachDetailsModel2.getCoach_Bank_City() + " -- " +
//                        coachDetailsModel2.getUser_type());

//                coachDetailsModel2.getCoach_Fname(), coachDetailsModel2.getCoach_Lname(),
//                        coachDetailsModel2.getCoach_Email(), coachDetailsModel2.getCoach_Phone(), Form_Two_Prive_Hr.getText().toString().toString(), Form_Two_Price_Ten_Hr.getText().toString().toString(),
//                        s, s_trans, coachDetailsModel2.getCoach_Image(), base64pdfstring, coachDetailsModel2.getCoach_Description(),
//                        coachDetailsModel2.getActive_Status(), coachDetailsModel2.getCoach_payment_type(),
//                        coachDetailsModel2.getCoach_Bank_Name(), coachDetailsModel2.getBranch_Code(), coachDetailsModel2.getCoach_Bank_ACCNum(), coachDetailsModel2.getCoach_Rayon(), coachDetailsModel2.getCoach_Bank_City(),
//                        coachDetailsModel2.getUser_type());

                String json = sharedPreferences.getString("MyObject","");
                coachDetailsModel2 = new Gson().fromJson(json,CoachDetailsModel.class);

                coachDetailsModel2.setCoach_Fname( coachDetailsModel2.getCoach_Fname());
                coachDetailsModel2.setCoach_Lname(coachDetailsModel2.getCoach_Lname());
                coachDetailsModel2.setCoach_Email(coachDetailsModel2.getCoach_Email());
                coachDetailsModel2.setCoach_Phone(coachDetailsModel2.getCoach_Phone());
                coachDetailsModel2.setCoach_Price(Form_Two_Prive_Hr.getText().toString());
                coachDetailsModel2.setCoach_PriceX10(Form_Two_Price_Ten_Hr.getText().toString());
                coachDetailsModel2.setCoach_Services(s);
                coachDetailsModel2.setCoach_transport(s_trans);
                coachDetailsModel2.setCoach_Image(coachDetailsModel2.getCoach_Image());
                coachDetailsModel2.setCoach_City(coachDetailsModel2.getCoach_City());
                System.out.println("base64pdfstring --> "+base64pdfstring);
                coachDetailsModel2.setCoach_Resume(base64pdfstring);
                coachDetailsModel2.setCoach_Description(coachDetailsModel2.getCoach_Description());
                coachDetailsModel2.setCoach_payment_type(coachDetailsModel2.getCoach_payment_type());
                coachDetailsModel2.setCoach_Bank_Name(coachDetailsModel2.getCoach_Bank_Name());
                coachDetailsModel2.setBranch_Code(coachDetailsModel2.getBranch_Code());
                coachDetailsModel2.setCoach_Bank_ACCNum(coachDetailsModel2.getCoach_Bank_ACCNum());
                coachDetailsModel2.setCoach_Rayon(Form_Two_Ray.getText().toString());
                coachDetailsModel2.setCoach_Bank_City(coachDetailsModel2.getCoach_Bank_City());
                coachDetailsModel2.setFacebookURL(coachDetailsModel2.getFacebookURL());
                coachDetailsModel2.setInstagramURL(coachDetailsModel2.getInstagramURL());
                coachDetailsModel2.setTwitterURL(coachDetailsModel2.getTwitterURL());

                Gson gson = new Gson();
                String coachDetaildsModel = gson.toJson(coachDetailsModel2);
                editor.putString("edit_mode_two","true");
                editor.putString("MyObject",coachDetaildsModel);
                editor.commit();
                previewTint();
                loadUpdata();
                
//                Call<CoachUpdateResponse> req = apiInterface1.getDetailedInsertCoach(coachDetailsModel2.getCoach_Fname(), coachDetailsModel2.getCoach_Lname(),
//                        coachDetailsModel2.getCoach_Email(), coachDetailsModel2.getCoach_Phone(), Form_Two_Prive_Hr.getText().toString(), Form_Two_Price_Ten_Hr.getText().toString(),
//                        s, s_trans, coachDetailsModel2.getCoach_Image(), base64pdfstring, coachDetailsModel2.getCoach_Description(),
//                        coachDetailsModel2.getActive_Status(), coachDetailsModel2.getCoach_payment_type(),
//                        coachDetailsModel2.getCoach_Bank_Name(), coachDetailsModel2.getBranch_Code(), coachDetailsModel2.getCoach_Bank_ACCNum(), coachDetailsModel2.getCoach_Rayon(), coachDetailsModel2.getCoach_Bank_City(),
//                        coachDetailsModel2.getUser_type());
//                req.enqueue(new Callback<CoachUpdateResponse>() {
//                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    @Override
//                    public void onResponse(Call<CoachUpdateResponse> call, Response<CoachUpdateResponse> response) {
//                        if (response.body().getIsSuccess().equals("true")) {
//                            previewTint();
//                            loadUpdata();
////                        textView.setText("Uploaded Successfully!");
////                        textView.setTextColor(Color.BLUE);
////                        Log.e("response.body()",new Gson(response.body().toString()));
////                        System.out.println(" response.body().getCoachUpdateRetrunList().get(0).getCoach_Image() " + response.body().getCoachUpdateRetrunList().get(0).getCoach_Image());
//
//                        } else {
//                            System.out.println(" Successfully  --> 12 " + new Gson().toJson(response.body()));
//                        }
//
////                    Toast.makeText(getContext(), response.code() + " Uploaded Successfully! ", Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<CoachUpdateResponse> call, Throwable t) {
////                    textView.setText("Uploaded Failed!");
////                    textView.setTextColor(Color.RED);
//                        System.out.println(" Successfully  --> 13 " + t.toString());
//
//                        Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
//                        t.printStackTrace();
//                    }
//                });
            }else {
                ((RealMainPageActivity)getContext()).dismissprocess();
                Toast.makeText(getContext(), "Please select transport", Toast.LENGTH_SHORT).show();
            }
        }else {
            ((RealMainPageActivity)getContext()).dismissprocess();
            Toast.makeText(getContext(), "Please select service", Toast.LENGTH_SHORT).show();
        }

            }else {
                ((RealMainPageActivity)getContext()).dismissprocess();
                Toast.makeText(getContext(), "Please upload pdf file.", Toast.LENGTH_SHORT).show();
            }

        }else {
            ((RealMainPageActivity)getContext()).dismissprocess();
            Toast.makeText(getContext(), "Please enter all data", Toast.LENGTH_SHORT).show();
        }
        ((RealMainPageActivity)getContext()).dismissprocess();
        ((MyAccountHomeFragment)getParentFragment()).morethree();

    }


    public void loadUpdata(){

        ((RealMainPageActivity)getContext()).showprocess();

        String json = sharedPreferences.getString("MyObject","");
        coachDetailsModel2 = new Gson().fromJson(json,CoachDetailsModel.class);

//        apiInterface1.getCoachDeatils(uPassword).enqueue(new Callback<CoachDetailsResponseData>() {
//            @Override
//            public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
//                if(response.body().getData() != null) {
//                    MyaccountGetData myaccountGetData = response.body().getData();
//                    coachDetailsModel2 = myaccountGetData.getCoachDetailsModelArrayList().get(0);
                    if(coachDetailsModel2.getCoach_Services() != null)
                        Form_Two_Service.setText(coachDetailsModel2.getCoach_Services());
                    else
                        Form_Two_Service.setText("-");
                    if(coachDetailsModel2.getCoach_Rayon() != null)
                        Form_Two_Ray.setText(coachDetailsModel2.getCoach_Rayon());
                    else
                        Form_Two_Ray.setText("-");
                    if(coachDetailsModel2.getCoach_Price() != null)
                        Form_Two_Prive_Hr.setText(coachDetailsModel2.getCoach_Price());
                    else
                        Form_Two_Prive_Hr.setText("-");
                    if(coachDetailsModel2.getCoach_PriceX10() != null)
                        Form_Two_Price_Ten_Hr.setText(coachDetailsModel2.getCoach_PriceX10());
                    else
                        Form_Two_Price_Ten_Hr.setText("-");
                    if(coachDetailsModel2.getCoach_transport() != null)
                        Form_Two_Transport.setText(coachDetailsModel2.getCoach_transport());
                    else
                        Form_Two_Transport.setText("-");
                    if(coachDetailsModel2.getCoach_Resume() != null){
                        base64pdfstring = coachDetailsModel2.getCoach_Resume();
                    }else{
                        base64pdfstring = "";
                    }

                    if(coachDetailsModel2.getCoach_transport() != null){
                        String ass = coachDetailsModel2.getCoach_transport();
                        String[] stringArrayList = ass.split(",");
                        for(String s:stringArrayList){
                            switch (s) {
                                case "Car":
                                    Transportcheck_1.setChecked(true);
                                    break;
                                case "Vélo":
                                    Transportcheck_2.setChecked(true);
                                    break;
                                case "Train":
                                    Transportcheck_3.setChecked(true);
                                    break;
                                case "Bus":
                                    Transportcheck_4.setChecked(true);
                                    break;
                            }
                        }
                    }

                    if(coachDetailsModel2.getCoach_Services() != null){
                        String ass = coachDetailsModel2.getCoach_Services();
                        String[] stringArrayLists = ass.split(",");
                        for(String ss:stringArrayLists){
                            switch (ss) {
                                case "CoursIndividuel":
                                    Servicecheck_1.setChecked(true);
                                    break;
                                case "CoursCollectifOndemand":
                                    Servicecheck_2.setChecked(true);
                                    break;
                                case "CoursCollectifClub":
                                    Servicecheck_3.setChecked(true);
                                    break;
                                case "Stage":
                                    Servicecheck_4.setChecked(true);
                                    break;
                                case "TeamBuilding":
                                    Servicecheck_5.setChecked(true);
                                    break;
                                case "Animations":
                                    Servicecheck_6.setChecked(true);
                                    break;
                                case "Bus":
                                    Servicecheck_7.setChecked(true);
                                    break;
                            }
                        }
                    }

//                }
//            }
//            @Override
//            public void onFailure(Call<CoachDetailsResponseData> call, Throwable t) {
//                System.out.println(" sys---> "+t);
//            }
//        });
//
//
////            if(coachDetailsModel2.getCoach_Resume() != null || coachDetailsModel2.getCoach_Resume().equals("null"))
////                Form_Two_Cv1.setText(coachDetailsModel2.getCoach_Resume());
////            else
////                Form_Two_Cv1.setText("-");
////            if(coachDetailsModel2.getCoach_Resume() != null || coachDetailsModel2.getCoach_Resume().equals("null"))
////                Form_Two_Cv2.setText(coachDetailsModel2.getCoach_Resume());
////            else
////                Form_Two_Cv2.setText("-");
//
//
//
//    }
        ((RealMainPageActivity)getContext()).dismissprocess();
}
    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            File file = new File(filePath.toString());

            Log.d("data", "onActivityResult: uri"+filePath.toString());
            //            myFile = new File(uriString);
            //            ret = myFile.getAbsolutePath();
            //Fpath.setText(ret);

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream in = getContext().getContentResolver().openInputStream(filePath);

                byte[] bytes=getBytes(in);
                Log.d("data", "onActivityResult: bytes size="+bytes.length);

                Log.d("data", "onActivityResult: Base64string="+ Base64.encodeToString(bytes,Base64.DEFAULT));
                base64pdfstring = Base64.encodeToString(bytes,Base64.DEFAULT);


//                InputStreamReader inputStreamReader = new InputStreamReader(in);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    total.append(line);
//                }
//
//                byte[] buffer = new byte[8192];
//                int bytesRead;
//                output = new ByteArrayOutputStream();
//                int count=0;
//                try {
//                    while ((bytesRead = in.read(buffer)) != -1) {
//                        output.write(buffer, 0, bytesRead);
//                        count++;
//                    }
//                } catch (IOException e) {
//                    Log.d("error byte", "onActivityResult: " + e.toString());
//                    e.printStackTrace();
//                }
//                bytes = output.toByteArray();
//
//                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                // FileInputStream fis = new FileInputStream(new File(uri.getPath()));

                //                byte[] buf = new byte[1024];
//                int n;
//                while (-1 != (n = in.read(buf))) {
//                    baos.write(buf, 0, n);
//                }
//                fileBytes = baos.toByteArray();
//                //Log.i("ByteArray" + path + ">><<" + ret, "----" + fileBytes.toString());
//                finalString = fileBytes.toString();


            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Log.d("error", "onActivityResult: " + e.toString());
            }

            if (filePath.toString() != null) {
                Log.d("Path: ", filePath.toString());
                pdfPath = filePath.toString();
                String filename = pdfPath.substring(pdfPath.lastIndexOf("/")+1);
                filename = filename.replace("%20"," ");
                Form_Two_Cv1.setText("Attaché");
            }
        }
    }



    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
