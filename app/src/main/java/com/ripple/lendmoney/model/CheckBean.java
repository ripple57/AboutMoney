package com.ripple.lendmoney.model;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/3 1:51.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class CheckBean {

    /**
     * data : {"requeUpdate":0,"versionNo":""}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
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
