package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.RetrofitManager;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.IndexBean;
import com.ripple.lendmoney.model.TestBean;
import com.ripple.lendmoney.ui.fragment.HomeFragment;
import com.ripple.lendmoney.utils.LogUtils;

import java.util.HashMap;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/24 16:25.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class HomeFragPresent extends BasePresent<HomeFragment> {

    public void test1() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionNo", 120);
        map.put("versionType", "android");
        RetrofitManager.getInstance().getApiService(URLConfig.BASE_URL).test1(map)
                .compose(XApi.<TestBean>getApiTransformer())
                .compose(XApi.<TestBean>getScheduler())
                .compose(getV().<TestBean>bindToLifecycle())
                .subscribe(new ApiSubscriber<TestBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        LogUtils.e("网络请求失败----------");
                    }

                    @Override
                    protected void onSuccess(TestBean bean) {
                        LogUtils.e("网络请求成功" + bean.getState());
                        //                        getV().refreshHome.finishRefresh();
                    }
                });
    }

    public void getHomeData(Activity context) {
        HttpUtils.post(context, URLConfig.index, null, new MyCallBack<IndexBean>() {
            @Override
            public void onMySuccess(IndexBean bean, MyMessage message) {
                getV().setHomeData(bean);
            }
        });
    }
}
