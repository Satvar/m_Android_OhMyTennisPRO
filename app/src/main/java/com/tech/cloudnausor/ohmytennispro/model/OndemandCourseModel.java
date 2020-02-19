package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OndemandCourseModel {
    @SerializedName("Group_Id")
    @Expose
    private String Group_Id;
    @SerializedName("Min_People")
    @Expose
    private String Min_People;
    @SerializedName("Max_People")
    @Expose
    private String Max_People;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Location")
    @Expose
    private String Location;
    @SerializedName("Mode_of_transport")
    @Expose
    private String Mode_of_transport;
    @SerializedName("Price_2pl_1hr")
    @Expose
    private String Price_2pl_1hr;
    @SerializedName("Price_2pl_10hr")
    @Expose
    private String Price_2pl_10hr;
    @SerializedName("Price_3pl_1hr")
    @Expose
    private String Price_3pl_1hr;
    @SerializedName("Price_3pl_10hr")
    @Expose
    private String Price_3pl_10hr;
    @SerializedName("Price_4pl_1hr")
    @Expose
    private String Price_4pl_1hr;
    @SerializedName("Price_4pl_10hr")
    @Expose
    private String Price_4pl_10hr;
    @SerializedName("Price_5pl_1hr")
    @Expose
    private String Price_5pl_1hr;
    @SerializedName("Price_6pl_1hr")
    @Expose
    private String Price_6pl_1hr;
    @SerializedName("Price_6pl_10hr")
    @Expose
    private String Price_6pl_10hr;
    @SerializedName("Plan")
    @Expose
    private String Plan;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("Coach_Id")
    @Expose
    private String Coach_Id;
    @SerializedName("Price_Mon")
    @Expose
    private String Price_Mon;
    @SerializedName("Price_Tue")
    @Expose
    private String Price_Tue;
    @SerializedName("Price_Wed")
    @Expose
    private String Price_Wed;
    @SerializedName("Price_Thr")
    @Expose
    private String Price_Thr;
    @SerializedName("Price_Fri")
    @Expose
    private String Price_Fri;
    @SerializedName("Price_Sat")
    @Expose
    private String Price_Sat;
    @SerializedName("Price_Sun")
    @Expose
    private String Price_Sun;
    @SerializedName("Postalcode")
    @Expose
    private String Postalcode;

    public OndemandCourseModel() {
    }

    public OndemandCourseModel( String min_People, String max_People, String description,
                               String location, String mode_of_transport, String price_2pl_1hr,
                               String price_2pl_10hr, String price_3pl_1hr, String price_3pl_10hr, String price_4pl_1hr,
                               String price_4pl_10hr, String price_5pl_1hr, String price_6pl_1hr, String price_6pl_10hr,
                               String plan,  String coach_Id, String price_Mon,
                               String price_Tue, String price_Wed, String price_Thr, String price_Fri,
                               String price_Sat, String price_Sun,String postalcode) {
        Min_People = min_People;
        Max_People = max_People;
        Description = description;
        Location = location;
        Mode_of_transport = mode_of_transport;
        Price_2pl_1hr = price_2pl_1hr;
        Price_2pl_10hr = price_2pl_10hr;
        Price_3pl_1hr = price_3pl_1hr;
        Price_3pl_10hr = price_3pl_10hr;
        Price_4pl_1hr = price_4pl_1hr;
        Price_4pl_10hr = price_4pl_10hr;
        Price_5pl_1hr = price_5pl_1hr;
        Price_6pl_1hr = price_6pl_1hr;
        Price_6pl_10hr = price_6pl_10hr;
        Plan = plan;
        Coach_Id = coach_Id;
        Price_Mon = price_Mon;
        Price_Tue = price_Tue;
        Price_Wed = price_Wed;
        Price_Thr = price_Thr;
        Price_Fri = price_Fri;
        Price_Sat = price_Sat;
        Price_Sun = price_Sun;
        Postalcode = postalcode;
    }
// Getter Methods

    public String getPostalcode() {
        return Postalcode;
    }

    public void setPostalcode(String postalcode) {
        Postalcode = postalcode;
    }

    public String getGroup_Id() {
        return Group_Id;
    }

    public String getMin_People() {
        return Min_People;
    }

    public String getMax_People() {
        return Max_People;
    }

    public String getDescription() {
        return Description;
    }

    public String getLocation() {
        return Location;
    }

    public String getMode_of_transport() {
        return Mode_of_transport;
    }

    public String getPrice_2pl_1hr() {
        return Price_2pl_1hr;
    }

    public String getPrice_2pl_10hr() {
        return Price_2pl_10hr;
    }

    public String getPrice_3pl_1hr() {
        return Price_3pl_1hr;
    }

    public String getPrice_3pl_10hr() {
        return Price_3pl_10hr;
    }

    public String getPrice_4pl_1hr() {
        return Price_4pl_1hr;
    }

    public String getPrice_4pl_10hr() {
        return Price_4pl_10hr;
    }

    public String getPrice_5pl_1hr() {
        return Price_5pl_1hr;
    }

    public String getPrice_6pl_1hr() {
        return Price_6pl_1hr;
    }

    public String getPrice_6pl_10hr() {
        return Price_6pl_10hr;
    }

    public String getPlan() {
        return Plan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCoach_Id() {
        return Coach_Id;
    }

    public String getPrice_Mon() {
        return Price_Mon;
    }

    public String getPrice_Tue() {
        return Price_Tue;
    }

    public String getPrice_Wed() {
        return Price_Wed;
    }

    public String getPrice_Thr() {
        return Price_Thr;
    }

    public String getPrice_Fri() {
        return Price_Fri;
    }

    public String getPrice_Sat() {
        return Price_Sat;
    }

    public String getPrice_Sun() {
        return Price_Sun;
    }

    // Setter Methods 

    public void setGroup_Id( String Group_Id ) {
        this.Group_Id = Group_Id;
    }

    public void setMin_People( String Min_People ) {
        this.Min_People = Min_People;
    }

    public void setMax_People( String Max_People ) {
        this.Max_People = Max_People;
    }

    public void setDescription( String Description ) {
        this.Description = Description;
    }

    public void setLocation( String Location ) {
        this.Location = Location;
    }

    public void setMode_of_transport( String Mode_of_transport ) {
        this.Mode_of_transport = Mode_of_transport;
    }

    public void setPrice_2pl_1hr( String Price_2pl_1hr ) {
        this.Price_2pl_1hr = Price_2pl_1hr;
    }

    public void setPrice_2pl_10hr( String Price_2pl_10hr ) {
        this.Price_2pl_10hr = Price_2pl_10hr;
    }

    public void setPrice_3pl_1hr( String Price_3pl_1hr ) {
        this.Price_3pl_1hr = Price_3pl_1hr;
    }

    public void setPrice_3pl_10hr( String Price_3pl_10hr ) {
        this.Price_3pl_10hr = Price_3pl_10hr;
    }

    public void setPrice_4pl_1hr( String Price_4pl_1hr ) {
        this.Price_4pl_1hr = Price_4pl_1hr;
    }

    public void setPrice_4pl_10hr( String Price_4pl_10hr ) {
        this.Price_4pl_10hr = Price_4pl_10hr;
    }

    public void setPrice_5pl_1hr( String Price_5pl_1hr ) {
        this.Price_5pl_1hr = Price_5pl_1hr;
    }

    public void setPrice_6pl_1hr( String Price_6pl_1hr ) {
        this.Price_6pl_1hr = Price_6pl_1hr;
    }

    public void setPrice_6pl_10hr( String Price_6pl_10hr ) {
        this.Price_6pl_10hr = Price_6pl_10hr;
    }

    public void setPlan( String Plan ) {
        this.Plan = Plan;
    }

    public void setCreatedAt( String createdAt ) {
        this.createdAt = createdAt;
    }

    public void setCoach_Id( String Coach_Id ) {
        this.Coach_Id = Coach_Id;
    }

    public void setPrice_Mon( String Price_Mon ) {
        this.Price_Mon = Price_Mon;
    }

    public void setPrice_Tue( String Price_Tue ) {
        this.Price_Tue = Price_Tue;
    }

    public void setPrice_Wed( String Price_Wed ) {
        this.Price_Wed = Price_Wed;
    }

    public void setPrice_Thr( String Price_Thr ) {
        this.Price_Thr = Price_Thr;
    }

    public void setPrice_Fri( String Price_Fri ) {
        this.Price_Fri = Price_Fri;
    }

    public void setPrice_Sat( String Price_Sat ) {
        this.Price_Sat = Price_Sat;
    }

    public void setPrice_Sun( String Price_Sun ) {
        this.Price_Sun = Price_Sun;
    }
}
