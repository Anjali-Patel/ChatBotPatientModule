package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalInfoWorkoutDatum {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("workout_id")
    @Expose
    private Integer workoutId;
    @SerializedName("average_sugar")
    @Expose
    private String averageSugar;
    @SerializedName("average_blood_pressure")
    @Expose
    private String averageBloodPressure;
    @SerializedName("average_hearts_beats")
    @Expose
    private String averageHeartsBeats;
    @SerializedName("average_colastrol")
    @Expose
    private String averageColastrol;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public String getAverageSugar() {
        return averageSugar;
    }

    public void setAverageSugar(String averageSugar) {
        this.averageSugar = averageSugar;
    }

    public String getAverageBloodPressure() {
        return averageBloodPressure;
    }

    public void setAverageBloodPressure(String averageBloodPressure) {
        this.averageBloodPressure = averageBloodPressure;
    }

    public String getAverageHeartsBeats() {
        return averageHeartsBeats;
    }

    public void setAverageHeartsBeats(String averageHeartsBeats) {
        this.averageHeartsBeats = averageHeartsBeats;
    }

    public String getAverageColastrol() {
        return averageColastrol;
    }

    public void setAverageColastrol(String averageColastrol) {
        this.averageColastrol = averageColastrol;
    }
}
