package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.AuthenticateInfoBean;
import com.ripple.lendmoney.ui.activity.AuthenticateInfoActivity;

public class AuthenticateInfoPresent extends BasePresent<AuthenticateInfoActivity> {
    public void getUserInfo(Activity activity) {
        HttpUtils.post(activity, URLConfig.getCustomerInfo, null, new MyCallBack<AuthenticateInfoBean>() {
            @Override
            public void onMySuccess(AuthenticateInfoBean bean, MyMessage message) {
                if (bean != null) {
                    GlobleParms.userInfo = bean.getData();
                    getV().setAuthenticateInfoDate(bean.getData());
                }

            }
        });
    }
}
