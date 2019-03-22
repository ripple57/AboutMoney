package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.fragment.IdCardFragment;

import java.io.File;
import java.util.HashMap;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:17.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class IdCardFragPresent extends BasePresent<IdCardFragment> {
    public void uploadIdCardInfo(Activity context, String realName, String idCardNo, String wechatNumber, HashMap<Object, File> fileMap) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("realName", realName);
        map.put("idNumber", idCardNo);
        map.put("wechatNumber", wechatNumber);
        HttpUtils.upload(context, URLConfig.addIDCard, map, fileMap, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().uploadSuccess();
            }
        });
    }

}
