package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.present.AssessPresent;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class AssessActivity extends BaseActivity<AssessPresent> {

    @BindView(R.id.iv_assessAct_banner)
    ImageView ivAssessActBanner;
    @BindView(R.id.iv_assessAct_checkAgreement)
    ImageView ivAssessActCheckAgreement;
    @BindView(R.id.tv_assessAct_agreement)
    TextView tvAssessActAgreement;
    @BindView(R.id.tv_assessAct_feeIntroduce)
    TextView tvAssessActFeeIntroduce;
    @BindView(R.id.tv_assessAct_nowPrice)
    TextView tvAssessActNowPrice;
    @BindView(R.id.tv_assessAct_prePrice)
    TextView tvAssessActPrePrice;
    @BindView(R.id.btn_assessAct_applyLend)
    Button btnAssessActApplyLend;
    @BindString(R.string.renzhengfeiyong_introduce)
    String introduce;

    public static void launch(Activity activity) {
        Router.newIntent(activity).to(AssessActivity.class).launch();
    }

    @Override
    protected String topBarTitle() {
        return "智能评估";
    }

    @Override
    protected boolean topBarIsTransparent() {
        return true;
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvAssessActFeeIntroduce.setText(introduce);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_assess;
    }

    @Override
    public AssessPresent newP() {
        return new AssessPresent();
    }


    @OnClick({R.id.iv_assessAct_checkAgreement, R.id.tv_assessAct_agreement, R.id.btn_assessAct_applyLend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_assessAct_checkAgreement:
                ToastUtil.showToast("申请借款");
                break;
            case R.id.tv_assessAct_agreement:
                WebActivity.launch(this, URLConfig.REGIST_AGREEMENT, "注册服务协议");
                break;
            case R.id.btn_assessAct_applyLend:
                ToastUtil.showToast("申请借款");
                break;
        }
    }

}
