package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.ui.fragment.AddressBookFragment;
import com.ripple.lendmoney.ui.fragment.BankCardFragment;
import com.ripple.lendmoney.ui.fragment.ContactsFragment;
import com.ripple.lendmoney.ui.fragment.CreditFragment;
import com.ripple.lendmoney.ui.fragment.IdCardFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.router.Router;

public class AuthenticateActivity extends BaseActivity {


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
    }

    private void initAuthenticateViewPager() {
        if (GlobleParms.AuthenticateCanNext) {
            fragmentList.add(new IdCardFragment());
            fragmentList.add(new ContactsFragment());
            fragmentList.add(new BankCardFragment());
            fragmentList.add(new CreditFragment());
            fragmentList.add(new AddressBookFragment());
        } else {
            switch (fragmentType) {
                case Constant.TYPE_IDCARDFRAG:
                    fragmentList.add(new IdCardFragment());
                    break;
                case Constant.TYPE_CONTACTSFRAG:
                    fragmentList.add(new ContactsFragment());
                    break;
                case Constant.TYPE_BANKCARDFRAG:
                    fragmentList.add(new BankCardFragment());
                    break;
                case Constant.TYPE_CREDITFRAG:
                    fragmentList.add(new CreditFragment());
                    break;
                case Constant.TYPE_ADDRESSBOOKFRAG:
                    fragmentList.add(new AddressBookFragment());
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
