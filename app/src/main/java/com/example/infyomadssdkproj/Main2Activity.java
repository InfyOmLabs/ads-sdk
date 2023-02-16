package com.example.infyomadssdkproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.infyom.adssdk.InfyOmAds;

import com.infyom.adssdk.aditerface.Interstitial;

public class Main2Activity extends AppCompatActivity {
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

//        InfyOmAds.showBanner(this,rlBanner,1);
        InfyOmAds.showNative(this,rl_native,tv_space,1, InfyOmAds.AdTemplate.NATIVE_300);

        showAds.setOnClickListener(v -> {
            InfyOmAds.showInterstitial(2, this, new Interstitial() {
                @Override
                public void onAdClose(boolean isFail) {
                    startActivity(new Intent(Main2Activity.this,MainActivity3.class));
                }
            });
        });


    }
}