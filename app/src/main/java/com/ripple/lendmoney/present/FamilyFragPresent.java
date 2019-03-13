package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.fragment.FamilyFragment;

import java.util.HashMap;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:28.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class FamilyFragPresent extends BasePresent<FamilyFragment> {

    public void uploadFamilyInfo(Activity context, String directRelation, String directName, String directPhone, String emergencyRelation, String emergencyName, String emergencyPhone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("directKinship", directRelation);
        map.put("kinshipName", directName);
        map.put("kinshipPhone", directPhone);
        map.put("urgentContact", emergencyRelation);
        map.put("contactName", emergencyName);
        map.put("contactPhone", emergencyPhone);

        HttpUtils.post(context, URLConfig.addContact, map, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().uploadSuccess();
            }
        });
    }
}
