package com.infyom.adssdk;

import android.os.CountDownTimer;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;

public  class Constants {

    public static boolean isTimeFinish = true;
    public static boolean isBannerClicked = false;
    public static boolean isNativeClicked = false;
    public static boolean isPreloadedNative = false;
    public static boolean isSplashRun = true;
    public static boolean isSplashRunNative = true;
    public static boolean isPreloadedFbNative = false;
    public static NativeAd nativeAds = null;
    public static AdView adView = null;
    public static InterstitialAd interAdmob = null;
    public static com.facebook.ads.InterstitialAd interFb = null;
    public static boolean isAdShowing = false;
    public static boolean isAdLoading = false;
    public static CountDownTimer mCountTimer = null;

}
