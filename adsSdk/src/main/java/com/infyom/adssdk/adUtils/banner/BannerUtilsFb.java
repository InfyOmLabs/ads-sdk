package com.infyom.adssdk.adUtils.banner;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;


public class BannerUtilsFb {
//    private static int loadFail = 0;

    public static void loadFbBanner(Context context, RelativeLayout adContainer, View space) {


        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        AdView adView = new AdView(context, accountProvider.getFbBannerAds(), AdSize.BANNER_HEIGHT_50);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("BANNER_ERROR-->", adError.getErrorMessage());
                adContainer.setVisibility(View.GONE);
                space.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdLoaded(Ad ad) {

                try {
                    if (adContainer.getChildCount() > 0) {
                        adContainer.removeAllViews();
                    }

                    adContainer.setVisibility(View.VISIBLE);
                    space.setVisibility(View.GONE);

                    adContainer.addView(adView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                Constants.isBannerClicked = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Constants.isBannerClicked = false;
                    }
                },accountProvider.getBannerAdsTime() * 1000);
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());

    }

}
