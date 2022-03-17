package com.example.st.bean;

import java.util.List;

public class FloorListBean {

    /**
     * devId : 50294D000338
     * info : [{"svcId":"103","endpoint":1,"cmdId":"50","content":[{"floorId":"1614062592891026","num":"1","name":"男厕所"},{"floorId":"1614062592933059","num":"2","name":"女厕所"}],"status":0}]
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
         * cmdId : 50
         * content : [{"floorId":"1614062592891026","num":"1","name":"男厕所"},{"floorId":"1614062592933059","num":"2","name":"女厕所"}]
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
             * floorId : 1614062592891026
             * num : 1
             * name : 男厕所
             */

            private String floorId;
            private String num;
            private String name;

            public String getFloorId() {
                return floorId;
            }

            public void setFloorId(String floorId) {
                this.floorId = floorId;
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
        }
    }
}
