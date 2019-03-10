package com.ripple.lendmoney.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.model.OrderListBean;

import java.util.List;

import cn.droidlover.xdroidmvp.kit.Kits;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/5 22:00.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean, BaseViewHolder> {


    public OrderListAdapter(int layoutResId, @Nullable List<OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean item) {
        helper.setText(R.id.tv_order_borrower, "迪肯").setText(R.id.tv_order_lender, "波波")
                .setText(R.id.tv_order_interest, "52.22元").setText(R.id.tv_order_limit, "50000元")
                .setText(R.id.tv_order_orderNo, "152115521286536326").setText(R.id.tv_order_repay_date, "2019-12-28")
                .setText(R.id.tv_order_term, "50天")
                .addOnClickListener(R.id.tv_order_cancel).addOnClickListener(R.id.btn_order_authenticate);

        int random = Kits.Random.getRandom(2);
        helper.setVisible(R.id.group_state_pass, random == 0).setVisible(R.id.group_state_nopass, random != 0);
    }
}
