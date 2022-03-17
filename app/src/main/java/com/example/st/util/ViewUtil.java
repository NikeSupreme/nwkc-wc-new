package com.example.st.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewUtil {
    public static void updateZoneView(ImageView iv,int resId,TextView tv,Boolean isShow){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                iv.setImageResource(resId);
                tv.setVisibility(isShow? View.VISIBLE:View.INVISIBLE);
            }
        });
    }

    public static void updateGasText(TextView tv, String text){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                CharSequence charSequence;
                charSequence = Html.fromHtml(text);
                tv.setText(charSequence);
            }
        });
    }

    public static void updateZoneName(TextView tv, String text){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                tv.setText(text);
            }
        });
    }

    public static void updateText(TextView tv, String text){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                CharSequence charSequence;
                charSequence = Html.fromHtml(text);
                tv.setText(charSequence);
            }
        });
    }


    public static void showDialog(Context context,String msg) {
        MediaUtil.openAssetMusics(context);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = DialogUtil.showCommonTipDialog(context, false, msg);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }, 60 * 1000);
            }
        });

    }


}
