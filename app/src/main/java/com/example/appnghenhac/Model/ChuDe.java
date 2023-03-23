package com.example.appnghenhac.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//KHOI TAO MODEL CHUDE//
public class ChuDe implements Serializable {

    @SerializedName("IDCHUDE")
    @Expose
    private String idchude;
    @SerializedName("TENCHUDE")
    @Expose
    private String tenchude;
    @SerializedName("HINHCHUDE")
    @Expose
    private String hinhchude;

    public String getIdchude() {
        return idchude;
    }

    public void setIdchude(String idchude) {
        this.idchude = idchude;
    }

    public String getTenchude() {
        return tenchude;
    }

    public void setTenchude(String tenchude) {
        this.tenchude = tenchude;
    }

    public String getHinhchude() {
        return hinhchude;
    }

    public void setHinhchude(String hinhchude) {
        this.hinhchude = hinhchude;
    }

}