package com.infyom.adssdk.adUtils.banner;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;


public class BannerUtilsFb {

    public static void show_banner(Context context, RelativeLayout bannerView) {

        if (Constants.adViewFb != null) {

            try {
                if (bannerView.getChildCount() > 0) {
                    bannerView.removeAllViews();
                }

                bannerView.addView(Constants.adViewFb);
            } catch (Exception e) {
                e.printStackTrace();
            }

            loadFbBanner(context, bannerView, false);
        } else {
            if (Constants.isSplashRun)  {
                Constants.isSplashRun = false;
                loadFbBanner(context, bannerView, false);
            } else {
                loadFbBanner(context, bannerView, true);
            }

        }
    }

    static void loadFbBanner(Context context, RelativeLayout adContainer, boolean isFailed) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        AdView adView = new AdView(context, accountProvider.getFbBannerAds(), AdSize.BANNER_HEIGHT_50);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("BANNER_ERROR-->", adError.getErrorMessage());
                Constants.adViewFb = null;
                loadFbBanner(context, adContainer, true);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("BANNER-->", "banner: " + Constants.adViewFb);

                if (isFailed) {

                    try {
                        if (adContainer.getChildCount() > 0) {
                            adContainer.removeAllViews();
                        }

                        adContainer.addView(adView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    Constants.adViewFb = adView;
                    loadFbBanner(context, adContainer, false);

                } else {
                    Constants.adViewFb = adView;

                }
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }

}
