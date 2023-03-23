package com.example.appnghenhac.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//KHOI TAO MODEL CHUDETRONGNGAY//
public class Chudetrongngay {

    @SerializedName("ChuDe")
    @Expose
    private List<ChuDe> chuDe = null;
    @SerializedName("CTChuDe")
    @Expose
    private List<CTChuDe> cTChuDe = null;

    public List<ChuDe> getChuDe() {
        return chuDe;
    }

    public void setChuDe(List<ChuDe> chuDe) {
        this.chuDe = chuDe;
    }

    public List<CTChuDe> getCTChuDe() {
        return cTChuDe;
    }

    public void setCTChuDe(List<CTChuDe> cTChuDe) {
        this.cTChuDe = cTChuDe;
    }

}