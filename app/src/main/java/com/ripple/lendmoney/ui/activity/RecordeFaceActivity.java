package com.ripple.lendmoney.ui.activity;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.RecordeFacePresent;
import com.ripple.lendmoney.utils.LogUtils;
import com.ripple.lendmoney.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;
import us.pinguo.svideo.bean.VideoInfo;
import us.pinguo.svideo.interfaces.ICameraProxyForRecord;
import us.pinguo.svideo.interfaces.IVideoPathGenerator;
import us.pinguo.svideo.interfaces.OnRecordListener;
import us.pinguo.svideo.interfaces.PreviewDataCallback;
import us.pinguo.svideo.interfaces.PreviewSurfaceListener;
import us.pinguo.svideo.interfaces.SurfaceCreatedCallback;
import us.pinguo.svideo.recorder.SAbsVideoRecorder;
import us.pinguo.svideo.recorder.SMediaCodecRecorder;

public class RecordeFaceActivity extends BaseActivity<RecordeFacePresent> {
    @BindView(R.id.textureview)
    TextureView mTextureView;
    private Camera mCamera;
    private static final int START_RECORDE = 0;
    private static final int END_RECORDE = 1;
    private static final int EYE = 2;
    private static final int MOUTH = 3;
    private static final int HEAD = 4;
    private SAbsVideoRecorder mRecorder;
    private PreviewDataCallback mCallback;
    private OnRecordListener recordListener;
    private Camera.Size mPreviewSize;
    public int mCameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_RECORDE:
                    mRecorder.startRecord();
                    handler.sendEmptyMessage(EYE);
                    break;
                case EYE:
                    Toast.makeText(getApplicationContext(), "请眨眼", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(MOUTH, 3000);
                    break;
                case MOUTH:
                    Toast.makeText(getApplicationContext(), "请张嘴", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(HEAD, 3000);
                    break;
                case HEAD:
                    Toast.makeText(getApplicationContext(), "请摇头", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(END_RECORDE, 3000);
                    break;
                case END_RECORDE:
                    mRecorder.stopRecord();
                    break;
            }
        }
    };

    @Override
    protected String topBarTitle() {
        return "验证人脸";
    }

    @Override
    public void getNetData() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initListener();
    }

    private void initListener() {
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                startPreview(surface);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        ICameraProxyForRecord cameraProxyForRecord = new ICameraProxyForRecord() {

            @Override
            public void addSurfaceDataListener(PreviewSurfaceListener listener, SurfaceCreatedCallback callback) {

            }

            @Override
            public void removeSurfaceDataListener(PreviewSurfaceListener listener) {

            }

            @Override
            public void addPreviewDataCallback(PreviewDataCallback callback) {
                mCallback = callback;
            }

            @Override
            public void removePreviewDataCallback(PreviewDataCallback callback) {
                mCallback = null;
            }

            @Override
            public int getPreviewWidth() {
                return mPreviewSize.width;
            }

            @Override
            public int getPreviewHeight() {
                return mPreviewSize.height;
            }

            @Override
            public int getVideoRotation() {
                return mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK ? 90 : 270;
            }
        };
        mRecorder = new SMediaCodecRecorder(this, cameraProxyForRecord);
        mRecorder.setVideoPathGenerator(new IVideoPathGenerator() {
            @Override
            public String generate() {
                String img_path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() +
                        File.separator + "face.mp4";
                return img_path;
            }
        });
        recordListener = new OnRecordListener() {
            @Override
            public void onRecordSuccess(VideoInfo videoInfo) {
                LogUtils.e("录制成功!!" + videoInfo.getVideoPath());
                File file = new File(videoInfo.getVideoPath());
                if (!file.exists()) {
                    ToastUtil.showToast("视频录制失败,请重新录制");
                    handler.sendEmptyMessageDelayed(START_RECORDE,2000);
                    return;
                }
                getP().upLoadVideo(RecordeFaceActivity.this, file);
            }

            @Override
            public void onRecordStart() {
                LogUtils.e("录制开始!!");
            }

            @Override
            public void onRecordFail(Throwable t) {
                LogUtils.e("录制失败!!" + t.toString());
            }

            @Override
            public void onRecordStop() {
                LogUtils.e("录制onRecordStop!!");
            }

            @Override
            public void onRecordPause() {
                LogUtils.e("录制onRecordPause!");
            }

            @Override
            public void onRecordResume() {
                LogUtils.e("录制onRecordResume!");
            }
        };
        mRecorder.addRecordListener(recordListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recorde_face;
    }

    @Override
    public RecordeFacePresent newP() {
        return new RecordeFacePresent();
    }

    private void startPreview(SurfaceTexture surfaceTexture) {
        openCamera();
        mCamera.setDisplayOrientation(90);
        Camera.Parameters parameters = mCamera.getParameters();
        mPreviewSize = getPreviewSize();
        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        parameters.setPreviewFormat(ImageFormat.NV21);
        List<String> focusModes = parameters.getSupportedFocusModes();
        for (String s : focusModes) {
            if (Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE.equals(s)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                break;
            }
        }
        mCamera.addCallbackBuffer(new byte[(int) (mPreviewSize.width * mPreviewSize.height * 1.5f)]);
        mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                if (mCallback != null) {
                    long timeUs = System.nanoTime() / 1000;
                    mCallback.onPreviewData(data, timeUs);
                }
                camera.addCallbackBuffer(data);
            }
        });
        mCamera.setParameters(parameters);
        adjustPreviewSize();
        try {
            mCamera.setPreviewTexture(surfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    private Camera.Size getPreviewSize() {
        List<Camera.Size> sizeList = mCamera.getParameters().getSupportedPreviewSizes();
        for (int i = 0; i < sizeList.size(); i++) {
            Camera.Size size = sizeList.get(i);
            if (size.width == 640 || size.width == 960 || size.width == 1280) {
                return size;
            }
        }
        return sizeList.get(0);
    }

    private void openCamera() {
        if (mCamera != null) {
            return;
        }
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int k = 0; k < Camera.getNumberOfCameras(); k++) {
            Camera.getCameraInfo(k, info);
            if (info.facing == mCameraFacing) {
                mCamera = Camera.open(k);
                break;
            }
        }
        if (mCamera == null) {
            throw new RuntimeException("Can't open frontal camera");
        }
    }

    private void adjustPreviewSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTextureView.getLayoutParams();
        params.height = displayMetrics.heightPixels;
        params.width = (int) (mPreviewSize.height / (float) mPreviewSize.width * params.height);
        mTextureView.setLayoutParams(params);
        //设置镜像
//        if (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//            mTextureView.setScaleX(-1);
//        } else {
//            mTextureView.setScaleX(1);
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(START_RECORDE, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
        mRecorder.cancelRecord();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecorder.removeRecordListener(recordListener);
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(RecordeFaceActivity.class)
                .launch();
    }

    public void uploadVideoSuccess() {
        ToastUtil.showToast("视频上传成功");
        AuthenticateActivity.launch(RecordeFaceActivity.this);
        finish();
    }

    public void uploadVideoFail() {
        ToastUtil.showToast("视频录制失败,请联系客服");
        AuthenticateActivity.launch(RecordeFaceActivity.this);
        finish();
    }
}

