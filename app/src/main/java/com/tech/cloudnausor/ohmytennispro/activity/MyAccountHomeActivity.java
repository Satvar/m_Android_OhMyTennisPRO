package com.tech.cloudnausor.ohmytennispro.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.adapter.MyAccountTabsAdapter;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;

public class MyAccountHomeActivity extends AppCompatActivity {

    private TextView My_A_Personal_Details,My_A_Skills_Set,My_A_Transaction_Details ;
    private ImageView One_dot,Second_dot,Third_dot ;
    private ProgressBar progressBarLine_one,progressBarLine_two ;
    private static final String PREFER_NAME = "Reg";
    String edit_sting = null,loginObject;
    ImageView GoBack;

    SingleTonProcess singleTonProcess;



    Toolbar toolbar;
//    ImageView Edit_Indi;
    ViewPager viewPager;
    MyAccountTabsAdapter tabsAdapter;
//    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_account_home);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        My_A_Personal_Details = (TextView)findViewById(R.id.persaonaldetails);
        GoBack = (ImageView)findViewById(R.id.back);

        My_A_Skills_Set = (TextView)findViewById(R.id.skillsset);
        My_A_Transaction_Details = (TextView)findViewById(R.id.tradetails);
        One_dot =(ImageView)findViewById(R.id.one_dot);
        Second_dot =(ImageView)findViewById(R.id.second_dot);
        Third_dot =(ImageView)findViewById(R.id.third_dot);
        toolbar = findViewById(R.id.toolbar_indi);
//        Edit_Indi=(ImageView)toolbar.findViewById(R.id.edit_indi) ;
        progressBarLine_one  =(ProgressBar)findViewById(R.id.processbar_one);
        progressBarLine_two  =(ProgressBar)findViewById(R.id.processbar_two);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        session = new SessionManagement(getApplicationContext());
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.form_one_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.form_two_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.form_three_tab)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(1);
        tabLayout.setTabTextColors(Color.parseColor("#494949"), Color.parseColor("#000000"));
        viewPager =(ViewPager) findViewById(R.id.view_pager);
        tabsAdapter = new MyAccountTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        My_A_Transaction_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        if(sharedPreferences.contains("IsMyEditString")){
            edit_sting = sharedPreferences.getString("IsMyEditString","");
        }
        if(sharedPreferences.contains("")){
            loginObject = sharedPreferences.getString("LoginObject","");
        }
        Gson gson = new Gson();
        CoachDetailsModel coachDetailsModel = gson.fromJson(loginObject,CoachDetailsModel.class);

//        if(edit_sting.equals("1")) {
//            Edit_Indi.setVisibility(View.GONE);
//
//        }else{
//
//        }
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


//        if(sharedPreferences.getString("IsMyaccountEdit","").equals("1")){
//            Edit_Indi.setVisibility(View.GONE);
//        }

//        Edit_Indi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editor.putString("IsMyEditString","1").apply();
//                editor.putBoolean("IsMyaccountEdit",true).apply();
//                editor.commit();
//                Edit_Indi.setVisibility(View.GONE);
//                startActivity(getIntent());
//            }
//        });

        My_A_Skills_Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        My_A_Personal_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                    progressBarLine_two.setProgress(0);
                    My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                    Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                    progressBarLine_one.setProgress(0);
                    My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                }else if(tab.getPosition() == 1){
                    My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                    Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                    progressBarLine_two.setProgress(0);
                    My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                    Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    progressBarLine_one.setProgress(100);
                    My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                }else if(tab.getPosition() == 2){
                    My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                    Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    progressBarLine_two.setProgress(100);
                    My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    progressBarLine_one.setProgress(100);
                    My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                }else {

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyAccountHomeActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}
