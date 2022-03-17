package com.example.st;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wlink.bridge.AppManager;
import com.wlink.bridge.FeatureManager;
import com.wlink.bridge.GatewayService;
import com.wlink.bridge.HouseManager;
import com.wlink.bridge.ScheduleManager;
import com.wlink.bridge.ServiceManager;
import com.wlink.bridge.WlinkApp;
import com.wlink.bridge.bean.AttrReadRequestInfo;
import com.wlink.bridge.bean.AttrWriteRequestInfo;
import com.wlink.bridge.bean.CmdRequestInfo;
import com.wlink.bridge.bean.FeatureQueryInfo;
import com.wlink.bridge.bean.FeatureUpdateInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WlinkSDKManger {
    private static final String TAG = "WlinkSDKManger";

    static {
        System.setProperty("jna.debug_load", "true");
        System.setProperty("jna.debug_load.jna", "true");
        System.loadLibrary("WlinkSdk");
    }

    private WlinkApp wlinkApp;
    private AppManager appManager;
    private HouseManager houseManager;
    private GatewayService gatewayService;
    private ServiceManager svcManager;
    private FeatureManager featureManager;
    private ScheduleManager scheduleManager;

    private WlinkSDKManger() {
        WlinkApp.setAppId("103242313");
        WlinkApp.setPartnerId("oishzpee22bm5abx");
        // SDK日志由APP记录
        // WlinkApp.setLogPath(Environment.getExternalStorageDirectory().getPath() + "/homepads/sdk");
        wlinkApp = WlinkApp.getInstance();
        appManager = wlinkApp.getAppManager();
        houseManager = wlinkApp.getHouseManager();
        gatewayService = wlinkApp.getGatewayService();
        svcManager = wlinkApp.getServiceManager();
        featureManager = wlinkApp.getFeatureManager();
        scheduleManager = wlinkApp.getScheduleManager();
    }

    private static class SingletonHolder {
        private static final WlinkSDKManger manger = new WlinkSDKManger();
    }

    public interface Callback {
        void handle(Object Obj);

        void error(String errorCode, String msg);
    }

    public static WlinkSDKManger getInstance() {
        return SingletonHolder.manger;
    }

    public void gw_initMQTT(String gwId, HashMap mqttData, Callback callBack) {
        // Log.i(TAG, "gw_initMQTT: ");
        Object object = JSONObject.parse(JSON.toJSONString(mqttData));
        appManager.InitConnection(gwId, object, (var1) -> {
            // Log.i(TAG, "initMQTTConnection: " + var1);
            callBack.handle(var1);
        });
    }

    public void gw_LoginAndBindLan(String nick, String host, String gwId, String passwd, Callback callback) {
        gatewayService.LoginAndBindGatewayLan(nick, host, gwId, passwd, (var1) -> {
            callback.handle(var1);
        });
    }

    //断开mqtt连接
    public void gw_disconnectMQTT(String gwId, Callback callBack) {
        if (TextUtils.isEmpty(gwId)) {
            callBack.handle(-1);
            return;
        }
        gatewayService.LogoutGateway(gwId, (status) -> {
            callBack.handle(status);
        });
    }

    //断开mqtt lan连接
    public void gw_disconnectMQTTLan(String gwId, Callback callBack) {
        Log.i(TAG, "gw_disconnectMQTTLan: " + gwId);
        if (TextUtils.isEmpty(gwId)) {
            callBack.handle(-1);
            return;
        }
        gatewayService.LogoutGatewayLan(gwId, (status) -> {
            callBack.handle(status);
        });
    }

    //批量设备指令请求
    public void dev_svcCmdsRequest(String devId, ArrayList<JSONObject> list, Callback callback) {
        Log.i(TAG, "dev_svcCmdsRequest req--->" + "devId = " + devId + ";list = " + JSON.toJSONString(list));
        if (TextUtils.isEmpty(devId)) {
            callback.error("-1", "error");
            return;
        }
        List<CmdRequestInfo> requestInfos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = list.get(i);
            String cmdId = (String) jsonObject.get("cmdId");
            String svcId = (String) jsonObject.get("svcId");
            int endpoint = (int) jsonObject.get("endpoint");
            Object object = jsonObject.get("cmdValue");
            CmdRequestInfo requestInfo = new CmdRequestInfo();
            requestInfo.setCmdId(cmdId);
            requestInfo.setSvcId(svcId);
            requestInfo.setCmdValue(object);
            requestInfo.setEndpoint(endpoint);
            requestInfos.add(requestInfo);
            // Log.i(TAG, "dev_svcCmdsRequest--->" + "devId = " + devId + ";cmdId = " + cmdId + ";svcId = " + svcId + ",endpoint = " + endpoint);

        }
        svcManager.SvcMultipleCmdRequest(devId, requestInfos, (devID, info) -> {
            Log.i(TAG, "dev_svcCmdsRequest: " + devID);
            if (info == null) {
                callback.error("-1", "error");
                return;
            }
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("devId", devID);
            data.put("info", jsonArray);
            Log.i(TAG, "dev_svcCmdsRequest: " + data.toJSONString());
            callback.handle(data);
        });
    }

    //查询设备的一组特征
    public void dev_featureRequest(String devId, ArrayList featureArr, Callback callback) {
        if (TextUtils.isEmpty(devId)) {
            callback.error("-1", "error");
            return;
        }
        // Log.i(TAG, "dev_featureRequest: ");
        List<FeatureQueryInfo> featureQueryInfos = new ArrayList<>();
        for (int i = 0; i < featureArr.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(featureArr.get(i)));
            String featureId = (String) jsonObject.get("featureId");
            String attrId = (String) jsonObject.get("attrId");
            int endpoint = (int) jsonObject.get("endpoint");
            FeatureQueryInfo featureQueryInfo = new FeatureQueryInfo();
            featureQueryInfo.setAttrId(attrId);
            featureQueryInfo.setFeatureId(featureId);
            featureQueryInfo.setEndpoint(endpoint);
            featureQueryInfos.add(featureQueryInfo);
        }
        featureManager.FeatQueryMultipleFeature(devId, featureQueryInfos, (devID, info) -> {
            if (info == null) {
                callback.error("-1", "error");
                return;
            }
            // Log.i(TAG, "FeatQueryMultipleFeature: " + devID);
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("devId", devID);
            data.put("info", jsonArray);
            callback.handle(data);
        });

    }

    //更新设备的一组特征
    public void dev_updateFeaturesRequest(String devId, ArrayList featureArr, Callback callback) {
        if (TextUtils.isEmpty(devId)) {
            callback.error("-1", "error");
            return;
        }
        // Log.i(TAG, "dev_updateFeaturesRequest: ");
        List<FeatureUpdateInfo> featureUpdateInfos = new ArrayList<>();
        for (int i = 0; i < featureArr.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(featureArr.get(i)));
            String featureId = (String) jsonObject.get("featureId");
            String attrId = (String) jsonObject.get("attrId");
            int endpoint = (int) jsonObject.get("endpoint");
            Object attrValue = jsonObject.get("attrValue");
            FeatureUpdateInfo featureUpdateInfo = new FeatureUpdateInfo();
            featureUpdateInfo.setAttrId(attrId);
            featureUpdateInfo.setFeatureId(featureId);
            featureUpdateInfo.setEndpoint(endpoint);
            featureUpdateInfo.setAttrValue(attrValue);
            featureUpdateInfos.add(featureUpdateInfo);
        }
        featureManager.FeatUpdateMultipleFeature(devId, featureUpdateInfos, (devID, info) -> {
            if (info == null) {
                callback.error("-1", "error");
                return;
            }
            // Log.i(TAG, "FeatUpdateMultipleFeature: " + devID);
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("devId", devID);
            data.put("info", jsonArray);
            callback.handle(data);
        });
    }

    //读一组属性
    public void dev_readAttributesRequest(String devId, ArrayList featureArr, Callback callback) {
        if (TextUtils.isEmpty(devId)) {
            callback.error("-1", "error");
            return;
        }
        // Log.i(TAG, "dev_readAttributesRequest: ");
        List<AttrReadRequestInfo> attrReadRequestInfos = new ArrayList<>();
        for (int i = 0; i < featureArr.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(featureArr.get(i)));
            String svcId = (String) jsonObject.get("svcId");
            String attrId = (String) jsonObject.get("attrId");
            int endpoint = (int) jsonObject.get("endpoint");
            AttrReadRequestInfo attrReadRequestInfo = new AttrReadRequestInfo();
            attrReadRequestInfo.setAttrId(attrId);
            attrReadRequestInfo.setSvcId(svcId);
            attrReadRequestInfo.setEndpoint(endpoint);
            attrReadRequestInfos.add(attrReadRequestInfo);
        }
        svcManager.SvcReadMultipleAttribute(devId, attrReadRequestInfos, (devID, info) -> {
            if (info == null) {
                callback.error("-1", "error");
                return;
            }
            // Log.i(TAG, "SvcReadMultipleAttribute: " + devID);
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("devId", devID);
            data.put("info", jsonArray);
            callback.handle(data);
        });

    }

    //写一组属性
    public void dev_writeAttributesRequest(String devId, ArrayList featureArr, Callback callback) {
        if (TextUtils.isEmpty(devId)) {
            callback.error("-1", "error");
            return;
        }
        // Log.i(TAG, "dev_writeAttributesRequest: ");
        List<AttrWriteRequestInfo> attrWriteRequestInfos = new ArrayList<>();
        for (int i = 0; i < featureArr.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(featureArr.get(i)));
            String svcId = (String) jsonObject.get("svcId");
            String attrId = (String) jsonObject.get("attrId");
            int endpoint = (int) jsonObject.get("endpoint");
            Object attrValue = jsonObject.get("attrValue");
            AttrWriteRequestInfo attrWriteRequestInfo = new AttrWriteRequestInfo();
            attrWriteRequestInfo.setAttrId(attrId);
            attrWriteRequestInfo.setSvcId(svcId);
            attrWriteRequestInfo.setEndpoint(endpoint);
            attrWriteRequestInfo.setAttrValue(attrValue);
            attrWriteRequestInfos.add(attrWriteRequestInfo);
        }
        svcManager.SvcWriteMultipleAttrrite(devId, attrWriteRequestInfos, (devID, info) -> {
            if (info == null) {
                callback.error("-1", "error");
                return;
            }
            // Log.i(TAG, "SvcWriteMultipleAttrrite: " + devID);
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("devId", devID);
            data.put("info", jsonArray);
            callback.handle(data);
        });
    }

    //写Token标记文件
    public void write_token_file(Callback callback) {
        Process p = null;
        String result = "";
        try {
//            String cmd = "echo true | su -c tee /data/wulian/AppHasToken";
            String cmd = "su -c tee /data/wulian/AppHasToken <<< true";
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                result += error;
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                result += line;
            }
            Log.i("ls", result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();
        data.put("result", "true");
        callback.handle(data);
    }

    //统一注册回调
    public void registerCallback(Callback callback) {
        Log.i(TAG, "handle: registerCallback");
//        registerAppManagerCb(callback);
//        registerFeatureReportCb(callback);
        registerSvcAttrReportCb(callback);
    }

    //注册网关状态回调
    public void registerAppManagerCb(Callback callback) {
        //网关连接状态上报回调
        appManager.RegisterConnectEventCb((var1, var2) -> {
            // Log.i(TAG, "handle: RegisterConnectEventCb:var1-->" + var1 + ",var2--->" + var2);
            String jsType = null;
            String jsState = null;
            if (var1 == 1) {
                jsType = "WAN";
            } else {
                jsType = "LAN";
            }

            if (var2 == -1) {
                jsState = "CONNFAILED";
            } else if (var2 == 1) {
                jsState = "CONNECTED";
            } else if (var2 == 2) {
                jsState = "DISCONNECTED";
            } else if (var2 == 3) {
                jsState = "CONNERROR";
            }
            JSONObject data = new JSONObject();
            data.put("connType", jsType);
            data.put("connState", jsState);
            data.put("func", "gwConnectEvent");
            callback.handle(data);
        });

        //zigbee设备上下线回调
        appManager.RegisterAppAnnounceDevOnlineCb((devId, isOnline, type, services) -> {
            // Log.i(TAG, "dev_onlineChangeCallback:devId： " + devId + ",isOnline:" + isOnline + ",type:" + type);
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(services));
            JSONObject data = new JSONObject();
            data.put("devId", devId);
            data.put("type", type);
            data.put("isOnline", isOnline);
            data.put("services", object);
            data.put("func", "devOnlineEvent");
            callback.handle(data);
        });

        //主网关上下线回调
        appManager.RegisterAppAnnounceHouseGatewayOnlineCb((devId, isOnline, services) -> {
            JSONObject object = JSONObject.parseObject(JSON.toJSONString(services));
            JSONObject data = new JSONObject();
            data.put("devId", devId);
            data.put("isOnline", isOnline);
            data.put("services", object);
            data.put("func", "devOnlineEvent");
            callback.handle(data);
        });


        //zigbee设备删除回调
        appManager.RegisterAppAnnounceDevExitCb((devId, type) -> {
            // Log.i(TAG, "dev_deleteCallback:devId： " + devId + ",type:" + type);
            JSONObject data = new JSONObject();
            data.put("devId", devId);
            data.put("type", type);
            data.put("func", "devDeleteEvent");
            callback.handle(data);
        });

        // SDK日志上报
        appManager.RegisterSdkLogCb((string) -> {
            // Log.i(TAG, "SdkLog Report： " + string);
            callback.handle("[WLINK-SDK]:  " + string);
        });

    }

    //服务特征状态上报
    private void registerSvcAttrReportCb(Callback callback) {
        //设备属性变化上报
        svcManager.RegisterSvcAttrReportCb((devID, info) -> {
            // Log.i(TAG, "svc_attrsChangeCallback: devID=" + devID);
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("devId", devID);
            data.put("attrsInfo", jsonArray);
            data.put("func", "devAttrsReportEvent");
            callback.handle(data);
        });
    }

    //设备特征变化上报
    private void registerFeatureReportCb(Callback callback) {
        featureManager.RegisterFeatureReportCb((devId, info) -> {
            // Log.i(TAG, "registerFeatureReportCb:devId=" + devId);
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("devId", devId);
            data.put("featInfos", jsonArray);
            data.put("func", "devFeatureReportEvent");
            callback.handle(data);
        });
    }

    // 报警消息上报
    private void registerNoticeMessageCb(Callback callback) {
        gatewayService.RegisterNoticeMessageCb((info) -> {
            // Log.i(TAG, "registerNoticeMessageCb: info" + JSON.toJSONString(info));
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            JSONObject data = new JSONObject();
            data.put("msgList", jsonArray);
            data.put("func", "messageNotice");
            callback.handle(data);
        });
    }

    // 被解除授权消息上报
    private void registerNoticeMessageNewCb(Callback callback) {
        gatewayService.RegisterNoticeMessageNewCb((behavior, info) -> {
            // Log.i(TAG, "registerNoticeMessageNewCb:behavior "+behavior+"，info:" + JSON.toJSONString(info));
            JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(info));
            if (behavior == 409) {
                JSONObject data = new JSONObject();
                data.put("msgList", jsonArray);
                data.put("func", "oauthCancel");
                callback.handle(data);
            }
        });
    }


    public void readGwIdFromFile(Callback callback) {

        Log.i(TAG, "readGwIdFromFile ----");

        Process p = null;
        String gwId = "";
        String gwVer = "";
        String hasToken = "";
        try {
            p = Runtime.getRuntime().exec("su -c cat /data/wulian/gatewayid");
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                gwId += error;
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                gwId += line;
            }
            Log.i("ls", gwId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            p = Runtime.getRuntime().exec("su -c cat /data/wulian/currentVersion");
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                gwVer += error;
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                gwVer += line;
            }
            Log.i("ls", gwVer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            p = Runtime.getRuntime().exec("su -c cat /data/wulian/AppHasToken");
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                hasToken += error;
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                hasToken += line;
            }
            Log.i("ls", hasToken);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "readGwIdFromFile: gwId:" + gwId + ",versionContent:" + gwVer + ",hasToken:" + hasToken);
        WlinkApp.configAppId(gwId);
        Log.i(TAG, "configAppId: " + gwId);
        JSONObject data = new JSONObject();
        data.put("gwId", gwId);
        data.put("gwVersion", gwVer);
        data.put("hasToken", hasToken);
        data.put("func", "gwIdReportEvent");
        callback.handle(data);
    }


    public void execShellCmd(String cmd, Callback callback) {
        Process p = null;
        String result = "";
        try {
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                result += error;
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                result += line;
            }
            Log.i("ls", result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();
        data.put("result", result);
        callback.handle(data);
    }

    public void deleteLogFile() {
        Process p = null;
        String result = "";

        try {
            p = Runtime.getRuntime().exec("rm /sdcard/homepads.zip");
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = null;
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                result += error;
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                result += line;
            }
            Log.i("ls", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            p = Runtime.getRuntime().exec("rm -r sdcard/homepads/");
//            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String error = null;
//            while ((error = ie.readLine()) != null
//                    && !error.equals("null")) {
//                result += error;
//            }
//            String line = null;
//            while ((line = in.readLine()) != null
//                    && !line.equals("null")) {
//                result += line;
//            }
//            Log.i("ls", result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            p = Runtime.getRuntime().exec("su -c mkdir -m 777 /sdcard/homepads");
//            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String error = null;
//            while ((error = ie.readLine()) != null
//                    && !error.equals("null")) {
//                result += error;
//            }
//            String line = null;
//            while ((line = in.readLine()) != null
//                    && !line.equals("null")) {
//                result += line;
//            }
//            Log.i("ls", result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
