package com.ripple.lendmoney.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.FaceRecognitionPresent;
import com.ripple.lendmoney.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class FaceRecognitionActivity extends BaseActivity<FaceRecognitionPresent> {


    @BindView(R.id.btn_facefrag_startRecognition)
    Button btnStartRecognition;

    public static void launch(Activity context) {
        Router.newIntent(context).to(FaceRecognitionActivity.class).launch();
    }

    @Override
    protected String topBarTitle() {
        return "人脸识别";
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_face_recognition;
    }

    @Override
    public FaceRecognitionPresent newP() {
        return new FaceRecognitionPresent();
    }


    @OnClick(R.id.btn_facefrag_startRecognition)
    public void onViewClicked() {
        getRxPermissions().request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                .subscribe(granted -> {
                    if (granted) {//同意
                        RecordeFaceActivity.launch(this);
                    } else {//拒绝
                        ToastUtil.showToast("亲，同意了权限才能更好的为您服务哦");
                    }
                });
    }
}
