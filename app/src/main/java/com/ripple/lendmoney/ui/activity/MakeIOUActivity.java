package com.ripple.lendmoney.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.present.MakeIOUPresent;

import butterknife.BindView;
import butterknife.OnClick;

public class MakeIOUActivity extends BaseActivity<MakeIOUPresent> {

    @BindView(R.id.tv_makeIou_max)
    TextView tvMakeIouMax;
    @BindView(R.id.tv_makeIou_borrower)
    TextView tvMakeIouBorrower;
    @BindView(R.id.tv_makeIou_lender)
    TextView tvMakeIouLender;
    @BindView(R.id.et_makeIou_lendNum)
    EditText etMakeIouLendNum;
    @BindView(R.id.tv_makeIou_repayMethod)
    TextView tvMakeIouRepayMethod;
    @BindView(R.id.tv_makeIou_repayDays)
    TextView tvMakeIouRepayDays;
    @BindView(R.id.tv_makeIou_interest)
    TextView tvMakeIouInterest;
    @BindView(R.id.tv_makeIou_computeRealNums)
    TextView tvMakeIouComputeRealNums;
    @BindView(R.id.tv_makeIou_realNums)
    TextView tvMakeIouRealNums;
    @BindView(R.id.tv_makeIou_computeAll)
    TextView tvMakeIouComputeAll;
    @BindView(R.id.tv_makeIou_repayAllNums)
    TextView tvMakeIouRepayAllNums;
    @BindView(R.id.tv_makeIou_repayDate)
    TextView tvMakeIouRepayDate;
    @BindView(R.id.tv_makeIou_repayAllNums1)
    TextView tvMakeIouRepayAllNums1;
    @BindView(R.id.tv_makeIou_lendAgreement)
    TextView tvMakeIouLendAgreement;
    @BindView(R.id.btn_makeIou_makeIou)
    Button btnMakeIouMakeIou;

    @Override
    protected String topBarTitle() {
        return "打借条";
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_make_iou;
    }

    @Override
    public MakeIOUPresent newP() {
        return new MakeIOUPresent();
    }


    @OnClick({R.id.tv_makeIou_repayMethod, R.id.tv_makeIou_repayDays, R.id.tv_makeIou_lendAgreement, R.id.btn_makeIou_makeIou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_makeIou_repayMethod:
                break;
            case R.id.tv_makeIou_repayDays:
                break;
            case R.id.tv_makeIou_lendAgreement:
                WebActivity.launch(this, URLConfig.LEND_AGREEMENT, "借款协议");
                break;
            case R.id.btn_makeIou_makeIou:
                finish();
                break;
        }
    }
}
