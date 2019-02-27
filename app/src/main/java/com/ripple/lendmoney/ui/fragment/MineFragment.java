package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.present.HomeFragPresent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/24 16:39.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class MineFragment   extends XLazyFragment<HomeFragPresent> implements OnRefreshListener {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_home_layout;
    }

    @Override
    public HomeFragPresent newP() {
        return new HomeFragPresent();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
