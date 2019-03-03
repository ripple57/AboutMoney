package com.ripple.lendmoney.http;

import android.app.Activity;
import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.ui.activity.LoginActivity;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.ToastUtil;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.reactivestreams.Publisher;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.net.XApi;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/4/11.
 */

public class HttpUtils {

    private static final String SOCKETTIMEOUTEXCEPTION = "网络连接超时，请稍后重试";
    private static final String CONNECTEXCEPTION = "服务器忙，请稍后重试";
    private static final String UNKNOWNHOSTEXCEPTION = "网络异常，请检查您的网络状态";
    private static final int GET = 0;
    private static final int POST = 1;

    private ApiService apiService;

    public ApiService getGankService(String BASE_URL) {
        if (apiService == null) {
            synchronized (HttpUtils.class) {
                if (apiService == null) {
                    apiService = XApi.getInstance().getRetrofit(BASE_URL + "/", true).create(ApiService.class);
                }
            }
        }
        return apiService;
    }


    /**
     * HttpUtil实例
     */
    private static HttpUtils INSTANCE;

    /**
     * 获取HttpUtil实例 ,单例模式
     */
    public static HttpUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpUtils();
                }
            }
        }
        return INSTANCE;
    }

    public static void post(Context context, String url, HashMap<String, Object> map, NetCallBack callBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("sessionId", GlobleParms.sessionId);
        getInstance().netMethod(context, url, map, callBack, POST);
    }

    public static void postDialog(Context context, String url, HashMap<String, Object> map, NetCallBack callBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("sessionId", GlobleParms.sessionId);
        getInstance().netMethodDialog(context, url, map, callBack, POST);
    }

    public static void get(Context context, String url, HashMap<String, Object> map, NetCallBack callBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("sessionId", GlobleParms.sessionId);
        getInstance().netMethod(context, url, map, callBack, GET);
    }

    public static void getDialog(Context context, String url, HashMap<String, Object> map, NetCallBack callBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("sessionId", GlobleParms.sessionId);
        getInstance().netMethodDialog(context, url, map, callBack, GET);
    }

    // TODO: 2019/2/28  上传文件需要测试
    public static void upload(Context context, String url, HashMap<String, Object> map, File file, NetCallBack callBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("sessionId", GlobleParms.sessionId);
        getInstance().uploadFile(context, url, map, file, callBack);
    }

    private void uploadFile(Context context, String url, Map<String, Object> map, File file, final NetCallBack callBack) {
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setTipWord("上传中")
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
        tipDialog.setCancelable(true);
        tipDialog.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), requestFile);

        getGankService(URLConfig.BASE_URL).upload(url, map, body)
                .retryWhen(new RetryWithDelay(3, 1000, context))
                .map(new Function<ResponseBody, String>() { //数据转换
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        String response = responseBody.string();
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((LifecycleProvider) context)
                        .bindToLifecycle())
                .subscribe(new ResourceSubscriber<String>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        if (tipDialog != null) {
                            tipDialog.show();
                        }
                    }

                    @Override
                    public void onNext(String string) {
                        if (tipDialog != null) {
                            tipDialog.dismiss();
                        }
                        LogUtils.e("Rx网络成功" + string);
                        MyMessage message = new MyMessage(string);//封装json数据为实例对象
                        if (message.getState() == Constant.REQUEST_NEED_LOGIN) {//网络请求成功
                            if (context instanceof Activity) {
                                LoginActivity.launch((Activity) context,true);
                            }
                        } else {
                            callBack.onSuccess(message);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (tipDialog != null) {
                            tipDialog.dismiss();
                        }
                        LogUtils.e("Rx网络错误" + t.toString());
                        if (t instanceof SocketTimeoutException) {
                            ToastUtil.showToast(SOCKETTIMEOUTEXCEPTION);
                        } else if (t instanceof ConnectException) {
                            ToastUtil.showToast(CONNECTEXCEPTION);
                        } else if (t instanceof UnknownHostException) {
                            ToastUtil.showToast(UNKNOWNHOSTEXCEPTION);
                        } else {
                            ToastUtil.showToast("网络数据异常");
                        }
                        callBack.onFailed(t);
                    }

                    @Override
                    public void onComplete() {
                        callBack.onComplete();
                    }

                });
    }

    /**
     * 查询网络
     */
    private void netMethod(final Context context, final String url, final Map<String, Object> map,
                           final NetCallBack callBack, int netType) {

        //生成网络观察者
        Flowable<ResponseBody> flowable = null;
        switch (netType) {
            case GET:
                flowable = getGankService(URLConfig.BASE_URL).get(url, map);
                break;
            case POST:
                flowable = getGankService(URLConfig.BASE_URL).post(url, map);
                break;
        }
        flowable.retryWhen(new RetryWithDelay(3, 1000, context))
                .map(new Function<ResponseBody, String>() { //数据转换
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        String response = responseBody.string();
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((LifecycleProvider) context)
                        .bindToLifecycle())
                .subscribe(new ResourceSubscriber<String>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onNext(String string) {
                        LogUtils.e("Rx网络成功" + string);
                        MyMessage message = new MyMessage(string);//封装json数据为实例对象
                        if (message.getState() == Constant.REQUEST_NEED_LOGIN) {//网络请求成功
                            if (context instanceof Activity) {
                                LoginActivity.launch((Activity) context,true);
                            }
                        } else {
                            callBack.onSuccess(message);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e("Rx网络错误" + t.toString());
                        if (t instanceof SocketTimeoutException) {
                            ToastUtil.showToast(SOCKETTIMEOUTEXCEPTION);
                        } else if (t instanceof ConnectException) {
                            ToastUtil.showToast(CONNECTEXCEPTION);
                        } else if (t instanceof UnknownHostException) {
                            ToastUtil.showToast(UNKNOWNHOSTEXCEPTION);
                        } else {
                            ToastUtil.showToast("网络数据异常");
                        }
                        callBack.onFailed(t);
                    }

                    @Override
                    public void onComplete() {
                        callBack.onComplete();
                    }

                });


    }

    /**
     * 查询网络带dialog
     */
    private void netMethodDialog(final Context context, final String url, final Map<String, Object> map,
                                 final NetCallBack callBack, int netType) {

        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setTipWord("加载中")
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
        tipDialog.setCancelable(true);
        //生成网络观察者
        Flowable<ResponseBody> flowable = null;
        switch (netType) {
            case GET:
                flowable = getGankService(URLConfig.BASE_URL).get(url, map);
                break;
            case POST:
                flowable = getGankService(URLConfig.BASE_URL).post(url, map);
                break;
        }
        flowable.retryWhen(new RetryWithDelay(3, 1000, context))
                .map(new Function<ResponseBody, String>() { //数据转换
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        String response = responseBody.string();
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((LifecycleProvider) context)
                        .bindToLifecycle())
                .subscribe(new ResourceSubscriber<String>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        if (tipDialog != null) {
                            tipDialog.show();
                        }
                    }

                    @Override
                    public void onNext(String string) {
                        if (tipDialog != null) {
                            tipDialog.dismiss();
                        }
                        LogUtils.e("Rx网络成功" + string);
                        MyMessage message = new MyMessage(string);//封装json数据为实例对象
                        if (message.getState() == Constant.REQUEST_NEED_LOGIN) {//网络请求成功
                            if (context instanceof Activity) {
                                LoginActivity.launch((Activity) context,true);
                            }
                        } else {
                            callBack.onSuccess(message);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (tipDialog != null) {
                            tipDialog.dismiss();
                        }
                        LogUtils.e("Rx网络错误" + t.toString());
                        if (t instanceof SocketTimeoutException) {
                            ToastUtil.showToast(SOCKETTIMEOUTEXCEPTION);
                        } else if (t instanceof ConnectException) {
                            ToastUtil.showToast(CONNECTEXCEPTION);
                        } else if (t instanceof UnknownHostException) {
                            ToastUtil.showToast(UNKNOWNHOSTEXCEPTION);
                        } else {
                            ToastUtil.showToast("网络数据异常");
                        }
                        callBack.onFailed(t);
                    }

                    @Override
                    public void onComplete() {
                        callBack.onComplete();
                    }

                });


    }

    public interface NetCallBack {
        void onSuccess(MyMessage message);

        void onFailed(Throwable t);

        void onComplete();
    }

///////////////////////////////////////////////////////////////////////////
// 以下是原来项目的网络请求工具
///////////////////////////////////////////////////////////////////////////

//    /**
//     * @param context
//     * @param showDialog
//     * @param json
//     * @param url
//     * @param actionName
//     * @param callBack
//     */
//    public void getNetData(Context context, boolean showDialog, JSONObject json, String url, String actionName, NetCallBack callBack) {
//        if (showDialog) {
//            queryNetDataDialog(context, false, json, url, actionName, callBack);
//        } else {
//            queryNetData(context, false, json, url, actionName, callBack);
//        }
//    }
//
//    public void getNetDataCache(Context context, boolean showDialog, JSONObject json, String url, String actionName, NetCallBack callBack) {
//        if (showDialog) {
//            queryNetDataDialog(context, true, json, url, actionName, callBack);
//        } else {
//            queryNetData(context, true, json, url, actionName, callBack);
//        }
//    }
//
//
//    /**
//     * 查询网络
//     */
//    private void queryNetData(final Context context, final boolean isSave, final JSONObject json, final String url, final String actionName, final NetCallBack callBack) {
//        String token = SPUtils.getString(context, "token", "accessToken");
//        String key = SPUtils.getString(context, "token", "secretKey");
//        final String jsonString = json.toJSONString();
//        long timeStamp = System.currentTimeMillis();
//        final String sign = StringUtils.encryptToSHA(token + actionName + jsonString + timeStamp + key);
//        //生成网络观察者
//        Flowable<String> netFlowable = getGankService(url).queryData(token, actionName, jsonString, timeStamp, sign, "")
//                .retryWhen(new RetryWithDelay(3, 3000, context))
//                .map(new Function<ResponseBody, String>() { //数据转换
//                    @Override
//                    public String apply(ResponseBody responseBody) throws Exception {
//                        String response = responseBody.string();
//                        try {
//                            BaseModel baseModel = JSONObject.parseObject(response, BaseModel.class);
//                            if (isSave && baseModel != null && baseModel.getState() == 0) { //此处存放缓存的时候需要做个验证，否则不正确的网络响应也会存进来
//                                DiskCache.getInstance(context).put(actionName + jsonString, response);//缓存存放
//                            }
//                        } catch (Exception e) {
//
//                        }
//
//                        return response;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//        Flowable<String> loadDataFlowable = RetrofitCache.load(context, actionName + jsonString, netFlowable, isSave);
//        loadDataFlowable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(((LifecycleProvider) context).bindToLifecycle())
//                .subscribe(new ResourceSubscriber<String>() {
//                    @Override
//                    protected void onStart() {
//                        super.onStart();
//                    }
//
//                    @Override
//                    public void onNext(String response) {
//                        try {
//                            LogUtils.e("网络响应RX", response);
//                            try {
//                                BaseModel baseModel = JSONObject.parseObject(response, BaseModel.class);
//                                if (baseModel != null && baseModel.getState() == 0) {
//                                    callBack.onSuccess(response);
//                                } else if (baseModel != null && baseModel.getState() == -1) {
//                                    getToken(context, json, url, actionName, callBack, isSave);
//                                }
//                            } catch (Exception e) {
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        LogUtils.e("网络错误RX", t.toString());
//                        if (t instanceof SocketTimeoutException) {
//                            ToastUtil.showToast(SOCKETTIMEOUTEXCEPTION);
//                        } else if (t instanceof ConnectException) {
//                            ToastUtil.showToast(CONNECTEXCEPTION);
//                        } else if (t instanceof UnknownHostException) {
//                            ToastUtil.showToast(UNKNOWNHOSTEXCEPTION);
//                        } else {
//                            ToastUtil.showToast("网络数据异常");
//                        }
//                        callBack.onFailed(t);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                });
//    }
//
//
//    /**
//     * 查询网络DIalog
//     */
//    private void queryNetDataDialog(final Context context, final boolean isSave, final JSONObject json, final String url, final String actionName, final NetCallBack callBack) {
//        String token = SPUtils.getString(context, "token", "accessToken");
//        String key = SPUtils.getString(context, "token", "secretKey");
//        final String jsonString = json.toJSONString();
//        long timeStamp = System.currentTimeMillis();
//        final String sign = StringUtils.encryptToSHA(token + actionName + jsonString + timeStamp + key);
//        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
//                .setTipWord("加载中")
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .create();
//        tipDialog.setCancelable(true);
//        //生成网络观察者
//        Flowable<String> netFlowable = getGankService(url).queryData(token, actionName, jsonString, timeStamp, sign, "")
//                .retryWhen(new RetryWithDelay(3, 3000, context))
//                .map(new Function<ResponseBody, String>() { //数据转换
//                    @Override
//                    public String apply(ResponseBody responseBody) throws Exception {
//                        String response = responseBody.string();
//                        try {
//                            BaseModel baseModel = JSONObject.parseObject(response, BaseModel.class);
//                            if (isSave && baseModel != null && baseModel.getState() == 0) { //此处存放缓存的时候需要做个验证，否则不正确的网络响应也会存进来
//                                DiskCache.getInstance(context).put(actionName + jsonString, response);//缓存存放
//                            }
//                        } catch (Exception e) {
//                            System.out.println("error" + e.toString());
//                        }
//                        return response;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//        Flowable<String> loadDataFlowable = RetrofitCache.load(context, actionName + jsonString, netFlowable, isSave);
//        loadDataFlowable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(((LifecycleProvider) context).bindToLifecycle())
//                .subscribe(new ResourceSubscriber<String>() {
//                    @Override
//                    protected void onStart() {
//                        super.onStart();
//                        if (tipDialog != null) {
//                            tipDialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(String response) {
//                        try {
//                            if (tipDialog != null) {
//                                tipDialog.dismiss();
//                            }
//                            LogUtils.e("网络响应RX", response);
//                            try {
//                                BaseModel baseModel = JSONObject.parseObject(response, BaseModel.class);
//                                if (baseModel != null && baseModel.getState() == 0) {
//                                    callBack.onSuccess(response);
//                                } else if (baseModel != null && baseModel.getState() == -1) {
//                                    getToken(context, json, url, actionName, callBack, isSave);
//                                }
//                            } catch (Exception e) {
//
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        if (tipDialog != null) {
//                            tipDialog.dismiss();
//                        }
//                        LogUtils.e("网络错误RX", t.toString());
//                        if (t instanceof SocketTimeoutException) {
//                            ToastUtil.showToast(SOCKETTIMEOUTEXCEPTION);
//                        } else if (t instanceof ConnectException) {
//                            ToastUtil.showToast(CONNECTEXCEPTION);
//                        } else if (t instanceof UnknownHostException) {
//                            ToastUtil.showToast(UNKNOWNHOSTEXCEPTION);
//                        } else {
//                            ToastUtil.showToast("网络数据异常");
//                        }
//                        callBack.onFailed(t);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                });
//    }
//
//
//    public void getToken(final Context context, final JSONObject json, final String url, final String actionName, final NetCallBack callBack, final boolean isSave) {
//        getGankService(URLConfig.BASE_API_URL).getToken("1", "111")
//                .compose(XApi.<TokenBean>getApiTransformer())
//                .compose(XApi.<TokenBean>getScheduler())
//                .compose(((LifecycleProvider) context).bindToLifecycle())
//                .subscribe(new ApiSubscriber<TokenBean>() {
//                    @Override
//                    protected void onFail(NetError error) {
//
//                    }
//
//                    @Override
//                    protected void onSuccess(TokenBean tokenBean) {
//                        LogUtils.e("获取RxToken", tokenBean.getAccessToken() + ">>><<<" + tokenBean.getSecretKey());
//                        SPUtils.setString(context, "token", "accessToken", tokenBean.getAccessToken());
//                        SPUtils.setString(context, "token", "secretKey", tokenBean.getSecretKey());
//                        if (isSave) {
//                            queryNetData(context, true, json, url, actionName, callBack);
//                            LogUtils.e("缓存Token");
//                        } else {
//                            queryNetData(context, false, json, url, actionName, callBack);
//                            LogUtils.e("正常Token");
//                        }
//                    }
//                });
//    }
//


    /**
     * 错误重试机制
     */
    public class RetryWithDelay implements Function<Flowable<? extends Throwable>, Flowable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;
        private Context context;

        public RetryWithDelay(int maxRetries, int retryDelayMillis, Context context) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
            this.context = context;
        }

        @Override
        public Flowable<?> apply(Flowable<? extends Throwable> flowable) throws Exception {
            return flowable
                    .flatMap(new Function<Throwable, Publisher<?>>() {
                        @Override
                        public Publisher<?> apply(Throwable throwable) throws Exception {
                            if (++retryCount <= maxRetries && Kits.NetWork.hasNetwork(context)) {
                                return Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                            }
                            return Flowable.error(throwable);
                        }
                    });
        }
    }
    /***************新网络通信模式，主要用于电影模块****************************************************/


}
