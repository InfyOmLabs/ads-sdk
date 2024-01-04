package com.infyom.adssdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.BuildConfig;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.infyom.adssdk.adUtils.banner.AdBanner;
import com.infyom.adssdk.adUtils.banner.BannerUtilsFb;
import com.infyom.adssdk.adUtils.inter.InterstitialUtils;
import com.infyom.adssdk.adUtils.inter.InterstitialUtilsFb;
import com.infyom.adssdk.adUtils.nativeAd.NativeUtils;
import com.infyom.adssdk.adUtils.nativeAd.NativeUtils150;
import com.infyom.adssdk.adUtils.nativeAd.NativeUtils350;
import com.infyom.adssdk.adUtils.nativeAd.NativeUtils40;
import com.infyom.adssdk.adUtils.nativeAd.NativeUtils50;
import com.infyom.adssdk.adUtils.nativeAd.NativeUtilsFb;
import com.infyom.adssdk.aditerface.Interstitial;


public class InfyOmAds {

    public static final String ADMOB = "admob";
    public static final String FB = "facebook";
    public static final String PRE = "pre";
    public static final String LOAD = "load";

    public enum AdTemplate {
        NATIVE_350,
        NATIVE_150,
        NATIVE_300,
        NATIVE_100,
        NATIVE_50,
        NATIVE_40
    }

    public static boolean isClicked = false;
    static AdsAccountProvider myPref;

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public static void loadPreInterstitial(int admob, Context context) {
        if (Constants.isTimeFinish) {
            AdsAccountProvider myPref = new AdsAccountProvider(context);

            String adsType;
            if (admob == 1) {
                adsType = myPref.getFirstAdsType();
            } else if (admob == 2) {
                adsType = myPref.getSecondAdsType();
            } else {
                adsType = myPref.getThirdAdsType();
            }

            if ((myPref.getAdsType().equals(ADMOB) && !adsType.equals(FB))  || adsType.equals(ADMOB)) {

                if (myPref.getLoad().equals(PRE)) {
                    String mUnitId = "oa";
                    if (admob == 1) {
                        mUnitId = myPref.getInterAds1();
                    } else if (admob == 2) {
                        mUnitId = myPref.getInterAds2();
                    } else {
                        mUnitId = myPref.getInterAds3();
                    }

                    AdRequest adRequest = InterstitialUtils.getAdRequest();

                    InterstitialUtils.loadPreInterstitialAd(context, mUnitId, adRequest);
                }
            }
        }
    }

    public static void showInterstitial(int admob, Context context, Interstitial listener) {

        if (checkValidation()) {
            return;
        }

        if (Constants.isTimeFinish) {
            AdsAccountProvider myPref = new AdsAccountProvider(context);

            String adsType;
            if (admob == 1) {
                adsType = myPref.getFirstAdsType();
            } else if (admob == 2) {
                adsType = myPref.getSecondAdsType();
            } else {
                adsType = myPref.getThirdAdsType();
            }

            if ((myPref.getAdsType().equals(ADMOB) && !adsType.equals(FB)) || adsType.equals(ADMOB)) {
                InterstitialUtils interstitialUtils = new InterstitialUtils(context, listener, admob);

                if (myPref.getLoad().equals(PRE)) {
                    interstitialUtils.showPreInterstitial();
                } else {
                    interstitialUtils.loadInterstitial();
                }
            } else if ((myPref.getAdsType().equals(FB) || adsType.equals(FB))) {
                if (Constants.isAdLoading) {
                    return;
                }
                Constants.isAdLoading = true;
                InterstitialUtilsFb.loadInterstitial(context, listener, null    );
            }  else {
                listener.onAdClose(false);
            }

        } else {
            Constants.isAdLoading = false;
            listener.onAdClose(true);
        }
    }

    public static void showBanner(Context context, RelativeLayout bannerContainer,View space, int admob) {


        if (Constants.isBannerClicked) {
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

        if ((myPref.getAdsType().equals(ADMOB) && !adsType.equals(FB)) || adsType.equals(ADMOB)) {
            AdBanner.showBanner(context, bannerContainer, space,admob);
        } else if (myPref.getAdsType().equals(FB) || adsType.equals(FB)) {
            BannerUtilsFb.loadFbBanner(context, bannerContainer,space);
        }
    }

    public static void showNative(Context context, RelativeLayout nativeContainer, View space, int admob, AdTemplate adTemplate) {
        if (Constants.isNativeClicked) {
            return;
        }

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

        if ((myPref.getAdsType().equals(ADMOB) && !adsType.equals(FB)) || adsType.equals(ADMOB)) {

            if (adTemplate.equals(AdTemplate.NATIVE_350)){
                NativeUtils350.showWhenLoadAd(context, nativeContainer, space, admob);
            } else  if (adTemplate.equals(AdTemplate.NATIVE_300)) {
                NativeUtils.load_native(context, nativeContainer, space, admob, true, preloadId);
            } else if (adTemplate.equals(AdTemplate.NATIVE_150)){
                NativeUtils150.loadNative150AdViewMedia(context, nativeContainer, space, admob);
            } else if (adTemplate.equals(AdTemplate.NATIVE_100)) {
                NativeUtils.load_native(context, nativeContainer, space, admob, false, preloadId);
            } else if (adTemplate.equals(AdTemplate.NATIVE_50)) {
                NativeUtils50.load_native(context, nativeContainer, space, admob);
            } else {
                NativeUtils40.load_native(context, nativeContainer, space, admob);
            }

        } else if (myPref.getAdsType().equals(FB) || adsType.equals(FB)) {
            NativeUtilsFb.loadFbNative(context, nativeContainer, space, adTemplate.equals(AdTemplate.NATIVE_300));
        }
    }

    public static void initializeAds(Context context) {
        MobileAds.initialize(context, initializationStatus -> {
        });
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
        myPref.setAdsNative4("/6499/example/native");
        myPref.setAdsNative5("/6499/example/native");
        myPref.setFbBannerAds("IMG_16_9_LINK#YOUR_PLACEMENT_ID");
        myPref.setFbNativeAds("IMG_16_9_LINK#YOUR_PLACEMENT_ID");
        myPref.setFbInterAds("IMG_16_9_LINK#YOUR_PLACEMENT_ID");
        myPref.setAdsTime(0);
        myPref.setBannerAdsTime(30);
        myPref.setNativeAdsTime(30);
        myPref.setSplashAds(1);
        myPref.setAdsType(ADMOB);
        myPref.setFirstAdsType(ADMOB);
        myPref.setSecondAdsType(ADMOB);
        myPref.setThirdAdsType(ADMOB);
        myPref.setLoad(LOAD);

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
        }, 1200);
        return false;
    }
}
