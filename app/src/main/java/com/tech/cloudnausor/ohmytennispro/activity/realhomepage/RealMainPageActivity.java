package com.tech.cloudnausor.ohmytennispro.activity.realhomepage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.BuildConfig;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.HomeActivity;
import com.tech.cloudnausor.ohmytennispro.activity.collectivecourse.CollectiveCourseActivity;
import com.tech.cloudnausor.ohmytennispro.activity.login.LoginActivity;
import com.tech.cloudnausor.ohmytennispro.adapter.NavigationAdapter;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.fragment.ChangePasswordActivity;
import com.tech.cloudnausor.ohmytennispro.fragment.coachuserreservation.CoachUserReservationFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.AnimationFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.CollectiveCourseClubFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.CollectiveCourseFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.OhMyEventSevice;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.StageFragmentPage;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.TeamBuildingFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.TournamentFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.individualcourse.IndividualCourseFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.myaccount.MyAccountHomeFragment;
import com.tech.cloudnausor.ohmytennispro.fragment.mycalender.MyCalenderViewFragmentKt;
import com.tech.cloudnausor.ohmytennispro.fragment.realhomefragment.RealHomeFragment;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.MyaccountGetData;
import com.tech.cloudnausor.ohmytennispro.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealMainPageActivity extends AppCompatActivity {
    private int mLastPosition = 0;
    private String mEventName = "";
    private String mEventId = "";
    private ListView mListDrawer;
    private DrawerLayout mLayoutDrawer;
    private RelativeLayout mUserDrawer;
    private RelativeLayout mRelativeDrawer;
    TextView title,tvVersion;
    private FragmentManager mFragmentManager;
    private NavigationAdapter mNavigationAdapter;
    private ActionBarDrawerToggleCompat mDrawerToggle;
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uPassword =null;
    SingleTonProcess singleTonProcess;
    ApiInterface apiInterface  = ApiClient.getClient().create(ApiInterface.class);
    CoachDetailsModel coachDetailsModelHome = new CoachDetailsModel("","","","",
            "","","","","","",
            "","","","","","",
            "","","","","","","");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setIcon(R.drawable.ic_launcher_background);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        setContentView(R.layout.activity_real_main_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        singleTonProcess = singleTonProcess.getInstance();
        singleTonProcess.show(RealMainPageActivity.this);

        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getApplicationContext());
        mListDrawer = (ListView) findViewById(R.id.listDrawer);
        mRelativeDrawer = (RelativeLayout) findViewById(R.id.relativeDrawer);
        mLayoutDrawer = (DrawerLayout) findViewById(R.id.layoutDrawer);
        title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        mUserDrawer = (RelativeLayout) findViewById(R.id.userDrawer);
        mUserDrawer.setOnClickListener(userOnClick);
        tvVersion = (TextView)findViewById(R.id.version_code);

        String versionName = BuildConfig.VERSION_NAME;
        tvVersion.setText("Version : " + versionName);



        if (mListDrawer != null) {

            // All header menus should be informed here
            // listHeader.add(MENU POSITION)
            List<Integer> mListHeader = new ArrayList<Integer>();
//            mListHeader.add(4);
//            mListHeader.add(6);
//            mListHeader.add(10);
            List<Integer> mListHide = new ArrayList<Integer>();
            mListHide.add(15);
            mListHide.add(14);
            mListHide.add(13);
            // All menus which will contain an accountant should be informed here
            // Counter.put ("POSITION MENU", "VALUE COUNTER");
            SparseIntArray mCounter = new SparseIntArray();
//            mCounter.put(Constant.MENU_RESERVATION,10);

//            mCounter.put(Constant.MENU_LOGOUT,10);
            mNavigationAdapter = new NavigationAdapter(this, NavigationList.getNavigationAdapter(this, mListHeader, mCounter, mListHide));
        }

        mListDrawer.setAdapter(mNavigationAdapter);
        mListDrawer.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggleCompat(this, mLayoutDrawer);
        mLayoutDrawer.addDrawerListener(mDrawerToggle);

        if (savedInstanceState != null) {
            setLastPosition(savedInstanceState.getInt(Constant.LAST_POSITION));
            setTitleFragments(mLastPosition);
            mNavigationAdapter.resetarCheck();
            mNavigationAdapter.setChecked(mLastPosition, true);
        }else{
            setLastPosition(mLastPosition);
            setFragmentList(mLastPosition);
        }

        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");

        }


        myprofiledata();
    }

    public void setFragmentList(int posicao){
        Fragment mFragment = null;
        mFragmentManager = getSupportFragmentManager();
        switch (posicao) {
            case Constant.MENU_DASHBOARD:
                mFragment = new RealHomeFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_DASHBOARD));
                break;
            case Constant.MENU_MY_ACCOUNT:
                mFragment = new MyAccountHomeFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_MY_ACCOUNT));
                break;
                case Constant.MENU_CALENDAR:
                    mFragment = MyCalenderViewFragmentKt.newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_CALENDAR));
                    break;
            case Constant.MENU_RESERVATION:
                mFragment =  new CoachUserReservationFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_RESERVATION));
                break;
            case Constant.MENU_INDIVIDUAL_COURSE:
                mFragment =  new IndividualCourseFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_INDIVIDUAL_COURSE));
                break;
            case Constant.MENU_COLLECTIVE_LESSON:
                mFragment =  new CollectiveCourseFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_COLLECTIVE_LESSON));
                break;
            case Constant.MENU_CLUB_COLLECTIVE_COURT:
                mFragment = new CollectiveCourseClubFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_CLUB_COLLECTIVE_COURT));
                break;
            case Constant.MENU_STAGE:
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_STAGE),"stage");
//                mFragment =  new StageFragmentPage().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_STAGE));
                break;
            case Constant.MENU_TEAMBUILDING:
//                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_STAGE),"teambuilding");

//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
                mFragment =  new TeamBuildingFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_TEAMBUILDING));
                break;
            case Constant.MENU_ANIMATION:
                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_ANIMATION),"animation");
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
//                mFragment =  new AnimationFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_ANIMATION));
                break;
            case Constant.MENU_TOURNAMENT:
                mFragment = new OhMyEventSevice().newInstance(Utils.getTitleItem(this, Constant.MENU_TOURNAMENT),"tournament");
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
//                mFragment =  new TournamentFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_TOURNAMENT));
                break;
            case Constant.MENU_STAGE_EDIT:
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
                mFragment =  new StageFragmentPage().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_STAGE_EDIT),mEventName,mEventId);
                break;
            case Constant.MENU_ANIMATION_EDIT:
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
                mFragment =  new AnimationFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_ANIMATION_EDIT),mEventName,mEventId);
                break;
            case Constant.MENU_TOURNAMENT_EDIT:
//                Toast.makeText(getApplicationContext(),"En cours d'élaboration",Toast.LENGTH_LONG).show();
                mFragment =  new TournamentFragment().newInstance(Utils.getTitleItem(RealMainPageActivity.this, Constant.MENU_TOURNAMENT_EDIT),mEventName,mEventId);
                break;
        }

        if (mFragment != null){
            if(mLastPosition == Constant.MENU_STAGE_EDIT){
                setTitleFragments(Constant.MENU_STAGE);
                mNavigationAdapter.resetarCheck();
                mNavigationAdapter.setChecked(Constant.MENU_STAGE, true);
            }else if(mLastPosition == Constant.MENU_ANIMATION_EDIT){
                setTitleFragments(Constant.MENU_ANIMATION);
                mNavigationAdapter.resetarCheck();
                mNavigationAdapter.setChecked(Constant.MENU_ANIMATION, true);
            }else if(mLastPosition == Constant.MENU_TOURNAMENT_EDIT){
                setTitleFragments(Constant.MENU_TOURNAMENT);
                mNavigationAdapter.resetarCheck();
                mNavigationAdapter.setChecked(Constant.MENU_TOURNAMENT, true);
            }else {
                setTitleFragments(mLastPosition);
                mNavigationAdapter.resetarCheck();
                mNavigationAdapter.setChecked(posicao, true);
            }

            mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragment).commit();
        }
    }
    public void hideMenus(Menu menu, int posicao) {
        boolean drawerOpen = mLayoutDrawer.isDrawerOpen(mRelativeDrawer);
        switch (posicao) {
            case Constant.MENU_DASHBOARD:
//                menu.findItem(Menus.ADD).setVisible(!drawerOpen);
//                menu.findItem(Menus.UPDATE).setVisible(!drawerOpen);
//                menu.findItem(Menus.SEARCH).setVisible(!drawerOpen);
                break;

            case Constant.MENU_MY_ACCOUNT:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_RESERVATION:

//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_INDIVIDUAL_COURSE:
                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_COLLECTIVE_LESSON:
                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_CLUB_COLLECTIVE_COURT:
                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_TEAMBUILDING:
                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_ANIMATION:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_TOURNAMENT:
//                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                break;
            case Constant.MENU_STAGE_EDIT:
                if(mEventName.equals("update")){
                menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                }else {
                    menu.findItem(Menus.EDIT).setVisible(drawerOpen);
                }
                break;
            case Constant.MENU_ANIMATION_EDIT:
                if(mEventName.equals("update")){
                    menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                }else {
                    menu.findItem(Menus.EDIT).setVisible(drawerOpen);
                }
                break;
            case Constant.MENU_TOURNAMENT_EDIT:
                if(mEventName.equals("update")){
                    menu.findItem(Menus.EDIT).setVisible(!drawerOpen);
                }else {
                    menu.findItem(Menus.EDIT).setVisible(drawerOpen);
                }
                break;
            case Constant.MENU_LOGOUT:
                editor.putBoolean("IsUserLogIn",false);
                editor.commit();
                Intent intent = new Intent(RealMainPageActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                    break;
                default:
                    break;
        }
    }

    private void setTitleFragments(int position){
        setIconActionBar(Utils.iconNavigation[position]);
        title.setText((Utils.getTitleItem(RealMainPageActivity.this, position)).toString().replace("  ",""));
        setTitleActionBar(Utils.getTitleItem(RealMainPageActivity.this, position).replace("  ",""));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.LAST_POSITION, mLastPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menus.HOME:
                if (mLayoutDrawer.isDrawerOpen(mRelativeDrawer)) {
                    mLayoutDrawer.closeDrawer(mRelativeDrawer);
                } else {
                    mLayoutDrawer.openDrawer(mRelativeDrawer);
                }
                return true;
            case Menus.ADD:
//                Toast.makeText(this,"",Toast.LENGTH_LONG).show();
            default:
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        hideMenus(menu, mLastPosition);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void setTitleActionBar(CharSequence informacao) {
        getSupportActionBar().setTitle(informacao);
    }

    public void setSubtitleActionBar(CharSequence informacao) {
        getSupportActionBar().setSubtitle(informacao);
    }

    public void setIconActionBar(int icon) {
        getSupportActionBar().setIcon(icon);
    }

    public void  setEventydata(String mEventName,String mEventId){
        this.mEventName = mEventName;
        this.mEventId = mEventId;
    }
    public void setLastPosition(int posicao){
        if(posicao != Constant.MENU_MY_ACCOUNT){
            editor.putString("edit_mode","true");
            editor.putString("edit_mode_one","true");
            editor.putString("edit_mode_two","true");
            editor.putString("edit_mode_three","true");
            editor.commit();
        }
        this.mLastPosition = posicao;
    }

    private class ActionBarDrawerToggleCompat extends ActionBarDrawerToggle
    {
        public ActionBarDrawerToggleCompat(Activity mActivity, DrawerLayout mDrawerLayout){
            super(
                    mActivity,
                    mDrawerLayout,
                    R.string.drawer_open,
                    R.string.drawer_close);
        }

        @Override
        public void onDrawerClosed(View view) {
            supportInvalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            mNavigationAdapter.notifyDataSetChanged();
            supportInvalidateOptionsMenu();

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
            setLastPosition(posicao);
            setFragmentList(mLastPosition);
            mLayoutDrawer.closeDrawer(mRelativeDrawer);
        }
    }

    private View.OnClickListener userOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
//            mLayoutDrawer.closeDrawer(mRelativeDrawer);
        }
    };

    @Override
    public void onBackPressed() {

        if(mLastPosition == Constant.MENU_STAGE_EDIT){
            setLastPosition(Constant.MENU_STAGE);
            setFragmentList(Constant.MENU_STAGE);
        }else if(mLastPosition == Constant.MENU_ANIMATION_EDIT){
            setLastPosition(Constant.MENU_ANIMATION);
            setFragmentList(Constant.MENU_ANIMATION);
        }else if(mLastPosition == Constant.MENU_TOURNAMENT_EDIT){
            setLastPosition(Constant.MENU_TOURNAMENT);
            setFragmentList(Constant.MENU_TOURNAMENT);
        }else if(mLastPosition != Constant.MENU_MY_ACCOUNT){
            editor.putString("edit_mode","true");
            editor.putString("edit_mode_one","true");
            editor.putString("edit_mode_two","true");
            editor.putString("edit_mode_three","true");
            editor.commit();
            myprofiledata();
            setLastPosition(Constant.MENU_DASHBOARD);
            setFragmentList(Constant.MENU_DASHBOARD);

        }else if(mLastPosition != Constant.MENU_DASHBOARD){
            myprofiledata();
            setLastPosition(Constant.MENU_DASHBOARD);
            setFragmentList(Constant.MENU_DASHBOARD);
        }else {
            super.onBackPressed();
            finish();
        }

    }

    public void showprocess(){
        singleTonProcess.show(RealMainPageActivity.this);
    }

    public void dismissprocess(){
         singleTonProcess.dismiss();
    }//
   public void myprofiledata(){

        singleTonProcess.show(RealMainPageActivity.this);
//       System.out.println("uPassword---> "+ uPassword);
       apiInterface.getCoachDeatils(uPassword).enqueue(new Callback<CoachDetailsResponseData>() {
           @Override
           public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
               assert response.body() != null;
               if (response.body().getData() != null) {
                   MyaccountGetData myaccountGetData = response.body().getData();

                   System.out.println(" myaccountGetData---> " + new Gson().toJson(myaccountGetData));


                   coachDetailsModelHome = myaccountGetData.getCoachDetailsModelArrayList().get(0);
                   if(coachDetailsModelHome.getCoach_Description() != null){

                   }else {
                       coachDetailsModelHome.setCoach_Description("");
                   }
                   if(coachDetailsModelHome.getCoach_Resume() != null){

                   }else {
                       coachDetailsModelHome.setCoach_Resume("");
                   }
                   if(coachDetailsModelHome.getCoach_Image() != null){

                   }else {
                       coachDetailsModelHome.setCoach_Image("");
                   }
                   System.out.println(new Gson().toJson(coachDetailsModelHome));
                   Gson gson = new Gson();
                   String coachDetaildsModel = gson.toJson(coachDetailsModelHome);
                   editor.putString("MyObject",coachDetaildsModel);
                   editor.commit();
                   singleTonProcess.dismiss();

                   System.out.println(" Successfully  --> 12 " + new Gson().toJson(coachDetailsModelHome));

//                        Picasso.get().load(decodedImage).into(profile);
//                        profile.setImageBitmap(decodedImage);
               }else {
                   singleTonProcess.dismiss();
               }
           }

           @Override
           public void onFailure(Call<CoachDetailsResponseData> call, Throwable t) {
               singleTonProcess.dismiss();
               System.out.println(" sys---> " + t);
           }
       });

   }

}
