package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class postalCodeList {

    @SerializedName("Nom_commune")
    @Expose
    private String Nom_commune;
    @SerializedName("Ligne_5")
    @Expose
    private String Ligne_5;
    @SerializedName("coordonnees_gps")
    @Expose
    private String coordonnees_gps;
    @SerializedName("Code_commune_INSEE")
    @Expose
    private String Code_commune_INSEE;
    @SerializedName("Code_postal")
    @Expose
    private String Code_postal;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Libelle_acheminement")
    @Expose
    private String Libelle_acheminement;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;


    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getNom_commune ()
    {
        return Nom_commune;
    }

    public void setNom_commune (String Nom_commune)
    {
        this.Nom_commune = Nom_commune;
    }

    public String getLigne_5 ()
    {
        return Ligne_5;
    }

    public void setLigne_5 (String Ligne_5)
    {
        this.Ligne_5 = Ligne_5;
    }

    public String getCoordonnees_gps ()
    {
        return coordonnees_gps;
    }

    public void setCoordonnees_gps (String coordonnees_gps)
    {
        this.coordonnees_gps = coordonnees_gps;
    }

    public String getCode_commune_INSEE ()
    {
        return Code_commune_INSEE;
    }

    public void setCode_commune_INSEE (String Code_commune_INSEE)
    {
        this.Code_commune_INSEE = Code_commune_INSEE;
    }

    public String getCode_postal ()
    {
        return Code_postal;
    }

    public void setCode_postal (String Code_postal)
    {
        this.Code_postal = Code_postal;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLibelle_acheminement ()
    {
        return Libelle_acheminement;
    }

    public void setLibelle_acheminement (String Libelle_acheminement)
    {
        this.Libelle_acheminement = Libelle_acheminement;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
