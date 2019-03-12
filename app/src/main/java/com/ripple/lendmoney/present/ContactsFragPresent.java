package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.fragment.ContactsFragment;

import java.util.HashMap;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:21.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class ContactsFragPresent extends BasePresent<ContactsFragment> {
    public void uploadContacts(Activity context, String jsonString) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("telephoneList", jsonString);
        HttpUtils.post(context, URLConfig.addTelephoneList, map, new MyCallBack<Void>() {

            @Override
            public void onMySuccess(Void bean, MyMessage message) {

            }
        });
        getV().uploadSuccess();
    }
}
