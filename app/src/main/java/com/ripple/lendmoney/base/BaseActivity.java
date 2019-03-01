package com.ripple.lendmoney.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.utils.AndroidBug5497Workaround;
import com.ripple.lendmoney.utils.AndroidWorkaround;
import com.ripple.lendmoney.utils.AppManager;

import cn.droidlover.xdroidmvp.kit.KnifeKit;
import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * Created by admin on 2018/4/24.
 */

public abstract class BaseActivity<P extends IPresent> extends XActivity<P> {
    RelativeLayout rl_topbar;
    protected QMUITopBar topBar;
    QMUIEmptyView emptyView;
    RelativeLayout container;
    protected View rootView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            AndroidBug5497Workaround.assistActivity(this);//To solve the conflict of the status bar and soft keyboard
        }
        //解决华为虚拟按键被遮挡的问题=>在util自定义了AndroidWorkaround类
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) { //适配华为手机虚拟键遮挡tab的问题
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));  //需要在setContentView()方法后面执行
        }
        AppManager.getAppManager().addActivity(this);

    }

    @Override
    public void bindUI(View root) {
        super.bindUI(root);
        KnifeKit.bind(this, rootView);
    }

    public boolean isSwipe() {
        return true;
    }

    @Override
    protected void initBaseView() {
        rl_topbar = (RelativeLayout) findViewById(R.id.rl_topbar);
        topBar = (QMUITopBar) findViewById(R.id.qm_topbar);
        emptyView = (QMUIEmptyView) findViewById(R.id.empty_loading_layout);
        container = (RelativeLayout) findViewById(R.id.base_container);

        LayoutInflater inflater = LayoutInflater.from(this);
        rootView = inflater.inflate(getLayoutId(), container, true);
    }

    @Override
    protected void initTopBar() {

        if (rl_topbar == null || topBar == null) return;
        if (topBarIsShow()) {
            topBar.setTitle(topBarTitle());
            rl_topbar.setVisibility(View.VISIBLE);
            QMUIStatusBarHelper.setStatusBarDarkMode(this);//状态栏深底白字
            topBar.setBackgroundAlpha(0);//透明后跟rltopbar颜色相同
            int statusbarHeight = QMUIStatusBarHelper.getStatusbarHeight(this);//获取状态栏高度
            rl_topbar.setPadding(0, statusbarHeight, 0, 0);//tapbar下移,漏出状态栏,形成沉浸式
            if (topBarIsTransparent()) {
                rl_topbar.setBackgroundColor(getResources().getColor(R.color.transparent));
            } else {
                rl_topbar.setBackgroundColor(getResources().getColor(R.color.topbar_color));
            }
            if (topBarIsShowBack()) {
                topBar.addLeftImageButton(R.drawable.ic_keyboard_arrow_left_black_24dp, R.id.qmui_topbar_item_left_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppManager.getAppManager().finishActivity(BaseActivity.this);
                    }
                });
            }
        } else {
            rl_topbar.setVisibility(View.GONE);
        }


    }

    protected abstract String topBarTitle();

    protected void setTopBarTitle(String title) {
        if (topBar != null) {
            topBar.setTitle(title);
        }
    }

    protected boolean topBarIsTransparent() {
        return false;
    }


    protected boolean topBarIsShow() {
        return true;
    }

    protected boolean topBarIsShowBack() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }


    public void showToast(String toast) {
        getvDelegate().toastShort(toast);
    }


    public void setRetryView(NetError error) {
        if (emptyView != null) {
            emptyView.show(false, error.getMessage(), null, "点击重试", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emptyView.show(true);
                    getNetData();
                }
            });
        }
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_base;
    }

    public void hideLoading() {
        if (emptyView != null) {
            emptyView.hide();
        }
    }

    public void ToActivityFinish(Activity activity, final Class clazz) {
        Router.newIntent(activity).to(clazz).launch();
        AppManager.getAppManager().finishActivity();
    }
    public void ToActivity(Activity activity, final Class clazz) {
        Router.newIntent(activity).to(clazz).launch();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public abstract void getNetData();


}
