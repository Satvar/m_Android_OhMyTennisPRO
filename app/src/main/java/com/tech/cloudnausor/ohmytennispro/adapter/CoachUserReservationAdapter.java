package com.tech.cloudnausor.ohmytennispro.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.dto.CourseMoreDetailsDTO;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.CollectiveCourseFragment;
import com.tech.cloudnausor.ohmytennispro.model.BookingData;
import com.tech.cloudnausor.ohmytennispro.model.BookingDataDetails;
import com.tech.cloudnausor.ohmytennispro.model.CoachUserReserveModel;
import com.tech.cloudnausor.ohmytennispro.response.BokingDataResponseData;
import com.tech.cloudnausor.ohmytennispro.response.BookingRequestStatusResponse;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachUserReservationAdapter extends RecyclerView.Adapter<CoachUserReservationAdapter.CoachUserReservationAdapterHolder> {
  Context context ;
    ArrayList<BookingDataDetails> coachUserReserveModelArrayList = new ArrayList<BookingDataDetails>();
    ArrayList<BookingDataDetails> coachUserReserveModelArrayListFilter = new ArrayList<BookingDataDetails>();
    View view_main;
    CourseMoreDetailsDTO.Booking bookingMore = new  CourseMoreDetailsDTO.Booking();
    SingleTonProcess singleTonProcess;
    String pricess = "0";



    Activity f;
    ApiInterface apiInterface;
    CollectiveCourseFragment collectiveCourseFragment = new CollectiveCourseFragment();
   String status ="";
ClickListener clickListener;

    ArrayList<String> reservationHeading = new ArrayList<String>();
  ArrayList<String> reserveName = new ArrayList<String>();
  ArrayList<String> reserveredDate = new ArrayList<String>();
  ArrayList<String> reserveredTime = new ArrayList<String>();
  ArrayList<String> reserveStatus = new ArrayList<String>();

//    public CoachUserReservationAdapter(Context context, ArrayList<String> reservationHeading, ArrayList<String> reserveName, ArrayList<String> reserveredDate, ArrayList<String> reserveredTime, ArrayList<String> reserveStatus) {
//        this.context = context;
//        this.reservationHeading = reservationHeading;
//        this.reserveName = reserveName;
//        this.reserveredDate = reserveredDate;
//        this.reserveredTime = reserveredTime;
//        this.reserveStatus = reserveStatus;
//    }

    interface ClickListener {
        void onItemClicked();
    }

    public CoachUserReservationAdapter(Activity t,Context context, ArrayList<BookingDataDetails> coachUserReserveModelArrayList) {
        this.f = t;
        this.context = context;
        this.coachUserReserveModelArrayList = coachUserReserveModelArrayList;
        this.coachUserReserveModelArrayListFilter.addAll(this.coachUserReserveModelArrayList);
        this.apiInterface = ApiClient.getClient().create(ApiInterface.class);
        this.singleTonProcess = this.singleTonProcess.getInstance();
    }

    @NonNull
    @Override
    public CoachUserReservationAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_main = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_list_coach,parent,false);
        return new CoachUserReservationAdapterHolder(view_main);
    }

    @Override
    public void onBindViewHolder(@NonNull final CoachUserReservationAdapterHolder holder, int position) {
        if(!coachUserReserveModelArrayListFilter.isEmpty()){
            BookingDataDetails coachUserReserveModel = coachUserReserveModelArrayListFilter.get(position);
            if(coachUserReserveModel != null){
                if(coachUserReserveModel.getBookingCourse() != null){
                    if(coachUserReserveModel.getBookingCourse().equals("CoursCollectifOndemand")){
                        System.out.println(" coachUserReserveModel---> "+new Gson().toJson(coachUserReserveModel));

                    holder.Reserve_Heading.setText("Cours collectif on demand");
                    holder.Dialog_user_course.setText("Cours collectif on demand");
                    holder.Discout_layout.setVisibility(View.GONE);
                        holder.layout_stage.setVisibility(View.GONE);
                        holder.hours_layout.setVisibility(View.VISIBLE);
                        holder.Dialog_Dis_Price.setText(coachUserReserveModel.getAmount().toString());
                        if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
                            holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
                            holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
                        }else {
                            holder.Reserve_Name.setText(" - ");
                        }

                        if(coachUserReserveModel.getBookingDate()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
                        }

                        if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
                        }if(coachUserReserveModel.getStatus() != null){

                            switch (coachUserReserveModel.getStatus()){
                                case "R":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(true);
                                    holder.Reserve_Accept.setAlpha(1);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha(1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Demande de réservation");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    break;
                                case "A":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Approuvé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "B":
                                    holder.Reserve_Modify.setEnabled(true);
                                    holder.Reserve_Modify.setAlpha((float) 1);

//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Réservé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "C":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);

//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "UC":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Utilisatrice Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "S":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Reprogrammer");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;

                                default:
                                    holder.Reserve_Status.setText("");
                                    holder.Reserve_Status.setVisibility(View.GONE);
                                    break;
                            }

                        }else {
                            holder.Reserve_Status.setText("");
                            holder.Reserve_Status.setVisibility(View.GONE);
                        }
                    }else if(coachUserReserveModel.getBookingCourse().equals("CoursIndividuel")){
                        holder.Reserve_Heading.setText("Cours individuel");
                        holder.Dialog_user_course.setText("Cours individuel");
                        holder.Discout_layout.setVisibility(View.GONE);
                        holder.layout_stage.setVisibility(View.GONE);
                        holder.hours_layout.setVisibility(View.VISIBLE);
                        holder.Dialog_Dis_Price.setText(coachUserReserveModel.getAmount().toString());
                        if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
                            holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
                            holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
                        }else {
                            holder.Reserve_Name.setText(" - ");
                        }

                        if(coachUserReserveModel.getBookingDate()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
                        }

                        if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
                        }
                        if(coachUserReserveModel.getStatus() != null){

                            switch (coachUserReserveModel.getStatus()){
                                case "R":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(true);
                                    holder.Reserve_Accept.setAlpha(1);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha(1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Demande de réservation");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    break;
                                case "A":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Approuvé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "B":
                                    holder.Reserve_Modify.setEnabled(true);
                                    holder.Reserve_Modify.setAlpha((float) 1);

//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Réservé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "C":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);

//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "UC":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Utilisatrice Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "S":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Reprogrammer");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;

                                default:
                                    holder.Reserve_Status.setText("");
                                    holder.Reserve_Status.setVisibility(View.GONE);
                                    break;
                            }

                        }else {
                            holder.Reserve_Status.setText("");
                            holder.Reserve_Status.setVisibility(View.GONE);
                        }
                    }else if(coachUserReserveModel.getBookingCourse().equals("CoursCollectifClub")){
                        holder.Reserve_Heading.setText("Cour collectif club");
                        holder.Dialog_user_course.setText("Cour collectif club");
                        holder.Discout_layout.setVisibility(View.VISIBLE);
                        holder.layout_stage.setVisibility(View.GONE);
                        holder.hours_layout.setVisibility(View.VISIBLE);
                        holder.Dialog_Dis_Price.setText(coachUserReserveModel.getAmount().toString());
                        if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
                            holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
                            holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
                        }else {
                            holder.Reserve_Name.setText(" - ");
                        }

                        if(coachUserReserveModel.getBookingDate()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
                        }

                        if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
                        }
                        if(coachUserReserveModel.getStatus() != null){

                            switch (coachUserReserveModel.getStatus()){
                                case "R":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(true);
                                    holder.Reserve_Accept.setAlpha(1);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha(1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Demande de réservation");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    break;
                                case "A":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Approuvé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "B":
                                    holder.Reserve_Modify.setEnabled(true);
                                    holder.Reserve_Modify.setAlpha((float) 1);

//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Réservé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "C":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);

//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "UC":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Utilisatrice Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "S":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Reprogrammer");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;

                                default:
                                    holder.Reserve_Status.setText("");
                                    holder.Reserve_Status.setVisibility(View.GONE);
                                    break;
                            }

                        }else {
                            holder.Reserve_Status.setText("");
                            holder.Reserve_Status.setVisibility(View.GONE);
                        }
                    }else if(coachUserReserveModel.getBookingCourse().equals("Stage")){
                        holder.Reserve_Heading.setText("Stage");
                        holder.Dialog_user_course.setText("Stage");
                        holder.Discout_layout.setVisibility(View.GONE);
                        holder.layout_stage.setVisibility(View.GONE);
                        holder.hours_layout.setVisibility(View.GONE);
                        holder.Dialog_Dis_Price.setText(coachUserReserveModel.getAmount().toString());
                        if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
                            holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
                            holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
                        }else {
                            holder.Reserve_Name.setText(" - ");
                        }

                        if(coachUserReserveModel.getBookingDate()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
                        }

                        if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
                        }
                        if(coachUserReserveModel.getStatus() != null){

                            switch (coachUserReserveModel.getStatus()){
                                case "R":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(true);
                                    holder.Reserve_Accept.setAlpha(1);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha(1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Demande de réservation");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    break;
                                case "A":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Approuvé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "B":
                                    holder.Reserve_Modify.setEnabled(true);
                                    holder.Reserve_Modify.setAlpha((float) 1);

//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Réservé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "C":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);

//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "UC":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Utilisatrice Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "S":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Reprogrammer");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;

                                default:
                                    holder.Reserve_Status.setText("");
                                    holder.Reserve_Status.setVisibility(View.GONE);
                                    break;
                            }

                        }else {
                            holder.Reserve_Status.setText("");
                            holder.Reserve_Status.setVisibility(View.GONE);
                        }
                    }else if(coachUserReserveModel.getBookingCourse().equals("Tournoi")){
                        apiInterface.getbookcourse(coachUserReserveModel.getBookingCourse(),coachUserReserveModel.getBooking_Id()).enqueue(new Callback<CourseMoreDetailsDTO>() {
                            @Override
                            public void onResponse(@NonNull Call<CourseMoreDetailsDTO> call, @NonNull Response<CourseMoreDetailsDTO> response) {
                                System.out.println("response.body()--->" + new Gson().toJson( response.body() ));
                                if(response.body().getIsSuccess().equals("true")){
                                    CourseMoreDetailsDTO.Booking bookingTournoi = new CourseMoreDetailsDTO.Booking();
                                    bookingTournoi= response.body().getBookingArray().getBookings().get(0);

                                    holder.diag_com_name.setText(bookingTournoi.getName_of_company());
                                    holder.diag_com_email.setText(bookingTournoi.getEmail());
                                    holder.diag_phone.setText(bookingTournoi.getMobile());
                                    holder.diag_com_place.setText(bookingTournoi.getAddress());
                                    holder.diag_com_no_person.setText(bookingTournoi.getNumber_of_person());
                                    String utcDateStr = bookingTournoi.getDate();
                                    SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    Date date = null;
                                    try {
                                        date = sdf.parse(utcDateStr);
                                        //                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
//                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();

//                            date = date +1 ;
                                        holder.diag_com_date.setText( new  SimpleDateFormat("yyyy-MM-dd").format(date));

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
//                                    holder.diag_com_date.setText(bookingTournoi.getDate());
                                    holder.diag_com_level.setText(bookingTournoi.getLevel());


                                }else {
                                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }
                            @Override
                            public void onFailure(Call<CourseMoreDetailsDTO> call, Throwable t) {
                                System.out.println("---> "+ call +" "+ t);
                            }
                        });

//                        CourseMoreDetailsDTO.Booking bookingTournoi = new CourseMoreDetailsDTO.Booking();
//                        bookingTournoi = moredetails(coachUserReserveModel.getBookingCourse(),coachUserReserveModel.getBooking_Id());
                        holder.Reserve_Heading.setText("Tournoi");
                        holder.Dialog_user_course.setText("Tournoi");
                        holder.postal_view.setVisibility(View.GONE);
                        holder.emplacement_view.setVisibility(View.GONE);
                        holder.Discout_layout.setVisibility(View.VISIBLE);
                        holder.layout_stage.setVisibility(View.VISIBLE);
                        holder.hours_layout.setVisibility(View.GONE);
                        holder.Dialog_Dis_Price.setText(coachUserReserveModel.getAmount().toString());
                        if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
                            holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
                            holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
                        }else {
                            holder.Reserve_Name.setText(" - ");
                        }

                        if(coachUserReserveModel.getBookingDate()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
                        }

                        if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
                        }
                        if(coachUserReserveModel.getStatus() != null){

                            switch (coachUserReserveModel.getStatus()){
                                case "R":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(true);
                                    holder.Reserve_Accept.setAlpha(1);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha(1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Demande de réservation");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    break;
                                case "A":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Approuvé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "B":
                                    holder.Reserve_Modify.setEnabled(true);
                                    holder.Reserve_Modify.setAlpha((float) 1);

//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Réservé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "C":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);

//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "UC":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Utilisatrice Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "S":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Reprogrammer");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;

                                default:
                                    holder.Reserve_Status.setText("");
                                    holder.Reserve_Status.setVisibility(View.GONE);
                                    break;
                            }

                        }else {
                            holder.Reserve_Status.setText("");
                            holder.Reserve_Status.setVisibility(View.GONE);
                        }

//                        holder.diag_com_name.setText(bookingTournoi.getName_of_company());
//                        holder.diag_com_email.setText(bookingTournoi.getEmail());
//                        holder.diag_phone.setText(bookingTournoi.getMobile());
//                        holder.diag_com_place.setText(bookingTournoi.getAddress());
//                        holder.diag_com_no_person.setText(bookingTournoi.getNumber_of_person());
//                        holder.diag_com_date.setText(bookingTournoi.getDate());
//                        holder.diag_com_level.setText(bookingTournoi.getLevel());
                    }else if(coachUserReserveModel.getBookingCourse().equals("TeamBuilding")){

                        apiInterface.getbookcourse(coachUserReserveModel.getBookingCourse(),coachUserReserveModel.getBooking_Id()).enqueue(new Callback<CourseMoreDetailsDTO>() {
                            @Override
                            public void onResponse(@NonNull Call<CourseMoreDetailsDTO> call, @NonNull Response<CourseMoreDetailsDTO> response) {
                                System.out.println("response.body()--->" + new Gson().toJson( response.body() ));
                                if(response.body().getIsSuccess().equals("true")){
                                    CourseMoreDetailsDTO.Booking bookingTeamBuilding = new CourseMoreDetailsDTO.Booking();
                                    bookingTeamBuilding= response.body().getBookingArray().getBookings().get(0);

                                    holder.diag_com_name.setText(bookingTeamBuilding.getName_of_company());
                                    holder.diag_com_email.setText(bookingTeamBuilding.getEmail());
                                    holder.diag_phone.setText(bookingTeamBuilding.getMobile());
                                    holder.diag_com_place.setText(bookingTeamBuilding.getAddress());
                                    holder.diag_com_no_person.setText(bookingTeamBuilding.getNumber_of_person());
//                                    holder.diag_com_date.setText(bookingTeamBuilding.getDate());
                                    String utcDateStr = bookingTeamBuilding.getDate();
                                    SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    Date date = null;
                                    try {
                                        date = sdf.parse(utcDateStr);
                                        //                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
//                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();

//                            date = date +1 ;
                                        holder.diag_com_date.setText( new  SimpleDateFormat("yyyy-MM-dd").format(date));

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    holder.diag_com_level.setText(bookingTeamBuilding.getLevel());

                                }else {
                                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }
                            @Override
                            public void onFailure(Call<CourseMoreDetailsDTO> call, Throwable t) {
                                System.out.println("---> "+ call +" "+ t);
                            }
                        });


//                        CourseMoreDetailsDTO.Booking bookingTeamBuilding = new CourseMoreDetailsDTO.Booking();
//                        bookingTeamBuilding = moredetails(coachUserReserveModel.getBookingCourse(),coachUserReserveModel.getBooking_Id());
                        holder.Reserve_Heading.setText("Team Building");
                        holder.Dialog_user_course.setText("Team Building");
                        holder.Discout_layout.setVisibility(View.VISIBLE);
                        holder.layout_stage.setVisibility(View.VISIBLE);
                        holder.hours_layout.setVisibility(View.GONE);
                        holder.postal_view.setVisibility(View.GONE);
                        holder.level_view.setVisibility(View.GONE);

                        holder.Dialog_Dis_Price.setText(coachUserReserveModel.getAmount().toString());
                        if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
                            holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
                            holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
                        }else {
                            holder.Reserve_Name.setText(" - ");
                        }

                        if(coachUserReserveModel.getBookingDate()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
                        }

                        if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
                        }
                        if(coachUserReserveModel.getStatus() != null){

                            switch (coachUserReserveModel.getStatus()){
                                case "R":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(true);
                                    holder.Reserve_Accept.setAlpha(1);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha(1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Demande de réservation");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    break;
                                case "A":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Approuvé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "B":
                                    holder.Reserve_Modify.setEnabled(true);
                                    holder.Reserve_Modify.setAlpha((float) 1);

//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Réservé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "C":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);

//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "UC":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Utilisatrice Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "S":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Reprogrammer");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;

                                default:
                                    holder.Reserve_Status.setText("");
                                    holder.Reserve_Status.setVisibility(View.GONE);
                                    break;
                            }

                        }else {
                            holder.Reserve_Status.setText("");
                            holder.Reserve_Status.setVisibility(View.GONE);
                        }

//                        holder.diag_com_name.setText(bookingTeamBuilding.getName_of_company());
//                        holder.diag_com_email.setText(bookingTeamBuilding.getEmail());
//                        holder.diag_phone.setText(bookingTeamBuilding.getMobile());
//                        holder.diag_com_place.setText(bookingTeamBuilding.getAddress());
//                        holder.diag_com_no_person.setText(bookingTeamBuilding.getNumber_of_person());
//                        holder.diag_com_date.setText(bookingTeamBuilding.getDate());
//                        holder.diag_com_level.setText(bookingTeamBuilding.getLevel());
                    }else if(coachUserReserveModel.getBookingCourse().equals("Animation")){

                        apiInterface.getbookcourse(coachUserReserveModel.getBookingCourse(),coachUserReserveModel.getBooking_Id()).enqueue(new Callback<CourseMoreDetailsDTO>() {
                            @Override
                            public void onResponse(@NonNull Call<CourseMoreDetailsDTO> call, @NonNull Response<CourseMoreDetailsDTO> response) {
                                System.out.println("response.body()--->" + new Gson().toJson( response.body() ));
                                if(response.body().getIsSuccess().equals("true")){
                                    CourseMoreDetailsDTO.Booking bookingAnimation = new CourseMoreDetailsDTO.Booking();
                                    bookingAnimation= response.body().getBookingArray().getBookings().get(0);

                                    holder.diag_com_name.setText(bookingAnimation.getName_of_company());
                                    holder.diag_com_email.setText(bookingAnimation.getEmail());
                                    holder.diag_phone.setText(bookingAnimation.getMobile());
                                    holder.diag_com_place.setText(bookingAnimation.getAddress());
                                    holder.diag_com_no_person.setText(bookingAnimation.getNumber_of_person());
                                    String utcDateStr = bookingAnimation.getDate();
                                    SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    Date date = null;
                                    try {
                                        date = sdf.parse(utcDateStr);
                                        //                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
//                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();

//                            date = date +1 ;
                                        holder.diag_com_date.setText( new  SimpleDateFormat("yyyy-MM-dd").format(date));

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    holder.diag_com_level.setText(bookingAnimation.getLevel());
                                    holder.diag_com_postal.setText(bookingAnimation.getPostalcode());

                                }else {
                                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();

                                }
                            }
                            @Override
                            public void onFailure(Call<CourseMoreDetailsDTO> call, Throwable t) {
                                System.out.println("---> "+ call +" "+ t);
                            }
                        });



//                        CourseMoreDetailsDTO.Booking bookingAnimation = new CourseMoreDetailsDTO.Booking();
//                        bookingAnimation = moredetails(coachUserReserveModel.getBookingCourse(),coachUserReserveModel.getBooking_Id());
                        holder.Reserve_Heading.setText("Animation");
                        holder.Dialog_user_course.setText("Animation");
                        holder.layout_stage.setVisibility(View.VISIBLE);
                        holder.Discout_layout.setVisibility(View.VISIBLE);
                        holder.hours_layout.setVisibility(View.GONE);
                        holder.Dialog_Dis_Price.setText(coachUserReserveModel.getAmount().toString());
                        if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
                            holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
                            holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
                        }else {
                            holder.Reserve_Name.setText(" - ");
                        }

                        if(coachUserReserveModel.getBookingDate()!=null){
                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
                        }

                        if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){

                            holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
                            holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
                            holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
                        }

                        if(coachUserReserveModel.getStatus() != null){

                            switch (coachUserReserveModel.getStatus()){
                                case "R":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(true);
                                    holder.Reserve_Accept.setAlpha(1);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha(1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Demande de réservation");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
                                    break;
                                case "A":
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Approuvé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "B":
                                    holder.Reserve_Modify.setEnabled(true);
                                    holder.Reserve_Modify.setAlpha((float) 1);

//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleBlue)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(true);
                                    holder.Reserve_Cancel.setAlpha((float) 1);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel)));
                                    holder.Reserve_Status.setText("Réservé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
                                    break;
                                case "C":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);

//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "UC":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Utilisatrice Annulé");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;
                                case "S":
//                            view_main.setVisibility(View.GONE);
//                            holder.Parent_Layout.setVisibility(View.GONE);
                                    holder.Reserve_Modify.setEnabled(false);
                                    holder.Reserve_Modify.setAlpha((float) 0.2);
//                            holder.Reserve_Modify.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_modify_disable)));
                                    holder.Reserve_Accept.setEnabled(false);
                                    holder.Reserve_Accept.setAlpha((float) 0.2);
//                            holder.Reserve_Accept.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_accept_disable)));
                                    holder.Reserve_Cancel.setEnabled(false);
                                    holder.Reserve_Cancel.setAlpha((float) 0.2);
//                            holder.Reserve_Cancel.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.button_cancel_disable)));
                                    holder.Reserve_Status.setText("Reprogrammer");
//                            holder.Reserve_Status.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
                                    break;

                                default:
                                    holder.Reserve_Status.setText("");
                                    holder.Reserve_Status.setVisibility(View.GONE);
                                    break;
                            }

                        }else {
                            holder.Reserve_Status.setText("");
                            holder.Reserve_Status.setVisibility(View.GONE);
                        }

//                        holder.diag_com_name.setText(bookingAnimation.getName_of_company());
//                        holder.diag_com_email.setText(bookingAnimation.getEmail());
//                        holder.diag_phone.setText(bookingAnimation.getMobile());
//                        holder.diag_com_place.setText(bookingAnimation.getAddress());
//                        holder.diag_com_no_person.setText(bookingAnimation.getNumber_of_person());
//                        holder.diag_com_date.setText(bookingAnimation.getDate());
//                        holder.diag_com_level.setText(bookingAnimation.getLevel());


                    }



                }else {
                    holder.Reserve_Heading.setText(" - ");
                    holder.Dialog_user_course.setText(" - ");
                }

//                if(coachUserReserveModel.getFirstName() != null || coachUserReserveModel.getLastName() != null){
//                    holder.Reserve_Name.setText(coachUserReserveModel.getFirstName().toString()+" "+coachUserReserveModel.getLastName());
//                    holder.Dialog_user_name.setText(coachUserReserveModel.getFirstName()+" "+coachUserReserveModel.getLastName());
//                }else {
//                    holder.Reserve_Name.setText(" - ");
//                }
//
//                if(coachUserReserveModel.getBookingDate()!=null){
//                    holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
//                    holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate());
//                }
//
//                if(coachUserReserveModel.getBookingDate()!=null && coachUserReserveModel.getBookingTime()!=null){
//                    holder.Reserve_Date.setText(coachUserReserveModel.getBookingDate());
//                    holder.Reserve_Time.setText(coachUserReserveModel.getBookingTime());
//                    holder.Dialog_user_time_date.setText(coachUserReserveModel.getBookingDate()+"   "+coachUserReserveModel.getBookingTime());
//                }
//                if(coachUserReserveModel.getReserveName() != null){
//                    holder.Reserve_Date.setText(coachUserReserveModel.getReserveredDate());
//                }else {
//                    holder.Reserve_Date.setText(" - ");
//                }
//                if(coachUserReserveModel.getReserveredTime() != null){
//                    holder.Reserve_Time.setText(coachUserReserveModel.getReserveredTime());
//                }else {
//                    holder.Reserve_Time.setText(" - ");
//                }


            }

        }



//            CoachUserReserveModel coachUserReserveModel = coachUserReserveModelArrayList.get(position);
//            if(reservationHeading.get(position) != null){
//                    holder.Reserve_Heading.setText(reservationHeading.get(position));
//                }else {
//                    holder.Reserve_Heading.setText(" - ");
//                }
//                if(reserveName.get(position) != null){
//                    holder.Reserve_Name.setText(reserveName.get(position));
//                }else {
//                    holder.Reserve_Name.setText(" - ");
//                }
//                if(reserveredDate.get(position) != null){
//                    holder.Reserve_Date.setText(reserveredDate.get(position));
//                }else {
//                    holder.Reserve_Date.setText(" - ");
//                }
//                if(reserveredTime.get(position) != null){
//                    holder.Reserve_Time.setText(reserveredTime.get(position));
//                }else {
//                    holder.Reserve_Time.setText(" - ");
//                }
//                if(reserveStatus.get(position) != null){
//
//                    switch (reserveStatus.get(position)){
//                        case "0":
//                            holder.Reserve_Status.setText("Pas encore confirmé");
//                            holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary)));
//                            break;
//                        case "1":
//                            holder.Reserve_Status.setText("Terminé");
//                            holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorGoogleGreen)));
//                            break;
//                        case "2":
//                            holder.Reserve_Status.setText("Annulé");
//                            holder.Reserve_Status.setVisibility(View.VISIBLE);
//                            holder.Reserve_Status.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.redcolor)));
//                            break;
//
//                        default:
//                            holder.Reserve_Status.setText("");
//                            holder.Reserve_Status.setVisibility(View.GONE);
//                            break;
//                    }
//
//                }else {
//                    holder.Reserve_Status.setText("");
//                    holder.Reserve_Status.setVisibility(View.GONE);
//                }

    }

    @Override
    public int getItemCount() {
        return (null != coachUserReserveModelArrayListFilter? coachUserReserveModelArrayListFilter.size() : 0);
    }

    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {

            @Override
            public void run() {

                // Clear the filter list
                coachUserReserveModelArrayListFilter.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    coachUserReserveModelArrayListFilter.addAll(coachUserReserveModelArrayList);

                } else {
                    System.out.println("testing bala filter 666 " + coachUserReserveModelArrayList);
                    // Iterate in the original List and add it to filter list...
                    for (BookingDataDetails item : coachUserReserveModelArrayList) {
                        System.out.println("testing bala filter 1"+item.getStatus());

                        if (item.getStatus().contains(text)) {
                            System.out.println("testing bala filter 1");

                            // Adding Matched items
                            coachUserReserveModelArrayListFilter.add(item);
                        }
                    }
                }

                // Set on UI Thread
                (f).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("testing bala filter 1");

                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }


    public class CoachUserReservationAdapterHolder extends RecyclerView.ViewHolder {
        TextView  Reserve_Heading,Reserve_Name,Reserve_Date,Reserve_Time;
        Button Reserve_Accept,Reserve_Cancel,Reserve_Modify;
        TextView Reserve_Status;
        LinearLayout Parent_Layout,status_bar;
        AlertDialog.Builder dialogBuilder;
        AlertDialog alertDialog;
        View dialogView;
        LayoutInflater inflater;
        TextView Dialog_user_name,Dialog_user_course,Dialog_user_time_date;
        Button Dialog_set_status,Dialog_close;
        TextView Reserve_dialog_heading;
        EditText Dialog_Dis_Price;
        LinearLayout Discout_layout,layout_stage,postal_view,emplacement_view,level_view;
        RelativeLayout hours_layout;
        TextView diag_com_name,diag_com_email,diag_com_level,diag_phone,diag_com_date,diag_com_place,diag_com_postal,diag_com_no_person;

        public CoachUserReservationAdapterHolder(@NonNull View itemView) {
            super(itemView);
            Parent_Layout =(LinearLayout)itemView.findViewById(R.id.reverse_parent_layout) ;
            status_bar = (LinearLayout)itemView.findViewById(R.id.status_bar) ;
            Reserve_Heading = (TextView)itemView.findViewById(R.id.reserve_heading);
            Reserve_Name = (TextView)itemView.findViewById(R.id.reserve_name);
            Reserve_Date = (TextView)itemView.findViewById(R.id.reserve_date);
            Reserve_Time  = (TextView)itemView.findViewById(R.id.reserve_time);
            Reserve_Status = (TextView)itemView.findViewById(R.id.reserve_status);
            Reserve_Accept = (Button)itemView.findViewById(R.id.reserve_accept);
            Reserve_Cancel = (Button)itemView.findViewById(R.id.reserve_cancel);
            Reserve_Modify = (Button)itemView.findViewById(R.id.reserve_modify);
            hours_layout  = (RelativeLayout) itemView.findViewById(R.id.hours_layout);
            dialogBuilder = new AlertDialog.Builder(f);
            inflater = LayoutInflater.from(context);
            dialogView = inflater.inflate(R.layout.dialiog_reservation_confirm_cancel, null);
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            Dialog_user_name = (TextView)dialogView.findViewById(R.id.dialog_user_name) ;
            Dialog_user_course = (TextView)dialogView.findViewById(R.id.dialog_user_course) ;
            Dialog_user_time_date = (TextView)dialogView.findViewById(R.id.dialog_date_time) ;
            Dialog_set_status =(Button)dialogView.findViewById(R.id.dialog_set_status);
            Dialog_close =(Button)dialogView.findViewById(R.id.dialog_close);
            layout_stage = (LinearLayout) dialogView.findViewById(R.id.layout_stage);
            postal_view = (LinearLayout) dialogView.findViewById(R.id.postal_view);
            emplacement_view = (LinearLayout)dialogView.findViewById(R.id.emplacement_view);
            level_view =(LinearLayout)dialogView.findViewById(R.id.level_view);
            diag_com_name = (TextView)dialogView.findViewById(R.id.diag_com_name) ;
                    diag_com_email= (TextView)dialogView.findViewById(R.id.diag_com_email) ;
            diag_phone= (TextView)dialogView.findViewById(R.id.diag_phone) ;
                    diag_com_date= (TextView)dialogView.findViewById(R.id.diag_com_date) ;
            diag_com_place= (TextView)dialogView.findViewById(R.id.diag_com_place) ;
                    diag_com_postal= (TextView)dialogView.findViewById(R.id.diag_com_postal) ;
            diag_com_no_person= (TextView)dialogView.findViewById(R.id.diag_com_no_person) ;

            diag_com_level= (TextView)dialogView.findViewById(R.id.diag_com_level) ;

            Reserve_dialog_heading = (TextView)dialogView.findViewById(R.id.reserve_dialog_heading);
            Dialog_Dis_Price =(EditText)dialogView.findViewById(R.id.dialog_dis_price);
            Discout_layout = (LinearLayout)dialogView.findViewById(R.id.discout_layout);

            Reserve_Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reserve_dialog_heading.setText("Approuver  formulaire");
                    Dialog_set_status.setText("Demande acceptée");
                    Dialog_Dis_Price.setEnabled(true);
                    status = "A";
                    alertDialog.show();
                }
            });

            Reserve_Modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reserve_dialog_heading.setText("Replanifier  formulaire");
                    Dialog_set_status.setText("Replanifier");
                    Dialog_Dis_Price.setEnabled(false);
                    status = "S";
                    alertDialog.show();
                }
            });

            Reserve_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reserve_dialog_heading.setText("Annuler  formulaire");
                    Dialog_set_status.setText("Demande Annuler");
                    Dialog_Dis_Price.setEnabled(false);
                    status = "C";
                    alertDialog.show();
                }
            });

            Dialog_set_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    singleTonProcess.show(context);
                    String dis = "";

                    if(Dialog_Dis_Price.getText().toString() != ""){
                        dis = Dialog_Dis_Price.getText().toString();
                    }else {
                        dis = "";
                    }

                    if(coachUserReserveModelArrayList.get(getAdapterPosition()).getBookingCourse().equals("Animation")) {
                        pricess = Dialog_Dis_Price.getText().toString();
                    }else if(coachUserReserveModelArrayList.get(getAdapterPosition()).getBookingCourse().equals("TeamBuilding")){
                        pricess = Dialog_Dis_Price.getText().toString();
                    }else if(coachUserReserveModelArrayList.get(getAdapterPosition()).getBookingCourse().equals("Tournoi")){
                        pricess = Dialog_Dis_Price.getText().toString();
                    }else{
                        pricess = coachUserReserveModelArrayList.get(getAdapterPosition()).getAmount();
                    }

                    apiInterface.setRequestedStatus( coachUserReserveModelArrayList.get(getAdapterPosition()).getCoach_ID(),status,coachUserReserveModelArrayList.get(getAdapterPosition()).getBooking_Id(),
                            coachUserReserveModelArrayList.get(getAdapterPosition()).getBookingDate(),pricess,
                            coachUserReserveModelArrayList.get(getAdapterPosition()).getBookingCourse(),
                            dis,coachUserReserveModelArrayList.get(getAdapterPosition()).getUser_Id()).enqueue(new Callback<BookingRequestStatusResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<BookingRequestStatusResponse> call, @NonNull Response<BookingRequestStatusResponse> response) {
                           System.out.println(" response data---> " + new Gson().toJson(response.body()));
                            assert response.body() != null;
                            if(response.body().getIsSuccess().equals("true")){
                                 singleTonProcess.dismiss();
                                Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                                ((RealMainPageActivity)context).setLastPosition(Constant.MENU_RESERVATION);
                                ((RealMainPageActivity)context).setFragmentList(Constant.MENU_RESERVATION);

//                                ((RealMainPageActivity)context).setLastPosition(Constant.MENU_RESERVATION);
                            }else {
                                 singleTonProcess.dismiss();
                                Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<BookingRequestStatusResponse> call, Throwable t) {
                             singleTonProcess.dismiss();
                            System.out.println("---> "+ call +" "+ t);
                        }
                    });
                }
            });

            Dialog_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        }
    }


    public CourseMoreDetailsDTO.Booking moredetails(String course,String book_id){
System.out.println("course--->" + course +"book_id--->"+ book_id);
        apiInterface.getbookcourse( course,book_id).enqueue(new Callback<CourseMoreDetailsDTO>() {
            @Override
            public void onResponse(@NonNull Call<CourseMoreDetailsDTO> call, @NonNull Response<CourseMoreDetailsDTO> response) {
                System.out.println("response.body()--->" + new Gson().toJson( response.body() ));
                if(response.body().getIsSuccess().equals("true")){
                    bookingMore= response.body().getBookingArray().getBookings().get(0);
                }else {
                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<CourseMoreDetailsDTO> call, Throwable t) {
                System.out.println("---> "+ call +" "+ t);
            }
        });

        return bookingMore;
    }


}
