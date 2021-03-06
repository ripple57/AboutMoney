package com.ripple.lendmoney.http;

import com.ripple.lendmoney.model.BaseModel;
import com.ripple.lendmoney.model.TestBean;
import com.ripple.lendmoney.model.TokenBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

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
//
//
//    @FormUrlEncoded
//    @POST("api")
//    Flowable<ResponseBody> queryData(@Field("accessToken") String token,
//                                     @Field("actionName") String actionName,
//                                     @Field("payload") String payload,
//                                     @Field("timestamp") long timaStamp,
//                                     @Field("sign") String sign,
//                                     @Field("from") String from);


//    @FormUrlEncoded
//    @POST("login")
//    Flowable<LoginBean> login(@Field("phoneNum") String phoneNum,
//                              @Field("code") String code);

    @FormUrlEncoded
    @POST("inter/appuser/sendCodeForLogin.do")
    Flowable<BaseModel> getCaptcha(@Field("userName") String userName);


    @FormUrlEncoded
    @POST
    Flowable<ResponseBody> post(@Url String url, @FieldMap Map<String, Object> map);

    @GET
    Flowable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> map);

    @Multipart
    @POST()
    Flowable<ResponseBody> upload(@Url String url, @PartMap Map<String, RequestBody> map, @Part() List<MultipartBody.Part> parts);



    @Multipart
    @POST()
    Flowable<ResponseBody> upload1(@Url String url, @QueryMap Map<String, Object> map, @PartMap() Map<String, RequestBody> maps);


    @Multipart
    @POST()
    Flowable<ResponseBody> upload2(@Url String url, @QueryMap Map<String, Object> map, @Part() MultipartBody.Part file);


    @Multipart
    @POST()
    Flowable<ResponseBody> upload3(@Url String url, @FieldMap Map<String, Object> map, @Part() MultipartBody.Part file);


    @FormUrlEncoded
    @POST("inter/index/checkVersion.do")
    Flowable<TestBean> test1(@FieldMap HashMap<String, Object> map);

}
