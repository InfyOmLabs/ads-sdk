package com.infyom.adssdk.adUtils.nativeAd;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.InfyOmAds;
import com.infyom.adssdk.R;

import java.util.ArrayList;
import java.util.List;

public class NativeUtilsFb {
    public static int loadFail = 0;
    public static void loadFbNative(Context context, RelativeLayout nativeAdLayout, View space,boolean isBigNative) {
        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        NativeAd nativeAd = new NativeAd(context, accountProvider.getFbNativeAds());

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load

                if (InfyOmAds.isConnectingToInternet(context)) {
                    if (loadFail != 3) {
                        Log.e("N_F_TAG", "onError: "+loadFail);
                        loadFail++;
                        loadFbNative(context, nativeAdLayout, space,isBigNative);
                    } else {
                        loadFail = 0;
                    }
                }

            }

            @Override
            public void onAdLoaded(Ad ad) {
                loadFail = 0;
                try {
                    if (nativeAdLayout.getChildCount() > 0) {
                        nativeAdLayout.removeAllViews();
                    }

                    space.setVisibility(View.GONE);
                    nativeAdLayout.setVisibility(View.VISIBLE);
                    inflateNativeAd(nativeAd, context, nativeAdLayout,isBigNative);
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
        };

        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());

    }

    private static void inflateNativeAd(NativeAd nativeAd, Context context, RelativeLayout nativeAdLayout,boolean isBigNative) {

        try {
            if (nativeAdLayout.getChildCount() > 0) {
                nativeAdLayout.removeAllViews();
            }

            nativeAd.unregisterView();

            LayoutInflater inflater = LayoutInflater.from(context);
            LinearLayout adView;
            if (isBigNative) {
                adView = (LinearLayout) inflater.inflate(R.layout.native_view_layout, nativeAdLayout, false);
            } else {
                adView = (LinearLayout) inflater.inflate(R.layout.banner_native_view_layout, nativeAdLayout, false);
            }
            nativeAdLayout.addView(adView);

            NativeAdLayout nativeAdLayout1 = new NativeAdLayout(context);
            nativeAdLayout.addView(nativeAdLayout1);

            // Add the AdOptionsView
            LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
            AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout1);
            adChoicesContainer.removeAllViews();
            adChoicesContainer.addView(adOptionsView, 0);

            // Create native UI using the ad metadata.
            MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
            TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
            MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
            TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
            TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
            TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
            Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

            nativeAdTitle.setText(nativeAd.getAdvertiserName());
            nativeAdBody.setText(nativeAd.getAdBodyText());
            nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);

            nativeAd.registerViewForInteraction(
                    adView, nativeAdMedia, nativeAdIcon, clickableViews);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}