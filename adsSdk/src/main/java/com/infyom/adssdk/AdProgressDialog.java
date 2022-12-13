package com.infyom.adssdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;


public class AdProgressDialog {

    public static Dialog show(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.getWindow().addFlags(2);
        dialog.setCancelable(false);
        dialog.getWindow().setDimAmount(0.7f);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.ads_progress_dialog);

        try {
            if (!((Activity) context).isFinishing()) {
                dialog.show();
            }
        } catch (Exception unused) {
            unused.printStackTrace();
        }
        return dialog;
    }
}