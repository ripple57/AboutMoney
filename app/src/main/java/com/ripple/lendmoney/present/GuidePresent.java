package com.ripple.lendmoney.present;

import android.content.Context;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.ui.activity.GuideActivity;

import java.io.File;
import java.util.HashMap;

public class GuidePresent extends BasePresent<GuideActivity> {

    public void uploadFile(Context context) {


        File file = null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionNo", 120);
        map.put("versionType", "android");
        HttpUtils.upload(context, "inter/appuser/uploadHeadIcon.do", map, file, new HttpUtils.NetCallBack() {
            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onFailed(Throwable t) {

            }
        });

    }
}
