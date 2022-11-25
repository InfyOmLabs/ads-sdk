package com.infyom.adssdk.adModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facebook {
    @SerializedName("interstitial")
    @Expose
    private String interstitial;
    @SerializedName("native")
    @Expose
    private String _native;
    @SerializedName("banner")
    @Expose
    private String banner;

    public String getInterstitial() {
        return interstitial;
    }

    public void setInterstitial(String interstitial) {
        this.interstitial = interstitial;
    }

    public String getNative() {
        return _native;
    }

    public void setNative(String _native) {
        this._native = _native;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

}