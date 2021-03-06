package com.ripple.lendmoney.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.present.MinePresent;
import com.ripple.lendmoney.ui.activity.LoginActivity;
import com.ripple.lendmoney.ui.activity.MyInfoActivity;
import com.ripple.lendmoney.ui.activity.SuggestActivity;
import com.ripple.lendmoney.utils.SPUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/*****************************************************
 * 作者: HuangShaobo on 2019/2/24 16:39.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class MineFragment extends XLazyFragment<MinePresent> {

    @BindView(R.id.iv_minefrag_headIcon)
    QMUIRadiusImageView ivMinefragHeadIcon;
    @BindView(R.id.tv_minefrag_username)
    TextView tvMinefragUsername;
    @BindView(R.id.tv_item_walletMoney)
    TextView tvItemWalletMoney;
    @BindView(R.id.tv_item_remainMoney)
    TextView tvItemRemainMoney;
    @BindView(R.id.ll_minefrag_myInfo)
    LinearLayout llMinefragMyInfo;
    @BindView(R.id.ll_minefrag_suggest)
    LinearLayout llMinefragSuggest;
    @BindView(R.id.ll_minefrag_aboutUs)
    LinearLayout llMinefragAboutUs;
    @BindView(R.id.ll_minefrag_contactUs)
    LinearLayout llMinefragContactUs;
    @BindView(R.id.ll_minefrag_quit)
    LinearLayout llMinefragQuit;

    @BindString(R.string.service_phone)
    String service_phone;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        tvMinefragUsername.setText(GlobleParms.anonymity);

    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_mine_layout;
    }

    @Override
    public MinePresent newP() {
        return new MinePresent();
    }



    @OnClick({R.id.iv_minefrag_headIcon, R.id.ll_minefrag_myInfo, R.id.ll_minefrag_suggest, R.id.ll_minefrag_aboutUs,
            R.id.ll_minefrag_contactUs, R.id.ll_minefrag_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_minefrag_headIcon:
                break;
            case R.id.ll_minefrag_myInfo:
                MyInfoActivity.launch(context);
                break;
            case R.id.ll_minefrag_suggest:
                SuggestActivity.launch(context);
                break;
            case R.id.ll_minefrag_aboutUs:
                new QMUIDialog.MessageDialogBuilder(context)
                        .setTitle("公司简介")
                        .setMessage("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试")
                        .addAction("知道了", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.ll_minefrag_contactUs:
                new QMUIDialog.MessageDialogBuilder(context)
                        .setTitle("联系客服")
                        .setMessage("拨打电话给" + service_phone)
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("打电话", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                callPhone(service_phone);
                                dialog.dismiss();
                            }
                        }).show();


                break;
            case R.id.ll_minefrag_quit:
                new QMUIDialog.MessageDialogBuilder(context).setMessage("您确定要退出吗?")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                getP().userExit(context,GlobleParms.userId);
                            }
                        }).show();
                break;
        }
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    public void exitSuccess() {
        SPUtils.getInstance(context).remove(Constant.SESSIONID);
        SPUtils.getInstance(context).remove(Constant.USERID);
        SPUtils.getInstance(context).remove(Constant.USERNAME);
        GlobleParms.sessionId = "";
        GlobleParms.userId = "";
        GlobleParms.userName = "";
        ToastUtil.showToast("用户已经退出了!");
        LoginActivity.launch(context);

    }
}
