package com.ripple.lendmoney.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.adapter.FamilyFragAdapter;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.present.FamilyFragPresent;
import com.ripple.lendmoney.ui.activity.AuthenticateActivity;
import com.ripple.lendmoney.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:28.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class FamilyFragment extends BaseLazyFragment<FamilyFragPresent> {
    @BindView(R.id.iv_familyFrag_head)
    ImageView ivFamilyFragHead;
    @BindView(R.id.rc_familyFrag_container)
    RecyclerView rcFamilyFragContainer;
    @BindView(R.id.btn_familyFrag_commit)
    Button btnFamilyFragCommit;
    @BindArray(R.array.directRelations)
    String[] directRelations;
    @BindArray(R.array.emergencynRelations)
    String[] emergencynRelations;
    private FamilyFragAdapter familyFragAdapter;
    ArrayList<Integer> familylist = new ArrayList<>();
    private TextView tv_familyItem_type;
    private TextView tv_familyItem_relation_show;
    private TextView tvDirectRelation;
    private EditText etDirectName;
    private EditText etDirectPhone;
    private TextView tvEmergencyRelation;
    private EditText etEmergencyName;
    private EditText etEmergencyPhone;

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initListenter();
    }

    private void initListenter() {
        tvDirectRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QMUIDialog.MenuDialogBuilder(context).addItems(directRelations, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvDirectRelation.setText(directRelations[i]);
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        tvEmergencyRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new QMUIDialog.MenuDialogBuilder(context).addItems(emergencynRelations, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvEmergencyRelation.setText(emergencynRelations[i]);
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }

    private void initView() {
        btnFamilyFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");

        rcFamilyFragContainer.setLayoutManager(new LinearLayoutManager(context));
        familyFragAdapter = new FamilyFragAdapter(R.layout.item_family_list_layout, familylist);//数据布局无所谓
        rcFamilyFragContainer.setAdapter(familyFragAdapter);
        rcFamilyFragContainer.setFocusableInTouchMode(false);
        initHeadView();


    }

    private void initHeadView() {
        //初始化头布局
        View directRelativesView = View.inflate(context, R.layout.item_family_list_layout, null);
        ((TextView) directRelativesView.findViewById(R.id.tv_familyItem_type)).setText("直系亲属");
        ((TextView) directRelativesView.findViewById(R.id.tv_familyItem_relation_show)).setText("亲属姓名");
        tvDirectRelation = (TextView) directRelativesView.findViewById(R.id.tv_familyItem_relation);
        etDirectName = (EditText) directRelativesView.findViewById(R.id.et_familyItem_name);
        etDirectPhone = (EditText) directRelativesView.findViewById(R.id.et_familyItem_phone);
        familyFragAdapter.addHeaderView(directRelativesView);


        View emergencyContactView = View.inflate(context, R.layout.item_family_list_layout, null);
        ((TextView) emergencyContactView.findViewById(R.id.tv_familyItem_type)).setText("紧急联系人");
        ((TextView) emergencyContactView.findViewById(R.id.tv_familyItem_relation_show)).setText("联系人姓名");
        tvEmergencyRelation = (TextView) emergencyContactView.findViewById(R.id.tv_familyItem_relation);
        etEmergencyName = (EditText) emergencyContactView.findViewById(R.id.et_familyItem_name);
        etEmergencyPhone = (EditText) emergencyContactView.findViewById(R.id.et_familyItem_phone);
        familyFragAdapter.addHeaderView(emergencyContactView);

    }


    @Override
    public int getLayoutId() {
        return R.layout.frag_family_layout;
    }

    @Override
    public FamilyFragPresent newP() {
        return new FamilyFragPresent();
    }


    @OnClick(R.id.btn_familyFrag_commit)
    public void onViewClicked() {

        String directRelation = tvDirectRelation.getText().toString().trim();
        String directName = etDirectName.getText().toString().trim();
        String directPhone = etDirectPhone.getText().toString().trim();
        String emergencyRelation = tvEmergencyRelation.getText().toString().trim();
        String emergencyName = etEmergencyName.getText().toString().trim();
        String emergencyPhone = etEmergencyPhone.getText().toString().trim();
//        if (TextUtils.isEmpty(directRelation) || TextUtils.isEmpty(directName) || TextUtils.isEmpty(directPhone)) {
//            ToastUtil.showToast("请完善直系亲属信息");
//        } else if (TextUtils.isEmpty(emergencyRelation) || TextUtils.isEmpty(emergencyName) || TextUtils.isEmpty(emergencyPhone)) {
//            ToastUtil.showToast("请完善紧急联系人信息");
//        } else if (!directPhone.matches(Constant.REG_PHONE) || !emergencyPhone.matches(Constant.REG_PHONE)) {
//            ToastUtil.showToast("请填写正确的手机号");
//        } else {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("directRelation", directRelation);
//            map.put("directName", directName);
//            map.put("directPhone", directPhone);
//            map.put("emergencyRelation", emergencyRelation);
//            map.put("emergencyName", emergencyName);
//            map.put("emergencyPhone", emergencyPhone);
//            getP().uploadFamilyInfo(map);
//        }
        getP().uploadFamilyInfo(new HashMap<>());

    }

    public void uploadSuccess() {
        ToastUtil.showToast("上传成功");
        if (GlobleParms.AuthenticateCanNext) {
            ((AuthenticateActivity) context).selectFragment(Constant.TYPE_BANKCARDFRAG);
        } else {
            context.finish();
        }
    }

}
