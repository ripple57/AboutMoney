package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.adapter.OrderListAdapter;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.event.RefreshOrderEvent;
import com.ripple.lendmoney.model.OrderListBean;
import com.ripple.lendmoney.present.OrderFragPresent;
import com.ripple.lendmoney.ui.activity.AssessActivity;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/24 16:38.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class OrderFragment extends BaseLazyFragment<OrderFragPresent> {

    @BindView(R.id.no_data_layout)
    RelativeLayout no_data_layout;
    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;
    @BindView(R.id.refresh_order)
    SmartRefreshLayout refreshOrder;
    private OrderListAdapter orderListAdapter;
    ArrayList<OrderListBean.DataBean> orderlist = new ArrayList<>();
    private int pageNum = 1;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initListener();
    }

    private void initListener() {
        refreshOrder.setEnableLoadMore(false);
        refreshOrder.setEnableRefresh(true);
        refreshOrder.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getP().getOrderList(context, pageNum++);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getNetData();

            }
        });

        orderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_order_cancel:
                        new QMUIDialog.MessageDialogBuilder(context).setMessage("您确定要删除该笔订单吗?")
                                .addAction("取消", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                    }
                                })
                                .addAction("确定", new QMUIDialogAction.ActionListener() {
                                    @Override
                                    public void onClick(QMUIDialog dialog, int index) {
                                        dialog.dismiss();
                                        orderlist.remove(position);
                                        orderListAdapter.notifyDataSetChanged();
                                        ToastUtil.showToast("订单已删除");
                                    }
                                }).show();


                        break;
                    case R.id.btn_order_authenticate:
                        AssessActivity.launch(context, orderlist.get(position).getAppIOUInfoid());
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

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RefreshOrderEvent event) {
        getNetData();
    }

    public void setOrderData(List<OrderListBean.DataBean> list) {
        refreshOrder.finishLoadMore();
        refreshOrder.finishRefresh();
        if (pageNum == 1) {
            orderlist.clear();
        }
        orderlist.addAll(list);
        orderListAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        LogUtils.e("onResumeLazy====");
        getNetData();
    }
    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("onresume====");
    }

    @Override
    public void getNetData() {
        LogUtils.e("订单页求情网络一次");
        getP().getOrderList(context, pageNum);
    }

    public void setNoDataView(boolean nodate) {
        no_data_layout.setVisibility(nodate ? View.VISIBLE : View.GONE);
    }
}
