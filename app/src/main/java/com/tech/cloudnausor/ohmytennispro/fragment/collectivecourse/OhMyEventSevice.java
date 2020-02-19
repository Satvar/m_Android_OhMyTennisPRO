package com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.adapter.OhMyEventAdapter;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.dto.AnimationCourse;
import com.tech.cloudnausor.ohmytennispro.dto.AnimationDTO;
import com.tech.cloudnausor.ohmytennispro.dto.OhMyEventsDTO;
import com.tech.cloudnausor.ohmytennispro.dto.StageDTO;
import com.tech.cloudnausor.ohmytennispro.dto.TournamentDTO;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OhMyEventSevice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "param2";
    private boolean mSearchCheck;
    Menu menumain;
    String coachid_ = null;
    String uPassword =null;
    ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    ArrayList<AnimationCourse> animationCourseArrayList = new  ArrayList<AnimationCourse>();
    ArrayList<StageDTO.StageCourse> stageCourseArrayList = new ArrayList<StageDTO.StageCourse>();
    ArrayList<TournamentDTO.TournamentCourse> tournamentCourseArrayList = new ArrayList<TournamentDTO.TournamentCourse>();
    SingleTonProcess singleTonProcess;


    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerViewEvent;
    View eventview;
    OhMyEventAdapter ohMyEventAdapter;
    String typeevent;
    TextView Add_hide;
    ArrayList<OhMyEventsDTO.OhMyEventData> ohMyEventDataArrayList = new ArrayList<OhMyEventsDTO.OhMyEventData>();

    private OnFragmentInteractionListener mListener;

//    public OhMyEventSevice() {
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CoachUserReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public  OhMyEventSevice newInstance(String text, String type) {
        OhMyEventSevice mFragment = new OhMyEventSevice();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mBundle.putString("type",type);
//        typeevent = type;
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            sharedPreferences = getContext().getSharedPreferences("Reg", 0);
            editor = sharedPreferences.edit();
            session = new SessionManagement(getContext());

            if (sharedPreferences.contains("KEY_Coach_ID"))
            {
                coachid_ = sharedPreferences.getString("KEY_Coach_ID", "");

            }
            if (sharedPreferences.contains("Email"))
            {
                uPassword = sharedPreferences.getString("Email", "");
            }
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         eventview = inflater.inflate(R.layout.fragment_oh_my_event_sevice, container, false);
          initview();
        return eventview;
    }


    public void initview(){
        recyclerViewEvent = (RecyclerView)eventview.findViewById(R.id.eventRecycleview);
        Add_hide = (TextView) eventview.findViewById(R.id.add_hide);
        recyclerViewEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1);
        recyclerViewEvent.setAdapter(ohMyEventAdapter);
//        adddata();
        if(mParam1.equals("animation")){
        getAnimation();
        }else if(mParam1.equals("stage")){
            getStage();
        }else if(mParam1.equals("tournament")){
            getTournament();
        }
    }

//    public  void adddata(){
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("Guna","25 DEC","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("Bala","11 OCT","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("King","12 JAN","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventDataArrayList.add(new OhMyEventsDTO.OhMyEventData("Won","15 AUG","","Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's.Lorem Ipsum is simply  text of the printing and typesetting industry. Lorem Ipsum has been the industry's."));
//        ohMyEventAdapter.notifyDataSetChanged();
//    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        menumain = menu;
        mSearchCheck = false;
        menu.findItem(Menus.ADD).setVisible(true);
        menu.findItem(Menus.ADD).setIcon(R.drawable.ic_action_add);
        menu.findItem(Menus.EDIT).setVisible(false);
        menu.findItem(Menus.UPDATE).setVisible(false);
        menu.findItem(Menus.SEARCH).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {

            case Menus.ADD:
//                Fragment fragment = null;
//                fragment =  new StageFragmentPage().newInstance(Utils.getTitleItem(getContext(), Constant.MENU_STAGE_EDIT));
//                String tag = fragment.getTag();
//                menumain.findItem(Menus.EDIT).setVisible(true);
//                ((RealMainPageActivity)getContext()).hideMenus(menumain,14);
                if(mParam1.equals("stage")){
                ((RealMainPageActivity)getContext()).setLastPosition(Constant.MENU_STAGE_EDIT);
                ((RealMainPageActivity)getContext()).setEventydata("insert","");
                ((RealMainPageActivity)getContext()).setFragmentList(Constant.MENU_STAGE_EDIT);

                }else if(mParam1.equals("animation")){
                    ((RealMainPageActivity)getContext()).setLastPosition(Constant.MENU_ANIMATION_EDIT);
                    ((RealMainPageActivity)getContext()).setEventydata("insert","");
                    ((RealMainPageActivity)getContext()).setFragmentList(Constant.MENU_ANIMATION_EDIT);
                }else if(mParam1.equals("tournament")){
                    ((RealMainPageActivity)getContext()).setLastPosition(Constant.MENU_TOURNAMENT_EDIT);
                    ((RealMainPageActivity)getContext()).setEventydata("insert","");
                    ((RealMainPageActivity)getContext()).setFragmentList(Constant.MENU_TOURNAMENT_EDIT);
                }
//                ((RealMainPageActivity)getContext()).getSupportFragmentManager().beginTransaction().add(R.id.content_frame,fragment)
//                        .addToBackStack(tag).commit();
                break;
            case Menus.EDIT:

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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

    public void getAnimation()
    {
        ((RealMainPageActivity)getContext()).showprocess();

        apiInterface.getanimationcourse(coachid_).enqueue(new Callback<AnimationDTO>() {
            @Override
            public void onResponse(@NonNull Call<AnimationDTO> call, @NonNull Response<AnimationDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    if(response.body().getData().getCourse().size() != 0){
                        Add_hide.setVisibility(View.GONE);
                     animationCourseArrayList = response.body().getData().getCourse();

                     ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1);
                     recyclerViewEvent.setAdapter(ohMyEventAdapter);
                    }else {
                        Add_hide.setVisibility(View.VISIBLE);
                    }
                    ((RealMainPageActivity)getContext()).dismissprocess();
                }else {
                    ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<AnimationDTO> call, Throwable t) {
                ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }

    public void getStage()
    {
        ((RealMainPageActivity)getContext()).showprocess();
        apiInterface.getstagecourse(coachid_).enqueue(new Callback<StageDTO>() {
            @Override
            public void onResponse(@NonNull Call<StageDTO> call, @NonNull Response<StageDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    if(response.body().getData().getCourse().size() != 0){
                        Add_hide.setVisibility(View.GONE);
                        stageCourseArrayList = response.body().getData().getCourse();
                        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1);
                        recyclerViewEvent.setAdapter(ohMyEventAdapter);
                    }else {
                        Add_hide.setVisibility(View.VISIBLE);
                    }
                    ((RealMainPageActivity)getContext()).dismissprocess();
                }else {
                    ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<StageDTO> call, Throwable t) {
                ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }



    public void getTournament()
    {
        ((RealMainPageActivity)getContext()).showprocess();
        apiInterface.gettournamentcourse(coachid_).enqueue(new Callback<TournamentDTO>() {
            @Override
            public void onResponse(@NonNull Call<TournamentDTO> call, @NonNull Response<TournamentDTO> response) {
                assert response.body() != null;
                System.out.println("- --> " + new Gson().toJson(response.body()));
                if(response.body().getIsSuccess().equals("true")){
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                    if(response.body().getData().getCourse().size() != 0){
                        Add_hide.setVisibility(View.GONE);
                        tournamentCourseArrayList = response.body().getData().getCourse();
                        ohMyEventAdapter = new OhMyEventAdapter(getContext(),animationCourseArrayList,stageCourseArrayList,tournamentCourseArrayList,mParam1);
                        recyclerViewEvent.setAdapter(ohMyEventAdapter);
                    }else {
                        Add_hide.setVisibility(View.VISIBLE);
                    }
                    ((RealMainPageActivity)getContext()).dismissprocess();
                }else {
                    ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TournamentDTO> call, Throwable t) {
                ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }



}
