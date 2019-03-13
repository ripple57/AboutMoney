package com.ripple.lendmoney.http;

import com.alibaba.fastjson.JSON;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.utils.ToastUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import cn.droidlover.xdroidmvp.net.NetError;

public abstract class MyCallBack<ResultType> implements HttpUtils.NetCallBack {
    @Override
    public void onSuccess(MyMessage message) {
        if (message.getState() == Constant.REQUEST_SUCCESS) {//网络请求成功
            //以下代码为了获取当前MyCallBack的定义地 泛型类型
            Type type = this.getClass().getGenericSuperclass();
            ParameterizedType p = (ParameterizedType) type;
            if (p.getActualTypeArguments()[0].toString().contains(Map.class.getName())) {
                onMySuccess((ResultType) JSON.parseObject(message.getData(), p.getActualTypeArguments()[0]), message);
            } else if (p.getActualTypeArguments()[0].toString().contains(Void.class.getName())) {
                onMySuccess(null, message);
            }else {
                onMySuccess((ResultType) JSON.parseObject(message.getBody(), p.getActualTypeArguments()[0]), message);
            }
        } else {//服务器连接成功但获取数据失败
            onMyFailure(message);
        }
    }

    @Override
    public void onError(NetError error) {//没有连接上服务器
    }

    @Override
    public void onComplete() {//网络请求完成

    }
    //服务器连接成功但数据有问题
    public void onMyFailure(MyMessage message) {
        if (message != null) {
            ToastUtil.showToast(message.getDescription());
        }
    }

    //服务器连接成功,网络请求成功
    public abstract void onMySuccess(ResultType bean, MyMessage message);

}
