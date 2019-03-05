package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.SuggestPresent;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class SuggestActivity extends BaseActivity<SuggestPresent> {

    @BindView(R.id.et_suggestact_suggest)
    EditText etSuggestactSuggest;
    @BindView(R.id.btn_suggestact_commit)
    Button btnSuggestactCommit;

    @Override
    protected String topBarTitle() {
        return "意见反馈";
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_suggest;
    }

    @Override
    public SuggestPresent newP() {
        return new SuggestPresent();
    }


    @OnClick(R.id.btn_suggestact_commit)
    public void onViewClicked() {
        String suggest = etSuggestactSuggest.getText().toString().trim();
        if (TextUtils.isEmpty(suggest)) {
            ToastUtil.showToast("请填写您的问题或意见后,再进行提交!");
        } else {
            getP().commitSuggest(suggest);
        }
    }

    public void commitSuccess() {
        ToastUtil.showToast("对于您所提交的问题,我们将尽快进行处理!");
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(SuggestActivity.class)
                .launch();
    }
}
