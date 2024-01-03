package com.infyom.adsmanager;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;

import com.infyom.adssdk.adUtils.banner.AdBanner;

public class MyApplication extends Application implements ActivityLifecycleCallbacks, LifecycleObserver {
    private Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);

    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {}

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        AdBanner.resumeAdView();

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        if (AdBanner.getCurrentActivity() != null && (AdBanner.getCurrentActivity() == currentActivity)) {
            AdBanner.pauseAdView();
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (AdBanner.getCurrentActivity() != null && (AdBanner.getCurrentActivity() == currentActivity)) {
            AdBanner.destroyAdView();
        }
    }




}

