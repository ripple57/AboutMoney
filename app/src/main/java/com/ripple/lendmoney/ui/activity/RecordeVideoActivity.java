package com.ripple.lendmoney.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ripple.lendmoney.R;
import com.ripple.lendmoney.base.BaseActivity;
import com.ripple.lendmoney.present.RecordeVideoPresent;
import com.ripple.lendmoney.utils.LogUtils;
import com.yixia.camera.MediaRecorderNative;
import com.yixia.camera.VCamera;
import com.yixia.camera.model.MediaObject;
import com.yixia.videoeditor.adapter.UtilityAdapter;
import com.zhaoshuang.weixinrecorded.FocusSurfaceView;
import com.zhaoshuang.weixinrecorded.SDKUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;

public class RecordeVideoActivity extends BaseActivity<RecordeVideoPresent> {
    private static final int START_RECORDE = 0;
    private static final int END_RECORDE = 1;
    private static final int EYE = 2;
    private static final int MOUTH = 3;
    private static final int HEAD = 4;
    private static final int UPLOAD_VIDEO = 5;
    @BindView(R.id.sv_ffmpeg)
    FocusSurfaceView sv_ffmpeg;


    private MediaRecorderNative mMediaRecorder;
    private MediaObject mMediaObject;
    private AlertDialog progressDialog;
    private TextView dialogTextView;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_RECORDE:
                    mMediaRecorder.startRecord();
                    handler.sendEmptyMessageDelayed(END_RECORDE, 10000);
                    break;
                case EYE:
                    mMediaRecorder.startRecord();
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
                    mMediaRecorder.stopRecord();
                    AuthenticateActivity.launch(RecordeVideoActivity.this);
                    handler.sendEmptyMessageDelayed(UPLOAD_VIDEO, 3000);
                    break;
                case UPLOAD_VIDEO:
                    int progress = UtilityAdapter.FilterParserAction("", UtilityAdapter.PARSERACTION_PROGRESS);
                    if (progress == 100) {
                        syntVideo();
                    } else if (progress == -1) {
                        Toast.makeText(getApplicationContext(), "视频保存失败", Toast.LENGTH_SHORT).show();
                    } else {
                        sendEmptyMessageDelayed(UPLOAD_VIDEO, 50);
                    }
                    break;
            }
        }
    };


    private String video_path;

    @Override
    protected String topBarTitle() {
        return "人脸验证";
    }

    @Override
    protected boolean topBarIsShow() {
        return false;
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initVCamera();
        initMediaRecorder();
        handler.sendEmptyMessageDelayed(EYE, 2000);
    }

    private void initMediaRecorder() {
        mMediaRecorder = new MediaRecorderNative();
        mMediaRecorder.switchCamera();//设置前置摄像头;
        String key = String.valueOf(System.currentTimeMillis());
        //设置缓存文件夹
        mMediaObject = mMediaRecorder.setOutputDirectory(key, VCamera.getVideoCachePath());
        //设置视频预览源
        mMediaRecorder.setSurfaceHolder(sv_ffmpeg.getHolder());
        //准备
        mMediaRecorder.prepare();
        //滤波器相关
        UtilityAdapter.freeFilterParser();
        UtilityAdapter.initFilterParser();
    }

    private void initVCamera() {
        video_path = "/sdcard/WeiXin/";
        video_path += String.valueOf(System.currentTimeMillis());
        //设置视频缓存路径
        VCamera.setVideoCachePath(video_path);
        // log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recorde_video;
    }

    @Override
    public RecordeVideoPresent newP() {
        return new RecordeVideoPresent();
    }

    /**
     * 删除文件夹下所有文件, 只保留一个
     *
     * @param fileName 保留的文件名称
     */
    public static void deleteDirRoom(File dir, String fileName) {

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                deleteDirRoom(f, fileName);
            }
        } else if (dir.exists()) {
            if (!dir.getAbsolutePath().equals(fileName)) {
                dir.delete();
            }
        }
    }

    /**
     * 合成视频
     */
    @SuppressLint("StaticFieldLeak")
    private void syntVideo() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
//                if (dialogTextView != null) dialogTextView.setText("视频合成中");
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                long time = System.currentTimeMillis();
//                String format = formatter.format(time);
//                Log.e("Ripple57", "开始合成当前时间===" + time + "+格式化后+" + format);
            }

            @Override
            protected String doInBackground(Void... params) {
                String output = SDKUtil.VIDEO_PATH + "/finish.mp4";
                MediaObject.MediaPart mediaPart = mMediaObject.getMediaParts().get(0);
                List<String> list = new ArrayList<>();
                list.add(mediaPart.mediaPath);
                boolean flag = ts2Mp4(list, output);
//                List<String> pathList = new ArrayList<>();
//                for (int x = 0; x < mMediaObject.getMediaParts().size(); x++) {
//                    MediaObject.MediaPart mediaPart = mMediaObject.getMediaParts().get(x);
//                    String mp4Path = SDKUtil.VIDEO_PATH + "/" + x + ".mp4";
//                    List<String> list = new ArrayList<>();
//                    list.add(mediaPart.mediaPath);
//                    ts2Mp4(list, mp4Path);
//                    pathList.add(mp4Path);
//                }
//
//                List<String> tsList = new ArrayList<>();
//                for (int x = 0; x < pathList.size(); x++) {
//                    String path = pathList.get(x);
//                    String ts = SDKUtil.VIDEO_PATH + "/" + x + ".ts";
//                    mp4ToTs(path, ts);
//                    tsList.add(ts);
//                }
//
//                String output = SDKUtil.VIDEO_PATH + "/finish.mp4";
//                boolean flag = ts2Mp4(tsList, output);
                if (!flag) output = "";
//                deleteDirRoom(new File(SDKUtil.VIDEO_PATH), output);
                return output;
            }

            @Override
            protected void onPostExecute(String result) {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                long time = System.currentTimeMillis();
//                String format = formatter.format(time);
//                Log.e("Ripple57", "合成结束当前时间===" + time + "+格式化后+" + format);
//                closeProgressDialog();
                if (!TextUtils.isEmpty(result)) {
                    getP().upLoadVideo(RecordeVideoActivity.this, result);
                } else {
                    Toast.makeText(getApplicationContext(), "视频识别失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void mp4ToTs(String path, String output) {

        //./ffmpeg -i 0.mp4 -c copy -bsf:v h264_mp4toannexb -f mpegts ts0.ts

        StringBuilder sb = new StringBuilder("ffmpeg");
        sb.append(" -i");
        sb.append(" " + path);
        sb.append(" -c");
        sb.append(" copy");
        sb.append(" -bsf:v");
        sb.append(" h264_mp4toannexb");
        sb.append(" -f");
        sb.append(" mpegts");
        sb.append(" " + output);

        int i = UtilityAdapter.FFmpegRun("", sb.toString());
    }

    public boolean ts2Mp4(List<String> path, String output) {

        //ffmpeg -i "concat:ts0.ts|ts1.ts|ts2.ts|ts3.ts" -c copy -bsf:a aac_adtstoasc out2.mp4

        StringBuilder sb = new StringBuilder("ffmpeg");
        sb.append(" -i");
        String concat = "concat:";
        for (String part : path) {
            concat += part;
            concat += "|";
        }
        concat = concat.substring(0, concat.length() - 1);
        sb.append(" " + concat);
        sb.append(" -c");
        sb.append(" copy");
        sb.append(" -bsf:a");
        sb.append(" aac_adtstoasc");
        sb.append(" -y");
        sb.append(" " + output);

        int i = UtilityAdapter.FFmpegRun("", sb.toString());
        return i == 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaRecorder.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaRecorder.stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaObject.cleanTheme();
        mMediaRecorder.release();
    }

    public void closeProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TextView showProgressDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = View.inflate(this, R.layout.dialog_loading, null);
        builder.setView(view);
        ProgressBar pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        TextView tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        tv_hint.setText("视频编译中");
        progressDialog = builder.create();
        progressDialog.show();

        return tv_hint;
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity).to(RecordeVideoActivity.class).launch();
    }

    public void uploadVideoSuccess() {
        LogUtils.e("视频上传成功!");
    }
}
