package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.fragment.CreditFragment;

import java.io.File;
import java.util.HashMap;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:27.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class CreditFragPresent extends BasePresent<CreditFragment> {

    public void uploadCreditInfo(Activity context, HashMap<Object, File> filesMap) {
        HttpUtils.upload(context, URLConfig.addAlipayCreditImg, null, filesMap, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().uploadSuccess();
            }
        });
    }
}
