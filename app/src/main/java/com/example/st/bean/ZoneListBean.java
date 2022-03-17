package com.example.st.bean;

import java.util.List;

public class ZoneListBean {
    /**
     * devId : 50294D000338
     * info : [{"svcId":"103","endpoint":1,"cmdId":"14","content":[{"floorId":"1614062592933059","favorites":"0","fnum":"","recommWakeup":"","num":"600","name":"坑3","icon":"1","zoneId":"1614062675164488","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592933059","favorites":"0","fnum":"","recommWakeup":"","num":"500","name":"坑2","icon":"1","zoneId":"1614062667133371","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592891026","favorites":"0","fnum":"","recommWakeup":"","num":"300","name":"坑位3","icon":"1","zoneId":"1614062649356144","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592933059","favorites":"0","fnum":"","recommWakeup":"","num":"400","name":"坑1","icon":"1","zoneId":"1614062659392874","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592891026","favorites":"0","fnum":"","recommWakeup":"","num":"200","name":"坑位2","icon":"1","zoneId":"1614062641271322","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592891026","favorites":"0","fnum":"","recommWakeup":"","num":"100","name":"坑位1","icon":"1","zoneId":"1614062626010903","masterZone":"","personal":"0","isSub":"","recommSleep":""}],"status":0}]
     */

    private String devId;
    private List<InfoBean> info;

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * svcId : 103
         * endpoint : 1
         * cmdId : 14
         * content : [{"floorId":"1614062592933059","favorites":"0","fnum":"","recommWakeup":"","num":"600","name":"坑3","icon":"1","zoneId":"1614062675164488","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592933059","favorites":"0","fnum":"","recommWakeup":"","num":"500","name":"坑2","icon":"1","zoneId":"1614062667133371","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592891026","favorites":"0","fnum":"","recommWakeup":"","num":"300","name":"坑位3","icon":"1","zoneId":"1614062649356144","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592933059","favorites":"0","fnum":"","recommWakeup":"","num":"400","name":"坑1","icon":"1","zoneId":"1614062659392874","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592891026","favorites":"0","fnum":"","recommWakeup":"","num":"200","name":"坑位2","icon":"1","zoneId":"1614062641271322","masterZone":"","personal":"0","isSub":"","recommSleep":""},{"floorId":"1614062592891026","favorites":"0","fnum":"","recommWakeup":"","num":"100","name":"坑位1","icon":"1","zoneId":"1614062626010903","masterZone":"","personal":"0","isSub":"","recommSleep":""}]
         * status : 0
         */

        private String svcId;
        private int endpoint;
        private String cmdId;
        private int status;
        private List<ContentBean> content;

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

        public String getCmdId() {
            return cmdId;
        }

        public void setCmdId(String cmdId) {
            this.cmdId = cmdId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * floorId : 1614062592933059
             * favorites : 0
             * fnum :
             * recommWakeup :
             * num : 600
             * name : 坑3
             * icon : 1
             * zoneId : 1614062675164488
             * masterZone :
             * personal : 0
             * isSub :
             * recommSleep :
             */

            private String floorId;
            private String favorites;
            private String fnum;
            private String recommWakeup;
            private String num;
            private String name;
            private String icon;
            private String zoneId;
            private String masterZone;
            private String personal;
            private String isSub;
            private String recommSleep;

            public String getFloorId() {
                return floorId;
            }

            public void setFloorId(String floorId) {
                this.floorId = floorId;
            }

            public String getFavorites() {
                return favorites;
            }

            public void setFavorites(String favorites) {
                this.favorites = favorites;
            }

            public String getFnum() {
                return fnum;
            }

            public void setFnum(String fnum) {
                this.fnum = fnum;
            }

            public String getRecommWakeup() {
                return recommWakeup;
            }

            public void setRecommWakeup(String recommWakeup) {
                this.recommWakeup = recommWakeup;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getZoneId() {
                return zoneId;
            }

            public void setZoneId(String zoneId) {
                this.zoneId = zoneId;
            }

            public String getMasterZone() {
                return masterZone;
            }

            public void setMasterZone(String masterZone) {
                this.masterZone = masterZone;
            }

            public String getPersonal() {
                return personal;
            }

            public void setPersonal(String personal) {
                this.personal = personal;
            }

            public String getIsSub() {
                return isSub;
            }

            public void setIsSub(String isSub) {
                this.isSub = isSub;
            }

            public String getRecommSleep() {
                return recommSleep;
            }

            public void setRecommSleep(String recommSleep) {
                this.recommSleep = recommSleep;
            }
        }
    }
}
