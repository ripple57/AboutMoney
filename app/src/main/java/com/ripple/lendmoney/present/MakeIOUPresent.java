package com.ripple.lendmoney.present;

import android.app.Activity;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.http.HttpUtils;
import com.ripple.lendmoney.http.MyCallBack;
import com.ripple.lendmoney.http.MyMessage;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.ui.activity.MakeIOUActivity;

import java.util.HashMap;
import java.util.Map;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/8 0:03.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class MakeIOUPresent extends BasePresent<MakeIOUActivity> {
    public void makeIou(Activity activity, String borrower, String lender, String loanAmount, String loanRate, String repaymentMethod, String loanDate) {
//        borrower 借款人
//    lender 出借人
//    loanAmount 借款金额
//    loanRate 借款利率
//    repaymentMethod 还款方式
//    loanDate 借款天数
        HashMap<String, Object> map = new HashMap<>();
        map.put("borrower", borrower);
        map.put("lender", lender);
        map.put("loanAmount", loanAmount);
        map.put("loanRate", loanRate);
        map.put("repaymentMethod", repaymentMethod);
        map.put("loanDate", loanDate);
        HttpUtils.post(activity, URLConfig.addIOUInfo, map, new MyCallBack<Map<String,String>>() {
            @Override
            public void onMySuccess(Map<String, String> bean, MyMessage message) {
                String iouid = bean.get("IOUID");
                getV().makeIouSuccess(iouid);
            }

        });
    }
}
