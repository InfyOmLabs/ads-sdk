package com.infyom.adssdk.adUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.infyom.adssdk.AdsAccountProvider;

public class BannerQuereca {

    public static void showBanner(Context context, RelativeLayout rlBanner) {

        String url;

        AdsAccountProvider adsAccountProvider = new AdsAccountProvider(context);

        ImageView bannerImageView = new ImageView(context);
        LinearLayout.LayoutParams paramsDefault =  new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerImageView.setLayoutParams(paramsDefault);

        Glide.with(context).load(adsAccountProvider.getImageUrl()).centerCrop().into(bannerImageView);

        rlBanner.addView(bannerImageView);

        url = adsAccountProvider.getUrl();

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        String finalUrl = url;

        rlBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
                context.startActivity(browserIntent);
            }
        });

    }
}
