package com.infyom.adssdk.adUtils.nativeAd;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.R;

public class NativeUtils150 {

    static String mUnitId;

    public static void loadNative150AdViewMedia(Context context,RelativeLayout rlNative, View space,int admob) {

        AdsAccountProvider accountProvider = new AdsAccountProvider(context);

        if (admob == 1) {
            mUnitId = accountProvider.getAdsNative4();
        } else if (admob == 2) {
            mUnitId = accountProvider.getAdsNative4();
        } else {
            mUnitId = accountProvider.getAdsNative4();
        }

        AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                try {
                    if (rlNative.getChildCount() > 0) {
                        rlNative.removeAllViews();
                    }

                    View view;
                        view = LayoutInflater.from(context).inflate(R.layout.ad_150, null);
                    populate150AdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.ads_100));

                    space.setVisibility(View.GONE);
                    rlNative.setVisibility(View.VISIBLE);
                    rlNative.removeAllViews();
                    rlNative.addView(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Constants.isNativeClicked = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Constants.isNativeClicked = false;
                    }
                },accountProvider.getNativeAdsTime() * 1000);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Constants.nativeAds = null;

                NativeUtilsFb.loadFbNative(context, rlNative, space,false);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private static void populate150AdViewMedia(NativeAd nativeAd, NativeAdView viewById) {

        try {
            viewById.setMediaView((MediaView) viewById.findViewById(R.id.adMediaView));
            viewById.setHeadlineView(viewById.findViewById(R.id.title));
            viewById.setIconView(viewById.findViewById(R.id.app_icon));
            viewById.setBodyView(viewById.findViewById(R.id.subTitle));
            viewById.setCallToActionView(viewById.findViewById(R.id.ad_Button));

            viewById.setStarRatingView(viewById.findViewById(R.id.ad_stars));
            viewById.setAdvertiserView(viewById.findViewById(R.id.ad_advertiser));
            viewById.setStoreView(viewById.findViewById(R.id.ad_advertiser));


            if (nativeAd.getIcon() == null) {
                ((ImageView) viewById.getIconView()).setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) viewById.getIconView()).setVisibility(View.VISIBLE);
                ((ImageView) viewById.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            }

            if (nativeAd.getMediaContent() == null) {
                ((MediaView) viewById.getMediaView()).setVisibility(View.GONE);
                if (nativeAd.getImages().size() > 0) {
                    ((ImageView) viewById.getImageView()).setVisibility(View.INVISIBLE);
                } else {
                    ((ImageView) viewById.getImageView()).setVisibility(View.VISIBLE);
                    ((ImageView) viewById.getImageView()).setImageDrawable(nativeAd.getImages().get(0).getDrawable());
                }
            } else {
                ((MediaView) viewById.getMediaView()).setVisibility(View.VISIBLE);
            }

            if (nativeAd.getHeadline() == null) {
                ((TextView) viewById.getHeadlineView()).setVisibility(View.INVISIBLE);
            } else {
                ((TextView) viewById.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) viewById.getHeadlineView()).setText(nativeAd.getHeadline());
            }

            if (nativeAd.getBody() == null) {
                viewById.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                viewById.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) viewById.getBodyView()).setText(nativeAd.getBody());
            }

            if (nativeAd.getCallToAction() == null) {
                viewById.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                viewById.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) viewById.getCallToActionView()).setText(nativeAd.getCallToAction());
                viewById.getCallToActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "onClick: " + "Native");
                    }
                });
            }

            if (nativeAd.getStarRating() != null){
                ((TextView) viewById.getStoreView()).setVisibility(View.GONE);
                viewById.getStarRatingView().setVisibility(View.VISIBLE);
                ((RatingBar) viewById.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());

            } else if (nativeAd.getStore() != null){
                viewById.getStarRatingView().setVisibility(View.GONE);
                ((TextView) viewById.getStoreView()).setVisibility(View.VISIBLE);
                ((TextView) viewById.getStoreView()).setText(nativeAd.getStore());
            } else if (nativeAd.getAdvertiser() != null){
                viewById.getStarRatingView().setVisibility(View.GONE);
                ((TextView) viewById.getAdvertiserView()).setVisibility(View.VISIBLE);
                ((TextView) viewById.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            } else {
                ((TextView) viewById.getStoreView()).setVisibility(View.GONE);
                viewById.getStarRatingView().setVisibility(View.GONE);
            }

            viewById.setNativeAd(nativeAd);

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
