package com.ripple.lendmoney.http;

import com.ripple.lendmoney.model.BaseModel;
import com.ripple.lendmoney.model.LoginBean;
import com.ripple.lendmoney.model.TestBean;
import com.ripple.lendmoney.model.TokenBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by admin on 2018/4/11.
 */

public interface ApiService {
//    @FormUrlEncoded
//    @POST("api")
//    Flowable<BannerBean> queryHomeData(@Field("accessToken") String token,
//                                       @Field("actionName") String actionName,
//                                       @Field("payload") String payload,
//                                       @Field("timestamp") long timaStamp,
//                                       @Field("sign") String sign,
//                                       @Field("from") String from);

    @FormUrlEncoded
    @POST("get-access-token")
    Flowable<TokenBean> getToken(@Field("mobileType") String type,
                                 @Field("mobileId") String mobileID);


    @FormUrlEncoded
    @POST("api")
    Flowable<ResponseBody> queryData(@Field("accessToken") String token,
                                     @Field("actionName") String actionName,
                                     @Field("payload") String payload,
                                     @Field("timestamp") long timaStamp,
                                     @Field("sign") String sign,
                                     @Field("from") String from);


    @FormUrlEncoded
    @POST("login")
    Flowable<LoginBean> login(@Field("phoneNum") String phoneNum,
                              @Field("code") String code);

    @FormUrlEncoded
    @POST("inter/appuser/sendCodeForLogin.do")
    Flowable<BaseModel> getCaptcha(@Field("userName") String userName);

    //    {
//        body: { },
//        des: "成功",
//        state: 0
//    }

    @FormUrlEncoded
    @POST("inter/index/{url}")
    Flowable<ResponseBody> post(@Path("url") String url, @FieldMap Map<String, Object> map);

    @GET("inter/index/{url}")
    Flowable<ResponseBody> get(@Path("url") String url, @QueryMap Map<String, Object> map);


//    @GET("inter/index/checkVersion.do")
//    Flowable<ResponseBody> post(@Path("url") String url,
//                                @QueryMap Map<String, Object> map);
//    @GET("inter/{url}")
//    Flowable<ResponseBody> post(@Path("url") String url,
//                                @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("inter/index/checkVersion.do")
    Flowable<TestBean> test1(@FieldMap HashMap<String, Object> map);

}
