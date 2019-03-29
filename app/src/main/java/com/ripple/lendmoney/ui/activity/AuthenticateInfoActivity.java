package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.event.RefreshUserInfoEvent;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.AuthenticateInfoBean;
import com.ripple.lendmoney.present.AuthenticateInfoPresent;
import com.ripple.lendmoney.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.imageloader.ILoader;
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
        getP().getUserInfo(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getNetData();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_authenticate_info;
    }

    @Override
    public AuthenticateInfoPresent newP() {
        return new AuthenticateInfoPresent();
    }


    @OnClick({R.id.tv_authenInfoAct_idEdit, R.id.tv_authenInfoAct_familyEdit, R.id.tv_authenInfoAct_bankEdit, R.id.tv_authenInfoAct_creditEdit, R.id.tv_authenInfoAct_contactEdit, R.id.btn_authenInfoAct_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_authenInfoAct_idEdit:
                AuthenticateActivity.launch(this, Constant.TYPE_IDCARDFRAG);
                break;
            case R.id.tv_authenInfoAct_familyEdit:
                AuthenticateActivity.launch(this, Constant.TYPE_FAMILYFRAG);
                break;
            case R.id.tv_authenInfoAct_bankEdit:
                AuthenticateActivity.launch(this, Constant.TYPE_BANKCARDFRAG);
                break;
            case R.id.tv_authenInfoAct_creditEdit:
                AuthenticateActivity.launch(this, Constant.TYPE_CREDITFRAG);
                break;
            case R.id.tv_authenInfoAct_contactEdit:
                AuthenticateActivity.launch(this, Constant.TYPE_CONTACTSFRAG);
                break;
            case R.id.btn_authenInfoAct_commit:
                commit();
                break;
        }
    }

    private void commit() {
        if (GlobleParms.userInfo.getInfoDegree() != 5) {
            ToastUtil.showToast("请完善您的认证信息");
        } else {
            MakeIOUActivity.launch(this);
        }
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity).to(AuthenticateInfoActivity.class).launch();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void refreshView(RefreshUserInfoEvent event) {
        getNetData();
    }

    public void setAuthenticateInfoDate(AuthenticateInfoBean.DataBean bean) {
        tvAuthenInfoActIdName.setText(bean.getRealName());
        tvAuthenInfoActIdNo.setText(bean.getIdNumber());
        tvAuthenInfoActFamilyDirectRelation.setText(bean.getDirectKinship());
        tvAuthenInfoActFamilyDirectName.setText(bean.getKinshipName());
        tvAuthenInfoActFamilyDirectPhone.setText(bean.getKinshipPhone());
        tvAuthenInfoActFamilyContactRelation.setText(bean.getUrgentContact());
        tvAuthenInfoActFamilyContactName.setText(bean.getContactName());
        tvAuthenInfoActFamilyContactPhone.setText(bean.getContactPhone());
        tvAuthenInfoActBankNo.setText(bean.getBankCardNumber());
        tvAuthenInfoActBankAddress.setText(bean.getOpeningBank());
        tvAuthenInfoActBankPhone.setText(bean.getReservePhone());
        tvAuthenInfoActContactState.setText(bean.getContactsState() ? "已获取" : "未获取");

        ILoader.Options options = new ILoader.Options(R.drawable.renzhengxinxi_icon_zhengmian, R.drawable.renzhengxinxi_icon_zhengmian);
        ILFactory.getLoader().loadNet(ivAuthenInfoActIdFront, URLConfig.BASE_URL + bean.getFrontIDCard(), options);

        options = new ILoader.Options(R.drawable.renzhengxinxi_icon_fanmian, R.drawable.renzhengxinxi_icon_fanmian);
        ILFactory.getLoader().loadNet(ivAuthenInfoActIdBack, URLConfig.BASE_URL + bean.getBackIDCard(), options);

        options = new ILoader.Options(R.drawable.renzhengxinxi_icon_gerenxinxi, R.drawable.renzhengxinxi_icon_gerenxinxi);
        ILFactory.getLoader().loadNet(ivAuthenInfoActCreditFront, URLConfig.BASE_URL + bean.getAlipayCreditImg1(), options);

        options = new ILoader.Options(R.drawable.renzhengxinxi_icon_zhimaxinyong, R.drawable.renzhengxinxi_icon_zhimaxinyong);
        ILFactory.getLoader().loadNet(ivAuthenInfoActCreditBack, URLConfig.BASE_URL + bean.getAlipayCreditImg2(), options);

    }
}
