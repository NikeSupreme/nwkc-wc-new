package com.example.st;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.example.st.util.DialogUtil;
import com.example.st.util.Global;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void navigateToLogin() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentTransaction ft = MainActivity.this.getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, loginFragment);
        ft.commit();
    }

    private void navigateToModel() {
        SharedPreferences sharedPreferences = getSharedPreferences("modelNum", MODE_PRIVATE);
        int num = sharedPreferences.getInt("num", -1);
        Fragment fragment;
        if (num == 1) {
            fragment = SmartModel1Fragment.newInstance();
        } else if (num == 2) {
            fragment = SmartModel2Fragment.newInstance();
        } else if (num == 3) {
            fragment = SmartModel3Fragment.newInstance();
        } else if (num == 4) {
            fragment = SmartModel4Fragment.newInstance();
        } else {
            fragment = LoginFragment.newInstance();
        }
        FragmentTransaction ft = MainActivity.this.getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment);
        ft.commit();
    }

    private void initData() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
        SharedPreferences sp = getSharedPreferences("accountInfo", MODE_PRIVATE);
        String ip = sp.getString("ip", "");
        String id = sp.getString("id", "");
        if (ip.isEmpty() || id.isEmpty()) {
            navigateToLogin();
        } else {
            WlinkSDKManger.getInstance().gw_LoginAndBindLan("123", ip, id, "", new WlinkSDKManger.Callback() {
                @Override
                public void handle(Object Obj) {
                    Log.i(TAG, "gw_LoginAndBindLan: " + JSON.toJSONString(Obj));
                    int res = (int) Obj;
                    if (res == 0) {
                        navigateToModel();
                        Global.gwId = id;
                        Log.i(TAG, "局域网登录成功");
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                navigateToLogin();
                                Toast.makeText(MainActivity.this, "连接网关失败，请检查网络配置或重试", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void error(String errorCode, String msg) {
                    Log.i(TAG, "error: ");
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DialogUtil.showExitDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getSharedPreferences("accountInfo", MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();
                    SharedPreferences sp2 = getSharedPreferences("modelNum", MODE_PRIVATE);
                    sp2.edit().clear().apply();
                    finish();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
