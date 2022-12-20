package com.infyom.adssdk.adUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.infyom.adssdk.AdProgressDialog;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.InfyOmAds;
import com.infyom.adssdk.aditerface.Interstitial;

public class InterstitialUtilsFb {

    public static void loadInterstitial(Context mContext, Interstitial listener) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(mContext);

        if (InfyOmAds.isConnectingToInternet(mContext)) {


            Dialog dialog = AdProgressDialog.show(mContext);
            InterstitialAd interstitialAd = new InterstitialAd(mContext,accountProvider.getFbInterAds());

            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Constants.isAdShowing = true;
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    Constants.isAdShowing = false;
                    Constants.isTimeFinish = false;
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
                    dialog.dismiss();
                    Constants.isTimeFinish = false;
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
                    dialog.dismiss();
                    if (!interstitialAd.isAdInvalidated()) {
                        interstitialAd.show();
                    } else {
                        loadInterstitial(mContext,listener);
                    }

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
        } else {
            Toast.makeText(mContext, "Please check internet connection", Toast.LENGTH_SHORT).show();
            listener.onAdClose(true);
        }
    }
}