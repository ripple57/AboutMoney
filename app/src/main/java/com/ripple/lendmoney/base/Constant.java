package com.ripple.lendmoney.base;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/2 21:37.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public final class Constant {

    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_NEED_LOGIN = -106;
    public static final String PARAM_URL = "url";
    public static final String PARAM_TITLE = "title";
    public static final String SOCKETTIMEOUTEXCEPTION = "网络连接超时，请稍后重试";
    public static final String CONNECTEXCEPTION = "跟服务器连接异常,请稍后再试";
    public static final String UNKNOWNHOSTEXCEPTION = "网络异常，请检查您的网络状态";
    public static final String OTHEREXCEPTION = "出现未知错误";
    public static final String PARAM_FRAGMENTTYPE = "fragmentType";
    public static final String REG_IDCARD = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
    public static final String REG_PHONE = "^1[345789]\\d{9}$";
    public static final int TYPE_IDCARDFRAG = 0;
    public static final int TYPE_FAMILYFRAG = 1;
    public static final int TYPE_BANKCARDFRAG = 2;
    public static final int TYPE_CREDITFRAG = 3;
    public static final int TYPE_CONTACTSFRAG = 4;
    public static final String SESSIONID = "sessionId";
    public static final String USERNAME = "userName";
    public static final String USERID = "userId";
}
