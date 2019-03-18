package com.ripple.lendmoney.base;

import android.app.Application;
import android.content.Context;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.utils.CrashHandler;

import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import me.jessyan.autosize.AutoSizeConfig;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by admin on 2018/4/12.
 */

public class MyApp extends Application {

    /**
     * 全局的上下文.
     */
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        GlobleParms.debug = true;
        GlobleParms.familyTest = true;










        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true);
        registerProvider();
//                LeakCanary.install(this);//检查内心泄露
        XDroidConf.devMode(GlobleParms.debug);
        XDroidConf.LOG = GlobleParms.debug;
        XDroidConf.IL_ERROR_RES = R.mipmap.img_default;
        XDroidConf.IL_LOADING_RES = R.mipmap.img_default;
//        Realm.init(this);//数据库操作
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
        if (GlobleParms.debug) {

        } else {
            CrashHandler.getInstance().init(this);
        }

    }


    /**
     * 获取Context.
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 注册网络框架
     */
    private void registerProvider() {
        XApi.registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }


}
