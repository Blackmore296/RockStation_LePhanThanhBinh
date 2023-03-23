package com.example.appnghenhac.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//KHOI TAO MODEL CT_CHUDE//
public class CTChuDe implements Serializable {

    @SerializedName("IDCTCHUDE")
    @Expose
    private String idctchude;
    @SerializedName("IDCHUDE")
    @Expose
    private String idchude;
    @SerializedName("TENCTCHUDE")
    @Expose
    private String tenctchude;
    @SerializedName("HINHCTCHUDE")
    @Expose
    private String hinhctchude;

    public String getIdctchude() {
        return idctchude;
    }

    public void setIdctchude(String idctchude) {
        this.idctchude = idctchude;
    }

    public String getIdchude() {
        return idchude;
    }

    public void setIdchude(String idchude) {
        this.idchude = idchude;
    }

    public String getTenctchude() {
        return tenctchude;
    }

    public void setTenctchude(String tenctchude) {
        this.tenctchude = tenctchude;
    }

    public String getHinhctchude() {
        return hinhctchude;
    }

    public void setHinhctchude(String hinhctchude) {
        this.hinhctchude = hinhctchude;
    }

}
