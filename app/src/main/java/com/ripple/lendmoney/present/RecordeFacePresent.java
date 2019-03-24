package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.activity.RecordeFaceActivity;

import java.io.File;

import cn.droidlover.xdroidmvp.net.NetError;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/24 12:38.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class RecordeFacePresent extends BasePresent<RecordeFaceActivity> {
    public void upLoadVideo(Activity activity, File file) {
        HttpUtils.upload(activity, URLConfig.addFaceVideo, null, file, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().uploadVideoSuccess();
            }

            @Override
            public void onError(NetError error) {
                super.onError(error);
                getV().uploadVideoFail();
            }

        });

    }
}
