package com.ripple.lendmoney.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.event.RefreshUserInfoEvent;
import com.ripple.lendmoney.present.IdCardFragPresent;
import com.ripple.lendmoney.ui.activity.AuthenticateActivity;
import com.ripple.lendmoney.utils.BitmapPhotoUtil;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.SPUtils;
import com.ripple.lendmoney.utils.ToastUtil;
import com.ripple.lendmoney.widget.cameralibrary.CameraActivity;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusFactory;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/4 23:16.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class IdCardFragment extends BaseLazyFragment<IdCardFragPresent> {


    //    @BindView(R.id.iv_idcardFrag_head)
//    ImageView ivIdcardFragHead;
    @BindView(R.id.iv_idcardFrag_idcard_front)
    ImageView ivIdcardFragIdcardFront;
    @BindView(R.id.iv_idcardFrag_idcard_back)
    ImageView ivIdcardFragIdcardBack;
    @BindView(R.id.et_idcardFrag_idcard)
    EditText etIdcardFragIdcard;
    @BindView(R.id.et_idcardFrag_name)
    EditText etIdcardFragName;
    @BindView(R.id.et_idcardFrag_wechat)
    EditText etIdcardFragWeChat;
    @BindView(R.id.btn_idcardFrag_commit)
    Button btnIdcardFragCommit;
    private HashMap<Object, File> fileMap = new HashMap<>();

    protected void doTakePhoto(int requestCode) {
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra("type", requestCode);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        btnIdcardFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_idcard_layout;
    }

    @Override
    public IdCardFragPresent newP() {
        return new IdCardFragPresent();
    }


    @OnClick({R.id.iv_idcardFrag_idcard_front, R.id.iv_idcardFrag_idcard_front_b, R.id.iv_idcardFrag_idcard_back,
            R.id.iv_idcardFrag_idcard_back_b, R.id.btn_idcardFrag_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_idcardFrag_idcard_front:
            case R.id.iv_idcardFrag_idcard_front_b:
                getRxPermissions().request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) {//同意
                                doTakePhoto(Constant.IDCARD_FRONT_DATA);
                            } else {//拒绝
                                ToastUtil.showToast("亲，同意了权限才能更好的为您服务哦");
                            }
                        });
                break;
            case R.id.iv_idcardFrag_idcard_back:
            case R.id.iv_idcardFrag_idcard_back_b:
                getRxPermissions().request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) {//同意
                                doTakePhoto(Constant.IDCARD_BACK_DATA);
                            } else {//拒绝
                                ToastUtil.showToast("亲，同意了权限才能更好的为您服务哦");
                            }
                        });
                break;
            case R.id.btn_idcardFrag_commit:
                String idCardNo = etIdcardFragIdcard.getText().toString().trim();
                String realName = etIdcardFragName.getText().toString().trim();
                String wechatNumber = etIdcardFragWeChat.getText().toString().trim();
                if (TextUtils.isEmpty(realName)) {
                    ToastUtil.showToast("请输入您的真实姓名");
                } else if (TextUtils.isEmpty(idCardNo)) {
                    ToastUtil.showToast("请输入您的身份证号");
                } else if (TextUtils.isEmpty(wechatNumber)) {
                    ToastUtil.showToast("请输入您的微信账号");
                } else if (!idCardNo.matches(Constant.REG_IDCARD)) {
                    ToastUtil.showToast("请输入正确的身份证号码");
                } else if (fileMap.size() != 2) {
                    ToastUtil.showToast("请拍摄身份证的正反面照片");
                } else {
                    getP().uploadIdCardInfo(context, realName, idCardNo, wechatNumber, fileMap);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("身份认证页面返回响应++++" + requestCode);
        if (data == null) {
            return;
        }
        String photoPath = data.getStringExtra(Constant.IntentKeyFilePath);
        if (photoPath == null) {
            return;
        }
        Bitmap photo = BitmapPhotoUtil.getSmallBitmap(photoPath);
        if (photo != null) {
            File f = new File(photoPath);
            if (f != null && f.exists()) {
                switch (requestCode) {
                    case Constant.IDCARD_FRONT_DATA:
                        ivIdcardFragIdcardFront.setImageBitmap(photo);
                        fileMap.put(Constant.IDCARD_FRONT_DATA, f);
                        break;
                    case Constant.IDCARD_BACK_DATA:
                        ivIdcardFragIdcardBack.setImageBitmap(photo);
                        fileMap.put(Constant.IDCARD_BACK_DATA, f);
                        break;
                }
            }

        }

    }


    public void uploadSuccess() {
        ToastUtil.showToast("上传成功");
        BusFactory.getBus().post(new RefreshUserInfoEvent());
        SPUtils.getInstance(context).save(Constant.REALNAME, etIdcardFragName.getText().toString().trim());
        if (GlobleParms.AuthenticateCanNext) {
            ((AuthenticateActivity) context).selectFragment(Constant.TYPE_FAMILYFRAG);
        } else {
            context.finish();
        }
    }
}
