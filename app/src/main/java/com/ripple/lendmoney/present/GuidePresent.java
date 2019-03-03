package com.ripple.lendmoney.present;

import android.app.Activity;
import android.content.Context;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.model.TestBean;
import com.ripple.lendmoney.ui.activity.GuideActivity;

import java.io.File;
import java.util.HashMap;

public class GuidePresent extends BasePresent<GuideActivity> {

    public void uploadFile(Context context) {


        File file = null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionNo", 120);
        map.put("versionType", "android");
        HttpUtils.upload(context, "inter/appuser/uploadHeadIcon.do", map, file, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {

            }
        });

    }

    public void test(Activity context) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionNo", 120);
        map.put("versionType", "android");
        HttpUtils.getDialog(context, "inter/index/checkVersion.do", map, new MyCallBack<TestBean>() {
            @Override
            public void onMySuccess(TestBean bean, MyMessage message) {

            }

            @Override
            public void onMyFailure(MyMessage message) {
                super.onMyFailure(message);

            }
        });
    }
}
