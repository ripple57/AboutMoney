package com.ripple.lendmoney.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.ui.fragment.HomeFragment;
import com.ripple.lendmoney.ui.fragment.MiddleFragment;
import com.ripple.lendmoney.ui.fragment.MineFragment;
import com.ripple.lendmoney.utils.AppManager;
import com.ripple.lendmoney.utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;

public class MainActivity extends BaseActivity {

    @BindView(R.id.home_vp)
    ViewPager homeVp;
    @BindView(R.id.home_bottom_view)
    BottomNavigationView homeBottomView;
    private List<Fragment> fragmentList = new ArrayList<>();




    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        setHomeVpAdapter();
    }


    private void initView() {
        BottomNavigationViewHelper.disableShiftMode(homeBottomView);
        homeVp.setOffscreenPageLimit(3);
        topBar.setTitle("首页");
        homeBottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        homeVp.setCurrentItem(0);
                        topBar.setTitle("首页");
                        break;

                    case R.id.bottom_middle:
                        homeVp.setCurrentItem(1);
                        topBar.setTitle("中间");
                        break;

                    case R.id.bottom_mine:
                        homeVp.setCurrentItem(2);
                        topBar.setTitle("我的");
                        break;

                }
                return true;
            }
        });


        homeVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        homeBottomView.setSelectedItemId(R.id.bottom_home);
                        break;

                    case 1:
                        homeBottomView.setSelectedItemId(R.id.bottom_middle);
                        break;

                    case 2:
                        homeBottomView.setSelectedItemId(R.id.bottom_mine);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    protected boolean isShowBack() {
        return false;
    }

    private void setHomeVpAdapter() {
        HomeFragment homeFragment = new HomeFragment();
        MiddleFragment mddleFragment = new MiddleFragment();
        MineFragment mineFragment = new MineFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(mddleFragment);
        fragmentList.add(mineFragment);
        XFragmentAdapter xFragmentAdapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, null);
        homeVp.setAdapter(xFragmentAdapter);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }


    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            getvDelegate().toastShort("再按一次退出"+getResources().getString(R.string.app_name));
            mExitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().AppExit(context);
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void getNetData() {

    }


}
