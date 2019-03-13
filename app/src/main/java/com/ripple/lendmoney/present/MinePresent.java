package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.fragment.MineFragment;

import java.util.HashMap;

public class MinePresent extends BasePresent<MineFragment> {

    public void userExit(Activity context, String userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        HttpUtils.post(context, URLConfig.appUserExit, map, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().exitSuccess();
            }
        });
    }
}
