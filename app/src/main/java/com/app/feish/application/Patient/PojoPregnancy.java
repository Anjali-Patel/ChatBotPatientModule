package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoPregnancy {
    @SerializedName("pregnancy_data")
    @Expose
    private List<PregnancyDatum> pregnancyData = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<PregnancyDatum> getPregnancyData() {
        return pregnancyData;
    }

    public void setPregnancyData(List<PregnancyDatum> pregnancyData) {
        this.pregnancyData = pregnancyData;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
