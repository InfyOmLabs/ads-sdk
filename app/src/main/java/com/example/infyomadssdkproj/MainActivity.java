package com.example.infyomadssdkproj;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.infyom.adssdk.InfyOmAds;
import com.infyom.adssdk.aditerface.Interstitial;

public class MainActivity extends AppCompatActivity {
    Button showAds;
    RelativeLayout rlBanner,rl_native;
    View tv_space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAds = findViewById(R.id.btn_next);
        rlBanner = findViewById(R.id.rl_banner);
        rl_native = findViewById(com.infyom.adssdk.R.id.rl_native);
        tv_space = findViewById(com.infyom.adssdk.R.id.tv_space);

        InfyOmAds.initializeAds(this);
        InfyOmAds.enableTestMode(this);

        InfyOmAds.initDefaultValue();
        InfyOmAds.showBanner(this,rlBanner,1);

        InfyOmAds.showNative(this,rl_native,tv_space,1, InfyOmAds.AdTemplate.NATIVE_100);

        showAds.setOnClickListener(v -> {
            InfyOmAds.showInterstitial(1, this, new Interstitial() {
                @Override
                public void onAdClose(boolean isFail) {
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }
            });
        });
    }

    @Override
    protected void onResume() {

        Application application = getApplication();

        if (!(application instanceof MyApplication)) {
            Log.e("TAG", "Failed to cast application class");
        }
        super.onResume();
    }
}