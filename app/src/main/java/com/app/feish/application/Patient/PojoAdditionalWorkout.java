package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoAdditionalWorkout {
    @SerializedName("additional_info_workout")
    @Expose
    private List<AdditionalInfoWorkoutDatum> additionalInfoWorkout = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<AdditionalInfoWorkoutDatum> getAdditionalInfoWorkout() {
        return additionalInfoWorkout;
    }

    public void setAdditionalInfoWorkout(List<AdditionalInfoWorkoutDatum> additionalInfoWorkout) {
        this.additionalInfoWorkout = additionalInfoWorkout;
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
