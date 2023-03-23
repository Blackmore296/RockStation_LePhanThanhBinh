package com.example.appnghenhac.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//KHOI TAO MODEL PLAYLIST//
public class Playlist implements Serializable {

    @SerializedName("IDPLAYLIST")
    @Expose
    private String idplaylist;
    @SerializedName("TEN")
    @Expose
    private String ten;
    @SerializedName("NEN")
    @Expose
    private String nen;
    @SerializedName("ICON")
    @Expose
    private String icon;

    public String getIdplaylist() {
        return idplaylist;
    }

    public void setIdplaylist(String idplaylist) {
        this.idplaylist = idplaylist;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNen() {
        return nen;
    }

    public void setNen(String nen) {
        this.nen = nen;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
