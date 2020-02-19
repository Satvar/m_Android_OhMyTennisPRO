package com.tech.cloudnausor.ohmytennispro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennispro.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennispro.dto.DeleteClubDTO;
import com.tech.cloudnausor.ohmytennispro.model.ClubAvabilityModelData;
import com.tech.cloudnausor.ohmytennispro.model.GetClubModel;
import com.tech.cloudnausor.ohmytennispro.response.GetClubResponseData;
import com.tech.cloudnausor.ohmytennispro.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Menus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class ClubAvaibilityAdapterData extends RecyclerView.Adapter<ClubAvaibilityAdapterData.ClubAvaibilityDataHolder> {
    Context context;
    ArrayList<ClubAvabilityModelData> clubAvabilityModelDataDetails;
    ItemClickListenerSelection mClickListener;
    FragmentActivity fragmentActivity ;
    String enable ="";
    SingleTonProcess singleTonProcess;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface .class);
    String coachis_ = "";


    public ClubAvaibilityAdapterData(Context context, ArrayList<ClubAvabilityModelData> clubAvabilityModelDataDetails, FragmentActivity fragmentActivity,String enable,String coachid ) {
        this.context = context;
        this.clubAvabilityModelDataDetails = clubAvabilityModelDataDetails;
        this. fragmentActivity = fragmentActivity ;
        this.enable = enable;
        this.coachis_= coachid;

    }

    @NonNull
    @Override
    public ClubAvaibilityDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_layout,parent,false);
        return new ClubAvaibilityDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubAvaibilityDataHolder holder, int position) {
        ClubAvabilityModelData clubAvabilityModelData = clubAvabilityModelDataDetails.get(position);

        holder.Details_Time_start.setText(clubAvabilityModelData.getStartTime());
        holder.Details_Time_end.setText(clubAvabilityModelData.getEndTime());
        holder.Details_max_person.setText(clubAvabilityModelData.getMaxCount());
        holder.Details_price.setText(clubAvabilityModelData.getPrice());
        if(enable.equals("1")){
            holder.Details_Edit.setEnabled(true);
        }else if(enable.equals("0")){
            holder.Details_Edit.setEnabled(false);
        }


    }

    @Override
    public int getItemCount() {
        return clubAvabilityModelDataDetails.size();
    }

    public class ClubAvaibilityDataHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView Details_Time_start,Details_Time_end,Details_max_person,Details_price;
        Button Details_Edit;
        AlertDialog.Builder dialogBuilder;
        LayoutInflater inflater;
        AlertDialog alertDialog;
        View dialogView;

        public ClubAvaibilityDataHolder(@NonNull View itemView) {
            super(itemView);

                    Details_Edit = (Button)itemView.findViewById(R.id.details_edit);
                    Details_Time_start = (TextView) itemView.findViewById(R.id.details_start);
                    Details_Time_end = (TextView) itemView.findViewById(R.id.details_end);
                    Details_max_person = (TextView) itemView.findViewById(R.id.details_max_person);
                    Details_price = (TextView) itemView.findViewById(R.id.details_price);

                    dialogBuilder = new AlertDialog.Builder(fragmentActivity);
                    inflater = LayoutInflater.from(context);
                    dialogView = inflater.inflate(R.layout.dialog_club_avaibility, null);
                    dialogBuilder.setView(dialogView);
                    alertDialog = dialogBuilder.create();

               Details_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    alertDialog.show();
                    apical(clubAvabilityModelDataDetails.get(getAdapterPosition()).getId());
                }
               });

//                    Details_Edit.setOnClickListener(this);
//                    itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.itemClickListerner(view, clubAvabilityModelDataDetails.get(getAdapterPosition()).getId());
        }
    }

    public void setClickListener(ItemClickListenerSelection itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListenerSelection{
        void itemClickListerner(View view, String postion);
    }

            public void apical(String bok){

                Log.e("Tag","coachis_---> "+coachis_+"coachis_---> "+bok);

        apiInterface.deleteclubavailablity(coachis_,bok).enqueue(new Callback<DeleteClubDTO>() {
            @Override
            public void onResponse(@NonNull Call<DeleteClubDTO> call, @NonNull Response<DeleteClubDTO> response) {
                assert response.body() != null;
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
