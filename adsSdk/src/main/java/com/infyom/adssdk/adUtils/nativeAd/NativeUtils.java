package com.infyom.adssdk.adUtils.nativeAd;

import android.content.Context;
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

public class NativeUtils {

     static String mUnitId;
//     public static int loadFailed = 0;
     public static void load_native(Context context,RelativeLayout rlNative, View space,int admob,boolean isBigNative,int preAdmobId) {

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
//                loadFailed = 0;
                try {
                    if (rlNative.getChildCount() > 0) {
                        rlNative.removeAllViews();
                    }

                    View view;
                    if (isBigNative) {
                        view = LayoutInflater.from(context).inflate(R.layout.ad_300, null);
                        populate300AppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.unified));

                    } else {
                        view = LayoutInflater.from(context).inflate(R.layout.ad_100, null);
                        populateAppInstallAdViewMedia(nativeAd, (NativeAdView) view.findViewById(R.id.unified));
                    }
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
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Constants.nativeAds = null;
//                if (InfyOmAds.isConnectingToInternet(context)) {
//                    if (loadFailed != 3) {
//                        Log.e("N_TAG", "onAdFailedToLoad: "+loadFailed );
//                        loadFailed++;
//                        load_native(context, rlNative, space, admob, isBigNative, preAdmobId);
//                    }
//                }
//                try {
//                    space.setVisibility(View.VISIBLE);
//                    rlNative.setVisibility(View.GONE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                NativeUtilsFb.loadFbNative(context,rlNative,space,isBigNative);
            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void showNative(Context context,RelativeLayout rlNative, View space,int admob,boolean isBigNative,int admobPreId) {
        if (Constants.nativeAds != null) {
            try {
                if (rlNative.getChildCount() > 0) {
                    rlNative.removeAllViews();
                }

                View view;
                if (isBigNative) {
                    view = LayoutInflater.from(context).inflate(R.layout.ad_300, null);
                    populate300AppInstallAdViewMedia(Constants.nativeAds, (NativeAdView) view.findViewById(R.id.unified));
                } else {
                    view = LayoutInflater.from(context).inflate(R.layout.ad_100, null);
                    populateAppInstallAdViewMedia(Constants.nativeAds, (NativeAdView) view.findViewById(R.id.unified));
                }
                space.setVisibility(View.GONE);
                rlNative.setVisibility(View.VISIBLE);
                rlNative.removeAllViews();
                rlNative.addView(view);
//                Log.e("NATIVE_ADS---->", "showNative: "+Constants.nativeAds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            load_native(context,rlNative,space,admobPreId,isBigNative, admobPreId);

        } else {

            if (Constants.isSplashRunNative)  {
                Constants.isSplashRunNative = false;
                Constants.isPreloadedNative = true;
                load_native(context,rlNative,space,admob,isBigNative,admobPreId);
            } else {
                Constants.isPreloadedNative = false;
                load_native(context,rlNative,space,admob,isBigNative,admobPreId);
            }

        }

    }

    public static void populateAppInstallAdViewMedia(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        try {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_icon));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
            unifiedNativeAdView.setImageView(unifiedNativeAdView.findViewById(R.id.ad_image));
            unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_rating));

            if (unifiedNativeAd.getIcon() == null) {
                ((ImageView) unifiedNativeAdView.getIconView()).setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setVisibility(View.VISIBLE);
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            }

            if (unifiedNativeAd.getMediaContent() == null) {
                ((MediaView) unifiedNativeAdView.getMediaView()).setVisibility(View.GONE);
                if (unifiedNativeAd.getImages().size() > 0) {
                    ((ImageView) unifiedNativeAdView.getImageView()).setVisibility(View.INVISIBLE);
                } else {
                    ((ImageView) unifiedNativeAdView.getImageView()).setVisibility(View.VISIBLE);
                    ((ImageView) unifiedNativeAdView.getImageView()).setImageDrawable(unifiedNativeAd.getImages().get(0).getDrawable());
                }
            } else {
                ((MediaView) unifiedNativeAdView.getMediaView()).setVisibility(View.VISIBLE);
            }


            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.INVISIBLE);
            } else {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }

            if (unifiedNativeAd.getStore() == null) {
                ((TextView) unifiedNativeAdView.getStoreView()).setVisibility(View.GONE);
            } else {
                ((TextView) unifiedNativeAdView.getStoreView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
            }

            if (unifiedNativeAd.getAdvertiser() == null) {
                ((TextView) unifiedNativeAdView.getAdvertiserView()).setVisibility(View.GONE);
                if (unifiedNativeAd.getStarRating() == null) {
                    unifiedNativeAdView.getStarRatingView().setVisibility(View.GONE);
                } else {
                    unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
                    ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
                    unifiedNativeAdView.getStoreView().setVisibility(View.GONE);
                }
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
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

    public static void populate300AppInstallAdViewMedia(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {

        try {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            } else {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.VISIBLE);
                ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
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