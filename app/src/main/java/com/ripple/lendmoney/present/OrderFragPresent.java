package com.ripple.lendmoney.present;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.model.OrderListBean;
import com.ripple.lendmoney.ui.fragment.OrderFragment;

import java.util.ArrayList;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/5 21:55.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class OrderFragPresent extends BasePresent<OrderFragment> {

    public void getOrderList(int pageNum) {
        ArrayList<OrderListBean> orderlist = new ArrayList<>();
        orderlist.add(new OrderListBean());
        orderlist.add(new OrderListBean());
        orderlist.add(new OrderListBean());
        orderlist.add(new OrderListBean());
        orderlist.add(new OrderListBean());
        orderlist.add(new OrderListBean());
        orderlist.add(new OrderListBean());
        getV().setOrderData(orderlist);
    }
}
