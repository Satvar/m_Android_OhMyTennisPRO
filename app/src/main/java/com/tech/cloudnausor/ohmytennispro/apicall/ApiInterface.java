package com.tech.cloudnausor.ohmytennispro.apicall;


import com.tech.cloudnausor.ohmytennispro.dto.AnimationCourse;
import com.tech.cloudnausor.ohmytennispro.dto.AnimationDTO;
import com.tech.cloudnausor.ohmytennispro.dto.CourseMoreDetailsDTO;
import com.tech.cloudnausor.ohmytennispro.dto.DeleteClubDTO;
import com.tech.cloudnausor.ohmytennispro.dto.StageDTO;
import com.tech.cloudnausor.ohmytennispro.dto.TeamBuildingDTO;
import com.tech.cloudnausor.ohmytennispro.dto.TournamentDTO;
import com.tech.cloudnausor.ohmytennispro.model.CoachDetailsModel;
import com.tech.cloudnausor.ohmytennispro.model.GetClubModel;
import com.tech.cloudnausor.ohmytennispro.model.OndemandCourseModel;
import com.tech.cloudnausor.ohmytennispro.model.setavaibility.SetAvaibilityData;
import com.tech.cloudnausor.ohmytennispro.response.BokingDataResponseData;
import com.tech.cloudnausor.ohmytennispro.response.BookingRequestStatusResponse;
import com.tech.cloudnausor.ohmytennispro.response.CalenderResponseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachDetailsResponseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachSchResponseData;
import com.tech.cloudnausor.ohmytennispro.response.CoachUpdateResponse;
import com.tech.cloudnausor.ohmytennispro.response.ForgotResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetClubResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetCoachCollectiveOnDemandResponseData;
import com.tech.cloudnausor.ohmytennispro.response.GetIndiCoachDetailsResponse;
import com.tech.cloudnausor.ohmytennispro.response.LoginResponseData;
import com.tech.cloudnausor.ohmytennispro.response.RegisterPostalCodeList;
import com.tech.cloudnausor.ohmytennispro.response.RegisterResponseData;
import com.tech.cloudnausor.ohmytennispro.response.ResetResponseData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("city/{postalcode}")
    Call<RegisterPostalCodeList> getPostalField(@Path("postalcode") String postalcode);

    @POST("user/login")
    @FormUrlEncoded
    Call<LoginResponseData> getLoginFieldDetails(@Field("email") String email, @Field("password") String password);

    @POST("/user/userVerification")
    @FormUrlEncoded
    Call<DeleteClubDTO> userVerification(@Field("email") String User_Email);

//    @POST("quickInsertCoach")
//    @FormUrlEncoded
//    Call<RegisterResponseData> getRegisterFieldDetails(@Field("Coach_Fname") String firstName, @Field("Coach_Lname") String secondName, @Field("Coach_Email") String email, @Field("Coach_Password") String pass, @Field("Code_postal") String postalCode, @Field("Coach_City") String PostalCitiy, @Field("Coach_Phone") String telepone);

    @POST("user/register/user")
    @FormUrlEncoded
    Call<RegisterResponseData> getRegisterFieldDetails(@Field("gender") String radio_value,@Field("firstName") String firstName, @Field("lastName") String secondName, @Field("email") String email, @Field("password") String pass, @Field("postalCode") String postalCode, @Field("User_City") String PostalCitiy, @Field("mobile") String telepone,@Field("cityId") String cityId,@Field("roleId") String roleId);


//    @GET("recipes_category")
//    Call<SampleModel> getSampleData();

//    @GET("city/{postalcode}")
//    Call<RegisterPostalCodeList> getPostalField(@Path("postalcode") String postalcode);
//
//    @POST("signIn")
//    @FormUrlEncoded
//    Call<LoginResponseData> getLoginFieldDetails(@Field("Coach_Email") String firstName, @Field("Coach_Password") String pass);
//
//    @POST("user/register/user")
//    @FormUrlEncoded
//    Call<RegisterResponseData> getRegisterFieldDetails(@Field("gender") String radio_value, @Field("firstName") String firstName, @Field("lastName") String secondName, @Field("email") String email, @Field("password") String pass, @Field("postalCode") String postalCode, @Field("User_City") String PostalCitiy, @Field("mobile") String telepone, @Field("cityId") String cityId, @Field("roleId") String roleId);

    @POST("user/password/forgot")
    @FormUrlEncoded
    Call<ForgotResponseData> getForgotFieldDetails(@Field("email") String email);

//    @POST("detailedInsertCoach")
//    @FormUrlEncoded
//    Call<RegisterResponseData> getDetailedInsertCoach(@Field("Coach_Fname") String firstName, @Field("Coach_Lname") String secondName, @Field("Coach_Email") String email, @Field("Coach_Password") String telephone, @Field("Code_postal") String description, @Field("Coach_City") String PostalCitiy, @Field("Coach_Phone") String telepone);

    @POST("coach/getcoachbyid")
    @FormUrlEncoded
    Call<CoachDetailsResponseData> getCoachDeatils(@Field("Coach_Email") String coach_id);

    @POST("coach/insertavailabilty")
    @FormUrlEncoded
    Call<CoachSchResponseData> getInsertAvailability(
            @Field("Week_Number") String Week_Number, @Field("Start_Date") String Start_Date, @Field("End_Date") String End_Date,
            @Field("Mon_mor") String Mon_mor, @Field("Mon_af") String Mon_af, @Field("Mon_eve") String Mon_eve,
            @Field("Tue_mor") String Tue_mor, @Field("Tue_af") String Tue_af, @Field("Tue_eve") String Tue_eve,
            @Field("Wed_mor") String Wed_mor, @Field("Wed_af") String Wed_af, @Field("Wed_eve") String Wed_eve,
            @Field("Thu_mor") String Thu_mor, @Field("Thu_af") String Thu_af , @Field("Thu_eve") String Thu_eve,
            @Field("Fri_mor") String Fri_mor, @Field("Fri_af") String Fri_af, @Field("Fri_eve") String Fri_eve,
            @Field("Sat_mor") String Sat_mor, @Field("Sat_af") String Sat_af, @Field("Sat_eve") String Sat_eve ,
            @Field("Sun_mor") String Sun_mor, @Field("Sun_af") String Sun_af, @Field("Sun_eve") String Sun_eve,
            @Field("Coach_id") String Coach_id,@Field("course") String course,@Field("Coach_Flag") String Coach_Flag);

    @POST("course/setindividualcourse")
    @FormUrlEncoded
    Call<CoachSchResponseData> getInsertIndividualCourse(
            @Field("Coach_Id") String Coach_ID, @Field("Description") String Course_Description,
            @Field("Technical_provided") String Course_Technique,@Field("Mode_of_Transport") String Mode_of_Transport,
            @Field("Video") String Course_Video, @Field("Price_min") String Course_Price, @Field("Price_max") String Course_Price10,@Field("Plan") String Plan
    ,@Field("Location") String Location,@Field("Postalcode") String Postalcode);

    @POST("/user/resetpassword")
    @FormUrlEncoded
    Call<DeleteClubDTO> setRest(@Field("email") String email ,@Field("hash") String hash,@Field("password")String password );

    @POST("coachReset")
    @FormUrlEncoded
    Call<ResetResponseData> getResetFieldDetails(@Field("email") String firstName,@Field("password") String password);

    @POST("getCalendarMyCalendar")
    @FormUrlEncoded
    Call<CalenderResponseData> getGetCalendarMyCalendars(@Field("Coach_ID") String Coach_ID);

//    @GET("course/getindividualcourse")
//    Call<GetIndiCoachDetailsResponse> getGetIndiCourseDetails(@Query("Coach_ID") String Coach_ID);
    @GET("course/getindividualcourse")
    Call<GetIndiCoachDetailsResponse> getGetIndiCourseDetails(@Query("coachId") String Coach_ID);

    @POST("coach/updateprofile")
    Call<CoachUpdateResponse> getDetailedInsertCoach(@Body CoachDetailsModel coachDetailsModel);

//    @POST("coach/updateprofile")
//    @FormUrlEncoded
//    Call<CoachUpdateResponse> getDetailedInsertCoach( @Field("Coach_Fname") String coach_Fname,
//                                                      @Field("Coach_Lname") String coach_Lname,
//                                                      @Field("Coach_Email") String coach_Email,
//                                                      @Field("Coach_Phone") String coach_Phone,
//                                                      @Field("Coach_Price") String coach_Price,
//                                                      @Field("Coach_PriceX10") String coach_PriceX10,
//                                                      @Field("Coach_Services") String coach_Services,
//                                                      @Field("Coach_transport") String Coach_transport,
//                                                      @Field("Coach_Image") String Coach_Image,
//                                                      @Field("Coach_Resume") String Coach_Resume,
//                                                      @Field("Coach_Description") String coach_Description,
//                                                      @Field("Active_Status") String Active_Status,
//                                                      @Field("Coach_payment_type") String Coach_payment_type,
//                                                      @Field("Coach_Bank_Name") String Coach_Bank_Name,
//                                                      @Field("Branch_Code") String Branch_Code,
//                                                      @Field("Coach_Bank_ACCNum") String Coach_Bank_ACCNum,
//                                                      @Field("Coach_Rayon") String Coach_Rayon,
//                                                      @Field("Coach_Bank_City") String coach_Bank_City,
//                                                      @Field("User_type") String User_type
//
//    );

    @GET("coach/getreservation")
    Call<BokingDataResponseData> getBookingDataResponse(@Query("Coach_ID") String Coach_ID);

    @POST("coach/setStatus")
    @FormUrlEncoded
    Call<BookingRequestStatusResponse> setRequestedStatus(@Field("Coach_ID") String Coach_ID, @Field("status") String status, @Field("booking_id") String booking_id,
                                                          @Field("booking_date") String booking_date,@Field("amount") String amount,@Field("course") String course,
                                                          @Field("discount") String discount,@Field("user_Id") String user_id);


    @POST("course/setcousecollectivedemanad")
    @FormUrlEncoded
    Call<CoachCollectiveOnDemandResponseData> setcousecollectivedemanad(@Field("Coach_ID") String Coach_ID, @Field("Min_People") String Min_People,
                                                                        @Field("Max_People") String Max_People, @Field("Description") String Description,
                                                                        @Field("Location") String Location, @Field("Mode_of_transport") String Mode_of_transport,
                                                                        @Field("Price_2pl_1hr") String Price_2pl_1hr, @Field("Price_3pl_1hr") String Price_3pl_1hr,
                                                                        @Field("Price_4pl_1hr") String Price_4pl_1hr, @Field("Price_5pl_1hr") String Price_5pl_1hr,
                                                                        @Field("Price_6pl_1hr") String Price_6pl_1hr, @Field("Plan") String Plan);

    @GET("course/getcousecollectivedemanad")
    Call<GetCoachCollectiveOnDemandResponseData> getcousecollectivedemanad(@Query("Coach_ID") String Coach_ID );

    @POST("coach/insertavailabilty")
    Call<GetCoachCollectiveOnDemandResponseData> coachsetinsertavailabilty(@Body SetAvaibilityData setAvaibilityData );

    @GET("getcalender")
    Call<CalenderResponseData> getcalender(@Query("Coach_ID") String Coach_ID );

    @POST("course/setcousecollectivedemanad")
    Call<GetCoachCollectiveOnDemandResponseData> setcousecollectivedemanad(@Body OndemandCourseModel ondemandCourseModel );


    @GET("course/getcousecollectiveclub")
    Call<GetClubResponseData> getcousecollectiveclub(@Query("coachId") String Coach_ID );

    @POST("course/setcousecollectiveclub")
    Call<GetClubResponseData> setcousecollectivedemanad(@Body GetClubModel getClubModel );

    @POST("course/deleteclubavailablity")
    @FormUrlEncoded
    Call<DeleteClubDTO> deleteclubavailablity(@Field("CoachId") String id, @Field("Id") String Coach_ID );

    @GET("course/getstagecourse")
    Call<StageDTO> getstagecourse(@Query("coachId") String Coach_ID );

    @GET("course/getstage")
    Call<StageDTO> getstage(@Query("id") String id,@Query("coachId") String Coach_ID );

    @GET("course/gettournamentcourse")
    Call<TournamentDTO> gettournamentcourse(@Query("coachId") String Coach_ID );

    @GET("course/gettournament")
    Call<TournamentDTO> gettournament(@Query("coachId") String Coach_ID,@Query("id") String id);

    @GET("course/getteambuildingcourse")
    Call<TeamBuildingDTO> getteambuildingcourse(@Query("coachId") String Coach_ID );

    @POST("course/setteambuildingcourse")
    Call<TeamBuildingDTO> setteambuildingcourse(@Body TeamBuildingDTO.TeamBuildingCourse teamBuildingCourse );

    @GET("course/getanimationcourseLeft")
    Call<AnimationDTO> getanimationcourse(@Query("coachId") String Coach_ID );

    @GET("course/getanimation")
    Call<AnimationDTO> getanimation(@Query("coachId") String Coach_ID, @Query("animation_id") String id );

    @POST("course/setanimationinsert")
    Call<AnimationDTO> setanimationinsert(@Body AnimationCourse animationCourse );

    @POST("course/setanimationupdate")
    Call<AnimationDTO> setanimationupdate(@Body AnimationCourse animationCourse );

    @POST("course/setstagecourseinsert")
    Call<StageDTO> setstagecourseinsert(@Body StageDTO.StageCourse stageCourse );

    @POST("course/setstagecourseupdate")
    Call<StageDTO> setstagecourseupdate(@Body StageDTO.StageCourse stageCourse );

    @POST("course/setTournamentCourseInsert")
    Call<TournamentDTO> setTournamentCourseInsert(@Body TournamentDTO.TournamentCourse tournamentCourse );

    @POST("course/setTournamentCourseUpdate")
    Call<TournamentDTO> setTournamentCourseUpdate(@Body TournamentDTO.TournamentCourse tournamentCourse );

    @GET("coachdetail/getbookcourse")
    Call<CourseMoreDetailsDTO> getbookcourse(@Query("course") String course, @Query("booking_id") String booking_id );

//    @POST("course/setanimationinsert")
//    Call<AnimationDTO> setanimationinsert(@Body AnimationCourse animationCourse );
//
//    @POST("course/setanimationupdate")
//    Call<AnimationDTO> setanimationupdate(@Body AnimationCourse animationCourse );

//    @GET("course/getanimationcourse")
//    Call<AnimationDTO> getanimationcourse(@Query("coachId") String Coach_ID );

//    @POST("detailedInsertCoach")
//    @Multipart
//    Call<CoachUpdateResponse> getDetailedInsertCoach( @Part("Coach_Aviailability") RequestBody coach_Aviailability,
//                                                       @Part("Coach_Fname") RequestBody coach_Fname,
//                                                       @Part("Coach_Lname") RequestBody coach_Lname,
//                                                       @Part("Coach_Email") RequestBody coach_Email,
//                                                       @Part("Coach_Phone") RequestBody coach_Phone,
//                                                       @Part("Coach_Price") RequestBody coach_Price,
//                                                       @Part("Coach_PriceX10") RequestBody coach_PriceX10,
//                                                       @Part("Coach_Services") RequestBody coach_Services,
//                                                       @Part("Active_City") RequestBody active_City,
//                                                       @Part("Coach_transport") RequestBody coach_transport,
//                                                       @Part MultipartBody.Part image,
//                                                       @Part("coachResumeFile") RequestBody coachResumeFile,
//                                                       @Part("Coach_Description") RequestBody coach_Description,
//                                                       @Part("Availability_StartDate") RequestBody availability_StartDate,
//                                                       @Part("Availability_EndDate") RequestBody availability_EndDate,
//                                                       @Part("Coach_payment_type") RequestBody coach_payment_type,
//                                                       @Part("Coach_Bank_Name") RequestBody coach_Bank_Name,
//                                                       @Part("Branch_Code") RequestBody branch_Code,
//                                                       @Part("Coach_Bank_ACCNum") RequestBody coach_Bank_ACCNum,
//                                                       @Part("Coach_Bank_City") RequestBody coach_Bank_City,
//                                                       @Part("Coach_Resume")RequestBody Coach_Resume
//                                                       );

//    @POST("detailedInsertCoach")
//    @Multipart
//    Call<CoachUpdateResponse> getDetailedInsertCoach2( @Part("Coach_Aviailability") RequestBody coach_Aviailability,
//                                                      @Part("Coach_Fname") RequestBody coach_Fname,
//                                                      @Part("Coach_Lname") RequestBody coach_Lname,
//                                                      @Part("Coach_Email") RequestBody coach_Email,
//                                                      @Part("Coach_Phone") RequestBody coach_Phone,
//                                                      @Part("Coach_Price") RequestBody coach_Price,
//                                                      @Part("Coach_PriceX10") RequestBody coach_PriceX10,
//                                                      @Part("Coach_Services") RequestBody coach_Services,
//                                                      @Part("Active_City") RequestBody active_City,
//                                                      @Part("Coach_transport") RequestBody coach_transport,
//                                                      @Part MultipartBody.Part image,
//                                                      @Part("Coach_Resume") RequestBody coachResumeFile,
//                                                      @Part("coachImageFile") RequestBody coachImageFile,
//                                                      @Part("Coach_Description") RequestBody coach_Description,
//                                                      @Part("Availability_StartDate") RequestBody availability_StartDate,
//                                                      @Part("Availability_EndDate") RequestBody availability_EndDate,
//                                                      @Part("Coach_payment_type") RequestBody coach_payment_type,
//                                                      @Part("Coach_Bank_Name") RequestBody coach_Bank_Name,
//                                                      @Part("Branch_Code") RequestBody branch_Code,
//                                                      @Part("Coach_Bank_ACCNum") RequestBody coach_Bank_ACCNum,
//                                                      @Part("Coach_Bank_City") RequestBody coach_Bank_City,
//                                                      @Part("Coach_Image")RequestBody Coach_Image
//    );
//
//
//
//    @POST("detailedInsertCoach")
//    @Multipart
//    Call<CoachUpdateResponse> getDetailedInsertCoach3( @Part("Coach_Aviailability") RequestBody coach_Aviailability,
//                                                      @Part("Coach_Fname") RequestBody coach_Fname,
//                                                      @Part("Coach_Lname") RequestBody coach_Lname,
//                                                      @Part("Coach_Email") RequestBody coach_Email,
//                                                      @Part("Coach_Phone") RequestBody coach_Phone,
//                                                      @Part("Coach_Price") RequestBody coach_Price,
//                                                      @Part("Coach_PriceX10") RequestBody coach_PriceX10,
//                                                      @Part("Coach_Services") RequestBody coach_Services,
//                                                      @Part("Active_City") RequestBody active_City,
//                                                      @Part("Coach_transport") RequestBody coach_transport,
//                                                       @Part("coachImageFile") RequestBody coachImageFile,
//                                                      @Part("Coach_Image") RequestBody Coach_Image,
//                                                      @Part("coachResumeFile") RequestBody coachResumeFile,
//                                                      @Part("Coach_Description") RequestBody coach_Description,
//                                                      @Part("Availability_StartDate") RequestBody availability_StartDate,
//                                                      @Part("Availability_EndDate") RequestBody availability_EndDate,
//                                                      @Part("Coach_payment_type") RequestBody coach_payment_type,
//                                                      @Part("Coach_Bank_Name") RequestBody coach_Bank_Name,
//                                                      @Part("Branch_Code") RequestBody branch_Code,
//                                                      @Part("Coach_Bank_ACCNum") RequestBody coach_Bank_ACCNum,
//                                                      @Part("Coach_Bank_City") RequestBody coach_Bank_City,
//                                                      @Part("Coach_Resume")RequestBody Coach_Resume
//    );

}