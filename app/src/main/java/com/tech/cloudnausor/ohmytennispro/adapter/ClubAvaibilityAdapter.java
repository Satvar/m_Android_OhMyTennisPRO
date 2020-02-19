package com.tech.cloudnausor.ohmytennispro.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.dto.DeleteClubDTO;
import com.tech.cloudnausor.ohmytennispro.model.ClubAvabilityModelData;
import com.tech.cloudnausor.ohmytennispro.response.GetClubResponseData;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubAvaibilityAdapter extends RecyclerView.Adapter<ClubAvaibilityAdapter.ClubAvaibilityHolder> {
    Context context;
    ArrayList<ArrayList<ClubAvabilityModelData>> ClubAvabilityModelDataArrayList;
    ClubAvaibilityAdapterData ClubAvaibilityAdapterData;
    ItemClickListenerRemoveSelection itemClickListenerRemoveSelection;
    FragmentActivity fragmentActivity ;
    String enable ="";
    String coachis_ ="";
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface .class);



//    DefaultCalendarActivity defaultCalendarActivity ;

    public ClubAvaibilityAdapter(Context context, ArrayList<ArrayList<ClubAvabilityModelData>> ClubAvabilityModelDataArrayList,FragmentActivity fragmentActivity,    String enable ,String coachid) {
        this.context = context;
        this.ClubAvabilityModelDataArrayList = ClubAvabilityModelDataArrayList;
        this.fragmentActivity = fragmentActivity;
        this.enable = enable;
        this.coachis_ = coachid;
//        this.defaultCalendarActivity = defaultCalendarActivity;
    }

    @NonNull
    @Override
    public ClubAvaibilityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.club_course_and_details,parent,false);
        return new ClubAvaibilityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClubAvaibilityHolder holder, final int position) {
        ArrayList<ClubAvabilityModelData> ClubAvabilityModelData = ClubAvabilityModelDataArrayList.get(position);
        ClubAvabilityModelData clubAvabilityModelDatass = ClubAvabilityModelData.get(0);
//        holder.timeTitle.setText(ClubAvabilityModelData.getDateTitte());
        ClubAvaibilityAdapterData = new ClubAvaibilityAdapterData(context,ClubAvabilityModelData,fragmentActivity,"1",coachis_);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder. club_course_details_show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder. club_course_details_show.getVisibility() == View.VISIBLE) {
                    holder.club_course_details_show.setVisibility(View.GONE);
                    holder.club_course_details_show_hide.setText("Plus");
                    holder.club_course_details_show_hide.setBackgroundTintList(ColorStateList.valueOf(
                            context.getResources().getColor(R.color.attach)));
                }else if(holder. club_course_details_show.getVisibility() == View.GONE){
                    holder. club_course_details_show.setVisibility(View.VISIBLE);
                holder.club_course_details_show_hide.setText("cacher");
                holder.club_course_details_show_hide.setBackgroundTintList(ColorStateList.valueOf(
                        context.getResources().getColor(R.color.button_cancel)));}
            }
        });

        holder. club_dialog_course_name.setText(clubAvabilityModelDatass.getCourse());
        holder.club_dialog_course_day.setText(clubAvabilityModelDatass.getWeekday());

        holder.club_course_details_recycl.setLayoutManager(linearLayoutManager);
        holder.club_course_details_recycl.setAdapter(ClubAvaibilityAdapterData);
        ClubAvaibilityAdapterData.notifyDataSetChanged();

        ClubAvaibilityAdapterData.setClickListener(new ClubAvaibilityAdapterData.ItemClickListenerSelection() {
            @Override
            public void itemClickListerner(View view, String postionTime) {
                apical(postionTime);
//                if (itemClickListenerRemoveSelection != null) itemClickListenerRemoveSelection.itemClickListerner(view, position,postionTime);
//                defaultCalendarActivity.ClubAvabilityModelData.get(position).arraySelectionTime.remove(postionTime);
//                if(defaultCalendarActivity.ClubAvabilityModelData.get(position).arraySelectionTime.isEmpty()){
//                    defaultCalendarActivity.ClubAvabilityModelData.remove(position);
//                    defaultCalendarActivity.ClubAvaibilityAdapter.notifyDataSetChanged();
//                    defaultCalendarActivity.recylerTimeSelection.scrollToPosition(defaultCalendarActivity.ClubAvabilityModelData.size()-1);
//                }else {
//                    defaultCalendarActivity.ClubAvaibilityAdapter.notifyDataSetChanged();
//                    defaultCalendarActivity.recylerTimeSelection.scrollToPosition(position);
//                }
//                        Toast.makeText(context,"Checking"+ position + postionTime,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != ClubAvabilityModelDataArrayList ? ClubAvabilityModelDataArrayList.size() : 0);
    }

    public class ClubAvaibilityHolder extends RecyclerView.ViewHolder   {
        TextView timeTitle;
        RecyclerView club_course_details_recycl;
        Button club_course_details_show_hide;
//        TextInputEditText club_dialog_course_name;
        TextView  club_dialog_course_day,club_dialog_course_name  ;
        LinearLayout club_course_details_show;

        public ClubAvaibilityHolder(@NonNull View itemView) {
            super(itemView);
            club_dialog_course_name = (TextView)itemView.findViewById(R.id.club_dialog_course_name);
            club_dialog_course_day = (TextView)itemView.findViewById(R.id.club_dialog_course_day);
            club_course_details_show_hide = (Button) itemView.findViewById(R.id.club_course_details_show_hide);
            club_course_details_show = (LinearLayout) itemView.findViewById(R.id.club_course_details_show);
            club_course_details_recycl=(RecyclerView)itemView.findViewById((R.id.club_course_details_recycl));

        }
    }

    public void setClickListener(ItemClickListenerRemoveSelection itemClickListenerRemoveSelection) {
        this.itemClickListenerRemoveSelection = itemClickListenerRemoveSelection;
    }

    public interface ItemClickListenerRemoveSelection{
        void itemClickListerner(View view, int postionDate, int postionTime);
    }

        public void apical(String bok){

        Log.e("Tag","coachis_---> "+coachis_+"coachis_---> "+bok);

        apiInterface.deleteclubavailablity(coachis_,bok).enqueue(new Callback<DeleteClubDTO>() {
            @Override
            public void onResponse(@NonNull Call<DeleteClubDTO> call, @NonNull Response<DeleteClubDTO> response) {
                System.out.println(new Gson().toJson( response.body().toString()));
                if(response.body().getIsSuccess().equals("true")) {
                    ((RealMainPageActivity)context).setLastPosition(Constant.MENU_CLUB_COLLECTIVE_COURT);
                    ((RealMainPageActivity)context). setFragmentList(Constant.MENU_CLUB_COLLECTIVE_COURT);
                }
            }
            @Override
            public void onFailure(Call<DeleteClubDTO> call, Throwable t) {
                System.out.println("---> "+ call +" "+ t);
            }
        });

    }
}
