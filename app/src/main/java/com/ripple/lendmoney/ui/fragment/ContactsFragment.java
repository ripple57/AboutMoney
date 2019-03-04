package com.ripple.lendmoney.ui.fragment;

import android.os.Bundle;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.present.ContactsFragPresent;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:21.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class ContactsFragment extends BaseLazyFragment<ContactsFragPresent> {
    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_idcard_layout;
    }

    @Override
    public ContactsFragPresent newP() {
        return null;
    }
}
