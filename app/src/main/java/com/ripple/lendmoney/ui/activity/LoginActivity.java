package com.ripple.lendmoney.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.model.UserBean;
import com.ripple.lendmoney.present.LoginPresent;
import com.ripple.lendmoney.utils.GPSUtils;
import com.ripple.lendmoney.utils.SPUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
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
    private boolean need_back;
    private String lat = "";
    private String lon = "";

    @Override
    protected String topBarTitle() {
        return "登录";
    }

    @Override
    protected boolean topBarIsTransparent() {
        return true;
    }

    @OnClick({R.id.imgbtn_login_phone_cancle, R.id.btn_login_getcode, R.id.btn_login_login, R.id.tv_login_agreement,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_login_phone_cancle:
                et_login_phone.setText(null);
                break;
            case R.id.btn_login_getcode:
                getRxPermissions()
                        .request(Manifest.permission.INTERNET)
                        .subscribe(granted -> {
                            if (granted) {//同意
                                getCode();
                            } else {//拒绝
                                ToastUtil.showToast("亲，同意了权限才能更好的为您服务哦");
                            }
                        });
                break;
            case R.id.btn_login_login:
                getRxPermissions()
                        .request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE)
                        .subscribe(granted -> {
                            if (granted) {//同意
                                login();
                            } else {//拒绝
                                ToastUtil.showToast("亲，同意了权限才能更好的为您服务哦");
                            }
                        });

                break;
            case R.id.tv_login_agreement:
                toAgreementView();
                break;

        }
    }

    private void toAgreementView() {
        WebActivity.launch(this, URLConfig.REGIST_AGREEMENT, "注册服务协议");
    }

    private void login() {
        String code = et_login_password.getText().toString().trim();
        phoneNum = et_login_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showToast("请输入手机号码");
        } else if (!phoneNum.matches(Constant.REG_PHONE)) {
            ToastUtil.showToast("请输入正确的手机号");
        } else if (TextUtils.isEmpty(code) || code.length() != 6) {
            ToastUtil.showToast("请输入6位验证码");
        } else {
            getP().login(this, phoneNum, code, lat, lon);
        }
    }

    private void getCode() {
        phoneNum = et_login_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showToast("请输入手机号码");
            return;
        } else if (!phoneNum.matches(Constant.REG_PHONE)) {
            ToastUtil.showToast("请输入正确的手机号");
            return;
        } else {
            btn_login_getcode.setClickable(false);
            btn_login_getcode.setText("正在发送");
            long tel = Long.parseLong(phoneNum.substring(1)) * 8;
            getP().getCode(this, tel + "");
        }

    }

    public void sendCodeSuccess() {
        ToastUtil.showToast("验证码已发送!");
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
                        btn_login_getcode.setText("获取验证码");
                        btn_login_getcode.setClickable(true);
                    }
                })
                .subscribe();
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        need_back = getIntent().getBooleanExtra("need_back", false);
        getRxPermissions()
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {//同意
                        GPSUtils.getInstance(this).getLngAndLat(new GPSUtils.OnLocationResultListener() {
                            @Override
                            public void onLocationResult(Location location) {

                            }

                            @Override
                            public void OnLocationChange(Location location) {
                                lat = location.getLatitude() + "";
                                lon = location.getLongitude() + "";
                            }
                        });
                    } else {//拒绝
                        ToastUtil.showToast("亲，同意了权限才能更好的为您服务哦");
                    }
                });
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
        GPSUtils.getInstance(this).removeListener();
    }

    public static void launch(Activity activity, boolean needBack) {
        Router.newIntent(activity)
                .putBoolean("need_back", needBack)
                .to(LoginActivity.class)
                .launch();
    }

    public void loginSuccess(UserBean bean) {
        GlobleParms.sessionId = bean.getSessionId();
        GlobleParms.userName = bean.getUserName();
        SPUtils.getInstance(this).save(Constant.SESSIONID, GlobleParms.sessionId);
        if (need_back) {
            finish();
        } else {
            MainActivity.launch(this);
            finish();
        }
    }

    public void sendCodeFailed() {
        btn_login_getcode.setText("获取验证码");
        btn_login_getcode.setClickable(true);
    }
}

