package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.activity.SuggestActivity;

import java.util.HashMap;

public class SuggestPresent extends BasePresent<SuggestActivity> {
    public void commitSuggest(Activity activity, String suggest) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("feedback", suggest);
        HttpUtils.post(activity, URLConfig.recordAppFeedback, map, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().commitSuccess();
            }
        });
    }
}
