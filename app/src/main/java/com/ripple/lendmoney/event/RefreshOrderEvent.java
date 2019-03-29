package com.ripple.lendmoney.event;

import cn.droidlover.xdroidmvp.event.IBus;

public class RefreshOrderEvent implements IBus.IEvent {
    @Override
    public int getTag() {
        return 0;
    }
}
