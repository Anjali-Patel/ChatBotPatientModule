package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PojoViewScanrReport {

    @SerializedName("reports")
    @Expose
    private List<ReportDatum> reports = null;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ReportDatum> getReports() {
        return reports;
    }

    public void setReports(List<ReportDatum> reports) {
        this.reports = reports;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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




/*
public class PojoViewScanrReport {

    @SerializedName("user_id")
    @Expose
    private Integer userid;

    @SerializedName("reports")
    @Expose
    private List<ReportDatum> reportDatumList = null;

    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("message")
    @Expose
    private String message;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public List<ReportDatum> getReportDatumList() {
        return reportDatumList;
    }

    public void setReportDatumList(List<ReportDatum> reportDatumList) {
        this.reportDatumList = reportDatumList;
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
*/
