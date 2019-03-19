package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.activity.RecordeVideoActivity;

import java.io.File;

import cn.droidlover.xdroidmvp.net.NetError;

public class RecordeVideoPresent extends BasePresent<RecordeVideoActivity> {
    public void upLoadVideo(Activity activity, String result) {
        File file = new File(result);
        if (!file.exists()) {

            return;
        }
        HttpUtils.uploadWithoutDialog(activity, URLConfig.addFaceVideo, null, file, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().uploadVideoSuccess();
            }

            @Override
            public void onError(NetError error) {
                super.onError(error);
            }

        });

    }
}
