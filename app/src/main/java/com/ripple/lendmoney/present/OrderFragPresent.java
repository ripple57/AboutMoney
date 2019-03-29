package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.OrderListBean;
import com.ripple.lendmoney.ui.fragment.OrderFragment;

import java.util.HashMap;

import cn.droidlover.xdroidmvp.net.NetError;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/5 21:55.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class OrderFragPresent extends BasePresent<OrderFragment> {

    public void getOrderList(Activity context, int pageNum) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        HttpUtils.post(context, URLConfig.getIOUInfoList, map, new MyCallBack<OrderListBean>() {
            @Override
            public void onMySuccess(OrderListBean bean, MyMessage message) {
                getV().hideLoading();

                getV().setNoDataView(bean.getData().size()==0);
                getV().setOrderData(bean.getData());
            }

            @Override
            public void onError(NetError error) {
                super.onError(error);
                getV().setRetryView(error);
            }
        });
    }
}
