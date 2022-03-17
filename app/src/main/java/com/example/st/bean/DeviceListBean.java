package com.example.st.bean;

import java.util.List;

public class DeviceListBean {
    /**
     * devId : 50294D000338
     * info : [{"svcId":"103","endpoint":1,"cmdId":"3","content":[{"owner":"50294D000338","rssi":"","addTime":1614051962,"subName":"HomePad S 全景面板","icon":"2020112018065979359.png","subDevId":"50294D000338","subType":"gw2021","state":"1","services":[{"svcId":"103","zone":"","svcIcon":"FqeE1pwywMDaHqqlgEokYNicdrYV","ep":1,"svcName":"ZigBee 网关"},{"svcId":"57","zone":"","svcIcon":"2020091119314843148.png","ep":2,"svcName":"智慧屏"},{"svcId":"1","zone":"","svcIcon":"FlfHknjb6l9yzLEyESq95vFO-t5L","ep":3,"svcName":"开关"},{"svcId":"1","zone":"","svcIcon":"FlfHknjb6l9yzLEyESq95vFO-t5L","ep":4,"svcName":"开关02"}],"category":2},{"owner":"50294D000338","rssi":"-64","addTime":1614062478,"subName":"门窗磁感应器01","icon":"Ft2Rb0DUSL8tyRNy43Za4ud6WVnn","subDevId":"78123B04004B1200","subType":"03","state":"1","services":[{"svcId":"7","zone":"1614062659392874","svcIcon":"Ftt5oorDXVhWrQChf0NeqGclrnFK","ep":1,"svcName":"门磁"}],"category":8},{"owner":"50294D000338","rssi":"-44","addTime":1614071989,"subName":"紧急按钮01","icon":"FnN2yaC578QSq6yR8K_10ijDfW9D","subDevId":"9256140B004B1200","subType":"04","state":"1","services":[{"svcId":"23","zone":"1614062626010903","svcIcon":"FpfgQBjV65UVN8IjaKGBibNHoOVR","ep":1,"svcName":"紧急按钮"}],"category":8}],"status":0}]
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
         * cmdId : 3
         * content : [{"owner":"50294D000338","rssi":"","addTime":1614051962,"subName":"HomePad S 全景面板","icon":"2020112018065979359.png","subDevId":"50294D000338","subType":"gw2021","state":"1","services":[{"svcId":"103","zone":"","svcIcon":"FqeE1pwywMDaHqqlgEokYNicdrYV","ep":1,"svcName":"ZigBee 网关"},{"svcId":"57","zone":"","svcIcon":"2020091119314843148.png","ep":2,"svcName":"智慧屏"},{"svcId":"1","zone":"","svcIcon":"FlfHknjb6l9yzLEyESq95vFO-t5L","ep":3,"svcName":"开关"},{"svcId":"1","zone":"","svcIcon":"FlfHknjb6l9yzLEyESq95vFO-t5L","ep":4,"svcName":"开关02"}],"category":2},{"owner":"50294D000338","rssi":"-64","addTime":1614062478,"subName":"门窗磁感应器01","icon":"Ft2Rb0DUSL8tyRNy43Za4ud6WVnn","subDevId":"78123B04004B1200","subType":"03","state":"1","services":[{"svcId":"7","zone":"1614062659392874","svcIcon":"Ftt5oorDXVhWrQChf0NeqGclrnFK","ep":1,"svcName":"门磁"}],"category":8},{"owner":"50294D000338","rssi":"-44","addTime":1614071989,"subName":"紧急按钮01","icon":"FnN2yaC578QSq6yR8K_10ijDfW9D","subDevId":"9256140B004B1200","subType":"04","state":"1","services":[{"svcId":"23","zone":"1614062626010903","svcIcon":"FpfgQBjV65UVN8IjaKGBibNHoOVR","ep":1,"svcName":"紧急按钮"}],"category":8}]
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
             * owner : 50294D000338
             * rssi :
             * addTime : 1614051962
             * subName : HomePad S 全景面板
             * icon : 2020112018065979359.png
             * subDevId : 50294D000338
             * subType : gw2021
             * state : 1
             * services : [{"svcId":"103","zone":"","svcIcon":"FqeE1pwywMDaHqqlgEokYNicdrYV","ep":1,"svcName":"ZigBee 网关"},{"svcId":"57","zone":"","svcIcon":"2020091119314843148.png","ep":2,"svcName":"智慧屏"},{"svcId":"1","zone":"","svcIcon":"FlfHknjb6l9yzLEyESq95vFO-t5L","ep":3,"svcName":"开关"},{"svcId":"1","zone":"","svcIcon":"FlfHknjb6l9yzLEyESq95vFO-t5L","ep":4,"svcName":"开关02"}]
             * category : 2
             */

            private String owner;
            private String rssi;
            private int addTime;
            private String subName;
            private String icon;
            private String subDevId;
            private String subType;
            private String state;
            private int category;
            private List<ServicesBean> services;

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getRssi() {
                return rssi;
            }

            public void setRssi(String rssi) {
                this.rssi = rssi;
            }

            public int getAddTime() {
                return addTime;
            }

            public void setAddTime(int addTime) {
                this.addTime = addTime;
            }

            public String getSubName() {
                return subName;
            }

            public void setSubName(String subName) {
                this.subName = subName;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getSubDevId() {
                return subDevId;
            }

            public void setSubDevId(String subDevId) {
                this.subDevId = subDevId;
            }

            public String getSubType() {
                return subType;
            }

            public void setSubType(String subType) {
                this.subType = subType;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public List<ServicesBean> getServices() {
                return services;
            }

            public void setServices(List<ServicesBean> services) {
                this.services = services;
            }

            public static class ServicesBean {
                /**
                 * svcId : 103
                 * zone :
                 * svcIcon : FqeE1pwywMDaHqqlgEokYNicdrYV
                 * ep : 1
                 * svcName : ZigBee 网关
                 */

                private String svcId;
                private String zone;
                private String svcIcon;
                private int ep;
                private String svcName;

                public String getSvcId() {
                    return svcId;
                }

                public void setSvcId(String svcId) {
                    this.svcId = svcId;
                }

                public String getZone() {
                    return zone;
                }

                public void setZone(String zone) {
                    this.zone = zone;
                }

                public String getSvcIcon() {
                    return svcIcon;
                }

                public void setSvcIcon(String svcIcon) {
                    this.svcIcon = svcIcon;
                }

                public int getEp() {
                    return ep;
                }

                public void setEp(int ep) {
                    this.ep = ep;
                }

                public String getSvcName() {
                    return svcName;
                }

                public void setSvcName(String svcName) {
                    this.svcName = svcName;
                }
            }
        }
    }
}
