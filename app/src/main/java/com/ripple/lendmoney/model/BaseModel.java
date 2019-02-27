package com.ripple.lendmoney.model;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by admin on 2018/4/23.
 */

public class BaseModel implements IModel {

    /**
     * body : {"data":{"requeUpdate":0,"versionNo":""}}
     * des : 成功
     * state : 0
     */

    public String des;
    public int state;

    //    {
//        body: {
//            data: {
//                requeUpdate: 0,
//                        versionNo: ""
//            }
//        },
//        des: "成功",
//                state: 0
//    }
    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
