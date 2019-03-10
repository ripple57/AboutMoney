package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.layout.QMUIButton;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.present.HomeFragPresent;
import com.ripple.lendmoney.ui.activity.FaceRecognitionActivity;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by admin on 2018/4/12.
 */

public class HomeFragment extends BaseLazyFragment<HomeFragPresent> {
    @BindView(R.id.topbanner_homefrag)
    Banner topBanner;
    @BindView(R.id.tv_homefrag_username)
    TextView tvUsername;
    @BindView(R.id.tv_homefrag_day)
    TextView tvDay;
    @BindView(R.id.tv_homefrag_scroll)
    TextView tvScroll;
    @BindView(R.id.btn_homefrag_lend)
    QMUIButton btnLend;
    @BindView(R.id.tv_item_walletMoney)
    TextView tvWalletMoney;
    @BindView(R.id.tv_item_remainMoney)
    TextView tvRemainMoney;


    @OnClick({R.id.btn_homefrag_lend})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_homefrag_lend:
                FaceRecognitionActivity.launch(context);
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
