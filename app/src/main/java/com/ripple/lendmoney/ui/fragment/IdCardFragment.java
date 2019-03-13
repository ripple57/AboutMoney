package com.ripple.lendmoney.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.event.RefreshMyInfoEvent;
import com.ripple.lendmoney.present.IdCardFragPresent;
import com.ripple.lendmoney.ui.activity.AuthenticateActivity;
import com.ripple.lendmoney.utils.BitmapPhotoUtil;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.ToastUtil;

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
    @BindView(R.id.btn_idcardFrag_commit)
    Button btnIdcardFragCommit;
    private File mCurrentPhotoFile;// 照相机拍照得到的图片
    private static final int IDCARD_FRONT_DATA = 350;
    private static final int IDCARD_BACK_DATA = 351;
    private HashMap<Object, File> fileMap = new HashMap<>();

    protected void doTakePhoto(int requestCode) {
        File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
        if (!PHOTO_DIR.exists()) {
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
        }
        mCurrentPhotoFile = new File(PHOTO_DIR, BitmapPhotoUtil.getPhotoFileName());// 给新照的照片文件命名
        Intent intent = BitmapPhotoUtil.getTakePickIntent(context, mCurrentPhotoFile);
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


    @OnClick({R.id.iv_idcardFrag_idcard_front, R.id.iv_idcardFrag_idcard_back, R.id.btn_idcardFrag_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_idcardFrag_idcard_front:
                doTakePhoto(IDCARD_FRONT_DATA);
                break;
            case R.id.iv_idcardFrag_idcard_back:
                doTakePhoto(IDCARD_BACK_DATA);
                break;
            case R.id.btn_idcardFrag_commit:
                String idCardNo = etIdcardFragIdcard.getText().toString().trim();
                String realName = etIdcardFragName.getText().toString().trim();
                if (TextUtils.isEmpty(realName)) {
                    ToastUtil.showToast("请输入您的真实姓名");
                } else if (TextUtils.isEmpty(idCardNo)) {
                    ToastUtil.showToast("请输入您的身份证号");
                } else if (!idCardNo.matches(Constant.REG_IDCARD)) {
                    ToastUtil.showToast("请输入正确的身份证号码");
                } else if (fileMap.size() != 2) {
                    ToastUtil.showToast("请拍摄身份证的正反面照片");
                }else {
                    getP().uploadIdCardInfo(context,realName, idCardNo,fileMap);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("身份认证页面返回响应++++" + requestCode);
        Bitmap photo = null;
        if (mCurrentPhotoFile != null) {
            photo = BitmapPhotoUtil.getSmallBitmap(mCurrentPhotoFile.getPath());
        }
        if (photo != null) {
            File f = BitmapPhotoUtil.replaceMyBitmap(photo, mCurrentPhotoFile);
            if (f != null) {
                switch (requestCode) {
                    case IDCARD_FRONT_DATA:
                        ivIdcardFragIdcardFront.setImageBitmap(photo);
                        fileMap.put(IDCARD_FRONT_DATA, f);
                        break;
                    case IDCARD_BACK_DATA:
                        ivIdcardFragIdcardBack.setImageBitmap(photo);
                        fileMap.put(IDCARD_BACK_DATA, f);
                        break;
                }
            }

        }

    }


    public void uploadSuccess() {
        ToastUtil.showToast("上传成功");
        BusFactory.getBus().post(new RefreshMyInfoEvent());
        if (GlobleParms.AuthenticateCanNext) {
            ((AuthenticateActivity) context).selectFragment(Constant.TYPE_FAMILYFRAG);
        } else {
            context.finish();
        }
    }
}
