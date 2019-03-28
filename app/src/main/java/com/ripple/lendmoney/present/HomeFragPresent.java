package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.AuthenticateInfoBean;
import com.ripple.lendmoney.model.IndexBean;
import com.ripple.lendmoney.ui.fragment.HomeFragment;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/24 16:25.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class HomeFragPresent extends BasePresent<HomeFragment> {

    public void getHomeData(Activity context) {
        HttpUtils.post(context, URLConfig.index, null, new MyCallBack<IndexBean>() {
            @Override
            public void onMySuccess(IndexBean bean, MyMessage message) {
                getV().setHomeData(bean.getData());
            }
        });
    }

    public void getUserInfo(Activity activity) {
        HttpUtils.post(activity, URLConfig.getCustomerInfo, null, new MyCallBack<AuthenticateInfoBean>() {
            @Override
            public void onMySuccess(AuthenticateInfoBean bean, MyMessage message) {
                if (bean != null) {
                    GlobleParms.userInfo = bean.getData();
                }
            }
        });
    }
}
