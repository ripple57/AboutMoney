package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.event.RefreshMyInfoEvent;
import com.ripple.lendmoney.model.AuthenticateInfoBean;
import com.ripple.lendmoney.present.MyInfoPresent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class MyInfoActivity extends BaseActivity<MyInfoPresent> {


    @BindView(R.id.tv_myinfoact_idcard_state)
    TextView tvMyinfoactIdcardState;
    @BindView(R.id.ll_myinfoact_idcard)
    LinearLayout llMyinfoactIdcard;
    @BindView(R.id.tv_myinfoact_family_state)
    TextView tvMyinfoactFamilyState;
    @BindView(R.id.ll_myinfoact_family)
    LinearLayout llMyinfoactFamily;
    @BindView(R.id.tv_myinfoact_bankcard_state)
    TextView tvMyinfoactBankcardState;
    @BindView(R.id.ll_myinfoact_bankcard)
    LinearLayout llMyinfoactBankcard;
    @BindView(R.id.tv_myinfoact_credit_state)
    TextView tvMyinfoactCreditState;
    @BindView(R.id.ll_myinfoact_credit)
    LinearLayout llMyinfoactCredit;
    @BindView(R.id.tv_myinfoact_contacts_state)
    TextView tvMyinfoactContactsState;
    @BindView(R.id.ll_myinfoact_contacts)
    LinearLayout llMyinfoactContacts;
    @BindView(R.id.tv_myinfoact_comple)
    TextView tvMyinfoactComple;
    private int infoDegree;

    @BindColor(R.color.text_red)
    int text_red;
    @BindColor(R.color.text_hint)
    int text_hint;
    @BindView(R.id.btn_myinfoact_commit)
    Button btnMyinfoactCommit;

    @Override
    protected String topBarTitle() {
        return "我的资料";
    }

    @Override
    public void getNetData() {
        getP().getUserInfo(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        infoDegree = 0;
        getNetData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    public MyInfoPresent newP() {
        return new MyInfoPresent();
    }


    @OnClick({R.id.ll_myinfoact_idcard, R.id.ll_myinfoact_family, R.id.ll_myinfoact_bankcard, R.id.ll_myinfoact_credit,
            R.id.ll_myinfoact_contacts, R.id.btn_myinfoact_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_myinfoact_idcard:
                AuthenticateActivity.launch(this, Constant.TYPE_IDCARDFRAG);
                break;
            case R.id.ll_myinfoact_family:
                AuthenticateActivity.launch(this, Constant.TYPE_FAMILYFRAG);
                break;
            case R.id.ll_myinfoact_bankcard:
                AuthenticateActivity.launch(this, Constant.TYPE_BANKCARDFRAG);
                break;
            case R.id.ll_myinfoact_credit:
                AuthenticateActivity.launch(this, Constant.TYPE_CREDITFRAG);
                break;
            case R.id.ll_myinfoact_contacts:
                AuthenticateActivity.launch(this, Constant.TYPE_CONTACTSFRAG);
                break;
            case R.id.btn_myinfoact_commit:
                MakeIOUActivity.launch(this);
                break;
        }
    }

    public void setItemAuthenState(TextView tv, boolean hasAuthen) {
        if (hasAuthen) {
            tv.setText("已认证");
            tv.setTextColor(text_hint);
            infoDegree++;
        } else {
            tv.setText("未认证");
            tv.setTextColor(text_red);
        }
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(MyInfoActivity.class)
                .launch();
    }


    public void setAuthenState(AuthenticateInfoBean.DataBean bean) {
        setItemAuthenState(tvMyinfoactIdcardState, bean.getIdCardState());
        setItemAuthenState(tvMyinfoactFamilyState, bean.getFamilyState());
        setItemAuthenState(tvMyinfoactBankcardState, bean.getBankState());
        setItemAuthenState(tvMyinfoactCreditState, bean.getCreditState());
        setItemAuthenState(tvMyinfoactContactsState, bean.getContactsState());
        tvMyinfoactComple.setText(infoDegree * 20 + "%");
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void refreshView(RefreshMyInfoEvent event) {
        getNetData();
    }
}
