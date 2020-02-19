package com.tech.cloudnausor.ohmytennispro.fragment.myaccount;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.HomeActivity;
import com.tech.cloudnausor.ohmytennispro.activity.MyAccountHomeActivity;
import com.tech.cloudnausor.ohmytennispro.adapter.MyAccountTabsAdapter;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.fragment.realhomefragment.RealHomeFragment;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.MyaccountGetData;
import com.tech.cloudnausor.ohmytennispro.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAccountHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAccountHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView My_A_Personal_Details,My_A_Skills_Set,My_A_Transaction_Details ;
    private ImageView One_dot,Second_dot,Third_dot ;
    private ProgressBar progressBarLine_one,progressBarLine_two ;
    private static final String PREFER_NAME = "Reg";
    private String edit_sting, loginObject;
    public String new_edit_string_main="0",new_edit_string_page1="0",new_edit_string_page2="0",new_edit_string_page3="0";
    private ImageView GoBack;
    private boolean mSearchCheck;
    ApiInterface apiInterface  = ApiClient.getClient().create(ApiInterface.class);
    TabLayout tabLayout;
//    CoachDetailsModel coachDetailsModelHome = new CoachDetailsModel();
SingleTonProcess singleTonProcess;

    String uPassword =null;

    //    ImageView Edit_Indi;
    ViewPager viewPager;
    MyAccountTabsAdapter tabsAdapter;
    //    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;





    // TODO: Rename and change types and number of parameters
    public  MyAccountHomeFragment newInstance(String text) {
        MyAccountHomeFragment mFragment = new MyAccountHomeFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_my_account_home, container, false);
        // Inflate the layout for this fragment
         tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        My_A_Personal_Details = (TextView)view.findViewById(R.id.persaonaldetails);
        GoBack = (ImageView)view.findViewById(R.id.back);
        My_A_Skills_Set = (TextView)view.findViewById(R.id.skillsset);
        My_A_Transaction_Details = (TextView)view.findViewById(R.id.tradetails);
        One_dot =(ImageView)view.findViewById(R.id.one_dot);
        Second_dot =(ImageView)view.findViewById(R.id.second_dot);
        Third_dot =(ImageView)view.findViewById(R.id.third_dot);
//        Edit_Indi=(ImageView)toolbar.view.findViewById(R.id.edit_indi) ;
        progressBarLine_one  =(ProgressBar)view.findViewById(R.id.processbar_one);
        progressBarLine_two  =(ProgressBar)view.findViewById(R.id.processbar_two);
        sharedPreferences = getContext().getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        session = new SessionManagement(getApplicationContext());
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.form_one_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.form_two_tab)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getText(R.string.form_three_tab)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(1);
        tabLayout.setTabTextColors(Color.parseColor("#494949"), Color.parseColor("#000000"));
//        viewPager =(ViewPager) view.findViewById(R.id.view_pager);
        tabsAdapter = new MyAccountTabsAdapter(getFragmentManager(),tabLayout.getTabCount());

        editor.putString("edit_mode","true");
        editor.putString("edit_mode_one","true");
        editor.putString("edit_mode_two","true");
        editor.putString("edit_mode_three","true");
        editor.commit();
//        viewPager.setAdapter(tabsAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        getChildFragmentManager().beginTransaction().add(R.id.view_pager_fragement,new MyAccFormOneFragment(),"MyAccFormOneFragment").commit();


        My_A_Transaction_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewPager.setCurrentItem(2);
                if( sharedPreferences.getString("edit_mode_one","").equals("true") &&  sharedPreferences.getString("edit_mode_two","").equals("true")){
                My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                progressBarLine_two.setProgress(100);
                My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                progressBarLine_one.setProgress(100);
                My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                getChildFragmentManager().beginTransaction().replace(R.id.view_pager_fragement,new MyAccFormThreeFragment(),"MyAccFormThreeFragment").commit();}else {
                    Toast.makeText(getContext(),"Veuillez cliquer sur enregistrer et continuer",Toast.LENGTH_LONG).show();
                }

            }
        });

        if(sharedPreferences.contains("IsMyEditString")){
            edit_sting = sharedPreferences.getString("IsMyEditString","");
        }
        if (sharedPreferences.contains("Email"))
        {
            uPassword = sharedPreferences.getString("Email", "");

        }

        if(sharedPreferences.contains("")){
            loginObject = sharedPreferences.getString("LoginObject","");
        }

//            apiInterface.getCoachDeatils(uPassword).enqueue(new Callback<CoachDetailsResponseData>() {
//                @Override
//                public void onResponse(Call<CoachDetailsResponseData> call, Response<CoachDetailsResponseData> response) {
//                    assert response.body() != null;
//                    if (response.body().getData() != null) {
//                        MyaccountGetData myaccountGetData = response.body().getData();
//                        coachDetailsModelHome = myaccountGetData.getCoachDetailsModelArrayList().get(0);
//                        System.out.println(new Gson().toJson(coachDetailsModelHome));
//                        Gson gson = new Gson();
//                        String coachDetaildsModel = gson.toJson(coachDetailsModelHome);
//                        editor.putString("MyObject",coachDetaildsModel);
//                        editor.commit();
////                        Picasso.get().load(decodedImage).into(profile);
////                        profile.setImageBitmap(decodedImage);}
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<CoachDetailsResponseData> call, Throwable t) {
//                    System.out.println(" sys---> " + t);
//                }
//            });




//        if(edit_sting.equals("1")) {
//            Edit_Indi.setVisibility(View.GONE);
//
//        }else{
//
//        }
//        GoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });


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
//                viewPager.setCurrentItem(1);
                if( sharedPreferences.getString("edit_mode_one","").equals("true")){
                My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                progressBarLine_two.setProgress(0);
                My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                progressBarLine_one.setProgress(100);
                My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                getChildFragmentManager().beginTransaction().replace(R.id.view_pager_fragement,new MyAccFormTwoFragment(),"MyAccFormTwoFragment").commit();
                }else {
                    Toast.makeText(getContext(),"Veuillez cliquer sur enregistrer et continuer",Toast.LENGTH_LONG).show();
                }
            }
        });

        My_A_Personal_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewPager.setCurrentItem(0);
                My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                progressBarLine_two.setProgress(0);
                My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                progressBarLine_one.setProgress(0);
                My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                getChildFragmentManager().beginTransaction().replace(R.id.view_pager_fragement,new MyAccFormOneFragment(),"MyAccFormOneFragment").commit();

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

//        MyAccFormOneFragment myAccFormOneFragment = (MyAccFormOneFragment) tabsAdapter.getFragement(0);
//        myAccFormOneFragment.setViewpagerInterface(new ViewpagerInterface() {
//            @Override
//            public void viewPage() {
//                System.out.println("myAccFormOneFragment---> " + "myAccFormOneFragment" );
//            }
//        });
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(MyAccountHomeActivity.this, HomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) (menu.findItem(Menus.SEARCH).getActionView());
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(OnQuerySearchView);

        menu.findItem(Menus.ADD).setVisible(false);
        menu.findItem(Menus.EDIT).setVisible(false);
        menu.findItem(Menus.UPDATE).setVisible(false);
        menu.findItem(Menus.SEARCH).setVisible(false);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case Menus.ADD:
                break;
            case Menus.EDIT:
                if(new_edit_string_main.equals("0")){
                    new_edit_string_page1 = "1";
                    new_edit_string_page2 = "1";
                    new_edit_string_page3 = "1";
                    viewPager.setCurrentItem(1);
                }
                break;

            case Menus.UPDATE:
                break;

            case Menus.SEARCH:
                mSearchCheck = true;
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener OnQuerySearchView = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onQueryTextChange(String arg0) {
            // TODO Auto-generated method stub
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };



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

 void moreTwo(){
                My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                progressBarLine_two.setProgress(0);
                My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                progressBarLine_one.setProgress(100);
                My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                getChildFragmentManager().beginTransaction().replace(R.id.view_pager_fragement,new MyAccFormTwoFragment(),"MyAccFormTwoFragment").commit();
                }

    void morethree()
    {
        My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        progressBarLine_two.setProgress(100);
        My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        progressBarLine_one.setProgress(100);
        My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        getChildFragmentManager().beginTransaction().replace(R.id.view_pager_fragement,new MyAccFormThreeFragment(),"MyAccFormThreeFragment").commit();

    }

    void moreOne(){
             My_A_Personal_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                Third_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                progressBarLine_two.setProgress(0);
                My_A_Transaction_Details.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                Second_dot.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
                progressBarLine_one.setProgress(0);
                My_A_Skills_Set.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.button_cancel)));
        getChildFragmentManager().beginTransaction().replace(R.id.view_pager_fragement,new MyAccFormOneFragment(),"MyAccFormOneFragment").commit();
    }
    void setPagerFragment()
    {
        viewPager.setCurrentItem(1);
    }
}
