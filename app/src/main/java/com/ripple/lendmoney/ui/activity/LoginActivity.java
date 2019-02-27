package com.ripple.lendmoney.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.LoginPresent;
import com.ripple.lendmoney.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginPresent> {
    @BindView(R.id.et_login_phone)
    EditText et_login_phone;
    @BindView(R.id.imgbtn_login_phone_cancle)
    ImageButton imgbtn_login_phone_cancle;
    @BindView(R.id.et_login_password)
    EditText et_login_password;
    @BindView(R.id.btn_login_getcode)
    Button btn_login_getcode;
    @BindView(R.id.btn_login_login)
    Button btn_login_login;
    @BindView(R.id.tv_login_agreement)
    TextView tv_login_agreement;
    private String phoneNum;
    private Disposable mdDisposable;


    @OnClick({R.id.imgbtn_login_phone_cancle, R.id.btn_login_getcode, R.id.btn_login_login, R.id.tv_login_agreement,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_login_phone_cancle:
                et_login_phone.setText(null);
                break;
            case R.id.btn_login_getcode:
                getCode();
                break;
            case R.id.btn_login_login:
                login();
                break;
            case R.id.tv_login_agreement:
                toAgreementView();
                break;

        }
    }

    private void toAgreementView() {
        // TODO: 2019/2/25 跳转服务协议页面
    }

    private void login() {
        String code = et_login_password.getText().toString().trim();
        getP().login(phoneNum, code);
    }

    private void getCode() {
        phoneNum = et_login_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showToast("请输入手机号码");
            return;
        } else if (!phoneNum.matches("^1[34578]\\d{9}$")) {
            ToastUtil.showToast("请输入正确的手机号");
            return;
        } else {
            btn_login_getcode.setText("正在发送");
            long tel = Long.parseLong(phoneNum.substring(1)) * 8;
            getP().getCode(phoneNum);
        }

        mdDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        btn_login_getcode.setText("重新获取" + (60 - aLong) + "s");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        btn_login_getcode.setText("重新获取验证码");
                    }
                })
                .subscribe();
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        topBar.setTitle("登录");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }

    public void setLoginSuccess() {

    }
}

