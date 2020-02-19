package com.tech.cloudnausor.ohmytennispro.activity.coachuserreservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.adapter.CoachUserReservationAdapter;
import com.tech.cloudnausor.ohmytennispro.model.CoachUserReserveModel;
import com.tech.cloudnausor.ohmytennispro.utils.MyAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class CoachUserReservation extends AppCompatActivity {
    RecyclerView coachUserReservationCycle;
    CoachUserReservationAdapter coachUserReservationAdapter;
    ArrayList<String> reservationHeading = new ArrayList<String>();
    ArrayList<String> reserveName = new ArrayList<String>();
    ArrayList<String> reserveredDate = new ArrayList<String>();
    ArrayList<String> reserveredTime = new ArrayList<String>();
    ArrayList<String> reserveStatus = new ArrayList<String>();
    ArrayList<CoachUserReserveModel> coachUserReserveModelArrayList = new ArrayList<CoachUserReserveModel>();
    MyAutoCompleteTextView Reserve_Filter;
    ArrayAdapter adapter;
    List<String> Reserve_fliter_value;
    ArrayList<String> Reserve_fliter_arraylist = new ArrayList<>();
    ImageView GoBack;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_coach_user_reservation);
        toolbar = findViewById(R.id.toolbar_indi);
        GoBack = (ImageView)findViewById(R.id.back);
        Reserve_fliter_value = Reserve_fliter_arraylist;
        Reserve_fliter_arraylist.clear();
        Reserve_fliter_arraylist.add("Toute");
        Reserve_fliter_arraylist.add("Annulé");
        Reserve_fliter_arraylist.add("Terminé");
        Reserve_fliter_arraylist.add("Pas encore confirmé");
        Reserve_fliter_value = Reserve_fliter_arraylist;

        adapter = new ArrayAdapter<String>(CoachUserReservation.this,android.R.layout.select_dialog_item, Reserve_fliter_value);
        Reserve_Filter = (MyAutoCompleteTextView) findViewById(R.id.reserve_filter);
        Reserve_Filter.setAdapter(adapter);
        coachUserReservationCycle = (RecyclerView)findViewById(R.id.reserve_cycle);
        coachUserReservationCycle.setLayoutManager(new LinearLayoutManager(this));
//        coachUserReservationAdapter = new CoachUserReservationAdapter(CoachUserReservation.this,coachUserReserveModelArrayList);
//        coachUserReservationAdapter = new CoachUserReservationAdapter(CoachUserReservation.this,getApplicationContext(),coachUserReserveModelArrayList);
//        coachUserReservationCycle.setAdapter(coachUserReservationAdapter);
//        adapterData();
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
                        coachUserReservationAdapter.filter("2");
                        break;
                    case "Confirmé":
                        coachUserReservationAdapter.filter("1");
                        break;
                    case "Pas encore confirmé":
                        coachUserReservationAdapter.filter("0");
                        break;
                    default:
                        coachUserReservationAdapter.filter("");
                        break;
                }

            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public void adapterData(){

        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training",
                "Bala","30/08/2019","de 6h à 12h","0"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Chandran","30/08/2019"
                ,"de 6h à 12h","1"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Guna","30/08/2019"
                ,"de 6h à 12h","2"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Sekaran","30/08/2019"
                ,"de 6h à 12h","2"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Balachandran","30/08/2019"
                ,"de 6h à 12h","0"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Gunasekaran","30/08/2019"
                ,"de 6h à 12h","1"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Vijayalakshmi","30/08/2019"
                ,"de 6h à 12h","0"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Boobalan","30/08/2019"
                ,"de 6h à 12h","1"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Cours de premiers soins - ATH Training","Suresh","30/08/2019"
                ,"de 6h à 12h","1"));
        coachUserReserveModelArrayList.add(new CoachUserReserveModel("Entraineurs de tennis britanniques","Pushpavalli","30/08/2019"
                ,"de 6h à 12h","2"));
//        coachUserReservationAdapter = new CoachUserReservationAdapter(CoachUserReservation.this,getApplicationContext(),coachUserReserveModelArrayList);
//        coachUserReservationCycle.setAdapter(coachUserReservationAdapter);

        reservationHeading.add("Cours de premiers soins - ATH Training");
        reservationHeading.add("Entraineurs de tennis britanniques");
        reservationHeading.add("Cours de premiers soins - ATH Training");
        reservationHeading.add("Entraineurs de tennis britanniques");
        reservationHeading.add("Cours de premiers soins - ATH Training");
        reservationHeading.add("Entraineurs de tennis britanniques");
        reservationHeading.add("Cours de premiers soins - ATH Training");
        reservationHeading.add("Entraineurs de tennis britanniques");
        reservationHeading.add("Cours de premiers soins - ATH Training");
        reservationHeading.add("Entraineurs de tennis britanniques");
        reserveName.add("Bala");
        reserveName.add("Chandran");
        reserveName.add("Guna");
        reserveName.add("Sekaran");
        reserveName.add("Balachandran");
        reserveName.add("Gunasekaran");
        reserveName.add("Bala");
        reserveName.add("Chandran");
        reserveName.add("Guna");
        reserveName.add("Sekaran");
        reserveredDate.add("30/08/2019");
        reserveredDate.add("1/09/2019");
        reserveredDate.add("3/09/2019");
        reserveredDate.add("30/09/2019");
        reserveredDate.add("3/10/2019");
        reserveredDate.add("8/10/2019");
        reserveredDate.add("11/10/2019");
        reserveredDate.add("29/10/2019");
        reserveredDate.add("30/08/2019");
        reserveredDate.add("1/09/2019");
        reserveredTime.add("de 6h à 12h");
        reserveredTime.add("de 10h à 14h");
        reserveredTime.add("de 2h à 12h");
        reserveredTime.add("de 13h à 24h");
        reserveredTime.add("de 12h à 12h");
        reserveredTime.add("de 10h à 2h");
        reserveredTime.add("de 2h à 7h");
        reserveredTime.add("de 8h à 1h");
        reserveredTime.add("de 6h à 12h");
        reserveredTime.add("de 10h à 14h");
        reserveStatus.add("0");
        reserveStatus.add("1");
        reserveStatus.add("2");
        reserveStatus.add("2");
        reserveStatus.add("0");
        reserveStatus.add("1");
        reserveStatus.add("0");
        reserveStatus.add("1");
        reserveStatus.add("1");
        reserveStatus.add("2");
        coachUserReservationAdapter.notifyDataSetChanged();
    }
}
