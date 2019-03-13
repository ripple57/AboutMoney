package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.fragment.BankCardFragment;

import java.util.HashMap;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:26.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class BankCardFraPresent extends BasePresent<BankCardFragment> {

    public void uploadBankCardInfo(Activity context, String bankNo, String bank, String bankPhone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("bankCardNumber", bankNo);
        map.put("openingBank", bank);
        map.put("reservePhone", bankPhone);
        HttpUtils.post(context, URLConfig.addBankCard, map, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().uploadSuccess();
            }
        });
    }
}
