package com.tech.cloudnausor.ohmytennispro.fragment.coachuserreservation;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.coachuserreservation.CoachUserReservation;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.adapter.CoachUserReservationAdapter;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.fragment.individualcourse.IndividualCourseFragment;
import com.tech.cloudnausor.ohmytennispro.model.BookingData;
import com.tech.cloudnausor.ohmytennispro.model.BookingDataDetails;
import com.tech.cloudnausor.ohmytennispro.model.CoachUserReserveModel;
import com.tech.cloudnausor.ohmytennispro.model.GetIndiCoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.IndiCourseData;
import com.tech.cloudnausor.ohmytennispro.response.BokingDataResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetIndiCoachDetailsResponse;
import com.tech.cloudnausor.ohmytennispro.session.SessionManagement;
import com.tech.cloudnausor.ohmytennispro.utils.MyAutoCompleteTextView;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CoachUserReservationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CoachUserReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachUserReservationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean mSearchCheck;


    RecyclerView coachUserReservationCycle;
    CoachUserReservationAdapter coachUserReservationAdapter;
    ArrayList<BookingDataDetails> bookingDataDetailsArrayList = new ArrayList<BookingDataDetails>();
    MyAutoCompleteTextView Reserve_Filter;
    ArrayAdapter adapter;
    List<String> Reserve_fliter_value;
    ArrayList<String> Reserve_fliter_arraylist = new ArrayList<>();
    ImageView GoBack;
    Toolbar toolbar;
    ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    SessionManagement session;
    SharedPreferences.Editor editor;
    String edit_sting = null,loginObject;
    String coachid_ = null;
    String uPassword =null;
    SingleTonProcess singleTonProcess;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CoachUserReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CoachUserReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public  CoachUserReservationFragment newInstance(String text) {
        CoachUserReservationFragment mFragment = new CoachUserReservationFragment();
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

        View view_reservation = inflater.inflate(R.layout.fragment_coach_user_reservation, container, false);
        // Inflate the layout for this fragment
        toolbar = view_reservation.findViewById(R.id.toolbar_indi);
        GoBack = (ImageView)view_reservation.findViewById(R.id.back);
        singleTonProcess = singleTonProcess.getInstance();
        Reserve_fliter_value = Reserve_fliter_arraylist;
        Reserve_fliter_arraylist.clear();
        Reserve_fliter_arraylist.add("Toute");
        Reserve_fliter_arraylist.add("Approuvé");
        Reserve_fliter_arraylist.add("Demande de réservation");
        Reserve_fliter_arraylist.add("Réservé");
        Reserve_fliter_arraylist.add("Reprogrammer");
        Reserve_fliter_arraylist.add("Utilisatrice Annulé");
        Reserve_fliter_arraylist.add("Annulé");
        Reserve_fliter_value = Reserve_fliter_arraylist;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, Reserve_fliter_value);
        Reserve_Filter = (MyAutoCompleteTextView) view_reservation.findViewById(R.id.reserve_filter);
        Reserve_Filter.setAdapter(adapter);
        coachUserReservationCycle = (RecyclerView)view_reservation.findViewById(R.id.reserve_cycle);
        coachUserReservationCycle.setLayoutManager(new LinearLayoutManager(getContext()));
//        coachUserReservationAdapter = new CoachUserReservationAdapter(CoachUserReservation.this,coachUserReserveModelArrayList);
        coachUserReservationAdapter = new CoachUserReservationAdapter(getActivity(),getContext(),bookingDataDetailsArrayList);
        coachUserReservationCycle.setAdapter(coachUserReservationAdapter);
//        adapterData();

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

        adapterData();

        Reserve_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reserve_Filter.showDropDown();

            }
        });
        Reserve_Filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String filterValue =  editable.toString();
                System.out.println("testing bala filter" + filterValue);

                switch (filterValue){
                    case "Annulé":
                        coachUserReservationAdapter.filter("C");
                        break;
                    case "Réservé":
                        coachUserReservationAdapter.filter("B");
                        break;
                    case "Demande de réservation":
                        coachUserReservationAdapter.filter("R");
                        break;
                    case "Approuvé":
                        coachUserReservationAdapter.filter("A");
                        break;
                    case "Utilisatrice Annulé":
                        coachUserReservationAdapter.filter("UC");
                        break;
                    case "Reprogrammer":
                        coachUserReservationAdapter.filter("S");
                        break;
                    default:
                        coachUserReservationAdapter.filter("");
                        break;
                }

            }
        });

//        GoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        return view_reservation;
    }

    public void adapterData(){
        ((RealMainPageActivity)getContext()).showprocess();
        apiInterface.getBookingDataResponse(coachid_).enqueue(new Callback<BokingDataResponseData>() {
            @Override
            public void onResponse(@NonNull Call<BokingDataResponseData> call, @NonNull Response<BokingDataResponseData> response) {
                assert response.body() != null;
                if(response.body().getIsSuccess().equals("true")){
                    BookingData bookingData = response.body().getData();
                    bookingDataDetailsArrayList = bookingData.getBookingDataDetailsArrayList();
                    coachUserReservationAdapter = new CoachUserReservationAdapter(getActivity(),getContext(),bookingDataDetailsArrayList);
                    coachUserReservationCycle.setAdapter(coachUserReservationAdapter);
                    coachUserReservationAdapter.notifyDataSetChanged();
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    System.out.println("- --> " + new Gson().toJson(response.body()));
                }else {
                     ((RealMainPageActivity)getContext()).dismissprocess();
                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<BokingDataResponseData> call, Throwable t) {
                 ((RealMainPageActivity)getContext()).dismissprocess();
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }

//    public void adapterData(){
//
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training",
//                "Bala","30/08/2019","de 6h à 12h","0"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Chandran","30/08/2019"
//                ,"de 6h à 12h","1"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Guna","30/08/2019"
//                ,"de 6h à 12h","2"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Sekaran","30/08/2019"
//                ,"de 6h à 12h","2"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Balachandran","30/08/2019"
//                ,"de 6h à 12h","0"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Gunasekaran","30/08/2019"
//                ,"de 6h à 12h","1"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Vijayalakshmi","30/08/2019"
//                ,"de 6h à 12h","0"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Boobalan","30/08/2019"
//                ,"de 6h à 12h","1"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Suresh","30/08/2019"
//                ,"de 6h à 12h","1"));
//        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Pushpavalli","30/08/2019"
//                ,"de 6h à 12h","2"));
//        coachUserReservationAdapter = new CoachUserReservationAdapter(getActivity(),getContext(),coachUserReserveModelArrayList);
//        coachUserReservationCycle.setAdapter(coachUserReservationAdapter);
//
//        reservationHeading.add("Cours de premiers soins - ATH Training");
//        reservationHeading.add("Entraineurs de tennis britanniques");
//        reservationHeading.add("Cours de premiers soins - ATH Training");
//        reservationHeading.add("Entraineurs de tennis britanniques");
//        reservationHeading.add("Cours de premiers soins - ATH Training");
//        reservationHeading.add("Entraineurs de tennis britanniques");
//        reservationHeading.add("Cours de premiers soins - ATH Training");
//        reservationHeading.add("Entraineurs de tennis britanniques");
//        reservationHeading.add("Cours de premiers soins - ATH Training");
//        reservationHeading.add("Entraineurs de tennis britanniques");
//        reserveName.add("Bala");
//        reserveName.add("Chandran");
//        reserveName.add("Guna");
//        reserveName.add("Sekaran");
//        reserveName.add("Balachandran");
//        reserveName.add("Gunasekaran");
//        reserveName.add("Bala");
//        reserveName.add("Chandran");
//        reserveName.add("Guna");
//        reserveName.add("Sekaran");
//        reserveredDate.add("30/08/2019");
//        reserveredDate.add("1/09/2019");
//        reserveredDate.add("3/09/2019");
//        reserveredDate.add("30/09/2019");
//        reserveredDate.add("3/10/2019");
//        reserveredDate.add("8/10/2019");
//        reserveredDate.add("11/10/2019");
//        reserveredDate.add("29/10/2019");
//        reserveredDate.add("30/08/2019");
//        reserveredDate.add("1/09/2019");
//        reserveredTime.add("de 6h à 12h");
//        reserveredTime.add("de 10h à 14h");
//        reserveredTime.add("de 2h à 12h");
//        reserveredTime.add("de 13h à 24h");
//        reserveredTime.add("de 12h à 12h");
//        reserveredTime.add("de 10h à 2h");
//        reserveredTime.add("de 2h à 7h");
//        reserveredTime.add("de 8h à 1h");
//        reserveredTime.add("de 6h à 12h");
//        reserveredTime.add("de 10h à 14h");
//        reserveStatus.add("0");
//        reserveStatus.add("1");
//        reserveStatus.add("2");
//        reserveStatus.add("2");
//        reserveStatus.add("0");
//        reserveStatus.add("1");
//        reserveStatus.add("0");
//        reserveStatus.add("1");
//        reserveStatus.add("1");
//        reserveStatus.add("2");
//        coachUserReservationAdapter.notifyDataSetChanged();
//    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
