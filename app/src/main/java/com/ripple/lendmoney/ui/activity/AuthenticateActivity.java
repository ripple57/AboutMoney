package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.ui.fragment.BankCardFragment;
import com.ripple.lendmoney.ui.fragment.ContactsFragment;
import com.ripple.lendmoney.ui.fragment.CreditFragment;
import com.ripple.lendmoney.ui.fragment.FamilyFragment;
import com.ripple.lendmoney.ui.fragment.IdCardFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.router.Router;

public class AuthenticateActivity extends BaseActivity {


    @BindView(R.id.iv_authenInfoAct_head)
    ImageView ivAuthenInfoActHead;
    @BindView(R.id.vp_authenticateActivity)
    QMUIViewPager authenticateViewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private int fragmentType;

    @Override
    protected String topBarTitle() {
        return "信息认证";
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
        fragmentType = getIntent().getIntExtra(Constant.PARAM_FRAGMENTTYPE, 0);

        initAuthenticateViewPager();
        authenticateViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case Constant.TYPE_IDCARDFRAG:
                        ivAuthenInfoActHead.setImageResource(R.drawable.shenfenrenzheng_banner);
                        break;
                    case Constant.TYPE_FAMILYFRAG:
                        ivAuthenInfoActHead.setImageResource(R.drawable.lianxiren_banner);
                        break;
                    case Constant.TYPE_BANKCARDFRAG:
                        ivAuthenInfoActHead.setImageResource(R.drawable.bangding_banner);
                        break;
                    case Constant.TYPE_CREDITFRAG:
                        ivAuthenInfoActHead.setImageResource(R.drawable.zhimaxinyong_banner);
                        break;
                    case Constant.TYPE_CONTACTSFRAG:
                        ivAuthenInfoActHead.setImageResource(R.drawable.tongxunlu_banner);
                        break;
                }

            }
        });
    }

    private void initAuthenticateViewPager() {
        authenticateViewPager.setOffscreenPageLimit(5);
        if (GlobleParms.AuthenticateCanNext) {
            fragmentList.add(new IdCardFragment());//身份证
            fragmentList.add(new FamilyFragment());//紧急联系人
            fragmentList.add(new BankCardFragment());//银行卡
            fragmentList.add(new CreditFragment());//芝麻信用
            fragmentList.add(new ContactsFragment());//通讯录
        } else {
            switch (fragmentType) {
                case Constant.TYPE_IDCARDFRAG:
                    fragmentList.add(new IdCardFragment());
                    break;
                case Constant.TYPE_FAMILYFRAG:
                    fragmentList.add(new FamilyFragment());
                    break;
                case Constant.TYPE_BANKCARDFRAG:
                    fragmentList.add(new BankCardFragment());
                    break;
                case Constant.TYPE_CREDITFRAG:
                    fragmentList.add(new CreditFragment());
                    break;
                case Constant.TYPE_CONTACTSFRAG:
                    fragmentList.add(new ContactsFragment());
                    break;
            }
        }
        XFragmentAdapter xFragmentAdapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, null);
        authenticateViewPager.setAdapter(xFragmentAdapter);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_authenticate;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void selectFragment(int fragmentType) {
        authenticateViewPager.setCurrentItem(fragmentType);
    }

    /**
     * 首次进入认证页面,会自动跳下一项认证
     *
     * @param activity
     */
    public static void launch(Activity activity) {
        GlobleParms.AuthenticateCanNext = true;
        Router.newIntent(activity)
                .to(AuthenticateActivity.class)
                .launch();
    }

    /**
     * 已认证过,进来修改认证信息
     *
     * @param activity
     * @param fragmentType
     */
    public static void launch(Activity activity, int fragmentType) {
        GlobleParms.AuthenticateCanNext = false;
        Router.newIntent(activity)
                .putInt(Constant.PARAM_FRAGMENTTYPE, fragmentType)
                .to(AuthenticateActivity.class)
                .launch();
    }
}
