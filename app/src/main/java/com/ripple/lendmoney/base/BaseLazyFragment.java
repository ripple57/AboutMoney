package com.ripple.lendmoney.base;

import android.view.View;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.ripple.lendmoney.R;

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.net.NetError;

public abstract class BaseLazyFragment<P extends IPresent> extends XLazyFragment<P> {
    protected QMUIEmptyView emptyView;

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        emptyView = (QMUIEmptyView) rootView.findViewById(R.id.empty_loading_layout);
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

    public void setTopBarTitle(String title) {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.setTopBarTitle(title);
    }

    public void hideLoading() {
        if (emptyView != null) {
            emptyView.hide();
        }
    }

    public abstract void getNetData();


}
