package com.tech.cloudnausor.ohmytennispro.session;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tech.cloudnausor.ohmytennispro.MainActivity;

import java.util.HashMap;

public class SessionManagement {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLogIn";

    public static final String IS_EDIT_IN = "IsMyEdit";

    public static final String IS_EDIT_IN_STRING = "IsMyEditString";




    // User name (make variable public to access from outside)
    public static final String KEY_COACH_ID = "KEY_Coach_ID";

    // Email address (make variable public to access from outside)
    public static final String KEY_COACH_EMAIL = "Email";



    // password
    public static final String KEY_COACH_PASSWORD = "txtPassword";



    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String uName, String uPassword){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in preferences
        editor.putString(KEY_COACH_ID, uName);
        // Storing email in preferences
        editor.putString(KEY_COACH_EMAIL,  uPassword);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(this.isUserLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//
//            // Closing all the Activities from stack
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
            return true;
        }else {
            return false;
        }

    }

    public boolean checkEdit(){
        // Check login status
        if(this.isEditIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//
//            // Closing all the Activities from stack
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
            return true;
        }else {
            return false;
        }

    }




    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_COACH_ID, pref.getString(KEY_COACH_ID, null));

        // user email id
        user.put(KEY_COACH_EMAIL, pref.getString(KEY_COACH_ID, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(_context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public boolean isEditIn(){
        return pref.getBoolean(IS_EDIT_IN,false);
    }
}
