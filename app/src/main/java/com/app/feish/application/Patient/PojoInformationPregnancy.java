package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoInformationPregnancy {


    @SerializedName("workout_plan_data")
    @Expose
    private List<WorkoutPlanDatum> workoutPlanData = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<WorkoutPlanDatum> getWorkoutPlanData() {
        return workoutPlanData;
    }

    public void setWorkoutPlanData(List<WorkoutPlanDatum> workoutPlanData) {
        this.workoutPlanData = workoutPlanData;
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
