package com.infyom.adssdk.adUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.infyom.adssdk.AdProgressDialog;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.InfyOmAds;
import com.infyom.adssdk.aditerface.Interstitial;

public class InterstitialUtils {
    Context mContext;
    String mUnitId;
    private Dialog dialog;
    AdsAccountProvider myPref;
    Interstitial listener;
    int adMobId;

    public InterstitialUtils(Context mContext,Interstitial listener,int adMobId) {
        this.mContext = mContext;
        this.listener = listener;
        this.adMobId = adMobId;
    }

     void load_interstitial(boolean isFailed) {

        myPref = new AdsAccountProvider(mContext);

        if (!isFailed) {
            if (adMobId == 1) {
                adMobId = 2;
            } else if(adMobId == 2) {
                adMobId = 3;
            } else {
                adMobId = 1;
            }
        } else {
            dialog = AdProgressDialog.show(mContext);
        }

        if (adMobId == 1) {
            mUnitId = myPref.getInterAds1();
        } else if (adMobId == 2) {
            mUnitId = myPref.getInterAds2();
        } else {
            mUnitId = myPref.getInterAds3();
        }

        if (Constants.isTimeFinish) {

            InterstitialAd.load(mContext, mUnitId, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);

                    if (InfyOmAds.isConnectingToInternet(mContext)) {
                        listener.onAdClose(true);
                    } else {
                        listener.onAdClose(true);
                        Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    setCountDown();
                    if (isFailed) {
                        show_interstitial(interstitialAd);
                    } else {
                        Constants.interAdmob = interstitialAd;
                    }
                }
            });
        } else {
            listener.onAdClose(true);
        }

    }

    static void setCountDown() {
        Constants.mCountTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                Constants.interAdmob = null;
                Constants.mCountTimer.cancel();
                Constants.mCountTimer = null;
            }


        };

        Constants.mCountTimer.start();
    }

     static void dismissCount() {
        if (Constants.mCountTimer != null) {
            Constants.mCountTimer.cancel();
            Constants.mCountTimer = null;
            Constants.interAdmob = null;
        }
    }


    public void show_interstitial(InterstitialAd mInterstitialAd) {

        if ( mInterstitialAd != null) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            mInterstitialAd.show((Activity) mContext);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    Constants.isInterDismissed = false;
                    dismissCount();
                    load_interstitial(false);
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Constants.isInterDismissed = true;
                    Constants.isTimeFinish = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Constants.isTimeFinish = true;
                        }
                    }, myPref.getAdsTime() * 1000);
                    listener.onAdClose(true);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    show_interstitial(mInterstitialAd);
                }
            });
        } else {
            load_interstitial(true);
        }

    }
}