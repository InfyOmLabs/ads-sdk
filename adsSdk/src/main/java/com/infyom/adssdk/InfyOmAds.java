package com.infyom.adssdk;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.BuildConfig;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.infyom.adssdk.adUtils.BannerUtils;
import com.infyom.adssdk.adUtils.BannerUtilsFb;
import com.infyom.adssdk.adUtils.InterstitialUtils;
import com.infyom.adssdk.adUtils.InterstitialUtilsFb;
import com.infyom.adssdk.adUtils.NativeUtils;
import com.infyom.adssdk.adUtils.NativeUtils40;
import com.infyom.adssdk.adUtils.NativeUtils50;
import com.infyom.adssdk.adUtils.NativeUtilsFb;
import com.infyom.adssdk.aditerface.Interstitial;


public class InfyOmAds {

    public enum AdTemplate {
        NATIVE_300,
        NATIVE_100,
        NATIVE_50,
        NATIVE_40
    }

    static boolean isClicked = false;
    //    static ArrayList<Datum> adsIdsList = new ArrayList<>();
    static AdsAccountProvider myPref;

    public static void initDefaultValue() {
        Constants.isSplashRun = true;
        Constants.isSplashRunNative = true;
        Constants.isPreloadedNative = false;
        Constants.isPreloadedFbNative = false;
        Constants.nativeAds = null;
        Constants.adView = null;
        Constants.adViewFb = null;
        Constants.nativeAdFb = null;
        Constants.interAdmob = null;
        Constants.interFb = null;
        Constants.isAdShowing = false;
        Constants.mCountTimer = null;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void showInterstitial(int admob, Context context, Interstitial listener) {

        if (checkValidation()) {
            return;
        }

        AdsAccountProvider myPref = new AdsAccountProvider(context);

        String adsType;
        if (admob == 1) {
            adsType = myPref.getFirstAdsType();
        } else if (admob == 2) {
            adsType = myPref.getSecondAdsType();
        } else {
            adsType = myPref.getThirdAdsType();
        }

        if ((myPref.getAdsType().equals("admob") && !adsType.equals("facebook")) || adsType.equals("admob")) {
            InterstitialUtils interstitialUtils = new InterstitialUtils(context, listener, admob);
            interstitialUtils.show_interstitial(Constants.interAdmob);
        } else if ((myPref.getAdsType().equals("facebook") || adsType.equals("facebook"))) {
            InterstitialUtilsFb.loadInterstitial(context, listener);
        } else {
            listener.onAdClose(false);
        }
    }

    public static void showBanner(Context context, RelativeLayout bannerContainer, int admob) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);
        int preloadId;

        String adsType;

        if (admob == 1) {
            adsType = myPref.getFirstAdsType();
            preloadId = 2;

        } else if (admob == 2) {
            adsType = myPref.getSecondAdsType();
            preloadId = 3;
        } else {
            adsType = myPref.getThirdAdsType();
            preloadId = 1;
        }

        if ((myPref.getAdsType().equals("admob") && !adsType.equals("facebook")) || adsType.equals("admob")) {
            BannerUtils.show_banner(context, bannerContainer, admob, preloadId);
        } else if (myPref.getAdsType().equals("facebook") || adsType.equals("facebook")) {
            BannerUtilsFb.show_banner(context, bannerContainer);
        }
    }

    public static void showNative(Context context, RelativeLayout nativeContainer, View space, int admob,AdTemplate adTemplate) {
        AdsAccountProvider myPref = new AdsAccountProvider(context);
        int preloadId;
        String adsType;

        if (admob == 1) {
            adsType = myPref.getFirstAdsType();
            preloadId = 2;
        } else if (admob == 2) {
            adsType = myPref.getSecondAdsType();
            preloadId = 3;
        } else {
            adsType = myPref.getThirdAdsType();
            preloadId = 1;
        }

        if ((myPref.getAdsType().equals("admob") && !adsType.equals("facebook")) || adsType.equals("admob")) {

            if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                NativeUtils.showNative(context, nativeContainer, space, admob, true, preloadId);
            } else if (adTemplate.equals(AdTemplate.NATIVE_100)){
                NativeUtils.showNative(context, nativeContainer, space, admob, false, preloadId);
            } else if (adTemplate.equals(AdTemplate.NATIVE_50)){
                NativeUtils50.showNative(context, nativeContainer, space, admob, preloadId);
            } else {
                NativeUtils40.showNative(context, nativeContainer, space, admob, preloadId);
            }

        } else if (myPref.getAdsType().equals("facebook") || adsType.equals("facebook")) {
            NativeUtilsFb.showNativeFb(context, nativeContainer, space, adTemplate.equals(AdTemplate.NATIVE_300));
        }
    }

    public static void initializeAds(Context context) {
        MobileAds.initialize(context, initializationStatus -> {});
        AudienceNetworkAds.initialize(context);
        if (BuildConfig.DEBUG) {
            AdSettings.setTestMode(true);
        }
    }

    public static void enableTestMode(Context context) {
        myPref = new AdsAccountProvider(context);

        myPref.setOpenAds("/6499/example/app-open");
        myPref.setBannerAds1("/6499/example/banner");
        myPref.setBannerAds2("/6499/example/banner");
        myPref.setBannerAds3("/6499/example/banner");
        myPref.setInterAds1("/6499/example/interstitial");
        myPref.setInterAds2("/6499/example/interstitial");
        myPref.setInterAds3("/6499/example/interstitial");
        myPref.setNativeAds1("/6499/example/native");
        myPref.setNativeAds2("/6499/example/native");
        myPref.setNativeAds3("/6499/example/native");
        myPref.setFbBannerAds("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        myPref.setFbNativeAds("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        myPref.setFbInterAds("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        myPref.setAdsTime(1);
        myPref.setSplashAds(1);
        myPref.setAdsType("admob");
        myPref.setFirstAdsType("admob");
        myPref.setSecondAdsType("admob");
        myPref.setThirdAdsType("admob");
    }


    static boolean checkValidation() {
        if (isClicked) {
            return true;
        }
        isClicked = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isClicked = false;
            }
        }, 1500);
        return false;
    }
}
