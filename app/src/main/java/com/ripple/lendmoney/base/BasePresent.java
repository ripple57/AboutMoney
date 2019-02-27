package com.ripple.lendmoney.base;

import cn.droidlover.xdroidmvp.mvp.IView;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * Created by admin on 2018/4/24.
 */

public class BasePresent<V extends IView> extends XPresent<V> {

//    public void refreshToken() {
//        RetrofitManager.getInstance().getApiService(URLConfig.BASE_MOVIE_URL).getHeaderData()
//                .compose(XApi.<FindHeaderBean>getApiTransformer())
//                .compose(XApi.<FindHeaderBean>getScheduler())
//                .subscribe(new ApiSubscriber<FindHeaderBean>() {
//                    @Override
//                    protected void onFail(NetError error) {
//
//                    }
//
//                    @Override
//                    protected void onSuccess(FindHeaderBean findHeaderBean) {
//
//                    }
//                });
//    }

}
