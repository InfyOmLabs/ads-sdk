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


public class BannerUtilsFb {
//    private static int loadFail = 0;

    public static void loadFbBanner(Context context, RelativeLayout adContainer) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        AdView adView = new AdView(context, accountProvider.getFbBannerAds(), AdSize.BANNER_HEIGHT_50);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("BANNER_ERROR-->", adError.getErrorMessage());
//                if (InfyOmAds.isConnectingToInternet(context)) {
//                    if (loadFail != 3) {
//                        Log.e("B_F_TAG", "onError: "+loadFail );
//                        loadFail++;
//                        loadFbBanner(context, adContainer);
//                    } else {
//                        loadFail = 0;
//                    }
//                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
//                loadFail = 0;
                try {
                    if (adContainer.getChildCount() > 0) {
                        adContainer.removeAllViews();
                    }

                    adContainer.addView(adView);
                } catch (Exception e) {
                    e.printStackTrace();
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
