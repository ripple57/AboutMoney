package com.ripple.lendmoney.http;

/**
 * 网络请求地址静态数据类
 * Created by Administrator on 2016-06-16.
 */
public class URLConfig {
    //    public static final String BASE_URL = "http://123.56.100.221:8088/";//e患者说服务器
    public static final String BASE_URL = "http://192.168.1.111/";//测试服务器
//    public static final String BASE_URL = "http://alongshow.6655.la/";//测试服务器


    public static final String REGIST_AGREEMENT = BASE_URL + "/h5/loginProtocol.do";
    public static final String LEND_AGREEMENT = BASE_URL + "/h5/IOUProtocol.do";


    //    userName	手机号   有简单的加密，和血压计一样，好像是去掉第一位*8给我的
    public static final String sendCodeForLogin = "/inter/appuser/sendCodeForLogin.do";//发送登录验证码

    //    sessionId
//    userName 手机号
//    valiCode 验证码
//    lon 经度
//    lat 纬度
//    location 定位信息
    public static final String appLogin = "/inter/appuser/appLogin.do";//发送登录验证码,app验证登录

    //sessionId
    public static final String sessionLogin = "/inter/appuser/sessionLogin.do";//session登录 这个好像是自动登录用的
    //sessionId  userId
    public static final String appUserExit = "/inter/appuser/appUserExit.do";//退出


    //    sessionId
    //feedback 反馈内容
    public static final String recordAppFeedback = "/inter/appuser/recordAppFeedback.do";//我的资料记录反馈

    public static final String index = "/inter/index/index.do";// 首页 返回登录的天数和30个随机通知
    //视频文件流
    public static final String addFaceVideo = "/inter/appcustomerinfo/addFaceVideo.do";//人脸识别
    //realName 真实姓名
    //idNumber 身份证ID
    //2个照片文件流
    public static final String addIDCard = "/inter/appcustomerinfo/addIDCard.do";//身份证信息
    //    directKinship 亲属关系
//    kinshipName 亲属姓名
//    kinshipPhone 亲属电话
//    urgentContact 联系人关系
//    contactName 联系人姓名
//    contactPhone 联系人电话
    public static final String addContact = "/inter/appcustomerinfo/addContact.do";//紧急联系人
    //    bankCardNumber 银行卡号
//    openingBank 开户行
//    reservePhone 预留手机
    public static final String addBankCard = "/inter/appcustomerinfo/addBankCard.do";//银行卡信息系
    //2个文件图片流
    public static final String addAlipayCreditImg = "/inter/appcustomerinfo/addAlipayCreditImg.do";//芝麻信用
    // telephoneList json
    public static final String addTelephoneList = "/inter/appcustomerinfo/addTelephoneList.do";//获取通讯录

    public static final String getCustomerInfo = "/inter/appcustomerinfo/getCustomerInfo.do";//获取客户资料 返回所有客户所有资料信息
    //    borrower 借款人
//    lender 出借人
//    loanAmount 借款金额
//    loanRate 借款利率
//    repaymentMethod 还款方式
//    loanDate 借款天数
    public static final String addIOUInfo = "/inter/appiouinfo/addIOUInfo.do";//增加借条订单 后台只要基础数据, 算法只作为展示

    public static final String getIOUInfoList = "/inter/appiouinfo/getIOUInfoList.do";//查看订单列表   返回LIST


}

