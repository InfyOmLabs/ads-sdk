package com.infyom.adssdk.adUtils.inter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.infyom.adssdk.AdsAccountProvider;
import com.infyom.adssdk.Constants;
import com.infyom.adssdk.R;
import com.infyom.adssdk.aditerface.Interstitial;

public class InterstitialQuereca {

    public static void showInterstitial(Context context, Interstitial listener) {

        String url;

        AdsAccountProvider adsAccountProvider = new AdsAccountProvider(context);

        Dialog dialog = new Dialog(context, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_inter_quereca);

        ImageView imageView = dialog.findViewById(R.id.iv_url_image);
        ImageView ivCancel = dialog.findViewById(R.id.iv_cancel);

        if (adsAccountProvider.getInterImageUrl() != null) {
            Glide.with(context).load(adsAccountProvider.getInterImageUrl()).centerCrop().into(imageView);
        }

        ivCancel.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            listener.onAdClose(false);

            Constants.isTimeFinish = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Constants.isTimeFinish = true;
                }
            },adsAccountProvider.getAdsTime() * 1000);

        });

        url = adsAccountProvider.getInterUrl();

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        String finalUrl = url;

        imageView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
            context.startActivity(browserIntent);
        });

        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivCancel.setVisibility(View.VISIBLE);
            }
        },3000);

    }
}
