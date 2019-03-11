package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.AuthenticateInfoPresent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class AuthenticateInfoActivity extends BaseActivity<AuthenticateInfoPresent> {

    @BindView(R.id.tv_authenInfoAct_idEdit)
    TextView tvAuthenInfoActIdEdit;
    @BindView(R.id.tv_authenInfoAct_idName)
    TextView tvAuthenInfoActIdName;
    @BindView(R.id.tv_authenInfoAct_idNo)
    TextView tvAuthenInfoActIdNo;
    @BindView(R.id.iv_authenInfoAct_idFront)
    ImageView ivAuthenInfoActIdFront;
    @BindView(R.id.iv_authenInfoAct_idBack)
    ImageView ivAuthenInfoActIdBack;
    @BindView(R.id.tv_authenInfoAct_familyEdit)
    TextView tvAuthenInfoActFamilyEdit;
    @BindView(R.id.tv_authenInfoAct_familyDirect_relation)
    TextView tvAuthenInfoActFamilyDirectRelation;
    @BindView(R.id.tv_authenInfoAct_familyDirect_name)
    TextView tvAuthenInfoActFamilyDirectName;
    @BindView(R.id.tv_authenInfoAct_familyDirect_phone)
    TextView tvAuthenInfoActFamilyDirectPhone;
    @BindView(R.id.tv_authenInfoAct_familyContact_relation)
    TextView tvAuthenInfoActFamilyContactRelation;
    @BindView(R.id.tv_authenInfoAct_familyContact_name)
    TextView tvAuthenInfoActFamilyContactName;
    @BindView(R.id.tv_authenInfoAct_familyContact_phone)
    TextView tvAuthenInfoActFamilyContactPhone;
    @BindView(R.id.tv_authenInfoAct_bankEdit)
    TextView tvAuthenInfoActBankEdit;
    @BindView(R.id.tv_authenInfoAct_bankNo)
    TextView tvAuthenInfoActBankNo;
    @BindView(R.id.tv_authenInfoAct_bankAddress)
    TextView tvAuthenInfoActBankAddress;
    @BindView(R.id.tv_authenInfoAct_bankPhone)
    TextView tvAuthenInfoActBankPhone;
    @BindView(R.id.tv_authenInfoAct_creditEdit)
    TextView tvAuthenInfoActCreditEdit;
    @BindView(R.id.iv_authenInfoAct_creditFront)
    ImageView ivAuthenInfoActCreditFront;
    @BindView(R.id.iv_authenInfoAct_creditBack)
    ImageView ivAuthenInfoActCreditBack;
    @BindView(R.id.tv_authenInfoAct_contactEdit)
    TextView tvAuthenInfoActContactEdit;
    @BindView(R.id.tv_authenInfoAct_contactState)
    TextView tvAuthenInfoActContactState;
    @BindView(R.id.btn_authenInfoAct_commit)
    Button btnAuthenInfoActCommit;

    @Override
    protected String topBarTitle() {
        return "认证信息";
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_authenticate_info;
    }

    @Override
    public AuthenticateInfoPresent newP() {
        return new AuthenticateInfoPresent();
    }


    @OnClick({R.id.tv_authenInfoAct_idEdit, R.id.iv_authenInfoAct_idFront, R.id.iv_authenInfoAct_idBack, R.id.tv_authenInfoAct_familyEdit, R.id.tv_authenInfoAct_bankEdit, R.id.tv_authenInfoAct_creditEdit, R.id.iv_authenInfoAct_creditFront, R.id.iv_authenInfoAct_creditBack, R.id.tv_authenInfoAct_contactEdit, R.id.btn_authenInfoAct_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_authenInfoAct_idEdit:
                break;
            case R.id.tv_authenInfoAct_familyEdit:
                break;
            case R.id.tv_authenInfoAct_bankEdit:
                break;
            case R.id.tv_authenInfoAct_creditEdit:
                break;
            case R.id.tv_authenInfoAct_contactEdit:
                break;
            case R.id.iv_authenInfoAct_idFront:
                break;
            case R.id.iv_authenInfoAct_idBack:
                break;
            case R.id.iv_authenInfoAct_creditFront:
                break;
            case R.id.iv_authenInfoAct_creditBack:
                break;

            case R.id.btn_authenInfoAct_commit:
                break;
        }
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity).to(AuthenticateInfoActivity.class).launch();
    }
}
