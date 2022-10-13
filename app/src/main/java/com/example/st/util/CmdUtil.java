package com.example.st.util;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.st.WlinkSDKManger;
import com.example.st.bean.DeviceListBean;
import com.example.st.bean.FloorListBean;
import com.example.st.bean.ZoneListBean;
import com.example.st.imp.CallbackImp;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CmdUtil {

    /**
     * [{
     * 'endpoint': 1,
     * 'svcId': '103',
     * 'cmdId': '57',
     * 'cmdValue': [],
     * },]
     */
    public static void reqDeviceList(String gwId, CallbackImp callbackImp) {
        JSONObject jsonObject = new JSONObject();
        ArrayList<JSONObject> arrayList = new ArrayList();
        try {
            jsonObject.put("endpoint", 1);
            jsonObject.put("svcId", "103");
            jsonObject.put("cmdId", "3");
            jsonObject.put("cmdValue", new JSONArray());
            arrayList.add(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        WlinkSDKManger.getInstance().dev_svcCmdsRequest(gwId, arrayList, new WlinkSDKManger.Callback() {
            @Override
            public void handle(Object Obj) {
                Log.i("CmdUtil", "reqDeviceList: " + JSON.toJSONString(Obj));
                parserDeviceList(Obj);
                callbackImp.success(Obj);
            }

            @Override
            public void error(String errorCode, String msg) {

            }
        });

    }

    /**
     * [
     * {
     * 'endpoint': 1,
     * 'svcId': '103',
     * 'cmdId': '50',
     * },
     * ]
     */
    public static void reqFloorList(String gwId, CallbackImp callbackImp) {
        JSONObject jsonObject = new JSONObject();
        ArrayList<JSONObject> arrayList = new ArrayList();
        try {
            jsonObject.put("endpoint", 1);
            jsonObject.put("svcId", "103");
            jsonObject.put("cmdId", "50");
            arrayList.add(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        WlinkSDKManger.getInstance().dev_svcCmdsRequest(gwId, arrayList, new WlinkSDKManger.Callback() {
            @Override
            public void handle(Object Obj) {
                Log.i("CmdUtil", "reqFloorList: " + JSON.toJSONString(Obj));
                parseFloorInfo(Obj);
                callbackImp.success(Obj);
            }

            @Override
            public void error(String errorCode, String msg) {
                callbackImp.onError(msg);
            }
        });

    }

    /**
     * [
     * {
     * 'endpoint': 1,
     * 'svcId': '103',
     * 'cmdId': '14',
     * },
     * ]
     */
    public static void reqZoneList(String gwId, CallbackImp callbackImp) {
        JSONObject jsonObject = new JSONObject();
        ArrayList<JSONObject> arrayList = new ArrayList();
        try {
            jsonObject.put("endpoint", 1);
            jsonObject.put("svcId", "103");
            jsonObject.put("cmdId", "14");
            arrayList.add(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        WlinkSDKManger.getInstance().dev_svcCmdsRequest(gwId, arrayList, new WlinkSDKManger.Callback() {
            @Override
            public void handle(Object Obj) {
                Log.i("CmdUtil", "reqZoneList: " + JSON.toJSONString(Obj));
                parseZoneInfo(Obj);
                callbackImp.success(Obj);
            }

            @Override
            public void error(String errorCode, String msg) {
                callbackImp.success(msg);

            }
        });

    }

    private static void parseFloorInfo(Object data) {
        FloorListBean bean = new Gson().fromJson(JSON.toJSONString(data), FloorListBean.class);
        if (bean != null && bean.getInfo() != null && bean.getInfo().get(0).getContent() != null) {
            List<FloorListBean.InfoBean.ContentBean> floorList = bean.getInfo().get(0).getContent();
            if (floorList != null && floorList.size() > 0) {
                Collections.sort(floorList, new Comparator<FloorListBean.InfoBean.ContentBean>() {
                    @Override
                    public int compare(FloorListBean.InfoBean.ContentBean o1, FloorListBean.InfoBean.ContentBean o2) {
                        //升序排列
                        if (Integer.parseInt(o1.getNum()) > Integer.parseInt(o2.getNum())) {
                            return 1;
                        }
                        return -1;
                    }
                });
                Global.allFloors.clear();
                Global.allFloors = floorList;
                if (floorList.size() == 3) {
                    Global.manFloorId = floorList.get(0).getFloorId();
                    Global.womanFloorId = floorList.get(1).getFloorId();
                    Global.otherFloorId = floorList.get(2).getFloorId();
                    Global.manFloorName = floorList.get(0).getName();
                    Global.womanFloorName = floorList.get(1).getName();
                    Global.otherFloorName = floorList.get(2).getName();
                } else if (floorList.size() == 2) {
                    Global.manFloorId = floorList.get(0).getFloorId();
                    Global.womanFloorId = floorList.get(1).getFloorId();
                    Global.manFloorName = floorList.get(0).getName();
                    Global.womanFloorName = floorList.get(1).getName();
                } else if (floorList.size() == 1) {
                    Global.manFloorId = floorList.get(0).getFloorId();
                    Global.manFloorName = floorList.get(0).getName();
                }

            }
        }

    }

    private static void parseZoneInfo(Object data) {
        ZoneListBean bean = new Gson().fromJson(JSON.toJSONString(data), ZoneListBean.class);
        if (bean != null && bean.getInfo() != null && bean.getInfo().get(0).getContent() != null) {
            List<ZoneListBean.InfoBean.ContentBean> zoneList = bean.getInfo().get(0).getContent();
            if (zoneList != null && zoneList.size() > 0) {
                Global.allZones.clear();
                Global.manFloorZones.clear();
                Global.womanFloorZones.clear();
                Global.otherFloorZones.clear();
                Global.allZones = zoneList;
                for (ZoneListBean.InfoBean.ContentBean zoneBean :
                        zoneList) {
                    if (zoneBean.getFloorId().equals(Global.manFloorId)) {
                        Global.manFloorZones.add(zoneBean);
                    } else if (zoneBean.getFloorId().equals(Global.womanFloorId)) {
                        Global.womanFloorZones.add(zoneBean);
                    } else if (zoneBean.getFloorId().equals(Global.otherFloorId)) {
                        Global.otherFloorZones.add(zoneBean);
                    } else if (TextUtils.isEmpty(zoneBean.getFloorId()) && TextUtils.isEmpty(Global.manFloorId)) {
                        Global.manFloorZones.add(zoneBean);
                    }
                }
                sort(Global.manFloorZones);
                sort(Global.womanFloorZones);
                sort(Global.otherFloorZones);
            }
        }
    }

    private static void parserDeviceList(Object data) {
        DeviceListBean bean = new Gson().fromJson(JSON.toJSONString(data), DeviceListBean.class);
        if (bean != null && bean.getInfo() != null && bean.getInfo().get(0).getContent() != null) {
            Global.deviceLists = bean.getInfo().get(0).getContent();
            if (bean.getInfo().get(0).getContent().size() > 0) {
                for (DeviceListBean.InfoBean.ContentBean bean1 :
                        bean.getInfo().get(0).getContent()) {
                    if (bean1.getSubDevId().equals(Global.gwId)) {
                        Global.gwName = bean1.getSubName();
                    }
                }
            }
        }
    }

    private static void sort(List<ZoneListBean.InfoBean.ContentBean> list) {
        Collections.sort(list, new Comparator<ZoneListBean.InfoBean.ContentBean>() {
            @Override
            public int compare(ZoneListBean.InfoBean.ContentBean o1, ZoneListBean.InfoBean.ContentBean o2) {
                if (Integer.parseInt(o1.getNum()) > Integer.parseInt(o2.getNum())) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public static String getBelongFloorId(String devId) {
        String result = "";
        for (DeviceListBean.InfoBean.ContentBean b :
                Global.deviceLists) {
            if (b.getSubDevId().equals(devId)) {
                String zoneId = b.getServices().get(0).getZone();
                for (ZoneListBean.InfoBean.ContentBean contentBean :
                        Global.allZones) {
                    if (contentBean.getZoneId().equals(zoneId)) {
                        String zoneName = contentBean.getName();
                        String floorId = contentBean.getFloorId();
                        result = floorId;
                    }
                }
            }
        }
        return result;
    }

    public static String getBelongZoneId(String devId) {
        String result = "";
        for (DeviceListBean.InfoBean.ContentBean b :
                Global.deviceLists) {
            if (b.getSubDevId().equals(devId)) {
                String zoneId = b.getServices().get(0).getZone();
                result = zoneId;
            }
        }
        return result;
    }


}
