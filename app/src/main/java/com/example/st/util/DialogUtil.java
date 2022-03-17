package com.example.st.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.st.R;


/**
 * Created by hxc on 2017/5/9.
 */

public class DialogUtil {

    /**
     * 设置对话框的宽度
     **/
    public static void changeDialogWidth(Dialog dialog, Context mContext,int padding) {
        if (null != dialog) {
            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = wm.getDefaultDisplay().getWidth();
            WindowManager.LayoutParams params = dialog.getWindow()
                    .getAttributes();
            params.width = screenWidth - 2 * padding;
            dialog.getWindow().setAttributes(params);
        }
    }

    public static Dialog showCommonTipDialog(Context mContext,
                                             boolean isCancel, String message) {
        View layout = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_alarm, null);
        Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
        dialog.setContentView(layout);
        dialog.setCancelable(isCancel);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        changeDialogWidth(dialog, mContext,360);
        if (!TextUtils.isEmpty(message)) {
            TextView message_tv = (TextView) layout.findViewById(R.id.tv_info);
            message_tv.setMovementMethod(new ScrollingMovementMethod());
            message_tv.setText(message);
        }
        // 绑定事件
        return dialog;
    }

    public static Dialog showExitDialog(Context mContext,final View.OnClickListener clearOnClick,final View.OnClickListener exitOnClick) {
        View layout = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_exit, null);
        Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        changeDialogWidth(dialog, mContext,260);
            TextView message_tv = (TextView) layout.findViewById(R.id.tv_info);
            message_tv.setMovementMethod(new ScrollingMovementMethod());
            message_tv.setText("确定要退出吗?");
        Button cancelBtn = (Button) layout.findViewById(R.id.btn_cancel);
        Button clearBtn = (Button) layout.findViewById(R.id.btn_clear);
        Button exitBtn = (Button) layout.findViewById(R.id.btn_exit);
        // 绑定事件
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clearOnClick) {
                    clearOnClick.onClick(v);
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != exitOnClick) {
                    exitOnClick.onClick(v);
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
