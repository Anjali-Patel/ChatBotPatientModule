package com.app.feish.application.model;

public class MedicalHistoryListModel {
    int id;
    String sign;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaxo() {
        return maxo;
    }

    public void setMaxo(String maxo) {
        this.maxo = maxo;
    }

    public String getMino() {
        return mino;
    }

    public void setMino(String mino) {
        this.mino = mino;
    }

    public String getTotalo() {
        return totalo;
    }

    public void setTotalo(String totalo) {
        this.totalo = totalo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    String unit;
    String maxo;
    String mino;
    String totalo;
    String remark;
}
