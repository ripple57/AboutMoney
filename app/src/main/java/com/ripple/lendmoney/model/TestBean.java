package com.ripple.lendmoney.model;


public class TestBean extends BaseModel {


    /**
     * body : {"data":{"requeUpdate":0,"versionNo":""}}
     */

    private BodyBean body;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * data : {"requeUpdate":0,"versionNo":""}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * requeUpdate : 0
             * versionNo :
             */

            private int requeUpdate;
            private String versionNo;

            public int getRequeUpdate() {
                return requeUpdate;
            }

            public void setRequeUpdate(int requeUpdate) {
                this.requeUpdate = requeUpdate;
            }

            public String getVersionNo() {
                return versionNo;
            }

            public void setVersionNo(String versionNo) {
                this.versionNo = versionNo;
            }
        }
    }
}
