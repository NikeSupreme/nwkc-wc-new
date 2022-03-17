package com.example.st;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.example.st.bean.DeviceAttrsBean;
import com.example.st.bean.DeviceListBean;
import com.example.st.bean.FloorListBean;
import com.example.st.bean.ZoneListBean;
import com.example.st.imp.CallbackImp;
import com.example.st.util.CmdUtil;
import com.example.st.util.Constant;
import com.example.st.util.DialogUtil;
import com.example.st.util.Global;
import com.example.st.util.ViewUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SmartModel3Fragment extends Fragment implements View.OnClickListener {

    private static String TAG = "SmartModel3Fragment";
    private String manFloorName ="女卫";

    private double manH2sValue;
    private double manNh3Value;

    private List<ZoneListBean.InfoBean.ContentBean> manFloorZones = new ArrayList<>();
    private List<DeviceListBean.InfoBean.ContentBean> deviceList = new ArrayList<>();


    private ImageView iv_man_1, iv_man_2, iv_man_3, iv_man_4;
    private ImageView iv_man_5, iv_man_6, iv_man_7;

    private TextView tv_man_1, tv_man_2, tv_man_3, tv_man_4;
    private TextView tv_man_5, tv_man_6, tv_man_7;
    private TextView tv_smell_show;
    private TextView tv_name;

    private TextView tv_exist_1,tv_exist_2,tv_exist_3,tv_exist_4;
    private TextView tv_exist_5,tv_exist_6,tv_exist_7;

    private TextView tv_man_position, tv_man_people, tv_man_env, tv_man_env_value1, tv_man_env_value2;
    private int manNum;
    private int curHour;//通过后一分钟的小时比前一分钟小来判断跨天
    private Timer timer;
    private int manExistNum;

    public static SmartModel3Fragment newInstance() {
        SmartModel3Fragment fragment = new SmartModel3Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smart_model3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();

    }
    private void initView(View view) {
        iv_man_1 = view.findViewById(R.id.iv_man_1);
        iv_man_2 = view.findViewById(R.id.iv_man_2);
        iv_man_3 = view.findViewById(R.id.iv_man_3);
        iv_man_4 = view.findViewById(R.id.iv_man_4);
        iv_man_5 = view.findViewById(R.id.iv_man_5);
        iv_man_6 = view.findViewById(R.id.iv_man_6);
        iv_man_7 = view.findViewById(R.id.iv_man_7);


        tv_man_1 = view.findViewById(R.id.tv_man_1);
        tv_man_2 = view.findViewById(R.id.tv_man_2);
        tv_man_3 = view.findViewById(R.id.tv_man_3);
        tv_man_4 = view.findViewById(R.id.tv_man_4);
        tv_man_5 = view.findViewById(R.id.tv_man_5);
        tv_man_6 = view.findViewById(R.id.tv_man_6);
        tv_man_7 = view.findViewById(R.id.tv_man_7);

        tv_exist_1 = view.findViewById(R.id.tv_exist_1);
        tv_exist_2 = view.findViewById(R.id.tv_exist_2);
        tv_exist_3 = view.findViewById(R.id.tv_exist_3);
        tv_exist_4 = view.findViewById(R.id.tv_exist_4);
        tv_exist_5 = view.findViewById(R.id.tv_exist_5);
        tv_exist_6 = view.findViewById(R.id.tv_exist_6);
        tv_exist_7 = view.findViewById(R.id.tv_exist_7);

        tv_name = view.findViewById(R.id.tv_name);
        tv_smell_show = view.findViewById(R.id.tv_smell_show);

        tv_man_position = view.findViewById(R.id.tv_man_position);
        tv_man_people = view.findViewById(R.id.tv_man_people);
        tv_man_env = view.findViewById(R.id.tv_man_env);
        tv_man_env_value1 = view.findViewById(R.id.tv_man_env_value1);
        tv_man_env_value2 = view.findViewById(R.id.tv_man_env_value2);
    }

    private void initData() {
        Date date = new Date();
        curHour = date.getHours();
        Calendar calendar = Calendar.getInstance();
        Date firstTime = calendar.getTime();
        // 间隔：1分钟
        long period = 1000 * 60;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date();
                int hours = date.getHours();
                Log.i(TAG, "前一分钟: " + curHour);
                Log.i(TAG, "当前: " + hours);
                if (hours < curHour) {
                    manNum = 0;
                }
                curHour = hours;
            }
        }, firstTime, period);
        CmdUtil.reqFloorList(Constant.gwId, new CallbackImp() {
            @Override
            public void success(Object data) {
            }

            @Override
            public void onError(String s) {

            }
        });
        CmdUtil.reqZoneList(Constant.gwId, new CallbackImp() {
            @Override
            public void success(Object data) {
                manFloorZones = Global.manFloorZones;
                setZoneName();
            }

            @Override
            public void onError(String s) {

            }
        });
        CmdUtil.reqDeviceList(Constant.gwId, new CallbackImp() {
            @Override
            public void success(Object data) {
                deviceList = Global.deviceLists;
                ViewUtil.updateZoneName(tv_name, Global.gwName);
            }

            @Override
            public void onError(String s) {

            }
        });

        WlinkSDKManger.getInstance().registerCallback(new WlinkSDKManger.Callback() {
            @Override
            public void handle(Object Obj) {
                Log.i(TAG, "registerCallback: " + JSON.toJSONString(Obj));
                dealReportMsg(Obj);
            }

            @Override
            public void error(String errorCode, String msg) {

            }
        });
    }

    private void setZoneName() {
        if (manFloorZones.size() == 7) {
            ViewUtil.updateZoneName(tv_man_1, manFloorZones.get(0).getName());
            ViewUtil.updateZoneName(tv_man_2, manFloorZones.get(1).getName());
            ViewUtil.updateZoneName(tv_man_3, manFloorZones.get(2).getName());
            ViewUtil.updateZoneName(tv_man_4, manFloorZones.get(3).getName());
            ViewUtil.updateZoneName(tv_man_5, manFloorZones.get(4).getName());
            ViewUtil.updateZoneName(tv_man_6, manFloorZones.get(5).getName());
            ViewUtil.updateZoneName(tv_man_7, manFloorZones.get(6).getName());
        }
    }


    private void dealReportMsg(Object data) {
        DeviceAttrsBean deviceAttrsBean = new Gson().fromJson(JSON.toJSONString(data), DeviceAttrsBean.class);
        if (deviceAttrsBean.getFunc().equals("devAttrsReportEvent")) {
            String devId = deviceAttrsBean.getDevId();
            List<DeviceAttrsBean.AttrsInfoBean> attrsInfos = deviceAttrsBean.getAttrsInfo();
            for (DeviceAttrsBean.AttrsInfoBean bean : attrsInfos
            ) {
                if (bean.getSvcId().equals("23") && bean.getAttrId().equals("1")) {//紧急按钮
                    parseEmergencyBtnData(bean, devId);
                } else if (bean.getSvcId().equals("11") && bean.getAttrId().equals("2")) {//氨气
                    double nh3Value = Double.parseDouble(bean.getAttrValue()) * 0.000759;
                    Log.i(getClass().getName(), "nh3Value: " + nh3Value);
                    updateNH3Status(devId, nh3Value);

                } else if (bean.getSvcId().equals("10") && bean.getAttrId().equals("1")) {//温湿度（硫化氢）
                    double h2sValue = Double.parseDouble(bean.getAttrValue()) * 0.001518;
                    Log.i(getClass().getName(), "h2sValue: " + h2sValue);
                    updateH2sStatus(devId, h2sValue);
                } else if (bean.getSvcId().equals("6") && bean.getAttrId().equals("1")) {//红外
                    String zoneId = CmdUtil.getBelongZoneId(devId);
                    String status = bean.getAttrValue();
                    int index = -1;
                    for (ZoneListBean.InfoBean.ContentBean contentBean :
                            manFloorZones) {
                        if (contentBean.getZoneId().equals(zoneId)) {
                            index = manFloorZones.indexOf(contentBean);
                            updateZoneView(index, status);
                        }
                    }
                }
            }
        }
    }

    private void updateNH3Status(String devId, double nh3Value) {
        manNh3Value = nh3Value;
        Log.i(TAG, "manNh3Value:" + manNh3Value);
        ViewUtil.updateText(tv_man_env_value1, String.valueOf(manNh3Value).length() > 4 ? String.valueOf(manNh3Value).substring(0, 4) : String.valueOf(manNh3Value));
        updateSmellView();
    }

    private void updateH2sStatus(String devId, double h2sValue) {
        manH2sValue = h2sValue;
        Log.i(TAG, "manH2sValue:" + manH2sValue);
        ViewUtil.updateText(tv_man_env_value2, String.valueOf(manH2sValue).length() > 4 ? String.valueOf(manH2sValue).substring(0, 4) : String.valueOf(manH2sValue));
        updateSmellView();
    }

    private void updateSmellView() {
        String manFloorText, womanFloorText, otherFloorText;
        if (manH2sValue > 0.06 || manNh3Value > 1.5) {
            manFloorText = manFloorName + ": " + "<font color = '#FFD400'>有异味</font>";
            ViewUtil.updateText(tv_man_env, "<font color = '#FF0000'>" + "差" + "</font>");
        } else {
            manFloorText = manFloorName + ": 正常 ";
            ViewUtil.updateText(tv_man_env, "<font color = '#00FFB0'>" + "优" + "</font>");
        }
        Log.i(TAG, manFloorText);

        ViewUtil.updateGasText(tv_smell_show, ("异味监测" + " " + manFloorText + "\u3000\u3000"));

    }

    private void updateZoneView(int index, String status) {
        if (TextUtils.equals("1", status)) {//有人
            ++manNum;
            if (manExistNum != 7) {
                ++manExistNum;
            }
        } else if (TextUtils.equals("0", status)) {//没人
            if (manExistNum != 0) {
                --manExistNum;
            }
        }
        ViewUtil.updateText(tv_man_people, manNum + "");
        ViewUtil.updateText(tv_man_position, "<font color = '#FFD400'>" + (7 - manExistNum) + "</font>" + "/7");


        if (index == 0) {
            Log.i(TAG, "女卫生间坑位1---->" + (status.equals("0") ? "没人" : "有人"));
            ViewUtil.updateZoneView(iv_man_1, status.equals("0") ? R.mipmap.icon_top_empty : R.mipmap.icon_top_exist,tv_exist_1,status.equals("1"));
        } else if (index == 1) {
            Log.i(TAG, "女卫生间坑位2---->" + (status.equals("0") ? "没人" : "有人"));
            ViewUtil.updateZoneView(iv_man_2, status.equals("0") ? R.mipmap.icon_top_empty : R.mipmap.icon_top_exist,tv_exist_2,status.equals("1"));
        } else if (index == 2) {
            Log.i(TAG, "女卫生间坑位3---->" + (status.equals("0") ? "没人" : "有人"));
            ViewUtil.updateZoneView(iv_man_3, status.equals("0") ? R.mipmap.icon_top_empty : R.mipmap.icon_top_exist,tv_exist_3,status.equals("1"));
        } else if (index == 3) {
            Log.i(TAG, "女卫生间坑位4---->" + (status.equals("0") ? "没人" : "有人"));
            ViewUtil.updateZoneView(iv_man_4, status.equals("0") ? R.mipmap.icon_bottom_empty : R.mipmap.icon_bottom_exist,tv_exist_4,status.equals("1"));
        } else if (index == 4) {
            Log.i(TAG, "女卫生间坑位5---->" + (status.equals("0") ? "没人" : "有人"));
            ViewUtil.updateZoneView(iv_man_5, status.equals("0") ? R.mipmap.icon_bottom_empty : R.mipmap.icon_bottom_exist,tv_exist_5,status.equals("1"));
        } else if (index == 5) {
            Log.i(TAG, "女卫生间坑位6---->" + (status.equals("0") ? "没人" : "有人"));
            ViewUtil.updateZoneView(iv_man_6, status.equals("0") ? R.mipmap.icon_bottom_empty : R.mipmap.icon_bottom_exist,tv_exist_6,status.equals("1"));
        } else if (index == 6) {
            Log.i(TAG, "女卫生间坑位7---->" + (status.equals("0") ? "没人" : "有人"));
            ViewUtil.updateZoneView(iv_man_7, status.equals("0") ? R.mipmap.icon_bottom_empty : R.mipmap.icon_bottom_exist,tv_exist_7,status.equals("1"));
        }

    }

    private void parseEmergencyBtnData(DeviceAttrsBean.AttrsInfoBean bean, String devId) {
        boolean hasAlarm = bean.getAttrValue().equals("1");
        Log.i(TAG, "dealReportMsg: " + hasAlarm);
        if (hasAlarm) {
            for (DeviceListBean.InfoBean.ContentBean b :
                    Global.deviceLists) {
                if (b.getSubDevId().equals(devId)) {
                    String zoneId = b.getServices().get(0).getZone();
                    for (ZoneListBean.InfoBean.ContentBean contentBean :
                            Global.allZones) {
                        if (contentBean.getZoneId().equals(zoneId)) {
                            String zoneName = contentBean.getName();
                            Log.i(TAG, "alarmMsg: " + "女卫" + zoneName + "有人求助");
                            ViewUtil.showDialog(getActivity(),"女卫" + zoneName + "有人求助");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }
}
