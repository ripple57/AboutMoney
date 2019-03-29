package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.event.MonitorOrderEvent;
import com.ripple.lendmoney.event.NewOrderEvent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.activity.MainActivity;
import com.ripple.lendmoney.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.event.BusFactory;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/2 10:12.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class MainPresent extends BasePresent<MainActivity> {
    public void monitorOrder(Activity activity, String iouID) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("IOUID", iouID);
        HttpUtils.post(activity, URLConfig.monitorOrder, map, new MyCallBack<Map<String, String>>() {
            @Override
            public void onMySuccess(Map<String, String> bean, MyMessage message) {
                String payState = bean.get("payState");
                if ("false".equals(payState)) {
                    BusFactory.getBus().post(new MonitorOrderEvent(iouID,5000));
                } else if ("true".equals(payState)) {
                    SPUtils.getInstance(activity).remove(Constant.IOUID);
                    BusFactory.getBus().post(new NewOrderEvent());
                }
            }

        });
    }
}
