package com.infyom.adssdk.adUtils.nativeAd;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MediaAspectRatio;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.R;

public class NativeUtils350 {

     static String mUnitId;
     static NativeAd mNativeAd = null;
    static AdsAccountProvider accountProvider;



     public static void showWhenLoadAd(Context context,RelativeLayout rlNative, View space,int admob) {
         accountProvider = new AdsAccountProvider(context);

        if (mNativeAd == null) {
            if (admob == 1) {
                mUnitId = accountProvider.getNativeAds1();
            } else if (admob == 2) {
                mUnitId = accountProvider.getNativeAds2();
            } else {
                mUnitId = accountProvider.getNativeAds3();
            }

            NativeAdOptions adOptions =
                    new NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.PORTRAIT).build();

            AdLoader adLoader = new AdLoader.Builder(context, mUnitId).forNativeAd(nativeAd -> {
                mNativeAd = nativeAd;
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
                    Log.e("ADS_SDK-->", "Native ad failed to load: " + loadAdError.toString());
                    mNativeAd = null;
                    NativeUtilsFb.loadFbNative(context,rlNative,space,true);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    showNativeAds(context,rlNative,space);
                }
            }).withNativeAdOptions(adOptions).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            mNativeAd = null;
        }

    }

    static void showNativeAds(Context context,RelativeLayout rlNative, View space) {
        try {
            if (rlNative.getChildCount() > 0) {
                rlNative.removeAllViews();
            }

            View view;

            view = LayoutInflater.from(context).inflate(R.layout.ad_new_350, null);
            populateAdsBig(mNativeAd, (NativeAdView) view.findViewById(R.id.unified));
            mNativeAd = null;
            space.setVisibility(View.GONE);
            rlNative.setVisibility(View.VISIBLE);
            rlNative.removeAllViews();
            rlNative.addView(view);

        } catch (Exception e) {
            mNativeAd = null;
            e.printStackTrace();
        }

    }

    public  static void populateAdsBig(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        try {
            unifiedNativeAdView.setMediaView(unifiedNativeAdView.findViewById(R.id.native_ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_sponsers));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));

            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.INVISIBLE);
            } else {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }

            if (unifiedNativeAd.getAdvertiser() == null) {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setVisibility(View.GONE);
            } else {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            }


            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }

            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
                unifiedNativeAdView.getCallToActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "onClick: " + "Native");
                    }
                });
            }

            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
            }

            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }


}