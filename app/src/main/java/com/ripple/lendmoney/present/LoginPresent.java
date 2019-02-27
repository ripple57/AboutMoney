package com.ripple.lendmoney.present;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.RetrofitManager;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.BaseModel;
import com.ripple.lendmoney.model.LoginBean;
import com.ripple.lendmoney.ui.activity.LoginActivity;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/25 20:26.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class LoginPresent extends BasePresent<LoginActivity> {

    public void login(String phoneNum, String code) {
        RetrofitManager.getInstance().getApiService(URLConfig.BASE_MOVIE_URL).login(phoneNum, code)
                .compose(XApi.<LoginBean>getApiTransformer())
                .compose(XApi.<LoginBean>getScheduler())
                .compose(getV().<LoginBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().setRetryView(error);
                    }

                    @Override
                    protected void onSuccess(LoginBean loginBean) {
                        getV().setLoginSuccess();
                    }
                });
    }

    public void getCode(String phoneNum) {
        RetrofitManager.getInstance().getApiService(URLConfig.BASE_URL).getCaptcha(phoneNum)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    protected void onSuccess(BaseModel baseModel) {

                    }
                });

    }
}
