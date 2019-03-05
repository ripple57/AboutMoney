package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.adapter.OrderListAdapter;
import com.ripple.lendmoney.model.OrderListBean;
import com.ripple.lendmoney.present.OrderFragPresent;
import com.ripple.lendmoney.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/24 16:38.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class OrderFragment extends XLazyFragment<OrderFragPresent> {

    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshOrder;
    private OrderListAdapter orderListAdapter;
    ArrayList<OrderListBean> orderlist = new ArrayList<>();
    private int pageNum = 1;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initListener();
        getP().getOrderList(pageNum);
    }

    private void initListener() {
        refreshOrder.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ToastUtil.showToast("加载更多");
                getP().getOrderList(pageNum++);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ToastUtil.showToast("刷新");
                pageNum = 1;
                getP().getOrderList(pageNum);

            }
        });

        orderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_order_cancel:
                        ToastUtil.showToast("点击了第" + position + "个位置的取消订单按钮;");
                        break;
                    case R.id.btn_order_authenticate:
                        ToastUtil.showToast("点击了第" + position + "个位置的去认证按钮;");
                        break;
                }
            }
        });
    }

    private void initView() {
        rvOrderList.setLayoutManager(new LinearLayoutManager(context));
        orderListAdapter = new OrderListAdapter(R.layout.item_order_list_layout, orderlist);
        rvOrderList.setAdapter(orderListAdapter);


    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_order_layout;
    }

    @Override
    public OrderFragPresent newP() {
        return new OrderFragPresent();
    }


    public void setOrderData(ArrayList<OrderListBean> list) {
        refreshOrder.finishLoadMore();
        refreshOrder.finishRefresh();
        if (pageNum == 1) {
            orderlist.clear();
        }
        orderlist.addAll(list);
        orderListAdapter.notifyDataSetChanged();
    }
}
