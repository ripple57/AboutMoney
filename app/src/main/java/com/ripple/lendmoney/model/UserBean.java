package com.ripple.lendmoney.model;

public class UserBean {

    /**
     * pushOnOff : 0
     * userName : 11114004040
     * sessionId :
     * userId : 402881ef696fe54601696fecab0a0000
     */

    private int pushOnOff;
    private String userName;
    private String sessionId;
    private String userId;

    public int getPushOnOff() {
        return pushOnOff;
    }

    public void setPushOnOff(int pushOnOff) {
        this.pushOnOff = pushOnOff;
    }

    public String getUserName() {
        return userName;
    }
    public String getAnonymity() {
        return userName.replace(userName.substring(3,7),"****");
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
}
