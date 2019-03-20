package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.present.WebPresent;
import com.ripple.lendmoney.utils.LogUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * Created by wanglei on 2016/12/31.
 */

public class WebActivity extends BaseActivity<WebPresent> {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    String title;
    String url;


    @Override
    public void initData(Bundle savedInstanceState) {
        url = getIntent().getStringExtra(Constant.PARAM_URL);
        title = getIntent().getStringExtra(Constant.PARAM_TITLE);
        setTopBarTitle(title);
        initRefreshLayout();
        initWebView();
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
                LogUtils.e("加载的网页地址:  "+url);
            }
        });

    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url1) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
                setTopBarTitle(view.getTitle());
                if (webView != null)
                    url = webView.getUrl();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
                    error) {
                super.onReceivedError(view, request, error);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

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
                .putString(Constant.PARAM_URL, url)
                .putString(Constant.PARAM_TITLE, title)
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
