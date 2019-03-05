package com.ripple.lendmoney.present;

import com.ripple.lendmoney.base.BasePresent;
import com.ripple.lendmoney.ui.fragment.MineFragment;

public class MinePresent extends BasePresent<MineFragment> {

    public void userExit() {
        getV().exitSuccess();
    }
}
