package com.ripple.lendmoney.present;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.ui.fragment.ContactsFragment;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:21.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class ContactsFragPresent extends BasePresent<ContactsFragment> {
    public void uploadContacts(String jsonString) {
        getV().uploadSuccess();
    }
}
