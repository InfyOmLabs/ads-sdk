package com.infyom.adssdk.adUtils.banner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.InfyOmAds;

public class CollapseAdBanner {
    static String mUnitId = null;
    static Context context = null;
    static View mSpace;
    static RelativeLayout rlBanner;
    static int adMobId = 1;
    static AdsAccountProvider accountProvider;
    static boolean isInitBanner = false;
    static AdRequest adRequest;

    public static void showBanner(Context mContext, RelativeLayout mRlBanner, View space, int mAdMobId, InfyOmAds.BannerAdTemplate bannerAdTemplate) {

        context = mContext;
        mSpace = space;
        rlBanner = mRlBanner;
        adMobId = mAdMobId;
        accountProvider = new AdsAccountProvider(context);
        isInitBanner = true;

        loadBannerAd(bannerAdTemplate);
    }


    public static void loadBannerAd(InfyOmAds.BannerAdTemplate bannerAdTemplate) {

        if (adMobId == 1) {
            mUnitId = accountProvider.getCollapseBanner1();
        } else if (adMobId == 2) {
            mUnitId = accountProvider.getCollapseBanner2();
        } else {
            mUnitId = accountProvider.getCollapseBanner3();
        }

        AdView adView = new AdView(context);
        adView.setAdUnitId(mUnitId);
        adView.setAdListener( new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("ADS_SDK-->", "failed : " +loadAdError.toString());
//                    BannerUtilsFb.loadFbBanner(context, rlBanner,mSpace);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("ADS_SDK-->", "load_ads : success call" );
//                    loadFailed = 0;
//                    setAdView( finalAdView );
                try {
                    if (rlBanner.getChildCount() > 0) {
                        rlBanner.removeAllViews();
                    }

                    rlBanner.setVisibility(View.VISIBLE);
                    mSpace.setVisibility(View.GONE);

                    rlBanner.addView(adView);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

                Constants.isBannerClicked = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Constants.isBannerClicked = false;
                    }
                },accountProvider.getBannerAdsTime() * 1000);
            }
        });


        AdSize adSize = getAdSize(context,rlBanner);
        adView.setAdSize(adSize);
        Bundle extras = new Bundle();

        if (bannerAdTemplate == InfyOmAds.BannerAdTemplate.COLLAPSE_TOP) {
            extras.putString("collapsible", "top");
        } else {
            extras.putString("collapsible", "bottom");
        }

        adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle( AdMobAdapter.class, extras)
                .build();
        adView.loadAd(adRequest);
    }


    static AdSize getAdSize(Context context, RelativeLayout rlBanner) {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = rlBanner.getWidth();

        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

}
