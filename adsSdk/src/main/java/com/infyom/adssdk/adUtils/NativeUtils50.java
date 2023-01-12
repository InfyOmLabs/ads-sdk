
package com.infyom.adssdk.adUtils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.R;

public class NativeUtils50 {

    public static String mUnitId;
    public static void load_native(Context context, RelativeLayout rlNative, View space, int admob) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        if (admob == 1) {
            mUnitId = accountProvider.getNativeAds1();
        } else if (admob == 2) {
            mUnitId = accountProvider.getNativeAds2();
        } else {
            mUnitId = accountProvider.getNativeAds3();
        }

        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                                                // && !(context instanceof SplashScreen)
                try {
                    if (rlNative.getChildCount() > 0) {
                        rlNative.removeAllViews();
                    }

                    View view = LayoutInflater.from(context).inflate( R.layout.ad_50, null);
                    populateNative50(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                    space.setVisibility(View.GONE);
                    rlNative.setVisibility(View.VISIBLE);
                    rlNative.removeAllViews();
                    rlNative.addView(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                if (!Constants.isPreloadedNative ) {
//                    Constants.isPreloadedNative = true;
//
//
//
//
//                    load_native(context, rlNative, space, admob);
//                } else {
//                    Constants.nativeAds = nativeAd;
//                }
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                try {
                    Constants.nativeAds = null;
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                load_native(context, rlNative, space, admob);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void showNative(Context context,RelativeLayout rlNative, View space,int admob,int admobPreId) {

        if (Constants.nativeAds != null) {
            try {
                if (rlNative.getChildCount() > 0) {
                    rlNative.removeAllViews();
                }

                View view = LayoutInflater.from(context).inflate( R.layout.ad_50, null);
                populateNative50(Constants.nativeAds, (NativeAdView) view.findViewById(R.id.unified));
                space.setVisibility(View.GONE);
                rlNative.setVisibility(View.VISIBLE);
                rlNative.removeAllViews();
                rlNative.addView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            load_native(context,rlNative,space,admobPreId);
        } else {
            Constants.isPreloadedNative = false;
            load_native(context,rlNative,space,admob);
        }

    }

    private static boolean adHasOnlyStore(NativeAd nativeAd) {
        return !TextUtils.isEmpty(nativeAd.getStore()) && TextUtils.isEmpty(nativeAd.getAdvertiser());
    }

    public static void populateNative50(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        try {
            TextView primaryView =  unifiedNativeAdView.findViewById(R.id.primary);
            TextView secondaryView =  unifiedNativeAdView.findViewById(R.id.secondary);
            TextView bodyText =  unifiedNativeAdView.findViewById(R.id.body);
            RatingBar ratingBar = (RatingBar) unifiedNativeAdView.findViewById(R.id.rating_bar);
            ratingBar.setEnabled(false);
            Button callToActionView =  unifiedNativeAdView.findViewById(R.id.cta);
            ImageView iconView =  unifiedNativeAdView.findViewById(R.id.icon);
//        background = (ConstraintLayout) view.findViewById(R.id.background);

            String store = unifiedNativeAd.getStore();
            String advertiser = unifiedNativeAd.getAdvertiser();
            String headline = unifiedNativeAd.getHeadline();
            String body = unifiedNativeAd.getBody();
            String callToAction = unifiedNativeAd.getCallToAction();
            Double starRating = unifiedNativeAd.getStarRating();
            NativeAd.Image icon = unifiedNativeAd.getIcon();
            unifiedNativeAdView.setCallToActionView(callToActionView);
            unifiedNativeAdView.setHeadlineView(primaryView);
//        unifiedNativeAdView.setMediaView(mediaView);
            secondaryView.setVisibility(View.VISIBLE);
            if (adHasOnlyStore(unifiedNativeAd)) {
                unifiedNativeAdView.setStoreView(secondaryView);
            } else if (!TextUtils.isEmpty(advertiser)) {
                unifiedNativeAdView.setAdvertiserView(secondaryView);
                store = advertiser;
            } else {
                store = "";
            }
            primaryView.setText(headline);
            callToActionView.setText(callToAction);
            if (starRating != null && starRating.doubleValue() > Double.longBitsToDouble(1)) {
                secondaryView.setVisibility(View.GONE);
                ratingBar.setVisibility(View.VISIBLE);
                ratingBar.setRating(starRating.floatValue());
                unifiedNativeAdView.setStarRatingView(ratingBar);
            } else {
                secondaryView.setText(store);
                secondaryView.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.GONE);
            }
            if (icon != null) {
                iconView.setVisibility(View.VISIBLE);
                iconView.setImageDrawable(icon.getDrawable());
            } else {
                iconView.setVisibility(View.GONE);
            }
            TextView textView = bodyText;
            if (textView != null) {
                textView.setText(body);
                unifiedNativeAdView.setBodyView(bodyText);
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}