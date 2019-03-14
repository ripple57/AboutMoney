package com.ripple.lendmoney.model;

public class UserBean {


    /**
     * data : {"userName":"18000000000","sessionId":"4E8F9F3D088E968EC1FDF093FE3785DB","userId":"402881ef6975e089016975f3bf270000"}
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
         * userName : 18000000000
         * sessionId : 4E8F9F3D088E968EC1FDF093FE3785DB
         * userId : 402881ef6975e089016975f3bf270000
         */

        private String userName;
        private String sessionId;
        private String userId;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getAnonymity() {
            return  userName.substring(0, 3) + "****" + userName.substring(7);
        }

    }
}
