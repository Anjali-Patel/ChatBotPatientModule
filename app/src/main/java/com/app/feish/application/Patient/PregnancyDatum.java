package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PregnancyDatum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pregnancy_time")
    @Expose
    private String pregnancyTime;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("week")
    @Expose
    private Integer week;
    @SerializedName("comment")
    @Expose
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPregnancyTime() {
        return pregnancyTime;
    }

    public void setPregnancyTime(String pregnancyTime) {
        this.pregnancyTime = pregnancyTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
