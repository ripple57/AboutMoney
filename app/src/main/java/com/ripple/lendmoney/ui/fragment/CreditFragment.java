package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.present.CreditFragPresent;
import com.ripple.lendmoney.ui.activity.AuthenticateActivity;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:26.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class CreditFragment extends BaseLazyFragment<CreditFragPresent> {
    public boolean hasCreditScore = false;
    public boolean hasAlipayInfo = false;
    @BindView(R.id.iv_creditFrag_head)
    ImageView ivCreditFragHead;
    @BindView(R.id.iv_creditFrag_alipay)
    ImageView ivCreditFragAlipay;
    @BindView(R.id.iv_creditFrag_credit)
    ImageView ivCreditFragCredit;
    @BindView(R.id.btn_creditFrag_commit)
    Button btnCreditFragCommit;

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        btnCreditFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_credit_layout;
    }

    @Override
    public CreditFragPresent newP() {
        return new CreditFragPresent();
    }


    @OnClick({R.id.iv_creditFrag_alipay, R.id.iv_creditFrag_credit, R.id.btn_creditFrag_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_creditFrag_alipay:
                getP().uploadAlipayInfo();
                break;
            case R.id.iv_creditFrag_credit:
                getP().uploadCreditScore();
                break;
            case R.id.btn_bankCardFrag_commit:
                if (!hasAlipayInfo) {
                    ToastUtil.showToast("请上传您的支付宝个人信息截图");
                } else if (!hasCreditScore) {
                    ToastUtil.showToast("请上传您的芝麻信用截图");
                } else {
                    uploadSuccess();
                }
                break;
        }
    }

    public void uploadSuccess() {
        ToastUtil.showToast("上传成功");
        if (GlobleParms.AuthenticateCanNext) {
            ((AuthenticateActivity) context).selectFragment(Constant.TYPE_CONTACTSFRAG);
        } else {
            context.finish();
        }
    }
}
