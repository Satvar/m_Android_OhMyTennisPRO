package com.tech.cloudnausor.ohmytennispro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennispro.R;
import com.tech.cloudnausor.ohmytennispro.activity.realhomepage.RealMainPageActivity;
import com.tech.cloudnausor.ohmytennispro.dto.AnimationCourse;
import com.tech.cloudnausor.ohmytennispro.dto.AnimationDTO;
import com.tech.cloudnausor.ohmytennispro.dto.OhMyEventsDTO;
import com.tech.cloudnausor.ohmytennispro.dto.StageDTO;
import com.tech.cloudnausor.ohmytennispro.dto.TournamentDTO;
import com.tech.cloudnausor.ohmytennispro.fragment.collectivecourse.StageFragmentPage;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Constant;
import com.tech.cloudnausor.ohmytennispro.utils.homepage.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class OhMyEventAdapter extends RecyclerView.Adapter<OhMyEventAdapter.OhMyEventAdapterHolder> {

    Context context;
    ArrayList<OhMyEventsDTO.OhMyEventData> ohMyEventDataArrayList;
    String typeofEvent;
    ArrayList<AnimationCourse> animationCourseArrayList;
    ArrayList<StageDTO.StageCourse> stageCourseArrayList;
    ArrayList<TournamentDTO.TournamentCourse> tournamentCourseArrayList;
    Bitmap decodedImage;
    String imageString = new String();


    public OhMyEventAdapter(Context context, ArrayList<AnimationCourse> animationCourseArrayList,ArrayList<StageDTO.StageCourse> stageCourseArrayList,ArrayList<TournamentDTO.TournamentCourse> tournamentCourseArrayList, String typeofEvent) {
        this.context = context;
        this.animationCourseArrayList = animationCourseArrayList;
        this.stageCourseArrayList = stageCourseArrayList;
        this.tournamentCourseArrayList = tournamentCourseArrayList;
        this.typeofEvent = typeofEvent;
    }

    @NonNull
    @Override
    public OhMyEventAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_events,parent,false);
        return new OhMyEventAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OhMyEventAdapterHolder holder, int position) {
       if(typeofEvent.equals("stage")){

           StageDTO.StageCourse stageCourse = stageCourseArrayList.get(position);
           if (stageCourse.getPhoto() != null && stageCourse.getPhoto() != "" && !stageCourse.getPhoto().matches("http") && !stageCourse.getPhoto().contains("WWW.") && !stageCourse.getPhoto().contains("https") &&
                   !stageCourse.getPhoto().contains(".jpeg") && !stageCourse.getPhoto().contains(".png") && !stageCourse.getPhoto().contains("undefined")) {
               imageString = stageCourse.getPhoto();
               if(imageString.contains("data:image/jpeg;base64,")){
                   imageString = imageString.replace("data:image/jpeg;base64,","");
               }
               if(imageString.contains("data:image/png;base64,")){
                   imageString = imageString.replace("data:image/png;base64,","");
               }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
               byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
               decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
               holder.stage_event_image.setImageBitmap(decodedImage);

           }


           holder.stage_event_name.setText(stageCourse.getEventname());
           holder.stage_event_descrition.setText(stageCourse.getDescription());
           String utcDateStr = stageCourse.getFrom_date();
           SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           Date date = null;
           try {
               date = sdf.parse(utcDateStr);
              //                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
               holder.stage_event_date.setText( new  SimpleDateFormat("dd MMM yyyy").format(date));

           } catch (ParseException e) {
               e.printStackTrace();
           }

           String utcDateStrto = stageCourse.getTo_date();
           SimpleDateFormat sdfto = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           Date dateto = null;
           try {
               dateto = sdfto.parse(utcDateStrto);

               //                            Calendar c = Calendar.getInstance();
//                            c.setTime(dateto);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
//               dateto = c.getTime();

               holder.end_event_date.setText( new  SimpleDateFormat("dd MMM yyyy").format(dateto));
           } catch (ParseException e) {
               e.printStackTrace();
           }


           holder.end_date_layout.setVisibility(View.VISIBLE);
           holder.start_date_layout_1.setVisibility(View.VISIBLE);
           holder.end_date_layout_1.setVisibility(View.VISIBLE);
           holder.stage_event_name.setVisibility(View.VISIBLE);
//           if(position == 0){
//                   holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }


       }else if(typeofEvent.equals("animation")){

               AnimationCourse animationCourse = animationCourseArrayList.get(position);
               if (animationCourse.getPhoto() != null && animationCourse.getPhoto() != "" && !animationCourse.getPhoto().matches("http") && !animationCourse.getPhoto().contains("WWW.") && !animationCourse.getPhoto().contains("https") &&
                       !animationCourse.getPhoto().contains(".jpeg") && !animationCourse.getPhoto().contains(".png") && !animationCourse.getPhoto().contains("undefined")) {
                   imageString = animationCourse.getPhoto();
                   if(imageString.contains("data:image/jpeg;base64,")){
                       imageString = imageString.replace("data:image/jpeg;base64,","");
                   }
                   if(imageString.contains("data:image/png;base64,")){
                       imageString = imageString.replace("data:image/png;base64,","");
                   }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
                   byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                   decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                   holder.stage_event_image.setImageBitmap(decodedImage);
               }

           holder.stage_event_descrition.setText(animationCourse.getDescription());
           holder.start_date_layout.setVisibility(View.GONE);
           holder.end_date_layout.setVisibility(View.GONE);
           holder.start_date_layout_1.setVisibility(View.GONE);
           holder.end_date_layout_1.setVisibility(View.GONE);
           holder.stage_event_name.setVisibility(View.GONE);

//           if(position == 0){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }           //        holder.stage_event_image.setText(ohMyEventDataArrayList.get(position).getEventname());
       }
//       else if(typeofEvent.equals("teambuilding")){
//           holder.stage_event_descrition.setText(ohMyEventDataArrayList.get(position).getEventdescribution());
//           holder.start_date_layout.setVisibility(View.GONE);
//           holder.end_date_layout.setVisibility(View.GONE);
//           holder.start_date_layout_1.setVisibility(View.GONE);
//           holder.end_date_layout_1.setVisibility(View.GONE);
//           holder.stage_event_name.setVisibility(View.GONE);
//           if(position == 0){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }           //        holder.stage_event_image.setText(ohMyEventDataArrayList.get(position).getEventname());
//       }
       else if(typeofEvent.equals("tournament")){
           TournamentDTO.TournamentCourse tournamentCourse = tournamentCourseArrayList.get(position);
           if (tournamentCourse.getPhoto() != null && tournamentCourse.getPhoto() != "" && !tournamentCourse.getPhoto().matches("http") && !tournamentCourse.getPhoto().contains("WWW.") && !tournamentCourse.getPhoto().contains("https") &&
                   !tournamentCourse.getPhoto().contains(".jpeg") && !tournamentCourse.getPhoto().contains(".png") && !tournamentCourse.getPhoto().contains("undefined")) {
               imageString = tournamentCourse.getPhoto();
               if(imageString.contains("data:image/jpeg;base64,")){
                   imageString = imageString.replace("data:image/jpeg;base64,","");
               }
               if(imageString.contains("data:image/png;base64,")){
                   imageString = imageString.replace("data:image/png;base64,","");
               }
//                            byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
               byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
               decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
               holder.stage_event_image.setImageBitmap(decodedImage);
           }
           holder.stage_event_name.setText(tournamentCourse.getTournamentname());
           holder.stage_event_descrition.setText(tournamentCourse.getDescription());

           String utcDateStr = tournamentCourse.getFrom_date();
           SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           Date date = null;
           try {
               date = sdf.parse(utcDateStr);
              //                            Calendar c = Calendar.getInstance();
//                            c.setTime(date);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
               holder.stage_event_date.setText( new  SimpleDateFormat("dd MMM yyyy").format(date));

           } catch (ParseException e) {
               e.printStackTrace();
           }

           String utcDateStrto = tournamentCourse.getTo_date();
           SimpleDateFormat sdfto = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           Date dateto = null;
           try {
               dateto = sdfto.parse(utcDateStrto);
               //                            Calendar c = Calendar.getInstance();
//                            c.setTime(dateto);
//                            c.add(Calendar.DATE, 1);
//                            date = c.getTime();
//               dateto = c.getTime();
               holder.end_event_date.setText( new  SimpleDateFormat("dd MMM yyyy").format(dateto));
           } catch (ParseException e) {
               e.printStackTrace();
           }

           holder.start_date_layout.setVisibility(View.VISIBLE);
           holder.end_date_layout.setVisibility(View.VISIBLE);
           holder.start_date_layout_1.setVisibility(View.VISIBLE);
           holder.end_date_layout_1.setVisibility(View.VISIBLE);
           holder.stage_event_name.setVisibility(View.VISIBLE);

//           if(position == 0){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
//           }else if(position == 1){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
//           }else if(position == 2){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.white));
//           }else if(position == 3){
//               holder.stage_event_image.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
//           }           //        holder.stage_event_image.setText(ohMyEventDataArrayList.get(position).getEventname());
       }
    }

    @Override
    public int getItemCount() {
        if(typeofEvent.equals("animation")){
        return animationCourseArrayList.size();
        }else if(typeofEvent.equals("stage")){
            return stageCourseArrayList.size();
        }else {
            return tournamentCourseArrayList.size();
        }
    }

    public class OhMyEventAdapterHolder extends RecyclerView.ViewHolder {

       TextView stage_event_name,stage_event_descrition,stage_event_date,end_event_date;
       ImageView stage_event_image;
       LinearLayout datelayout,start_date_layout,end_date_layout,end_date_layout_1,start_date_layout_1;

        public OhMyEventAdapterHolder(@NonNull View itemView) {
            super(itemView);
            stage_event_name=(TextView)itemView.findViewById(R.id.stage_event_name);
            stage_event_descrition=(TextView)itemView.findViewById(R.id.stage_event_descrition);
            stage_event_date=(TextView)itemView.findViewById(R.id.start_event_date);
            stage_event_image =(ImageView) itemView.findViewById(R.id.stage_event_image);
            start_date_layout  =(LinearLayout) itemView.findViewById(R.id.start_date_layout);
            start_date_layout_1 =(LinearLayout) itemView.findViewById(R.id.start_date_layout_1);
            end_date_layout =(LinearLayout) itemView.findViewById(R.id.end_date_layout);
            end_date_layout_1 =(LinearLayout) itemView.findViewById(R.id.end_date_layout_1);
            end_event_date =(TextView)itemView.findViewById(R.id.end_event_date);
            datelayout  =(LinearLayout) itemView.findViewById(R.id.datelayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(typeofEvent.equals("animation")) {
                         AnimationCourse animationCourse = animationCourseArrayList.get(getAdapterPosition());
                        ((RealMainPageActivity) context).setLastPosition(Constant.MENU_ANIMATION_EDIT);
                        ((RealMainPageActivity) context).setEventydata("update", animationCourse.getId());
                        ((RealMainPageActivity) context).setFragmentList(Constant.MENU_ANIMATION_EDIT);

                    }else if(typeofEvent.equals("stage")){
                        StageDTO.StageCourse stageCourse = stageCourseArrayList.get(getAdapterPosition());
                        ((RealMainPageActivity) context).setLastPosition(Constant.MENU_STAGE_EDIT);
                        ((RealMainPageActivity) context).setEventydata("update", stageCourse.getId());
                        ((RealMainPageActivity) context).setFragmentList(Constant.MENU_STAGE_EDIT);

                    }else if(typeofEvent.equals("tournament")){
                        TournamentDTO.TournamentCourse tournamentCourse = tournamentCourseArrayList.get(getAdapterPosition());
                        ((RealMainPageActivity) context).setLastPosition(Constant.MENU_TOURNAMENT_EDIT);
                        ((RealMainPageActivity) context).setEventydata("update", tournamentCourse.getId());
                        ((RealMainPageActivity) context).setFragmentList(Constant.MENU_TOURNAMENT_EDIT);
                    }
                }
            });
        }
    }

}
