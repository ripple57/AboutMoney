package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.http.URLConfig;
import com.ripple.lendmoney.present.AssessPresent;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class AssessActivity extends BaseActivity<AssessPresent> {

    @BindView(R.id.iv_assessAct_banner)
    ImageView ivAssessActBanner;
    @BindView(R.id.iv_assessAct_checkAgreement)
    ImageView ivAssessActCheckAgreement;
    @BindView(R.id.tv_assessAct_agreement)
    TextView tvAssessActAgreement;
    @BindView(R.id.tv_assessAct_feeIntroduce)
    TextView tvAssessActFeeIntroduce;
    @BindView(R.id.tv_assessAct_nowPrice)
    TextView tvAssessActNowPrice;
    @BindView(R.id.tv_assessAct_prePrice)
    TextView tvAssessActPrePrice;
    @BindView(R.id.btn_assessAct_applyLend)
    Button btnAssessActApplyLend;
    @BindString(R.string.renzhengfeiyong_introduce)
    String introduce;

    public static void launch(Activity activity) {
        Router.newIntent(activity).to(AssessActivity.class).launch();
    }

    @Override
    protected String topBarTitle() {
        return "智能评估";
    }

    @Override
    protected boolean topBarIsTransparent() {
        return true;
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        SpannableString spannableString = new SpannableString(introduce);
//        //0 第一行缩进像素 , SizeUtils.dp2px(15)非第一行缩进像素
//        LeadingMarginSpan.Standard what = new LeadingMarginSpan.Standard(0, Kits.Dimens.dpToPxInt(context, 20));
//        spannableString.setSpan(what, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
//        tvAssessActFeeIntroduce.setText(introduce);
//        autoSplitText(tvAssessActFeeIntroduce,"1.");
        tvAssessActFeeIntroduce.setText(autoSplitText(tvAssessActFeeIntroduce,"1."));
    }
    private String autoSplitText(final TextView tv, final String indent) {
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

        //将缩进处理成空格
        String indentSpace = "";
        float indentWidth = 0;
        if (!TextUtils.isEmpty(indent)) {
            float rawIndentWidth = tvPaint.measureText(indent);
            if (rawIndentWidth < tvWidth) {
                while ((indentWidth = tvPaint.measureText(indentSpace)) < rawIndentWidth) {
                    indentSpace += " ";
                }
            }
        }

        //将原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    //从手动换行的第二行开始，加上悬挂缩进
                    if (lineWidth < 0.1f && cnt != 0) {
                        sbNewText.append(indentSpace);
                        lineWidth += indentWidth;
                    }
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_assess;
    }

    @Override
    public AssessPresent newP() {
        return new AssessPresent();
    }


    @OnClick({R.id.iv_assessAct_checkAgreement, R.id.tv_assessAct_agreement, R.id.btn_assessAct_applyLend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_assessAct_checkAgreement:
                ToastUtil.showToast("申请借款");
                break;
            case R.id.tv_assessAct_agreement:
                WebActivity.launch(this, URLConfig.LEND_AGREEMENT, "借款协议");
                break;
            case R.id.btn_assessAct_applyLend:
                ToastUtil.showToast("申请借款");
                break;
        }
    }

}
