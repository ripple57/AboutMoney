package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.WebPresent;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xstatecontroller.XStateController;

/**
 * Created by wanglei on 2016/12/31.
 */

public class WebActivity extends BaseActivity<WebPresent> {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.contentLayout)
    XStateController contentLayout;

    String title;
    String url;

    public static final String PARAM_URL = "url";
    public static final String PARAM_TITLE = "title";


    @Override
    public void initData(Bundle savedInstanceState) {
        url = getIntent().getStringExtra(PARAM_URL);
        title = getIntent().getStringExtra(PARAM_TITLE);

        initContentLayout();
        initRefreshLayout();
        initWebView();
    }

    private void initContentLayout() {
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
            }
        });

    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (contentLayout != null)
                        contentLayout.showContent();
                    if (webView != null)
                        url = webView.getUrl();
                } else {
                    if (contentLayout != null)
                        contentLayout.showLoading();
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);

        webView.loadUrl(url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected String topBarTitle() {
        return title;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }

    @Override
    public void getNetData() {

    }

    public static void launch(Activity activity, String url, String title) {
        Router.newIntent(activity)
                .to(WebActivity.class)
                .putString(PARAM_URL, url)
                .putString(PARAM_TITLE, title)
                .launch();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public WebPresent newP() {
        return null;
    }
    // TODO: 2019/3/1 标题设置   topbar返回键功能    错误显示  动态更改标题
}
