package com.infyom.adssdk;

import android.content.Context;
import android.content.SharedPreferences;



public class AdsAccountProvider {
    static final String KEY_OPEN_ADS = "key_open_ads";
    static final String KEY_NATIVE1 = "key_native_ads1";
    static final String KEY_NATIVE2= "key_native_ads2";
    static final String KEY_NATIVE3 = "key_native_ads3";
    static final String KEY_BANNER_ADS1 = "key_banner_ads1";
    static final String KEY_BANNER_ADS2 = "key_banner_ads2";
    static final String KEY_BANNER_ADS3 = "key_banner_ads3";
    static final String KEY_INTERSTITIAL_ADS1 = "key_inter_ads1";
    static final String KEY_INTERSTITIAL_ADS2 = "key_inter_ads2";
    static final String KEY_INTERSTITIAL_ADS3 = "key_inter_ads3";
    static final String KEY_ADS_TYPE = "key_ads_type";
    static final String KEY_ADS_TIME = "key_ads_time";
    static final String KEY_IS_SPLASH_ADS = "key_is_splash_ads";
    static final String KEY_FB_BANNER = "key_banner_fb_ads";
    static final String KEY_FB_NATIVE = "key_native_fb_ads";
    static final String KEY_FB_INTER = "key_inter_fb_ads";
    static final String KEY_FIRST_ADS_TYPE = "key_first_ads_type";
    static final String KEY_SECOND_ADS_TYPE = "key_second_ads_type";
    static final String KEY_THIRD_ADS_TYPE = "key_third_ads_type";
    static final String KEY_PACKAGE = "key_third_ads_type";
    static final String KEY_url = "key_url";
    static final String KEY_IMAGE_URL = "key_image_url";
    static final String KEY_INTER_URL = "key_inter_url";
    static final String KEY_INTER_IMAGE_URL = "key_inter_image_url";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AdsAccountProvider(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setPackage(String packageName) {
        sharedPreferences.edit().putString(KEY_PACKAGE, packageName).apply();
    }

    public String getPackage() {
        return sharedPreferences.getString(KEY_PACKAGE, "null");
    }

    public void setOpenAds(String user) {
        sharedPreferences.edit().putString(KEY_OPEN_ADS, user).apply();
    }


    public String getOpenAds() {
        return sharedPreferences.getString(KEY_OPEN_ADS, "");
    }


    public void setFbBannerAds(String user) {
        sharedPreferences.edit().putString(KEY_FB_BANNER, user ).apply();
    }

    public String getFbBannerAds() {
        return sharedPreferences.getString(KEY_FB_BANNER, "0a" );

    }

    public void setFbInterAds(String user) {
        sharedPreferences.edit().putString(KEY_FB_INTER, user ).apply();
    }

    public String getFbInterAds() {
        return sharedPreferences.getString(KEY_FB_INTER, "0a" );

    }

    public void setFbNativeAds(String user) {
        sharedPreferences.edit().putString(KEY_FB_NATIVE, user ).apply();
    }

    public String getFbNativeAds() {
        return sharedPreferences.getString(KEY_FB_NATIVE, "0a" );

    }

    // Banner ads
    public void setBannerAds1(String user) {
        sharedPreferences.edit().putString(KEY_BANNER_ADS1, user ).apply();
    }

    public String getBannerAds1() {
        return sharedPreferences.getString(KEY_BANNER_ADS1, "0a" );

    }

    public void setBannerAds2(String user) {
        sharedPreferences.edit().putString(KEY_BANNER_ADS2, user ).apply();
    }

    public String getBannerAds2() {
        return sharedPreferences.getString(KEY_BANNER_ADS2, "0a" );

    }

    public void setBannerAds3(String user) {
        sharedPreferences.edit().putString(KEY_BANNER_ADS3, user ).apply();
    }

    public String getBannerAds3() {
        return sharedPreferences.getString(KEY_BANNER_ADS3, "0a" );

    }

    // Inter ads
    public void setInterAds1(String user) {
        sharedPreferences.edit().putString(KEY_INTERSTITIAL_ADS1, user ).apply();
    }

    public String getInterAds1() {
        return sharedPreferences.getString(KEY_INTERSTITIAL_ADS1, "0a" );

    }

    public void setInterAds2(String user) {
        sharedPreferences.edit().putString(KEY_INTERSTITIAL_ADS2, user ).apply();
    }

    public String getInterAds2() {
        return sharedPreferences.getString(KEY_INTERSTITIAL_ADS2, "0a" );

    }

    public void setInterAds3(String user) {
        sharedPreferences.edit().putString(KEY_INTERSTITIAL_ADS3, user ).apply();
    }

    public String getInterAds3() {
        return sharedPreferences.getString(KEY_INTERSTITIAL_ADS3, "0a" );

    }

    // Native ads
    public void setNativeAds1(String user) {
        sharedPreferences.edit().putString(KEY_NATIVE1, user ).apply();
    }

    public String getNativeAds1() {
        return sharedPreferences.getString(KEY_NATIVE1, "0a" );

    }

    public void setNativeAds2(String user) {
        sharedPreferences.edit().putString(KEY_NATIVE2, user ).apply();
    }

    public String getNativeAds2() {
        return sharedPreferences.getString(KEY_NATIVE2, "0a" );

    }

    public void setNativeAds3(String user) {
        sharedPreferences.edit().putString(KEY_NATIVE3, user ).apply();
    }

    public String getNativeAds3() {
        return sharedPreferences.getString(KEY_NATIVE3, "0a" );

    }

    public void setAdsTime(int user) {
        sharedPreferences.edit().putInt(KEY_ADS_TIME, user ).apply();
    }

    public int getAdsTime() {
        return sharedPreferences.getInt(KEY_ADS_TIME, 20);
    }

    public void setSplashAds(int user) {
        sharedPreferences.edit().putInt(KEY_IS_SPLASH_ADS, user ).apply();
    }

    public int getSplashAds() {
        return sharedPreferences.getInt(KEY_IS_SPLASH_ADS, 0);
    }

    public void setFirstAdsType(String user) {
        sharedPreferences.edit().putString(KEY_FIRST_ADS_TYPE, user ).apply();
    }

    public String getFirstAdsType() {
        return sharedPreferences.getString(KEY_FIRST_ADS_TYPE, "0q" );

    }

    public void setSecondAdsType(String user) {
        sharedPreferences.edit().putString(KEY_SECOND_ADS_TYPE, user ).apply();
    }

    public String getSecondAdsType() {
        return sharedPreferences.getString(KEY_SECOND_ADS_TYPE, "0q" );

    }

    public void setThirdAdsType(String user) {
        sharedPreferences.edit().putString(KEY_THIRD_ADS_TYPE, user ).apply();
    }

    public String getThirdAdsType() {
        return sharedPreferences.getString(KEY_THIRD_ADS_TYPE, "0q" );

    }

    public void setAdsType(String user) {
        sharedPreferences.edit().putString(KEY_ADS_TYPE, user ).apply();
    }

    public String getAdsType() {
        return sharedPreferences.getString(KEY_ADS_TYPE, "admob" );

    }

    public void setUrl(String url) {
        sharedPreferences.edit().putString(KEY_url, url ).apply();
    }

    public String getUrl() {
        return sharedPreferences.getString(KEY_url, "0Ab" );

    }

    public void setImageUrl(String url) {
        sharedPreferences.edit().putString(KEY_IMAGE_URL, url ).apply();
    }

    public String getImageUrl() {
        return sharedPreferences.getString(KEY_IMAGE_URL, "0Ab" );

    }

    public void setInterUrl(String url) {
        sharedPreferences.edit().putString(KEY_INTER_URL, url ).apply();
    }

    public String getInterUrl() {
        return sharedPreferences.getString(KEY_INTER_URL, "0Ab" );

    }

    public void setInterImageUrl(String url) {
        sharedPreferences.edit().putString(KEY_INTER_IMAGE_URL, url ).apply();
    }

    public String getInterImageUrl() {
        return sharedPreferences.getString(KEY_INTER_IMAGE_URL, "0Ab" );

    }

}
