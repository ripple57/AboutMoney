package com.ripple.lendmoney.present;

import android.content.Context;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.UserBean;
import com.ripple.lendmoney.ui.activity.LoginActivity;
import com.ripple.lendmoney.utils.PhoneInfoUtil;

import java.util.HashMap;

import cn.droidlover.xdroidmvp.net.NetError;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/25 20:26.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class LoginPresent extends BasePresent<LoginActivity> {

    public void login(Context context, String phoneNum, String code, String lat, String lon) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", phoneNum);
        map.put("valiCode", code);
        map.put("lon", lon);
        map.put("lat", lat);
        map.put("location", "");
        map.put("sessionId", PhoneInfoUtil.getSessionId(context, phoneNum));
        HttpUtils.post(context, URLConfig.appLogin, map, new MyCallBack<UserBean>() {
            @Override
            public void onMySuccess(UserBean bean, MyMessage message) {
                getV().loginSuccess(bean.getData());
            }
        });

    }

    public void getCode(Context context, String phoneNum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", phoneNum);
        HttpUtils.post(context, URLConfig.sendCodeForLogin, map, new MyCallBack<Void>() {
            @Override
            public void onMySuccess(Void bean, MyMessage message) {
                getV().sendCodeSuccess();
            }

            @Override
            public void onMyFailure(MyMessage message) {
                super.onMyFailure(message);
                getV().sendCodeFailed();
            }

            @Override
            public void onError(NetError error) {
                getV().sendCodeFailed();
            }
        });
    }
}
