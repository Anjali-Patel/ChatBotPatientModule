package com.app.feish.application.Patient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportDatum {

        @SerializedName("name")
        @Expose
        private String Name =null;

        @SerializedName("date")
        @Expose
        private String Date=null;

        @SerializedName("comment")
        @Expose
        private String comment=null;

        @SerializedName("report")
        @Expose
        private String raw;

    public ReportDatum(String name, String date, String comment, String raw) {
        Name = name;
        Date = date;
        this.comment = comment;
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }


    }


