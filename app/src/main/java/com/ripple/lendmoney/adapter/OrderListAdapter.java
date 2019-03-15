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
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean.DataBean, BaseViewHolder> {


    public OrderListAdapter(int layoutResId, @Nullable List<OrderListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.DataBean item) {
        helper.setText(R.id.tv_order_borrower, item.getBorrower())
                .setText(R.id.tv_order_lender, item.getLender())
                .setText(R.id.tv_order_interest, item.getLoanDate() + "元")
                .setText(R.id.tv_order_limit, item.getLoanAmount() + "元")
                .setText(R.id.tv_order_orderNo, item.getOrderId())
                .setText(R.id.tv_order_repay_date, Kits.Date.dayComputer(item.getCreateTime(), item.getLoanDate()))
                .setText(R.id.tv_order_term, item.getLoanDate() + "天")
                .addOnClickListener(R.id.tv_order_cancel)
                .addOnClickListener(R.id.btn_order_authenticate);

        helper.setVisible(R.id.group_state_pass, item.getPayState() != -1)
                .setVisible(R.id.group_state_nopass, item.getPayState() == -1);
    }
}
