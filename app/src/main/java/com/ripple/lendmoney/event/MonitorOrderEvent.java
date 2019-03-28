package com.ripple.lendmoney.event;

import cn.droidlover.xdroidmvp.event.IBus;

public class MonitorOrderEvent implements IBus.IEvent {
    public MonitorOrderEvent(String iouID, int delayMillis) {
        this.iouID = iouID;
        this.delayMillis = delayMillis;
    }

    public String getIouID() {
        return iouID;
    }

    private final String iouID;

    public int getDelayMillis() {
        return delayMillis;
    }

    private final int delayMillis;


    @Override
    public int getTag() {
        return 0;
    }
}
