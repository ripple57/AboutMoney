package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.layout.QMUIButton;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.present.HomeFragPresent;
import com.ripple.lendmoney.ui.activity.GuideActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/4/12.
 */

public class HomeFragment extends BaseLazyFragment<HomeFragPresent> {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.btn_home_frag_lend)
    QMUIButton btn_home_frag_lend;

    @OnClick({R.id.textView, R.id.textView1,R.id.btn_home_frag_lend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView:
                break;
            case R.id.textView1:
                GuideActivity.launch(context);
                break;
            case R.id.btn_home_frag_lend:
                GuideActivity.launch(context);
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
    public void getNetData() {

    }
}
