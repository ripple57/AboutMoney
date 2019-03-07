package com.ripple.lendmoney.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.Constant;

import java.util.List;

public class FamilyFragAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public FamilyFragAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (Constant.TYPE_DIRECT_RELATIVES == item) {
            helper.setText(R.id.tv_familyItem_type, "直系亲属").setText(R.id.tv_familyItem_relation_show, "亲属姓名");
        } else if (Constant.TYPE_EMERGENCY_CONTACT == item) {
            helper.setText(R.id.tv_familyItem_type, "紧急联系人").setText(R.id.tv_familyItem_relation_show, "联系人姓名");
        }
        helper.addOnClickListener(R.id.tv_familyItem_relation);
    }
}
