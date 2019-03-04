package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.qmuiteam.qmui.layout.QMUIButton;
import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.FaceRecognitionPresent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;

public class FaceRecognitionActivity extends BaseActivity<FaceRecognitionPresent> {


    @BindView(R.id.btn_facefrag_startRecognition)
    QMUIButton btnStartRecognition;

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

    }
}
