package com.infyom.adssdk.adUtils.banner;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;

public class AdBanner {
    static AdView mAdView = null;
    static String mUnitId = null;
    static Context context = null;
    static View mSpace;
    static RelativeLayout rlBanner;
    static int adMobId = 1;
    static AdsAccountProvider accountProvider;
    static boolean isInitBanner = false;

    public static void showBanner(Context mContext, RelativeLayout mRlBanner, View space, int mAdMobId) {


        context = null;
        initAdView();

        context = mContext;
        mSpace = space;
        rlBanner = mRlBanner;
        adMobId = mAdMobId;
        accountProvider = new AdsAccountProvider(context);

        isInitBanner = true;

        load_ads();
    }

    static void initAdView() {
        mAdView = null;
    }

    static AdView getAdView() {
        return mAdView;
    }

    static void setAdView(AdView adView) {
        mAdView = adView;
    }

    public static void load_ads() {

        if (getAdView() == null) {
            if (adMobId == 1) {
                mUnitId = accountProvider.getBannerAds1();
            } else if (adMobId == 2) {
                mUnitId = accountProvider.getBannerAds2();
            } else {
                mUnitId = accountProvider.getBannerAds3();
            }

            AdView adView = new AdView(context);
            adView.setAdUnitId(mUnitId);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    BannerUtilsFb.loadFbBanner(context, rlBanner,mSpace);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("AD-BANNER", "load_ads : success call" );
//                    loadFailed = 0;
                    setAdView(adView);
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



//            AdSize adSize = getAdSize(context, rlBanner);
            adView.setAdSize(AdSize.BANNER);
            adView.loadAd(getAdRequest());
        } else {
            Log.e("AD-BANNER", "load_ads : already loaded call" );
        }
    }

    static void loadAfterFail() {
        AdView adView = new AdView(context);
        adView.setAdUnitId(mUnitId);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("AD-BANNER", "onAdFailedToLoad: " + loadAdError);
                initAdView();
//                if (loadFailed != 3) {
//                    Log.e("AD-BANNER", "onAdFailedToLoad: " + loadFailed);
//                    loadFailed++;
//                    loadAfterFail();
//                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("AD-BANNER", "load_ads : success call" );
//                loadFailed = 0;
                setAdView(adView);
            }
        });

        try {
            if (rlBanner.getChildCount() > 0) {
                rlBanner.removeAllViews();
            }
            rlBanner.addView(adView);
        } catch (Exception e) {
            e.printStackTrace();
        }


        AdSize adSize = getAdSize(context, rlBanner);
        adView.setAdSize(adSize);
        adView.loadAd(getAdRequest());
    }

    static AdRequest getAdRequest() {
        AdRequest adRequest = new AdRequest.Builder().build();
        return adRequest;
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
        return AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, adWidth);
    }

    public static Activity getCurrentActivity() {
        return (Activity) context;
    }

    public static void pauseAdView() {
        if (getAdView() != null && isInitBanner) {
            getAdView().pause();
            Log.e("AD-BANNER", "pauseAdView: ");
        }
    }

    public static void resumeAdView() {
        if (getAdView() != null && isInitBanner) {
            getAdView().resume();
            Log.e("AD-BANNER", "resumeAdView: ");
        }
    }

    public static void destroyAdView() {
        if (getAdView() != null && isInitBanner) {
            isInitBanner = false;
            getAdView().destroy();
            Log.e("AD-BANNER", "destroyAdView: ");
        }
    }

}
