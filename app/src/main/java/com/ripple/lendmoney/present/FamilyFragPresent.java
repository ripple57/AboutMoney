package com.ripple.lendmoney.present;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.ui.fragment.FamilyFragment;

import java.util.HashMap;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:28.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class FamilyFragPresent extends BasePresent<FamilyFragment> {
    public void uploadFamilyInfo(HashMap<String, String> map) {
        getV().uploadSuccess();
    }
}
