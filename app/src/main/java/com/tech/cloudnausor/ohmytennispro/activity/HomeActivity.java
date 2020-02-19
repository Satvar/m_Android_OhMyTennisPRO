package com.tech.cloudnausor.ohmytennispro.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.coachuserreservation.CoachUserReservation;
import com.tech.cloudnausor.ohmytennispro.activity.collectivecourse.AnimationActivity;
import com.tech.cloudnausor.ohmytennispro.activity.collectivecourse.CollectiveCourseActivity;
import com.tech.cloudnausor.ohmytennispro.activity.collectivecourse.CollectiveCourseClubActivity;
import com.tech.cloudnausor.ohmytennispro.activity.collectivecourse.TeamBuildingActivity;
import com.tech.cloudnausor.ohmytennispro.activity.collectivecourse.TournamentActivity;
import com.tech.cloudnausor.ohmytennispro.activity.forgot.ChangePasswordActivity;
import com.tech.cloudnausor.ohmytennispro.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennispro.calenderactivity.BasicActivity;
import com.tech.cloudnausor.ohmytennispro.fragment.individualcourse.IndividualCourseFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccountHomeFragment;
import com.tech.cloudnausor.ohmytennispro.model.CoachUserReserveModel;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Class fragmentClass;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        fragmentClass = MyAccountHomeFragment.class;
//        loadFragment(fragmentClass);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_my_acct_edit) {
            return true;
        }else if(id ==R.id.action_notifications){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
           startActivity(getIntent());
            // Handle the camera action
        } else if (id == R.id.nav_my_account) {
            Intent intent = new Intent(HomeActivity.this, MyAccountHomeActivity.class);
            startActivity(intent);
//            fragmentClass = MyAccountHomeFragment.class;
//            loadFragment(fragmentClass);
        } else if (id == R.id.nav_indi_course) {
            Intent intent = new Intent(HomeActivity.this, IndividualCourseActivity.class);
            startActivity(intent);
//            fragmentClass = IndividualCourseFragment.class;
//            loadFragment(fragmentClass);
        } else if (id == R.id.nav_calenderpage) {
            Intent intent = new Intent(HomeActivity.this, BasicActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_resevation) {
            Intent intent = new Intent(HomeActivity.this, CoachUserReservation.class);
            startActivity(intent);
        } else if (id == R.id.nav_cours_collectif) {
            Intent intent = new Intent(HomeActivity.this, CollectiveCourseActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_cour_collectif_club) {
            Intent intent = new Intent(HomeActivity.this, CollectiveCourseClubActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_teambuilding) {
            Intent intent = new Intent(HomeActivity.this, TeamBuildingActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_animation) {
            Intent intent = new Intent(HomeActivity.this, AnimationActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_tournoi) {
            Intent intent = new Intent(HomeActivity.this, TournamentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_change_pass) {
            Intent intent = new Intent(HomeActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_tools2) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            editor.putBoolean("IsUserLoggedIn",false);
            editor.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Class fragmentClass) {

        System.out.println("current fragment--------->"+fragmentClass.getSimpleName());

        /*if (fragmentClass.getSimpleName().equals("HomeFragment")){
            resetCheckedItemMenu();
        }
        if (fragmentClass.getSimpleName().equals("WalletFragment")){
            resetCheckedItemMenu();
        }
        if (fragmentClass.getSimpleName().equals("NotificationFragment")){
            resetCheckedItemMenu();
        }
        if (fragmentClass.getSimpleName().equals("ProfileFragment")){
            resetCheckedItemMenu();
        }*/


        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName()).commit();
        /*if (Constant.theme == 0){
            System.out.println("fragment1===============>");
            fragmentManager.beginTransaction().replace(R.id.content_frame1, fragment, fragment.getClass().getSimpleName()).commit();
        }else {
            System.out.println("fragment2===============>");
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragment.getClass().getSimpleName()).commit();
        }*/

        hideKeyboard(this);

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
