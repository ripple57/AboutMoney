package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.present.BankCardFraPresent;
import com.ripple.lendmoney.ui.activity.AuthenticateActivity;
import com.ripple.lendmoney.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:25.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class BankCardFragment extends BaseLazyFragment<BankCardFraPresent> {
    @BindView(R.id.iv_banKCardFrag_head)
    ImageView ivBanKCardFragHead;
    @BindView(R.id.et_bankCardFrag_bankNo)
    EditText etBankCardFragBankNo;
    @BindView(R.id.et_bankCardFrag_bank)
    EditText etBankCardFragBank;
    @BindView(R.id.et_bankCardFrag_phone)
    EditText etBankCardFragPhone;
    @BindView(R.id.btn_bankCardFrag_commit)
    Button btnBankCardFragCommit;

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        btnBankCardFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_bankcard_layout;
    }

    @Override
    public BankCardFraPresent newP() {
        return new BankCardFraPresent();
    }


    @OnClick(R.id.btn_bankCardFrag_commit)
    public void onViewClicked() {
        String bankNo = etBankCardFragBankNo.getText().toString().trim();
        String bank = etBankCardFragBank.getText().toString().trim();
        String bankPhone = etBankCardFragPhone.getText().toString().trim();
        if (TextUtils.isEmpty(bankNo)) {
            ToastUtil.showToast("请填写您的银行卡号");
        } else if (TextUtils.isEmpty(bank)) {
            ToastUtil.showToast("请填写您的开户行");
        } else if (!bankPhone.matches(Constant.REG_PHONE)) {
            ToastUtil.showToast("请填写正确的手机号");
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("bankNo", bankNo);
            map.put("bank", bank);
            map.put("bankPhone", bankPhone);
            getP().uploadBankCardInfo(map);
        }
    }

    public void uploadSuccess() {
        ToastUtil.showToast("上传成功");
        if (GlobleParms.AuthenticateCanNext) {
            ((AuthenticateActivity) context).selectFragment(Constant.TYPE_CREDITFRAG);
        } else {
            context.finish();
        }
    }
}
