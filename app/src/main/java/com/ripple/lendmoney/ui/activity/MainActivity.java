package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.event.OrderEvent;
import com.ripple.lendmoney.present.MainPresent;
import com.ripple.lendmoney.ui.fragment.HomeFragment;
import com.ripple.lendmoney.ui.fragment.MineFragment;
import com.ripple.lendmoney.ui.fragment.OrderFragment;
import com.ripple.lendmoney.utils.AppManager;
import com.ripple.lendmoney.utils.BottomNavigationViewHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<MainPresent> {

    @BindView(R.id.home_vp)
    QMUIViewPager homeVp;
    @BindView(R.id.home_bottom_view)
    BottomNavigationView homeBottomView;
    private List<Fragment> fragmentList = new ArrayList<>();
    private View point;//是否有订单的指示点


    @Override
    protected String topBarTitle() {
        return "首页";
    }

    @Override
    protected boolean topBarIsTransparent() {
        return false;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        setHomeVpAdapter();
    }


    private void initView() {

        //获取整个的NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) homeBottomView.getChildAt(0);
        //这里就是获取所添加的每一个Tab(或者叫menu)，
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1);
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false);
        //添加到Tab上
        itemView.addView(badge);
        point = badge.findViewById(R.id.iv_bottom_point);


        BottomNavigationViewHelper.disableShiftMode(homeBottomView);
        homeVp.setOffscreenPageLimit(3);
        homeVp.setSwipeable(false);
        homeBottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        homeVp.setCurrentItem(0);
                        setTopBarIsShow(false);
                        break;

                    case R.id.bottom_order:
                        homeVp.setCurrentItem(1);
                        point.setVisibility(View.GONE);
                        setTopBarIsShow(true);
                        setTopBarIsShowBack(false);
                        setTopBarTitle("订单列表");
                        break;

                    case R.id.bottom_mine:
                        homeVp.setCurrentItem(2);
                        setTopBarIsShow(true);
                        setTopBarIsShowBack(false);
                        setTopBarTransparent(true);
                        setTopBarTitle("我的");
                        break;

                }
                return true;
            }
        });

    }


    @Override
    protected boolean topBarIsShow() {
        return false;
    }

    private void setHomeVpAdapter() {
        HomeFragment homeFragment = new HomeFragment();
        OrderFragment mddleFragment = new OrderFragment();
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
    public MainPresent newP() {
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
            getvDelegate().toastShort("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().AppExit(context);
        }
    }


    @Override
    public void getNetData() {

    }

    @Subscribe
    public void event(OrderEvent event) {
        point.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindEvent() {
        BusProvider.getBus().toFlowable(OrderEvent.class).subscribe(new Consumer<OrderEvent>() {
            @Override
            public void accept(OrderEvent event) throws Exception {
                point.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(MainActivity.class)
                .launch();
    }
}
