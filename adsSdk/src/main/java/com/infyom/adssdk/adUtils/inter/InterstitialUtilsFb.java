package com.infyom.adssdk.adUtils.inter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.infyom.adssdk.AdProgressDialog;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.aditerface.Interstitial;

public class InterstitialUtilsFb {
    public static void loadInterstitial(Context mContext, Interstitial listener,Dialog dialog) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(mContext);

        if (dialog == null) {
            dialog = AdProgressDialog.show(mContext);
        }

        InterstitialAd interstitialAd = new InterstitialAd(mContext,accountProvider.getFbInterAds());

        Dialog finalDialog = dialog;
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                if (finalDialog != null && finalDialog.isShowing()) {
                    finalDialog.dismiss();
                }
                Constants.isAdLoading = false;
                Constants.isAdShowing = true;
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                if (finalDialog != null && finalDialog.isShowing()) {
                    finalDialog.dismiss();
                }
//                    failLoad = 0;
                Constants.isAdShowing = false;
                Constants.isTimeFinish = false;
                Constants.isAdLoading = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Constants.isTimeFinish = true;
                    }
                },accountProvider.getAdsTime() * 1000);
                listener.onAdClose(false);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("INTER_ERROR-->", "Interstitial ad failed to load: " + adError.getErrorMessage());
                if (finalDialog.isShowing()) {
                    finalDialog.dismiss();
                }
                Constants.isTimeFinish = false;
                Constants.isAdLoading = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Constants.isTimeFinish = true;
                    }
                }, accountProvider.getAdsTime() * 1000);
                listener.onAdClose(true);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {}

            @Override
            public void onLoggingImpression(Ad ad) {}

        };

        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }
}