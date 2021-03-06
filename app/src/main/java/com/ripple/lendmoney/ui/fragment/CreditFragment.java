package com.ripple.lendmoney.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseLazyFragment;
import com.ripple.lendmoney.base.Constant;
import com.ripple.lendmoney.base.GlobleParms;
import com.ripple.lendmoney.event.RefreshUserInfoEvent;
import com.ripple.lendmoney.present.CreditFragPresent;
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
 * 作者: HuangShaobo on 2019/3/4 23:26.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class CreditFragment extends BaseLazyFragment<CreditFragPresent> {
    @BindView(R.id.iv_creditFrag_head)
    ImageView ivCreditFragHead;
    @BindView(R.id.iv_creditFrag_alipay)
    ImageView ivCreditFragAlipay;
    @BindView(R.id.iv_creditFrag_credit)
    ImageView ivCreditFragCredit;
    @BindView(R.id.btn_creditFrag_commit)
    Button btnCreditFragCommit;
    private File mCurrentPhotoFile;// 照相机拍照得到的图片
    private static final int CREDIT_INFO_DATA = 353;
    private static final int CREDIT_SCORE_DATA = 354;
    private HashMap<Object, File> filesMap = new HashMap<>();

    @Override
    public void getNetData() {

    }

    private void doPickPhotoFromGallery(int requestCode) {
        getRxPermissions().request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                            if (granted) {//同意
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                this.startActivityForResult(intent, requestCode);
                            } else {//拒绝
                                ToastUtil.showToast("亲，同意了权限才能更好的为您服务哦");
                            }
                        }

                );


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        btnCreditFragCommit.setText(GlobleParms.AuthenticateCanNext ? "下一步" : "提交");
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_credit_layout;
    }

    @Override
    public CreditFragPresent newP() {
        return new CreditFragPresent();
    }


    @OnClick({R.id.rl_credit_info, R.id.rl_credit_credit, R.id.btn_creditFrag_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_credit_info:
                doPickPhotoFromGallery(CREDIT_INFO_DATA);
                break;
            case R.id.rl_credit_credit:
                doPickPhotoFromGallery(CREDIT_SCORE_DATA);
                break;
            case R.id.btn_creditFrag_commit:
                if (filesMap.size() != 2) {
                    ToastUtil.showToast("请上传您的支付宝个人信息和芝麻信用分的页面截图");
                } else {
                    getP().uploadCreditInfo(context, filesMap);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("信用认证页面返回响应++++" + requestCode);
        if (data != null) {
            Uri uri = data.getData();
            if (null == uri) {
                return;
            } else {
                if (uri.toString().contains("MIUI/Gallery/cloud")) {
                    ToastUtil.showShort(context, "请选择本地相册的图片");
                    return;
                }
            }
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            mCurrentPhotoFile = new File(picturePath);
            cursor.close();
        }

        Bitmap photo = null;
        if (mCurrentPhotoFile != null) {
            photo = BitmapPhotoUtil.getSmallBitmap(mCurrentPhotoFile.getPath());
            if (photo == null) {
                ToastUtil.showToast("请检查您的手机读写权限");
                return;
            }
            switch (requestCode) {
                case CREDIT_INFO_DATA:
                    ivCreditFragAlipay.setImageBitmap(photo);
                    File front_f = BitmapPhotoUtil.saveToFile(photo, context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() +
                            File.separator + "credit_front.jpg");
                    filesMap.put(CREDIT_INFO_DATA, front_f);
                    break;
                case CREDIT_SCORE_DATA:
                    ivCreditFragCredit.setImageBitmap(photo);
                    File back_f = BitmapPhotoUtil.saveToFile(photo, context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() +
                            File.separator + "credit_back.jpg");
                    filesMap.put(CREDIT_SCORE_DATA, back_f);
                    break;
            }

        }

    }

    public void uploadSuccess() {
        BusFactory.getBus().post(new RefreshUserInfoEvent());
        ToastUtil.showToast("上传成功");
        if (GlobleParms.AuthenticateCanNext) {
            ((AuthenticateActivity) context).selectFragment(Constant.TYPE_CONTACTSFRAG);
        } else {
            context.finish();
        }
    }
}
