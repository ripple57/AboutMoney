package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.event.OrderEvent;
import com.ripple.lendmoney.present.MakeIOUPresent;
import com.ripple.lendmoney.utils.SPUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusFactory;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.router.Router;

public class MakeIOUActivity extends BaseActivity<MakeIOUPresent> {

    @BindView(R.id.tv_makeIou_max)
    TextView tvMakeIouMax;
    @BindView(R.id.tv_makeIou_borrower)
    TextView tvMakeIouBorrower;
    @BindView(R.id.et_makeIou_lendNum)
    EditText etMakeIouLendNum;
    @BindView(R.id.tv_makeIou_repayMethod)
    TextView tvMakeIouRepayMethod;
    @BindView(R.id.tv_makeIou_lendDays)
    TextView tvMakeIouLendDays;
    @BindView(R.id.tv_makeIou_interest)
    TextView tvMakeIouInterest;
    @BindView(R.id.tv_makeIou_computeRealNums)
    TextView tvMakeIouComputeRealNums;
    @BindView(R.id.tv_makeIou_realNums)
    TextView tvMakeIouRealNums;
    @BindView(R.id.tv_makeIou_computeAll)
    TextView tvMakeIouComputeAll;
    @BindView(R.id.tv_makeIou_repayAllNums)
    TextView tvMakeIouRepayAllNums;
    @BindView(R.id.tv_makeIou_repayDate)
    TextView tvMakeIouRepayDate;
    @BindView(R.id.tv_makeIou_repayAllNums1)
    TextView tvMakeIouRepayAllNums1;
    @BindView(R.id.tv_makeIou_lendAgreement)
    TextView tvMakeIouLendAgreement;
    @BindView(R.id.btn_makeIou_makeIou)
    Button btnMakeIouMakeIou;
    @BindArray(R.array.lendDays)
    String[] lendDays;
    @BindArray(R.array.lendInterests)
    String[] lendInterests;

    @BindColor(R.color.text_black)
    int text_black;
    private String borrower;// borrower 借款人
    private int lendDay = 0;
    private float lendInterest = 0f;
    private int lendNum = 0;

    @Override
    protected String topBarTitle() {
        return "打借条";
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        borrower = SPUtils.getInstance(this).getValue(Constant.REALNAME, "");
        if (TextUtils.isEmpty(borrower)) {
            ToastUtil.showToast("请先前往我的资料进行身份认证");
            FaceRecognitionActivity.launch(this);
        } else {
            tvMakeIouBorrower.setText(borrower);
        }
        initListener();

    }

    private void initListener() {
        //添加文本变化的监听
        etMakeIouLendNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    lendNum = 0;
                } else {
                    try {
                        lendNum = Integer.parseInt(s.toString());
                    } catch (NumberFormatException e) {
                        ToastUtil.showToast("请输入整数!");
                    }
                }
                changeCardShow();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //修改卡片显示内容
    private void changeCardShow() {
        tvMakeIouComputeRealNums.setText("借款金额 " + lendNum + " - 手续费 0");
        tvMakeIouRealNums.setText("¥ " + lendNum);
        float interestMoney = (float) Math.round(lendInterest / 100 / 365 * (lendNum) * lendDay * 100) / 100;
        tvMakeIouComputeAll.setText("本金 " + lendNum + " + 利息 " + interestMoney);
        tvMakeIouRepayAllNums.setText("¥ " + (lendNum + interestMoney));
        tvMakeIouRepayAllNums1.setText("¥ " + (lendNum + interestMoney));
        tvMakeIouRepayDate.setText(Kits.Date.dayComputer(Kits.Date.getCurrentTime(), lendDay));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_make_iou;
    }

    @Override
    public MakeIOUPresent newP() {
        return new MakeIOUPresent();
    }


    @OnClick({R.id.tv_makeIou_repayMethod, R.id.tv_makeIou_lendDays, R.id.tv_makeIou_interest, R.id.btn_makeIou_makeIou
            , R.id.iv_makeIou_delete, R.id.iv_makeIou_lendDays, R.id.iv_makeIou_interest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_makeIou_repayMethod:
                break;
            case R.id.iv_makeIou_delete:
                etMakeIouLendNum.setText("");
                break;
            case R.id.tv_makeIou_lendDays:
            case R.id.iv_makeIou_lendDays:
                new QMUIDialog.MenuDialogBuilder(context).addItems(lendDays, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lendDay = Integer.parseInt(lendDays[i].replace("天", ""));
                        tvMakeIouLendDays.setText(lendDays[i]);
                        tvMakeIouLendDays.setTextColor(text_black);
                        changeCardShow();
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.tv_makeIou_interest:
            case R.id.iv_makeIou_interest:
                new QMUIDialog.MenuDialogBuilder(context).addItems(lendInterests, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lendInterest = 10 + i;
                        tvMakeIouInterest.setText(lendInterests[i]);
                        tvMakeIouInterest.setTextColor(text_black);
                        changeCardShow();
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.btn_makeIou_makeIou:

                String loanAmount = etMakeIouLendNum.getText().toString().trim();//loanAmount 借款金额
                String repaymentMethod = tvMakeIouRepayMethod.getText().toString().trim();//repaymentMethod 还款方式
                String loanRate = lendInterest + "";//loanRate 借款利率
                String loanDate = lendDay + "";//loanDate 借款天数
                if (TextUtils.isEmpty(borrower)) {
                    ToastUtil.showToast("请先前往我的资料进行身份认证");
                } else if (TextUtils.isEmpty(loanAmount)) {
                    ToastUtil.showToast("请填写借款金额!");
                } else if (lendDay == 0) {
                    ToastUtil.showToast("请选择借款天数!");
                } else if (lendInterest == 0) {
                    ToastUtil.showToast("请选择年化利率!");
                } else {
                    getP().makeIou(this, borrower, loanAmount, loanRate, repaymentMethod, loanDate);
                }
                break;
        }
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(MakeIOUActivity.class)
                .launch();
    }

    public void makeIouSuccess(String iouid) {
        AssessActivity.launch(this, iouid);
        BusFactory.getBus().post(new OrderEvent());
        SPUtils.getInstance(this).save(Constant.IOUID, iouid);
    }
}
