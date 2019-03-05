package com.ripple.lendmoney.present;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.ui.activity.SuggestActivity;

public class SuggestPresent extends BasePresent<SuggestActivity> {
    public void commitSuggest(String suggest) {
        getV().commitSuccess();
    }
}
