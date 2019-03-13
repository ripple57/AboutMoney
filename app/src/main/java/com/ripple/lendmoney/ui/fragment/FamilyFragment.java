package com.ripple.lendmoney.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.event.RefreshMyInfoEvent;
import com.ripple.lendmoney.present.FamilyFragPresent;
import com.ripple.lendmoney.ui.activity.AuthenticateActivity;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusFactory;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:28.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class FamilyFragment extends BaseLazyFragment<FamilyFragPresent> {
    @BindView(R.id.iv_familyFrag_head)
    ImageView ivFamilyFragHead;
    @BindView(R.id.btn_familyFrag_commit)
    Button btnFamilyFragCommit;
    @BindArray(R.array.directRelations)
    String[] directRelations;
    @BindArray(R.array.emergencynRelations)
    String[] emergencynRelations;
    @BindView(R.id.iv_familyFrag_direRelation)
    ImageView ivFamilyFragDireRelation;
    @BindView(R.id.tv_familyFrag_direRelation)
    TextView tvFamilyFragDireRelation;
    @BindView(R.id.et_familyFrag_direName)
    EditText etFamilyFragDireName;
    @BindView(R.id.et_familyFrag_direPhone)
    EditText etFamilyFragDirePhone;
    @BindView(R.id.iv_familyFrag_contactRelation)
    ImageView ivFamilyFragContactRelation;
    @BindView(R.id.tv_familyFrag_contactRelation)
    TextView tvFamilyFragContactRelation;
    @BindView(R.id.et_familyFrag_contactName)
    EditText etFamilyFragContactName;
    @BindView(R.id.et_familyFrag_contactPhone)
    EditText etFamilyFragContactPhone;

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }


    private void initView() {
        btnFamilyFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");
    }


    @Override
    public int getLayoutId() {
        return R.layout.frag_family_layout;
    }

    @Override
    public FamilyFragPresent newP() {
        return new FamilyFragPresent();
    }




    @OnClick({R.id.iv_familyFrag_direRelation, R.id.tv_familyFrag_direRelation, R.id.iv_familyFrag_contactRelation, R.id.tv_familyFrag_contactRelation, R.id.btn_familyFrag_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_familyFrag_direRelation:
            case R.id.tv_familyFrag_direRelation:
                new QMUIDialog.MenuDialogBuilder(context).addItems(directRelations, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvFamilyFragDireRelation.setText(directRelations[i]);
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.iv_familyFrag_contactRelation:
            case R.id.tv_familyFrag_contactRelation:
                new QMUIDialog.MenuDialogBuilder(context).addItems(emergencynRelations, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvFamilyFragContactRelation.setText(emergencynRelations[i]);
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
            case R.id.btn_familyFrag_commit:
                String directRelation = tvFamilyFragDireRelation.getText().toString().trim();
                String directName = etFamilyFragDireName.getText().toString().trim();
                String directPhone = etFamilyFragDirePhone.getText().toString().trim();
                String emergencyRelation = tvFamilyFragDireRelation.getText().toString().trim();
                String emergencyName = etFamilyFragContactName.getText().toString().trim();
                String emergencyPhone = etFamilyFragContactPhone.getText().toString().trim();
                if ("请选择".equals(directRelation) || TextUtils.isEmpty(directName) || TextUtils.isEmpty(directPhone)) {
                    ToastUtil.showToast("请完善直系亲属信息");
                } else if ("请选择".equals(emergencyRelation) || TextUtils.isEmpty(emergencyName) || TextUtils.isEmpty(emergencyPhone)) {
                    ToastUtil.showToast("请完善紧急联系人信息");
                } else if (!directPhone.matches(Constant.REG_PHONE) || !emergencyPhone.matches(Constant.REG_PHONE)) {
                    ToastUtil.showToast("请填写正确的手机号");
                } else {

                    getP().uploadFamilyInfo(context,directRelation,directName,directPhone,emergencyRelation,emergencyName,emergencyPhone);
                }
                break;
        }
    }
    public void uploadSuccess() {
        BusFactory.getBus().post(new RefreshMyInfoEvent());
        ToastUtil.showToast("上传成功");
        if (GlobleParms.AuthenticateCanNext) {
            ((AuthenticateActivity) context).selectFragment(Constant.TYPE_BANKCARDFRAG);
        } else {
            context.finish();
        }
    }
}
