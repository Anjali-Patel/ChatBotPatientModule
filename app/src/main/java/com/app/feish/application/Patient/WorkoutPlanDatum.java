package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkoutPlanDatum {

    @SerializedName("workout_id")
    @Expose
    private Integer workoutId;
    @SerializedName("workout_week")
    @Expose
    private String workoutWeek;
    @SerializedName("child_weight")
    @Expose
    private String childWeight;
    @SerializedName("complications")
    @Expose
    private String complications;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("workout_plan_id")
    @Expose
    private Integer workoutPlanId;

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public String getWorkoutWeek() {
        return workoutWeek;
    }

    public void setWorkoutWeek(String workoutWeek) {
        this.workoutWeek = workoutWeek;
    }

    public String getChildWeight() {
        return childWeight;
    }

    public void setChildWeight(String childWeight) {
        this.childWeight = childWeight;
    }

    public String getComplications() {
        return complications;
    }

    public void setComplications(String complications) {
        this.complications = complications;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getWorkoutPlanId() {
        return workoutPlanId;
    }

    public void setWorkoutPlanId(Integer workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
    }

}
