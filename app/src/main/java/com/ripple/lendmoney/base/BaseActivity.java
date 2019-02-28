package com.ripple.lendmoney.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by admin on 2018/4/24.
 */

public abstract class BaseActivity<P extends IPresent> extends XActivity<P> {

    private RelativeLayout rl_topbar;
    protected QMUITopBar topBar;
    private QMUIEmptyView emptyView;


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


    public boolean isSwipe() {
        return true;
    }


    @Override
    protected void initTopBar() {
        rl_topbar = (RelativeLayout) findViewById(R.id.rl_topbar);
        topBar = (QMUITopBar) findViewById(R.id.qm_topbar);
        emptyView = (QMUIEmptyView) findViewById(R.id.empty_loading_layout);

        if (rl_topbar == null) return;
        if (isShowTopBar()) {
            rl_topbar.setVisibility(View.VISIBLE);
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
            rl_topbar.setBackgroundColor(getResources().getColor(R.color.topbar_color));
            int statusbarHeight = QMUIStatusBarHelper.getStatusbarHeight(this);
            rl_topbar.setPadding(0, statusbarHeight, 0, 0);
        } else {
            rl_topbar.setVisibility(View.GONE);
        }

        if (isShowBack()) {
            topBar.addLeftImageButton(R.drawable.ic_keyboard_arrow_left_black_24dp, R.id.qmui_topbar_item_left_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppManager.getAppManager().finishActivity(BaseActivity.this);
                }
            });
        }
    }


    private boolean isShowTopBar() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }


    protected boolean isShowBack() {
        return true;
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


    public void hideLoading() {
        if (emptyView != null) {
            emptyView.hide();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    public abstract void getNetData();


}
