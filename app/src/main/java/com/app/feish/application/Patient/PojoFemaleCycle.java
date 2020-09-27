package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PojoFemaleCycle {
    @SerializedName("menstruation_data")
    @Expose
    private List<MenstruationDatum> menstruationData = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<MenstruationDatum> getMenstruationData() {
        return menstruationData;
    }

    public void setMenstruationData(List<MenstruationDatum> menstruationData) {
        this.menstruationData = menstruationData;
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
