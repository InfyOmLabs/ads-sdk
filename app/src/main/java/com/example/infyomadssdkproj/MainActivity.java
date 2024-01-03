package com.example.infyomadssdkproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;
import com.infyom.adssdk.InfyOmAds;
import com.infyom.adssdk.ShimmerLayout;
import com.infyom.adssdk.aditerface.Interstitial;

public class MainActivity extends AppCompatActivity {
    Button showAds;
    RelativeLayout rlBanner,rl_native;
    View tv_space;
    ShimmerLayout space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAds = findViewById(R.id.btn_next);
        rlBanner = findViewById(R.id.rl_banner);
        space = findViewById(R.id.shimmer_space);
        rl_native = findViewById(com.infyom.adssdk.R.id.rl_native);
        tv_space = findViewById(com.infyom.adssdk.R.id.tv_space);

        InfyOmAds.enableTestMode(this);

        InfyOmAds.showBanner(MainActivity.this,rlBanner,space,1);
        InfyOmAds.showNative(MainActivity.this,rl_native,tv_space,3, InfyOmAds.AdTemplate.NATIVE_100);

        showAds.setOnClickListener(v -> {
            InfyOmAds.showInterstitial(1, this, new Interstitial() {
                @Override
                public void onAdClose(boolean isFail) {
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }
            });
        });
    }


    private ConsentInformation consentInformation;

    void consentForm() {
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);

        consentInformation.requestConsentInfoUpdate (
                this,
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.
                        if (consentInformation.isConsentFormAvailable()) {
                            Log.e("TAG", "onConsentInfoUpdateSuccess: FORM AVAILABLE");
                            loadForm();
                        } else {
                            Log.e("TAG", "onConsentInfoUpdateSuccess: NOT FORM AVAILABLE");

                        }
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        // Handle the error.
                        Log.e("TAG", "onConsentInfoUpdateFailure: "+formError.getMessage() );
                    }
                });
    }

    public void loadForm() {
        // Loads a consent form. Must be called on the main thread.
        UserMessagingPlatform.loadConsentForm(
                this,
                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        Log.e("TAG", "onConsentFormLoadSuccess: SUCCESS");
                        if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm.show(
                                    MainActivity.this,
                                    new ConsentForm.OnConsentFormDismissedListener() {
                                        @Override
                                        public void onConsentFormDismissed(@Nullable FormError formError) {
                                            if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.OBTAINED) {
                                                // App can start requesting ads.
                                                InfyOmAds.showBanner(MainActivity.this,rlBanner,space,1);
                                                InfyOmAds.showNative(MainActivity.this,rl_native,tv_space,1, InfyOmAds.AdTemplate.NATIVE_50);
                                            }

                                            // Handle dismissal by reloading form.
                                            loadForm();
                                        }
                                    });
                        }
                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        // Handle Error.
                        Log.e("TAG", "onConsentFormLoadFailure: "+formError.getMessage() );
                    }
                }
        );
    }
}