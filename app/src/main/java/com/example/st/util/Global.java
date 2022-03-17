package com.example.st.util;

import com.example.st.bean.DeviceListBean;
import com.example.st.bean.FloorListBean;
import com.example.st.bean.ZoneListBean;

import java.util.ArrayList;
import java.util.List;

public class Global {
    public static String manFloorId, womanFloorId, otherFloorId;
    public static String manFloorName, womanFloorName, otherFloorName;
    public static String gwId,gwName;

    public static List<FloorListBean.InfoBean.ContentBean> allFloors = new ArrayList<>();
    public static List<ZoneListBean.InfoBean.ContentBean> allZones = new ArrayList<>();
    public static List<ZoneListBean.InfoBean.ContentBean> manFloorZones = new ArrayList<>();
    public static List<ZoneListBean.InfoBean.ContentBean> womanFloorZones = new ArrayList<>();
    public static List<ZoneListBean.InfoBean.ContentBean> otherFloorZones = new ArrayList<>();

    public static List<DeviceListBean.InfoBean.ContentBean> deviceLists = new ArrayList<>();



}
