package com.infyom.adssdk.adUtils;

import android.content.Context;
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
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.InfyOmAds;
import com.infyom.adssdk.R;

public class NativeUtils {

     static String mUnitId;
     public static int loadFailed = 0;
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
                loadFailed = 0;
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

//                if (!Constants.isPreloadedNative) {
//                    Constants.isPreloadedNative = true;
//
//
//                    load_native(context,rlNative,space,preAdmobId,isBigNative,preAdmobId);
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
                Constants.nativeAds = null;
                if (InfyOmAds.isConnectingToInternet(context)) {
                    if (loadFailed != 3) {
                        Log.e("N_TAG", "onAdFailedToLoad: "+loadFailed );
                        loadFailed++;
                        load_native(context, rlNative, space, admob, isBigNative, preAdmobId);
                    }
                }
                try {
                    space.setVisibility(View.VISIBLE);
                    rlNative.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));

            if (unifiedNativeAd.getHeadline() == null) {
                ((TextView) unifiedNativeAdView.getHeadlineView()).setVisibility(View.INVISIBLE);
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
            return;
        }

    }

//    public static void populateNativeAdViewNew(NativeAd nativeAd, NativeAdView adView) {
//        // Set the media view.
//        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
//
//        // Set other ad assets.
//        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
//        adView.setBodyView(adView.findViewById(R.id.ad_body));
//        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
//        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//        adView.setPriceView(adView.findViewById(R.id.ad_price));
//        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//        adView.setStoreView(adView.findViewById(R.id.ad_store));
//        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
//
//        // The headline and mediaContent are guaranteed to be in every NativeAd.
//        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
//        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
//
//        // These assets aren't guaranteed to be in every NativeAd, so it's important to
//        // check before trying to display them.
//        if (nativeAd.getBody() == null) {
//            adView.getBodyView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getBodyView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
//        }
//
//        if (nativeAd.getCallToAction() == null) {
//            adView.getCallToActionView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getCallToActionView().setVisibility(View.VISIBLE);
//            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//        }
//
//        if (nativeAd.getIcon() == null) {
//            adView.getIconView().setVisibility(View.GONE);
//        } else {
//            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
//            adView.getIconView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getPriceView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }
//
//        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getStoreView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }
//
//        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }
//
//        // This method tells the Google Mobile Ads SDK that you have finished populating your
//        // native ad view with this native ad.
//        adView.setNativeAd(nativeAd);
//
//        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
//        // have a video asset.
////        VideoController vc = nativeAd.getMediaContent().getVideoController();
//
//        // Updates the UI to say whether or not this ad has a video asset.
////        if (vc.hasVideoContent()) {
////            videoStatus.setText(
////                    String.format(
////                            Locale.getDefault(),
////                            "Video status: Ad contains a %.2f:1 video asset.",
////                            nativeAd.getMediaContent().getAspectRatio()));
////            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
////            // VideoController will call methods on this object when events occur in the video
////            // lifecycle.
////            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
////                @Override
////                public void onVideoEnd() {
////                    // Publishers should allow native ads to complete video playback before
////                    // refreshing or replacing them with another ad in the same UI location.
////                    refresh.setEnabled(true);
////                    videoStatus.setText("Video status: Video playback has ended.");
////                    super.onVideoEnd();
////                }
////            });
////        } else {
////            videoStatus.setText("Video status: Ad does not contain a video asset.");
////            refresh.setEnabled(true);
////        }
//    }

}