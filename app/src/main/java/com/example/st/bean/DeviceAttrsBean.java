package com.example.st.bean;

import java.util.List;

public class DeviceAttrsBean {
    /**
     * devId : 9256140B004B1200
     * func : devAttrsReportEvent
     * attrsInfo : [{"svcId":"23","endpoint":1,"attrId":"1","attrValue":"1"}]
     */

    private String devId;
    private String func;
    private List<AttrsInfoBean> attrsInfo;

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public List<AttrsInfoBean> getAttrsInfo() {
        return attrsInfo;
    }

    public void setAttrsInfo(List<AttrsInfoBean> attrsInfo) {
        this.attrsInfo = attrsInfo;
    }

    public static class AttrsInfoBean {
        /**
         * svcId : 23
         * endpoint : 1
         * attrId : 1
         * attrValue : 1
         */

        private String svcId;
        private int endpoint;
        private String attrId;
        private String attrValue;

        public String getSvcId() {
            return svcId;
        }

        public void setSvcId(String svcId) {
            this.svcId = svcId;
        }

        public int getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(int endpoint) {
            this.endpoint = endpoint;
        }

        public String getAttrId() {
            return attrId;
        }

        public void setAttrId(String attrId) {
            this.attrId = attrId;
        }

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }
    }
}
