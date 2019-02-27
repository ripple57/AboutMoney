package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.present.HomeFragPresent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * Created by admin on 2018/4/12.
 */

public class HomeFragment extends XLazyFragment<HomeFragPresent> implements OnRefreshListener {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView1)
    TextView textView1;

    @OnClick({R.id.textView,R.id.textView1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView:
                getP().test(context);
                break;
            case R.id.textView1:
                getP().test1();
                break;
        }
    }

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
