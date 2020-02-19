package com.tech.cloudnausor.ohmytennispro.activity.collectivecourse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.HomeActivity;
import com.tech.cloudnausor.ohmytennispro.activity.IndividualCourseActivity;

import java.util.ArrayList;

public class CollectiveCourseActivity extends AppCompatActivity {
    EditText CC_Transport;
    CheckBox Transportcheck_1,Transportcheck_2,Transportcheck_3,Transportcheck_4;
    ArrayList<String> transportHolder = new ArrayList<>();
    Button Transport_cancel,Transport_ok;
    ImageView GoBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_collective_course);
        CC_Transport = (EditText)findViewById(R.id.cc_transport);
        GoBack = (ImageView)findViewById(R.id.back);
//        Button Page_Cancel = (Button)findViewById(R.id.page_cancel) ;
//        Page_Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//
//            }
//        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        CC_Transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Rect displayRectangle = new Rect();
                    Window window = CollectiveCourseActivity.this.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(CollectiveCourseActivity.this,R.style.DialogTheme);
                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dailog_transport, viewGroup, false);
                    dialogView.setMinimumWidth((int)(displayRectangle.width()* 1f));
//            dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();
                    WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
                    wmlp.gravity = Gravity.TOP;
                    Transportcheck_1 =(CheckBox)dialogView.findViewById(R.id.transportcheck_1);
                    Transportcheck_2 =(CheckBox)dialogView.findViewById(R.id.transportcheck_2);
                    Transportcheck_3 =(CheckBox)dialogView.findViewById(R.id.transportcheck_3);
                    Transportcheck_4 =(CheckBox)dialogView.findViewById(R.id.transportcheck_4);

                    Transport_cancel =(Button) dialogView.findViewById(R.id.transport_cancel);
                    Transport_ok =(Button) dialogView.findViewById(R.id.transport_ok);
                    Transport_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            transportHolder.clear();
                            if(Transportcheck_1.isChecked()){
                                transportHolder.add(Transportcheck_1.getText().toString());
                            }
                            if(Transportcheck_2.isChecked()){
                                transportHolder.add(Transportcheck_2.getText().toString());
                            }
                            if(Transportcheck_3.isChecked()){
                                transportHolder.add(Transportcheck_3.getText().toString());
                            }
                            if(Transportcheck_4.isChecked()){
                                transportHolder.add(Transportcheck_4.getText().toString());
                            }
                            String s = TextUtils.join(",", transportHolder);
                            CC_Transport.setText(s);
                            alertDialog.dismiss();
                        }
                    });
                    Transport_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CollectiveCourseActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
