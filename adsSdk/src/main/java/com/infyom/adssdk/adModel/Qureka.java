package com.infyom.adssdk.adModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Qureka {

    @SerializedName("interstitial")
    @Expose
    private String interstitial;
    @SerializedName("interstitial_image")
    @Expose
    private String interstitialImage;
    @SerializedName("native")
    @Expose
    private String _native;
    @SerializedName("native_image")
    @Expose
    private String nativeImage;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;

    public String getInterstitial() {
        return interstitial;
    }

    public void setInterstitial(String interstitial) {
        this.interstitial = interstitial;
    }

    public String getInterstitialImage() {
        return interstitialImage;
    }

    public void setInterstitialImage(String interstitialImage) {
        this.interstitialImage = interstitialImage;
    }

    public String getNative() {
        return _native;
    }

    public void setNative(String _native) {
        this._native = _native;
    }

    public String getNativeImage() {
        return nativeImage;
    }

    public void setNativeImage(String nativeImage) {
        this.nativeImage = nativeImage;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

}