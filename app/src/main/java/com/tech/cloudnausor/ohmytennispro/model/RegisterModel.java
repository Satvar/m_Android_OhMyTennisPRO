package com.tech.cloudnausor.ohmytennispro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("fieldCount")
    @Expose
    private String fieldCount;
    @SerializedName("serverStatus")
    @Expose
    private String serverStatus;
    @SerializedName("protocol41")
    @Expose
    private String protocol41;
    @SerializedName("changedRows")
    @Expose
    private String changedRows;
    @SerializedName("affectedRows")
    @Expose
    private String affectedRows;
    @SerializedName("warningCount")
    @Expose
    private String warningCount;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("insertId")
    @Expose
    private String insertId;

    public String getFieldCount ()
    {
        return fieldCount;
    }

    public void setFieldCount (String fieldCount)
    {
        this.fieldCount = fieldCount;
    }

    public String getServerStatus ()
    {
        return serverStatus;
    }

    public void setServerStatus (String serverStatus)
    {
        this.serverStatus = serverStatus;
    }

    public String getProtocol41 ()
    {
        return protocol41;
    }

    public void setProtocol41 (String protocol41)
    {
        this.protocol41 = protocol41;
    }

    public String getChangedRows ()
    {
        return changedRows;
    }

    public void setChangedRows (String changedRows)
    {
        this.changedRows = changedRows;
    }

    public String getAffectedRows ()
    {
        return affectedRows;
    }

    public void setAffectedRows (String affectedRows)
    {
        this.affectedRows = affectedRows;
    }

    public String getWarningCount ()
    {
        return warningCount;
    }

    public void setWarningCount (String warningCount)
    {
        this.warningCount = warningCount;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getInsertId ()
    {
        return insertId;
    }

    public void setInsertId (String insertId)
    {
        this.insertId = insertId;
    }

}
