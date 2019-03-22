package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.present.AssessPresent;
import com.ripple.lendmoney.utils.SPUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class AssessActivity extends BaseActivity<AssessPresent> {

    private static final String IOUID = "iouId";
    @BindView(R.id.iv_assessAct_banner)
    ImageView ivAssessActBanner;
    @BindView(R.id.iv_assessAct_checkAgreement)
    ImageView ivAssessActCheckAgreement;
    @BindView(R.id.tv_assessAct_agreement)
    TextView tvAssessActAgreement;
    @BindView(R.id.tv_assessAct_nowPrice)
    TextView tvAssessActNowPrice;
    @BindView(R.id.btn_assessAct_applyLend)
    Button btnAssessActApplyLend;
    private String iouID;

    public static void launch(Activity activity, String iouId) {
        Router.newIntent(activity).putString(IOUID, iouId).to(AssessActivity.class).launch();
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
        iouID = getIntent().getStringExtra(IOUID);
        tvAssessActNowPrice.setText(SPUtils.getInstance(this).getValue(Constant.PRICE, 79f)+"");
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
                WebActivity.launch(this, URLConfig.LEND_AGREEMENT + "?IOUID=" + iouID, "借条合同");
                break;
            case R.id.tv_assessAct_agreement:
                break;
            case R.id.btn_assessAct_applyLend:
                showSimpleBottomSheetGrid();
                break;
        }
    }

    private void showSimpleBottomSheetGrid() {
        final int TAG_WECHAT = 0;
        final int TAG_ALIPAY = 1;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this);
        builder.addItem(R.drawable.wechat, "微信支付", TAG_WECHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.alipay, "支付宝支付", TAG_ALIPAY, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView) {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_WECHAT:
                                ToastUtil.showToast("选择了微信支付");
                                break;
                            case TAG_ALIPAY:
                                ToastUtil.showToast("选择了支付宝支付");
                                break;

                        }
                    }
                }).build().show();


    }

}
