package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.present.HomeFragPresent;
import com.ripple.lendmoney.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/24 16:39.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class MineFragment extends XLazyFragment<HomeFragPresent> implements OnRefreshListener {

    @BindView(R.id.iv_minefrag_headIcon)
    QMUIRadiusImageView ivMinefragHeadIcon;
    @BindView(R.id.tv_minefrag_username)
    TextView tvMinefragUsername;
    @BindView(R.id.tv_item_walletMoney)
    TextView tvItemWalletMoney;
    @BindView(R.id.tv_item_remainMoney)
    TextView tvItemRemainMoney;
    @BindView(R.id.ll_minefrag_myInfo)
    LinearLayout llMinefragMyInfo;
    @BindView(R.id.ll_minefrag_suggest)
    LinearLayout llMinefragSuggest;
    @BindView(R.id.ll_minefrag_aboutUs)
    LinearLayout llMinefragAboutUs;
    @BindView(R.id.ll_minefrag_contactUs)
    LinearLayout llMinefragContactUs;
    @BindView(R.id.ll_minefrag_quit)
    LinearLayout llMinefragQuit;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_mine_layout;
    }

    @Override
    public HomeFragPresent newP() {
        return new HomeFragPresent();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }


    @OnClick({R.id.iv_minefrag_headIcon, R.id.ll_minefrag_myInfo, R.id.ll_minefrag_suggest, R.id.ll_minefrag_aboutUs,
            R.id.ll_minefrag_contactUs, R.id.ll_minefrag_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_minefrag_headIcon:
                ToastUtil.showToast("点击了头像");
                break;
            case R.id.ll_minefrag_myInfo:
                ToastUtil.showToast("点击了我的资料");
                break;
            case R.id.ll_minefrag_suggest:
                ToastUtil.showToast("点击了建议反馈");
                break;
            case R.id.ll_minefrag_aboutUs:
                ToastUtil.showToast("点击了关于我们");
                break;
            case R.id.ll_minefrag_contactUs:
                ToastUtil.showToast("点击了客服服务");
                break;
            case R.id.ll_minefrag_quit:
                ToastUtil.showToast("点击了退出登录");
                break;
        }
    }
}
