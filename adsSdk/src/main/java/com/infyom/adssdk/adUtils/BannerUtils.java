package com.infyom.adssdk.adUtils;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.InfyOmAds;


public class BannerUtils {
    static String mUnitId = null;
    public static int loadFailed = 0;

    public static void show_banner(Context context, RelativeLayout bannerView, int adMobId,int preloadId) {

        if (Constants.adView != null) {

            try {
                if (bannerView.getChildCount() > 0) {
                    bannerView.removeAllViews();
                }

                bannerView.addView(Constants.adView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            load_ads(context, bannerView, preloadId,false);

        } else {
            if (Constants.isSplashRun)  {
                Constants.isSplashRun = false;
                load_ads(context, bannerView, adMobId,false);
            } else {
                load_ads(context, bannerView, adMobId,true);
            }
        }

    }

    public static void load_ads(Context context, RelativeLayout bannerView, int adMobId,boolean isFailed) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(context);
        if (adMobId == 1) {
            mUnitId = accountProvider.getBannerAds1();
        } else if (adMobId == 2) {
            mUnitId = accountProvider.getBannerAds2();
        } else {
            mUnitId = accountProvider.getBannerAds3();
        }

        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(mUnitId);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Constants.adView = null;
                if (InfyOmAds.isConnectingToInternet(context)) {
                    if (loadFailed != 3) {
                        Log.e("B_TAG", "onAdFailedToLoad: "+loadFailed );
                        loadFailed++;
                        load_ads(context, bannerView, adMobId,true);
                    }
                }

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                loadFailed = 0;

                if (isFailed) {

                    try {
                        if (bannerView.getChildCount() > 0) {
                            bannerView.removeAllViews();
                        }

                        bannerView.addView(adView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Constants.adView = adView;
                    load_ads(context, bannerView, adMobId,false);

                } else {

                    Constants.adView = adView;

                }
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }

}
