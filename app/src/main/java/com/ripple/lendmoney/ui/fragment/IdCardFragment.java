package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.present.IdCardFragPresent;
import com.ripple.lendmoney.ui.activity.AuthenticateActivity;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:16.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class IdCardFragment extends BaseLazyFragment<IdCardFragPresent> {


    @BindView(R.id.iv_idcardFrag_head)
    ImageView ivIdcardFragHead;
    @BindView(R.id.iv_idcardFrag_idcard_front)
    ImageView ivIdcardFragIdcardFront;
    @BindView(R.id.iv_idcardFrag_idcard_back)
    ImageView ivIdcardFragIdcardBack;
    @BindView(R.id.et_idcardFrag_idcard)
    EditText etIdcardFragIdcard;
    @BindView(R.id.et_idcardFrag_name)
    EditText etIdcardFragName;
    @BindView(R.id.btn_idcardFrag_commit)
    Button btnIdcardFragCommit;

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        btnIdcardFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_idcard_layout;
    }

    @Override
    public IdCardFragPresent newP() {
        return null;
    }


    @OnClick({R.id.iv_idcardFrag_idcard_front, R.id.iv_idcardFrag_idcard_back, R.id.btn_idcardFrag_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_idcardFrag_idcard_front:
                break;
            case R.id.iv_idcardFrag_idcard_back:
                break;
            case R.id.btn_idcardFrag_commit:
                String idCardNo = etIdcardFragIdcard.getText().toString().trim();
                String realName = etIdcardFragName.getText().toString().trim();
                String reg = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
                if (TextUtils.isEmpty(realName)) {
                    ToastUtil.showToast("请输入您的真实姓名");
                } else if (TextUtils.isEmpty(idCardNo)) {
                    ToastUtil.showToast("请输入您的身份证号");
                } else if (!idCardNo.matches(reg)) {
                    ToastUtil.showToast("请输入正确的身份证号码");
                } else {
                    getP().uploadIdCardInfo(realName, idCardNo);

                    if (GlobleParms.AuthenticateCanNext) {
                        ((AuthenticateActivity) context).selectFragment(Constant.TYPE_FAMILYFRAG);
                    } else {

                    }
                }
                break;
        }
    }

    public void uploadSuccess() {
        ToastUtil.showToast("上传成功");
    }
}
